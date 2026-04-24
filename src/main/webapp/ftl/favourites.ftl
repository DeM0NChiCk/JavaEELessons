<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Избранное</title>
    <link rel="stylesheet" href="../css/favourites.css">
</head>
<body>

<div class="container">
    <div class="header">
        <div class="left-buttons">
            <button onclick="location.href='main'">На главную</button>
        </div>
        <h1>Избранное</h1>
        <div class="right-buttons">
            <button onclick="location.href='products'">Каталог</button>
            <button class="cart" onclick="openCart()">Корзина</button>
        </div>
    </div>

    <div class="content">
        <#list favourites.products as product>
            <#assign categoryNames = "">
            <#if product.category??>
                <#list product.category as category>
                    <#assign categoryNames += " " + category.name>
                </#list>
            </#if>

            <div class="product-item" data-id="${product.id?html}" data-name="${product.name?html}" data-price="${product.price?html}">
                <img src="data:image/jpeg;base64,${product.image}" alt="${product.name?html}">
                <h3>${product.name?html}</h3>
                <p>Цена: ${product.price?html}₽</p>
                <div class="buttons">
                    <button onclick="buyProduct(this)">Купить</button>
                    <button class="toggle-favorite"
                            data-id="${product.id?html}"
                            data-favorite="true">
                        Удалить из избранного
                    </button>
                </div>
            </div>
        </#list>
    </div>
</div>

<div id="cart-modal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeCart()">&times;</span>
        <h2>Корзина</h2>
        <table class="cart-table">
            <thead>
            <tr>
                <th>Товар</th>
                <th>Цена</th>
                <th>Количество</th>
                <th>Удалить</th>
            </tr>
            </thead>
            <tbody id="cart-items"></tbody>
        </table>
        <div class="cart-buttons">
            <button onclick="placeOrder()">Оформить заказ</button>
        </div>
    </div>
</div>

<script>
    window.csrf = {
        parameterName: '${_csrf.parameterName}',
        token: '${_csrf.token}',
        headerName: '${_csrf.headerName}'
    };
</script>

<script src="../js/cart.js"></script>

</body>
</html>
