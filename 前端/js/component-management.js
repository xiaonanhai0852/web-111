// 元器件管理系统前端脚本
const API_BASE_URL = 'http://localhost:8080/api';

// 当前用户信息
let currentUser = null;

// 页面加载时初始化
document.addEventListener('DOMContentLoaded', function() {
    try {
        // 检查登录状态
        checkLogin();

        // 加载数据
        loadComponentTypes();
        loadSuppliers();

        // 确保只显示第一个模块（入库管理）
        initializeDefaultTab();

        // 绑定表单提交事件
        const stockInForm = document.getElementById('stock-in-form');
        const stockOutForm = document.getElementById('stock-out-form');
        const componentTypeForm = document.getElementById('component-type-form');

        if (stockInForm) {
            stockInForm.addEventListener('submit', handleStockIn);
        }
        if (stockOutForm) {
            stockOutForm.addEventListener('submit', handleStockOut);
        }
        if (componentTypeForm) {
            componentTypeForm.addEventListener('submit', handleAddComponentType);
        }

        // 绑定管理员功能表单
        const supplierForm = document.getElementById('supplier-form');
        const userForm = document.getElementById('user-form');

        if (supplierForm) {
            supplierForm.addEventListener('submit', handleAddSupplier);
        }
        if (userForm) {
            userForm.addEventListener('submit', handleAddUser);
        }
    } catch (error) {
        console.error('页面初始化错误:', error);
    }
});

// 初始化默认标签页
function initializeDefaultTab() {
    // 隐藏所有内容区域
    document.querySelectorAll('.content-section').forEach(section => {
        section.classList.remove('active');
    });

    // 显示第一个模块（入库管理）
    const firstSection = document.getElementById('stock-in');
    if (firstSection) {
        firstSection.classList.add('active');
    }

    // 激活第一个标签
    document.querySelectorAll('.nav-tab').forEach((tab, index) => {
        if (index === 0) {
            tab.classList.add('active');
        } else {
            tab.classList.remove('active');
        }
    });
}

// 检查登录状态
function checkLogin() {
    const userInfo = localStorage.getItem('userInfo');
    if (!userInfo) {
        window.location.href = '登录.html';
        return;
    }

    try {
        currentUser = JSON.parse(userInfo);

        // 显示用户信息
        const usernameDisplay = document.getElementById('username-display');
        const roleDisplay = document.getElementById('role-display');

        if (usernameDisplay) {
            usernameDisplay.textContent = currentUser.username;
        }

        if (roleDisplay) {
            const roleText = currentUser.role === 'ADMIN' ? '管理员' : '普通用户';
            const roleClass = currentUser.role === 'ADMIN' ? 'admin' : 'user';
            roleDisplay.innerHTML = `<span class="user-badge ${roleClass}">${roleText}</span>`;
        }

        // 根据角色显示/隐藏功能
        if (currentUser.role === 'ADMIN') {
            document.body.classList.add('admin');
        } else {
            document.body.classList.remove('admin');
        }
    } catch (error) {
        console.error('登录状态检查错误:', error);
        localStorage.removeItem('userInfo');
        window.location.href = '登录.html';
    }
}

// 退出登录
function logout() {
    if (confirm('确定要退出登录吗？')) {
        localStorage.removeItem('userInfo');
        window.location.href = '登录.html';
    }
}

// 切换标签页
function showTab(tabName, event) {
    try {
        console.log('切换到标签页:', tabName);

        // 先隐藏所有内容区域
        const allSections = document.querySelectorAll('.content-section');
        allSections.forEach(section => {
            section.classList.remove('active');
        });

        // 移除所有标签的active类
        const allTabs = document.querySelectorAll('.nav-tab');
        allTabs.forEach(tab => {
            tab.classList.remove('active');
        });

        // 显示选中的内容区域
        const targetSection = document.getElementById(tabName);
        if (targetSection) {
            targetSection.classList.add('active');
            console.log('已显示模块:', tabName);
        } else {
            console.error('找不到模块:', tabName);
        }

        // 激活对应的标签
        if (event && event.target) {
            event.target.classList.add('active');
        }

        // 根据不同页面加载数据
        switch(tabName) {
            case 'statistics':
                loadStatistics();
                break;
            case 'stock-in-records':
                loadStockInRecords();
                break;
            case 'stock-out-records':
                loadStockOutRecords();
                break;
            case 'component-type':
                loadComponentTypes();
                loadSuppliers();
                loadComponentTypeList();
                break;
            case 'supplier':
                loadSuppliers();
                break;
            case 'user-management':
                console.log('加载用户列表');
                loadUsers();
                break;
            case 'stock-in':
                // 入库页面不需要额外加载
                break;
            case 'stock-out':
                // 取用页面不需要额外加载
                break;
            default:
                console.warn('未知的标签页:', tabName);
        }
    } catch (error) {
        console.error('切换标签页错误:', error);
    }
}

