<%@ page import="java.util.ArrayList" %>
<%@ page import="DAO.ProductUnitDAO" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.Duration" %>
<%@ page import="DAO.SaleProgramDAO" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="model.*" %>
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
<%
    ArrayList<OrderUnit> orderUnits = (ArrayList<OrderUnit>) request.getAttribute("orderUnits");
    int currentStatus = (int) request.getAttribute("status");
    String message = (String) request.getAttribute("message");
    OrderUnit order = (OrderUnit) request.getAttribute("OrderPresentation");

%>
<%
    if(message!=null) {
%>
        <script>
            alert("<%=message%>");
        </script>
<%
    }
%>
<%@ include file="header.jsp" %>
<div class="content-container">
    <h3 style="text-align: center">Lịch sử mua hàng</h3>
    <div class="flex-roww" style="align-items: end;">
        <div class="grid-col-8">
            <div class="sub-content">
                <ul class="tab-menu flex-roww" style="align-items: end;">
                    <!--            <li class="order-menu-item active"><a href="cus-order?action=all">Tất cả</a></li>-->
                    <li class="<%=currentStatus==Constant.WAITING?"active":""%>"><a href="order?action=bystatus&status=<%=Constant.WAITING%>">Chờ xác nhận</a></li>
                    <li class="<%=currentStatus==Constant.CONFIRM?"active":""%>"><a href="order?action=bystatus&status=<%=Constant.CONFIRM%>">Chuẩn bị</a></li>
                    <li class="<%=currentStatus==Constant.DELIVERY?"active":""%>"><a href="order?action=bystatus&status=<%=Constant.DELIVERY%>">Vận chuyển</a></li>
                    <li class="<%=currentStatus==Constant.COMPLETE?"active":""%>"><a href="order?action=bystatus&status=<%=Constant.COMPLETE%>">Hoàn thành</a></li>
                    <li class="<%=currentStatus==Constant.CANCEL?"active":""%>"><a href="order?action=bystatus&status=<%=Constant.CANCEL%>">Hủy</a></li>
                    <li class="<%=currentStatus==-1?"active":""%>"><a href="order?action=bystatus&status=-1">Tất cả</a></li>
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
                <%
                    for(OrderUnit o : orderUnits) {
                %>
                    <div class="order-item sub-content">
                        <div class="flex-roww" style="justify-content: space-between;">
                            <p>Mã đơn hàng: <span class="orderID"><%=o.getOrderID()%></span> </p>
                            <p class="finish-color orderStatus status-<%=o.getStatus()%>"><%=Constant.getStatusString(o.getStatus())%></p>

                            <!--                        <span class="orderStatusNumber" style="display: none">trang thai theo so (ẩn)</span>-->
                            <!--                        <span class="orderReceiver" style="display: none">nguoi nhan</span>-->
                            <!--                        <span class="orderReceiverPhone" style="display: none">so dien thoai</span>-->
                            <!--                        <span class="orderAddress" style="display: none">dia chi</span>-->
                        </div>
                        <div class="seperate-horizontal-100"></div>
                        <%
                            ArrayList<OrderDetail> details = o.details;
                            if(!details.isEmpty()) {
                                OrderDetail firstDe = details.get(0);
                        %>
<%--                                <div class="order-detail grid__row" onclick="showDetailsOrder.call(this)">--%>
                                <div class="order-detail grid__row">
                                    <div class="grid__row grid-col-9" style="justify-content: left;">
                                        <div class="grid-col-3" style="padding: 5px;">
                                            <img src="./assets/img/thumbnail/<%=firstDe.productUnit.thumbnail%>" alt="" style="width: 100%;">
                                        </div>
                                        <div class="grid-col-9 flex-coll" style="padding: 5px;align-items: start;justify-content: space-between;">
                                            <p class="product-name"><%=firstDe.productUnit.name%></p>
                                            <p><span class="product-price"><%=firstDe.getCurrentPrice()%></span> (VND)</p>

                                        </div>
                                    </div>
                                    <div class="grid-col-3" style="text-align: end;">
                                        <p>Số lượng: <span class="product-qty"><%=firstDe.quantity%></span></p>
                                    </div>
                                </div>

                                <div class="other-detail hide">
                            <%
                                for(int i=1;i<details.size();i++) {
                            %>
                                    <div class="order-detail grid__row <%=(i%2)!=0?"odd":""%>" onclick="showDetailsOrder.call(this)">
                                        <div class="grid__row grid-col-9" style="justify-content: left;">
                                            <div class="grid-col-3" style="padding: 5px;border: 1px solid black;">
                                                <img src="./assets/img/thumbnail/<%=details.get(i).productUnit.thumbnail%>" alt="" style="width: 100%;">
                                            </div>
                                            <div class="grid-col-9 flex-coll" style="padding: 5px;align-items: start;justify-content: space-between;">
                                                <p class="product-name"><%=details.get(i).productUnit.name%></p>
                                                <p><span class="product-price"><%=details.get(i).getCurrentPrice()%></span> (VND)</p>

                                            </div>
                                        </div>
                                        <div class="grid-col-3" style="text-align: end;">
                                            <p>Số lượng: <span class="product-qty"><%=details.get(i).quantity%></span></p>
                                        </div>
                                    </div>
                                <%
                                    }
                                %>
                            </div>

                        <%
                            }
                        %>

                        <div class="btn-more-container">
                            <p class="btn-more" onclick="showMoreOrderDetail(event,'.order-item');">Xem thêm <i class="bi bi-chevron-down"></i></p>
                        </div>
                        <!--                <p class="btn-more">Thu gọn <i class="bi bi-chevron-up"></i></p>-->

                        <div class="seperate-horizontal-100"></div>
                        <div class="flex-roww" style="justify-content: space-between;">
                            <div class="flex-coll" style="align-items: start; justify-content: space-between;">
                                <p style="margin-bottom: 10px;">Ngày đặt: <span class="order-dateSet"><%=o.getDateSetInLine()%></span></p>
                                <p>Ngày hoàn thành: <span class="order-dateFinish"><%=o.getUpdateTimeInLine()%></span></p>
                            </div>
                            <div class="flex-coll" style="align-items: end; justify-content: space-between;">
                                <p style="margin-bottom: 10px;">Tổng tiền: <span class="order-Price"><%=o.getTotalMoney()%></span> (VND)</p>
                                <%
                                    if(o.getStatus()<=Constant.CONFIRM) {
                                %>
                                        <a class="btn btn-third btn-status-10" href="order?action=cancel&id=<%=o.getOrderID()%>">Hủy</a>
                                <%
                                    }
                                %>
                            </div>
                        </div>
                        <a href="order?action=detail&id=<%=o.getOrderID()%>&status=<%=o.getStatus()%>">Xem chi tiết</a>
                    </div>
                <%
                    }
                %>

            </div>
        </div>
