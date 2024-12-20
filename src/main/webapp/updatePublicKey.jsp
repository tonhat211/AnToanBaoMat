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
    <title>Cập nhật Public Key</title>

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
<div class="content-container" style="display: flex;justify-content: center;">
    <div class="sub-content" style="width: 50%;">
        <form action="updatePublicKey" method="POST" id="update-public-key-form">
            <input type="text" name="action" value="update" hidden onchange="previewPublicKey(event)">
            <div class="flex-coll" style="justify-content: center;margin:22px 0;font-size: 19px">
                <h3>Cập nhật public key</h3>
                <p class="note">Nếu bạn chưa có key mới, bạn có thể bỏ trống key
                    </br> Khi có key mới
                    </br> Hãy thực hiện lại báo cáo lộ key với ĐÚNG thời gian bạn đã nhập.
                </p>
                <div class="form-group grid-col-6" style="margin-top: 10px;">
                    <label>Thời gian lộ key</label>
                    <input type="datetime-local" name="time" required>
                </div>
                <div class="form-group grid-col-6" style="margin-top: 10px;">
                    <div class="flex-roww" >
                        <input type="file" multiple data-max_length="20" accept=".txt" onchange="previewPublicKey(event);" style="margin: 5px 0;">
                    </div>
                    <textarea contenteditable="true" id="update-public-key" type="text" class="form-control" name="public-key" placeholder="Nhập public key"></textarea>
                </div>
            </div>
            <div class="flex-roww" style="justify-content: space-around; margin-top: 20px">
                <button class="btn btn-outline-primary btn-cancel-filter" onclick="removeModal('#modal-container');"><i class="bi bi-x-lg"></i> Hủy</button>
                <button class="btn btn-primary" type="submit">Cập nhật</button>
            </div>
        </form>
    </div>
    <script>
        function previewPublicKey(event) {
            const file = event.target.files[0];
            if (file && file.type === "text/plain") { // Kiểm tra định dạng file
                const reader = new FileReader(); // Tạo đối tượng FileReader
                reader.onload = function(e) {
                    const content = e.target.result; // Lấy nội dung file
                    document.querySelector('#update-public-key').value = content; // Hiển thị nội dung file
                };
                reader.readAsText(file); // Đọc file dưới dạng văn bản
            } else {
                alert("Please upload a valid .txt file."); // Thông báo khi file không hợp lệ
            }
        }

    </script>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>