package com.zaprk.gateway.filter;


import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.aspectj.weaver.patterns.IToken;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AccessFilter extends ZuulFilter {
    //谷歌的限流工具  每秒只允许过10000个请求
    //令牌机制
    private final RateLimiter rateLimiter= RateLimiter.create(10000);


  //  Filter 的类型：Filter 的类型决定了此 Filter 在 Filter 链中的执行顺序。
  //  可能是路由动作发生前，可能是路由动作发生时，可能是路由动作发生后，也可能是路由过程发生异常时。

    //前置执行
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }
/*
    Filter 的执行顺序：同一种类型的 Filter 可以通过 flterOrder() 方法来设定执行顺序。一般会根据业务的执行顺序需求，来设定自定义 Filter 的执行顺序。
    Filter 的执行条件：Filter 运行所需要的标准或条件。
    Filter 的执行效果：符合某个 Filter 执行条件，产生的执行效果

*/
    @Override
    public int filterOrder() {
        //zuul过滤器默认最早的是-3 现在是-4 更早 最先执行
        return -5;
    }

    @Override
    public boolean shouldFilter() {//后面写可以写是否登录
        //return false 不经过过滤器
        //return ture; //是所有请求都经过过滤吧
        //应该设置一个过滤要求
      return true;
      //  return false;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
    /*    String token = request.getHeader("token");
        //登录授权的过滤验证
        if(token==null || "".equals(token)){
           context.setSendZuulResponse(false);
           context.setResponseBody("你还没有登录，请登录");
           context.setResponseStatusCode(400);
        }
*/
//        rateLimiter.acquire();//阻塞式的令牌获取
//        rateLimiter.tryAcquire();//非阻塞式的令牌获取
        if (!rateLimiter.tryAcquire()){
            context.setSendZuulResponse(false);
            context.setResponseBody("be limited");
            context.setResponseStatusCode(400);
        }

        return null;
    }
}
