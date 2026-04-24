function showDetails(button) {
    const productItem = button.closest('.product-item');
    const title = productItem.querySelector('h3').innerText;
    const description = productItem.querySelector('h4').innerText;
    const price = productItem.querySelector('p').innerText;
    const quantity = productItem.querySelector('h5').innerText;

    document.getElementById('modal-title').innerText = title;
    document.getElementById('modal-description').innerText = price + "\n" + description + "\n" + quantity;

    document.getElementById('modal').style.display = 'block';
}

function closeModal() {
    document.getElementById('modal').style.display = 'none';
}

document.querySelectorAll('.category-filter').forEach(checkbox => {
    checkbox.addEventListener('change', function () {
        const selectedCategories = Array.from(document.querySelectorAll('.category-filter:checked')).map(cb => cb.value);
        const products = document.querySelectorAll('.product-item');

        products.forEach(product => {
            const productCategories = product.dataset.categories.split(',');
            const isVisible = selectedCategories.length === 0 || selectedCategories.every(category => productCategories.includes(category));
            product.style.display = isVisible ? 'flex' : 'none';
        });
    });
});

const csrfParameterName = window.csrf.parameterName;
const csrfToken = window.csrf.token;
const csrfHeaderName = window.csrf.headerName;

document.querySelectorAll('.toggle-favorite').forEach(button => {
    button.addEventListener('click', () => {
        const productId = button.dataset.id;
        const isFavorite = button.dataset.favorite === 'true';

        fetch('/web/toggleProduct', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                [csrfHeaderName]: csrfToken
            },
            body: new URLSearchParams({
                productId,
                isFavorite
            })
        }).then(response => {
            if (response.ok) {
                button.dataset.favorite = (!isFavorite).toString();
                button.textContent = isFavorite ? 'Добавить в избранное' : 'Удалить из избранного';
            }
        });
    });
});