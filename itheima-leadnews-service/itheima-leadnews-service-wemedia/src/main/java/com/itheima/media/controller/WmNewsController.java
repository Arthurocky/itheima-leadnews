package com.itheima.media.controller;


import com.itheima.common.pojo.PageInfo;
import com.itheima.common.pojo.PageRequestDto;
import com.itheima.common.pojo.Result;
import com.itheima.media.dto.WmNewsDto;
import com.itheima.media.pojo.WmNews;
import com.itheima.media.service.WmNewsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.itheima.core.controller.AbstractCoreController;

/**
* <p>
* 自媒体图文内容信息表 控制器</p>
* @author Arthurocky
*/
@Api(value="自媒体图文内容信息表",tags = "WmNewsController")
@RestController
@RequestMapping("/wmNews")
public class WmNewsController extends AbstractCoreController<WmNews> {

    private WmNewsService wmNewsService;

    //注入
    @Autowired
    public WmNewsController(WmNewsService wmNewsService) {
        super(wmNewsService);
        this.wmNewsService=wmNewsService;
    }

    /**
     * 分页查询
     * @param pageRequestDto
     * @return
     */
    @PostMapping("/searchPage")
    public Result<PageInfo<WmNews>> findByPageDto(@RequestBody PageRequestDto<WmNewsDto> pageRequestDto){
        PageInfo<WmNews> pageInfo = wmNewsService.findByPageDto(pageRequestDto);
        return Result.ok(pageInfo);
    }

}

