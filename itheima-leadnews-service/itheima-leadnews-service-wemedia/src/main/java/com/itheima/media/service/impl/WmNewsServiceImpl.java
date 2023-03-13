package com.itheima.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.common.pojo.PageInfo;
import com.itheima.common.pojo.PageRequestDto;
import com.itheima.common.utils.RequestContextUtil;
import com.itheima.media.dto.WmNewsDto;
import com.itheima.media.mapper.WmNewsMapper;
import com.itheima.media.pojo.WmNews;
import com.itheima.media.service.WmNewsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 自媒体图文内容信息表 服务实现类
 * </p>
 * Arthurocky
 */
@Service
public class WmNewsServiceImpl extends ServiceImpl<WmNewsMapper, WmNews> implements WmNewsService {

    @Override
    public PageInfo<WmNews> findByPageDto(PageRequestDto<WmNewsDto> pageRequestDto)
    {

        //select * from wm_news where status=? and title=? and user_id=当前的用户的ID and channl_id=? and publishedTime between ? and ? limit 0,10

        // 获取请求体对象
        WmNewsDto dtoBody = pageRequestDto.getBody();

        QueryWrapper<WmNews> wrapper = new QueryWrapper<>();

        //添加查询当前用户的文章的列表
        String userInfo = RequestContextUtil.getUserInfo();
        wrapper.eq("user_id",Integer.valueOf(userInfo));

        //判断搜索条件 是否为空 如果不为空 则拼接查询条件
        if (dtoBody != null) {
            //状态
            if (!StringUtils.isEmpty(dtoBody.getStatus())) {
                wrapper.eq("status",dtoBody.getStatus());
            }
            //标题
            if (!StringUtils.isEmpty(dtoBody.getTitle())) {
                wrapper.like("title",dtoBody.getTitle());
            }
            //频道
            if (!StringUtils.isEmpty(dtoBody.getChannelId())) {
                wrapper.eq("channel_id",dtoBody.getChannelId());
            }
            //发布时间
            if (!StringUtils.isEmpty(dtoBody.getStartTime()) && !StringUtils.isEmpty(dtoBody.getEndTime())) {
                wrapper.between("publish_time", dtoBody.getStartTime(),dtoBody.getEndTime());
            }
        }

        //Page用于定义每页的规格
        //IPage以规格和其他内容为参数，将记录进行分页
        long page = pageRequestDto.getPage();
        long size = pageRequestDto.getSize();

        //4.执行分页查询
        IPage<WmNews> pageSetup = new Page<WmNews>(page,size);
        IPage<WmNews> iPage = this.page(pageSetup, wrapper);

        return new PageInfo<WmNews>(iPage.getCurrent(),iPage.getSize(), iPage.getTotal(),iPage.getPages(),iPage.getRecords());

    }
}
