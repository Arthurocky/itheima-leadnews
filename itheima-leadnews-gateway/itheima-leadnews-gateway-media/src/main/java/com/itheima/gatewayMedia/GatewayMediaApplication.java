package com.itheima.gatewayMedia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * @author Arthurocky
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayMediaApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayMediaApplication.class,args);
    }
}
