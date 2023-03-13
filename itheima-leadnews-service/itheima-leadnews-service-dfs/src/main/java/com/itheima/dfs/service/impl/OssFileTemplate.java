package com.itheima.dfs.service.impl;
import com.itheima.dfs.pojo.BaseFileModel;
import com.itheima.dfs.pojo.DFSType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Arthurocky
 */
@Component
public class OssFileTemplate extends AbstractDfsTemplate {
    //oss的逻辑
    @Override
    public String uploadFile(BaseFileModel fileModel) {


        //5.do

        return null;
    }


    @Override
    public boolean delete(String fullPath) {

        //5.do

        return false;
    }

    @Override
    public List<byte[]> download(List<String> fullPath) {


        //5.do
        return null;
    }

    @Override
    public DFSType support() {
        return DFSType.OSSDFS;
    }
}