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
    <script type="text/javascript" src="./assets/js/cusOrderManagement.js"></script>


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
<%--    <link href="./assets/css/smartphone.css" rel="stylesheet">--%>
    <link href="./assets/css/cusOrderManagement.css" rel="stylesheet">

</head>
<body>
<%@ include file="header.jsp" %>
<div class="content-container">
    <h3 style="text-align: center">Lịch sử mua hàng</h3>
    <div class="flex-roww" style="align-items: end;">
        <div class="grid-col-8">
            <div class="sub-content">
                <ul class="tab-menu flex-roww" style="align-items: end;">
                    <!--            <li class="order-menu-item active"><a href="cus-order?action=all">Tất cả</a></li>-->
                    <li class="active"><a href="">Chờ xác nhận</a></li>
                    <li class=""><a href="">Chuẩn bị</a></li>
                    <li class=""><a href="">Vận chuyển</a></li>
                    <li class=""><a href="">Hoàn thành</a></li>
                    <li class=""><a href="">Hủy</a></li>
                    <li class=""><a href="">Tất cả</a></li>
                </ul>
            </div>
        </div>
        <div class="grid-col-4">
            <p style="text-align: center;">Chi tiết đơn hàng</p>
        </div>
    </div>
    <div class="flex-roww" style="align-items: start;">
        <div class="grid-col-8" style="padding-right: 5px;">
            <div class="order-container" style="margin-bottom: 20px">
                <div class="order-item sub-content">
                    <div class="flex-roww" style="justify-content: space-between;">
                        <p>Mã đơn hàng: <span class="orderID">1</span> </p>
                        <p class="finish-color orderStatus <%=item.getKey().getColorByStatus()%>">Hoàn thành</p>

                        <!--                        <span class="orderStatusNumber" style="display: none">trang thai theo so (ẩn)</span>-->
                        <!--                        <span class="orderReceiver" style="display: none">nguoi nhan</span>-->
                        <!--                        <span class="orderReceiverPhone" style="display: none">so dien thoai</span>-->
                        <!--                        <span class="orderAddress" style="display: none">dia chi</span>-->
                    </div>
                    <div class="seperate-horizontal-100"></div>
                    <div class="order-detail grid__row" onclick="showDetailsOrder.call(this)">
                        <div class="grid__row grid-col-9" style="justify-content: left;">
                            <div class="grid-col-3" style="padding: 5px;">
                                <img src="../assets/img/product/samsung-galaxy-s23-fe-sld-1-1020x570.jpg" alt="" style="width: 100%;">
                            </div>
                            <div class="grid-col-9 flex-coll" style="padding: 5px;align-items: start;justify-content: space-between;">
                                <p class="product-name">Samsung galaxy s23</p>
                                <p><span class="product-price">1.000.000</span> (VND)</p>

                            </div>
                        </div>
                        <div class="grid-col-3" style="text-align: end;">
                            <p>Số lượng: <span class="product-qty">2</span></p>
                        </div>
                    </div>
                    <div class="other-detail hide">
                        <div class="order-detail grid__row odd" onclick="showDetailsOrder.call(this)">
                            <div class="grid__row grid-col-9" style="justify-content: left;">
                                <div class="grid-col-3" style="padding: 5px;border: 1px solid black;">
                                    <img src="../assets/img/product/samsung-galaxy-s23-fe-sld-1-1020x570.jpg" alt="" style="width: 100%;">
                                </div>
                                <div class="grid-col-9 flex-coll" style="padding: 5px;align-items: start;justify-content: space-between;">
                                    <p class="product-name">Samsung galaxy s23</p>
                                    <p><span class="product-price">1.000.000</span> (VND)</p>

                                </div>
                            </div>
                            <div class="grid-col-3" style="text-align: end;">
                                <p>Số lượng: <span class="product-qty">2</span></p>
                            </div>
                        </div>
                        <div class="order-detail grid__row" onclick="showDetailsOrder.call(this)">
                            <div class="grid__row grid-col-9" style="justify-content: left;">
                                <div class="grid-col-3" style="padding: 5px;border: 1px solid black;">
                                    <img src="../assets/img/product/samsung-galaxy-s23-fe-sld-1-1020x570.jpg" alt="" style="width: 100%;">
                                </div>
                                <div class="grid-col-9 flex-coll" style="padding: 5px;align-items: start;justify-content: space-between;">
                                    <p class="product-name">Samsung galaxy s23</p>
                                    <p><span class="product-price">1.000.000</span> (VND)</p>

                                </div>
                            </div>
                            <div class="grid-col-3" style="text-align: end;">
                                <p>Số lượng: <span class="product-qty">2</span></p>
                            </div>
                        </div>
                    </div>
                    <div class="btn-more-container">
                        <p class="btn-more" onclick="showMoreOrderDetail(event,'.order-item');">Xem thêm <i class="bi bi-chevron-down"></i></p>
                    </div>
                    <!--                <p class="btn-more">Thu gọn <i class="bi bi-chevron-up"></i></p>-->

                    <div class="seperate-horizontal-100"></div>
                    <div class="flex-roww" style="justify-content: space-between;">
                        <div class="flex-coll" style="align-items: start; justify-content: space-between;">
                            <p style="margin-bottom: 10px;">Ngày đặt: <span class="order-dateSet">10/10/2024 10:00</span></p>
                            <p>Ngày hoàn thành: <span class="order-dateFinish">10/10/2024 10:30</span></p>
                        </div>
                        <div class="flex-coll" style="align-items: end; justify-content: space-between;">
                            <p style="margin-bottom: 10px;">Tổng tiền: <span class="order-Price">4.000.000</span> (VND)</p>
                            <button type="button" class="btn btn-third" >Hủy</button>
                        </div>
                    </div>
                    <a href="">Xem chi tiết</a>
                </div>
                <div class="order-item sub-content">
                    <div class="flex-roww" style="justify-content: space-between;">
                        <p>Mã đơn hàng: <span class="orderID">1</span> </p>
                        <p class="finish-color orderStatus <%=item.getKey().getColorByStatus()%>">Hoàn thành</p>

                        <!--                        <span class="orderStatusNumber" style="display: none">trang thai theo so (ẩn)</span>-->
                        <!--                        <span class="orderReceiver" style="display: none">nguoi nhan</span>-->
                        <!--                        <span class="orderReceiverPhone" style="display: none">so dien thoai</span>-->
                        <!--                        <span class="orderAddress" style="display: none">dia chi</span>-->
                    </div>
                    <div class="seperate-horizontal-100"></div>
                    <div class="order-detail grid__row" onclick="showDetailsOrder.call(this)">
                        <div class="grid__row grid-col-9" style="justify-content: left;">
                            <div class="grid-col-3" style="padding: 5px;border: 1px solid black;">
                                <img src="../assets/img/product/samsung-galaxy-s23-fe-sld-1-1020x570.jpg" alt="" style="width: 100%;">
                            </div>
                            <div class="grid-col-9 flex-coll" style="padding: 5px;align-items: start;justify-content: space-between;">
                                <p class="product-name">Samsung galaxy s23</p>
                                <p><span class="product-price">1.000.000</span> (VND)</p>

                            </div>
                        </div>
                        <div class="grid-col-3" style="text-align: end;">
                            <p>Số lượng: <span class="product-qty">2</span></p>
                        </div>
                    </div>
                    <div class="other-detail hide">
                        <div class="order-detail grid__row odd" onclick="showDetailsOrder.call(this)">
                            <div class="grid__row grid-col-9" style="justify-content: left;">
                                <div class="grid-col-3" style="padding: 5px;border: 1px solid black;">
                                    <img src="../assets/img/product/samsung-galaxy-s23-fe-sld-1-1020x570.jpg" alt="" style="width: 100%;">
                                </div>
                                <div class="grid-col-9 flex-coll" style="padding: 5px;align-items: start;justify-content: space-between;">
                                    <p class="product-name">Samsung galaxy s23</p>
                                    <p><span class="product-price">1.000.000</span> (VND)</p>

                                </div>
                            </div>
                            <div class="grid-col-3" style="text-align: end;">
                                <p>Số lượng: <span class="product-qty">2</span></p>
                            </div>
                        </div>
                        <div class="order-detail grid__row" onclick="showDetailsOrder.call(this)">
                            <div class="grid__row grid-col-9" style="justify-content: left;">
                                <div class="grid-col-3" style="padding: 5px;border: 1px solid black;">
                                    <img src="../assets/img/product/samsung-galaxy-s23-fe-sld-1-1020x570.jpg" alt="" style="width: 100%;">
                                </div>
                                <div class="grid-col-9 flex-coll" style="padding: 5px;align-items: start;justify-content: space-between;">
                                    <p class="product-name">Samsung galaxy s23</p>
                                    <p><span class="product-price">1.000.000</span> (VND)</p>

                                </div>
                            </div>
                            <div class="grid-col-3" style="text-align: end;">
                                <p>Số lượng: <span class="product-qty">2</span></p>
                            </div>
                        </div>
                    </div>
                    <div class="btn-more-container">
                        <p class="btn-more" onclick="showMoreOrderDetail(event,'.order-item');">Xem thêm <i class="bi bi-chevron-down"></i></p>
                    </div>
                    <!--                <p class="btn-more">Thu gọn <i class="bi bi-chevron-up"></i></p>-->

                    <div class="seperate-horizontal-100"></div>
                    <div class="flex-roww" style="justify-content: space-between;">
                        <div class="flex-coll" style="align-items: start; justify-content: space-between;">
                            <p style="margin-bottom: 10px;">Ngày đặt: <span class="order-dateSet">10/10/2024 10:00</span></p>
                            <p>Ngày hoàn thành: <span class="order-dateFinish">10/10/2024 10:30</span></p>
                        </div>
                        <div class="flex-coll" style="align-items: end; justify-content: space-between;">
                            <p style="margin-bottom: 10px;">Tổng tiền: <span class="order-Price">4.000.000</span> (VND)</p>
                            <button type="button" class="btn btn-third" >Hủy</button>
                        </div>
                    </div>
                    <a href="">Xem chi tiết</a>
                </div>
                <div class="order-item sub-content">
                    <div class="flex-roww" style="justify-content: space-between;">
                        <p>Mã đơn hàng: <span class="orderID">1</span> </p>
                        <p class="finish-color orderStatus <%=item.getKey().getColorByStatus()%>">Hoàn thành</p>

                        <!--                        <span class="orderStatusNumber" style="display: none">trang thai theo so (ẩn)</span>-->
                        <!--                        <span class="orderReceiver" style="display: none">nguoi nhan</span>-->
                        <!--                        <span class="orderReceiverPhone" style="display: none">so dien thoai</span>-->
                        <!--                        <span class="orderAddress" style="display: none">dia chi</span>-->
                    </div>
                    <div class="seperate-horizontal-100"></div>
                    <div class="order-detail grid__row" onclick="showDetailsOrder.call(this)">
                        <div class="grid__row grid-col-9" style="justify-content: left;">
                            <div class="grid-col-3" style="padding: 5px;border: 1px solid black;">
                                <img src="../assets/img/product/samsung-galaxy-s23-fe-sld-1-1020x570.jpg" alt="" style="width: 100%;">
                            </div>
                            <div class="grid-col-9 flex-coll" style="padding: 5px;align-items: start;justify-content: space-between;">
                                <p class="product-name">Samsung galaxy s23</p>
                                <p><span class="product-price">1.000.000</span> (VND)</p>

                            </div>
                        </div>
                        <div class="grid-col-3" style="text-align: end;">
                            <p>Số lượng: <span class="product-qty">2</span></p>
                        </div>
                    </div>
                    <div class="other-detail hide">
                        <div class="order-detail grid__row odd" onclick="showDetailsOrder.call(this)">
                            <div class="grid__row grid-col-9" style="justify-content: left;">
                                <div class="grid-col-3" style="padding: 5px;border: 1px solid black;">
                                    <img src="../assets/img/product/samsung-galaxy-s23-fe-sld-1-1020x570.jpg" alt="" style="width: 100%;">
                                </div>
                                <div class="grid-col-9 flex-coll" style="padding: 5px;align-items: start;justify-content: space-between;">
                                    <p class="product-name">Samsung galaxy s23</p>
                                    <p><span class="product-price">1.000.000</span> (VND)</p>

                                </div>
                            </div>
                            <div class="grid-col-3" style="text-align: end;">
                                <p>Số lượng: <span class="product-qty">2</span></p>
                            </div>
                        </div>
                        <div class="order-detail grid__row" onclick="showDetailsOrder.call(this)">
                            <div class="grid__row grid-col-9" style="justify-content: left;">
                                <div class="grid-col-3" style="padding: 5px;border: 1px solid black;">
                                    <img src="../assets/img/product/samsung-galaxy-s23-fe-sld-1-1020x570.jpg" alt="" style="width: 100%;">
                                </div>
                                <div class="grid-col-9 flex-coll" style="padding: 5px;align-items: start;justify-content: space-between;">
                                    <p class="product-name">Samsung galaxy s23</p>
                                    <p><span class="product-price">1.000.000</span> (VND)</p>

                                </div>
                            </div>
                            <div class="grid-col-3" style="text-align: end;">
                                <p>Số lượng: <span class="product-qty">2</span></p>
                            </div>
                        </div>
                    </div>
                    <div class="btn-more-container">
                        <p class="btn-more" onclick="showMoreOrderDetail(event,'.order-item');">Xem thêm <i class="bi bi-chevron-down"></i></p>
                    </div>
                    <!--                <p class="btn-more">Thu gọn <i class="bi bi-chevron-up"></i></p>-->

                    <div class="seperate-horizontal-100"></div>
                    <div class="flex-roww" style="justify-content: space-between;">
                        <div class="flex-coll" style="align-items: start; justify-content: space-between;">
                            <p style="margin-bottom: 10px;">Ngày đặt: <span class="order-dateSet">10/10/2024 10:00</span></p>
                            <p>Ngày hoàn thành: <span class="order-dateFinish">10/10/2024 10:30</span></p>
                        </div>
                        <div class="flex-coll" style="align-items: end; justify-content: space-between;">
                            <p style="margin-bottom: 10px;">Tổng tiền: <span class="order-Price">4.000.000</span> (VND)</p>
                            <button type="button" class="btn btn-third" >Hủy</button>
                        </div>
                    </div>
                    <a href="">Xem chi tiết</a>
                </div>
            </div>
        </div>
        <div class="grid-col-4" style="padding-left: 5px;">
            <div class="sub-content orderDetail-container">
                <div class="flex-roww" style="justify-content: space-between">
                    <p>Mã đơn hàng: <span>1</span></p>
                    <p class="order-status">Hoàn thành</p>
                </div>
                <p style="margin-top: 20px;margin-bottom: 5px;">Danh sách sản phẩm</p>
                <div class="o-product-list">
                    <div class="o-product-item">
                        <p>Tên: <span>Samsung s23</span></p>
                        <p>Màu: <span>Đen</span></p>
                        <p>Ram: <span>6</span> (GB)</p>
                        <p>Rom: <span>128</span> (GB)</p>
                        <p>Imei1: <span>1234567</span></p>
                        <p>Imei2: <span>4567889</span></p>
                    </div>
                    <div class="o-product-item">
                        <p>Tên: <span>Samsung s23</span></p>
                        <p>Màu: <span>Đen</span></p>
                        <p>Ram: <span>6</span> (GB)</p>
                        <p>Rom: <span>128</span> (GB)</p>
                        <p>Imei1: <span>1234567</span></p>
                        <p>Imei2: <span>4567889</span></p>
                    </div>
                </div>
                <p style="margin-top: 20px;margin-bottom: 5px;">Địa chỉ nhận hàng</p>

            </div>
        </div>
    </div>


</div>
<%@ include file="footer.jsp" %>
</body>
</html>