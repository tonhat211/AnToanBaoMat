<%@ page import="model.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Quản lý khách hàng</title>
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
    <link href="./assets/css/adminCustomer.css" rel="stylesheet">


</head>
<body>

<%@ include file="adminHeader.jsp" %>
<div class="flex-roww">
    <%@ include file="adminMenu.jsp" %>
    <%
        ArrayList<User> customers = (ArrayList<User>) request.getAttribute("customers");
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
    <main id="main" class="main grid-col-10">
        <div id="toast"></div>
        <div class="pagetitle grid-col-3">
            <h1>Quản lý khách hàng</h1>
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="index">Trang chủ</a></li>
                    <li class="breadcrumb-item active">Quản lý khách hàng</li>
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
                <a href="" id="add-product-btn">Thêm tài khoản</a>
            </div>
            <div class="info-container">
                <table style="width: 100%;" id="infoTable" class="info-table">
                    <thead>
                    <tr>
                        <th scope="col" class="grid-col-0_5 text-center" style="height: fit-content;">STT</th>
                        <th scope="col" class="grid-col-0_5 text-center">ID</th>
                        <th scope="col" class="grid-col-2">Tên</th>
                        <th scope="col" class="grid-col-2">Email</th>
                        <th scope="col" class="grid-col-1">Đơn hàng</th>
                        <th scope="col" class="grid-col-1">Thao tác</th>
                    </tr>
                    </thead>
                    <tbody class="group">
                    <tr class="group" id="detail-container">
                        <td style="width: 100%" colspan="11">
                            <table id="detail-table">
                                <thead>
                                <tr>
                                    <th scope="col" class="grid-col-1" style="height: fit-content;">Giới tính</th>
                                    <th scope="col" class="grid-col-1">Ngày sinh</th>
                                    <th scope="col" class="grid-col-1">Điện thoại</th>
                                    <th scope="col" class="grid-col-1">Ngày tham gia</th>
                                </tr>
                                </thead>
                                <tbody class="group">
                                <tr class="group">
                                    <th scope="row" class="grid-col-1" >
                                        <span class="gender">1</span>
                                    </th>
                                    <td class="grid-col-1">
                                        <span class="birthday">1</span>
                                    </td>
                                    <td class="grid-col-1">
                                        <input class="info-input" type="text" name="phone" value="01324523" readonly>
                                    </td>
                                    <td class="grid-col-1">
                                        <input class="info-input" type="text" name="dateIn" readonly>
                                    </td>
                                </tr>

                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <%
                        int stt=1;
                        for(User u : customers) {
                    %>
                            <tr class="group <%=u.isLocked()?"locked":""%>" id="customer<%=u.getId()%>" onclick="showDetail(event);">
                                <th scope="row" class="grid-col-0_5 text-center" >
                                    <span class="stt"><%=stt++%></span>
<%--                                    <span class="stt">1</span>--%>
                                </th>
                                <td class="grid-col-0_5 text-center product-id">
                                    <span class="id"><%=u.getId()%></span>
                                </td>
                                <td class="grid-col-2">
                                    <span class="name"><%=u.getName()%></span>
                                </td>
                                <td class="grid-col-2">
                                    <span class="email"><%=u.getEmail()%></span>
                                </td>
                                <td class="grid-col-1 text-center">
                                    <a href="adminorder?action=orderof&id=<%=u.getId()%>">Đơn hàng</a>
                                </td>
                                <td class="grid-col-1" onclick="event.stopPropagation();">
                                    <select name="action" onchange="handleChange(event);" data-default="none">
                                        <option value="none">...</option>
                                        <option class="lock-option" value="lock">Khóa</option>
                                        <option class="unlock-option" value="unlock">Mở khóa</option>
                                        <option value="issuePassword">Cấp mật khẩu</option>
                                        <option value="delete">Xóa</option>
                                    </select>
                                </td>
                            </tr>
                    <%
                        }
                    %>


                    </tbody>
                </table>


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
                    <form action="admincustomer" method="post" style="width: 100%">
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
        <div class="modall" id="add-modal">
            <div class="modal__overlay" onclick="closeModal(event);">
