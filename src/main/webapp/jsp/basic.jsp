<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Супермаркет</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            background-image: url('../image/background.jpg'); /* Путь к фоновому изображению */
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            color: #333;
        }

        .container {
            text-align: center;
            background-color: rgba(255, 255, 255, 0.95); /* Полупрозрачный фон для контента */
            padding: 20px 40px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
        }

        h1 {
            font-size: 2.5rem;
            margin-bottom: 20px;
            color: #444;
        }

        .buttons {
            margin-top: 20px;
        }

        .buttons button {
            padding: 10px 20px;
            margin: 0 10px;
            font-size: 1rem;
            cursor: pointer;
            border: none;
            border-radius: 5px;
            transition: background-color 0.3s, transform 0.2s;
        }

        .buttons button.register {
            background-color: #4CAF50;
            color: white;
        }

        .buttons button.login {
            background-color: #2196F3;
            color: white;
        }

        .buttons button:hover {
            background-color: #333;
            transform: scale(1.05);
        }

        .admin-button {
            position: absolute;
            top: 20px;
            right: 20px;
            background-color: #FF5722;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.2s;
        }

        .admin-button:hover {
            background-color: #E64A19;
            transform: scale(1.05);
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Добро пожаловать в наш супермаркет!</h1>
    <div class="buttons">
        <button class="register" onclick="location.href='/signUp'">Зарегистрироваться</button>
        <button class="login" onclick="location.href='/signIn'">Авторизоваться</button>
    </div>
</div>

<button class="admin-button" onclick="location.href='/admin/signIn'">Для администраторов</button>
</body>
</html>