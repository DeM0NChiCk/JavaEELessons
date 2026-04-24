document.querySelector('.dropdown-toggle').addEventListener('click', function () {
    const dropdownMenu = document.querySelector('.dropdown-menu');
    dropdownMenu.style.display = dropdownMenu.style.display === 'block' ? 'none' : 'block';
});

document.addEventListener('click', function (e) {
    if (!e.target.closest('.custom-dropdown')) {
        document.querySelector('.dropdown-menu').style.display = 'none';
    }
});

document.querySelectorAll('.dropdown-menu input[type="checkbox"]').forEach(checkbox => {
    checkbox.addEventListener('change', function () {
        const selectedCategories = Array.from(document.querySelectorAll('.dropdown-menu input[type="checkbox"]:checked'))
            .map(checkbox => checkbox.value)
            .join(',');

        document.querySelector('.dropdown-toggle').textContent = selectedCategories || 'Выберите категории';
    });
});

const csrfParameterName = window.csrf.parameterName;
const csrfToken = window.csrf.token;
const csrfHeaderName = window.csrf.headerName;

document.addEventListener('DOMContentLoaded', () => {
    let currentProductId = null;

    const cleanNumber = str => str.replace(/\s/g, '').replace(',', '.');

    document.querySelectorAll('.edit-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            const row = btn.closest('tr');
            currentProductId = btn.dataset.id;

            document.getElementById('edit-id').value = currentProductId;
            document.getElementById('edit-name').value = row.children[1].innerText;
            document.getElementById('edit-description').value = row.children[2].innerText;

            document.getElementById('edit-price').value = parseFloat(cleanNumber(row.children[3].innerText));
            document.getElementById('edit-quantity').value = parseInt(cleanNumber(row.children[5].innerText));

            document.getElementById('edit-modal').style.display = 'block';
        });
    });

    document.getElementById('edit-product-form').addEventListener('submit', function (e) {
        e.preventDefault();

        const formData = new FormData(this);

        fetch(`/admin/products/${formData.get("id")}`, {
            method: 'PUT',
            body: formData,
            headers: {
                [csrfHeaderName]: csrfToken
            }
        })
            .then(async response => {
                if (response.ok) {
                    alert("Товар успешно обновлён.");
                    location.reload();
                } else {
                    const message = await response.text();
                    alert("Ошибка при обновлении: " + message);
                }
            })
            .catch(error => {
                alert("Ошибка сети: " + error.message);
            });
    });

    document.querySelectorAll('.delete-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            currentProductId = btn.dataset.id;
            document.getElementById('delete-modal').style.display = 'block';
        });
    });

    document.getElementById('confirm-delete-btn').addEventListener('click', () => {
        fetch(`/admin/products/${currentProductId}`, {
            method: 'DELETE',
            headers: {
                [csrfHeaderName]: csrfToken
            }
        })
            .then(async response => {
                if (response.ok) {
                    alert("Товар успешно удалён.");
                    location.reload();
                } else {
                    const message = await response.text();
                    alert("Ошибка при удалении: " + message);
                }
            })
            .catch(error => {
                alert("Ошибка сети: " + error.message);
            });
    });
});


function closeEditModal() {
    document.getElementById('edit-modal').style.display = 'none';
}

function closeDeleteModal() {
    document.getElementById('delete-modal').style.display = 'none';
}