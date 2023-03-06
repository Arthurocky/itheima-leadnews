package com.itheima.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.admin.pojo.AdChannel;
import com.itheima.common.pojo.PageInfo;
import com.itheima.common.pojo.PageRequestDto;

/**
 * @author Arthurocky
 */
public interface AdChannelService extends IService<AdChannel> {

    /**
     * 分页查询
     */
    PageInfo<AdChannel> search(PageRequestDto<AdChannel> pageRequestDto);

    //查询所有
    //List<AdChannel> findAll();

    //根据ID 查询

    //根据ID 删除

    //更加ID 更新
}
