<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Cart" %>
<%@ page import="model.CartUnit" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thietbididong.com</title>

    <!-- thu vien jquery   -->
    <script type="text/javascript" src="./assets/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="./assets/js/js_bootstrap4/bootstrap.min.js"></script>
    <script type="text/javascript" src="./assets/js/cart.js"></script>


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
    <link href="./assets/css/cart.css" rel="stylesheet">


</head>
<body>
<jsp:include page="header.jsp" />

<div class="content-container">
    <%
        ArrayList<CartUnit> carts = (ArrayList<CartUnit>) request.getAttribute("carts");
    %>
    <div class="filter-options modal-login-require">
        <div class="filter-details modall" style="width: 600px;height: 150px;">
            <div class="flex-roww" style="justify-content: center;margin:22px 0;font-size: 19px">
                <p>Bạn chưa đăng nhập, vui lòng đăng nhập để sử dụng giỏ hàng</p>
            </div>
            <div class="flex-roww" style="justify-content: space-around; margin-top: 20px">
                <button class="btn btn-outline-primary btn-cancel-filter" onclick="removeModal('.modal-filter-details')"><i class="bi bi-x-lg"></i> Hủy</button>
                <a href="" class="btn btn-primary btn-filter" style="color: white;"><i class="bi bi-check-lg"></i> Đăng nhập</a>

            </div>
        </div>
        <div class="overlay" onclick="removeModal('.modal-filter-details')"></div>
    </div>
    <div class="grid-col-9">
        <h2 class="center-title">Giỏ hàng</h2>
        <p class="center-title">Bạn có <span class="num-of-cart"><%=carts.size()%></span> sản phẩm trong giỏ hàng</p>
    </div>
    <form action="payment" method="post" id="cartForm" class="cart-form">
        <input type="text" value="buy" name="action" hidden>
        <div class="flex-roww" style="align-items: start;margin-top: 20px;">
            <div class="grid-col-9" style="padding-right: 10px;">
                <div class="sub-content">
                    <table style="width: 100%;" id="cartTable">
                        <thead>
                        <tr>
                            <th scope="col" class="col-base" style="height: fit-content;">Chọn <input type="checkbox" id="check-all"></th>
                            <th scope="col" class="grid-col-2">Hình ảnh</th>
                            <th scope="col" class="grid-col-4">Tên sản phẩm</th>
                            <th scope="col" class="grid-col-2">Đơn giá (VND)</th>
                            <th scope="col" class="grid-col-2">Số lượng </th>
                            <th scope="col" class="col-base" style="height: fit-content;">Xóa</th>
                        </tr>
                        </thead>
                        <tbody class="group">
                        <%
                            for(CartUnit c : carts) {
                        %>
                        <tr data-value="<%=c.getId()%>">
                            <th scope="row" class="col-base" >
                                <div class="form-check">
                                    <input class="form-check-input p-check" type="checkbox" name="cart-check" value="<%=c.getId()%>">
                                </div>
                            </th>
                            <td class="grid-col-2">
                                <img src="./assets/img/thumbnail/<%=c.getThumbnail()%>" alt="" style="width: 100%">
                            </td>
                            <td class="grid-col-4">
                                <div class="flex-coll" style="padding: 5px;align-items: start;justify-content: space-between;">
                                    <p class="product-name bold-text-6"><%=c.getFullName()%></p>
                                    <p style="color: var(--text-color-2);font-size: 13px;">Phân loại: <span class="product-option"><%=c.getClassification()%></span> </p>
                                </div>
                            </td>
                            <td class="price-unit"><%=c.getCurrentPrice()%></td>
                            <td>
                                <input type="number" class="temp-money" hidden>
                                <div class="flex-roww qty-control">
                                    <i class="fa-solid fa-plus btn-plus"></i>
                                    <!--                                    <input type="number" class="idproduct" name="idproduct" hidden value="" maxlength="2" size="2">-->
                                    <!--                                    <input class="idcart" type="number" hidden name="idcart" value="">-->
                                    <input type="number" class="qty-input qty" name="qty" value="<%=c.getQty()%>" maxlength="2" size="2" readonly>
                                    <i class="fa-solid fa-minus btn-minus"></i>
                                </div>
                            </td>
                            <td class="col-base"><i class="fa-solid fa-x deleteProductBtn"></i></td>
                        </tr>

                        <%
                            }
                        %>


                        </tbody>
                    </table>
                </div>

            </div>
            <div class="grid-col-3" style="padding-left: 10px;">
                <div class="sub-content money">
                    <h4 class="center-title">Tổng tiền</h4>
                    <p style="color: indianred; font-weight: 600"><span id="totalMoney">0</span> (VND) </p>
                    <p style="font-size: 14px">Tổng sản phẩm: <span class="totalProduct">0</span> (sản phẩm) </p>
                    <div class="ad_func-container">
                        <button class="btn btn-primary btn-buy" type="submit">Mua hàng</button>
                    </div>
                </div>

            </div>
        </div>
    </form>
    <div id="cart-response"></div>
    <script>
        setupCartTable('#cartTable');
        document.getElementById("cartForm").addEventListener("submit", function(e) {
            e.preventDefault();
            var formData = new FormData(this);
            const selectedItems = [];
            document.querySelectorAll('input[name="cart-check"]:checked').forEach((checkbox) => {
                selectedItems.push(checkbox.value);
                console.log(checkbox.value);
            });
            if (selectedItems.length > 0) {
                console.log("Các mục đã chọn:", selectedItems);
                this.submit();
            } else {
                alert("Vui lòng chọn ít nhất một mục!");
            }
        });
    </script>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>