<%--        <%--%>
<%--            OrderUnit order = orderUnits.get(0);--%>
<%--        %>--%>
        <div class="grid-col-4" style="padding-left: 5px;">
            <div class="sub-content orderDetail-container">
                <div class="flex-roww" style="justify-content: space-between">
                    <p style="font-weight: bold;">Mã đơn hàng: <span><%=order.getOrderID()%></span></p>
<%--                    <p class="order-status"><%=Constant.getStatusString(order.getStatus())%></p>--%>
                    <p class="status-<%=order.getStatus()%>"><%=Constant.getStatusString(order.getStatus())%></p>

                </div>
                <p style="margin-top: 20px;margin-bottom: 5px;font-weight: bold;">Danh sách sản phẩm</p>
                <div class="o-product-list">
                    <%
                        ArrayList<OrderDetail> details = order.details;
                        for(OrderDetail detail : details) {
                    %>
                            <div class="o-product-item">
                                <p>Tên: <span><%=detail.productUnit.name%></span></p>
                                <p>Màu: <span><%=detail.productUnit.color%></span></p>
                                <p>Ram: <span><%=detail.productUnit.ram%></span> (GB)</p>
                                <p>Rom: <span><%=detail.productUnit.rom%></span> (GB)</p>
                            </div>

                    <%
                        }
                    %>

                </div>
                <p style="margin-top: 20px;margin-bottom: 5px;font-weight: bold;">Địa chỉ nhận hàng</p>
                <p><%=order.order.getAddress()%></p>

            </div>
        </div>
    </div>


</div>
<%@ include file="footer.jsp" %>
</body>
</html>