<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Каталог товаров</title>
    <link rel="stylesheet" href="../css/product.css">
</head>
<body>

<div class="container">
    <div class="header">
        <div class="left-buttons">
            <button onclick="location.href='main'">На главную</button>
        </div>
        <h1>Каталог товаров</h1>
        <div class="right-buttons">
            <button onclick="location.href='favourites'">Избранное</button>
        </div>
    </div>

    <div class="content">
        <div class="filter-section">
            <#list categories as category>
                <label>
                    <input type="checkbox" value="${category.name?html}" class="category-filter"> ${category.name?html}
                </label><br/>
            </#list>
        </div>

        <div class="products-section">
            <#list products as product>
                <#assign categoryNames = product.category?map(c -> c.name)?join(",")>

                <div class="product-item" data-categories="${categoryNames}">
                    <img src="data:image/jpeg;base64,${product.image}" alt="${product.name?html}">
                    <h3>${product.name?html}</h3>
                    <p>Цена: ${product.price?html}₽</p>
                    <h4 style="display:none;">Описание: ${product.description?html}</h4>
                    <h5 style="display:none;">Количество: ${product.quantity?html}</h5>
                    <div class="buttons">
                        <button onclick="showDetails(this)">Подробнее</button>
                        <button class="toggle-favorite"
                                data-id="${product.id?html}"
                                data-favorite="${product.favorite?string('true', 'false')}">
                            <#if product.favorite>
                                Удалить из избранного
                            <#else>
                                Добавить в избранное
                            </#if>
                        </button>
                    </div>
                </div>
            </#list>
        </div>
    </div>
</div>

<div id="modal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <h3 id="modal-title"></h3>
        <p id="modal-description"></p>
    </div>
</div>

<script>
    window.csrf = {
        parameterName: '${_csrf.parameterName}',
        token: '${_csrf.token}',
        headerName: '${_csrf.headerName}'
    };
</script>

<script src="../js/products.js"></script>

</body>
</html>
