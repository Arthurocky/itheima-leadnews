package com.itheima.controller;

import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.itheima.common.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
public class FileController {


    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    private FdfsWebServer fdfsWebServer;


    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result<Map<String, String>> upload(MultipartFile file) throws Exception
    {
        //将图片 上传给fdfs
        StorePath storePath = fastFileStorageClient.uploadFile(
                //图片本身对应的stream流对象 InputStream
                file.getInputStream(),
                //图片的文件的大小 long
                file.getSize(),
                //图片的扩展名 .png   String
                StringUtils.getFilenameExtension(file.getOriginalFilename()),
                //元数据-服务IP地址 大小 时间戳
                null);

        // url 要求： http://192.168.211.136/group1/M00/00/01/wKjTiGQLDmKABHePAABtFsqQiig517.jpg
        // 现在： group1/M00/00/01/wKjTiGQLDmKABHePAABtFsqQiig517.jpg  --->通过在yml中配置web-server-url
        String fullPath = storePath.getFullPath();
        String realUrl = fdfsWebServer.getWebServerUrl() + fullPath;
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("url", realUrl);
        //设置返回图片的路径
        return Result.ok(resultMap);
    }
}
