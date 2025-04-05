<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Каталог товаров</title>
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
            background-color: #2196F3; /* Синий цвет для кнопок */
            color: white;
            border: none;
            padding: 10px 20px; /* Увеличенный размер */
            border-radius: 5px;
            font-size: 1rem; /* Увеличенный размер текста */
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .header button:hover {
            background-color: #1976D2; /* Темно-синий цвет при наведении */
        }

        .content {
            display: flex;
            flex: 1;
        }

        .filter-section {
            flex: 1; /* Левая часть: чекбоксы */
            display: flex;
            flex-direction: column;
            align-items: flex-start;
            padding-right: 20px;
            border-right: 1px solid #ddd; /* Разделитель между левой и правой частью */
        }

        .filter-section label {
            margin-bottom: 10px;
            cursor: pointer;
        }

        .products-section {
            flex: 3; /* Правая часть: список продуктов */
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            overflow-y: auto; /* Прокрутка для продуктов */
            padding: 10px;
        }

        .product-item {
            flex: 0 0 calc(33.33% - 20px); /* Три товара в ряд */
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
            transition: background-color 0.3s, color 0.3s;
        }

        .product-item .buttons button:hover {
            background-color: #45a049; /* Темно-зеленый цвет при наведении */
        }

        .product-item .buttons button.remove {
            background-color: #f44336; /* Красный цвет для удаления */
        }

        .product-item .buttons button.remove:hover {
            background-color: #d32f2f; /* Темно-красный цвет при наведении */
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
    </style>
</head>
<body>

<div class="container">
    <!-- Заголовок "Каталог товаров" -->
    <div class="header">
        <!-- Кнопка "На главную" слева -->
        <div class="left-buttons">
            <button onclick="location.href='/main'">На главную</button>
        </div>

        <!-- Заголовок -->
        <h1>Каталог товаров</h1>

        <!-- Кнопка "Избранное" справа -->
        <div class="right-buttons">
            <button onclick="location.href='/favourites'">Избранное</button>
        </div>
    </div>

    <!-- Основной контент -->
    <div class="content">
        <!-- Левая часть: Фильтр -->
        <div class="filter-section">
            <c:forEach var="category" items="${sessionScope.categories.categories}">
                <label>
                    <input type="checkbox" value="${category.name}" class="category-filter"> ${category.name}
                </label><br/>
            </c:forEach>
        </div>

        <!-- Правая часть: Список продуктов -->
        <div class="products-section">
            <c:forEach var="product" items="${sessionScope.products.products}">

                <c:set var="categoryNames" value="" />
                <c:forEach var="category" items="${product.category}">
                    <c:set var="categoryNames" value="${categoryNames += ' ' += category.name}" />
                </c:forEach>

                <div class="product-item" data-categories="${categoryNames}">
                    <img src="data:image/jpeg;base64,${product.image}" alt="${product.name}">
                    <h3>${product.name}</h3>
                    <p>Цена: $${product.price}</p>
                    <h4 style="display:none;">Описание: ${product.description}</h4>
                    <div class="buttons">
                        <button onclick="showDetails(this)">Подробнее</button>
                        <form method="post" action="toggleProduct" style="display:inline;">
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
</div>

<!-- Модальное окно -->
<div id="modal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <h3 id="modal-title"></h3>
        <p id="modal-description"></p>
    </div>
</div>

<script>
    // Функция для отображения модального окна
    function showDetails(button) {
        const productItem = button.parentElement.parentElement;
        const title = productItem.querySelector('h3').innerText;
        const description = productItem.querySelector('h4').innerText;
        const price = productItem.querySelector('p').innerText;


        // Заполняем модальное окно данными
        document.getElementById('modal-title').innerText = title;
        document.getElementById('modal-description').innerText = price + "\n" + description;



        // Показываем модальное окно
        document.getElementById('modal').style.display = 'block';
    }

    // Функция для закрытия модального окна
    function closeModal() {
        document.getElementById('modal').style.display = 'none';
    }


    // Фильтр по категориям
    document.querySelectorAll('.category-filter').forEach(checkbox => {
        checkbox.addEventListener('change', function () {
            const selectedCategories = Array.from(document.querySelectorAll('.category-filter:checked')).map(cb => cb.value);
            const products = document.querySelectorAll('.product-item');

            products.forEach(product => {
                const productCategories = product.dataset.categories.split(' ');
                const isVisible = selectedCategories.length === 0 || selectedCategories.every(category => productCategories.includes(category));
                product.style.display = isVisible ? 'flex' : 'none';
            });
        });
    });
</script>

</body>
</html>