// 加载元器件类型列表（用于管理页面）
async function loadComponentTypeList() {
    console.log('开始加载元器件类型列表');

    try {
        const response = await fetch(`${API_BASE_URL}/component/types?page=1&size=100`);
        const result = await response.json();

        const tbody = document.getElementById('component-type-list');
        if (!tbody) {
            console.error('找不到component-type-list元素');
            return;
        }

        if (result.success && result.data && result.data.length > 0) {
            console.log('加载到', result.data.length, '个元器件类型');
            tbody.innerHTML = '';

            result.data.forEach(item => {
                const spec = item.specification || {};
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${item.id}</td>
                    <td>${item.name}</td>
                    <td>${item.model}</td>
                    <td>${item.supplierId}</td>
                    <td>¥${parseFloat(item.unitPrice).toFixed(2)}</td>
                    <td>${item.imageUrl ? '<a href="' + item.imageUrl + '" target="_blank">查看</a>' : '-'}</td>
                    <td>${spec.voltage || '-'}</td>
                    <td>${spec.power || '-'}</td>
                    <td>${spec.tolerance || '-'}</td>
                    <td>${spec.temperature || '-'}</td>
                    <td>${spec.packageType || '-'}</td>
                    <td style="max-width: 150px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;" title="${spec.otherParams || ''}">${spec.otherParams || '-'}</td>
                    <td>
                        <button onclick="deleteComponentType(${item.id})" style="background: #dc3545; padding: 5px 10px; color: white; border: none; border-radius: 3px; cursor: pointer;">删除</button>
                    </td>
                `;
                tbody.appendChild(row);
            });

            console.log('元器件类型列表加载完成');
        } else {
            tbody.innerHTML = '<tr><td colspan="13" style="text-align: center;">暂无数据</td></tr>';
        }
    } catch (error) {
        console.error('加载元器件类型列表失败:', error);
        const tbody = document.getElementById('component-type-list');
        if (tbody) {
            tbody.innerHTML = '<tr><td colspan="13" style="text-align: center; color: #dc3545;">加载失败，请检查后端服务</td></tr>';
        }
    }
}

// 删除元器件类型
async function deleteComponentType(id) {
    if (!confirm('确定要删除这个元器件类型吗？')) {
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/component/type/${id}`, {
            method: 'DELETE'
        });

        const result = await response.json();
        alert(result.message);

        if (result.success) {
            loadComponentTypeList(); // 刷新列表
            loadComponentTypes(); // 刷新下拉框
        }
    } catch (error) {
        alert('删除失败：' + error.message);
    }
}

// 加载元器件类型列表
async function loadComponentTypes() {
    try {
        const response = await fetch(`${API_BASE_URL}/component/types?page=1&size=100`);
        const result = await response.json();

        if (result.success && result.data) {
            const selectIn = document.getElementById('component-type-in');
            const selectOut = document.getElementById('component-type-out');

            if (selectIn) {
                // 清空现有选项
                selectIn.innerHTML = '<option value="">请选择元器件类型</option>';

                // 添加选项
                result.data.forEach(item => {
                    const option = `<option value="${item.id}">${item.name} - ${item.model}</option>`;
                    selectIn.innerHTML += option;
                });
            }

            if (selectOut) {
                // 清空现有选项
                selectOut.innerHTML = '<option value="">请选择元器件类型</option>';

                // 添加选项
                result.data.forEach(item => {
                    const option = `<option value="${item.id}">${item.name} - ${item.model}</option>`;
                    selectOut.innerHTML += option;
                });
            }
        }
    } catch (error) {
        console.error('加载元器件类型失败:', error);
    }
}

// 更新库存显示
async function updateStock() {
    const componentTypeSelect = document.getElementById('component-type-out');
    const stockSpan = document.getElementById('current-stock');

    if (!componentTypeSelect || !stockSpan) {
        return;
    }

    const componentTypeId = componentTypeSelect.value;

    if (!componentTypeId) {
        stockSpan.textContent = '-';
        stockSpan.style.color = '#999';
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/component/stock/${componentTypeId}`);
        const result = await response.json();

        if (result.success) {
            const stock = result.stock || 0;
            stockSpan.textContent = stock;
            stockSpan.style.color = stock > 10 ? '#28a745' : '#dc3545';
        } else {
            stockSpan.textContent = '查询失败';
            stockSpan.style.color = '#dc3545';
        }
    } catch (error) {
        console.error('查询库存失败:', error);
        stockSpan.textContent = '查询失败';
        stockSpan.style.color = '#dc3545';
    }
}

// 处理入库提交
async function handleStockIn(event) {
    event.preventDefault();

    try {
        const componentTypeId = document.getElementById('component-type-in').value;
        const quantity = document.getElementById('quantity-in').value;
        const purchaseDate = document.getElementById('purchase-date').value;
        const purchaserName = document.getElementById('purchaser-name').value;
        const batchNumber = document.getElementById('batch-number').value;
        const remarks = document.getElementById('remarks-in').value;

        if (!componentTypeId || !quantity || !purchaseDate || !purchaserName) {
            showAlert('stock-in-alert', '请填写所有必填项', false);
            return;
        }

        const data = {
            componentTypeId: parseInt(componentTypeId),
            quantity: parseInt(quantity),
            purchaseDate: purchaseDate,
            purchaserId: currentUser ? 1 : 1, // 使用当前用户ID（演示用固定值）
            purchaserName: purchaserName || (currentUser ? currentUser.username : ''),
            batchNumber: batchNumber || '',
            remarks: remarks || ''
        };

        const response = await fetch(`${API_BASE_URL}/component/stock-in`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        const result = await response.json();
        showAlert('stock-in-alert', result.message, result.success);

        if (result.success) {
            const form = document.getElementById('stock-in-form');
            if (form) {
                form.reset();
            }
        }
    } catch (error) {
        console.error('入库错误:', error);
        showAlert('stock-in-alert', '入库失败：' + error.message, false);
    }
}

// 处理取用提交
async function handleStockOut(event) {
    event.preventDefault();

    try {
        const componentTypeId = document.getElementById('component-type-out').value;
        const quantity = document.getElementById('quantity-out').value;
        const userName = document.getElementById('user-name').value;
        const purpose = document.getElementById('purpose').value;
        const projectName = document.getElementById('project-name').value;
        const remarks = document.getElementById('remarks-out').value;

        if (!componentTypeId || !quantity || !userName) {
            showAlert('stock-out-alert', '请填写所有必填项', false);
            return;
        }

        const data = {
            componentTypeId: parseInt(componentTypeId),
            quantity: parseInt(quantity),
            userId: currentUser ? 1 : 1, // 使用当前用户ID（演示用固定值）
            userName: userName || (currentUser ? currentUser.username : ''),
            purpose: purpose || '',
            projectName: projectName || '',
            remarks: remarks || ''
        };

        const response = await fetch(`${API_BASE_URL}/component/stock-out`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        const result = await response.json();
        showAlert('stock-out-alert', result.message, result.success);

        if (result.success) {
            const form = document.getElementById('stock-out-form');
            const stockSpan = document.getElementById('current-stock');

            if (form) {
                form.reset();
            }
            if (stockSpan) {
                stockSpan.textContent = '-';
                stockSpan.style.color = '#999';
            }
        }
    } catch (error) {
        console.error('取用错误:', error);
        showAlert('stock-out-alert', '取用失败：' + error.message, false);
    }
}

// 加载统计数据
async function loadStatistics() {
    try {
        const response = await fetch(`${API_BASE_URL}/statistics/inventory`);
        const result = await response.json();

        const tbody = document.getElementById('statistics-body');
        if (!tbody) {
            console.error('找不到statistics-body元素');
            return;
        }

        if (result.success && result.data && result.data.length > 0) {
            tbody.innerHTML = '';

            result.data.forEach(item => {
                const stock = item.stock || 0;
                const unitPrice = item.unitPrice || 0;
                const value = (stock * unitPrice).toFixed(2);
                const stockColor = stock > 10 ? '#28a745' : '#dc3545';

                const row = `
                    <tr>
                        <td>${item.id || '-'}</td>
                        <td>${item.name || '-'}</td>
                        <td>${item.model || '-'}</td>
                        <td style="color: ${stockColor}; font-weight: bold;">${stock}</td>
                        <td>¥${unitPrice.toFixed(2)}</td>
                        <td>¥${value}</td>
                    </tr>
                `;
                tbody.innerHTML += row;
            });
        } else {
            tbody.innerHTML = '<tr><td colspan="6" style="text-align: center;">暂无数据</td></tr>';
        }
    } catch (error) {
        console.error('加载统计数据失败:', error);
        const tbody = document.getElementById('statistics-body');
        if (tbody) {
            tbody.innerHTML = '<tr><td colspan="6" style="text-align: center; color: #dc3545;">加载失败，请检查后端服务</td></tr>';
        }
    }
}

// 处理添加元器件类型
async function handleAddComponentType(event) {
    event.preventDefault();

    const data = {
        name: document.getElementById('component-name').value,
        model: document.getElementById('component-model').value,
        supplierId: parseInt(document.getElementById('supplier-id-select').value),
        unitPrice: parseFloat(document.getElementById('unit-price').value),
        imageUrl: document.getElementById('component-image-url')?.value || '',
        voltage: document.getElementById('component-voltage')?.value || '',
        power: document.getElementById('component-power')?.value || '',
        tolerance: document.getElementById('component-tolerance')?.value || '',
        temperature: document.getElementById('component-temperature')?.value || '',
        packageType: document.getElementById('component-package')?.value || '',
        otherParams: document.getElementById('component-other-params')?.value || ''
    };

    try {
        const response = await fetch(`${API_BASE_URL}/component/type`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        const result = await response.json();
        showAlert('component-type-alert', result.message, result.success);

        if (result.success) {
            document.getElementById('component-type-form').reset();
            loadComponentTypes(); // 重新加载元器件类型列表
            loadComponentTypeList(); // 刷新管理列表
        }
    } catch (error) {
        showAlert('component-type-alert', '添加失败：' + error.message, false);
    }
}

// 显示提示信息
function showAlert(elementId, message, isSuccess) {
    const alert = document.getElementById(elementId);
    if (!alert) {
        console.error(`找不到alert元素: ${elementId}`);
        return;
    }

    alert.textContent = message || '操作完成';
    alert.className = 'alert ' + (isSuccess ? 'success' : 'error');
    alert.style.display = 'block';

    // 3秒后自动隐藏
    setTimeout(() => {
        alert.style.display = 'none';
    }, 3000);
}

// ========== 供应商管理功能 ==========

// 加载供应商列表
async function loadSuppliers() {
    console.log('开始加载供应商列表');

    try {
        const response = await fetch(`${API_BASE_URL}/supplier/list?page=1&size=100`);
        const result = await response.json();

        if (result.success && result.data) {
            console.log('加载到', result.data.length, '个供应商');

            // 更新供应商下拉框
            const select = document.getElementById('supplier-id-select');
            if (select) {
                select.innerHTML = '<option value="">请选择供应商</option>';
                result.data.forEach(supplier => {
                    const option = document.createElement('option');
                    option.value = supplier.id;
                    option.textContent = supplier.name;
                    select.appendChild(option);
                });
            }

            // 更新供应商列表表格
            const tbody = document.getElementById('supplier-list');
            if (tbody) {
                if (result.data.length > 0) {
                    tbody.innerHTML = '';
                    result.data.forEach(supplier => {
                        const addr = supplier.address || {};
                        const row = document.createElement('tr');
                        row.innerHTML = `
                            <td>${supplier.id}</td>
                            <td>${supplier.name}</td>
                            <td>${supplier.phone || '-'}</td>
                            <td>${supplier.email || '-'}</td>
                            <td>${supplier.contactPerson || '-'}</td>
                            <td>${addr.province || '-'}</td>
                            <td>${addr.city || '-'}</td>
                            <td>${addr.district || '-'}</td>
                            <td>${addr.street || '-'}</td>
                            <td>${addr.postalCode || '-'}</td>
                            <td>
                                <button onclick="deleteSupplier(${supplier.id})" style="background: #dc3545; padding: 5px 10px; color: white; border: none; border-radius: 3px; cursor: pointer;">删除</button>
                            </td>
                        `;
                        tbody.appendChild(row);
                    });
                    console.log('供应商列表加载完成');
                } else {
                    tbody.innerHTML = '<tr><td colspan="11" style="text-align: center;">暂无数据</td></tr>';
                }
            }
        } else {
            const tbody = document.getElementById('supplier-list');
            if (tbody) {
                tbody.innerHTML = '<tr><td colspan="11" style="text-align: center;">暂无数据</td></tr>';
            }
        }
    } catch (error) {
        console.error('加载供应商列表失败:', error);
        const tbody = document.getElementById('supplier-list');
        if (tbody) {
            tbody.innerHTML = '<tr><td colspan="11" style="text-align: center; color: #dc3545;">加载失败，请检查后端服务</td></tr>';
        }
    }
}

// 添加供应商
async function handleAddSupplier(event) {
    event.preventDefault();

    const data = {
        name: document.getElementById('supplier-name').value,
        phone: document.getElementById('supplier-phone').value,
        email: document.getElementById('supplier-email').value || '',
        contactPerson: document.getElementById('supplier-contact').value || '',
        province: document.getElementById('supplier-province').value || '',
        city: document.getElementById('supplier-city').value || '',
        district: document.getElementById('supplier-district').value || '',
        street: document.getElementById('supplier-street').value || '',
        postalCode: document.getElementById('supplier-postal-code').value || ''
    };

    try {
        const response = await fetch(`${API_BASE_URL}/supplier`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        const result = await response.json();
        showAlert('supplier-alert', result.message, result.success);

        if (result.success) {
            document.getElementById('supplier-form').reset();
            loadSuppliers();
        }
    } catch (error) {
        showAlert('supplier-alert', '添加失败：' + error.message, false);
    }
}

// 删除供应商
async function deleteSupplier(id) {
    if (!confirm('确定要删除这个供应商吗？')) {
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/supplier/${id}`, {
            method: 'DELETE'
        });

        const result = await response.json();
        alert(result.message);

        if (result.success) {
            loadSuppliers();
        }
    } catch (error) {
        alert('删除失败：' + error.message);
    }
}

// ========== 用户管理功能 ==========

// 加载用户列表
async function loadUsers() {
    console.log('开始加载用户列表');

    try {
        const response = await fetch(`${API_BASE_URL}/user/list?page=1&size=100`);
        const result = await response.json();

        const tbody = document.getElementById('user-list');
        if (!tbody) {
            console.error('找不到user-list元素');
            return;
        }

        if (result.success && result.data && result.data.length > 0) {
            tbody.innerHTML = '';

            result.data.forEach(user => {
                const roleText = user.role === 'ADMIN' ? '管理员' : '普通用户';
                const roleClass = user.role.toLowerCase();

                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${user.id}</td>
                    <td>${user.studentId}</td>
                    <td>${user.studentId}</td>
                    <td>${user.realName}</td>
                    <td>${user.email}</td>
                    <td>${user.phone}</td>
                    <td><span class="user-badge ${roleClass}">${roleText}</span></td>
                    <td>${user.department || '-'}</td>
                    <td>
                        <button onclick="deleteUser(${user.id})" style="background: #dc3545; padding: 5px 10px; color: white; border: none; border-radius: 3px; cursor: pointer;">删除</button>
                    </td>
                `;
                tbody.appendChild(row);
            });

            console.log('用户列表加载完成，共', result.data.length, '个用户');
        } else {
            tbody.innerHTML = '<tr><td colspan="9" style="text-align: center;">暂无数据</td></tr>';
        }
    } catch (error) {
        console.error('加载用户列表失败:', error);
        const tbody = document.getElementById('user-list');
        if (tbody) {
            tbody.innerHTML = '<tr><td colspan="9" style="text-align: center; color: #dc3545;">加载失败，请检查后端服务</td></tr>';
        }
    }
}

// 添加用户
async function handleAddUser(event) {
    event.preventDefault();

    try {
        const studentId = document.getElementById('user-student-id').value;
        const realName = document.getElementById('user-real-name').value;
        const username = document.getElementById('user-username').value;
        const password = document.getElementById('user-password').value;
        const email = document.getElementById('user-email').value;
        const phone = document.getElementById('user-phone').value;
        const role = document.getElementById('user-role').value;
        const department = document.getElementById('user-department').value;

        // 验证密码长度
        if (password.length < 6) {
            showAlert('user-alert', '密码长度至少6位！', false);
            return;
        }

        // 验证手机号
        if (!/^[0-9]{11}$/.test(phone)) {
            showAlert('user-alert', '请输入正确的11位手机号！', false);
            return;
        }

        const data = {
            studentId: studentId,
            realName: realName,
            username: username,
            password: password,
            email: email,
            phone: phone,
            role: role,
            department: department || ''
        };

        console.log('添加用户:', data);

        // 调用后端API添加用户
        const response = await fetch(`${API_BASE_URL}/user`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        const result = await response.json();

        if (result.success) {
            showAlert('user-alert', '用户添加成功！', true);

            const form = document.getElementById('user-form');
            if (form) {
                form.reset();
            }

            // 立即刷新用户列表
            loadUsers();
        } else {
            showAlert('user-alert', result.message || '添加失败', false);
        }
    } catch (error) {
        console.error('添加用户错误:', error);
        showAlert('user-alert', '添加失败：' + error.message, false);
    }
}

// 删除用户
async function deleteUser(id) {
    if (!confirm('确定要删除这个用户吗？')) {
        return;
    }

    console.log('删除用户ID:', id);

    try {
        const response = await fetch(`${API_BASE_URL}/user/${id}`, {
            method: 'DELETE'
        });

        const result = await response.json();

        if (result.success) {
            alert('用户删除成功！');
            // 立即刷新用户列表
            loadUsers();
        } else {
            alert(result.message || '删除失败');
        }
    } catch (error) {
        console.error('删除用户错误:', error);
        alert('删除失败：' + error.message);
    }
}

// ========== 入库记录和出库记录功能 ==========

// 加载入库记录
async function loadStockInRecords() {
    console.log('开始加载入库记录');

    try {
        const response = await fetch(`${API_BASE_URL}/component/stock-in/records?page=1&size=100`);
        const result = await response.json();

        const tbody = document.getElementById('stock-in-records-body');
        if (!tbody) {
            console.error('找不到stock-in-records-body元素');
            return;
        }

        if (result.success && result.data && result.data.length > 0) {
            tbody.innerHTML = '';

            result.data.forEach(record => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${record.id}</td>
                    <td>${record.componentTypeId}</td>
                    <td>${record.quantity}</td>
                    <td>${formatDateTime(record.purchaseDate)}</td>
                    <td>${record.purchaserName}</td>
                    <td>${record.batchNumber || '-'}</td>
                    <td>${record.remarks || '-'}</td>
                    <td>${formatDateTime(record.createdAt)}</td>
                `;
                tbody.appendChild(row);
            });

            console.log('入库记录加载完成，共', result.data.length, '条');
        } else {
            tbody.innerHTML = '<tr><td colspan="8" style="text-align: center;">暂无数据</td></tr>';
        }
    } catch (error) {
        console.error('加载入库记录失败:', error);
        const tbody = document.getElementById('stock-in-records-body');
        if (tbody) {
            tbody.innerHTML = '<tr><td colspan="8" style="text-align: center; color: #dc3545;">加载失败，请检查后端服务</td></tr>';
        }
    }
}

// 加载出库记录
async function loadStockOutRecords() {
    console.log('开始加载出库记录');

    try {
        const response = await fetch(`${API_BASE_URL}/component/stock-out/records?page=1&size=100`);
        const result = await response.json();

        const tbody = document.getElementById('stock-out-records-body');
        if (!tbody) {
            console.error('找不到stock-out-records-body元素');
            return;
        }

        if (result.success && result.data && result.data.length > 0) {
            tbody.innerHTML = '';

            result.data.forEach(record => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${record.id}</td>
                    <td>${record.componentTypeId}</td>
                    <td>${record.quantity}</td>
                    <td>${record.userName}</td>
                    <td>${record.purpose || '-'}</td>
                    <td>${record.projectName || '-'}</td>
                    <td>${record.remarks || '-'}</td>
                    <td>${formatDateTime(record.createdAt)}</td>
                `;
                tbody.appendChild(row);
            });

            console.log('出库记录加载完成，共', result.data.length, '条');
        } else {
            tbody.innerHTML = '<tr><td colspan="8" style="text-align: center;">暂无数据</td></tr>';
        }
    } catch (error) {
        console.error('加载出库记录失败:', error);
        const tbody = document.getElementById('stock-out-records-body');
        if (tbody) {
            tbody.innerHTML = '<tr><td colspan="8" style="text-align: center; color: #dc3545;">加载失败，请检查后端服务</td></tr>';
        }
    }
}

// 格式化日期时间
function formatDateTime(dateTimeStr) {
    if (!dateTimeStr) return '-';

    try {
        const date = new Date(dateTimeStr);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        const seconds = String(date.getSeconds()).padStart(2, '0');

        return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    } catch (error) {
        return dateTimeStr;
    }
}

