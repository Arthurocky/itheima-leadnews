package com.itheima.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.admin.pojo.AdChannel;
import org.mapstruct.Mapper;
import org.mybatis.spring.annotation.MapperScan;

/**
 * @author Arthurocky
 */
public interface AdChannelMapper extends BaseMapper<AdChannel> {

    //CRUD
    //查询所有
   /* @Select(value="select * from ad_channel")
    List<AdChannel> findAll();*/

    //根据ID 查询

    //根据ID 删除

    //根据ID 更新
}
