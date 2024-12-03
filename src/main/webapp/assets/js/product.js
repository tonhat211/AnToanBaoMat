function checkElement(parent, element) {
    const btns = document.querySelectorAll(element);
    btns.forEach((btn) => {
        btn.addEventListener('click', (event) => {
            event.preventDefault();
            chooseFilter(event);
            checkAllBtn(parent, element);
        });
    });
}

function chooseFilter(event) {
    if(event.currentTarget.classList.contains('active')) {
        event.currentTarget.classList.remove('active');
    } else {
        event.currentTarget.classList.add('active');
    }
}

function queryProductByBrands(event, siblings, orderby=1,offset=0,amount=20) {
    event.preventDefault();
    const E = event.currentTarget;
    let url = E.href;
    var brandIDs = [];
    const btnBrands = document.querySelector('.btn-brand-container').querySelectorAll('.btn-brand');
    btnBrands.forEach((btn) => {
        if (btn.classList.contains('active')) {
            brandIDs.push(btn.getAttribute('data-value'));
        }
    });
    if(brandIDs.length==0) brandIDs.push(0);
    var condition = JSON.stringify(brandIDs);
    queryProduct(url, condition,orderby,offset,amount);

}


function checkAllBtn(parent, element) {
    const btns = document.querySelectorAll(element);
    let i = btns.length;
    btns.forEach((btn) => {
        if(btn.classList.contains('active')){
            i=i-1;
        }
    });
    if(i==0) {
        btns.forEach((btn) => {
            btn.classList.remove('active');
        });
    }

}

function queryProduct(urlin, condition, orderby=1,offset=0,amount=20) {
    console.log(condition);
    $.ajax({
        type: "GET",
        url: urlin,
        data: {"condition" : condition, "orderby" : orderby, "offset" : offset, "amount" : amount},
        success: function(data) {
            console.log("thanh cong: " + offset);
            if(offset!=0) {
                console.log("!=0");
                $('#product-list').html($('#product-list').html() + data);
            } else {
                $('#product-list').html(data);

            }

        },
        error: function(error) {
            console.error("Error during querying: ", error);
        }
    });
}

function queryProductByFilters(event, orderby=1,offset=0,amount=20) {
    event.preventDefault();
    const E = event.currentTarget;
    let url = E.href;
    var osList = [];
    const parentE = document.querySelector('.filter-details');
    const btnOses = parentE.querySelectorAll('.btn-os');
    btnOses.forEach((btnOs) => {
        if(btnOs.classList.contains('active')){
            osList.push(btnOs.innerText);
        }
    });
    var priceRange=[];
    const btnPrices = parentE.querySelectorAll('.btn-price-range');
    for (let item of btnPrices) {
        if(item.classList.contains('active')){
            minPrice = item.getAttribute('data-value-min');
            maxPrice = item.getAttribute('data-value-max');
            priceRange.push(minPrice);
            priceRange.push(maxPrice);
            break;
        }
    }
    if((osList.length==0) && (priceRange.length==0)) return;
    if(osList.length==0) osList.push("all");
    if(priceRange.length==0) priceRange.push("0");
    if(priceRange.length==0) priceRange.push("100");

    let conditionTemp = {
        osList: osList,
        priceRange: priceRange
    }
    var condition = JSON.stringify(conditionTemp);
    console.log(condition);
    queryProduct(url, condition,orderby,offset,amount);

}


function cancelFilter(event) {
    const parentE = event.currentTarget.closest('.filter-details');
    const btnOses = parentE.querySelectorAll('.btn-os');
    btnOses.forEach((btnOs) => {
        if(btnOs.classList.contains('active')){
            btnOs.classList.remove('active');
        }
    });
    var priceRange=0;
    const btnPrices = parentE.querySelectorAll('.btn-price-range');
    btnPrices.forEach((btnPrice) => {
        if(btnPrice.classList.contains('active')){
            btnPrice.classList.remove('active');
        }
    });
}