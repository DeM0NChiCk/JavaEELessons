<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Меню</title>
    <link rel="stylesheet" href="../css/main.css">
</head>
<body>

<div class="container">
    <div class="header">
        <h1>Добро пожаловать, ${user.username?html}!</h1>
    </div>

    <div class="columns">
        <div class="left-column">
            <a href="products">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
                    <path d="M3 13h2v-2H3v2zm0 4h2v-2H3v2zm0-8h2V7H3v2zm4 4h14v-2H7v2zm0 4h14v-2H7v2zM7 7v2h14V7H7z"/>
                </svg>
                Каталог
            </a>
            <a href="favourites">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
                    <path d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z"/>
                </svg>
                Избранное
            </a>
            <a href="orders">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
                    <path d="M13 12h7v1.5h-7zm0-2.5h7V11h-7zm0 5h7V16h-7zM21 4H3c-1.1 0-2 .9-2 2v13c0 1.1.9 2 2 2h18c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2zm0 15h-9V6h9v13z"/>
                </svg>
                Заказы
            </a>
        </div>

        <div class="right-column">
            <div class="news-header">Новости</div>
            <div class="news-list">
                <#list news as item>
                    <div class="news-item">
                        <h2>${item.title}</h2>
                        <p>${item.summary}</p>
                        <button onclick="toggleDetails(this)">Подробнее</button>
                        <div class="news-details">
                            <p><strong>Подробности:</strong> ${item.details}</p>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </div>

    <form action="/logout" method="post" class="logout-button">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <button type="submit">Logout</button>
    </form>
</div>

<script src="../js/main.js"></script>

</body>
</html>
