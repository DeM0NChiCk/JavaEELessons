<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Home</title>
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
            height: 650px; /* Увеличена ширина контейнера */
            display: flex;
            flex-direction: column; /* Заголовок будет над колонками */
            position: relative; /* Для позиционирования кнопки */
        }

        .header {
            text-align: center;
            margin-bottom: 20px; /* Отступ между заголовком и колонками */
        }

        .buttons-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            flex: 1; /* Контейнер занимает всю оставшуюся высоту */
            position: relative; /* Для позиционирования кнопок */
        }

        .button {
            text-decoration: none;
            color: white; /* Белый цвет текста */
            background-color: #2196F3; /* Синий цвет для кнопок */
            font-size: 1.8rem; /* Увеличенный размер текста */
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px 40px; /* Увеличенный размер кнопки */
            border-radius: 10px; /* Закругленные углы */
            transition: background-color 0.3s, transform 0.3s;
            position: absolute;
            width: 300px; /* Фиксированная ширина кнопок */
            transform-origin: center; /* Точка увеличения: центр */
        }

        .button:hover {
            background-color: #1976D2; /* Темно-синий цвет при наведении */
            transform: scale(1.1); /* Увеличение кнопки при наведении */
        }

        .button svg {
            width: 40px; /* Увеличенный размер иконки */
            height: 40px;
            margin-right: 20px; /* Увеличенный отступ между иконкой и текстом */
            fill: white; /* Белый цвет иконки */
            transition: fill 0.3s;
        }

        .button:hover svg {
            fill: white; /* Белый цвет иконки при наведении */
        }

        .button-left-top {
            top: 20%; /* Смещение вниз */
            left: 15%; /* Смещение к центру */
        }

        .button-right-top {
            top: 20%; /* Смещение вниз */
            right: 15%; /* Смещение к центру */
        }

        .button-middle {
            top: 60%; /* Смещение вниз */
            left: calc(50% - 180px); /* Центрирование по горизонтали */
        }

        .logout-button {
            position: absolute;
            bottom: 20px; /* Отступ снизу */
            right: 20px; /* Отступ справа */
        }

        .logout-button button {
            background-color: #f44336; /* Красный цвет для кнопки */
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            font-size: 1rem;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.2s;
            transform-origin: bottom right; /* Точка увеличения: внизу справа */
        }

        .logout-button button:hover {
            background-color: #d32f2f; /* Темно-красный цвет при наведении */
            transform: scale(1.05); /* Увеличение кнопки при наведении */
        }
    </style>
</head>
<body>

<div class="container">
    <!-- Заголовок "Welcome" -->
    <div class="header">
        <h1>Добро пожаловать, Администратор!</h1>
    </div>

    <!-- Контейнер для кнопок -->
    <div class="buttons-container">
        <!-- Кнопка в левом верхнем углу -->
        <a href="/admin/products" class="button button-left-top">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
                <path d="M3 13h2v-2H3v2zm0 4h2v-2H3v2zm0-8h2V7H3v2zm4 4h14v-2H7v2zm0 4h14v-2H7v2zM7 7v2h14V7H7z"/>
            </svg>
            Каталог
        </a>

        <!-- Кнопка в правом верхнем углу -->
        <a href="/admin/news" class="button button-right-top">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
                <path d="M20 2H4c-1.1 0-2 .9-2 2v18l4-4h14c1.1 0 2-.9 2-2V4c0-1.1-.9-2-2-2z"/>
            </svg>
            Новости
        </a>

        <!-- Кнопка посередине под ними -->
        <a href="/admin/orders" class="button button-middle">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
                <path d="M13 12h7v1.5h-7zm0-2.5h7V11h-7zm0 5h7V16h-7zM21 4H3c-1.1 0-2 .9-2 2v13c0 1.1.9 2 2 2h18c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2zm0 15h-9V6h9v13z"/>
            </svg>
            Заказы
        </a>
    </div>

    <!-- Кнопка Logout -->
    <form action="/logout" method="get" class="logout-button">
        <button type="submit">Logout</button>
    </form>
</div>

</body>
</html>