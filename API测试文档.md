# 元器件管理系统 - API测试文档

## 一、Swagger API文档访问

启动后端服务后，访问以下地址查看完整的API文档：

```
http://localhost:8080/swagger-ui/index.html
```

Swagger提供了交互式的API文档，可以直接在浏览器中测试所有接口。

## 二、核心API接口列表

### 1. 元器件管理接口

#### 1.1 获取元器件类型列表

**接口地址：** `GET /api/component/types`

**请求参数：**
- page: 页码（默认1）
- size: 每页数量（默认10）

**请求示例：**
```
GET http://localhost:8080/api/component/types?page=1&size=10
```

**响应示例：**
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "name": "电阻",
      "model": "10KΩ",
      "supplierId": 1,
      "unitPrice": 0.05
    }
  ],
  "total": 8,
  "page": 1,
  "size": 10
}
```

#### 1.2 添加元器件类型（管理员）

**接口地址：** `POST /api/component/type`

**请求体：**
```json
{
  "name": "电阻",
  "model": "100KΩ",
  "supplierId": 1,
  "unitPrice": 0.06,
  "voltage": "5V",
  "power": "0.25W",
  "tolerance": "±5%",
  "temperature": "-40℃~85℃",
  "packageType": "0805"
}
```

**响应示例：**
```json
{
  "success": true,
  "message": "添加成功",
  "data": {
    "id": 9,
    "name": "电阻",
    "model": "100KΩ"
  }
}
```

#### 1.3 添加入库记录

**接口地址：** `POST /api/component/stock-in`

**请求体：**
```json
{
  "componentTypeId": 1,
  "quantity": 100,
  "purchaseDate": "2024-01-25T10:00:00",
  "purchaserId": 1,
  "purchaserName": "张三",
  "batchNumber": "BATCH20240125001",
  "remarks": "补充库存"
}
```

**响应示例：**
```json
{
  "success": true,
  "message": "入库成功",
  "data": {
    "id": 9,
    "componentTypeId": 1,
    "quantity": 100
  }
}
```

#### 1.4 取用元器件

**接口地址：** `POST /api/component/stock-out`

**请求体：**
```json
{
  "componentTypeId": 1,
  "quantity": 10,
  "userId": 2,
  "userName": "李四",
  "purpose": "实验使用",
  "projectName": "智能温控系统",
  "remarks": "用于电路板焊接"
}
```

**成功响应：**
```json
{
  "success": true,
  "message": "取用成功",
  "data": {
    "id": 9,
    "componentTypeId": 1,
    "quantity": 10
  }
}
```

**库存不足响应：**
```json
{
  "success": false,
  "message": "库存不足！当前库存：5，需要：10"
}
```

#### 1.5 查询当前库存

**接口地址：** `GET /api/component/stock/{componentTypeId}`

**请求示例：**
```
GET http://localhost:8080/api/component/stock/1
```

**响应示例：**
```json
{
  "success": true,
  "stock": 950
}
```

#### 1.6 查询取用历史

**接口地址：** `GET /api/component/stock-out/history/{componentTypeId}`

**请求示例：**
```
GET http://localhost:8080/api/component/stock-out/history/1
```

**响应示例：**
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "componentTypeId": 1,
      "quantity": 50,
      "userId": 2,
      "userName": "张三",
      "purpose": "实验使用",
      "projectName": "智能温控系统",
      "createdAt": "2024-01-23T14:30:00"
    }
  ]
}
```

### 2. 统计查询接口

#### 2.1 统计库存

**接口地址：** `GET /api/statistics/inventory`

**请求示例：**
```
GET http://localhost:8080/api/statistics/inventory
```

