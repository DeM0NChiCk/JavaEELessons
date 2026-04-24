<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Все заказы</title>
    <link rel="stylesheet" href="../css/orders.css">
    <link rel="stylesheet" href="../css/adminOrder.css">
</head>
<body>

<div class="container">
    <div class="header">
        <button class="home-button" onclick="location.href='main'">На главную</button>
        <h1>Все заказы</h1>
        <button class="home-button" onclick="location.href='products'">Каталог</button>
    </div>

    <#if orders?size == 0>
        <p>Нет заказов.</p>
    <#else>
        <table>
            <thead>
            <tr>
                <th>Номер заказа</th>
                <th>Дата</th>
                <th>Пользователь</th>
                <th>Товары</th>
                <th>Текущий статус</th>
                <th>Изменить статус</th>
            </tr>
            </thead>
            <tbody>
            <#list orders as order>
                <tr>
                    <td>${order.orderNumber}</td>
                    <td>${order.orderDate?string("dd.MM.yyyy HH:mm")}</td>
                    <td>${order.user.email!''}</td>
                    <td>
                        <#list order.orderItems as item>
                            ${item.product.name} - ${item.product.price}₽ × ${item.quantity}<br>
                        </#list>
                    </td>
                    <td>
                        <#if order.statusCode == "pending">
                            В обработке
                        <#elseif order.statusCode == "completed">
                            Выполнен
                        <#elseif order.statusCode == "cancelled">
                            Отменён
                        <#else>
                            Неизвестно
                        </#if>
                    </td>
                    <td class="action-cell">
                        <select class="status-select" id="status-${order.orderNumber}">
                            <option value="pending">В обработке</option>
                            <option value="completed">Выполнен</option>
                            <option value="cancelled">Отменён</option>
                        </select>
                        <button class="submit-btn action-button-update" onclick="updateStatus('${order.orderNumber}')">Обновить</button>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </#if>
</div>

<script>
    window.csrf = {
        parameterName: '${_csrf.parameterName}',
        token: '${_csrf.token}',
        headerName: '${_csrf.headerName}'
    };
</script>
<script src="../js/adminOrder.js"></script>

</body>
</html>
