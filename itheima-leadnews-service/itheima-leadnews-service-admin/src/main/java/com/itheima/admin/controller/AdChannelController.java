package com.itheima.admin.controller;

import com.itheima.admin.pojo.AdChannel;
import com.itheima.admin.service.AdChannelService;
import com.itheima.common.pojo.PageInfo;
import com.itheima.common.pojo.PageRequestDto;
import com.itheima.common.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Arthurocky
 */
@RestController
@RequestMapping("/channel")
public class AdChannelController {

    @Autowired
    private AdChannelService adChannelService;

    //查询所有
    @GetMapping("/findAll")
    public Result<List<AdChannel>> findAll() {
        List<AdChannel> list = adChannelService.list();
        return Result.ok(list);
    }

    //顺便 就能通过代码生成接口文档

    @PostMapping("/search")
    public Result<PageInfo<AdChannel>> search(@RequestBody PageRequestDto<AdChannel> pageRequestDto){
        PageInfo<AdChannel> pageInfo =  adChannelService.search(pageRequestDto);
        return Result.ok(pageInfo);
    }

    //新增

    //删除

    //更新


}
