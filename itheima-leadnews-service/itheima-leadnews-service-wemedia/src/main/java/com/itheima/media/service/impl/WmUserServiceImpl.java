package com.itheima.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.common.exception.LeadNewsException;
import com.itheima.common.utils.AppJwtUtil;
import com.itheima.media.mapper.WmUserMapper;
import com.itheima.media.pojo.WmUser;
import com.itheima.media.service.WmUserService;
import com.itheima.media.vo.LoginMediaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 自媒体用户信息表 服务实现类
 * </p>
 *
 * @author ljh
 * @since 2021-12-22
 */
@Service
public class WmUserServiceImpl extends ServiceImpl<WmUserMapper, WmUser> implements WmUserService {

    @Autowired
    private WmUserMapper wmUserMapper;

    @Override
    public Map<String, Object> login(LoginMediaVo loginMediaVo) throws LeadNewsException
    {
        //用户名
        String name = loginMediaVo.getName();

        //密码
        String password = loginMediaVo.getPassword();

        //1.登录界面用户名或密码为空时
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
            throw new LeadNewsException("用户名和密码不能为空");
        }

        //2.根据用户名 从数据库查询数据 如果没有找到 报错
/*        QueryWrapper<WmUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        WmUser wmUser = wmUserMapper.selectOne(queryWrapper);*/
        LambdaQueryWrapper<WmUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WmUser::getName,name);
        WmUser wmUser = this.getOne(wrapper);
        if (wmUser == null) {
            throw new LeadNewsException("用户名或者密码错误");
        }

        //3.获取页面传递过来的密码 进行md5加密   再和数据库的密文进行对比  如果对比失败  报错
        String temp = loginMediaVo.getPassword() + wmUser.getSalt();
        //进行md5加密
        String passwordFromWebMd5 = DigestUtils.md5DigestAsHex(temp.getBytes());
        if (passwordFromWebMd5.equals(wmUser.getPassword())) {
            throw new LeadNewsException("用户名或者密码错误");
        }

        //4. 密码正确，生成令牌返回给前端
        String token = AppJwtUtil.createToken(Long.valueOf(wmUser.getId()));
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("headPic", wmUser.getImage());
        map.put("nickName", wmUser.getNickname());
        //info.put("nickName",wmUser.getNickname());
        return map;
    }
}
