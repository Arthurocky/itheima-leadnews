package com.itheima.media.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itheima.common.exception.LeadNewsException;
import com.itheima.common.pojo.Result;
import com.itheima.common.utils.RequestContextUtil;
import com.itheima.core.controller.AbstractCoreController;
import com.itheima.media.pojo.WmMaterial;
import com.itheima.media.service.WmMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
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
    public WmMaterialController(WmMaterialService wmMaterialService)
    {
        super(wmMaterialService);
        this.wmMaterialService = wmMaterialService;
    }

    /**
     * 重写父类方法 实现添加素材
     */
    @PostMapping
    @Override
    public Result insert(@RequestBody WmMaterial record)
    {
        //1.设置补充属性
        String userInfo = RequestContextUtil.getUserInfo();
        int id = Integer.parseInt(userInfo);
        record.setUserId(id);
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

    /**
     * 重写父类方法 实现修改素材
     *
     * @param record
     * @return
     */
    @PutMapping
    @Override
    public Result updateByPrimaryKey(@RequestBody WmMaterial record)
    {
        //1.设置补充属性
        //update 表 set url = ? where id = ? And userid = 当前用户id
        String userInfo = RequestContextUtil.getUserInfo();
        int id = Integer.parseInt(userInfo);
        QueryWrapper wrapper = new QueryWrapper<WmMaterial>();
        wrapper.eq("userId", userInfo);
        WmMaterial wmMaterial = wmMaterialService.getOne(wrapper);
        if (wmMaterial == null) {
            try {
                throw new LeadNewsException("该素材不存在");
            } catch (LeadNewsException e) {
                //
            }
        }

        wmMaterial.setUrl(record.getUrl());
        wmMaterial.setUserId(id);
        //未收藏
        wmMaterial.setIsCollection(0);
        //图片
        wmMaterial.setType(0);
        //创建时间
        wmMaterial.setCreatedTime(LocalDateTime.now());

        //2.保存到数据库中
        wmMaterialService.save(wmMaterial);
        return Result.ok(wmMaterial);
    }

    /**
     * 重写父类方法 实现删除素材
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @Override
    public Result deleteById(@PathVariable(name = "id") Serializable id)
    {
        //1.设置补充属性
        //update 表 set url = ? where id = ? And userid = 当前用户id
        String userInfo = RequestContextUtil.getUserInfo();
        int userId = Integer.parseInt(userInfo);
        QueryWrapper wrapper = new QueryWrapper<WmMaterial>();
        wrapper.eq("userId", userId);
        WmMaterial wmMaterial = wmMaterialService.getOne(wrapper);
        if (wmMaterial == null) {
            try {
                throw new LeadNewsException("该素材不存在");
            } catch (LeadNewsException e) {
                //
            }
        }

        wmMaterialService.removeById(id);
        return Result.ok();
    }

    /**
     * 设置收藏
     */
    @PostMapping("/{id}")
    public Result setType(@PathVariable(name = "id") int type){
        //1.设置补充属性
        //update 表 set url = ? where id = ? And userid = 当前用户id
        String userInfo = RequestContextUtil.getUserInfo();
        int userId = Integer.parseInt(userInfo);
        QueryWrapper wrapper = new QueryWrapper<WmMaterial>();
        wrapper.eq("userId", userId);
        WmMaterial wmMaterial = wmMaterialService.getOne(wrapper);
        if (wmMaterial == null) {
            try {
                throw new LeadNewsException("该素材不存在");
            } catch (LeadNewsException e) {
                //
            }
        }
        wmMaterial.setType(type);
        return Result.ok();
    }

}
