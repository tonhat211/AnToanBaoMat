<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thietbididong.com</title>

    <!-- thu vien jquery   -->
    <script type="text/javascript" src="./assets/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="./assets/js/js_bootstrap4/bootstrap.min.js"></script>
    <script type="text/javascript" src="./assets/js/login.js"></script>

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
    <link href="./assets/css/login.css" rel="stylesheet">

</head>
<body>
<jsp:include page="header.jsp" />

<div class="content-container flex-roww">
    <div class="grid-col-6">
        <img src="./assets/img/banner/login.png" alt="" style="width: 100%;margin-top: 30px">
    </div>
    <div class="grid-col-6 flex-roww" style="justify-content: center;align-items: unset">
        <div class="form-container sub-content ">
            <p style="font-size: 30px; font-weight: 700;text-align: center">Đăng nhập</p>
            <form action="login" id="login-form" style="width: 100%">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" class="form-control" id="email" name="email" aria-describedby="emailHelp" placeholder="Nhập email" required value="2003tonhat@gmail.com">
                </div>
                <div class="form-group">
                    <label for="password">Mật khẩu</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="Nhập mật khẩu" required value="1234">
                    <span class="pwd-error">* Mật khẩu phải trên 8 ký tự</span>
                </div>
                <input type="text" name="page" value="<%=request.getAttribute("page")!=null?request.getAttribute("page"):""%>" hidden>
                <div class="flex-roww" style="justify-content: right;"><a href="" style="font-size: 13px;">Quên mật khẩu</a></div>
                <div class="flex-roww" style="justify-content: space-between;margin: 20px 0">
                    <a href="signup" style="font-size: 13px">Chưa có tài khoản. Đăng ký</a>
                    <button type="submit" class="btn btn-login">Đăng nhập</button>
                </div>
                <p style="font-size: 14px; text-align: center;color:#b5b5b5">Đăng nhập bằng</p>
                <div class="flex-roww" style="justify-content: center; margin-top: 5px">
                    <div class="grid-col-1">

                        <a href=""><img src="./assets/img/icon/icons8-google-48.png" alt="" style="width: 100%"></a>
                    </div>
                </div>
            </form>

        </div>
        <script>
            document.querySelector("#login-form").addEventListener('submit', function(event) {
               event.preventDefault();
               console.log('form submit');
                var formdata = new FormData(document.querySelector("#login-form"));
                var email = formdata.get("email");
                var password = formdata.get("password");
                var page = formdata.get("page");
                login(email, password,page);
            });
        </script>
    </div>
    <div id="login-response"></div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>