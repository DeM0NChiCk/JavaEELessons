<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
    <h1><%= "Hello World!" %></h1>
<form action="/calculator" method="get">
    <h1>Калькулятор</h1>

    <label for="value1">Первое значение:</label>
    <input type="text" id="value1" name="value1" required>

    <label for="value2">Второе значение:</label>
    <input type="text" id="value2" name="value2" required>

    <button type="submit">Посчитать</button>
</form>

<a href="hello-servlet">Hello Servlet</a>
</body>
</html>