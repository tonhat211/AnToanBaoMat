<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Báo lộ Key</title>

    <!-- thu vien jquery   -->
    <script type="text/javascript" src="./assets/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="./assets/js/js_bootstrap4/bootstrap.min.js"></script>
    <script type="text/javascript" src="./assets/js/forgetPassword.js"></script>


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

    <!-- css tu them   -->
    <link href="./assets/css/login.css" rel="stylesheet">
    <link href="./assets/css/forgetPassword.css" rel="stylesheet">

</head>
<body>
<%
    String email = (String) request.getAttribute("email");
    String time = (String) request.getAttribute("time");
%>
<jsp:include page="header.jsp" />
<div class="content-container bg-img">
    <div class="static-overlay flex-roww" style="justify-content: center;align-items: unset;">
        <div class="grid-col-9 flex-roww otp-form active" style="justify-content: center;align-items: unset;height: fit-content; margin-top: 40px;">
            <div class="sub-content form-container">
                <div class="flex-roww" style="justify-content: space-between;">
                    <i class="bi bi-arrow-left btn-back"></i>
                    <p style="width: 100%; font-size: 30px;text-align: center">Nhập mã xác nhận</p>
                </div>
                <div class="flex-coll" style="justify-content: center;margin-top: 20px;">
                    <p>Mã xác thức sẽ được gửi đến email</p>
                    <p class="email-address"><%=email%></p>
                    <p class="note">Mã sẽ hết hạn trong vòng 5 phút</p>
                </div>
                <form action="verify" id="otp-form" style="width: 100%;margin-top: 15px;" class="group">
                    <input type="text" name="action" value="verifyOTP" hidden>
                    <div id="OTP_Div" class="group">
                        <input autofocus class="form-control otp-input empty" onkeyup="alter_box(event)" maxlength="1"  required type="number" id="o1" name="o1"/>
                        <input class="form-control otp-input empty" required maxlength="1" type="number" id="o2" name="o2" onkeyup="alter_box(event)" />
                        <input class="form-control otp-input empty" required maxlength="1" type="number" id="o3" name="o3" onkeyup="alter_box(event)" />
                        <input class="form-control otp-input empty" required maxlength="1" type="number" id="o4" name="o4" onkeyup="alter_box(event)" />
                        <input class="form-control otp-input empty" required maxlength="1" type="number" id="o5" name="o5" onkeyup="alter_box(event)" />
                    </div>
                    <span class="pwd-error">Mã xác nhận sai. Vui lòng nhập lại</span>
                    <p style="text-align: center;">Chưa nhận được mã?</p>
                    <div class="flex-coll group" style="justify-content: center;margin-top: 15px;">
                        <p style="text-align: center;" id="countdown-container" class="active" >Vui lòng đợi <span id="countdown">0s</span></p>
                        <a style="text-align: center;" href="reportKey?action=resend&time=<%=time%>" id="btn-resend" onclick="resend(event);">Gửi lại</a>
                    </div>
                    <input type="text" name="time" value="<%=time%>">
                    <button type="button" class="btn btn-login" style="margin-top: 20px;width: 100%;" onclick="verify(event);">Xác nhận</button>
                </form>
            </div>
        </div>
<%--        <div class="flex-roww otp-message" style="justify-content: center;align-items: center;">--%>
<%--            <div class="sub-content form-container" style="height: fit-content;">--%>
<%--                <div class="flex-coll" style="justify-content: center;margin-top: 20px;">--%>
<%--                    <p style="width: 100%; font-size: 30px;text-align: center">Thông báo</p>--%>
<%--                    <p>Xác thực thành công, vui lòng đăng nhập lại.</p>--%>
<%--                    <a href="login" class="btn btn-login" style="margin-top: 20px;width: 100%;">Đăng nhập</a>--%>
<%--                </div>--%>

<%--            </div>--%>
<%--        </div>--%>
    </div>
    <div id="server-response"></div>
    <script>
        countdown(60,'#countdown','#btn-resend');

        function resend(event) {
            event.preventDefault();
            var url = event.currentTarget.href;
            $.ajax({
                type: "GET",
                url: url,
                data: {},
                success: function(data) {

                    countdown(60,'#countdown','#btn-resend');
                },
                error: function(error) {
                    console.error("Error during querying address: ", error);
                }
            });
        }

        function wrongOTP() {
            document.querySelector('.pwd-error').classList.add('active');
        }

        function verify(event) {
            const group = event.currentTarget.closest('.group');
            var o1 = group.querySelector('input[name="o1"]').value;
            var o2 = group.querySelector('input[name="o2"]').value;
            var o3 = group.querySelector('input[name="o3"]').value;
            var o4 = group.querySelector('input[name="o4"]').value;
            var o5 = group.querySelector('input[name="o5"]').value;
            var otp = o1+""+o2+""+o3+""+o4+""+o5;
            var time = group.querySelector('input[name="time"]').value;
            $.ajax({
                type: "POST",
                url: "reportKey",
                data: {action:"verifyOTP",otp:otp,time:time},
                success: function(data) {
                    wrongOTP();
                    // document.querySelector('#server-response').innerHTML = data;
                },
                error: function(error) {
                    console.error("Error during querying address: ", error);
                }
            });
        }
    </script>

</div>
<%@ include file="footer.jsp" %>
</body>
</html>