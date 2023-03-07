package com.itheima.admin.controller;

import com.itheima.admin.pojo.AdChannel;
import com.itheima.admin.service.AdChannelService;
import com.itheima.common.exception.LeadNewsException;
import com.itheima.common.pojo.PageInfo;
import com.itheima.common.pojo.PageRequestDto;
import com.itheima.common.pojo.Result;
import com.itheima.common.pojo.StatusCode;
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
    @ApiOperation(value = "查询所有", notes = "这个是频道管理里面查询所有的频道测试用的", tags = "频道管理")
    public Result<List<AdChannel>> findAll()
    {
        List<AdChannel> list = adChannelService.list();
        return Result.ok(list);
    }

    /**
     * 顺便 就能通过代码生成接口文档
     */
    @PostMapping("/search")
    public Result<PageInfo<AdChannel>> search(@RequestBody PageRequestDto<AdChannel> pageRequestDto)
    {
        PageInfo<AdChannel> pageInfo = adChannelService.search(pageRequestDto);
        return Result.ok(pageInfo);
    }

    /**
     * 新增
     */
    @PostMapping
    public Result insert(@RequestBody AdChannel adChannel)
    {
        boolean flag = adChannelService.save(adChannel);
        if (!flag) {
            return Result.error();
        }
        return Result.ok();
    }


    /**
     * 根据Id主键进行删除频道
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable(name = "id") Integer id)
    {
        boolean flag = adChannelService.removeById(id);
        if (!flag) {
            return Result.error();
        }
        return Result.ok();
    }


    /**
     * 根据id主键获取频道信息
     */
    @GetMapping("/{id}")
    public Result<AdChannel> findById(@PathVariable(name = "id") Integer id)
    {
        AdChannel channel = adChannelService.getById(id);
        return Result.ok(channel);
    }

    /**
     * 根据id主键进行修改频道
     */
    @PutMapping
    public Result updateById(@RequestBody AdChannel adChannel)
    {
        if (adChannel.getId() == null) {
            return Result.errorMessage("必须带有主键值", StatusCode.PARAM_ERROR.code(), null);
        }
        boolean flag = adChannelService.updateById(adChannel);
        if (!flag) {
            return Result.error();
        }
        return Result.ok();
    }

    /**
     * 测试异常
     * @return
     * @throws LeadNewsException
     */
    @GetMapping("/test")
    public String test() throws LeadNewsException
    {
        //int a = 1 / 0 ;
        //业务代码调用
        if (1 == 1) {
            throw new LeadNewsException("抛出自定义异常");
        }
        return "abc";
    }

}
