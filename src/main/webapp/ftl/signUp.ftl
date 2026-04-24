<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
    <link rel="stylesheet" href="../css/signUp.css">
</head>
<body>

<form action="signUp" method="post">
    <h1>Регистрация</h1>

    <label for="email">Электронная почта:</label>
    <input type="email" id="email" name="email" required>

    <label for="username">Имя пользователя:</label>
    <input type="text" id="username" name="username" required>

    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required>

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

    <button type="submit">Зарегистрироваться</button>

    <p>Уже есть аккаунт? <a href="signIn">Авторизация</a></p>
</form>

</body>
</html>
