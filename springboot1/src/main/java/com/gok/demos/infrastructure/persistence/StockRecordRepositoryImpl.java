package com.gok.demos.infrastructure.persistence;

import com.gok.demos.domain.entity.StockInRecord;
import com.gok.demos.domain.entity.StockOutRecord;
import com.gok.demos.domain.repository.StockRecordRepository;
import com.gok.demos.infrastructure.persistence.mapper.StockRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 库存记录仓储实现
 */
@Repository
public class StockRecordRepositoryImpl implements StockRecordRepository {

    @Autowired
    private StockRecordMapper stockRecordMapper;

    @Override
    public int saveStockIn(StockInRecord record) {
        return stockRecordMapper.insertStockIn(record);
    }

    @Override
    public int saveStockOut(StockOutRecord record) {
        return stockRecordMapper.insertStockOut(record);
    }

    @Override
    public List<StockInRecord> findStockInByComponentTypeId(Long componentTypeId) {
        return stockRecordMapper.findStockInByComponentTypeId(componentTypeId);
    }

    @Override
    public List<StockOutRecord> findStockOutByComponentTypeId(Long componentTypeId) {
        return stockRecordMapper.findStockOutByComponentTypeId(componentTypeId);
    }

    @Override
    public Integer calculateCurrentStock(Long componentTypeId) {
        return stockRecordMapper.calculateCurrentStock(componentTypeId);
    }

    @Override
    public List<StockOutRecord> findStockOutByUserId(Long userId) {
        return stockRecordMapper.findStockOutByUserId(userId);
    }

    @Override
    public List<StockInRecord> findAllStockIn(int offset, int size) {
        return stockRecordMapper.findAllStockIn(offset, size);
    }

    @Override
    public int countStockIn() {
        return stockRecordMapper.countStockIn();
    }

    @Override
    public List<StockOutRecord> findAllStockOut(int offset, int size) {
        return stockRecordMapper.findAllStockOut(offset, size);
    }

    @Override
    public int countStockOut() {
        return stockRecordMapper.countStockOut();
    }
}

