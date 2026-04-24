<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Аутентификация</title>
    <link rel="stylesheet" href="../css/signIn.css">
</head>
<body>

<form action="/login" method="post">
    <h1>Авторизация</h1>

    <label for="email">Электронная почта:</label>
    <input type="email" id="email" name="email" required>

    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required>

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

    <button type="submit">Авторизоваться</button>

    <p>Нет учетной записи? <a href="signUp">Регистрация</a></p>
</form>

</body>
</html>
