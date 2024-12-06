<%@ page import="model.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thietbididong.com</title>

    <!-- thu vien jquery   -->
    <script type="text/javascript" src="./assets/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="./assets/js/js_bootstrap4/bootstrap.min.js"></script>
    <script type="text/javascript" src="./assets/js/header.js"></script>
    <script type="text/javascript" src="./assets/js/login.js"></script>
    <script type="text/javascript" src="./assets/js/smartphone.js"></script>
    <script type="text/javascript" src="./assets/js/toast.js"></script>

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
    <link href="./assets/css/header.css" rel="stylesheet">
    <link href="./assets/css/toast.css" rel="stylesheet">

    <!-- css tu them   -->
    <link href="./assets/css/smartphone.css" rel="stylesheet">
    <link href="./assets/css/index.css" rel="stylesheet">
    <link href="./assets/css/login.css" rel="stylesheet">

</head>
<body>
<%
    User userLogging = (User) session.getAttribute("userLogging");
//    userLogging = new User(1,"Minh Nhat","minhnhat@gmail.com");
%>

<div class="header pd10-20">
    <div id="toast-header" class="toast">

    </div>

    <div class="header_top grid__row flex-roww">
        <a href="index" class="logo grid-col-2 pd0">
            <img src="./assets/img/logo/Logo-The-Gioi-Di-Dong-MWG-B-H.webp" alt="" style="color:black;">
        </a>
        <form action="product" class="search-bar grid-col-4 ">
            <button style="border: none;background: none;"><i class="fa-solid fa-magnifying-glass"></i></button>
            <input type="text" name="action" value="Search" hidden>
            <input type="text" placeholder="Bạn tìm gì..." name="search-input">
        </form>
        <div class="user_part flex-roww grid-col-3">
            <%
                if(userLogging==null) {
            %>
            <div class="cart flex-roww" href="" onclick="openHeaderModal('.login-require-modal');">
                <i class="bi bi-cart3"></i>
                <p>Giỏ hàng</p>
            </div>
            <%
                } else {
            %>
                <a class="cart flex-roww" href="cart">
                    <i class="bi bi-cart3"></i>
                    <p>Giỏ hàng</p>
                </a>
            <%
                }
            %>
            <script>
                // function openModal(event, modal) {
                //     event
                // }
            </script>

            <div class="user flex-roww">
                <i class="bi bi-person"></i>
            <% if(userLogging==null) {
            %>
                <p><a href="login" >Đăng nhập</a></p>
            <%
                } else  {
            %>
                <p><%=userLogging.getName()%></p>
                <ul class="sub-menu">
                    <li>
                        <a href="order" class="li-order">Đơn hàng</a>
                    </li>
                    <li>
                        <a href="profile?action=addressBook" class="li-address" onclick="manageAddress(event);">Sổ địa chỉ</a>
                    </li>
                    <li>
                        <a href="profile?action=info" class="li-profile" onclick="manageProfile(event);">Quản lý</a>
                    </li>
                    <li>
                        <a href="login?action=logout" onclick="logout(event);" class="li-logout">Đăng xuất</a>
                    </li>
                </ul>
                <%
                    }
                %>
            </div>
        </div>
    </div>
    <div class="header_bottom grid__row">
        <ul class="main-menu flex-roww">
            <li>
                <a href="product"><i class="bi bi-phone"></i>
                    Điện thoại</a>
            </li>
            <li>
                <a href=""><i class="bi bi-tablet-landscape"></i>
                    Máy tính bảng</a>
            </li>
            <li>
                <a href=""><i class="bi bi-laptop"></i>
                    Laptop</a>
            </li>
        </ul>
    </div>
    <div id="modal-container">
        <!--   cap nhat so dia chi -->
        <div class="modall address-modal" onclick="removeModal('#modal-container');">
            <div class="modall-content" style="width: 80%; background-color: unset;">
                <div class="flex-roww" style="align-items: start;justify-content: space-between;height: 100%;">
                    <div class="sub-content" style="margin-right: 10px;height: 100%; padding-right: 5px;" onclick="event.stopPropagation();">
                        <h3 style="text-align: center;padding-right: 10px;">Sổ địa chỉ</h3>
                        <div class="update-container">
                            <div id="address-container" style="padding-right: 10px;">

                            </div>
                        </div>
                    </div>
                    <div class="sub-content"  style="margin-left: 10px;" onclick="event.stopPropagation();">
                        <form id="editAddressForm" action="profile">
                            <p class="bold-text-6 edit-title" style="text-align: center">Thêm địa chỉ</p>
                            <div class="flex-roww" style="margin-top: 10px">
                                <input type="text"  class="form-control w-80" id="receiver" name="receiver"  aria-describedby="" placeholder="Tên người nhận" value="">
                                <div style="margin:0 10px"></div>
                                <input type="text" class="form-control w-80" id="phone" name="phone"  aria-describedby="" placeholder="Số điện thoại nhận hàng" value="">
                            </div>
                            <p>Địa chỉ:</p>
                            <div style="margin-left:10px">
                                <input type="text" style="margin-top: 10px" class="form-control w-100" id="province" name="province"  aria-describedby="" placeholder="Thành phố/Tỉnh" value="">
                                <input type="text" style="margin-top: 10px" class="form-control w-100" id="district" name="district"  aria-describedby="" placeholder="Quận/Huyện" value="">
                                <input type="text" style="margin-top: 10px" class="form-control w-100" id="village" name="village"  aria-describedby="" placeholder="Phường/Xã" value="">
                                <input type="text" style="margin-top: 10px" class="form-control w-100" id="street" name="street"  aria-describedby="" placeholder="Chi tiết" value="">
                            </div>
                            <input type="text" class="form-control w-80" id="id" name="id"  aria-describedby="" placeholder="ID account" value="" hidden>
                            <input type="text" class="form-control w-80" name="action"  aria-describedby="" placeholder="ID account" value="addAddress" hidden>
                            <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                                <button class="btn btn-third" type="button" onclick="cancelEdit('#editAddressForm');">Hủy</button>
                                <button class="btn btn-primary btn-edit" type="submit">Lưu</button>
                            </div>
                        </form>
                        <script>
                            const updateAddressForm = document.querySelector('#editAddressForm');
                            updateAddressForm.addEventListener('submit', function(e) {
                                console.log(this);
                                console.log("call submit");
                                e.preventDefault();
                                var formData = new FormData(this);
                                let id = formData.get('id');
                                let receiver = formData.get('receiver');
                                let phone = formData.get("phone");
                                let street = formData.get("street");
                                let village = formData.get("village");
                                let district = formData.get("district");
                                let province = formData.get("province");
                                let action = formData.get("action");
                                if(action=="updateAddress") {
                                    updateAddressAjax(action,id,receiver,phone,street,village,district,province);
                                } else {
                                    addAddressAjax(action,id,receiver,phone,street,village,district,province);
                                }
                            });
                        </script>
                    </div>
                </div>

            </div>
            <div class="modal-confirm sub-content address-confirm" onclick="event.stopPropagation();" >
                <p class="bold-text-6 edit-title" style="text-align: center">Xác nhận xóa địa chỉ</p>
                <div class="address-item">
                    <div class="flex-roww" style="justify-content: space-between; align-items: start">
                        <div class="address-info">
                            <p>Người nhận: <span class="receiver">nhat</span></p>
                            <p>Số điện thoại: <span class="phone">091250154</span></p>
                            <p class="id" hidden>0</p>
                            <p>Địa chỉ: <span class="address">nong lam</span></p>
                        </div>
                    </div>
                </div>
                <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                    <button class="btn btn-third" type="button" onclick="cancelDelete(event,'.address-confirm');">Hủy</button>
                    <button class="btn btn-primary btn-confirm-delete" onclick="deleteAddress()" type="button">Xóa</button>
                </div>
                <script>
                    function deleteAddress() {
                        let id =document.querySelector('.address-confirm .id').innerText;
                        deleteAddressAjax(id);
                    }

                </script>
            </div>
        </div>


        <div class="modall info-modal" onclick="removeModal('#modal-container');">
            <div class="modall-content" style="width: 80%; background-color: unset;top: 35%;">
                <div class="flex-roww" style="align-items: start;justify-content: center;height: 100%;">
                    <div class="sub-content"  style="width: 50%;margin-top: 0" onclick="event.stopPropagation();">
                        <form id="editInfoForm" action="profile" method="post">
                        	<input type="text" name="action" value="updateInfo" hidden>
                            <h3 style="text-align: center;padding-right: 10px;">Thông tin cá nhân</h3>
                            <div class="flex-roww update-action" style="justify-content: right;">
                                <i class="bi bi-pencil-square btn-edit" onclick="initUpdateInfo(event,'#editInfoForm');"></i>
                            </div>
                            <p class="bold-text-6 edit-title" style="text-align: center;color: var(--bold-color);display: none;">Cập nhật thông tin</p>
							<div class="flex-roww" style="justify-content: space-between;">
                                <div class="form-group">
                                    <label>Họ tên</label>
                                    <input type="text" class="form-control" name="name" placeholder="Nhập họ và tên" required readonly>
                                </div>
                                <div class="form-group" style="margin-left: 10px;">
                                    <label>Ngày tham gia</label>
                                    <input type="text" class="form-control" name="dateIn" value="" required readonly>
                                </div>
							</div>
                            
                            <div class="form-group">
                                <label>Email</label>
                                <input type="email" class="form-control" name="email" aria-describedby="emailHelp" placeholder="Nhập email" required readonly>
                            </div>
                            <div class="flex-roww" style="justify-content: space-between;">
                                <div class="form-group">
                                    <label>Số điện thoại</label>
                                    <input type="text" class="form-control" name="phone" placeholder="Nhập số điện thoại" required readonly>
                                </div>
                                <div class="form-group" style="margin-left: 10px;">
                                    <label>Ngày sinh</label>
                                    <input type="date" class="form-control" name="birthday" placeholder="Nhập ngày sinh" required readonly>
                                </div>
                            </div>

                            <p>Giới tính:</p>
                            <div class="flex-roww" style="justify-content: space-around;margin-bottom: 10px;">
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="gender" id="exampleRadios1" value="male" checked readonly>
                                    <label class="form-check-label" for="exampleRadios1">
                                        Nam
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="gender" id="exampleRadios2" value="female" readonly>
                                    <label class="form-check-label" for="exampleRadios2">
                                        Nữ
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="gender" id="exampleRadios3" value="notToSay" readonly>
                                    <label class="form-check-label" for="exampleRadios3">
                                        Không muốn tiết lộ
                                    </label>
                                </div>
                            </div>

                            <a href="" onclick="initChangePwd(event,'#edit-pwd-form')">Đổi mật khẩu</a>
                            <div class="flex-roww btn-edit-container" style="margin-top:20px; justify-content: space-around; display: none;">
                                <button class="btn btn-third" type="button" onclick="cancelEditInfo(event,'#editInfoForm');">Hủy</button>
                                <button class="btn btn-primary btn-edit" type="submit">Lưu</button>
                            </div>
                        </form>
                        <script>
                    
	                        const updateInfoForm = document.querySelector('#editInfoForm');
	                        updateInfoForm.addEventListener('submit', function(e) {
	                            console.log(this);
	                            console.log("call submit");
	                            e.preventDefault();
	                            var formData = new FormData(this);
	                            let name = formData.get('name');
	                            let email = formData.get('email');
	                            let gender = formData.get("gender");
	                            let phone = formData.get("phone");
	                            let birthday = formData.get("birthday");
	                            let dateIn = formData.get("dateIn");
	                            let action = formData.get("action");

                                updateInfoAjax(action,name,email,gender,phone,birthday,dateIn);


	                        });

                            function updateInfoAjax(action,name,email,gender,phone,birthday,dateIn) {
                                console.log("call update info ajax");
                                console.log("action:" +action);
                                $.ajax({
                                    type: "post",
                                    url: "profile",
                                    data: {action: action ,name: name,email:email,gender:gender, phone: phone,birthday:birthday,dateIn:dateIn},
                                    success: function(data) {
                                        console.log(data);
                                        console.log("name: " + data.name);
                                        $('#editInfoForm input[name="name"]').val(data.name);
                                        $('#editInfoForm input[name="email"]').val(data.email);

                                        const info = JSON.parse(data.info);
                                        $('#editInfoForm input[name="dateIn"]').val(info.dateIn);
                                        $('#editInfoForm input[name="phone"]').val(info.phone);
                                        $('#editInfoForm input[name="gender"]').val(info.gender);
                                        $('#editInfoForm input[name="birthday"]').val(info.birthday);
                                        // cancelEdit('#editInfoForm');
                                        showSuccessToast('Cập nhật thông tin thành công','#toast-header');
                                    },
                                    error: function(error) {
                                        console.error("Error during querying info: ", error);
                                    }
                                });
                            }
                   
                        
                        </script>
                    </div>
                    <div class="sub-content" id="edit-pwd-form"  style="margin-left: 10px;width: 50%;margin-top: 0;" onclick="event.stopPropagation();">
                        <form id="editPasswordForm">
                            <input type="text" name="action" value="updatePassword" hidden>
                            <p class="bold-text-6 edit-title" style="text-align: center;color:var(--bold-color);">Đổi mật khẩu</p>
                            <div class="form-group">
                                <label for="current-password">Mật khẩu hiện tại</label>
                                <div class="flex-roww">
                                    <input type="password" class="form-control btrr0 bbrr0" id="current-password" name="currentPassword" placeholder="Nhập mật khẩu hiện tại" required><button class="btnShowPwd" type="button"><i class="bi bi-eye-slash" onclick="showPassword(event);"></i></button>
                                </div>
                                <span class="current-pwd-error">Sai mật khẩu</span>
                            </div>
                            <div class="form-group">
                                <label for="password">Mật khẩu mới</label>
                                <div class="flex-roww">
                                    <input type="password" class="form-control btrr0 bbrr0" id="password" name="password" placeholder="Nhập mật khẩu hiện mới" required><button class="btnShowPwd" type="button"><i class="bi bi-eye-slash" onclick="showPassword(event);"></i></button>
                                </div>
                                <span class="pwd-error">* Mật khẩu phải trên 8 ký tự</span>
                            </div>
                            <div class="form-group">
                                <label for="password">Nhập lại mật khẩu mới</label>
                                <div class="flex-roww">
                                    <input type="password" class="form-control btrr0 bbrr0" id="repassword" name="repassword" placeholder="Nhập lại mật khẩu hiện mới" required><button class="btnShowPwd" type="button"><i class="bi bi-eye-slash" onclick="showPassword(event);"></i></button>
                                </div>
                                <span class="repwd-error">* Mật khẩu phải trên 8 ký tự</span>

                            </div>
                            <div class="flex-roww" style="margin-top:20px; justify-content: space-around">
                                <button class="btn btn-third" type="button" onclick="cancelChangePwd('#edit-pwd-form');">Hủy</button>
                                <button class="btn btn-primary btn-edit" type="submit">Lưu</button>
                            </div>
                        </form>
                        <script>
                            document.querySelector("#editPasswordForm").addEventListener('submit', function(event) {
                                event.preventDefault();
                                console.log('signup form submit');
                                var formdata = new FormData(document.querySelector("#editPasswordForm"));
                                var action = formdata.get("action");
                                var currentPassword = formdata.get("currentPassword");
                                var password = formdata.get("password");
                                var repassword = formdata.get("repassword");
                                changePwd(action,currentPassword,password,repassword);
                            });
                            function changePwd(action,currentPassword,password,repassword) {
                                //  nhung loi: mat khau yeu, mat khau khong khop, email da duoc dang ky
                                const regex = /^(?=.*[a-z])(?=.*[A-Z]).{8,}$/; //test mat khau: it nhat 8 kí tu, chu thuong, chu hoa

                                if(!regex.test(password)){
                                    console.log("mat khau yeu");
                                    tellWeakPassword("#editPasswordForm .pwd-error","#editPasswordForm .repwd-error",'#editPasswordForm input[name="password"]','#editPasswordForm input[name="repassword"]');
                                } else if(password != repassword) {
                                    console.log("mat khau khong khop");
                                    tellNotSamePassword("#editPasswordForm .pwd-error");
                                    tellNotSamePassword("#editPasswordForm .repwd-error");
                                } else {
                                    console.log("goi servlet change pwd");
                                    $.ajax({
                                        url: "profile",
                                        method: "POST",
                                        data: { action: action, currentPassword: currentPassword, password : password,repassword: repassword},
                                        success: function(data) {
                                            console.log(data);
                                            $("#header-response").html(data);
                                        }
                                    });
                                }
                            }

                            function tellWrongCurrentPassword() {
                                console.log("wrong current pwd");
                                const errorSpan =document.querySelector("#editPasswordForm .current-pwd-error");
                                errorSpan.innerText = "Sai mật khẩu";
                                errorSpan.classList.add('active');
                                const pwdInput = document.querySelector('#editPasswordForm input[name="currentPassword"]');
                                pwdInput.value = '';
                            }
                        </script>
                    </div>

                </div>

            </div>
        </div>
        <div class="modall login-require-modal" onclick="removeModal('#modal-container');">
            <div class="modall-content" style="width: 80%; background-color: unset;">
                <div style="width: 100%; background-color: white;border-radius: 10px;padding: 20px;">
                    <div class="flex-roww" style="justify-content: center;margin:22px 0;font-size: 19px">
                        <p>Bạn chưa đăng nhập, vui lòng đăng nhập để sử dụng giỏ hàng</p>
                    </div>
                    <div class="flex-roww" style="justify-content: space-around; margin-top: 20px">
                        <button class="btn btn-outline-primary btn-cancel-filter" onclick="removeModal('.modal-filter-details')"><i class="bi bi-x-lg"></i> Hủy</button>
                        <a href="login?action=require&pageAction=forward&page=cart" class="btn btn-primary btn-filter" style="color: white;">Đăng nhập</a>
                    </div>
                </div>


            </div>
        </div>

    </div>
    <div id="header-response"></div>
</div>

</body>
</html>