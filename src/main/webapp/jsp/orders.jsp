<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Заказы</title>
    <style>
        body {
            background-image: url('../image/background.jpg'); /* Фоновое изображение */
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
            color: #333;
        }

        .container {
            background-color: rgba(255, 255, 255, 0.95); /* Полупрозрачный белый фон */
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
            width: 1200px;
            height: 650px;
            display: flex;
            flex-direction: column;
            align-items: center;
            overflow-y: auto; /* Прокрутка, если контент не помещается */
            position: relative; /* Для позиционирования кнопок */
        }

        h1 {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }

        .order-card {
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            padding: 20px;
            margin-bottom: 20px;
            width: 100%;
        }

        .order-card h2 {
            margin-bottom: 10px;
            font-size: 24px;
        }

        .order-card .products {
            margin-bottom: 10px;
        }

        .order-card .products p {
            margin: 5px 0;
            font-size: 16px;
        }

        .order-card .status {
            font-weight: bold;
        }

        .order-card .status.pending {
            color: #FF9800;
        }

        .order-card .status.completed {
            color: #4CAF50;
        }

        .order-card .status.cancelled {
            color: #f44336;
        }

        /* Кнопка "На главную" */
        .home-button {
            position: absolute;
            top: 20px;
            left: 20px;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s, transform 0.2s;
        }

        .home-button:hover {
            background-color: #45a049;
            transform: scale(1.05);
        }
    </style>
</head>
<body>

<div class="container">
    <!-- Кнопка "На главную" -->
    <button class="home-button" onclick="location.href='/main'">На главную</button>

    <h1>Заказы</h1>


    <c:forEach var="order" items="${sessionScope.orders}">
        <div class="order-card">
            <h2>Заказ #${order.id}</h2>
            <c:set var="productId" value="${order.productId}" />
            <div class="products">
                <p>${sessionScope.products.products.get(productId).name} (${sessionScope.products.products.get(productId).price})</p>
            </div>
            <div class="status ${order.statusCode}">
                <c:choose>
                    <c:when test="${order.statusCode == 'pending'}">В обработке</c:when>
                    <c:when test="${order.statusCode == 'completed'}">Выполнен</c:when>
                    <c:when test="${order.statusCode == 'cancelled'}">Отменен</c:when>
                    <c:otherwise>Неизвестно</c:otherwise>
                </c:choose>
            </div>
        </div>
    </c:forEach>
</div>

</body>
</html>