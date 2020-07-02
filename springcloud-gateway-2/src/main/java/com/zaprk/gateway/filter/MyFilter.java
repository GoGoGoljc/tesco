package com.zaprk.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Component
public class MyFilter extends ZuulFilter {

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -8;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        //判断条件，判断的url(先写一个先)
        String url = request.getRequestURL().toString();
        if (url.startsWith("http://172.18.84.32:9960/orderitem-service")
                ||
                url.startsWith("http://localhost:9960/orderitem-service")
                ||
                url.startsWith("http://172.18.84.32:9960/order-service")
                ||
                url.startsWith("http://localhost:9960/order-service")) {
            return true;//经过过滤器
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        // AtomicInteger responseCode= new AtomicInteger(400);
        //Boolean flag=false;
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String token = request.getHeader("token");
        String admintoken = request.getHeader("admintoken");
        //判断token在redis里存不存在，如果存在，则放行，如果不存在，则返回一个结果，前台通过这个结果进行重定向到登录。
        System.out.println("传过来的token"+token);
        System.out.println("传过来的admintoken"+admintoken);
        if (redisTemplate.hasKey("userToken:"+token)) {
            redisTemplate.expire("userToken:"+token, 5, TimeUnit.HOURS);
            //控制zuul是否将请求下发到下游(是将请求发往注册中心，或者服务吗)
            context.setSendZuulResponse(true);
            context.setResponseStatusCode(200);
        }else  if(redisTemplate.hasKey("adminToken:"+token)){
            redisTemplate.expire("adminToken:"+token, 5, TimeUnit.HOURS);
            //控制zuul是否将请求下发到下游(是将请求发往注册中心，或者服务吗)
            context.setSendZuulResponse(true);
            context.setResponseStatusCode(200);
        }else{
            System.err.println("1111111111");
            context.setResponseStatusCode(400);
            context.setSendZuulResponse(false);
            context.setResponseBody("no login");
        }
        return null;

    }
}
