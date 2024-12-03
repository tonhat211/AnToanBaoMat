<%@ page import="java.util.ArrayList" %>
<%@ page import="model.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <%
        ProductUnit pu = (ProductUnit) request.getAttribute("pu");
    %>
    <title><%=pu.getFullName()%></title>

    <!-- thu vien jquery   -->
    <script type="text/javascript" src="./assets/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="./assets/js/js_bootstrap4/bootstrap.min.js"></script>
    <script type="text/javascript" src="./assets/js/smartphone.js"></script>
    <script type="text/javascript" src="./assets/js/rating-star.js"></script>

    <!-- Favicons -->

    <!-- Google Fonts -->
    <link href="https://fonts.gstatic.com" rel="preconnect">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">
    <!-- Template Main CSS File -->
    <link rel="stylesheet" href="./assets/css/css_bootstrap4/bootstrap.min.css">
    <!--icon-->
    <link rel="stylesheet" href="./assets/fonts/fontawesome-free-6.4.0-web/css/all.min.css">
    <link rel="stylesheet" href="./assets/fonts/fonts-bootstrap/bootstrap-icons.min.css">



<%--    <link href="./assets/css/base.css" rel="stylesheet">--%>
    <link href="./assets/css/toast.css" rel="stylesheet">



    <style>
        .sub-content {
            box-shadow: 0 0 4px 2px rgb(179 179 179 / 10%) !important;

        }

        .carousel-item {
            overflow: hidden !important;
        }

        .carousel-item img {
            min-height: 317px !important;
            max-height: 318px !important;
        }

        .carousel-nav {
            /*position: relative !important;*/
            /*margin-top: 15px !important;*/
            /*right: unset !important;*/
            /*left: unset !important;*/
            /*bottom: unset !important;*/
            /*width: 100% !important;*/
            /*margin-left: unset !important;*/
            /*margin-right: unset !important;*/
        }
    </style>
    <!-- css tu them   -->
    <link href="./assets/css/smartphone.css" rel="stylesheet">
    <link href="./assets/css/productDetail.css" rel="stylesheet">
    <link href="./assets/css/rating-star.css" rel="stylesheet">

</head>
<body>
<%@ include file="header.jsp" %>

<%
    ArrayList<Image> images = (ArrayList<Image>) request.getAttribute("images");
    SaleProgram saleProgram = (SaleProgram) request.getAttribute("saleProgram");
    ArrayList<Comment> comments = (ArrayList<Comment>) request.getAttribute("comments");
%>

<div class="content-container">
    <div class="breadcrumb flex-roww" style="background-color: var(--base-bg-color);padding: 0;">
        <a href="#">Điện thoại </a><p style=";margin-bottom: 0;"> / <span class="total-phone" style="margin-bottom: 0">Điện thoại Samsung</span></p>
    </div>

    <div class="productDetail-main-content">
        <div class="sub-content">
            <div class="p-title flex-roww">
                <h4 class="p-name"><%=pu.getFullName()%></h4>
                <div class="flex-roww status">
<%--                    <p class="status-item">Mẫu mới</p>--%>
                    <%=pu.getStatusItem()%>
                </div>
                <div class="rate">
                    <p><i class="bi bi-star-fill"></i><span class="rate-figure"><%=pu.getRate()%></span>(<span class="total-estimate"><%=pu.getTotalComment()%></span>)</p>
                </div>
            </div>
        </div>
        <div class="grid__row">
            <div class="grid-col-8">
                <div class="p-img-container sub-content">
                    <div id="carouselExampleIndicators" class="carousel slide img-presentation " data-ride="carousel">
                        <div class="carousel-inner">
