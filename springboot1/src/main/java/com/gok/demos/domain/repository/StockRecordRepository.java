package com.gok.demos.domain.repository;

import com.gok.demos.domain.entity.StockInRecord;
import com.gok.demos.domain.entity.StockOutRecord;
import java.util.List;

/**
 * 库存记录仓储接口
 * 管理入库和取用记录
 */
public interface StockRecordRepository {
    
    /**
     * 保存入库记录
     */
    int saveStockIn(StockInRecord record);
    
    /**
     * 保存取用记录
     */
    int saveStockOut(StockOutRecord record);
    
    /**
     * 查询元器件的入库记录
     */
    List<StockInRecord> findStockInByComponentTypeId(Long componentTypeId);
    
    /**
     * 查询元器件的取用记录
     */
    List<StockOutRecord> findStockOutByComponentTypeId(Long componentTypeId);
    
    /**
     * 计算元器件的当前库存
     * 库存 = SUM(入库数量) - SUM(取用数量)
     */
    Integer calculateCurrentStock(Long componentTypeId);
    
    /**
     * 查询用户的取用历史
     */
    List<StockOutRecord> findStockOutByUserId(Long userId);

    /**
     * 获取所有入库记录（分页）
     */
    List<StockInRecord> findAllStockIn(int offset, int size);

    /**
     * 获取入库记录总数
     */
    int countStockIn();

    /**
     * 获取所有出库记录（分页）
     */
    List<StockOutRecord> findAllStockOut(int offset, int size);

    /**
     * 获取出库记录总数
     */
    int countStockOut();
}

