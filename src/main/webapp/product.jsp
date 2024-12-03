<%@ page import="java.util.ArrayList" %>
<%@ page import="model.ProductUnit" %>
<%@ page import="model.Brand" %>
<%@ page import="model.Constant" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <%
        String category = (String) request.getAttribute("category");
        if(category==null) category="Điện thoại";

    %>
    <title>Thietbididong.com - <%=category%></title>

    <!-- thu vien jquery   -->
    <script type="text/javascript" src="./assets/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="./assets/js/js_bootstrap4/bootstrap.min.js"></script>
    <script type="text/javascript" src="./assets/js/smartphone.js"></script>
    <script type="text/javascript" src="./assets/js/product.js"></script>

    <!-- Favicons -->

    <!-- Google Fonts -->
    <link href="https://fonts.gstatic.com" rel="preconnect">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

    <!--icon-->
    <link rel="stylesheet" href="./assets/fonts/fontawesome-free-6.4.0-web/css/all.min.css">
    <link rel="stylesheet" href="./assets/fonts/fonts-bootstrap/bootstrap-icons.min.css">

    <!-- Template Main CSS File -->
    <link rel="stylesheet" href="./assets/css/css_bootstrap4/bootstrap.min.css">

    <link href="./assets/css/base.css" rel="stylesheet">
    <link href="./assets/css/toast.css" rel="stylesheet">

    <!-- css tu them   -->
    <link href="./assets/css/smartphone.css" rel="stylesheet">

</head>
<%
    ArrayList<Brand> brands = (ArrayList<Brand>) request.getAttribute("brands");
    ArrayList<ProductUnit> productUnits = (ArrayList<ProductUnit>) request.getAttribute("productUnits");

