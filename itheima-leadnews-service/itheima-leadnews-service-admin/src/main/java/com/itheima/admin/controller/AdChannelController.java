package com.itheima.admin.controller;

import com.itheima.admin.pojo.AdChannel;
import com.itheima.admin.service.AdChannelService;
import com.itheima.common.pojo.PageInfo;
import com.itheima.common.pojo.PageRequestDto;
import com.itheima.common.pojo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Arthurocky
 */
@RestController
@RequestMapping("/channel")
@Api(value = "频道管理", tags = "channel管理", description = "这个是描述频道管理的接口")
public class AdChannelController {

    @Autowired
    private AdChannelService adChannelService;

    /**
     * 查询所有
     */
    @GetMapping("/findAll")
    @ApiOperation(value="查询所有",notes = "这个是频道管理里面查询所有的频道测试用的",tags ="频道管理" )
    public Result<List<AdChannel>> findAll() {
        List<AdChannel> list = adChannelService.list();
        return Result.ok(list);
    }

    /**
     * 顺便 就能通过代码生成接口文档
     */
    @PostMapping("/search")
    public Result<PageInfo<AdChannel>> search(@RequestBody PageRequestDto<AdChannel> pageRequestDto){
        PageInfo<AdChannel> pageInfo =  adChannelService.search(pageRequestDto);
        return Result.ok(pageInfo);
    }

    //新增

    //删除

    //更新


}
