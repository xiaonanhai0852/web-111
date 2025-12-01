package com.gok.demos.application;

import com.gok.demos.domain.entity.ComponentType;
import com.gok.demos.domain.repository.ComponentTypeRepository;
import com.gok.demos.domain.repository.StockRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计应用服务
 * 提供各种统计查询功能
 */
@Service
public class StatisticsApplicationService {

    @Autowired
    private ComponentTypeRepository componentTypeRepository;

    @Autowired
    private StockRecordRepository stockRecordRepository;

    /**
     * 按元器件类型统计库存总量
     * 返回每种元器件类型的当前库存
     */
    public Map<String, Object> getInventoryStatistics() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            List<ComponentType> componentTypes = componentTypeRepository.findAll();
            List<Map<String, Object>> statistics = new ArrayList<>();
            
            for (ComponentType type : componentTypes) {
                Integer stock = stockRecordRepository.calculateCurrentStock(type.getId());
                
                Map<String, Object> item = new HashMap<>();
                item.put("id", type.getId());
                item.put("name", type.getName());
                item.put("model", type.getModel());
                item.put("stock", stock != null ? stock : 0);
                item.put("unitPrice", type.getUnitPrice());
                
                statistics.add(item);
            }
            
            result.put("success", true);
            result.put("data", statistics);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "统计失败：" + e.getMessage());
        }
        
        return result;
    }

    /**
     * 按具体型号查询当前库存数量
     */
    public Map<String, Object> getStockByModel(String model) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            ComponentType componentType = componentTypeRepository.findByModel(model);
            if (componentType == null) {
                result.put("success", false);
                result.put("message", "型号不存在");
                return result;
            }
            
            Integer stock = stockRecordRepository.calculateCurrentStock(componentType.getId());
            
            result.put("success", true);
            result.put("componentType", componentType);
            result.put("stock", stock != null ? stock : 0);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }
        
        return result;
    }

    /**
     * 获取库存预警列表
     * 返回库存低于阈值的元器件
     */
    public Map<String, Object> getLowStockAlert(Integer threshold) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            if (threshold == null || threshold < 0) {
                threshold = 10; // 默认阈值
            }
            
            List<ComponentType> componentTypes = componentTypeRepository.findAll();
            List<Map<String, Object>> alerts = new ArrayList<>();
            
            for (ComponentType type : componentTypes) {
                Integer stock = stockRecordRepository.calculateCurrentStock(type.getId());
                int currentStock = stock != null ? stock : 0;
                
                if (currentStock < threshold) {
                    Map<String, Object> item = new HashMap<>();
                    item.put("id", type.getId());
                    item.put("name", type.getName());
                    item.put("model", type.getModel());
                    item.put("stock", currentStock);
                    item.put("threshold", threshold);
                    
                    alerts.add(item);
                }
            }
            
            result.put("success", true);
            result.put("data", alerts);
            result.put("count", alerts.size());
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }
        
        return result;
    }
}

