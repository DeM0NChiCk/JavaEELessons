<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-image: url('../image/background.jpg'); /* Фоновое изображение */
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
            color: #333;
        }

        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }

        form {
            background-color: rgba(255, 255, 255, 0.95); /* Полупрозрачный белый фон */
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
            width: 300px;
            text-align: left;
        }

        label {
            display: block;
            margin-bottom: 8px;
            color: #555;
            font-weight: bold;
        }

        input[type="email"],
        input[type="password"],
        input[type="text"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 1rem;
            box-sizing: border-box;
        }

        button {
            width: 100%;
            padding: 12px;
            background-color: #4CAF50; /* Зеленый цвет кнопки */
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 1rem;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        button:hover {
            background-color: #45a049; /* Темно-зеленый цвет при наведении */
        }

        p {
            text-align: center;
            margin-top: 15px;
            color: #555;
        }

        a {
            color: #2196F3; /* Синий цвет ссылки */
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline; /* Подчеркивание при наведении */
        }
    </style>
</head>
<body>

<form action="/admin/signIn" method="post">
    <h1>Вход для администратора</h1>

    <label for="email">Электронная почта:</label>
    <input type="email" id="email" name="email" required>

    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required>

    <label for="codeword">Кодовое слово:</label>
    <input type="text" id="codeword" name="codeword" required>

    <button type="submit">Войти</button>
</form>

</body>
</html>