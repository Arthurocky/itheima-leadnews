package com.itheima.gatewayadmin.filter;

import com.itheima.common.constants.SystemConstants;
import com.itheima.common.utils.AppJwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 编写全局过滤
 * @author Arthurocky
 */
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.获取请求对象(request)和响应对象(response)
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        //2.判断当前的请求的路径 如果当前请求的路径是去登陆 放行
        String path = request.getURI().getPath();
        if (path.endsWith("/admin/admin/login")) {
            return chain.filter(exchange);
        }

        //3.从页面传递过来的请求头中的数据头中获取令牌 判断是否为空 如果为空  报错（要去登录） 头名叫什么你自己决定
        //获取第一个token
        String token = request.getHeaders().getFirst("token");
        if (StringUtils.isEmpty(token)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        //4.校验令牌  如果校验失败 报错（要去登录）
        Integer code = AppJwtUtil.verifyToken(token);
        //???? == 对比地址值的
        if (!code.equals(SystemConstants.JWT_OK)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        //5.放行
        return chain.filter(exchange);
    }

    /**
     *  定义过滤器的执行的优先级 值越低 优先级越高
     */
    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
