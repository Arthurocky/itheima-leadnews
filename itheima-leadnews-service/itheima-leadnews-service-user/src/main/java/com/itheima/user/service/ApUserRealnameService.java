package com.itheima.user.service;

import com.itheima.user.pojo.ApUserRealname;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * APP实名认证信息表 服务类
 * </p>
 *
 * @author ljh
 * @since 2021-12-22
 */
public interface ApUserRealnameService extends IService<ApUserRealname> {

    /**
     * 审核通过
     * @param id
     */
    void pass(Integer id);

    /**
     * 审核拒绝
     * @param id
     * @param reason
     */
    void reject(Integer id, String reason);

}
