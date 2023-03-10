package com.itheima.media.feign;

import com.itheima.common.pojo.Result;
import com.itheima.feign.CoreFeign;
import com.itheima.media.pojo.WmUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Arthurocky
 */
@FeignClient(name="leadnews-wemedia",path = "/wmUser")
public interface WmUserFeign extends CoreFeign<WmUser> {
    /**
     * 创建自媒体账户信息
     * @param wmUser
     * @return
     */
/*    @PostMapping
    public Result save(@RequestBody WmUser wmUser);*/

    /**
     * 根据apUserId获取
     * @param apUserId
     * @return
     */
    @GetMapping("/one/{apUserId}")
    public WmUser getByApUserId(@PathVariable(name="apUserId") Integer apUserId);
}