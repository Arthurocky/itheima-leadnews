package com.itheima.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.admin.mapper.AdChannelMapper;
import com.itheima.admin.pojo.AdChannel;
import com.itheima.admin.service.AdChannelService;
import com.itheima.common.pojo.PageInfo;
import com.itheima.common.pojo.PageRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Arthurocky
 */
@Service
public class AdChannelServiceImpl extends ServiceImpl<AdChannelMapper, AdChannel> implements AdChannelService {


    @Autowired
    private AdChannelMapper adChannelMapper;

    /**
     * 根据分页请求对象 分页查询频道列表
     */
    @Override
    @PostMapping("/search")
    public PageInfo<AdChannel> search(@RequestBody PageRequestDto<AdChannel> pageRequestDto)
    {
        //核心的逻辑是什么 调用核心的API之后 需要什么参数 给他传递什么参数    再进行校验      分页查询

        //获取当前页码和每页显示的行 以及获取页面传递过来的条件对象
        Long page = pageRequestDto.getPage();
        Long size = pageRequestDto.getSize();

        AdChannel channel = pageRequestDto.getBody();

        // 创建分页条件
        Page<AdChannel> channelPage = new Page<AdChannel>(page, size);

        // 创建查询条件
        QueryWrapper<AdChannel> queryWrapper = new QueryWrapper<>();

        queryWrapper.like(channel.getName() != null, "name", channel.getName());
        queryWrapper.eq(channel.getName() != null, "status", channel.getStatus());

        //执行查询得到结果
        IPage<AdChannel> iPage = adChannelMapper.selectPage(channelPage, queryWrapper);

        //封装到pageinfo对象中返回
        return new PageInfo<AdChannel>(
                iPage.getCurrent(),
                iPage.getSize(),
                iPage.getTotal(),
                iPage.getPages(),
                iPage.getRecords()
        );
    }

    /**
     * mybatis方式
     * controller--》service--》mapper
     */
    /*@Autowired
    private AdChannelMapper adChannelMapper;

    @Override
    public List<AdChannel> findAll() {
        return adChannelMapper.selectList(null);
    }*/
}
