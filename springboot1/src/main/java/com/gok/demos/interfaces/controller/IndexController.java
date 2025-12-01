package com.gok.demos.interfaces.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 首页控制器
 * 提供系统欢迎页面和健康检查
 */
@RestController
@RequestMapping("/")
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080", "file://*", "http://localhost:63342"}, 
             allowCredentials = "true")
@Api(tags = "系统信息接口")
public class IndexController {

    /**
     * 系统欢迎页面
     */
    @GetMapping
    @ApiOperation(value = "系统欢迎页面", notes = "返回系统基本信息")
    public Map<String, Object> index() {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("system", "元器件管理系统");
        result.put("version", "v1.0.0");
        result.put("description", "基于DDD架构的智能元器件管理系统");
        result.put("swagger", "http://localhost:8080/swagger-ui/index.html");
        result.put("apis", new String[]{
            "GET /api/component/types - 获取元器件类型列表",
            "POST /api/component/stock-in - 添加入库记录",
            "POST /api/component/stock-out - 取用元器件",
            "GET /api/statistics/inventory - 统计库存"
        });
        return result;
    }

    /**
     * 健康检查
     */
    @GetMapping("/health")
    @ApiOperation(value = "健康检查", notes = "检查系统是否正常运行")
    public Map<String, Object> health() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "UP");
        result.put("message", "系统运行正常");
        result.put("timestamp", System.currentTimeMillis());
        return result;
    }

    /**
     * API信息
     */
    @GetMapping("/api")
    @ApiOperation(value = "API信息", notes = "返回API文档地址")
    public Map<String, Object> apiInfo() {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("swagger", "http://localhost:8080/swagger-ui/index.html");
        result.put("message", "请访问Swagger查看完整的API文档");
        return result;
    }
}

