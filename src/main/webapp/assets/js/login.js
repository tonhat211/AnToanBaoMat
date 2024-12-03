// hien mat khau
function showPassword(event) {
    const btn = event.currentTarget.closest("button")
    btn.innerHTML="<i class=\"bi bi-eye\" onclick=\"hidePassword(event);\"></i>";
    const input = btn.parentNode.querySelector("input");
    input.type = "text";
}

// an mat khau
function hidePassword(event) {
    const btn = event.currentTarget.closest("button")
    btn.innerHTML="<i class=\"bi bi-eye-slash\" onclick=\"showPassword(event);\"></i>";
    const input = btn.parentNode.querySelector("input");
    input.type = "password";
}

function login(email, password,page) {
    var contextPath = "${pageContext.request.contextPath}";
    console.log(window.location.href);
    $.ajax({
        url: "login",
        method: "POST",
        data: { action: "check",email: email, password : password, page: page},
        success: function(data) {
            console.log(data);
            $("#login-response").html(data);
        }
    });
}

function tellWrongPassword() {
    console.log("wrong pwd");
    const errorSpan =document.querySelector("#login-form .pwd-error");
    errorSpan.innerText = "Sai mật khẩu";
    errorSpan.classList.add('active');
    const pwdInput = document.querySelector('#login-form input[name="password"]');
    pwdInput.value = '';
}

function forward(url) {
    window.location.href = url;
}

// sign up
function signup(name,email,password,repassword) {
    //  nhung loi: mat khau yeu, mat khau khong khop, email da duoc dang ky
    const regex = /^(?=.*[a-z])(?=.*[A-Z]).{8,}$/; //test mat khau: it nhat 8 kí tu, chu thuong, chu hoa

    if(!regex.test(password)){
        console.log("mat khau yeu");
        hideSpan("#signup-form .email-error");
        tellWeakPassword("#signup-form .pwd-error","#signup-form .repwd-error",'#signup-form input[name="password"]','#signup-form input[name="repassword"]');
    } else if(password != repassword) {
        console.log("mat khau khong khop");
        hideSpan("#signup-form .email-error");
        tellNotSamePassword("#signup-form .pwd-error");
        tellNotSamePassword("#signup-form .repwd-error");
    } else {
        console.log("goi servlet singup");
        $.ajax({
            url: "signup",
            method: "POST",
            data: { action: "signup",name: name, email: email, password : password,repassword: repassword},
            success: function(data) {
                console.log(data);
                $("#signup-response").html(data);
            }
        });
    }
}


// thong bao mat khau yeu
function tellWeakPassword(e1,e2,i1,i2) {
    console.log("not same pwd");
    const errorSpan =document.querySelector(e1);
    errorSpan.innerText = "Mật khẩu phải có ít nhất 8 kí tự, bao gồm chữ hoa, chữ thường";
    errorSpan.classList.add('active');
    hideSpan(e2);
    document.querySelector(i1).value = '';
    document.querySelector(i2).value = '';

}

// thong bao mat khau khong khop
function tellNotSamePassword(e1) {
    console.log("not same pwd");
    const errorSpan =document.querySelector(e1);
    errorSpan.innerText = "Mật khẩu không khớp";
    errorSpan.classList.add('active');

}

//an error
function hideSpan(e) {
    const E = document.querySelector(e);
    if(E.classList.contains('active')) {
        E.classList.remove('active');
    }
}

// thong bao email da duoc dung de dang ky truoc do
function tellUsedEmail() {
    hideSpan("#signup-form .pwd-error");
    hideSpan("#signup-form .repwd-error");
    console.log("email da duoc dang ky truoc do");
    const errorSpan =document.querySelector("#signup-form .email-error");
    errorSpan.innerText = "Email đã được dùng để đăng ký trước đó";
    errorSpan.classList.add('active');
}