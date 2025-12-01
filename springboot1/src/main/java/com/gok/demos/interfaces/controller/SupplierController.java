package com.gok.demos.interfaces.controller;

import com.gok.demos.application.SupplierApplicationService;
import com.gok.demos.domain.entity.Supplier;
import com.gok.demos.domain.valueobject.Address;
import com.gok.demos.interfaces.dto.SupplierDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 供应商控制器
 * 管理员用例：对供应商信息的增删改查
 */
@RestController
@RequestMapping("/api/supplier")
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080", "file://*", "http://localhost:63342"}, 
             allowCredentials = "true")
@Api(tags = "供应商管理接口")
public class SupplierController {

    @Autowired
    private SupplierApplicationService supplierApplicationService;

    /**
     * 获取供应商列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取供应商列表", notes = "分页查询供应商列表")
    public Map<String, Object> getSupplierList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return supplierApplicationService.getSupplierList(page, size);
    }

    /**
     * 获取供应商详情
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "获取供应商详情", notes = "根据ID查询供应商详细信息")
    public Map<String, Object> getSupplierById(@PathVariable Long id) {
        return supplierApplicationService.getSupplierById(id);
    }

    /**
     * 添加供应商
     */
    @PostMapping
    @ApiOperation(value = "添加供应商", notes = "管理员添加新的供应商")
    public Map<String, Object> addSupplier(@RequestBody SupplierDTO dto) {
        // 创建地址值对象
        Address address = new Address(
            dto.getProvince(), dto.getCity(), dto.getDistrict(),
            dto.getStreet(), dto.getPostalCode()
        );

        // 创建供应商实体
        Supplier supplier = new Supplier();
        supplier.setName(dto.getName());
        supplier.setAddress(address);
        supplier.setPhone(dto.getPhone());
        supplier.setEmail(dto.getEmail());
        supplier.setContactPerson(dto.getContactPerson());

        return supplierApplicationService.addSupplier(supplier);
    }

    /**
     * 更新供应商
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "更新供应商", notes = "管理员更新供应商信息")
    public Map<String, Object> updateSupplier(@PathVariable Long id, @RequestBody SupplierDTO dto) {
        // 创建地址值对象
        Address address = new Address(
            dto.getProvince(), dto.getCity(), dto.getDistrict(),
            dto.getStreet(), dto.getPostalCode()
        );

        // 创建供应商实体
        Supplier supplier = new Supplier();
        supplier.setId(id);
        supplier.setName(dto.getName());
        supplier.setAddress(address);
        supplier.setPhone(dto.getPhone());
        supplier.setEmail(dto.getEmail());
        supplier.setContactPerson(dto.getContactPerson());

        return supplierApplicationService.updateSupplier(supplier);
    }

    /**
     * 删除供应商
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除供应商", notes = "管理员删除供应商")
    public Map<String, Object> deleteSupplier(@PathVariable Long id) {
        return supplierApplicationService.deleteSupplier(id);
    }
}

