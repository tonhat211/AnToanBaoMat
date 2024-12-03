<%@ page import="model.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Quản lý sản phẩm</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="description">
    <meta content="" name="keywords">

    <script type="text/javascript" src="./assets/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="./assets/js/js_bootstrap4/bootstrap.min.js"></script>
    <script type="text/javascript" src="./assets/js/toast.js"></script>
    <script type="text/javascript" src="./assets/js/admin.js"></script>


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
    <link href="./assets/css/toast.css" rel="stylesheet">



    <link href="./assets/css/adminBase.css" rel="stylesheet">
<%--    <link href="./assets/css/adminOrder.css" rel="stylesheet">--%>
    <link href="./assets/css/adminProduct.css" rel="stylesheet">

</head>
<body>
<%
//    User userLogging = (User) session.getAttribute("userLogging");
//    userLogging = new User(1,"Minh Nhat","minhnhat@gmail.com");
%>
<%@ include file="adminHeader.jsp" %>
<div class="flex-roww">
    <%@ include file="adminMenu.jsp" %>
    <%
        ArrayList<ProductUnit> productUnits = (ArrayList<ProductUnit>) request.getAttribute("productUnits");
        int numOfPages = request.getAttribute("numOfPages")!=null?(int) request.getAttribute("numOfPages"):0;
        int cateID = request.getAttribute("cateID")!=null?(int) request.getAttribute("cateID"):Constant.ALL;

        String message = (String) request.getAttribute("message");
    %>

    <%
        if(message!=null) {
    %>
    <script>
        alert('<%= message %>');
    </script>
    <%

        }
    %>

    <script>
        function previewImage(event) {
            const parent = event.currentTarget.closest('.group');
            var file = parent.querySelector('.thumbnailInput').files[0];
            var reader = new FileReader();

            reader.onload = function(e) {
                var imagePreview = parent.querySelector('.thumbnailPreview');
                imagePreview.src = e.target.result;
                imagePreview.style.display = 'block';  // Hiển thị ảnh
            }

            if (file) {
                reader.readAsDataURL(file);
            }
        }
        function previewImages(event) {
            const parent = event.currentTarget.closest('.group');
            console.log(parent);
            const imgListContainer = parent.querySelector('.upload__img-wrap');
            const files = Array.from(event.currentTarget.files);
            files.forEach(function (f) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    var imgBox = document.createElement('div');
                    imgBox.classList.add('upload__img-box');
                    var imgBg = document.createElement('div');
                    imgBg.classList.add('img-bg');
                    imgBg.style.backgroundImage = 'url('+e.target.result+')';
                    imgBg.setAttribute('data-file', f.name);

                    var imgClose = document.createElement('div');
                    imgClose.classList.add('upload__img-close');
                    imgClose.setAttribute('onclick', 'removeImg(event)');

                    imgBg.appendChild(imgClose);
                    imgBox.appendChild(imgBg);
                    imgListContainer.appendChild(imgBox);

                    updateImgUrls(parent);
                };
                reader.readAsDataURL(f);
            });

        }
        function removeImg(event) {
            const imgBox = event.currentTarget.closest('.img-bg');
            const container = event.currentTarget.closest('.group');
            imgBox.parentNode.remove();
            updateImgUrls(container);
        }
        function updateImgUrls(container) {
            const elements = container.querySelectorAll('.img-bg');
            console.log("so luong hinh: "+ elements.length);
            var urls="";
            elements.forEach(function (element) {
                urls+=element.getAttribute('data-file')+'==';
            });
            container.querySelector('input[name="imgUrls"]').value = urls;
            console.log(container.querySelector('input[name="imgUrls"]').value);

        }
    </script>
    <main id="main" class="main grid-col-10">
        <div id="toast"></div>
        <div class="pagetitle grid-col-3">
            <h1>Quản lý sản phẩm</h1>
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="index">Trang chủ</a></li>
                    <li class="breadcrumb-item active">Quản lý sản phẩm</li>
                </ol>
            </nav>
        </div><!-- End Page Title -->
        <div class="sub-content">
            <div class="flex-roww" style="justify-content: space-between">
                <form action="adminproduct" method="get" class="search-bar grid-col-4 ">
                    <i class="fa-solid fa-magnifying-glass"></i>
                    <input type="text" name="search" placeholder="Bạn tìm gì...">
                    <input type="text" name="action" value="search" hidden>
                    <input type="submit" hidden>
                </form>
                <a href="" id="add-product-btn">Thêm sản phẩm</a>
            </div>
            <div class="flex-roww" style="margin-left: auto">
                <p style="margin-right: 10px">Xem theo trạng thái: </p>
                <select name="byCategory" data-default="<%=cateID%>" onchange="queryBy(event);" >
                    <option value="<%=Constant.ALL%>">Tất cả</option>
                    <option value="<%=Constant.SMARTPHONE_CATEGORY%>">Smartphone</option>
                    <option value="<%=Constant.TABLET_CATEGORY%>">Tablet</option>
                    <option value="<%=Constant.LAPTOP_CATEGORY%>">Laptop</option>
                </select>
            </div>
            <div class="info-container">
                <table style="width: 100%;" id="infoTable" class="info-table">
                    <thead>
                    <tr>
                        <th scope="col" class="grid-col-0_5 text-center" style="height: fit-content;">STT</th>
                        <th scope="col" class="grid-col-0_5 text-center">ID</th>
                        <th scope="col" class="grid-col-0_5 text-center">Ảnh</th>
                        <th scope="col" class="grid-col-1_5">Tên</th>
                        <th scope="col" class="grid-col-1">Phiên bản</th>
                        <th scope="col" class="grid-col-1 text-center">Ram(GB)</th>
                        <th scope="col" class="grid-col-1 text-center">Rom(GB)</th>
                        <th scope="col" class="grid-col-1 text-center">Số lượng</th>
                        <th scope="col" class="grid-col-1">Thương hiệu</th>
                        <th scope="col" class="grid-col-1">Phân loại</th>
                        <!--            <th scope="col" class="grid-col-0_5 text-center">Nổi bật</th>-->
                        <th scope="col" class="grid-col-1">Thao tác</th>
                    </tr>
                    </thead>
                    <tbody class="group">
                    <tr class="group" id="detail-container">
                        <td style="width: 100%" colspan="11">
