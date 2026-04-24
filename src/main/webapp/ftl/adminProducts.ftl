<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Каталог продуктов</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/adminProducts.css">
</head>
<body>

<div class="container">
    <button class="home-button" onclick="location.href='main'">На главную</button>

    <h1>Каталог продуктов</h1>

    <table id="products-table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Название</th>
            <th>Описание</th>
            <th>Цена</th>
            <th>Категории</th>
            <th>Количество</th>
            <th>Изображение</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <#list products.products as product>
            <#assign categoryNames = "" />
            <#list product.category as category>
                <#if category?index != 0>
                    <#assign categoryNames = categoryNames + ", " />
                </#if>
                <#assign categoryNames = categoryNames + category.name />
            </#list>
            <tr>
                <td>${product.id?html}</td>
                <td>${product.name?html}</td>
                <td>${product.description?html}</td>
                <td>${product.price?html}₽</td>
                <td>${categoryNames?html}</td>
                <td>${product.quantity?html}</td>
                <td>
                    <div class="product-item">
                        <img src="data:image/jpeg;base64,${product.image}" alt="${product.name?html}">
                    </div>
                </td>
                <td>
                    <button class="edit-btn action-button-edit" data-id="${product.id}">Редактировать</button>
                    <button class="delete-btn action-button-delete" data-id="${product.id}">Удалить</button>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>

    <form class="add-product-form" id="add-product-form" action="products" method="post" enctype="multipart/form-data">
        <input type="text" name="name" placeholder="Название продукта" required>
        <input type="text" name="description" placeholder="Описание продукта" required>
        <input type="number" name="price" placeholder="Цена" step="0.01" required>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

        <div class="custom-dropdown">
            <button type="button" class="dropdown-toggle">Выберите категории</button>
            <div class="dropdown-menu">
                <label><input type="checkbox" name="categories" value="Электроника"> Электроника</label>
                <label><input type="checkbox" name="categories" value="Одежда"> Одежда</label>
                <label><input type="checkbox" name="categories" value="Продукты"> Продукты</label>
                <label><input type="checkbox" name="categories" value="Книги"> Книги</label>
                <label><input type="checkbox" name="categories" value="Спорт"> Спорт</label>
                <label><input type="checkbox" name="categories" value="Фрукты"> Фрукты</label>
            </div>
        </div>

        <input type="number" name="quantity" placeholder="Количество" required>
        <input type="file" name="image" accept="image/*">
        <button type="submit">Добавить продукт</button>
    </form>
</div>

<!-- Модальное окно для редактирования -->
<div id="edit-modal" class="modal" style="display: none;">
    <div class="modal-content">
        <span class="close" onclick="closeEditModal()">&times;</span>
        <h2>Редактировать продукт</h2>
        <form id="edit-product-form" enctype="multipart/form-data">
            <input type="hidden" name="id" id="edit-id">
            <input type="text" name="name" id="edit-name" placeholder="Название продукта" required>
            <input type="text" name="description" id="edit-description" placeholder="Описание продукта" required>
            <input type="number" name="price" id="edit-price" placeholder="Цена ₽" step="0.01" required>
            <input type="number" name="quantity" id="edit-quantity" placeholder="Количество" required>
            <input type="file" name="image" id="edit-image">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <button type="submit">Сохранить изменения</button>
        </form>
    </div>
</div>

<!-- Модальное окно подтверждения удаления -->
<div id="delete-modal" class="modal" style="display: none;">
    <div class="modal-content">
        <span class="close" onclick="closeDeleteModal()">&times;</span>
        <h2>Удалить продукт</h2>
        <p>Вы уверены, что хотите удалить этот продукт?</p>
        <button id="confirm-delete-btn">Подтвердить</button>
    </div>
</div>

<script>
    window.csrf = {
        parameterName: '${_csrf.parameterName}',
        token: '${_csrf.token}',
        headerName: '${_csrf.headerName}'
    };
</script>

<script src="../js/adminProducts.js"></script>

</body>
</html>
