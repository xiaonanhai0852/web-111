package com.gok.demos.interfaces.controller;

import com.gok.demos.application.StatisticsApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 统计控制器
 * 提供各种统计查询接口
 */
@RestController
@RequestMapping("/api/statistics")
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080", "file://*", "http://localhost:63342"}, 
             allowCredentials = "true")
@Api(tags = "统计查询接口")
public class StatisticsController {

    @Autowired
    private StatisticsApplicationService statisticsApplicationService;

    /**
     * 按元器件类型统计库存总量
     */
    @GetMapping("/inventory")
    @ApiOperation(value = "统计库存", notes = "按元器件类型统计库存总量，返回每种元器件的当前库存")
    public Map<String, Object> getInventoryStatistics() {
        return statisticsApplicationService.getInventoryStatistics();
    }

    /**
     * 按具体型号查询当前库存数量
     */
    @GetMapping("/stock-by-model")
    @ApiOperation(value = "按型号查询库存", notes = "根据元器件型号查询当前库存数量")
    public Map<String, Object> getStockByModel(
            @ApiParam(value = "元器件型号", required = true, example = "10KΩ")
            @RequestParam String model) {
        return statisticsApplicationService.getStockByModel(model);
    }

    /**
     * 获取库存预警列表
     */
    @GetMapping("/low-stock-alert")
    @ApiOperation(value = "库存预警", notes = "返回库存低于阈值的元器件列表")
    public Map<String, Object> getLowStockAlert(
            @ApiParam(value = "库存阈值", example = "10")
            @RequestParam(defaultValue = "10") Integer threshold) {
        return statisticsApplicationService.getLowStockAlert(threshold);
    }
}

