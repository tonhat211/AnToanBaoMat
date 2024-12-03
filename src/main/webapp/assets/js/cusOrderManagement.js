function showMoreOrderDetail(event,element) {
    const parent = event.currentTarget.closest(element);
    parent.querySelector('.other-detail').classList.remove('hide');
    parent.querySelector('.btn-more-container').innerHTML = '<p class="btn-more" onclick="hideMoreOrderDetail(event,\'.order-item\');">Thu gọn <i class="bi bi-chevron-up"></i></p>';

}

function hideMoreOrderDetail(event,element) {
    const parent = event.currentTarget.closest(element);
    parent.querySelector('.other-detail').classList.add('hide');
    parent.querySelector('.btn-more-container').innerHTML = '<p class="btn-more" onclick="showMoreOrderDetail(event,\'.order-item\');">Xem thêm <i class="bi bi-chevron-down"></i></p>';

}