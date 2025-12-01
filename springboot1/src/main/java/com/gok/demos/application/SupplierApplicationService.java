package com.gok.demos.application;

import com.gok.demos.domain.entity.Supplier;
import com.gok.demos.domain.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 供应商应用服务
 * 管理员用例：对供应商信息的增删改查
 */
@Service
public class SupplierApplicationService {

    @Autowired
    private SupplierRepository supplierRepository;

    /**
     * 获取供应商列表
     */
    public Map<String, Object> getSupplierList(int page, int size) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            int offset = (page - 1) * size;
            List<Supplier> list = supplierRepository.findByPage(offset, size);
            int total = supplierRepository.count();
            
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
     * 添加供应商
     */
    @Transactional
    public Map<String, Object> addSupplier(Supplier supplier) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            supplier.setCreatedAt(LocalDateTime.now());
            supplier.setUpdatedAt(LocalDateTime.now());
            
            int rows = supplierRepository.save(supplier);
            if (rows > 0) {
                result.put("success", true);
                result.put("message", "添加成功");
                result.put("data", supplier);
            } else {
                result.put("success", false);
                result.put("message", "添加失败");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "添加失败：" + e.getMessage());
        }
        
        return result;
    }

    /**
     * 更新供应商
     */
    @Transactional
    public Map<String, Object> updateSupplier(Supplier supplier) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            supplier.setUpdatedAt(LocalDateTime.now());
            
            int rows = supplierRepository.update(supplier);
            if (rows > 0) {
                result.put("success", true);
                result.put("message", "更新成功");
            } else {
                result.put("success", false);
                result.put("message", "更新失败");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "更新失败：" + e.getMessage());
        }
        
        return result;
    }

    /**
     * 删除供应商
     */
    @Transactional
    public Map<String, Object> deleteSupplier(Long id) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            int rows = supplierRepository.delete(id);
            if (rows > 0) {
                result.put("success", true);
                result.put("message", "删除成功");
            } else {
                result.put("success", false);
                result.put("message", "删除失败");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "删除失败：" + e.getMessage());
        }
        
        return result;
    }

    /**
     * 获取供应商详情
     */
    public Map<String, Object> getSupplierById(Long id) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Supplier supplier = supplierRepository.findById(id);
            if (supplier != null) {
                result.put("success", true);
                result.put("data", supplier);
            } else {
                result.put("success", false);
                result.put("message", "供应商不存在");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询失败：" + e.getMessage());
        }
        
        return result;
    }
}

