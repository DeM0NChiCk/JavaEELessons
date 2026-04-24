function toggleDetails(button) {
    const newsItem = button.parentElement;
    const details = newsItem.querySelector('.news-details');
    details.classList.toggle('active');
}