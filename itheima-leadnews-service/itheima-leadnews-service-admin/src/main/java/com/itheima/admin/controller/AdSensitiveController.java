package com.itheima.admin.controller;

import com.itheima.admin.pojo.AdSensitive;
import com.itheima.admin.service.AdSensitiveService;
import com.itheima.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Arthurocky
 */
@RestController
@RequestMapping("/adSensitive")
@Api(value="敏感词信息表",tags = "AdSensitiveController")
public class AdSensitiveController extends AbstractCoreController<AdSensitive> {

    /*public AdSensitiveController(IService<AdSensitive> coreService)
    {
        super(coreService);
    }*/

    @Autowired
    private final AdSensitiveService adSensitiveService;

    public AdSensitiveController(AdSensitiveService adSensitiveService)
    {
        super(adSensitiveService);
        this.adSensitiveService = adSensitiveService;
    }

}
