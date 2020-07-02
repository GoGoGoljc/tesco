package com.zkdj.admins.client;

import com.zkdj.admins.fallback.AdminServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author SJXY
 */
@FeignClient(value = "admin-service",fallback = AdminServiceFallback.class)
public class AdminClient {
}
