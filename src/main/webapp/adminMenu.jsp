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
    User userLoggingMenu = (User) session.getAttribute("userLogging");
    String adminMenu = (String) session.getAttribute("adminMenu");
    if(adminMenu==null) adminMenu="";
%>
<aside id="sidebar" class="sidebar grid-col-2">
    <ul class="sidebar-nav" id="sidebar-nav">
        <h3 style="text-align: center">[Admin]</h3>
        <%if(userLoggingMenu.hasRole("CUSTOMER")) {%>
            <li class="nav-item">
                <a class="nav-link <%=adminMenu.equalsIgnoreCase("customer")?"":"collapsed"%>" href="adminmenu?action=admincustomer">
                    <span>Quản lý khách hàng</span>
                </a>
            </li>
        <%
            }
        %>
        <%if(userLoggingMenu.hasRole("EMPLOYEE")) {%>
        <li class="nav-item ">
            <a class="nav-link <%=adminMenu.equalsIgnoreCase("employee")?"":"collapsed"%>" href="adminmenu?action=adminemployee">
                <span>Quản lý nhân viên</span>
            </a>
        </li>
        <%
            }
        %>
        <%if(userLoggingMenu.hasRole("PRODUCT")) {%>
        <li class="nav-item ">
            <a class="nav-link <%=adminMenu.equalsIgnoreCase("product")?"":"collapsed"%>" href="adminmenu?action=adminproduct">
                <span>Quản lý sản phẩm</span>
            </a>
        </li>
        <%
            }
        %>
        <%if(userLoggingMenu.hasRole("ORDER")) {%>
        <li class="nav-item ">
            <a class="nav-link <%=adminMenu.equalsIgnoreCase("order")?"":"collapsed"%>" href="adminmenu?action=adminorder">
                <span>Quản lý đơn hàng</span>
            </a>
        </li>
        <%
            }
        %>
    </ul>
</aside><!-- End Sidebar-->
</body>
</html>