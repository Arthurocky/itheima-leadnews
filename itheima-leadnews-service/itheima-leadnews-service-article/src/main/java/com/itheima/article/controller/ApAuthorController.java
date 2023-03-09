package com.itheima.article.controller;


import com.itheima.article.pojo.ApAuthor;
import com.itheima.article.service.ApAuthorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.itheima.core.controller.AbstractCoreController;

/**
* <p>
* APP文章作者信息表 控制器</p>
* @author ljh
* @since 2021-12-22
*/
@Api(value="APP文章作者信息表",tags = "ApAuthorController")
@RestController
@RequestMapping("/apAuthor")
public class ApAuthorController extends AbstractCoreController<ApAuthor> {

    private ApAuthorService apAuthorService;

    //注入
    @Autowired
    public ApAuthorController(ApAuthorService apAuthorService) {
        super(apAuthorService);
        this.apAuthorService=apAuthorService;
    }

}

