package com.itheima.article.feign;

import com.itheima.article.pojo.ApAuthor;
import com.itheima.common.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Arthurocky
 */
@FeignClient(name="leadnews-article",path = "/apAuthor")
public interface ApAuthorFeign {
    /**
     * 保存作者账号
     * @param apAuthor
     * @return
     */
    @PostMapping
    public Result save(@RequestBody ApAuthor apAuthor);

    /**
     * 根据APP用户的ID 获取 作者信息
     * @param apUserId
     * @return
     */
    @GetMapping("/one/{apUserId}")
    public ApAuthor getByApUserId(@PathVariable(name="apUserId")Integer apUserId);
}