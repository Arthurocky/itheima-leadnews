package com.itheima.dfs.strategy;

import com.itheima.dfs.pojo.DFSType;
import com.itheima.dfs.service.IFileService;
import com.itheima.dfs.service.impl.AbstractDfsTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

/**
 * ApplicationContextAware  实现了这个接口 一定是当spring管理这个bean的时候 就会自动的调用setApplicationContext方法
 */
@Component
public class FileServiceStrategyContext implements ApplicationContextAware {

    /**
     * key  就是某一个类型
     * value 就是接口对应的具体子类对象
     */
    private static final Map<DFSType, IFileService> dfsServices = new EnumMap<DFSType, IFileService>(DFSType.class);

    /**
     *
     * @param applicationContext  就是容器本身 spring容器
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        //向map里面put值

        //抽闲类的所有的子类对象 从容器中获取所有的子类对象 key: beanName value bean对象
        Map<String, AbstractDfsTemplate> types = applicationContext.getBeansOfType(AbstractDfsTemplate.class);

        //循环抽象类的子类
        for (AbstractDfsTemplate value : types.values()) {
            DFSType support = value.support();
            //key  枚举 子类的返回值  value 子类本身的bean对象
            dfsServices.put(support, value);
        }
    }

    /**
     * 提供方法获取具体实现类
     * @param dfsType
     * @return
     */
    public IFileService getIFleService(DFSType dfsType) {
        return dfsServices.get(dfsType);
    }

}