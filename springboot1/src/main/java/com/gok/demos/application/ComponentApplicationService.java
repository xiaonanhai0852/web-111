package com.gok.demos.application;

import com.gok.demos.domain.entity.ComponentType;
import com.gok.demos.domain.entity.StockInRecord;
import com.gok.demos.domain.entity.StockOutRecord;
import com.gok.demos.domain.repository.ComponentTypeRepository;
import com.gok.demos.domain.repository.StockRecordRepository;
import com.gok.demos.domain.service.ComponentTakingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 元器件应用服务
 * 应用层服务协调领域对象完成用例，处理事务边界
 */
@Service
public class ComponentApplicationService {

    @Autowired
    private ComponentTypeRepository componentTypeRepository;

    @Autowired
    private StockRecordRepository stockRecordRepository;

    @Autowired
    private ComponentTakingService componentTakingService;

    /**
     * 添加元器件入库
     * 普通用户用例：选择已有类型，填写数量、采购日期、采购人
     */
    @Transactional
    public Map<String, Object> addStockIn(Long componentTypeId, Integer quantity, 
                                         LocalDateTime purchaseDate, Long purchaserId, 
                                         String purchaserName, String batchNumber, String remarks) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 验证元器件类型是否存在
            ComponentType componentType = componentTypeRepository.findById(componentTypeId);
            if (componentType == null) {
                result.put("success", false);
                result.put("message", "元器件类型不存在");
                return result;
            }

            // 创建入库记录
            StockInRecord record = new StockInRecord();
            record.setComponentTypeId(componentTypeId);
            record.setQuantity(quantity);
            record.setPurchaseDate(purchaseDate);
            record.setPurchaserId(purchaserId);
            record.setPurchaserName(purchaserName);
            record.setBatchNumber(batchNumber);
            record.setRemarks(remarks);
            record.setCreatedAt(LocalDateTime.now());

            int rows = stockRecordRepository.saveStockIn(record);
            if (rows > 0) {
                result.put("success", true);
                result.put("message", "入库成功");
                result.put("data", record);
            } else {
                result.put("success", false);
                result.put("message", "入库失败");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "入库失败：" + e.getMessage());
        }
        
        return result;
    }

    /**
     * 取用元器件
     * 普通用户用例：选择类型和型号，填写取用数量
     */
    @Transactional
    public Map<String, Object> takeComponent(Long componentTypeId, Integer quantity, 
                                            Long userId, String userName,
                                            String purpose, String projectName, String remarks) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 调用领域服务执行取用逻辑
            StockOutRecord record = componentTakingService.takeComponent(
                componentTypeId, quantity, userId, userName, purpose, projectName, remarks
            );
            
            result.put("success", true);
            result.put("message", "取用成功");
            result.put("data", record);
        } catch (IllegalArgumentException e) {
            result.put("success", false);
            result.put("message", "参数错误：" + e.getMessage());
        } catch (IllegalStateException e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "取用失败：" + e.getMessage());
        }
        
        return result;
    }

    /**
     * 查询元器件类型列表（用于下拉框）
     */
    public Map<String, Object> getComponentTypeList(int page, int size) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            int offset = (page - 1) * size;
            List<ComponentType> list = componentTypeRepository.findByPage(offset, size);
            int total = componentTypeRepository.count();
            
            result.put("success", true);
            result.put("data", list);
            result.put("total", total);
            result.put("page", page);
            result.put("size", size);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }
        
        return result;
    }

    /**
     * 查询元器件的取用历史
     */
    public Map<String, Object> getStockOutHistory(Long componentTypeId) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            List<StockOutRecord> records = stockRecordRepository.findStockOutByComponentTypeId(componentTypeId);
            result.put("success", true);
            result.put("data", records);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }
        
        return result;
    }

    /**
     * 查询当前库存
     */
    public Map<String, Object> getCurrentStock(Long componentTypeId) {
        Map<String, Object> result = new HashMap<>();

        try {
            Integer stock = componentTakingService.getCurrentStock(componentTypeId);
            result.put("success", true);
            result.put("stock", stock != null ? stock : 0);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }

        return result;
    }

    /**
     * 获取所有入库记录
     */
    public Map<String, Object> getAllStockInRecords(int page, int size) {
        Map<String, Object> result = new HashMap<>();

        try {
            int offset = (page - 1) * size;
            List<StockInRecord> records = stockRecordRepository.findAllStockIn(offset, size);
            int total = stockRecordRepository.countStockIn();

            result.put("success", true);
            result.put("data", records);
            result.put("total", total);
            result.put("page", page);
            result.put("size", size);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }

        return result;
    }

    /**
     * 获取所有出库记录
     */
    public Map<String, Object> getAllStockOutRecords(int page, int size) {
        Map<String, Object> result = new HashMap<>();

        try {
            int offset = (page - 1) * size;
            List<StockOutRecord> records = stockRecordRepository.findAllStockOut(offset, size);
            int total = stockRecordRepository.countStockOut();

            result.put("success", true);
            result.put("data", records);
            result.put("total", total);
            result.put("page", page);
            result.put("size", size);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }

        return result;
    }

    /**
     * 删除元器件类型
     */
    @Transactional
    public Map<String, Object> deleteComponentType(Long id) {
        Map<String, Object> result = new HashMap<>();

        try {
            // 检查是否存在关联的入库或出库记录
            List<StockInRecord> stockInRecords = stockRecordRepository.findStockInByComponentTypeId(id);
            List<StockOutRecord> stockOutRecords = stockRecordRepository.findStockOutByComponentTypeId(id);

            if (!stockInRecords.isEmpty() || !stockOutRecords.isEmpty()) {
                result.put("success", false);
                result.put("message", "该元器件类型存在关联的入库或出库记录，无法删除");
                return result;
            }

            int rows = componentTypeRepository.delete(id);
            if (rows > 0) {
                result.put("success", true);
                result.put("message", "删除成功");
            } else {
                result.put("success", false);
                result.put("message", "元器件类型不存在");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "删除失败：" + e.getMessage());
        }

        return result;
    }
}

