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
    <script type="text/javascript" src="./assets/js/toast.js"></script>
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
    <link href="./assets/css/productDetail.css" rel="stylesheet">
    <link href="./assets/css/rating-star.css" rel="stylesheet">
    <style>
        .carousel-indicators {
            position: relative;
        }
        .carousel-nav li {
            width: 8.33%;
            height: unset;
        }



    </style>

</head>
<body>
<%@ include file="header.jsp" %>
<%
    ArrayList<Image> images = (ArrayList<Image>) request.getAttribute("images");
    SaleProgram saleProgram = (SaleProgram) request.getAttribute("saleProgram");
    ArrayList<Comment> comments = (ArrayList<Comment>) request.getAttribute("comments");
    ArrayList<ProductUnit> crossSells = (ArrayList<ProductUnit>) request.getAttribute("crossSells");
%>
<div class="content-container product-details-container">
    <div id="toast">

    </div>
    <div class="filter-options modal-filter-details">
        <div class="filter-details modall" style="width: 600px;height: 150px;">
            <div class="flex-roww" style="justify-content: center;margin:22px 0;font-size: 19px">
                <p>Bạn chưa đăng nhập, vui lòng đăng nhập để sử dụng giỏ hàng</p>
            </div>
            <div class="flex-roww" style="justify-content: space-around; margin-top: 20px">
                <button class="btn btn-outline-primary btn-cancel-filter" onclick="removeModal('.modal-filter-details')"><i class="bi bi-x-lg"></i> Hủy</button>
                <a href="login?action=require&page=product&pageAction=detail&pageProductID=<%=pu.getProductID()%>" class="btn btn-primary btn-filter" style="color: white;">Đăng nhập</a>

            </div>
        </div>
        <div class="overlay" onclick="removeModal('.modal-filter-details')"></div>
    </div>

    <div class="breadcrumb flex-roww" style="background-color: var(--base-bg-color);padding: 0;">
        <a href="#">Điện thoại </a><p style=";margin-bottom: 0;"> / <span class="total-phone" style="margin-bottom: 0">Điện thoại Samsung</span></p>
    </div>

    <div class="productDetail-main-content">
        <div class="sub-content">
            <div class="p-title flex-roww">
                <h4 class="p-name"><%=pu.getFullName()%></h4>
                <div class="flex-roww status">
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
                            <%=ProductUnit.getCarouselItems(images)%>

                        </div>
                        <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="sr-only">Next</span>
                        </a>
                        <ol class="carousel-indicators carousel-nav">
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
                        mô tả
                    </div>
                </div>

                <div class="sub-content">
                    <div class="rating-product">
                        <p class="bold-text-6">Đánh giá sản phẩm</p>
                    </div>
                    <div class="rating-main flex-roww" id="rating-presentation">
                        <p class="rating-figure bold-text-6"><%=pu.getRate()%></p>
                        <div class="rating">
                            <span class="star" data-value="1"><i class="bi bi-star"></i></span>
                            <span class="star" data-value="2"><i class="bi bi-star"></i></span>
                            <span class="star" data-value="3"><i class="bi bi-star"></i></span>
                            <span class="star" data-value="4"><i class="bi bi-star"></i></span>
                            <span class="star" data-value="5"><i class="bi bi-star"></i></span>
                        </div>
                        <p style="color: var(--bold-color);font-weight: 600"><span class="total-rating"><%=pu.getTotalComment()%></span> đánh giá</p>
                    </div>
                    <div class="comment-container">
                        <%
                            for(Comment cmt : comments) {
                        %>
                            <div class="comment-item" id="cmt<%=cmt.getId()%>">
                                <div class="comment-header flex-roww">
                                    <p class="comment-owner"><%=cmt.getUser().getName()%></p>
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
                                    initRating('#cmt<%=cmt.getId()%>',<%=cmt.getStar()%>);
                                </script>
                                <div class="comment-content">
                                    <p><%=cmt.getContent()%></p>
                                </div>
                                <div class="seperate-horizontal-90" style="margin: 20px 0;"></div>
                            </div>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>
            <div class="grid-col-4" style="padding-left: 20px;">
                <div> <span class="id-product-detail">12</span></div>
                <div class="sub-content">
                    <div class="grid__row option-selector group">
                        <%
                            ArrayList<String> storageVersions = pu.getStorageItems();
                            for(String s : storageVersions) {
                        %>
                            <button class="grid-col-3_6 btn btn-outline-primary option-item" <%=pu.checkExhausted(s)?"disabled":""%> onclick="checkVersion(event,'.color-selector',<%=pu.getProductID()%>,'storage');"><%=s%></button>
<%--                        <button class="grid-col-3_6 btn btn-outline-primary option-item">8GB - 128GB</button>--%>
<%--                        <button class="grid-col-3_6 btn btn-outline-primary option-item">8GB - 256GB</button>--%>
                        <%
                            }
                        %>
                        <script>
                            const storageVersionBtns = document.querySelectorAll('.option-selector .option-item');
                            for(let i = 0; i < storageVersionBtns.length; i++) {
                                if(storageVersionBtns[i].disabled == false) {
                                    storageVersionBtns[i].classList.add('active');
                                    break;
                                }
                            }

                        </script>
                    </div>
                    <div class="grid__row color-selector group">
                        <%
                            ArrayList<String> colorVersions = pu.getColorItems();
                            for(String s : colorVersions) {
                        %>
                            <button class="grid-col-3 btn btn-outline-primary option-item" <%=pu.checkExhausted(s)?"disabled":""%> onclick="checkVersion(event,'.option-selector',<%=pu.getProductID()%>,'color');"><%=s%></button>
<%--                        <button class="grid-col-3 btn btn-outline-primary option-item">Trắng</button>--%>
<%--                        <button class="grid-col-3 btn btn-outline-primary option-item">Đen</button>--%>
                        <%
                            }
                        %>
                        <script>
                            const colorVersionBtns = document.querySelectorAll('.color-selector .option-item');
                            for(let i = 0; i < colorVersionBtns.length; i++) {
                                console.log(colorVersionBtns[i].disabled);
                                if(colorVersionBtns[i].disabled == false) {
                                    colorVersionBtns[i].classList.add('active');
                                    break;
                                }
                            }

                            function disableVersion(element, names) {
                                console.log("names:" + names);
                                const versionBtns = document.querySelectorAll(element);
                                console.log("so luong btn: " +versionBtns.length);
                                versionBtns.forEach(versionBtn => {
                                    if(names.includes(versionBtn.innerText)) {
                                        versionBtns.disabled = true;
                                    }
                                });

                            }




                        </script>
                    </div>
                    <p style="margin-top: 20px">Giá tại: <span class="location-warehouse">Hồ Chí Minh</span></p>
                    <div class="price flex-roww" style="padding: 0">
                        <div><p class="cur-price"><span class="cur-price product-price">6.000.000</span><span> VND</span></p> </div>
                        <div><p class="init-price" style="padding-left: 10px">10.000.000 <span>VND</span></p> </div>
                    </div>
                    <script>
                        function updatePrice(price) {
                            document.querySelector('.cur-price').innerText = price;
                        }

                        function checkVersion(event,otherParent,productID,kind) {
                            let version = event.currentTarget.innerText;
                            let other;
                            const otherParentE = document.querySelector(otherParent);
                            const otherEs = otherParentE.querySelectorAll('.option-item');
                            for(let i=0;i<otherEs.length;i++) {
                                if(otherEs[i].classList.contains('active')) {
                                    other= otherEs[i].innerText;
                                }
                            }
                            checkVersionAjax(productID,kind, version, other);

                        }

                        function checkVersionAjax(productID,kind, version, other) {
                            $.ajax({
                                type: "GET",
                                url: "product?action=checkVersion",
                                data: {"productID" : productID,"kind": kind, "version" : version, "other" : other},
                                success: function(data) {
                                    $('#productDetail-response').html(data);
                                },
                                error: function(error) {
                                    console.error("Error during querying: ", error);
                                }
                            });
                        }


                    </script>
                    <div id="productDetail-response"></div>
                    <div class="promotion-container">
                        <%
                            if(saleProgram!=null) {
                        %>
                                <div class="promotion__header">
                                    <p class="bold-text-6"><%=saleProgram.getName()%></p>
                                    <p style="font-size: 12px; color: var(--text-bold-color);">Thời gian áp dụng đến hết <span class="promotion-end-date"><%=saleProgram.getEndTimeString()%></span></p>
                                </div>
                                <div class="promotion__content">
                                    <%
                                        String[] saleProgramContents = saleProgram.getContentItems();
                                        for(int i=0;i<saleProgramContents.length;i++) {
                                    %>
                                            <p class="promotion__content-item"><span class="ordinal"><%=i+1%></span> <%=saleProgramContents[i]%></p>
                                <%
                                    }
                                %>
                                </div>
                            <%
                                }
                            %>

                    </div>
                    <div class="grid__row" style="margin: 15px 0;">
                        <div class="grid-col-6" style="padding-right: 5px">
                            <button class="btn btn-outline-primary btn-add-to-cart" onclick="addToCart(<%=pu.getProductID()%>)"><i class="bi bi-cart-plus"></i>Thêm vào giỏ<span class="flex-coll"></span></button>
                        </div>
                        <div class="grid-col-6" style="padding-left: 5px">
                            <button class="btn btn-buy">Mua ngay</button>
                        </div>
                    </div>
                    <script>

                        function addToCart(productID) {
                            const colorEs = document.querySelectorAll('.color-selector .option-item');
                            const versionEs = document.querySelectorAll('.option-selector .option-item');
                            let color ="";
                            let version="";
                            colorEs.forEach(element => {
                                if(element.classList.contains('active')) {
                                    color = element.innerText;
                                }
                            });
                            versionEs.forEach(element => {
                                if(element.classList.contains('active')) {
                                    version = element.innerText;
                                }
                            });
                            console.log("add to cart: " + productID + "-"+ color + "-" + version);
                            addToCartAjax(productID, color, version);
                        }


                        function addToCartAjax(productID, color, version) {
                            $.ajax({
                                type: "GET",
                                url: "product?action=addToCart",
                                data: {"productID" : productID,"color": color, "version" : version},
                                success: function(data) {
                                    console.log(data);
                                    $('#productDetail-response').html(data);
                                },
                                error: function(error) {
                                    console.error("Error during querying: ", error);
                                }
                            });
                        }
                    </script>
                </div>
            </div>
        </div>
        <%
            if(!crossSells.isEmpty()) {
        %>
            <div class="sub-content">
                <p class="bold-text-6">Sản phẩm thường mua cùng</p>
                <div class="addOn-product grid__row">
                    <%
                        for (ProductUnit p : crossSells) {
                    %>
                    <%--                    mot san pham --%>
                        <div class="grid-col-2_4 product-item-container">
                        <div class="product-item">
                            <a href="<%=p.getUrlToDetail()%>">
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
        <%
            }
        %>
    </div>
    <script>
        $('.carousel').carousel();
        radioElements('.version-item');
        radioElements('.sort-option');
        radioElements('.filter-btn');
        radioElements('.option-item');
        initRating('#rating-presentation',<%=pu.getRate()%>);
        console.log(<%=pu.getRate()%>);
        activeRating('#rating-presentation');

        radioElements('.btn-show');

    </script>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>