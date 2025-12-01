package com.gok.demos.infrastructure.persistence.mapper;

import com.gok.demos.domain.entity.StockInRecord;
import com.gok.demos.domain.entity.StockOutRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 库存记录Mapper接口
 */
@Mapper
public interface StockRecordMapper {
    
    int insertStockIn(StockInRecord record);
    
    int insertStockOut(StockOutRecord record);
    
    List<StockInRecord> findStockInByComponentTypeId(@Param("componentTypeId") Long componentTypeId);
    
    List<StockOutRecord> findStockOutByComponentTypeId(@Param("componentTypeId") Long componentTypeId);
    
    Integer calculateCurrentStock(@Param("componentTypeId") Long componentTypeId);

    List<StockOutRecord> findStockOutByUserId(@Param("userId") Long userId);

    List<StockInRecord> findAllStockIn(@Param("offset") int offset, @Param("size") int size);

    int countStockIn();

    List<StockOutRecord> findAllStockOut(@Param("offset") int offset, @Param("size") int size);

    int countStockOut();
}