<%--                            <form action="adminproduct" method="post" id="detailForm">--%>
                                <input type="submit" hidden>
                                <table id="detail-table">
                                    <thead>
                                    <tr>
                                        <th scope="col" class="grid-col-0_5 text-center" style="height: fit-content;">STT</th>
                                        <th scope="col" class="grid-col-0_5 text-center">ID</th>
                                        <th scope="col" class="grid-col-1">Màu</th>
                                        <th scope="col" class="grid-col-1 text-center">Ram(GB)</th>
                                        <th scope="col" class="grid-col-1 text-center">Rom(GB)</th>
                                        <th scope="col" class="grid-col-1 text-center">Giá</th>
                                        <th scope="col" class="grid-col-1 text-center">Số lượng</th>
                                        <th scope="col" class="grid-col-1">Thao tác</th>
                                    </tr>
                                    </thead>
                                    <tbody class="group">
                                    <tr class="group locked">
                                        <th scope="row" class="grid-col-0_5 text-center" >
                                            <span class="stt">1</span>
                                        </th>
                                        <td class="grid-col-0_5 text-center">
                                            <span class="id">1</span>
                                        </td>
                                        <td class="grid-col-1">
                                            <input class="info-input" type="text" name="color" value="Xanh" readonly>
                                        </td>
                                        <td class="grid-col-1 text-center">
                                            <input class="info-input text-center" type="number" name="ram" value="4" readonly>
                                        </td>
                                        <td class="grid-col-1 text-center">
                                            <input class="info-input text-center" type="number" name="rom" value="64" readonly>
                                        </td>
                                        <td class="grid-col-1 text-center">
                                            <input class="info-input text-center" type="number" name="price" value="8900000" readonly>
                                        </td>
                                        <td class="grid-col-1">
                                            <input class="info-input text-center" type="number" name="qty" value="3" readonly>
                                        </td>
                                        <td class="grid-col-1">
                                            <select name="action" onchange="handleChange(event);" data-default="none">
                                                <option value="none">...</option>
                                                <option value="updatedetail">Cập nhật</option>
                                                <option class="lock-option" value="lockdetail">Khóa</option>
                                                <option class="unlock-option" value="unlockdetail">Mở khóa</option>
                                                <option value="deletedetail">Xóa</option>
                                            </select>
                                            <button class="update-detail-btn active" onclick="updateDetail(event);"><i class="bi bi-floppy"></i></button>

                                        </td>

                                    </tr>
                                    <tr class="group unlocked">
                                        <th scope="row" class="grid-col-0_5 text-center" >
                                            <span class="stt">1</span>
                                        </th>
                                        <td class="grid-col-0_5 text-center">
                                            <span class="id">1</span>
                                        </td>
                                        <td class="grid-col-1">
                                            <input class="info-input" type="text" name="color" value="Xanh" readonly>
                                        </td>
                                        <td class="grid-col-1 text-center">
                                            <input class="info-input text-center" type="number" name="ram" value="4" readonly>
                                        </td>
                                        <td class="grid-col-1 text-center">
                                            <input class="info-input text-center" type="number" name="rom" value="64" readonly>
                                        </td>
                                        <td class="grid-col-1 text-center">
                                            <input class="info-input text-center" type="number" name="price" value="8900000" readonly>
                                        </td>
                                        <td class="grid-col-1">
                                            <input class="info-input text-center" type="number" name="qty" value="3" readonly>
                                        </td>
                                        <td class="grid-col-1">
                                            <select name="action" onchange="handleChange(event);" data-default="none">
                                                <option value="none">...</option>
                                                <option value="updatedetail">Cập nhật</option>
                                                <option class="lock-option" value="lockdetail">Khóa</option>
                                                <option class="unlock-option" value="unlockdetail">Mở khóa</option>
                                                <option value="deletedetail">Xóa</option>
                                            </select>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
