package com.itheima.dfs.service;

import com.itheima.dfs.pojo.BaseFileModel;

import java.util.List;

/**
 * @author Arthurocky
 */
public interface IFileService {

    /**
     * 上传文件
     *
     * @param fileModel
     * @return 文件的路径信息
     */
    public String uploadFile(BaseFileModel fileModel);

    /**
     * 删除文件
     *
     * @param fullPath
     * @return 是否成功
     */
    public boolean delete(String fullPath);

    /**
     * 批量下载
     * @param fullPath
     * @return
     */
    List<byte[]> download(List<String> fullPath);


}