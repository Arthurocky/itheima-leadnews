package com.itheima.controller;

import com.itheima.common.pojo.Result;
import com.itheima.common.utils.RequestContextUtil;
import com.itheima.pojo.BaseFileModel;
import com.itheima.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Arthurocky
 */
@RestController
@RequestMapping("/dfs")
public class BaseController {

    @Autowired
    //根据bean名字获取,指定使用fdfsFile
    @Qualifier(value = "fdfsFileTemplate")
    private IFileService iFileService;


    @PostMapping("/upload")
    public Result<Map<String, String>> upload(MultipartFile file) throws Exception
    {

        Map<String, String> map = new HashMap<>();
        if (!file.isEmpty()) {
            BaseFileModel fileModel = new BaseFileModel();
            //登录的用户的ID对应的用户名 todo
            String userInfo = RequestContextUtil.getUserInfo();
            //file.getName();上传图片的原名
            fileModel.setAuthor(userInfo);
            fileModel.setSize(file.getSize());
            fileModel.setName(file.getOriginalFilename());
            fileModel.setContent(file.getBytes());
            String path = iFileService.uploadFile(fileModel);
            //设置返回图片的路径
            map.put("url", path);
            return Result.ok(map);
        }
        return Result.errorMessage("错误");
    }
}
