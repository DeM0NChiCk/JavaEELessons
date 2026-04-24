<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ооой! Ошибочка</title>
    <link rel="stylesheet" href="../css/error.css">
</head>
<body>

<div class="container">
    <h1>Ооой! Ошибочка:</h1>
    <img src="../image/error.jpg" alt="Error Image">
    <p class="error-message">${err?html}</p>
    <hr>
    <button onclick="location.href='/'">На главную</button>
</div>

</body>
</html>
