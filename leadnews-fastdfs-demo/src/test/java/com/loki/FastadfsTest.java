package com.loki;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadCallback;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import java.io.*;



@SpringBootTest
public class FastadfsTest {

    @Autowired
    private FastFileStorageClient storageClient;

    //上传图片
    @Test
    public void uploadFile() throws IOException {

        //创建流对象
        File file = new File("D:\\HeiMa\\images\\469670fb-0654-424f-86fc-7bbb414ef9a6.jpg");
        FileInputStream inputStream = new FileInputStream(file);
        long length = file.length();
        //获取文件的扩展名不带点
        String extName = StringUtils.getFilenameExtension(file.getName());
        //上传图片
        StorePath storePath = storageClient.uploadFile(
                inputStream,
                length,
                extName,
                null);

        System.out.println(storePath);
        System.out.println(storePath.getFullPath());
    }

    //删除图片
    //
    @Test
    public void deleteFile() {
        //group + path
        storageClient.deleteFile("group1/M00/00/00/wKjTiF_BIUqAMwDrAAAl8vdCW2Y127.png");
    }

    //下载图片
    @Test
    public void download() throws Exception{
        byte[] group1s = storageClient.downloadFile("group1", "M00/00/00/wKjTiF_BIrCAAn9IAAAl8vdCW2Y205.png", new DownloadCallback<byte[]>() {
            @Override
            public byte[] recv(InputStream ins) throws IOException {

                //获取字节数组
                byte[] bytes = IOUtils.toByteArray(ins);
                return bytes;
            }
        });

        //下载
        FileOutputStream fileOutputStream = new FileOutputStream(new File("D:\\HeiMa\\images\\test\\abc.png"));
        fileOutputStream.write(group1s);
        fileOutputStream.close();
    }


}
