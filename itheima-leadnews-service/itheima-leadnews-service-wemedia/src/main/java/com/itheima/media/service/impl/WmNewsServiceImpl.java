package com.itheima.media.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.common.pojo.PageInfo;
import com.itheima.common.pojo.PageRequestDto;
import com.itheima.common.utils.RequestContextUtil;
import com.itheima.media.dto.ContentNode;
import com.itheima.media.dto.WmNewsDto;
import com.itheima.media.dto.WmNewsDtoSave;
import com.itheima.media.mapper.WmNewsMapper;
import com.itheima.media.pojo.WmNews;
import com.itheima.media.service.WmNewsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 自媒体图文内容信息表 服务实现类
 * </p>
 * Arthurocky
 */
@Service
public class WmNewsServiceImpl extends ServiceImpl<WmNewsMapper, WmNews> implements WmNewsService {

    @Autowired
    private WmNewsMapper wmNewsMapper;

    @Override
    public PageInfo<WmNews> findByPageDto(PageRequestDto<WmNewsDto> pageRequestDto)
    {

        //select * from wm_news where status=? and title=? and user_id=当前的用户的ID and channl_id=? and publishedTime between ? and ? limit 0,10

        // 获取请求体对象
        WmNewsDto dtoBody = pageRequestDto.getBody();

        QueryWrapper<WmNews> wrapper = new QueryWrapper<>();

        //添加查询当前用户的文章的列表
        String userInfo = RequestContextUtil.getUserInfo();
        wrapper.eq("user_id", Integer.valueOf(userInfo));

        //判断搜索条件 是否为空 如果不为空 则拼接查询条件
        if (dtoBody != null) {
            //状态
            if (!StringUtils.isEmpty(dtoBody.getStatus())) {
                wrapper.eq("status", dtoBody.getStatus());
            }
            //标题
            if (!StringUtils.isEmpty(dtoBody.getTitle())) {
                wrapper.like("title", dtoBody.getTitle());
            }
            //频道
            if (!StringUtils.isEmpty(dtoBody.getChannelId())) {
                wrapper.eq("channel_id", dtoBody.getChannelId());
            }
            //发布时间
            if (!StringUtils.isEmpty(dtoBody.getStartTime()) && !StringUtils.isEmpty(dtoBody.getEndTime())) {
                wrapper.between("publish_time", dtoBody.getStartTime(), dtoBody.getEndTime());
            }
        }

        //Page用于定义每页的规格
        //IPage以规格和其他内容为参数，将记录进行分页
        long page = pageRequestDto.getPage();
        long size = pageRequestDto.getSize();

        //4.执行分页查询
        IPage<WmNews> pageSetup = new Page<WmNews>(page, size);
        IPage<WmNews> iPage = this.page(pageSetup, wrapper);

        return new PageInfo<WmNews>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getPages(), iPage.getRecords());

    }

    @Override
    public WmNews save(WmNewsDtoSave wmNewsDtoSave, Integer isSubmit)
    {

        WmNews wmNews = new WmNews();
        //copy数据
        BeanUtils.copyProperties(wmNewsDtoSave, wmNews);
        String userInfo = RequestContextUtil.getUserInfo();
        //开始补充设置数据
        wmNews.setUserId(Integer.valueOf(userInfo));
        //将输入的内容转为JSON存入数据库中
        wmNews.setContent(JSON.toJSONString(wmNewsDtoSave.getContent()));
        //设置封面图片 将list 转成一个以逗号分隔的字符串
        List<String> images = wmNewsDtoSave.getImages();
        if (images != null && images.size() > 0) {
            wmNews.setImages(String.join(",", wmNewsDtoSave.getImages()));
        }
        //如果是自动图(Type为-1)
        if (wmNewsDtoSave.getType() == -1) {
            //则判断 图文内容中的图片有多少张，如果是>2 则为多图 如果是1 则为单图 如果是小于1 则为 无图
            List<String> imagesFromContent = getImagesFromContent(wmNewsDtoSave);
            //说明是多图
            if (imagesFromContent.size() > 2) {
                //设置为多图
                wmNews.setType(3);
                //并设置图片 因为页面没有传递了
                wmNews.setImages(String.join(",", imagesFromContent));
            } else if (imagesFromContent.size() > 0 && imagesFromContent.size() <= 2) {
                //设置为单图
                wmNews.setType(1);
                //设置图片为一张
                wmNews.setImages(imagesFromContent.get(0));
            } else {
                //无图
                wmNews.setType(0);
                //空字符串
                wmNews.setImages("");
            }
        }
        //保存草稿或者提交审核
        wmNews.setStatus(isSubmit);
        if (isSubmit == 1) {
            wmNews.setSubmitedTime(LocalDateTime.now());
        }
        //修改数据
        if (wmNewsDtoSave.getId() != null) {
            //this.updateById(wmNews);
            wmNewsMapper.updateById(wmNews);
        } else {
            //添加数据
            wmNews.setCreatedTime(LocalDateTime.now());
            //this.save(wmNews);
            wmNewsMapper.insert(wmNews);
        }
        return wmNews;
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @Override
    public WmNewsDtoSave getDtoById(Integer id)
    {
        WmNews wmNews = wmNewsMapper.selectById(id);
        if (wmNews != null) {
            WmNewsDtoSave wmNewsDtoSave = new WmNewsDtoSave();
            //copy
            BeanUtils.copyProperties(wmNews, wmNewsDtoSave);
            //设置内容,转为JSON显示
            String content = wmNews.getContent();
            List<ContentNode> contentNodes = JSON.parseArray(content, ContentNode.class);
            wmNewsDtoSave.setContent(contentNodes);
            //设置图片
            String images = wmNews.getImages();
            if (!StringUtils.isEmpty(images)) {
                //设置图片列表
                wmNewsDtoSave.setImages(Arrays.asList(images.split(",")));
            }
            return wmNewsDtoSave;
        }
        return null;
    }


    /**
     * 获取图片路径列表
     */
    private List<String> getImagesFromContent(WmNewsDtoSave wmNewsDtoSave)
    {
        List<ContentNode> content = wmNewsDtoSave.getContent();
        List<String> images = new ArrayList<String>();
        for (ContentNode contentNode : content) {
            //图片
            if (contentNode.getType().equals("image")) {
                String value = contentNode.getValue();
                images.add(value);
            }
        }
        return images;
    }
}
