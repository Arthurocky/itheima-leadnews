package com.itheima.media.controller;


import com.itheima.common.pojo.PageInfo;
import com.itheima.common.pojo.PageRequestDto;
import com.itheima.common.pojo.Result;
import com.itheima.core.controller.AbstractCoreController;
import com.itheima.media.dto.WmNewsDto;
import com.itheima.media.dto.WmNewsDtoSave;
import com.itheima.media.pojo.WmNews;
import com.itheima.media.service.WmNewsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * <p>
 * 自媒体图文内容信息表 控制器</p>
 *
 * @author Arthurocky
 */
@Api(value = "自媒体图文内容信息表", tags = "WmNewsController")
@RestController
@RequestMapping("/wmNews")
public class WmNewsController extends AbstractCoreController<WmNews> {

    private WmNewsService wmNewsService;

    //注入
    @Autowired
    public WmNewsController(WmNewsService wmNewsService)
    {
        super(wmNewsService);
        this.wmNewsService = wmNewsService;
    }

    /**
     * 分页查询
     *
     * @param pageRequestDto
     * @return
     */
    @PostMapping("/searchPage")
    public Result<PageInfo<WmNews>> findByPageDto(@RequestBody PageRequestDto<WmNewsDto> pageRequestDto)
    {
        PageInfo<WmNews> pageInfo = wmNewsService.findByPageDto(pageRequestDto);
        return Result.ok(pageInfo);
    }

    /**
     * 保存自媒体文章 </br>
     * 保存草稿 和 添加 或者修改
     */
    @PostMapping("/save/{isSubmit}")
    public Result<WmNews> save(@PathVariable(name = "isSubmit") Integer isSubmit, @RequestBody WmNewsDtoSave wmNewsDtoSave)
    {
        //内容为空或未创建文章时
        if (StringUtils.isEmpty(isSubmit) || wmNewsDtoSave == null) {
            return Result.errorMessage("数据不能为空");
        }
        //isSubitm -- 1标识为提交 0标识为保存草稿
        if (isSubmit > 1 || isSubmit < 0) {
            return Result.errorMessage("isSubmit的值有误");
        }
        WmNews wmNews = wmNewsService.save(wmNewsDtoSave, isSubmit);
        return Result.ok(wmNews);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/one/{id}")
    public Result<WmNewsDtoSave> getById(@PathVariable(name = "id") Integer id)
    {
        WmNewsDtoSave wmNewsDtoSave = wmNewsService.getDtoById(id);
        return Result.ok(wmNewsDtoSave);
    }

    /**
     * 删除
     */
    @Override
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable(name = "id") Serializable id)
    {
        WmNews wmNews = wmNewsService.getById(id);
        if (wmNews == null) {
            return Result.errorMessage("不存在的文章");
        }
        Integer enable = wmNews.getEnable();
        Integer status = wmNews.getStatus();
        //已发布 且上架
        if (status == 9 && enable == 1) {
            return Result.errorMessage("已发布 且上架 不能删除");
        }
        wmNewsService.removeById(id);
        return Result.ok();
    }

    /**
     * 自媒体文章-上架、下架
     */
    @PutMapping("/upOrDown/{id}/{enable}")
    public Result updateUpDown(@PathVariable(name = "id") Serializable id, @PathVariable(name = "enable") Integer enable)
    {
        WmNews wmNews = wmNewsService.getById(id);
        if (wmNews == null) {
            return Result.errorMessage("不存在的文章");
        }
        //未发布发布或未上架
        if (wmNews.getStatus() != 9) {
            return Result.errorMessage("文章没发布，不能上下架");
        }
        if(enable>1 || enable<0){
            return Result.errorMessage("错误的数字范围 只能是0,1");
        }
        wmNews.setEnable(enable);
        wmNewsService.updateById(wmNews);
        return Result.ok();
    }

}