<%--                            </form>--%>

                        </td>
                    </tr>
                    <%
                        int stt=0;
                        for(ProductUnit p : productUnits) {
                    %>
                            <tr class="group <%=p.isLocked()?"locked":"active"%>" id="product<%=p.getProductID()%>" onclick="showDetail(event);">
                                <th scope="row" class="grid-col-0_5 text-center" >
                                    <span class="stt"><%=stt++%></span>
                                </th>
                                <td class="grid-col-0_5 text-center product-id">
                                    <span class="id"><%=p.getProductID()%></span>
                                </td>
                                <td class="grid-col-0_5">
                                    <div class="img-thumbnail-container">
                                        <img src="./assets/img/thumbnail/<%=p.getThumbnail()%>" alt="" style="width: 100%">
                                    </div>
                                </td>
                                <td class="grid-col-1_5">
                                    <span class="name"><%=p.getName()%></span>
                                </td>
                                <td class="grid-col-1">
                                    <span class="version"><%=p.getVersion()%></span>
                                </td>
                                <td class="grid-col-1 text-center">
                                    <%=p.getRam()%>
                                </td>
                                <td class="grid-col-1 text-center">
                                    <%=p.getRom()%>
                                </td>
                                <td class="grid-col-0_5 text-center">
                                    <%=p.totalQty%>
                                </td>
                                <td class="grid-col-1">
                                    <%=p.brand.name%>
                                </td>
                                <td class="grid-col-1">
                                    <%=p.getCategoryName()%>
<%--                                    <%=p.cateID%>--%>
                                </td>

                                <td class="grid-col-1" onclick="event.stopPropagation();">
                                    <select name="action" onchange="handleChange(event);" data-default="none">
                                        <option value="none">...</option>
                                        <option value="update">Cập nhật</option>
                                        <option class="lock-option" value="lock">Khóa</option>
                                        <option class="unlock-option" value="unlock">Mở khóa</option>
                                        <option value="detail">Chi tiết</option>
                                        <option value="delete">Xóa</option>
                                    </select>
                                </td>
                            </tr>
                    <%
                        }
                    %>


                    </tbody>
                </table>
                <div class="temp" style="display: none;">

                </div>

            </div>

        </div>
        <div class="modall" id="lock-confirm-modal">
            <div class="modal__overlay group" onclick="closeModal(event);">
                <div class="modall-content grid-col-6" onclick="event.stopPropagation();">
                    <p class="confirm-content" style="text-align: center">Xác nhận khóa </br> <span class="object">Nhật</span></p>
                    <input type="text" name="id" hidden>

                    <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                        <button class="btn btn-fourth btn-cancel" type="button" onclick="closeModal(event);">Hủy</button>
                        <button class="btn btn-primary btn-confirm" onclick="lock(event);" type="button">Khóa</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modall" id="unlock-confirm-modal">
            <div class="modal__overlay group" onclick="closeModal(event);">
                <div class="modall-content grid-col-6" onclick="event.stopPropagation();">
                    <p class="confirm-content" style="text-align: center">Xác nhận mở khóa </br> <span class="object">Nhi</span></p>
                    <input type="text" name="id" hidden>
                    <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                        <button class="btn  btn-fourth btn-cancel" type="button" onclick="closeModal(event);">Hủy</button>
                        <button class="btn btn-primary btn-confirm" onclick="active(event);" type="button">Mở khóa</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modall" id="delete-confirm-modal">
            <div class="modal__overlay group" onclick="closeModal(event);">

                <div class="modall-content grid-col-6" onclick="event.stopPropagation();">
                    <form action="adminproduct" method="post" style="width: 100%">
                        <p class="confirm-content" style="text-align: center">Xác nhận xoá </br> <span class="object">Nhi</span></p>
                        <input type="text" name="id" hidden>
                        <input type="text" name="action" value="delete">
                        <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                            <button class="btn  btn-fourth btn-cancel" type="button" onclick="closeModal(event);">Hủy</button>
                            <button class="btn btn-primary btn-confirm" type="submit">Xóa</button>
                        </div>
                    </form>
                </div>


            </div>
        </div>
        <div class="modall" id="update-info-modal">
            <div class="modal__overlay" onclick="closeModal(event);">
                <div class="modall-content grid-col-6 custom-scroll" onclick="event.stopPropagation();">
                    <form action="adminproduct" method="POST" id="updateInfoForm" enctype="multipart/form-data">
                        <h4 class="confirm-content" style="text-align: center">Cập nhật thông tin sản phẩm</h4>
                        <div class="flex-roww" style="justify-content: space-around; margin-top: 10px;">
                            <div class="form-group grid-col-4">
                                <label for="name">Tên</label>
                                <input type="text" class="form-control" id="name" name="name" aria-describedby="emailHelp" placeholder="Nhập tên sản phẩm" required>
                            </div>
                            <div class="form-group flex-roww grid-col-4">
                                <label for="id">ID:</label>
                                <input type="text" class="form-control" id="id" name="id" aria-describedby="emailHelp" placeholder="ID" value="17" readonly>
                            </div>
                        </div>
                        <div class="form-group grid-col-4">
                            <label for="version">Phiên bản</label>
                            <input type="text" class="form-control" id="version" name="version" aria-describedby="emailHelp" placeholder="Nhập phiên bản" required>
                        </div>
                        <div class="flex-roww" style="justify-content: space-around; margin-top: 15px;">
                            <div class="form-group grid-col-4">
                                <label>Thương hiệu</label>
                                <select name="brandID" data-default="1">
                                    <option value="1">Samsung</option>
                                    <option value="2">Apple</option>
                                    <option value="3">Oppo</option>
                                    <option value="4">Xiaomi</option>
                                    <option value="5">Realme</option>
                                </select>
                            </div>
                            <div class="form-group grid-col-4">
                                <label>Phân loại</label>
                                <select name="cateID" data-default="1">
                                    <option value="1">Smartphone</option>
                                    <option value="2">Tablet</option>
                                    <option value="3">Laptop</option>
                                </select>
                            </div>
                        </div>
                        <div class="flex-roww" style="justify-content: space-around; margin-top: 15px;">
                            <div class="form-group grid-col-4">
                                <label for="saleDate">Ngày mở bán</label>
                                <input type="date" class="form-control" id="saleDate" name="saleDate" value="2024-10-10" placeholder="Nhập ngày mở bán" required>
                            </div>
                            <div class="form-group grid-col-4">
                                <label for="initial-price">Giá mở bán</label>
                                <input type="number" class="form-control" id="initial-price" name="initialPrice" value="8900000" placeholder="Nhập giá mở bán" required>
                            </div>
                            <input type="text" name="firstSale">
                        </div>
                        <div class="flex-roww" style="justify-content: space-around; margin-top: 15px;" >
                            <div class="form-group grid-col-4">
                                <label>Cấu hình</label>
                                <table id="configTable" class="table">
                                    <thead>
                                    <tr>
                                        <th>Tên</th>
                                        <th>Giá trị</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td><input type="text" onkeydown="handleKeyDown(event, 1)"></td>
                                        <td><input type="text" onkeydown="handleKeyDown(event, 2)"></td>
                                    </tr>
                                    </tbody>
                                </table>
                                <input type="text" name="config">
                            </div>
                            <div class="form-group grid-col-4">
                            </div>
                        </div>
                        <div class="flex-roww" style="justify-content: space-around; margin-top: 15px;" >
                            <div class="form-group grid-col-4">
                                <label>Nổi bật</label>
                                <input type="text" name="feature" placeholder="Nhập thuộc tính nổi bật">
                            </div>
                            <div class="form-group grid-col-4">
                            </div>
                        </div>
                        <div class="flex-roww" style="justify-content: space-around; margin-top: 15px;" >
                            <div class="form-group grid-col-4">
                                <label for="prominence">Độ nổi bật</label>
                                <input type="number" class="form-control" id="prominence" name="prominence" value="99" placeholder="Nhập độ nổi bật" required>
                            </div>
                            <div class="form-group grid-col-4">
                            </div>

                        </div>
                        <div class="flex-roww" style="justify-content: space-around; margin-top: 20px;" >
                            <div class="form-group grid-col-4">
                                <p>Ảnh thumbnail</p>
                                <input id="thumbnailInput" type="file" name="thumbnail" accept=".jpg, .jpeg, .png"  onchange="previewImage()" />
                                <div class="thumbnail-img-container grid-col-6" style="margin-top: 15px;">
                                    <img src="./assets/img/thumbnail/iphone-11-trang-600x600.jpg" alt="" id="thumbnailPreview" style="width:100%">
                                    <input type="text" value="" name="thumbnailInput">
                                </div>
                            </div>
                            <div class="form-group grid-col-4">
                            </div>
                        </div>
                        <div class="flex-roww" style="justify-content: space-around; margin-top: 20px;" >
                            <div class="form-group">
                                <p>Ảnh chi tiết</p>
                                <!--                new -->
                                <div class="upload__box group">
                                    <div class="upload__btn-box">
                                        <label class="upload__btn">
                                            <p>Tải ảnh</p>
                                            <input type="file" multiple data-max_length="20" name="imgs" class="upload__inputfile" accept=".jpg, .jpeg, .png" onchange="previewImages(event);">
                                        </label>
                                    </div>
                                    <div class="upload__img-wrap"></div>
                                    <input type="text" name="imgUrls" value="">
                                </div>


                            </div>



                        </div>



                        <p>Mô tả</p>
                        <div class="toolbar">
                            <button onclick="document.execCommand('bold')"><b>B</b></button>
                            <button onclick="document.execCommand('italic')"><i>I</i></button>
                            <button onclick="document.execCommand('underline')"><u>U</u></button>
                        </div>

                        <textarea contenteditable="true" id="editor" class="editor" name="description" placeholder="Bắt đầu soạn thảo văn bản ở đây..."></textarea>
                        <br>
                        <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                            <input type="text" name="action" value="update">
                            <button class="btn  btn-fourth btn-cancel" type="button" onclick="closeModal(event);">Hủy</button>
                            <button class="btn btn-primary btn-confirm" onclick="" type="submit">Lưu</button>
                        </div>
                    </form>

                    <script>



                    </script>

                    <script>
                        document.querySelector('#updateInfoForm').addEventListener('submit', function(event) {
                            event.preventDefault();
                            var configTable = document.getElementById("configTable");
                            var configJson = {};
                            for (let i = 1; i < configTable.rows.length; i++) {
                                const row = configTable.rows[i];
                                const inputKey = row.cells[0].querySelector('input'); // Tìm ô input trong cột giá trị
                                const valueKey = inputKey ? inputKey.value.trim() : ''; // Lấy giá trị từ ô input nếu có// Cột thứ nhất là tên thuộc tính
                                const inputValue = row.cells[1].querySelector('input'); // Tìm ô input trong cột giá trị
                                const valueValue = inputValue ? inputValue.value.trim() : ''; // Lấy giá trị từ ô input nếu có// Cột thứ hai là giá trị
                                configJson[valueKey] = valueValue;
                            }
                            const featureInput = this.querySelector('input[name="feature"]').value.trim();
                            const featureArray = featureInput ? featureInput.split(',').map(item => item.trim()) : [];
                            configJson['features'] = featureArray;
                            var configString = JSON.stringify(configJson, null, 2);
                            this.querySelector('input[name="config"]').value = configString;
                            var saleDate = this.querySelector('input[name="saleDate"]').value;
                            var initialPrice = this.querySelector('input[name="initialPrice"]').value;
                            const firstSaleJson = {};
                            firstSaleJson['date'] = saleDate;
                            firstSaleJson['initial-price'] = initialPrice;
                            const firstSaleString = JSON.stringify(firstSaleJson, null, 2);
                            this.querySelector('input[name="firstSale"]').value = firstSaleString;
                            this.submit();

                        });
                    </script>
                </div>
            </div>
        </div>

        <div class="modall" id="add-modal">
            <div class="modal__overlay" onclick="closeModal(event);">
