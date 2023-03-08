package com.itheima.admin.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.admin.pojo.AdUser;
import com.itheima.admin.vo.LoginAdminVo;
import com.itheima.common.exception.LeadNewsException;
import java.util.Map;

/**
 * @author Arthurocky
 */
public interface AdUserService extends IService<AdUser> {

    Map<String, Object> login(LoginAdminVo loginAdminVo) throws LeadNewsException;
}
