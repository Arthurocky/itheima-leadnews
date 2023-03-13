package com.itheima.media.controller;


import com.itheima.common.pojo.PageInfo;
import com.itheima.common.pojo.PageRequestDto;
import com.itheima.common.pojo.Result;
import com.itheima.media.dto.WmNewsDto;
import com.itheima.media.dto.WmNewsDtoSave;
import com.itheima.media.pojo.WmNews;
import com.itheima.media.service.WmNewsService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
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

    /**
     * 保存自媒体文章 </br>
     * 保存草稿 和 添加 或者修改
     */
    @PostMapping("/save/{isSubmit}")
    public Result<WmNews> save(@PathVariable(name="isSubmit") Integer isSubmit, @RequestBody WmNewsDtoSave wmNewsDtoSave){
        //内容为空或未创建文章时
        if(StringUtils.isEmpty(isSubmit) || wmNewsDtoSave==null){
            return Result.errorMessage("数据不能为空");
        }
        //isSubitm -- 1标识为提交 0标识为保存草稿
        if(isSubmit>1 || isSubmit<0){
            return Result.errorMessage("isSubmit的值有误");
        }
        WmNews wmNews =  wmNewsService.save(wmNewsDtoSave,isSubmit);
        return Result.ok(wmNews);
    }

}

