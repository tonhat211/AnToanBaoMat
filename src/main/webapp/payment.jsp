<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Cart" %>
<%@ page import="model.CartUnit" %>
<%@ page import="model.Address" %>
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
    <script type="text/javascript" src="./assets/js/payment.js"></script>


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
<%--    <link href="./assets/css/cart.css" rel="stylesheet">--%>
    <link href="./assets/css/payment.css" rel="stylesheet">


</head>
<body>
<jsp:include page="header.jsp" />
<div class="content-container" style="padding: 40px;">
    <%
        ArrayList<CartUnit> carts = (ArrayList<CartUnit>) request.getAttribute("carts");
        ArrayList<Address> addresses = (ArrayList<Address>) request.getAttribute("addresses");
        SaleProgram saleProgram = (SaleProgram) request.getAttribute("saleProgram");
    %>
    <div class="breadcrumb flex-roww" style="background-color: var(--base-bg-color);padding: 0;font-size: 20px;">
        <a href="#">Giỏ hàng </a><p style="margin-bottom: 0;"> / Thanh toán</p>
    </div>
    <form action="payment" id="paymentForm">
        <input type="text" name="action" value="order" hidden>
        <div class="sub-content">
            <h5 style="margin-bottom: 15px;"><i class="bi bi-geo-alt"></i> Địa chỉ nhận hàng</h5>
            <div class="flex-roww chosen-address">
                <%
                    if(!addresses.isEmpty()) {
                        Address address = addresses.get(0);
                %>
                    <p class="grid-col-3">
                        <span class="receiver"><%=address.getReceiver()%></span> <span class="phone"><%=address.getPhone()%></span>
                    </p>
                    <p class="grid-col-8 address"><%=address.getAddress()%></p>
                <%
                    } else {
                %>
                    <p class="grid-col-11">Chưa có địa chỉ nhận hàng</p>
                <%
                    }
                %>
                <a href="" class="grid-col-1" onclick="openAddressModal(event)">Thay đổi</a>
            </div>
            <input type="text" name="address-input" hidden>
        </div>

        <div class="sub-content" style="margin: 20px 0;">
            <h5>Sản phẩm</h5>
            <table class="" style="width: 100%;">
                <thead>
                <tr>
                    <th scope="col" class="col-base" style="height: fit-content;">STT</th>
                    <th scope="col" class="grid-col-2">Hình ảnh</th>
                    <th scope="col" class="grid-col-3">Sản phẩm</th>
                    <th scope="col" class="grid-col-3">Phân loại</th>
                    <th scope="col" class="grid-col-1">Đơn giá</th>
                    <th scope="col" class="grid-col-1">Số lượng</th>
                    <th scope="col" class="grid-col-2">Thành tiền</th>
                </tr>
                </thead>
                <tbody id="cart-body">
                <%
                    int i=0;
                    for(CartUnit c : carts) {
                %>
                    <tr class="">
                        <input type="text" name="cart-id" value="<%=c.getId()%>" hidden>
                        <th scope="row" class="col-base"><%=++i%></th>
                        <td style="height: 100px" class="grid-col-2"><img src="./assets/img/thumbnail/<%=c.getThumbnail()%>" alt="" style="height: 100%"></td>
                        <td class="grid-col-3"><%=c.getFullName()%></td>
                        <td class="grid-col-3"><%=c.getClassification()%></td>
                        <td class="grid-col-1"><%=c.getCurrentPrice()%></td>
                        <td class="col-base"><%=c.getQty()%></td>
                        <td class="grid-col-2 temp-money"><%=c.getTotalMoney()%></td>
                    </tr>
                <%
                    }
                %>

                </tbody>
            </table>
        </div>
        <div class="sub-content">
            <div class="flex-roww">
                <div class="grid-col-8">
                    <h5>Phương thức thanh toán</h5>
                </div>
                <div class="grid-col-4">
                    <div class="flex-roww" style="justify-content: space-between;">
                        <p>Thanh toán khi nhận hàng</p>
                        <a href="">Thay đổi</a>
                    </div>
                </div>
            </div>
            <div class="seperate-horizontal"></div>
            <div class="flex-roww payment-result" style="font-size: 15px;color:var(--text-color-2);">
                <div class="grid-col-9">
                </div>
                <div class="grid-col-3">
                    <div class="flex-roww" style="justify-content: space-between;">
                        <p>Tổng tiền hàng: </p>
                        <p style="color: black"><span id="total-money-temp">2000000</span> (VND)</p>
                    </div>
                    <div class="" style="">
                        <div class="flex-roww" style="justify-content: space-between;">
                            <p style="margin-bottom: 0">Giảm: </p>
                            <p style="color: black; margin-bottom: 0;"><span id="discount"><%=saleProgram.getDiscount()%></span>% </p>
                        </div>
                        <div class="flex-roww" style="justify-content: right;">
                            <p style="margin-top: 0">Chương trình: <%=saleProgram.getName()%></p>
                        </div>
                   </div>

                    <div class="flex-roww" style="justify-content: space-between;">
                        <p>Tổng thanh toán: </p>
                        <p><span class="total-money" id="total-money">2000000</span> (VND)</p>
                    </div>
                    <input type="text" name="total-money-input" hidden>
                    <div class="flex-roww">
                        <button style="width: 100%" class="btn btn-primary btn-buy" type="submit">Đặt hàng</button>
                    </div>
                </div>
                <script>
                    const cartE = document.querySelector('#cart-body');
                    const tempMoneyEs = cartE.querySelectorAll('.temp-money');
                    var totalTempMoney = 0;
                    tempMoneyEs.forEach(function (el) {
                        let temp = el.innerText.replace(/\./g, '').replace(',', '.');
                        totalTempMoney += parseFloat(temp);
                    });
                    document.querySelector('#total-money-temp').innerText = totalTempMoney.toLocaleString('vi-VN');
                    let discount = parseFloat(document.querySelector('#discount').innerText);
                    let totalMoney = totalTempMoney - (totalTempMoney * discount / 100);
                    console.log(document.querySelector('#total-money'));
                    document.querySelector('#total-money').innerText = totalMoney.toLocaleString('vi-VN');
                </script>

            </div>
        </div>
    </form>

    <script>
        const paymentForm = document.querySelector('#paymentForm');
        paymentForm.addEventListener('submit', function (event) {
            event.preventDefault();
            console.log("submit payment");
            const addressInput = this.querySelector('input[name="address-input"]');
            var receiver = document.querySelector('.chosen-address .receiver').innerText;
            var phone = document.querySelector('.chosen-address .phone').innerText;
            var address = document.querySelector('.chosen-address .address').innerText;
            var fullAddress = receiver +" (" + phone + ")   |" + address;
            addressInput.value = fullAddress;
            const totalMoneyInput = this.querySelector('input[name="total-money-input"]');
            var totalMoney = document.querySelector('#total-money').innerText.replace(/\./g, '').replace(',', '.');
            totalMoneyInput.value = totalMoney;
            var form = new FormData(this);
            var ids = form.getAll('cart-id');
            console.log(ids);
            $.ajax({
                url: "payment?action=order", // Đường dẫn Servlet xử lý
                type: "POST",
                data: {idsJson : JSON.stringify(ids), address :fullAddress, totalMoney:totalMoney},
                success: function(response) {
                    $('#payment-response').html(response);
                },
                error: function (xhr, status, error) {
                    console.error("Có lỗi xảy ra: ", error);
                }
            });

        });
    </script>
    <div id="modal-container2">
        <!--   cap nhat so dia chi -->
        <div class="modall change-address-modal" onclick="removeModal('#modal-container2');">
            <div class="modall-content" style="width: 40%; background-color: unset;">
                <div class="flex-roww" style="align-items: start;justify-content: space-between;height: 100%;">
                    <div class="sub-content" style="margin-right: 10px;height: 100%; padding-right: 5px;" onclick="event.stopPropagation();">
                        <h3 style="text-align: center;padding-right: 10px;">Sổ địa chỉ</h3>
                        <div class="update-container">
                            <div id="address-container2" style="padding-right: 10px;">
                                <%
                                    for(Address a : addresses) {
                                %>
                                        <div class="address-item">
                                            <div class="flex-roww" style="justify-content: space-between; align-items: start">
                                                <div class="address-info">
                                                    <p><span class="receiver"><%=a.getReceiver()%></span> |   <span class="phone"><%=a.getPhone()%></span></p>
                                                    <p class="id" hidden><%=a.getId()%></p>
                                                    <p style="margin-top: 5px">Địa chỉ: <span class="address"><%=a.getAddress()%></span></p>
                                                </div>
                                            </div>
                                            <div class="seperate-horizontal-90"></div>
                                        </div>
                                <%
                                    }
                                %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="modall sign-order-modal">
            <div class="modall-content sub-content" style="width: 70%;">
                <h3 style="text-align: center;padding-right: 10px;">Ký đơn hàng</h3>
                <p style="text-align: center;font-style: italic;font-size: 14px;">Để đảm bảo an toàn, đơn hàng cần phải được ký</br> Đơn hàng sẽ bị hủy nếu không được ký</p>
                <div class=" flex-roww" style="margin-right: 10px;padding-right: 5px; justify-content: space-around" onclick="event.stopPropagation();">
                    <form action="payment" class="group" id="signOrderForm">
                        <input type="text" name="orderID" value="1" hidden>
                        <input type="text" name="action" value="sign" hidden>
                        <div class="flex-coll" style="align-items: start;">
                            <button class="btn btn-outline-primary" type="button" onclick="downloadOrder(event)">Tải đơn hàng ID <span class="orderID">1</span></button>
                            <div class="form-group" style="margin-top: 10px;">
                                <div class="flex-roww" >
                                    <label>Chữ ký</label>
                                    <input type="file" multiple data-max_length="20" class="" accept=".txt" onchange="previewSign(event);" style="margin-left: 30px;margin-bottom: 10px;">
                                </div>
                                <textarea contenteditable="true" type="text" class="form-control textarea-signature" name="digital-signature" placeholder="Nhập chữ ký" required=""></textarea>
                            </div>
                            <div class="flex-roww" style="justify-content: end;width: 100%;">
                                <button class="btn btn-primary" type="submit">Ký</button>
                            </div>
                        </div>
                    </form>
                    <div>
                        <p style="font-weight: bold">Hướng dẫn ký:</p>
                        <div style="font-style: italic;">
                            <p>Bước 1: Tải đơn hàng về máy</p>
                            <p>Bước 2: Mở ứng dụng chữ ký điện tử</p>
                            <p>Bước 3: Nhập Đơn hàng đã tải vào ứng dụng</p>
                            <p>Bước 4: Nhập Private key</p>
                            <p>Bước 5: Chọn bắt đầu</p>
                            <p>Bước 6: Nhập chữ ký vào mục Chữ ký lên website</p>
                        </div>


                    </div>

                </div>
                <script>
                    function downloadOrder(event) {
                        const group = event.currentTarget.closest('.group');
                        var id = group.querySelector('input[name="orderID"]').value;
                        console.log("id: " + id);
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

                    function setupSignOrderModal(id) {
                        document.querySelector('.sign-order-modal').classList.add('active');
                        const signOrderForm = document.querySelector('#signOrderForm');
                        signOrderForm.querySelector('input[name="orderID"]').value= id;
                        signOrderForm.querySelector('.orderID').innerText = id;
                    }
                </script>
            </div>
        </div>
    </div>
    <div id="payment-response"></div>
    <script>
        function openAddressModal(event) {
            event.preventDefault();
            document.querySelector(".change-address-modal").classList.add('active');
        }

        const addressItemEs = document.querySelectorAll('.address-item');
        for(let el of addressItemEs) {
            el.addEventListener('click', function (event) {
                selectAddressItem(event);
            });
        }

        function selectAddressItem(event) {
            let receiver = event.currentTarget.querySelector('.receiver').innerText;
            let phone = event.currentTarget.querySelector('.phone').innerText;
            let address = event.currentTarget.querySelector('.address').innerText;

            document.querySelector('.chosen-address .receiver').innerText = receiver;
            document.querySelector('.chosen-address .phone').innerText = phone;
            document.querySelector('.chosen-address .address').innerText = address;

            document.querySelector(".change-address-modal").classList.remove('active');

        }
    </script>








</div>
<%@ include file="footer.jsp" %>
</body>
</html>