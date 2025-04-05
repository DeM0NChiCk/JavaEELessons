<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Избранное</title>
    <style>
        body {
            background-image: url('../image/background.jpg'); /* Путь к фоновому изображению */
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
            color: #333; /* Основной цвет текста */
        }

        .container {
            background-color: rgba(255, 255, 255, 0.95); /* Полупрозрачный белый фон */
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
            width: 1200px;
            height: 650px; /* Фиксированные размеры контейнера */
            display: flex;
            flex-direction: column;
            position: relative; /* Для позиционирования кнопок */
        }

        .header {
            text-align: center;
            margin-bottom: 20px; /* Отступ между заголовком и фильтром */
            display: flex;
            justify-content: space-between; /* Распределяем кнопки по краям */
            align-items: center;
        }

        .header .left-buttons,
        .header .right-buttons {
            display: flex;
            gap: 10px;
        }

        .header button {
            background-color: #4CAF50; /* Зеленый цвет для кнопок */
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 5px;
            font-size: 0.8rem;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .header button:hover {
            background-color: #45a049; /* Темно-зеленый цвет при наведении */
        }

        .header button.cart {
            background-color: rgba(33, 150, 243, 0.7); /* Полупрозрачный синий цвет */
        }

        .header button.cart:hover {
            background-color: rgba(33, 150, 243, 0.9); /* Темно-синий цвет при наведении */
        }

        .content {
            display: flex;
            flex: 1;
            flex-wrap: wrap;
            gap: 20px;
            overflow-y: auto;
            padding: 10px;
        }

        .product-item {
            flex: 0 0 calc(25% - 20px); /* Четыре товара в ряд */
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 10px;
            padding: 15px;
            display: flex;
            flex-direction: column;
            align-items: center;
            text-align: center;
            box-sizing: border-box;
            height: calc(50% - 20px); /* 50% контейнера в высоту */
            justify-content: space-between; /* Распределяем содержимое по высоте */
        }

        .product-item img {
            max-width: 80px;
            max-height: 80px;
            margin-bottom: 10px;
        }

        .product-item h3 {
            font-size: 1rem;
            color: #333;
            margin-bottom: 10px;
        }

        .product-item p {
            font-size: 0.9rem;
            color: #555;
            margin-bottom: 10px;
        }

        .product-item .buttons {
            display: flex;
            gap: 10px;
            margin-top: auto; /* Кнопки всегда внизу ячейки */
        }

        .product-item .buttons button {
            background-color: #4CAF50; /* Зеленый цвет для кнопок */
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 5px;
            font-size: 0.8rem;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .product-item .buttons button:hover {
            background-color: #45a049; /* Темно-зеленый цвет при наведении */
        }

        /* Модальное окно */
        .modal {
            display: none; /* Скрыто по умолчанию */
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.5); /* Полупрозрачный фон */
        }

        .modal-content {
            background-color: white;
            margin: 15% auto; /* Центрирование по вертикали */
            padding: 20px;
            border: 1px solid #888;
            width: 50%; /* Ширина модального окна */
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
            position: relative;
        }

        .close {
            position: absolute;
            top: 10px;
            right: 15px;
            font-size: 24px;
            font-weight: bold;
            cursor: pointer;
        }

        .close:hover {
            color: #555;
        }

        /* Таблица корзины */
        .cart-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        .cart-table th,
        .cart-table td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: center;
        }

        .cart-table th {
            background-color: #f2f2f2;
        }

        .cart-table input {
            width: 50px;
            text-align: center;
        }

        .cart-table button {
            background-color: #f44336;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 5px;
            cursor: pointer;
        }

        .cart-table button:hover {
            background-color: #d32f2f;
        }

        .cart-buttons {
            display: flex;
            justify-content: flex-end;
            gap: 10px;
        }

        .cart-buttons button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
        }

        .cart-buttons button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="header">
        <div class="left-buttons">
            <button onclick="location.href='/main'">На главную</button>
        </div>

        <h1>Избранное</h1>

        <div class="right-buttons">
            <button onclick="location.href='/products'">Каталог</button>
        </div>
    </div>

    <div class="content">

        <c:forEach var="product" items="${sessionScope.favourites.products}">

            <c:set var="categoryNames" value=""/>
            <c:forEach var="category" items="${product.category}">
                <c:set var="categoryNames" value="${categoryNames += ' ' += category.name}"/>
            </c:forEach>

            <div class="product-item" data-categories="${categoryNames}">
                <img src="data:image/jpeg;base64,${product.image}" alt="${product.name}">
                <h3>${product.name}</h3>
                <p>Цена: $${product.price}</p>
                <h4 style="display:none;">Описание: ${product.description}</h4>
                <div class="buttons">
                    <form method="post" action="/saveOrder" >
                        <input type="hidden" name="productId" value="${product.id}">
                        <button type="submit">Купить</button>
                    </form>
                    <form method="post" action="/toggleFavorite" style="display:inline;">
                        <input type="hidden" name="productId" value="${product.id}">
                        <input type="hidden" name="isFavorite" id="isFavorite_${product.id}" value="${product.favorite}">
                        <button type="submit" data-favorite="${product.favorite}">
                            <c:choose>
                                <c:when test="${product.favorite}">
                                    Удалить из избранного
                                </c:when>
                                <c:otherwise>
                                    Добавить в избранное
                                </c:otherwise>
                            </c:choose>
                        </button>
                    </form>
                </div>
            </div>
        </c:forEach>
    </div>


</div>

</body>
</html>