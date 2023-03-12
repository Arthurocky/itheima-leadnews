package com.itheima.media.controller;

import com.itheima.common.pojo.Result;
import com.itheima.core.controller.AbstractCoreController;
import com.itheima.media.pojo.WmMaterial;
import com.itheima.media.service.WmMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


/**
 * @author Arthurocky
 */
@RestController
@RequestMapping("/wmMaterial")
public class WmMaterialController extends AbstractCoreController<WmMaterial> {

    private WmMaterialService wmMaterialService;

    //注入
    @Autowired
    public WmMaterialController(WmMaterialService wmMaterialService) {
        super(wmMaterialService);
        this.wmMaterialService=wmMaterialService;
    }

    /**
     * 重写父类方法 实现添加素材
     */
    @PostMapping
    @Override
    public Result insert(@RequestBody WmMaterial record) {
        //1.设置补充属性
        //todo 先硬编码 设置为该素材所属的自媒体账号ID
        record.setUserId(1000);
        //未收藏
        record.setIsCollection(0);
        //图片
        record.setType(0);
        //创建时间
        record.setCreatedTime(LocalDateTime.now());
        //2.保存到数据库中
        wmMaterialService.save(record);
        return Result.ok(record);
    }
}
