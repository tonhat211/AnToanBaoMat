function verifyCode(action,code) {
    console.log(code.length);
    if(code.length!=5) tellWrongCode();

    else {
        $.ajax({
            url: "/ThietBiDiDong/verify",
            method: "POST",
            data: { action: action, code: code},
            success: function(data) {
                console.log(data);
                $("#verifyCode-response").html(data);
            }
        });
    }

}

function tellWrongCode() {
    console.log("ma xac nhan sai");
    const errorSpan = document.querySelector("#otp-form .pwd-error");
    errorSpan.innerText = "Mã xác nhận không hợp lệ.";
    errorSpan.classList.add('active');
}

function tellExpiredCode() {
    const errorSpan = document.querySelector("#otp-form .pwd-error");
    errorSpan.innerText = "Mã xác nhận đã hết hiệu lực.";
    errorSpan.classList.add('active');
}

function tellVerifySuccessful() {
    document.querySelector(".otp-message").classList.add('active');
}