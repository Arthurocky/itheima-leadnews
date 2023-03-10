package com.itheima.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.admin.mapper.AdUserMapper;
import com.itheima.admin.pojo.AdUser;
import com.itheima.admin.service.AdUserService;
import com.itheima.admin.vo.LoginAdminVo;
import com.itheima.common.exception.LeadNewsException;
import com.itheima.common.pojo.Result;
import com.itheima.common.utils.AppJwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Arthurocky
 */
@Service
public class AdUserServiceImpl extends ServiceImpl<AdUserMapper, AdUser> implements AdUserService {

    @Autowired
    private AdUserMapper adUserMapper;

    @Override
    public Map<String, Object> login(LoginAdminVo loginAdminVo) throws LeadNewsException
    {
        //1.判断用户有和密码是否为空 为空 则报错提示不能为空
        if (StringUtils.isEmpty(loginAdminVo.getName()) || StringUtils.isEmpty(loginAdminVo.getPassword())) {
            throw new LeadNewsException("用户名和密码不能为空");
        }

        //2.根据用户名 从数据库查询数据 如果没有找到 报错
        QueryWrapper<AdUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", loginAdminVo.getName());
        AdUser adUserFromDb = adUserMapper.selectOne(queryWrapper);
        if (adUserFromDb == null) {
            throw new LeadNewsException("用户名或者密码错误");
        }

        //3.获取页面传递过来的密码 进行md5加密  再和数据库的密文(明文+盐值)进行对比  如果对比失败  报错
        String temp = loginAdminVo.getPassword() + adUserFromDb.getSalt();
        //MD5加密后的内容
        String passwordFromWebMd5 = DigestUtils.md5DigestAsHex(temp.getBytes());
        if (!passwordFromWebMd5.equals(adUserFromDb.getPassword())) {
            throw new LeadNewsException("用户名或者密码错误");
        }

        //4.成功，生成令牌返回给前端
        String token = AppJwtUtil.createToken(Long.valueOf(adUserFromDb.getId()));
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        resultMap.put("headPic", adUserFromDb.getImage());
        resultMap.put("nickName", adUserFromDb.getNickname());
        //info.put("nickName",adUserFromDb.getNickname());
        return resultMap;
    }
}