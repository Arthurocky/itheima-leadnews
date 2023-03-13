package com.itheima.service.impl;

import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.itheima.pojo.BaseFileModel;
import com.itheima.pojo.DFSType;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Arthurocky
 */
@Component
public class FdfsFileTemplate extends AbstractDfsTemplate {

    @Autowired
    private FastFileStorageClient client;

    @Autowired
    private FdfsWebServer fdfsWebServer;

    @Override
    public String uploadFile(BaseFileModel fileModel) {
        HashSet<MetaData> metaData = new HashSet<>();
        //设置md5作为设置图片的签名
        metaData.add(new MetaData("md5", fileModel.getMd5()));
        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(fileModel.getContent());
        StorePath storePath = client.uploadFile(
                byteInputStream,
                fileModel.getSize(),
                StringUtils.getFilenameExtension(fileModel.getName()),
                metaData);
        String webServerUrl = fdfsWebServer.getWebServerUrl();
        return webServerUrl + storePath.getFullPath();
    }

    @Override
    public boolean delete(String fullPath) {
        try {
            client.deleteFile(fullPath);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<byte[]> download(List<String> fullPaths) {

        List<byte[]> picList = fullPaths.stream().map(
                fullpath -> {
                    try {
                        StorePath storePath = StorePath.parseFromUrl(fullpath);
                        byte[] bytes = client.downloadFile(storePath.getGroup(), storePath.getPath(), ins -> IOUtils.toByteArray(ins));
                        return bytes;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }
        ).filter(bytes -> bytes != null).collect(Collectors.toList());
        return picList;
    }

    @Override
    public DFSType support() {
        return DFSType.FASTDFS;
    }
}