<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Заказы</title>
    <link rel="stylesheet" href="../css/orders.css">
</head>
<body>

<div class="container">
    <div class="header">
        <button class="home-button" onclick="location.href='main'">На главную</button>
        <h1>Ваши заказы</h1>
        <button class="home-button" onclick="location.href='favourites'">Избранное</button>
    </div>

    <#if orders?size == 0>
        <p>У вас пока нет заказов.</p>
    <#else>
        <#list orders as order>
            <div class="order-card">
                <h2>Заказ № - ${order.orderNumber}</h2>
                <p>Дата: ${order.orderDate?string("dd.MM.yyyy HH:mm")}</p>

                <div class="products">
                    <#list order.orderItems as item>
                        <div class="product">
                            <p>${item.product.name?html} — ${item.product.price?html}₽ × ${item.quantity}</p>
                        </div>
                    </#list>
                </div>

                <div class="status ${order.statusCode}">
                    <#if order.statusCode == "pending">
                        В обработке
                    <#elseif order.statusCode == "completed">
                        Выполнен
                    <#elseif order.statusCode == "cancelled">
                        Отменен
                    <#else>
                        Неизвестно
                    </#if>
                </div>
            </div>
        </#list>
    </#if>
</div>

</body>
</html>
