// const p$ = document.querySelector.bind(document);
// const p$$ = document.querySelectorAll.bind(document);
// const items = p$$('.form-check-input');
// const totalMoney = p$('.totalMoney');
// const totalProduct = p$('.totalProduct');
// const chooseAll = p$('.chooseAll');

function chooseAll(event,parent,element) {
    var isChecked = event.currentTarget.checked;
    const parentElement = event.currentTarget.closest(parent);
    const choices = parentElement.querySelector('.group').querySelectorAll(element);

    if(isChecked) {
        choices.forEach(choice => {
            if(!choice.checked) {
                choice.checked = true;
                choice.dispatchEvent(new Event('change'));
            }
        });
    } else {
        choices.forEach(choice => {
            choice.checked = false;
            choice.dispatchEvent(new Event('change'));
        });
    }
}

function setupCartTable(element) {
    const tableElement = document.querySelector(element);

    //     chon tat ca
    const btnCheckAll =   tableElement.querySelector('#check-all');
    if(btnCheckAll) btnCheckAll.addEventListener('change', (e) => {
        chooseAll(e,'.cart-form','.p-check');
    });
//     tang giam so luong
    const cartItems = tableElement.querySelectorAll('tbody tr');
    cartItems.forEach(item => {
    //     thiet lap gia tri cho temp money
        const inputTempMoney = item.querySelector('.temp-money');
        const priceUnit = item.querySelector('.price-unit');
        const qtyInput = item.querySelector('.qty-input');
        if(inputTempMoney && priceUnit) inputTempMoney.value = parseFloat(priceUnit.innerText.replace(/\./g, '')) * parseInt(qtyInput.value);


    //     gan su kien tang giam so luong
        const btnPlus = item.querySelector('.btn-plus');
        const btnMinus = item.querySelector('.btn-minus');
        if (btnPlus) btnPlus.addEventListener('click', (e) => {
            plusProduct(e);
        });
        if (btnMinus) btnMinus.addEventListener('click', (e) => {
            minusProduct(e);
        });

    //     gan su kien chon san pham
        const checkboxProduct = item.querySelector('.p-check');
        if(checkboxProduct) checkboxProduct.addEventListener('change', (e) => {
            checkProduct(e);
            updateCheckAll(cartItems,btnCheckAll);
        });

    //     gan su kien tien tam cua moi san pham thay doi
        if(inputTempMoney) inputTempMoney.addEventListener('change', (e) => {
            catchTempMoney(e);
        });

    });

}

function plusProduct(event) {
    const cartItem = event.currentTarget.closest('tr');
    const inputQty = cartItem.querySelector('.qty-input');
    let qty;
    //     goi ajax de cap nhat qty trong database
    updateQtyAjax(cartItem.getAttribute("data-value"), 'plus')
        .then(newQty => {
            qty = newQty;
            const inputTempMoney = cartItem.querySelector('.temp-money');
            inputQty.value = qty;

            // cap nhat temp money
            let priceUnit = cartItem.querySelector('.price-unit').innerText.replace(/\./g, '');
            inputTempMoney.value = parseFloat(priceUnit) * qty;
            inputTempMoney.dispatchEvent(new Event('change'));
        })
        .catch(error => {
            console.error("Có lỗi khi cập nhật số lượng:", error);
        });


}

function minusProduct(event) {
    const cartItem = event.currentTarget.closest('tr');
    const inputQty = cartItem.querySelector('.qty-input');
    let qty;
//     goi ajax de cap nhat qty trong database
    updateQtyAjax(cartItem.getAttribute("data-value"), 'minus')
        .then(newQty => {
            qty = newQty;
            const inputTempMoney = cartItem.querySelector('.temp-money');

            if (qty>0) {
                inputQty.value = qty;
                // Cập nhật temp money
                let priceUnit = cartItem.querySelector('.price-unit').innerText.replace(/\./g, '');
                inputTempMoney.value = parseFloat(priceUnit) * qty;
            } else {
                // Xóa khỏi giỏ hàng
                cartItem.style.display = 'none';
                const numOfCartE = document.querySelector('.num-of-cart');
                let newNum = parseInt(numOfCartE.innerText) - 1;
                numOfCartE.innerText = newNum;

                // Cập nhật temp money
                inputTempMoney.value=0;
            }

            inputTempMoney.dispatchEvent(new Event('change'));
        })
        .catch(error => {
            console.error("Có lỗi khi cập nhật số lượng:", error);
        });
}

function updateQtyAjax(cartID, operation,callback){
    return new Promise((resolve, reject) => {
        $.ajax({
            url: "cart?action=updateQty",
            type: "GET",
            dataType: "text",
            data: { cartID: cartID, operation: operation },
            success: function(response) {
                resolve(parseInt(response)); // Gọi resolve với giá trị mới
            },
            error: function(xhr, status, error) {
                reject(error); // Gọi reject khi có lỗi
            }
        });
    });
}
function checkProduct(event) {
    const cartItem = event.currentTarget.closest('tr');
    const inputTempMoney = cartItem.querySelector('.temp-money');
    const totalMoney = document.querySelector('#totalMoney');

    if(event.currentTarget.checked) {
        totalMoney.innerText = (parseFloat(totalMoney.innerText.replace(/\./g, '').replace(',', '.')) + parseFloat(inputTempMoney.value)).toLocaleString('vi-VN');
        // totalMoney.innerText = (parseFloat(totalMoney.innerText) + parseFloat(inputTempMoney.value));
    } else if(!event.currentTarget.checked) {
        totalMoney.innerText = (parseFloat(totalMoney.innerText.replace(/\./g, '').replace(',', '.')) - parseFloat(inputTempMoney.value)).toLocaleString('vi-VN');
        // totalMoney.innerText = (parseFloat(totalMoney.innerText) - parseFloat(inputTempMoney.value));
    }
}

function updateCheckAll(cartItems,btnCheckAll) {
    let isAll = true;
    for(let i=0;i<cartItems.length;i++) {
        if(!cartItems[i].querySelector('.p-check').checked) {
            isAll = false;
            break;
        }
    }
    btnCheckAll.checked = isAll;
}

function catchTempMoney(event) {
    const bodyElment = event.currentTarget.closest('.group');
    const cartItems = bodyElment.querySelectorAll('tr');
    let tempTotalMoney = 0;
    cartItems.forEach(item => {
        if (item.querySelector('.p-check').checked) {
            let tempMoney = parseFloat(item.querySelector('.temp-money').value);
            tempTotalMoney += tempMoney;
        }

    });

    const totalMoney = document.querySelector('#totalMoney');
    totalMoney.innerText = tempTotalMoney.toLocaleString('vi-VN');


}