<%--            <div class="modal__overlay">--%>
                <div class="modall-content grid-col-6 custom-scroll" style="max-height: 90%;" onclick="event.stopPropagation();">
                    <form action="adminproduct" method="POST" id="addProductForm" enctype="multipart/form-data">
                        <h4 class="confirm-content" style="text-align: center">Thêm sản phẩm mơi</h4>
                        <div class="flex-roww" style="justify-content: space-between; margin-top: 10px;">
                            <div class="form-group grid-col-4">
                                <label>Tên</label>
                                <input type="text" class="form-control" name="name" aria-describedby="emailHelp" placeholder="Nhập tên sản phẩm" required="">
                            </div>
<%--                            <div class="form-group flex-roww grid-col-4">--%>
<%--                                <label>ID:</label>--%>
<%--                                <input type="text" class="form-control" name="id" aria-describedby="emailHelp" placeholder="ID" value="92" readonly="">--%>
<%--                            </div>--%>
                        </div>
                        <div style="margin-top: 15px;">
                            <p>Phiên bản:</p>
                            <div class="group">
                                <div class="flex-roww version-input-container group">
                                    <div class="grid-col-0_5 flex-roww" style="justify-content: center;">
<%--                                        <i class="bi bi-plus-circle-fill btn-add-version active" onclick="addMoreConfig(event);"></i>--%>
<%--                                        <i class="bi bi-dash-circle-fill btn-remove-version" onclick="removeConfig(event)"></i>--%>
                                    </div>
                                    <div class="grid-col-11 version-item" style="border-left: 1px solid var(--bold-color);padding-left: 4px;">
                                        <input type="text" name="version" placeholder="Phiên bản">
                                        <div class="flex-roww group version-detail" style="justify-content: space-between;margin-top: 10px">
                                            <input class="grid-col-2" type="text" name="color" placeholder="Màu">
                                            <input class="grid-col-2" type="text" name="ram" placeholder="Ram(GB)">
                                            <input class="grid-col-2" type="text" name="rom" placeholder="Rom(GB)">
                                            <input class="grid-col-2" type="text" name="price" placeholder="Giá">
                                            <input class="grid-col-2" type="text" name="qty" placeholder="Số lượng">
                                            <i class="bi bi-plus-circle-fill btn-add-version active" onclick="addMoreConfigDetail(event)"></i>
                                            <i class="bi bi-dash-circle-fill btn-remove-version" onclick="removeConfigDetail(event)"></i>
                                        </div>
                                    </div>

                                </div>
                            </div>
                            <input type="text" name="versions" hidden>
                        </div>
                        <div class="flex-roww" style="justify-content: space-between; margin-top: 15px;">
                            <div class="form-group grid-col-4">
                                <label>Thương hiệu</label>
                                <select name="brandID" data-default="1">
                                    <option value="1" selected="">Samsung</option>
                                    <option value="2">Apple</option>
                                    <option value="3">Oppo</option>
                                    <option value="4">Xiaomi</option>
                                    <option value="5">Realme</option>
                                </select>
                            </div>
                            <div class="form-group grid-col-4">
                                <label>Phân loại</label>
                                <select name="cateID" data-default="1">
                                    <option value="1" selected="">Smartphone</option>
                                    <option value="2">Tablet</option>
                                    <option value="3">Laptop</option>
                                </select>
                            </div>
                        </div>
                        <div class="flex-roww" style="justify-content: space-between; margin-top: 15px;">
                            <div class="form-group grid-col-4">
                                <label>Ngày mở bán</label>
                                <input type="date" class="form-control" name="saleDate" value="2020-10-10" placeholder="Nhập ngày mở bán" required="">
                            </div>
                            <div class="form-group grid-col-4">
                                <label>Giá mở bán</label>
                                <input type="text" class="form-control" name="initialPrice" value="22.000.000" placeholder="Nhập giá mở bán" required="">
                            </div>
                            <input type="text" name="firstSale" value="{&quot;date&quot;:&quot;2020-10-10&quot;,&quot;initial-price&quot;:22000000}" hidden="">
                        </div>
                        <div class="flex-roww" style="margin-top: 15px;">
                            <div class="form-group grid-col-4">
                                <label>Cấu hình</label>
                                <table class="table configTable">
                                    <thead>
                                    <tr>
                                        <th>Tên</th>
                                        <th>Giá trị</th>
                                    </tr>
                                    </thead>
                                    <tbody class="group">
                                        <tr>
                                            <td><input type="text" value="" onkeydown="handleKeyDown(event, 1)"></td>
                                            <td><input type="text" value="" onkeydown="handleKeyDown(event, 2)"></td>
                                        </tr>


                                    </tbody>
                                </table>
                                <input type="text" name="config" hidden="">
                            </div>
                            <div class="form-group grid-col-4">
                            </div>
                        </div>
                        <div class="flex-roww" style="margin-top: 15px;">
                            <div class="form-group grid-col-4">
                                <label>Nổi bật</label>
                                <input type="text" name="feature" value="" placeholder="Nhập thuộc tính nổi bật">
                            </div>
                            <div class="form-group grid-col-4">
                            </div>
                        </div>
                        <div class="flex-roww" style="margin-top: 15px;">
                            <div class="form-group grid-col-4">
                                <label>Độ nổi bật</label>
                                <input type="number" class="form-control" name="prominence" value="" placeholder="Nhập độ nổi bật" required="">
                            </div>
                            <div class="form-group grid-col-4">
                            </div>

                        </div>
                        <div class="flex-roww" style="margin-top: 20px;">
                            <div class="form-group grid-col-4 group">
                                <p>Ảnh thumbnail</p>
                                <input class="thumbnailInput" type="file" name="thumbnail" accept=".jpg, .jpeg, .png" onchange="previewImage(event)">
                                <div class="thumbnail-img-container grid-col-6" style="margin-top: 15px;">
                                    <img src="./assets/img/thumbnail/samsung-galaxy-a05s-sliver-thumbnew-600x600.jpg" alt="" class="thumbnailPreview" style="width:100%">
                                </div>
                            </div>
                            <div class="form-group grid-col-4">
                            </div>

                        </div>
                        <div class="flex-roww" style="margin-top: 20px;">
                            <div class="form-group">
                                <p>Ảnh chi tiết</p>
                                <!--                new -->
                                <div class="upload__box group">
                                    <div class="upload__btn-box">
                                        <label class="upload__btn">
                                            <input type="file" multiple="" data-max_length="20" name="imgs" class="upload__inputfile" accept=".jpg, .jpeg, .png" onchange="previewImages(event);">
                                        </label>
                                    </div>
                                    <div class="upload__img-wrap"></div>
                                    <input type="text" name="imgUrls" value="iphone-13-xanh-glr-4-750x500.jpg==iphone-13-xanh-glr-5-750x500.jpg==vi-vn-samsung-galaxy-s23-fe-5g-2.jpeg==vi-vn-samsung-galaxy-s23-fe-5g-4.jpeg==vi-vn-samsung-galaxy-s23-fe-5g-5.jpeg==vi-vn-samsung-galaxy-s23-fe-5g-6.jpeg==vi-vn-samsung-galaxy-s23-fe-5g-8.jpeg" hidden="">
                                </div>
                            </div>



                        </div>



                        <p>Mô tả</p>
                        <div class="toolbar">
                            <button onclick="document.execCommand('bold')"><b>B</b></button>
                            <button onclick="document.execCommand('italic')"><i>I</i></button>
                            <button onclick="document.execCommand('underline')"><u>U</u></button>
                        </div>

                        <textarea contenteditable="true" class="editor" name="description" placeholder="Bắt đầu soạn thảo văn bản ở đây..."></textarea>
                        <br>
                        <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                            <input type="text" name="action" value="add" hidden>
                            <button class="btn  btn-fourth btn-cancel" type="button" onclick="closeModal(event);">Hủy</button>
                            <button class="btn btn-primary btn-confirm" onclick="" type="submit">Thêm</button>
                        </div>
                    </form>

                    <script>



                    </script>

                    <script>
                        document.querySelector('#addProductForm').addEventListener('submit', function(event) {
                            event.preventDefault();
                            var configTable = this.querySelector(".configTable");
                            var configJson = {};
                            for (let i = 1; i < configTable.rows.length; i++) {
                                const row = configTable.rows[i];
                                const inputKey = row.cells[0].querySelector('input'); // Tìm ô input trong cột giá trị
                                const valueKey = inputKey ? inputKey.value.trim() : ''; // Lấy giá trị từ ô input nếu có// Cột thứ nhất là tên thuộc tính
                                const inputValue = row.cells[1].querySelector('input'); // Tìm ô input trong cột giá trị
                                const valueValue = inputValue ? inputValue.value.trim() : ''; // Lấy giá trị từ ô input nếu có// Cột thứ hai là giá trị
                                configJson[valueKey] = valueValue;
                            }
                            const featureInput = this.querySelector('input[name="feature"]').value.trim();
                            const featureArray = featureInput ? featureInput.split(',').map(item => item.trim()) : [];
                            configJson['features'] = featureArray;
                            var configString = JSON.stringify(configJson, null, 2);
                            this.querySelector('input[name="config"]').value = configString;
                            var saleDate = this.querySelector('input[name="saleDate"]').value;
                            var initialPrice = this.querySelector('input[name="initialPrice"]').value;
                            const firstSaleJson = {};
                            firstSaleJson['date'] = saleDate;
                            firstSaleJson['initial-price'] = initialPrice;
                            const firstSaleString = JSON.stringify(firstSaleJson, null, 2);
                            this.querySelector('input[name="firstSale"]').value = firstSaleString;

                            //version
                            var versionJson = {};
                            var versionArr=[];

                            const versionDetails = this.querySelectorAll('.version-detail');
                            console.log("so detail: " + versionDetails.length);
                            versionDetails.forEach(versionDetail => {
                                var versionItemJson={};
                                var color = versionDetail.querySelector('input[name="color"]').value;
                                var ram = versionDetail.querySelector('input[name="ram"]').value;
                                var rom = versionDetail.querySelector('input[name="rom"]').value;
                                var price = versionDetail.querySelector('input[name="price"]').value;
                                var qty = versionDetail.querySelector('input[name="qty"]').value;
                                versionItemJson['color'] = color;
                                versionItemJson['ram'] = ram;
                                versionItemJson['rom'] = rom;
                                versionItemJson['price'] = price;
                                versionItemJson['qty'] = qty;
                                versionArr.push(versionItemJson);
                            });
                            versionJson['versions'] = versionArr;
                            const versionString = JSON.stringify(versionJson, null, 2);
                            console.log(versionString);
                            this.querySelector('input[name="versions"]').value = versionString;

                            this.submit();

                        });

                        document.querySelector('#updateInfoForm').addEventListener('submit', function(event) {
                            event.preventDefault();
                            var configTable = document.getElementById("configTable");
                            var configJson = {};
                            for (let i = 1; i < configTable.rows.length; i++) {
                                const row = configTable.rows[i];
                                const inputKey = row.cells[0].querySelector('input'); // Tìm ô input trong cột giá trị
                                const valueKey = inputKey ? inputKey.value.trim() : ''; // Lấy giá trị từ ô input nếu có// Cột thứ nhất là tên thuộc tính
                                const inputValue = row.cells[1].querySelector('input'); // Tìm ô input trong cột giá trị
                                const valueValue = inputValue ? inputValue.value.trim() : ''; // Lấy giá trị từ ô input nếu có// Cột thứ hai là giá trị
                                configJson[valueKey] = valueValue;
                            }
                            const featureInput = this.querySelector('input[name="feature"]').value.trim();
                            const featureArray = featureInput ? featureInput.split(',').map(item => item.trim()) : [];
                            configJson['features'] = featureArray;
                            var configString = JSON.stringify(configJson, null, 2);
                            this.querySelector('input[name="config"]').value = configString;
                            var saleDate = this.querySelector('input[name="saleDate"]').value;
                            var initialPrice = this.querySelector('input[name="initialPrice"]').value;
                            const firstSaleJson = {};
                            firstSaleJson['date'] = saleDate;
                            firstSaleJson['initial-price'] = initialPrice;
                            const firstSaleString = JSON.stringify(firstSaleJson, null, 2);
                            this.querySelector('input[name="firstSale"]').value = firstSaleString;
                            this.submit();

                        });
                    </script>
                </div>
            </div>
        </div>
        <script>
            function addMoreConfigDetail(event) {
                const group = event.currentTarget.closest('.group');
                const newDetail = group.cloneNode(true);
                clearInput(newDetail);
                group.parentNode.appendChild(newDetail);
                const btnRemove = group.querySelector('.btn-remove-version');
                plusToMinus(event.currentTarget,btnRemove);
            }

            function removeConfigDetail(event) {
                const group = event.currentTarget.closest('.group');
                group.parentNode.removeChild(group);
            }

            function clearInput(group) {
                const inputs = group.querySelectorAll("input");
                inputs.forEach(input => { input.value = "";});
            }

            function plusToMinus(plus,minus) {
                plus.classList.remove('active');
                minus.classList.add('active');
            }

            function minusToPlus(minus,plus) {
                minus.classList.remove('active');
                plus.classList.add('active');
            }

            function addMoreConfig(event) {
                const group = event.currentTarget.closest('.group');
                const newConfig = group.cloneNode(true);
                let configDetails = newConfig.querySelectorAll('.group');
                for(let i=0;i<configDetails.length-1;i++) {
                    configDetails[i].parentNode.removeChild(configDetails[i]);
                }
                clearInput(newConfig);
                group.parentNode.appendChild(newConfig);
                const btnRemove = event.currentTarget.parentNode.querySelector('.btn-remove-version');
                plusToMinus(event.currentTarget,btnRemove);
            }

            function removeConfig(event) {
                const group = event.currentTarget.closest('.group');
                group.parentNode.removeChild(group);
            }

        </script>
        <div class="modall" id="lock-detail-confirm-modal">
            <div class="modal__overlay group" onclick="closeModal(event);">
                <div class="modall-content grid-col-6" onclick="event.stopPropagation();">
                    <p class="confirm-content" style="text-align: center">Xác nhận khóa </br> <span class="object">Nhật</span></p>
                    <input type="text" name="id" hidden>
                    <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                        <button class="btn btn-fourth btn-cancel" type="button" onclick="closeModal(event);">Hủy</button>
                        <button class="btn btn-primary btn-confirm" onclick="lockDetail(event);" type="button">Khóa</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modall" id="unlock-detail-confirm-modal">
            <div class="modal__overlay group" onclick="closeModal(event);">
                <div class="modall-content grid-col-6" onclick="event.stopPropagation();">
                    <p class="confirm-content" style="text-align: center">Xác nhận mở khóa </br> <span class="object">Nhi</span></p>
                    <input type="text" name="id" hidden>
                    <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                        <button class="btn  btn-fourth btn-cancel" type="button" onclick="closeModal(event);">Hủy</button>
                        <button class="btn btn-primary btn-confirm" onclick="activeDetail(event);" type="button">Mở khóa</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modall" id="delete-detail-confirm-modal">
            <div class="modal__overlay group" onclick="closeModal(event);">

                <div class="modall-content grid-col-6" onclick="event.stopPropagation();">
                    <form action="adminproduct" method="post" style="width: 100%">
                        <p class="confirm-content" style="text-align: center">Xác nhận xoá </br> <span class="object">Nhi</span></p>
                        <input type="text" name="id" hidden>
                        <input type="text" name="action" value="deleteDetail">
                        <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                            <button class="btn  btn-fourth btn-cancel" type="button" onclick="closeModal(event);">Hủy</button>
                            <button class="btn btn-primary btn-confirm" type="submit">Xóa</button>
                        </div>
                    </form>
                </div>


            </div>
        </div>
        <script>
            function closeModal(event) {
                event.currentTarget.closest(".modall").classList.remove("active");
            }
        </script>
        <script>
            // Hàm xử lý khi nhấn Enter hoặc Backspace
            function handleKeyDown(event, column) {
                const body = event.currentTarget.closest('.group');
                const currentRow = event.target.closest('tr');
                const currentInput = event.target;
                const currentValue = currentInput.value;
                const firstColumnInput = currentRow.cells[0].querySelector('input');
                const secondColumnInput = currentRow.cells[1].querySelector('input');

                // Kiểm tra khi nhấn Backspace
                if (event.key === 'Backspace') {
                    // Nếu ô trống và ô đang được focus là ô đầu tiên
                    if (currentValue === '' && column === 1) {
                        event.preventDefault(); // Ngăn không cho xóa tiếp
                        const previousRow = currentRow.previousElementSibling;

                        if (previousRow) {
                            // Chuyển focus đến ô cuối cùng của cột 2 ở dòng trên nếu có
                            const previousCell = previousRow.cells[1].querySelector('input');
                            previousCell.focus();
                            currentRow.remove();
                        }
                        return;
                    }

                    // Nếu ô đang được focus là ô thứ hai và không còn ký tự
                    if (currentValue === '' && column === 2) {
                        // Nếu cột 1 vẫn còn dữ liệu thì focus về ô đầu tiên
                        if (firstColumnInput.value !== '') {
                            event.preventDefault();
                            firstColumnInput.focus();
                            return;
                        }
                        return;
                    }
                }

                // Kiểm tra khi nhấn Enter
                if (event.key === 'Enter') {
                    event.preventDefault(); // Ngăn form gửi đi (nếu có form bao quanh)
                    const nextColumn = column === 1 ? 2 : 1;

                    if (nextColumn === 2) {
                        // Chuyển sang ô thứ 2 trong cùng hàng
                        currentRow.cells[1].querySelector('input').focus();
                    } else {
                        // Nếu đang ở ô thứ 2, thêm hàng mới và chuyển focus đến ô đầu tiên của hàng mới
                        addNewRow(body);
                        const newRow = body.lastElementChild;
                        newRow.cells[0].querySelector('input').focus();
                    }
                }
            }

            // Hàm thêm hàng mới vào bảng
            function addNewRow(body) {
                // const table = document.getElementById('configTable').getElementsByTagName('tbody')[0];
                const newRow = document.createElement('tr');

                // Thêm 2 ô nhập liệu vào hàng mới
                for (let i = 0; i < 2; i++) {
                    const newCell = document.createElement('td');
                    const input = document.createElement('input');
                    input.type = 'text';
                    input.onkeydown = (event) => handleKeyDown(event, i + 1); // Gắn sự kiện Enter và Backspace cho từng ô
                    newCell.appendChild(input);
                    newRow.appendChild(newCell);
                }

                // Thêm hàng mới vào cuối bảng
                body.appendChild(newRow);
            }
        </script>
        <script>

            function setupAdd(event) {
                // event.preventDefault();
                // document.querySelector(".add-form").classList.add("active");
            }

            // xử lý khi action được chọn
            function handleChange(event) {
                event.stopPropagation();
                let selectedValue = event.target.value;
                setDefaultOption("action");
                event.target.value = selectedValue;
                console.log("selected value: " + selectedValue);
                const group = event.currentTarget.closest(".group");
                selectedValue=selectedValue.toUpperCase();
                switch (selectedValue) {
                    case "UPDATE":
                        setupUpdate(group,"#update-info-modal");
                        break;
                    case "LOCK": {
                        setInfosStatus(group,false);
                        var name = group.querySelector(".name").innerText;
                        var version = group.querySelector(".version").innerText;
                        var object = name + " " + version;
                        var id = group.querySelector(".id").innerText;
                        setupConfirm(id,'#lock-confirm-modal',object);
                        break;
                    }
                    case "UNLOCK": {
                        setInfosStatus(group,false);
                        var name = group.querySelector(".name").innerText;
                        var version = group.querySelector(".version").innerText;
                        var object = name + " " + version;
                        var id = group.querySelector(".id").innerText;
                        setupConfirm(id,'#unlock-confirm-modal',object);
                        break;
                    }
                    case "DELETE": {
                        setInfosStatus(group,false);
                        var name = group.querySelector(".name").innerText;
                        var version = group.querySelector(".version").innerText;
                        var object = name + " " + version;
                        var id = group.querySelector(".id").innerText;
                        setupConfirm(id,'#delete-confirm-modal',object);
                        break;
                    }
                    case "UPDATEDETAIL" : {
                        setInfosStatus(group,true);
                        group.querySelector(".update-detail-btn").classList.add('active');
                        break;
                    }
                    case "LOCKDETAIL": {
                        setInfosStatus(group,false);
                        var color = group.querySelector("input[name='color']").value;
                        var ram = group.querySelector("input[name='ram']").value;
                        var rom = group.querySelector("input[name='rom']").value;
                        var object = "Phiên bản: màu: " + color + " - ram: " + ram + "(GB) - rom: " + rom + "(GB)";
                        var id = group.querySelector('.id').innerText;
                        setupConfirm(id,'#lock-detail-confirm-modal',object);
                        break;
                    }
                    case "UNLOCKDETAIL": {
                        setInfosStatus(group,false);
                        var color = group.querySelector("input[name='color']").value;
                        var ram = group.querySelector("input[name='ram']").value;
                        var rom = group.querySelector("input[name='rom']").value;
                        var object = "Phiên bản: màu: " + color + " - ram: " + ram + "(GB) - rom: " + rom + "(GB)";
                        var id = group.querySelector('.id').innerText;
                        setupConfirm(id,'#unlock-detail-confirm-modal',object);
                        break;
                    }
                    case "DELETEDETAIL": {
                        setInfosStatus(group,false);
                        var color = group.querySelector("input[name='color']").value;
                        var ram = group.querySelector("input[name='ram']").value;
                        var rom = group.querySelector("input[name='rom']").value;
                        var object = "Phiên bản: màu: " + color + " - ram: " + ram + "(GB) - rom: " + rom + "(GB)";
                        var id = group.querySelector('.id').innerText;
                        setupConfirm(id,'#delete-detail-confirm-modal',object);
                        break;
                    }

                    default:
                        setInfosStatus(group,false);

                }
            }

            function queryBy(event) {
                var value = event.currentTarget.value;
                window.location.href = "adminproduct?action=queryBy&cateID=" + value;
                console.log(value);
            }



            document.querySelector('#add-product-btn').addEventListener('click', function(e) {
                e.preventDefault();
                openModal("#add-modal");
            });

            function setupUpdate(group,modalID) {
                var id = group.querySelector(".id").innerText;
                console.log("set up update: " + id);
                openModal(modalID);
                // document.querySelector(modalID).querySelector("input[name='id']").value = id;
                $.ajax({
                    type: "POST",
                    url: "adminproduct?action=prepareupdate",
                    data: {id: id},
                    success: function(data) {
                        $('#updateInfoForm').html(data);

                    },
                    error: function(error) {
                        $('#server-response').html(error.responseText);
                    }
                });



            }



            // mở modal xác nhận
            function setupConfirm(id,modalID,object) {
                // var name = group.querySelector('input[name="name"]').value;
                openModal(modalID)
                document.querySelector(modalID).querySelector('.object').innerText = object;
                document.querySelector(modalID).querySelector('input[name="id"]').value = id;
            }

            function openModal(modalID) {
                document.querySelector(modalID).classList.add('active');
            }


            function hideModal(modalID) {
                document.querySelector(modalID).classList.remove('active');
            }

            function setInfosStatus(group,status) {
                const inputs = group.querySelectorAll("input");
                var status =!status;
                inputs.forEach((input) => {
                    input.readOnly = status;
                });
                group.querySelector("select").disabled = status;
                group.querySelector("select[name='action']").disabled =false;
            }

            function setDefaultOption(name) {
                document.querySelectorAll('select[name="'+name+'"]').forEach(select => {
                    const defaultValue = select.getAttribute('data-default');
                    select.value = defaultValue;
                });
            }

            // Gọi hàm một lần cho các phần tử hiện có
            setDefaultOption("gender");
            setDefaultOption("byCategory");

            // function setSelectedOption(name,selectedValue) {
            //     document.querySelectorAll('select[name="'+name+'"]').forEach(select => {
            //         select.setAttribute('data-default',selectedValue);
            //         select.value = selectedValue;
            //     });
            // }

            function showDetail(event) {
                event.stopPropagation();
                const detailContainer = document.getElementById("detail-container");
                detailContainer.classList.toggle("active");
                var id = event.currentTarget.querySelector('.product-id .id').innerText;
                $.ajax({
                    type: "POST",
                    url: "adminproduct?action=showdetail",
                    data: {id: id},
                    success: function(data) {
                        detailContainer.querySelector('#detail-table tbody').innerHTML = data;


                    },
                    error: function(error) {
                        $('#server-response').html(error.responseText);
                    }
                });

                const currentRow = event.currentTarget;
                currentRow.parentNode.insertBefore(detailContainer, currentRow.nextElementSibling);
            }

            function hideDetail(element) {
                document.querySelector(element).classList.remove("active");
            }

            function removeActive(element) {
                document.querySelector(element).classList.remove("active");
            }

            function lock(event) {
                var id = event.currentTarget.closest('.group').querySelector('input[name="id"]').value;
                $.ajax({
                    type: "POST",
                    url: "adminproduct?action=lock",
                    data: {id: id},
                    success: function(data) {
                        document.querySelector('#product'+id).classList.remove('active');
                        document.querySelector('#product'+id).classList.add('locked');
                        $('#server-response').html(data);
                        console.log(data);
                        hideModal('#lock-confirm-modal');


                    },
                    error: function(error) {
                        $('#server-response').html(error.responseText);
                    }
                });
            }
            function active(event) {
                var id = event.currentTarget.closest('.group').querySelector('input[name="id"]').value;
                $.ajax({
                    type: "POST",
                    url: "adminproduct?action=active",
                    data: {id: id},
                    success: function(data) {
                        document.querySelector('#product'+id).classList.remove('locked');
                        document.querySelector('#product'+id).classList.add('active');
                        $('#server-response').html(data);
                        console.log(data);
                        hideModal('#unlock-confirm-modal');


                    },
                    error: function(error) {
                        $('#server-response').html(error.responseText);
                    }
                });
            }

            function deleteItem(event) {
                var id = event.currentTarget.closest('.group').querySelector('input[name="id"]').value;
                console.log("xóa: " + id);
                $.ajax({
                    type: "POST",
                    url: "adminproduct?action=delete",
                    data: {id: id},
                    success: function(data) {
                        document.querySelector('#product'+id).style.display = 'none';
                        $('#server-response').html(data);
                        console.log(data);
                        hideModal('#delete-confirm-modal');


                    },
                    error: function(error) {
                        $('#server-response').html(error.responseText);
                    }
                });
            }

            function updateDetail(event) {
                const group = event.currentTarget.closest('.group');
                var id = group.querySelector('.id').innerText;
                var stt = group.querySelector('.stt').innerText;
                var color = group.querySelector('input[name="color"]').value;
                var ram = group.querySelector('input[name="ram"]').value;
                var rom = group.querySelector('input[name="rom"]').value;
                var price = group.querySelector('input[name="price"]').value;
                var qty = group.querySelector('input[name="qty"]').value;
                $.ajax({
                    type: "POST",
                    url: "adminproduct?action=updateDetail",
                    data: {id: id, color: color,ram:ram, rom: rom, price: price, qty: qty,stt:stt},
                    success: function(data) {
                        showSuccessToast('Cập nhật thành công!');
                        group.innerHTML=data;
                        group.querySelector(".update-detail-btn").classList.remove('active');

                    },
                    error: function(error) {
                        $('#server-response').html(error.responseText);
                    }
                });
            }

            function lockDetail(event) {
                var id = event.currentTarget.closest('.group').querySelector('input[name="id"]').value;
                $.ajax({
                    type: "POST",
                    url: "adminproduct?action=lockdetail",
                    data: {id: id},
                    success: function(data) {
                        document.querySelector('#detail'+id).classList.remove('active');
                        document.querySelector('#detail'+id).classList.add('locked');
                        $('#server-response').html(data);
                        console.log(data);
                        hideModal('#lock-detail-confirm-modal');


                    },
                    error: function(error) {
                        $('#server-response').html(error.responseText);
                    }
                });
            }

            function activeDetail(event) {
                var id = event.currentTarget.closest('.group').querySelector('input[name="id"]').value;
                $.ajax({
                    type: "POST",
                    url: "adminproduct?action=activeDetail",
                    data: {id: id},
                    success: function(data) {
                        document.querySelector('#detail'+id).classList.remove('locked');
                        document.querySelector('#detail'+id).classList.add('active');
                        $('#server-response').html(data);
                        console.log(data);
                        hideModal('#unlock-detail-confirm-modal');


                    },
                    error: function(error) {
                        $('#server-response').html(error.responseText);
                    }
                });
            }

            function deleteDetail(event) {
                var id = event.currentTarget.closest('.group').querySelector('input[name="id"]').value;
                console.log("xóa: " + id);
                $.ajax({
                    type: "POST",
                    url: "adminproduct?action=deleteDetail",
                    data: {id: id},
                    success: function(data) {
                        document.querySelector('#product'+id).style.display = 'none';
                        $('#server-response').html(data);
                        console.log(data);
                        hideModal('#delete-detail-confirm-modal');


                    },
                    error: function(error) {
                        $('#server-response').html(error.responseText);
                    }
                });
            }
        </script>
    <div id="server-response"></div>
    </main><!-- End #main -->
</div>

</body>
</html>