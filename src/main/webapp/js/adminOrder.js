const csrfParameterName = window.csrf.parameterName;
const csrfToken = window.csrf.token;
const csrfHeaderName = window.csrf.headerName;

function updateStatus(orderNumber) {
    const selectedStatus = document.getElementById("status-" + orderNumber).value;

    fetch('orders/updateStatus', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeaderName]: csrfToken
        },
        body: JSON.stringify({
            orderNumber: orderNumber,
            statusCode: selectedStatus
        })
    }).then(response => {
        if (response.ok) {
            alert("Статус обновлён.");
            location.reload();
        } else {
            response.text().then(text => alert("Ошибка: " + text));
        }
    }).catch(error => alert("Ошибка: " + error));
}