<%--                            <div class="carousel-item active">--%>
<%--                                <img class="d-block w-100" src="./assets/img/product/samsung-galaxy-s23-fe-sld-1-1020x570.jpg" alt="First slide">--%>
<%--                            </div>--%>
<%--                            <div class="carousel-item">--%>
<%--                                <img class="d-block w-100" src="./assets/img/product/vi-vn-samsung-galaxy-s23-fe-5g-2.jpeg" alt="Second slide">--%>
<%--                            </div>--%>
<%--                            <%=ProductUnit.getCarouselItems(images)%>--%>
                            <div class="carousel-item active">
        <img class="d-block w-100" src="./assets/img/product/samsung-galaxy-s23-fe-sld-1-1020x570.jpg" alt="First slide">
    </div>

                        </div>
                        <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="sr-only">Next</span>
                        </a>
                        <ol class="carousel-nav carousel-indicators">
<%--                            <li data-target="#carouselExampleIndicators" data-slide-to="0" class="grid-col-1 active">--%>
<%--                                <img class="d-block w-100" src="./assets/img/product/samsung-galaxy-s23-fe-sld-1-1020x570.jpg" alt="First slide">--%>
<%--                            </li>--%>
<%--                            <li data-target="#carouselExampleIndicators" data-slide-to="1" class="grid-col-1">--%>
<%--                                <img class="d-block w-100" src="./assets/img/product/vi-vn-samsung-galaxy-s23-fe-5g-2.jpeg" alt="Second slide">--%>
<%--                            </li>--%>
                            <%=ProductUnit.getCarouselIndicators(images)%>
                        </ol>

                    </div>
                </div>

                <div class="sub-content">
                    <p class="bold-text-6">Thiết Bị Di Động cam kết</p>
                    <div class="pledge-container grid__row">
                        <div class="pledge-item grid-col-6 flex-roww">
                            <i class="bi bi-arrow-counterclockwise"></i>
                            <p>Hư gì đổi nấy <span class="bold-text-7">12 tháng</span> tại tất cả siêu thị toàn quốc (miễn phí tháng đầu)</p>
                        </div>
                        <div class="pledge-item grid-col-6 flex-roww">
                            <i class="bi bi-shield-check"></i>
                            <p>Bảo hành <span class="bold-text-7">chính hãng điện thoại 1 năm</span> tại các trung tâm bảo hành hãng</p>
                        </div>
                    </div>
                </div>

                <div class="sub-content">
                    <div class="flex-roww" style="justify-content: center;">
                        <div class="flex-roww group" style="justify-content: space-evenly;width: 60%">
                            <button class="btn btn-outline-primary active btn-show" onclick="getConfig()">Thông số kỹ thuật</button>
                            <button class="btn btn-outline-primary btn-show" onclick="getDes()">Bài viết đánh giá</button>
                        </div>
                    </div>
                    <div class="seperate-horizontal-90" style="margin: 20px 0;"></div>

                    <div id="config-show" class="p-des active">
                        <%=pu.getConfiguration()%>
                    </div>
                    <div id="des-show" class="p-des">
                        mo ta
                    </div>


                </div>

                <div class="sub-content">
                    <div class="rating-product">
                        <p class="bold-text-6">Đánh giá sản phẩm</p>
                    </div>
                    <div class="rating-main flex-roww" id="rating-presentation">
                        <p class="rating-figure bold-text-6">4.5</p>
                        <div class="rating">
                            <span class="star" data-value="1"><i class="bi bi-star"></i></span>
                            <span class="star" data-value="2"><i class="bi bi-star"></i></span>
                            <span class="star" data-value="3"><i class="bi bi-star"></i></span>
                            <span class="star" data-value="4"><i class="bi bi-star"></i></span>
                            <span class="star" data-value="5"><i class="bi bi-star"></i></span>
                        </div>
                        <p style="color: var(--bold-color);font-weight: 600"><span class="total-rating">72</span> đánh giá</p>
                    </div>
                    <div class="comment-container">
                        <div class="comment-item" id="user1">
                            <div class="comment-header flex-roww">
                                <p class="comment-owner">Tô Nhật</p>
                                <p class="buy-check"><i class="bi bi-check-circle"></i> Đã mua tại thietbididong</p>
                            </div>
                            <div class="rating">
                                <span class="star" data-value="1"><i class="bi bi-star"></i></span>
                                <span class="star" data-value="2"><i class="bi bi-star"></i></span>
                                <span class="star" data-value="3"><i class="bi bi-star"></i></span>
                                <span class="star" data-value="4"><i class="bi bi-star"></i></span>
                                <span class="star" data-value="5"><i class="bi bi-star"></i></span>
                            </div>
                            <script>
                                initRating('#user1',2);
                            </script>
                            <div class="comment-content">
                                <p>Mới mua mà thấy pin tụt mau quá, máy nóng, 100% để không mà cũng tụt xuống nhanh nửa</p>
                            </div>
                            <div class="seperate-horizontal-90" style="margin: 20px 0;"></div>
                        </div>
                        <div class="comment-item" id="user2">
                            <div class="comment-header flex-roww">
                                <p class="comment-owner">Tú Trinh</p>
                                <p class="buy-check"><i class="bi bi-check-circle"></i> Đã mua tại thietbididong</p>
                            </div>
                            <div class="rating">
                                <span class="star" data-value="1"><i class="bi bi-star"></i></span>
                                <span class="star" data-value="2"><i class="bi bi-star"></i></span>
                                <span class="star" data-value="3"><i class="bi bi-star"></i></span>
                                <span class="star" data-value="4"><i class="bi bi-star"></i></span>
                                <span class="star" data-value="5"><i class="bi bi-star"></i></span>
                            </div>
                            <script>
                                initRating('#user2',4);
                            </script>
                            <div class="comment-content">
                                <p>Mới mua 1 ngày. Lúc 23h45p còn 78% pin. Tắt màn hình đi ngủ tới 4h30 mở lên còn 58% Pin. Gần 5 giờ ko dùng đến mà tuột 20% Pina</p>
                            </div>
                            <div class="seperate-horizontal-90" style="margin: 20px 0;"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="grid-col-4" style="padding-left: 20px;">
                <div class="sub-content">
                    <div class="grid__row option-selector group">
                        <button class="grid-col-3_6 btn btn-outline-primary option-item active">6GB - 128GB</button>
                        <button class="grid-col-3_6 btn btn-outline-primary option-item">8GB - 128GB</button>
                        <button class="grid-col-3_6 btn btn-outline-primary option-item">8GB - 256GB</button>
                    </div>
                    <div class="grid__row color-selector group">
                        <button class="grid-col-3 btn btn-outline-primary option-item active">Xanh</button>
                        <button class="grid-col-3 btn btn-outline-primary option-item">Trắng</button>
                        <button class="grid-col-3 btn btn-outline-primary option-item">Đen</button>
                    </div>
                    <p style="margin-top: 20px">Giá tại: <span class="location-warehouse">Hồ Chí Minh</span></p>
                    <div class="price flex-roww" style="padding: 0">
                        <div><p class="cur-price" >6.000.000 <span>VND</span></p> </div>
                        <div><p class="init-price" style="padding-left: 10px">10.000.000 <span>VND</span></p> </div>
                    </div>
                    <div class="promotion-container">
                        <div class="promotion__header">
                            <p class="bold-text-6">Khuyến mãi trị giá <span class="promotion-number">500.000</span> VND</p>
                            <p style="font-size: 12px; color: var(--text-bold-color);">Thời gian áp dụng đến hết ngày <span class="promotion-end-date">11/11/2024</span></p>
                        </div>
                        <div class="promotion__content">
                            <p class="promotion__content-item"><span class="ordinal">1</span> Phiếu mua hàng 500,000đ</p>
                            <p class="promotion__content-item"><span class="ordinal">2</span> Tặng Bảo hiểm bảo hành mở rộng_12 tháng</p>
                            <p class="promotion__content-item"><span class="ordinal">3</span> Cơ hội trúng 10 xe máy Yamaha Sirius mỗi tháng, tổng giải thưởng lên đến 390 Triệu</p>
                            <p class="promotion__content-item"><span class="ordinal">4</span> 5
                                Cơ hội nhận ngay Phiếu mua hàng trị giá 1,000,000đ khi tham gia Trả góp Duyệt qua điện thoại, giao hàng tận nhà.</p>
                        </div>

                    </div>
                    <div class="grid__row" style="margin: 15px 0;">
                        <div class="grid-col-6" style="padding-right: 5px">
                            <button class="btn btn-outline-primary btn-add-to-cart"><i class="bi bi-cart-plus"></i>Thêm vào giỏ<span class="flex-coll"></span></button>
                        </div>
                        <div class="grid-col-6" style="padding-left: 5px">
                            <button class="btn btn-buy">Mua ngay</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="sub-content">
            <p class="bold-text-6">Sản phẩm thường mua cùng</p>
            <div class="addOn-product grid__row">
                <div class="grid-col-2_4 product-item-container">
                    <div class="product-item">
                        <div class="info flex-roww status">
                            <p class="status-item">Mẫu mới</p>
                        </div>
                        <div class="img-container flex-roww">
                            <img class="img-product" src="./assets/img/product/samsung-galaxy-s23-fe-mint-thumbnew-600x600.jpg" alt="">
                        </div>
                        <div class="info promotion">
                            <p class="promotion-item"><i class="bi bi-gift"></i> Đổi 4G tặng 480k</p>
                        </div>
                        <div class="info features flex-roww">
                            <p class="feature-item">Man hinh 4k</p>
                            <p class="feature-item">sac 5p day</p>
                        </div>
                        <div class="version flex-roww group">
                            <div class="version-item active">
                                <p>64gb</p>
                            </div>
                            <div class="version-item">
                                <p>128gb</p>
                            </div>
                        </div>
                        <div class="price">
                            <div><p class="cur-price">6.000.000 <span>VND</span></p> </div>
                            <div><p class="init-price">10.000.000 <span>VND</span></p> </div>
                        </div>
                        <div class="info rate">
                            <p><i class="bi bi-star-fill"></i><span class="rate-figure">4.9</span>(<span class="total-estimate">16</span>)</p>
                        </div>
                    </div>

                </div>
                <div class="grid-col-2_4 product-item-container">
                    <div class="product-item">
                        <div class="info flex-roww status">
                            <p class="status-item">Mẫu mới</p>
                        </div>
                        <div class="img-container flex-roww">
                            <img class="img-product" src="./assets/img/product/samsung-galaxy-s23-fe-mint-thumbnew-600x600.jpg" alt="">
                        </div>
                        <div class="info promotion">
                            <p class="promotion-item"><i class="bi bi-gift"></i> Đổi 4G tặng 480k</p>
                        </div>
                        <div class="info features flex-roww">
                            <p class="feature-item">Man hinh 4k</p>
                            <p class="feature-item">sac 5p day</p>
                        </div>
                        <div class="version flex-roww group">
                            <div class="version-item active">
                                <p>64gb</p>
                            </div>
                            <div class="version-item">
                                <p>128gb</p>
                            </div>
                        </div>
                        <div class="price">
                            <div><p class="cur-price">6.000.000 <span>VND</span></p> </div>
                            <div><p class="init-price">10.000.000 <span>VND</span></p> </div>
                        </div>
                        <div class="info rate">
                            <p><i class="bi bi-star-fill"></i><span class="rate-figure">4.9</span>(<span class="total-estimate">16</span>)</p>
                        </div>
                    </div>

                </div>
                <div class="grid-col-2_4 product-item-container">
                    <div class="product-item">
                        <div class="info flex-roww status">
                            <p class="status-item">Mẫu mới</p>
                        </div>
                        <div class="img-container flex-roww">
                            <img class="img-product" src="./assets/img/product/samsung-galaxy-s23-fe-mint-thumbnew-600x600.jpg" alt="">
                        </div>
                        <div class="info promotion">
                            <p class="promotion-item"><i class="bi bi-gift"></i> Đổi 4G tặng 480k</p>
                        </div>
                        <div class="info features flex-roww">
                            <p class="feature-item">Man hinh 4k</p>
                            <p class="feature-item">sac 5p day</p>
                        </div>
                        <div class="version flex-roww group">
                            <div class="version-item active">
                                <p>64gb</p>
                            </div>
                            <div class="version-item">
                                <p>128gb</p>
                            </div>
                        </div>
                        <div class="price">
                            <div><p class="cur-price">6.000.000 <span>VND</span></p> </div>
                            <div><p class="init-price">10.000.000 <span>VND</span></p> </div>
                        </div>
                        <div class="info rate">
                            <p><i class="bi bi-star-fill"></i><span class="rate-figure">4.9</span>(<span class="total-estimate">16</span>)</p>
                        </div>
                    </div>

                </div>
                <div class="grid-col-2_4 product-item-container">
                    <div class="product-item">
                        <div class="info flex-roww status">
                            <p class="status-item">Mẫu mới</p>
                        </div>
                        <div class="img-container flex-roww">
                            <img class="img-product" src="./assets/img/product/samsung-galaxy-s23-fe-mint-thumbnew-600x600.jpg" alt="">
                        </div>
                        <div class="info promotion">
                            <p class="promotion-item"><i class="bi bi-gift"></i> Đổi 4G tặng 480k</p>
                        </div>
                        <div class="info features flex-roww">
                            <p class="feature-item">Man hinh 4k</p>
                            <p class="feature-item">sac 5p day</p>
                        </div>
                        <div class="version flex-roww group">
                            <div class="version-item active">
                                <p>64gb</p>
                            </div>
                            <div class="version-item">
                                <p>128gb</p>
                            </div>
                        </div>
                        <div class="price">
                            <div><p class="cur-price">6.000.000 <span>VND</span></p> </div>
                            <div><p class="init-price">10.000.000 <span>VND</span></p> </div>
                        </div>
                        <div class="info rate">
                            <p><i class="bi bi-star-fill"></i><span class="rate-figure">4.9</span>(<span class="total-estimate">16</span>)</p>
                        </div>
                    </div>

                </div>
                <div class="grid-col-2_4 product-item-container">
                    <div class="product-item">
                        <div class="info flex-roww status">
                            <p class="status-item">Mẫu mới</p>
                        </div>
                        <div class="img-container flex-roww">
                            <img class="img-product" src="./assets/img/product/samsung-galaxy-s23-fe-mint-thumbnew-600x600.jpg" alt="">
                        </div>
                        <div class="info promotion">
                            <p class="promotion-item"><i class="bi bi-gift"></i> Đổi 4G tặng 480k</p>
                        </div>
                        <div class="info features flex-roww">
                            <p class="feature-item">Man hinh 4k</p>
                            <p class="feature-item">sac 5p day</p>
                        </div>
                        <div class="version flex-roww group">
                            <div class="version-item active">
                                <p>64gb</p>
                            </div>
                            <div class="version-item">
                                <p>128gb</p>
                            </div>
                        </div>
                        <div class="price">
                            <div><p class="cur-price">6.000.000 <span>VND</span></p> </div>
                            <div><p class="init-price">10.000.000 <span>VND</span></p> </div>
                        </div>
                        <div class="info rate">
                            <p><i class="bi bi-star-fill"></i><span class="rate-figure">4.9</span>(<span class="total-estimate">16</span>)</p>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <script>
        $('.carousel').carousel();
        radioElements('.version-item');
        radioElements('.sort-option');
        radioElements('.filter-btn');
        radioElements('.option-item');
        initRating('#rating-presentation',4.5);
        activeRating('#rating-presentation');

        radioElements('.btn-show');

    </script>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>