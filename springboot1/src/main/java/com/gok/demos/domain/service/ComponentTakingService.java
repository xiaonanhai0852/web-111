package com.gok.demos.domain.service;

import com.gok.demos.domain.entity.ComponentType;
import com.gok.demos.domain.entity.StockOutRecord;
import com.gok.demos.domain.repository.ComponentTypeRepository;
import com.gok.demos.domain.repository.StockRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 元器件取用领域服务
 * 这是核心领域服务，协调元器件类型、库存检查和取用记录的创建
 */
@Service
public class ComponentTakingService {

    @Autowired
    private ComponentTypeRepository componentTypeRepository;

    @Autowired
    private StockRecordRepository stockRecordRepository;

    /**
     * 取用元器件
     * 这是核心领域逻辑，需要：
     * 1. 检查元器件类型是否存在
     * 2. 检查库存是否充足
     * 3. 创建取用记录
     * 
     * @param componentTypeId 元器件类型ID
     * @param quantity 取用数量
     * @param userId 取用人ID
     * @param userName 取用人姓名
     * @param purpose 取用目的
     * @param projectName 项目名称
     * @param remarks 备注
     * @return 取用记录
     * @throws IllegalArgumentException 如果参数无效
     * @throws IllegalStateException 如果库存不足
     */
    @Transactional
    public StockOutRecord takeComponent(Long componentTypeId, Integer quantity, 
                                       Long userId, String userName,
                                       String purpose, String projectName, String remarks) {
        // 1. 验证参数
        if (componentTypeId == null || quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("元器件类型ID和数量不能为空，且数量必须大于0");
        }
        if (userId == null || userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("取用人信息不能为空");
        }

        // 2. 获取元器件类型（聚合根）
        ComponentType componentType = componentTypeRepository.findById(componentTypeId);
        if (componentType == null) {
            throw new IllegalArgumentException("元器件类型不存在：" + componentTypeId);
        }

        // 3. 检查库存是否充足
        Integer currentStock = stockRecordRepository.calculateCurrentStock(componentTypeId);
        if (!componentType.checkInventorySufficiency(quantity, currentStock)) {
            throw new IllegalStateException(
                String.format("库存不足！当前库存：%d，需要：%d", 
                    currentStock != null ? currentStock : 0, quantity)
            );
        }

        // 4. 创建取用记录
        StockOutRecord record = new StockOutRecord();
        record.setComponentTypeId(componentTypeId);
        record.setQuantity(quantity);
        record.setUserId(userId);
        record.setUserName(userName);
        record.setPurpose(purpose);
        record.setProjectName(projectName);
        record.setRemarks(remarks);
        record.setCreatedAt(LocalDateTime.now());

        // 5. 保存取用记录
        int rows = stockRecordRepository.saveStockOut(record);
        if (rows <= 0) {
            throw new IllegalStateException("保存取用记录失败");
        }

        return record;
    }

    /**
     * 检查库存是否充足（查询方法，不修改状态）
     */
    public boolean checkStockSufficiency(Long componentTypeId, Integer quantity) {
        ComponentType componentType = componentTypeRepository.findById(componentTypeId);
        if (componentType == null) {
            return false;
        }
        Integer currentStock = stockRecordRepository.calculateCurrentStock(componentTypeId);
        return componentType.checkInventorySufficiency(quantity, currentStock);
    }

    /**
     * 获取当前库存
     */
    public Integer getCurrentStock(Long componentTypeId) {
        return stockRecordRepository.calculateCurrentStock(componentTypeId);
    }
}

