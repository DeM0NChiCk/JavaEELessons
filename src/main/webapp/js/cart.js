let cartItems = [];

function buyProduct(button) {
    const product = button.closest('.product-item');
    const id = parseInt(product.dataset.id);
    const name = product.dataset.name;
    const price = parseFloat(product.dataset.price);

    const existing = cartItems.find(item => item.productId === id);
    if (existing) {
        existing.quantity += 1;
    } else {
        cartItems.push({ productId: id, title: name, price: price, quantity: 1 });
    }

    alert('Товар добавлен в корзину!');
}

function openCart() {
    const tbody = document.getElementById('cart-items');
    tbody.innerHTML = '';

    cartItems.forEach((item, index) => {
        const row = document.createElement('tr');
        row.innerHTML = `
                <td>${item.title}</td>
                <td>${item.price}₽</td>
                <td><input type="number" value="${item.quantity}" min="1" onchange="updateQuantity(${index}, this.value)"></td>
                <td><button onclick="removeFromCart(${index})">Удалить</button></td>
            `;
        tbody.appendChild(row);
    });

    document.getElementById('cart-modal').style.display = 'block';
}

function closeCart() {
    document.getElementById('cart-modal').style.display = 'none';
}

function updateQuantity(index, quantity) {
    cartItems[index].quantity = parseInt(quantity);
}

function removeFromCart(index) {
    cartItems.splice(index, 1);
    openCart();
}

const csrfParameterName = window.csrf.parameterName;
const csrfToken = window.csrf.token;
const csrfHeaderName = window.csrf.headerName;

function placeOrder() {
    if (cartItems.length === 0) {
        alert('Корзина пуста!');
        return;
    }

    const order = {
        orderDate: new Date().toISOString(),
        statusCode: 'NEW',
        items: cartItems.map(item => ({
            productId: item.productId,
            quantity: item.quantity
        }))
    };

    fetch('saveOrder', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeaderName]: csrfToken
        },
        body: JSON.stringify(order)
    })
        .then(response => {
            if (!response.ok) throw new Error('Ошибка оформления заказа');
            return response.json();
        })
        .then(data => {
            alert('Заказ оформлен!');
            cartItems = [];
            closeCart();
        })
        .catch(error => {
            alert('Произошла ошибка: ' + error.message);
        });
}

document.querySelectorAll('.toggle-favorite').forEach(button => {
    button.addEventListener('click', () => {
        const productId = button.dataset.id;

        fetch('/web/toggleFavorite', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                [window.csrf.headerName]: window.csrf.token
            },
            body: new URLSearchParams({
                productId,
                isFavorite: 'true'
            })
        }).then(response => {
            if (response.ok) {
                button.closest('.product-item').remove();
            }
        });
    });
});