%>
<body>
<%@ include file="header.jsp" %>
<div class="content-container">
    <div class="breadcrumb flex-roww" style="background-color: var(--base-bg-color);padding: 0;">
        <a href="#">Trang chủ </a><p style=";margin-bottom: 0;"> / <span class="total-phone" style="margin-bottom: 0">1000</span> điện thoại</p>
    </div>
    <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel" style="border-radius: 10px; overflow: hidden;">
        <ol class="carousel-indicators" style="bottom:-30px;">
            <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
        </ol>
        <div class="carousel-inner">
            <div class="carousel-item active">
                <div class="row">
                    <div class="col-6">
                        <img class="d-block w-100" src="./assets/img/banner/banner1.png" alt="First slide">
                    </div>
                    <div class="col-6">
                        <img class="d-block w-100" src="./assets/img/banner/banner2.png" alt="Second slide">
                    </div>
                </div>
            </div>
            <div class="carousel-item">
                <div class="row">
                    <div class="col-6">
                        <img class="d-block w-100" src="./assets/img/banner/banner3.jpg" alt="First slide">
                    </div>
                    <div class="col-6">
                        <img class="d-block w-100" src="./assets/img/banner/banner4.png" alt="Second slide">
                    </div>
                </div>
            </div>
            <div class="carousel-item">
                <div class="row">
                    <div class="col-6">
                        <img class="d-block w-100" src="./assets/img/banner/banner5.jpg" alt="First slide">
                    </div>
                    <div class="col-6">
                        <img class="d-block w-100" src="./assets/img/banner/banner6.png" alt="Second slide">
                    </div>
                </div>
            </div>

        </div>
        <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev" style="justify-content: start">
            <!--            <span class="carousel-control-prev-icon" aria-hidden="true"></span>-->
            <i class="bi bi-arrow-left-circle-fill" style="font-size: 40px; margin: 20px;"></i>
            <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next" style="justify-content: end">
            <!--            <span class="carousel-control-next-icon" aria-hidden="true"></span>-->
            <i class="bi bi-arrow-right-circle-fill" style="font-size: 40px; margin: 20px;"></i>
            <span class="sr-only">Next</span>
        </a>
    </div>
    <div class="main-content">
        <div class="filter-container flex-roww">
            <div class="filter-brand flex-roww active">
                <button class="btn filter-btn ml-0 all-brand" value="<%=Constant.ALL%>">Tất cả</button>
                <div class="btn-brand-container">
                    <%
                        for(Brand b : brands) {
                    %>
                            <a class="btn filter-btn-outline btn-brand" href="product?action=BYBRAND" data-value="<%=b.getId()%>"><%=b.getName()%></a>
                    <%
                        }
                    %>
                </div>
            </div>
            <div class="filter-options modal-filter-details">
                <button class="btn filter-btn mr-0 not-active" onclick="openModal('.modal-filter-details')"><i class="bi bi-funnel"></i> Lọc</button>
                <div class="filter-details modall">
                    <div class="os-filter">
                        <p>Hệ điều hành</p>
                        <div class="flex-roww group">
                            <button type="button" class="btn filter-btn-outline filter-btn btn-os">Android</button>
                            <button type="button" class="btn filter-btn-outline filter-btn btn-os">IOS</button>
                            <button type="button" class="btn filter-btn-outline filter-btn btn-os">Window</button>
                            <button type="button" class="btn filter-btn-outline filter-btn btn-os">Harmony OS</button>
                        </div>

                    </div>
                    <div class="seperate-horizontal-90" style="margin: 5px 0"></div>
                    <div class="price-filter group">
                        <p>Giá</p>
                        <div class="flex-roww grid__row group">
                            <button class="btn filter-btn btn-price-range" data-value-min="0" data-value-max="2">Dưới 2 triệu</button>
                            <button class="btn filter-btn btn-price-range" data-value-min="2" data-value-max="4">Từ 2 - 4 triệu</button>
                            <button class="btn filter-btn btn-price-range" data-value-min="4" data-value-max="7">Từ 4 - 7 triệu</button>
                            <button class="btn filter-btn btn-price-range" data-value-min="7" data-value-max="13">Từ 7 - 13 triệu</button>
                            <button class="btn filter-btn btn-price-range" data-value-min="13" data-value-max="20">Từ 13 - 20 triệu</button>
                            <button class="btn filter-btn btn-price-range" data-value-min="20" data-value-max="99">Trên 20 triệu</button>
                        </div>


                    </div>
                    <div class="flex-roww" style="justify-content: space-around; margin-top: 20px">
                        <button class="btn btn-outline-primary btn-cancel-filter" onclick="removeModal('.modal-filter-details')"><i class="bi bi-x-lg"></i> Hủy</button>
                        <a href="product?action=FILTER" class="btn btn-primary btn-filter" style="color: white;"><i class="bi bi-check-lg"></i> Lọc</a>

                    </div>
                </div>
                <div class="overlay" onclick="removeModal('.modal-filter-details')"></div>
            </div>
        </div>
        <div class="sort-container flex-roww group">
            <p>Sắp xếp theo: </p>
            <a href="product?action=SORT&orderby=1" data-value="1" class="sort-option active" onclick="changeCurrentPriceSort('default')">Nổi bật</a>
            <a href="product?action=SORT&orderby=2" data-value="2" class="sort-option" onclick="changeCurrentPriceSort('default')">Mới</a>
            <div class="parent-option">
                <p class="current-sort-price" style="color: var(--text-bold-color);margin: 0 10px;">Giá <i class="bi bi-chevron-down"></i></p>
                <div class="sub-option flex-coll">
                    <a href="product?action=SORT&orderby=4" data-value="4" class="sort-option" onclick="changeCurrentPriceSort('up')">Thấp đến cao <i class="bi bi-arrow-up-right"></i></a>
                    <div class="seperate-horizontal-90"></div>
                    <a href="product?action=SORT&orderby=5" data-value="5" class="sort-option" onclick="changeCurrentPriceSort('down')">Cao đến thấp <i class="bi bi-arrow-down-right"></i></a>
                </div>
            </div>

        </div>
        <div class="product-container">
            <div class="grid" style="min-width: unset">
                <div class="grid__row" id="product-list">

                <%
                    for (ProductUnit p : productUnits) {
                %>
<%--                    mot san pham --%>
                        <div class="grid-col-2_4 product-item-container">
                            <div class="product-item">
                                <a href="product?action=detail&id=<%=p.getProductID()%>">
                                    <div class="info flex-roww status">
                                        <%=p.getStatusItem()%>
                                    </div>
                                    <div class="img-container flex-roww" style="justify-content: center;">
                                        <img class="img-product" src="./assets/img/thumbnail/<%=p.getThumbnail()%>" alt="<%=p.getFullName()%>">
                                    </div>
                                    <div class="info promotion">
                                        <%--                                    <p class="promotion-item"><i class="bi bi-gift"></i><%=p.getPromotion()%></p>--%>
                                        <p class="promotion-item"><%=p.getSaleProgramItem()%></p>
                                    </div>
                                    <div class="info name">
                                        <p><%=p.getFullName()%></p>
                                    </div>
                                    <div class="info features flex-roww">
                                        <%--                                    <p class="feature-item">Man hinh 4k</p>--%>
                                        <%=p.getFeatureItems()%>
                                    </div>
                                    <div class="version flex-roww group">
                                        <%--                                    <div class="version-item active" onclick="getPriceOf(event);">--%>
                                        <%--                                        <p>64gb</p>--%>
                                        <%--                                    </div>--%>
                                        <%--                                    <div class="version-item" onclick="getPriceOf(event);">--%>
                                        <%--                                        <p>128gb</p>--%>
                                        <%--                                    </div>--%>
                                        <%=p.getStorageVersionItems()%>

                                    </div>
                                    <div class="price">
                                        <div><p class="cur-price"><%=p.getCurrentPrice()%> <span>VND</span></p> </div>
                                        <div class="flex-roww" style="justify-content: space-between;">
                                            <p class="init-price"><%=p.getInitialPrice()%> <span>VND</span></p>
                                            <div class="info rate">
                                                <p><i class="bi bi-star-fill"></i><span class="rate-figure"><%=p.getRate()%></span>(<span class="total-estimate"><%=p.getTotalComment()%></span>)</p>
                                            </div>
                                        </div>
                                    </div>
                                </a>


                            </div>

                        </div>
                <%
                    }
                %>



                </div>
            </div>
        </div>
    </div>
    <div class="flex-roww" style="margin: 10px 0;justify-content: center">
        <a class="btn btn-outline-primary more" onclick="moreProduct(event)">Xem thêm sản phẩm <i class="bi bi-chevron-down"></i></a>
    </div>
    <script>

    </script>
    <script>
        $('.carousel').carousel();
        radioElements('.version-item');
        radioElements('.sort-option');
        radioElements('.btn-price-range');
        checkElement('.group','.btn-os');

        checkElement('.group', '.btn-brand');
        const brandBtn = document.querySelectorAll('.btn-brand');
        brandBtn.forEach((el) => {
            el.addEventListener('click', (event) => {
                event.preventDefault();
                queryProductByBrands(event, '.btn-brand');
            });
        });

        const filterBtn = document.querySelector('.btn-filter');
        filterBtn.addEventListener('click', (event) => {
            event.preventDefault();
            console.log(filterBtn);
            queryProductByFilters(event);
        });
        const btnCancelFilter = document.querySelector('.btn-cancel-filter');
        btnCancelFilter.addEventListener('click', (event) => {
            cancelFilter(event);
        });


        //sort

        const btnSorts = document.querySelectorAll('.sort-option');
        console.log(btnSorts.length);
        btnSorts.forEach((btn) => {
            btn.addEventListener('click', (event) => {
                event.preventDefault();
                const orderby = btn.getAttribute("data-value");
                console.log("order by: " +orderby);
                // const filterBrandContainer =  document.querySelector('.filter-brand');
                const filterOptionContainer =  document.querySelector('.filter-options');
                if(filterOptionContainer.classList.contains('active')) {
                    event.currentTarget.href= "product?action=FILTER";
                    queryProductByFilters(event,orderby);
                } else {
                    event.currentTarget.href= "product?action=BYBRAND";
                    queryProductByBrands(event, '.btn-brand',orderby);
                }
            });
        });

        function changeCurrentPriceSort(newTitle) {
            const temp = document.querySelector('.current-sort-price');
            if (newTitle=='up') {
                temp.innerHTML = 'Thấp đến cao <i class="bi bi-arrow-up-right"></i>';
                temp.style.color = '#0b5ed7';
            } else if(newTitle=='down') {
                temp.innerHTML = 'Cao đến thấp <i class="bi bi-arrow-down-right"></i>';
                temp.style.color = '#0b5ed7';
            } else {
                temp.innerHTML = 'Giá <i class="bi bi-chevron-down"></i>';
                temp.style.color = 'var(--text-bold-color)';
            }

        }


        // more product
        function moreProduct(event) {
            event.preventDefault();
            var orderby = 1;
            var offset = document.querySelectorAll('.product-item-container').length;
            const orderbys = document.querySelectorAll(".sort-container .sort-option");
            for(var orderbyE of orderbys) {
                if(orderbyE.classList.contains("active")) {
                    orderby = orderbyE.getAttribute("data-value");
                    break;
                }
            }

            const filterOptionContainer =  document.querySelector('.filter-options');
            if(filterOptionContainer.classList.contains('active')) {
                event.currentTarget.href= "product?action=FILTER";
                queryProductByFilters(event,orderby,offset);
            } else {
                event.currentTarget.href= "product?action=BYBRAND";
                queryProductByBrands(event, '.btn-brand',orderby,offset);
            }
        }


    </script>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>