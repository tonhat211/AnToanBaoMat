<%@ page import="model.ProductUnit" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="DAO.ProductUnitDAO" %>
<%@ page import="model.Constant" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.Duration" %>
<%@ page import="DAO.SaleProgramDAO" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="model.SaleProgram" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thietbididong.com</title>

    <!-- thu vien jquery   -->
    <script type="text/javascript" src="./assets/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="./assets/js/js_bootstrap4/bootstrap.min.js"></script>
    <script type="text/javascript" src="./assets/js/header.js"></script>
    <script type="text/javascript" src="./assets/js/smartphone.js"></script>
    <script type="text/javascript" src="./assets/js/toast.js"></script>
    <script type="text/javascript" src="./assets/js/index.js"></script>

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
    <link href="./assets/css/index.css" rel="stylesheet">

</head>
<body>
<%@ include file="header.jsp" %>
<script>
    console.log("index");
</script>
<%
    ArrayList<ProductUnit> saledOnlineProductUnits = (ArrayList<ProductUnit>) request.getAttribute("saledOnlineProductUnits");
    ArrayList<ProductUnit> suggestedProductUnits = (ArrayList<ProductUnit>) request.getAttribute("suggestedProductUnits");

%>
<script>
    console.log(<%=saledOnlineProductUnits.size()%>);
    console.log(<%=suggestedProductUnits.size()%>);
</script>

<div class="content-container">
    <div class="advertisement">
        <img src="./assets/img/advertisement/ad-iphone16.jpg" alt="">
    </div>
    <%
        if(saledOnlineProductUnits.size()>0) {
            LocalTime remaningTime = (LocalTime) request.getAttribute("remaningTime");
    %>
    <div class="sub-content online-promotion">
        <p class="bold-text-7" style="font-size: 25px">Khuyến mãi online</p>
        <div class="flex-roww" style="justify-content: center">
            <p class="promotion-time flex-roww" id="promotion-remaining-time">Chỉ còn
                <span class="time-component promotion-time_hour hour"></span> :
                <span class="time-component promotion-time_minute minute"></span> :
                <span class="time-component promotion-time_second second"></span>
            </p>
        </div>
        <script>
            countdown("#promotion-remaining-time",<%=remaningTime.getHour()%>,<%=remaningTime.getMinute()%>,<%=remaningTime.getSecond()%>);
        </script>
        <div class="promotion-product-list">
            <div class="grid__row">
                <%
                    for (ProductUnit p : saledOnlineProductUnits) {
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

    </div>
    <%
        }
    %>
    <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel" style="border-radius: 10px; overflow: hidden;margin-top: 20px;">
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
    <div class="sub-content cue-product">
        <p class="bold-text-7" style="font-size: 25px">Gợi ý cho bạn</p>
        <div class="promotion-product-list">
            <div class="grid__row">
                <%
                    for (ProductUnit p : suggestedProductUnits) {
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

    </div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>