**响应示例：**
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "name": "电阻",
      "model": "10KΩ",
      "stock": 950,
      "unitPrice": 0.05
    },
    {
      "id": 2,
      "name": "电阻",
      "model": "1KΩ",
      "stock": 470,
      "unitPrice": 0.05
    }
  ]
}
```

#### 2.2 按型号查询库存

**接口地址：** `GET /api/statistics/stock-by-model`

**请求参数：**
- model: 元器件型号

**请求示例：**
```
GET http://localhost:8080/api/statistics/stock-by-model?model=10KΩ
```

**响应示例：**
```json
{
  "success": true,
  "componentType": {
    "id": 1,
    "name": "电阻",
    "model": "10KΩ"
  },
  "stock": 950
}
```

#### 2.3 库存预警

**接口地址：** `GET /api/statistics/low-stock-alert`

**请求参数：**
- threshold: 库存阈值（默认10）

**请求示例：**
```
GET http://localhost:8080/api/statistics/low-stock-alert?threshold=20
```

**响应示例：**
```json
{
  "success": true,
  "data": [
    {
      "id": 5,
      "name": "传感器",
      "model": "DHT11",
      "stock": 15,
      "threshold": 20
    }
  ],
  "count": 1
}
```

### 3. 供应商管理接口

#### 3.1 获取供应商列表

**接口地址：** `GET /api/supplier/list`

**请求参数：**
- page: 页码（默认1）
- size: 每页数量（默认10）

**请求示例：**
```
GET http://localhost:8080/api/supplier/list?page=1&size=10
```

**响应示例：**
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "name": "深圳市华强电子有限公司",
      "phone": "0755-12345678",
      "email": "huaqiang@example.com"
    }
  ],
  "total": 3
}
```

#### 3.2 添加供应商

**接口地址：** `POST /api/supplier`

**请求体：**
```json
{
  "name": "北京电子科技有限公司",
  "province": "北京市",
  "city": "北京市",
  "district": "海淀区",
  "street": "中关村大街200号",
  "postalCode": "100000",
  "phone": "010-99999999",
  "email": "beijing@example.com",
  "contactPerson": "赵经理"
}
```

**响应示例：**
```json
{
  "success": true,
  "message": "添加成功",
  "data": {
    "id": 4,
    "name": "北京电子科技有限公司"
  }
}
```

## 三、使用Swagger进行API测试

### 步骤1：访问Swagger UI

启动后端服务后，在浏览器中打开：
```
http://localhost:8080/swagger-ui/index.html
```

### 步骤2：选择要测试的接口

在Swagger UI中，可以看到所有的API接口按照Controller分组显示。

### 步骤3：测试"取用元器件"接口

1. 找到 **元器件管理接口** 分组
2. 点击 `POST /api/component/stock-out` 接口
3. 点击 **Try it out** 按钮
4. 填写请求参数：
   ```json
   {
     "componentTypeId": 1,
     "quantity": 10,
     "userId": 2,
     "userName": "测试用户",
     "purpose": "测试取用",
     "projectName": "测试项目"
   }
   ```
5. 点击 **Execute** 按钮
6. 查看响应结果

### 步骤4：验证库存扣减

1. 测试 `GET /api/component/stock/1` 接口
2. 查看库存是否正确扣减

## 四、常见错误处理

### 错误1：库存不足

**请求：**
```json
{
  "componentTypeId": 1,
  "quantity": 10000
}
```

**响应：**
```json
{
  "success": false,
  "message": "库存不足！当前库存：950，需要：10000"
}
```

### 错误2：元器件类型不存在

**请求：**
```json
{
  "componentTypeId": 999,
  "quantity": 10
}
```

**响应：**
```json
{
  "success": false,
  "message": "参数错误：元器件类型不存在：999"
}
```

### 错误3：参数验证失败

**请求：**
```json
{
  "componentTypeId": 1,
  "quantity": -10
}
```

**响应：**
```json
{
  "success": false,
  "message": "参数错误：元器件类型ID和数量不能为空，且数量必须大于0"
}
```

## 五、测试建议

1. **先测试查询接口**：确保系统有数据
2. **测试入库功能**：添加一些库存
3. **测试取用功能**：验证库存扣减逻辑
4. **测试边界情况**：库存不足、参数错误等
5. **测试统计功能**：验证库存计算是否正确

## 六、性能测试

可以使用以下工具进行性能测试：
- **JMeter**：模拟并发请求
- **Postman**：批量测试接口
- **curl**：命令行测试

示例curl命令：
```bash
curl -X POST "http://localhost:8080/api/component/stock-out" \
  -H "Content-Type: application/json" \
  -d '{
    "componentTypeId": 1,
    "quantity": 10,
    "userId": 2,
    "userName": "测试用户"
  }'
```

