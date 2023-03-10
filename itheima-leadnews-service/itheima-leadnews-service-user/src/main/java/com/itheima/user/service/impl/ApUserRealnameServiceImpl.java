package com.itheima.user.service.impl;

import com.itheima.common.constants.BusinessConstants;
import com.itheima.common.constants.SystemConstants;
import com.itheima.user.mapper.ApUserMapper;
import com.itheima.user.pojo.ApUser;
import com.itheima.user.pojo.ApUserRealname;
import com.itheima.user.mapper.ApUserRealnameMapper;
import com.itheima.user.service.ApUserRealnameService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * APP实名认证信息表 服务实现类
 * </p>
 *
 * @author ljh
 * @since 2021-12-22
 */
@Service
public class ApUserRealnameServiceImpl extends ServiceImpl<ApUserRealnameMapper, ApUserRealname> implements ApUserRealnameService {

    @Autowired
    private ApUserRealnameMapper apUserRealnameMapper;

    @Override
    public void pass(Integer id)
    {
        ApUserRealname apUser = new ApUserRealname();
        apUser.setUserId(id);
        apUser.setStatus(BusinessConstants.ApUserRealnameConstants.SHENHE_SUCCESS);
        apUserRealnameMapper.updateById(apUser);

        //通过远程调用实现创建作者的账号  todo

        //通过远程调用实现创建作者的账号  todo


    }


    @Override
    public void reject(Integer id, String reason) {
        ApUserRealname apUserRealname = new ApUserRealname();
        apUserRealname.setId(id);
        apUserRealname.setReason(reason);
        //更新时间
        apUserRealname.setUpdatedTime(LocalDateTime.now());
        //设置状态为审核失败
        apUserRealname.setStatus(BusinessConstants.ApUserRealnameConstants.SHENHE_FALSE);
        apUserRealnameMapper.updateById(apUserRealname);
    }
}
