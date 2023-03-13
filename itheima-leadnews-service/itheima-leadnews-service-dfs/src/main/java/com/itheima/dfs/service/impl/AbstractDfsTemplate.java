package com.itheima.dfs.service.impl;

import com.itheima.dfs.pojo.DFSType;
import com.itheima.dfs.service.IFileService;
import org.springframework.beans.factory.InitializingBean;

public abstract class AbstractDfsTemplate implements IFileService, InitializingBean {


    /**
     * 定义支持的类型 必须设置值
     *
     * @return
     */
    public abstract DFSType support();

    @Override
    public void afterPropertiesSet() throws Exception {
        DFSType[] values = DFSType.values();

        boolean flag = false;
        for (DFSType value : values) {
            DFSType support = support();
            if(support==value){
                flag=true;
                break;
            }
        }
        if(!flag){
            throw new java.lang.RuntimeException("不支持的dfs类型");
        }
    }
}