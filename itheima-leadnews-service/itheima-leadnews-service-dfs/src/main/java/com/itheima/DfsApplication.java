package com.itheima;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;


/**
 * @author Arthurocky
 */
@SpringBootApplication
@EnableDiscoveryClient
//可以省略，DFS中已经配置了
@Import(FdfsClientConfig.class)
public class DfsApplication {
    public static void main(String[] args) {
        SpringApplication.run(DfsApplication.class, args);
    }
}
