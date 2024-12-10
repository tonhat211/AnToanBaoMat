<%@ page import="model.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="description">
    <meta content="" name="keywords">

    <!-- Favicons -->


    <!-- Google Fonts -->
    <link href="https://fonts.gstatic.com" rel="preconnect">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

    <!-- Vendor CSS Files -->
    <link href="./assets/css/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="./assets/css/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="./assets/fonts/fonts-bootstrap/bootstrap-icons.min.css">
    <link href="./assets/css/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
    <link href="./assets/css/vendor/quill/quill.snow.css" rel="stylesheet">
    <link href="./assets/css/vendor/quill/quill.bubble.css" rel="stylesheet">
    <link href="./assets/css/vendor/remixicon/remixicon.css" rel="stylesheet">
    <link href="./assets/css/vendor/simple-datatables/style.css" rel="stylesheet">

    <!-- Template Main CSS File -->
    <link href="./assets/css/style.css" rel="stylesheet">
    <link href="./assets/css/base.css" rel="stylesheet">
    <link href="./assets/css/modal.css" rel="stylesheet">



    <link href="./assets/css/adminBase.css" rel="stylesheet">
    <link href="./assets/css/adminOrder.css" rel="stylesheet">
</head>
<body>
<%
    User userLogging = (User) session.getAttribute("userLogging");
%>

<header id="header" class="header fixed-top flex-roww" style="justify-content: space-between">
    <a href="index" class="logo grid-col-2 pd0">
        <img src="./assets/img/logo/Logo-The-Gioi-Di-Dong-MWG-B-H.webp" alt="" style="color:black;">
    </a>
    <nav class="header-nav ms-auto">
        <ul class="d-flex align-items-center">

            <li class="nav-item d-block d-lg-none">
                <a class="nav-link nav-icon search-bar-toggle " href="#">
                    <i class="bi bi-search"></i>
                </a>
            </li><!-- End Search Icon-->

            <li class="nav-item dropdown pe-3">

                <a class="nav-link nav-profile d-flex align-items-center pe-0" href="#">
                    <span class="d-none d-md-block dropdown-toggle ps-2"><%=userLogging.getName()%></span>
                </a><!-- End Profile Iamge Icon -->

                <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow profile" id="profile-dropdown">
                    <li class="dropdown-header">
                        <h6><%=userLogging.getUserInfo().getPosition()%></h6>
                        <span><%=userLogging.getUserInfo().getArea()%></span>
                    </li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>

                    <li>
                        <a class="dropdown-item d-flex align-items-center" href="">
                            <i class="bi bi-person"></i>
                            <span>Quản lý tài khoản</span>
                        </a>
                    </li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>

                    <li>
                        <a class="dropdown-item d-flex align-items-center" href="login?action=logout">
                            <i class="bi bi-box-arrow-right"></i>
                            <span>Đăng xuất</span>
                        </a>
                    </li>

                </ul><!-- End Profile Dropdown Items -->
            </li><!-- End Profile Nav -->

        </ul>
    </nav><!-- End Icons Navigation -->
    <script>

        const link = document.querySelector('.nav-profile');

        link.addEventListener("click", (e) => {
            e.preventDefault(); // Ngăn chặn hành vi mặc định
            e.stopPropagation();
            document.querySelector('#profile-dropdown').classList.add('active');
            console.log(document.querySelector('#profile-dropdown'));
        });

        // Đảm bảo menu không bị đóng khi bấm vào bên trong nó
        document.querySelector('#profile-dropdown').addEventListener("click", (e) => {
            e.stopPropagation(); // Ngăn chặn sự kiện lan ra bên ngoài
        });

        // Đóng menu khi bấm ra ngoài
        document.addEventListener("click", () => {
            document.querySelector('#profile-dropdown').classList.remove('active');
        });
    </script>
</header>

</body>
</html>