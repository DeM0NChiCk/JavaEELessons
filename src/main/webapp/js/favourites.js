const csrfParameterName = window.csrf.parameterName;
const csrfToken = window.csrf.token;
const csrfHeaderName = window.csrf.headerName;

document.querySelectorAll('.toggle-favorite').forEach(button => {
    button.addEventListener('click', () => {
        const productId = button.dataset.id;

        fetch('/web/toggleFavorite', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                [csrfHeaderName]: csrfToken
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