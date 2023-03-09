package com.itheima.media.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itheima.core.controller.AbstractCoreController;
import com.itheima.media.pojo.WmUser;
import com.itheima.media.service.WmUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 自媒体用户信息表 控制器</p>
* @author ljh
* @since 2021-12-22
*/
@Api(value="自媒体用户信息表",tags = "WmUserController")
@RestController
@RequestMapping("/wmUser")
public class WmUserController extends AbstractCoreController<WmUser> {

    private WmUserService wmUserService;

    //注入
    @Autowired
    public WmUserController(WmUserService wmUserService) {
        super(wmUserService);
        this.wmUserService=wmUserService;
    }

   /* @PostMapping("/save")
    public Result<WmUser> save(@RequestBody WmUser wmUser){
        wmUserService.save(wmUser);
    }*/

    @GetMapping("/one/{apUserId}")
    public WmUser getByApUserId(@PathVariable(name="apUserId") Integer apUserId){
        //select * from biao where ap_user_id=?
        QueryWrapper<WmUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ap_user_id",apUserId);
        return  wmUserService.getOne(queryWrapper);
    }

}

