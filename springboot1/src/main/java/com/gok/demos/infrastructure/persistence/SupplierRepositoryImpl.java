package com.gok.demos.infrastructure.persistence;

import com.gok.demos.domain.entity.Supplier;
import com.gok.demos.domain.repository.SupplierRepository;
import com.gok.demos.infrastructure.persistence.mapper.SupplierMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 供应商仓储实现
 */
@Repository
public class SupplierRepositoryImpl implements SupplierRepository {

    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public Supplier findById(Long id) {
        return supplierMapper.findById(id);
    }

    @Override
    public List<Supplier> findAll() {
        return supplierMapper.findAll();
    }

    @Override
    public List<Supplier> findByPage(Integer offset, Integer size) {
        return supplierMapper.findByPage(offset, size);
    }

    @Override
    public int count() {
        return supplierMapper.count();
    }

    @Override
    public int save(Supplier supplier) {
        return supplierMapper.insert(supplier);
    }

    @Override
    public int update(Supplier supplier) {
        return supplierMapper.update(supplier);
    }

    @Override
    public int delete(Long id) {
        return supplierMapper.delete(id);
    }
}

