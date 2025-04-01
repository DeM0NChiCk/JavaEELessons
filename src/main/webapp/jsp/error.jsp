<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Ооой! Ошибочка</title>
  <style>
    body {
      background-image: url('../image/background.jpg'); /* Путь к фоновому изображению */
      background-size: cover;
      background-position: center;
      background-repeat: no-repeat;
      font-family: Arial, sans-serif;
      margin: 0;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      min-height: 100vh;
      color: #333;
    }

    .container {
      max-width: 600px;
      background-color: rgba(255, 255, 255, 0.95); /* Полупрозрачный белый фон */
      border-radius: 15px;
      padding: 30px;
      text-align: center;
      box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
    }

    h1 {
      font-size: 2.5rem;
      margin-bottom: 20px;
      color: #444;
    }

    .error-message {
      text-align: center;
      margin-top: 15px;
      color: #555; /* Серый цвет для текста */
      font-size: 1.2em;
    }

    img {
      max-height: 300px;
      border-radius: 10px;
      margin-bottom: 20px;
    }

    button {
      width: 100%;
      padding: 12px;
      background-color: #4CAF50;
      color: white;
      border: none;
      border-radius: 5px;
      font-size: 1rem;
      cursor: pointer;
      transition: background-color 0.3s;
    }

    button:hover {
      background-color: #45a049;
    }

    hr {
      border: 0;
      height: 1px;
      background: #4CAF50; /* Зеленый цвет для разделительной линии */
      margin: 20px 0;
    }
  </style>
</head>
<body>

<div class="container">
  <h1>Ооой! Ошибочка:</h1>
  <img src="../image/error.jpg" alt="Error Image">
  <p class="error-message"><%=request.getParameter("err")%></p>
  <hr>
  <button onclick="location.href='/'">На главную</button>
</div>

</body>
</html>