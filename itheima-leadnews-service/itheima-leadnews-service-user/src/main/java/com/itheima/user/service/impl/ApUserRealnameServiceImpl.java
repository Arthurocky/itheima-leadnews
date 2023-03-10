package com.itheima.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.article.feign.ApAuthorFeign;
import com.itheima.article.pojo.ApAuthor;
import com.itheima.common.constants.BusinessConstants;
import com.itheima.common.pojo.Result;
import com.itheima.media.feign.WmUserFeign;
import com.itheima.media.pojo.WmUser;
import com.itheima.user.mapper.ApUserMapper;
import com.itheima.user.mapper.ApUserRealnameMapper;
import com.itheima.user.pojo.ApUser;
import com.itheima.user.pojo.ApUserRealname;
import com.itheima.user.service.ApUserRealnameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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

    private static final Logger logger = LoggerFactory.getLogger(ApUserRealnameServiceImpl.class);


    @Autowired
    private ApUserRealnameMapper apUserRealnameMapper;

    @Autowired
    private ApUserMapper apUserMapper;

    @Autowired
    private WmUserFeign wmUserFeign;

    @Autowired
    private ApAuthorFeign apAuthorFeign;

    @Override
    public void pass(Integer id)
    {
        //防止apUser为空时的空指针异常
        ApUserRealname entity = new ApUserRealname();
        entity.setUserId(id);

        //审核通过
        entity.setStatus(BusinessConstants.ApUserRealnameConstants.SHENHE_SUCCESS);
        apUserRealnameMapper.updateById(entity);

        //通过远程调用实现创建作者的账号  feign远程调用 自媒体服务 创建自媒体账号
        ApUserRealname apUserRealname = apUserRealnameMapper.selectById(id);
        //如果该自媒体用户不存在
        if (apUserRealname != null) {
            ApUser apUser = apUserMapper.selectById(apUserRealname.getUserId());
            WmUser wmUser = wmUserFeign.getByApUserId(apUser.getId());
            //如果该用户不存在
            if (wmUser == null) {
                //将申请自媒体的信息复制到自媒体账号上
                wmUser = new WmUser();
                BeanUtils.copyProperties(entity, wmUser);
                //设置状态-有效
                wmUser.setStatus(BusinessConstants.WmUserConstants.WM_USER_OK);
                //设置APP用户ID
                wmUser.setApUserId(apUser.getId());
                //设置创建时间
                wmUser.setCreatedTime(LocalDateTime.now());
                Result result = wmUserFeign.save(wmUser);
                //Result<WmUser> result = wmUserFeign.save(wmUser);
                if (result.isSuccess()) {
                    logger.info("自媒体账号创建成功");
                }
            }
            //通过远程调用实现创建作者的账号  feign远程调用 文章微服务 创建作者账号
            ApAuthor apAuthor = apAuthorFeign.getByApUserId(apUser.getId());
            //不存在账号时注册
            if (apAuthor == null) {
                apAuthor = new ApAuthor();
                //作者名称就是登录名
                apAuthor.setName(apUser.getName());
                apAuthor.setType(BusinessConstants.ApAuthorConstants.A_MEDIA_USER);
                apAuthor.setCreatedTime(LocalDateTime.now());
                apAuthor.setUserId(apUser.getId());
                apAuthor.setWmUserId(wmUser.getId());
                apAuthorFeign.save(apAuthor);
            }
        }

    }


    @Override
    public void reject(Integer id, String reason)
    {
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
