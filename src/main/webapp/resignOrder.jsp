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
    <title>Ký lại đơn hàng</title>

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
    String message = (String) request.getAttribute("message");

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
    <div id="toast"></div>
    <h3 style="text-align: center">Đơn hàng cần kiểm tra</h3>
    <p style="text-align: center">Đây là danh sách những đơn hàng đã phát sinh từ thời điểm bạn bị lộ Key</p>
    <div class="flex-roww" style="align-items: start;">
        <div class="grid-col-12" style="padding-right: 5px;">
            <div class="order-container" style="margin-bottom: 20px">
                <%
                    for(OrderUnit o : orderUnits) {
                %>
                    <div class="flex-roww group">
                        <div class="grid-col-8">
                            <div class="order-item sub-content">
                                <div class="flex-roww" style="justify-content: space-between;">
                                    <p>Mã đơn hàng: <span class="orderID"><%=o.getOrderID()%></span> </p>
                                    <p class="finish-color orderStatus status-<%=o.getStatus()%>"><%=Constant.getStatusString(o.getStatus())%></p>
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

                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="grid-col-4" style="padding: 10px;">
                            <div class="">
                                <div class="flex-roll grid-col-12">
                                    <button class="btn btn-outline-primary" type="button" onclick="downloadOrder(<%=o.getOrderID()%>)">Tải đơn hàng id <%=o.getOrderID()%></button>
                                    <div class="flex-roww" style="justify-content: space-between;">
                                        <div class="form-group signature-form-container" style="margin-top: 10px;">
                                            <label>Chữ ký</label>
                                            <input type="file" data-max_length="20" class="" accept=".txt" onchange="previewSign(event);" style="font-size: 10px;">
                                            <textarea contenteditable="true" type="text" class="form-control textarea-signature" name="digital-signature" placeholder="Nhập chữ ký" required="" style="width: 100%"></textarea>
                                        </div>
                                        <button class="btn btn-primary" type="button" onclick="resign(event)">Ký lại</button>
                                    </div>


                                </div>
                                <div class="seperate-horizontal-100"></div>
                                <div>
                                    <p>Đơn hàng không phải do bạn phát sinh?</p>
                                    <%
                                        if(o.getStatus()<=Constant.CANCEL) {
                                    %>
                                        <div class="flex-roww" style="justify-content: right;">
                                            <a class="btn btn-third btn-status-10" href="resignOrder?action=cancel&id=<%=o.getOrderID()%>" onclick="cancel(event);">Hủy</a>
                                        </div>
                                    <%
                                        } else if(o.getStatus()==Constant.COMPLETE) {
                                    %>
                                        <p>Yêu cầu xử lý hoàn tiền</p>
                                        <div class="flex-roww" style="justify-content: right;">
                                            <a class="btn btn-third btn-status-10" href="resignOrder?action=request&id=<%=o.getOrderID()%>">Yêu cầu</a>
                                        </div>
                                    <%
                                        }
                                    %>

                                </div>
                            </div>

                        </div>
                    </div>

                <%
                    }
                %>

            </div>
        </div>

    </div>
</div>
<script>
    function downloadOrder(id) {
        $.ajax({
            url: "order?action=download", // Đường dẫn Servlet xử lý
            type: "POST",
            data: {id:id},
            success: function(response) {
                const blob = new Blob([response], { type: "text/plain" });
                const link = document.createElement("a");
                link.href = URL.createObjectURL(blob);
                link.download = "Đơn hàng ID "+id+".txt";
                link.click();
                URL.revokeObjectURL(link.href);
            },
            error: function (xhr, status, error) {
                console.error("Có lỗi xảy ra: ", error);
            }
        });
    }

    function previewSign(event) {
        const group = event.currentTarget.closest('.group');
        const file = event.target.files[0];
        if (file && file.type === "text/plain") { // Kiểm tra định dạng file
            const reader = new FileReader(); // Tạo đối tượng FileReader
            reader.onload = function(e) {
                const content = e.target.result; // Lấy nội dung file
                group.querySelector('textarea[name="digital-signature"]').value = content; // Hiển thị nội dung file
            };
            reader.readAsText(file); // Đọc file dưới dạng văn bản
        } else {
            alert("Please upload a valid .txt file."); // Thông báo khi file không hợp lệ
        }
    }

    function resign(event) {
        const group = event.currentTarget.closest('.group');
        var signature = group.querySelector('textarea[name="digital-signature"]').value;
        if(signature=='') return;
        var id = group.querySelector('.orderID').innerText;
        $.ajax({
            url: "resignOrder?action=resign", // Đường dẫn Servlet xử lý
            type: "POST",
            data: {id:id,signature:signature},
            success: function(response) {
                group.style.display = 'none';
                showSuccessToast('Cập nhật thành công!');
            },
            error: function (xhr, status, error) {
                console.error("Có lỗi xảy ra: ", error);
            }
        });
    }

    function cancel(event) {
        event.preventDefault();
        var url =event.currentTarget.href;
        const group = event.currentTarget.closest('.group');
        console.log(url);
        $.ajax({
            url: url,
            type: "POST",
            data: {},
            success: function(response) {
                group.style.display = 'none';
            },
            error: function (xhr, status, error) {
                console.error("Có lỗi xảy ra: ", error);
            }
        });
    }
</script>
<%@ include file="footer.jsp" %>
</body>
</html>