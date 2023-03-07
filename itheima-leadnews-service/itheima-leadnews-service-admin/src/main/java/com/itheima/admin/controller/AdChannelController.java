package com.itheima.admin.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.admin.pojo.AdChannel;
import com.itheima.admin.service.AdChannelService;
import com.itheima.common.exception.LeadNewsException;
import com.itheima.common.pojo.PageInfo;
import com.itheima.common.pojo.PageRequestDto;
import com.itheima.common.pojo.Result;
import com.itheima.common.pojo.StatusCode;
import com.itheima.core.controller.AbstractCoreController;
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
public class AdChannelController extends AbstractCoreController<AdChannel> {

    //@Autowird 默认已设计自动注入
    public AdChannelController(AdChannelService adChannelService)
    {
        super(adChannelService);
    }

/*    public AdChannelController(IService<AdChannel> coreService)
    {
        super(coreService);
    }*/


}
