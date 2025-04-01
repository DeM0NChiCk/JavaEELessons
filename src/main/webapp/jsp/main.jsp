<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Home</title>
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

        .columns {
            display: flex;
            flex: 1; /* Колонки занимают всю оставшуюся высоту */
        }

        .left-column {
            flex: 0 0 20%; /* Левая колонка занимает 20% ширины */
            border-right: 2px solid #ddd; /* Разделительная полоска */
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            padding: 20px;
        }

        .left-column a {
            text-decoration: none;
            color: #333; /* Тёмный цвет для ссылок */
            font-size: 1.5rem; /* Увеличенный размер текста */
            transition: color 0.3s;
            display: flex;
            align-items: center; /* Выравнивание по центру */
            justify-content: center; /* Выравнивание по центру */
            height: 33.33%; /* Каждая ссылка занимает 1/3 высоты колонки */
            border-bottom: 1px solid #ddd; /* Разделительная линия между ссылками */
        }

        .left-column a:last-child {
            border-bottom: none; /* Убираем границу у последней ссылки */
        }

        .left-column a:hover {
            color: #45a049; /* Темно-зеленый цвет при наведении */
        }

        .left-column a svg {
            width: 32px; /* Увеличенный размер иконки */
            height: 32px;
            margin-right: 15px; /* Увеличенный отступ между иконкой и текстом */
            fill: #333; /* Цвет иконки */
            transition: fill 0.3s;
        }

        .left-column a:hover svg {
            fill: #45a049; /* Цвет иконки при наведении */
        }

        .right-column {
            flex: 1; /* Правая колонка занимает оставшиеся 80% ширины */
            padding: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .news-header {
            font-size: 1.5rem;
            color: #333; /* Тёмный цвет для заголовка "Новости" */
            margin-bottom: 20px;
        }

        .news-list {
            width: 100%;
            max-height: 400px; /* Максимальная высота для прокрутки */
            overflow-y: auto; /* Добавляем прокрутку */
            border: 1px solid #ddd;
            border-radius: 10px;
            padding: 10px;
        }

        .news-item {
            margin-bottom: 20px;
            padding: 10px;
            border-bottom: 1px solid #ddd;
        }

        .news-item:last-child {
            border-bottom: none; /* Убираем границу у последнего элемента */
        }

        .news-item h2 {
            font-size: 1.2rem;
            color: #333; /* Тёмный цвет для заголовков новостей */
            margin-bottom: 10px;
        }

        .news-item p {
            font-size: 1rem;
            color: #555; /* Тёмный цвет для текста новостей */
        }

        .news-item button {
            background-color: #4CAF50; /* Зеленый цвет для кнопки */
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 5px;
            font-size: 0.9rem;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .news-item button:hover {
            background-color: #45a049; /* Темно-зеленый цвет при наведении */
        }

        .news-details {
            display: none; /* Скрываем детали новости по умолчанию */
            margin-top: 10px;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #f9f9f9;
        }

        .news-details.active {
            display: block; /* Показываем детали новости, когда они активны */
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
        <h1>Добро пожаловать, ${sessionScope.user.username}!</h1>
    </div>

    <!-- Колонки -->
    <div class="columns">
        <!-- Левая колонка: 3 ссылки -->
        <div class="left-column">
            <a href="/products">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
                    <path d="M3 13h2v-2H3v2zm0 4h2v-2H3v2zm0-8h2V7H3v2zm4 4h14v-2H7v2zm0 4h14v-2H7v2zM7 7v2h14V7H7z"/>
                </svg>
                Каталог
            </a>
            <a href="/favourites">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
                    <path d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z"/>
                </svg>
                Избранное
            </a>
            <a href="/orders">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
                    <path d="M13 12h7v1.5h-7zm0-2.5h7V11h-7zm0 5h7V16h-7zM21 4H3c-1.1 0-2 .9-2 2v13c0 1.1.9 2 2 2h18c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2zm0 15h-9V6h9v13z"/>
                </svg>
                Заказы
            </a>
        </div>

        <!-- Правая колонка: новости -->
        <div class="right-column">
            <div class="news-header">Новости</div>
            <div class="news-list">
                <!-- Новость 1 -->
                <div class="news-item">
                    <h2>Поиск доставщиков</h2>
                    <p>Мы активно ищем новых доставщиков для улучшения качества наших услуг. Если вы заинтересованы, свяжитесь с нами!</p>
                    <button onclick="toggleDetails(this)">Подробнее</button>
                    <div class="news-details">
                        <p><strong>Подробности:</strong> Мы ищем надежных партнеров для доставки товаров. Требования: опыт работы, наличие транспорта, ответственность.</p>
                    </div>
                </div>

                <div class="news-item">
                    <h2>Вакансия: Курьер</h2>
                    <p>Мы открываем вакансию для курьера. Если вы хотите присоединиться к нашей команде, отправьте своё резюме на почту.</p>
                    <button onclick="toggleDetails(this)">Подробнее</button>
                    <div class="news-details">
                        <p><strong>Подробности:</strong> Требуется курьер для доставки заказов. График работы: гибкий. Зарплата: от 50 000 рублей.</p>
                    </div>
                </div>

                <div class="news-item">
                    <h2>Акция: Скидки до 0%</h2>
                    <p>Специальное предложение для наших клиентов! Скидки на все товары до 0%. Успейте воспользоваться!</p>
                    <button onclick="toggleDetails(this)">Подробнее</button>
                    <div class="news-details">
                        <p><strong>Подробности:</strong> Акция действует до конца месяца. Скидки на все категории товаров. Подробности уточняйте у менеджеров.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Кнопка Logout -->
    <form action="/logout" method="get" class="logout-button">
        <button type="submit">Logout</button>
    </form>
</div>

<script>
    function toggleDetails(button) {
        const newsItem = button.parentElement;
        const details = newsItem.querySelector('.news-details');
        details.classList.toggle('active');
    }
</script>

</body>
</html>