<%--            <div class="modal__overlay">--%>
                <div class="modall-content grid-col-6 custom-scroll" style="max-height: 90%;" onclick="event.stopPropagation();">
                    <form action="admincustomer" method="POST" id="addCustomerForm">
                        <h4 class="confirm-content" style="text-align: center">Thêm tài khoản khách hàng mơi</h4>
                        <div class="flex-roww" style="justify-content: space-between; margin-top: 10px;">
                            <div class="form-group grid-col-4">
                                <label>Tên khách hàng</label>
                                <input type="text" class="form-control" name="name" aria-describedby="emailHelp" placeholder="Nhập tên khách hàng" required="">
                            </div>
                        </div>
                        <div class="flex-roww" style="justify-content: space-between; margin-top: 10px;">
                            <div class="form-group grid-col-4">
                                <label>Email khách hàng</label>
                                <input type="text" class="form-control" name="email" aria-describedby="emailHelp" placeholder="Nhập email khách hàng" required="">
                            </div>
                        </div>
                        <div class="flex-roww" style="justify-content: space-between; margin-top: 15px;">
                            <div class="form-group grid-col-4">
                                <label>Ngày tham gia</label>
                                <input type="date" class="form-control" name="dateIn">
                            </div>
                            <div class="form-group grid-col-4">
                                <label>Mật khẩu mặc định</label>
                                <input type="text" class="form-control" name="defaultPassword">
                            </div>
                        </div>
                        <input type="text" name="action" value="add" hidden>
                        <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                            <button class="btn btn-fourth btn-cancel" type="button" onclick="closeModal(event);">Hủy</button>
                            <button class="btn btn-primary btn-confirm" type="submit">Thêm</button>
                        </div>
                    </form>

                    <script>
                        // Lấy ngày hiện tại
                        const today = new Date();
                        var day = today.getDate();
                        day=(day < 10) ? '0' + day : day;
                        var month = today.getMonth() + 1;
                        month=(month < 10) ? '0' + month : month;
                        var year = today.getFullYear();
                        const formattedDate = year+'-'+month+'-'+day;
                        console.log(formattedDate);
                        // Gán giá trị ngày cho input date
                        document.querySelector('#addCustomerForm input[name="dateIn"]').value = formattedDate;
                        document.querySelector('#addCustomerForm input[name="defaultPassword"]').value = day+""+month+year;

                    </script>


                </div>
            </div>
        </div>
        <script>
            function closeModal(event) {
                event.currentTarget.closest(".modall").classList.remove("active");
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
                    case "LOCK": {
                        setInfosStatus(group,false);
                        var name = group.querySelector(".name").innerText;
                        var id = group.querySelector(".id").innerText;
                        setupConfirm(id,'#lock-confirm-modal',name);
                        break;
                    }
                    case "UNLOCK": {
                        setInfosStatus(group,false);
                        var name = group.querySelector(".name").innerText;
                        var id = group.querySelector(".id").innerText;
                        setupConfirm(id,'#unlock-confirm-modal',name);
                        break;
                    }
                    case "DELETE": {
                        setInfosStatus(group,false);
                        var name = group.querySelector(".name").innerText;
                        var id = group.querySelector(".id").innerText;
                        setupConfirm(id,'#delete-confirm-modal',name);
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
                    url: "admincustomer?action=showdetail",
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
                    url: "admincustomer?action=lock",
                    data: {id: id},
                    success: function(data) {
                        document.querySelector('#customer'+id).classList.remove('active');
                        document.querySelector('#customer'+id).classList.add('locked');
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
                    url: "admincustomer?action=active",
                    data: {id: id},
                    success: function(data) {
                        document.querySelector('#customer'+id).classList.remove('locked');
                        document.querySelector('#customer'+id).classList.add('active');
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
                    url: "admincustomer?action=delete",
                    data: {id: id},
                    success: function(data) {
                        document.querySelector('#customer'+id).style.display = 'none';
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