var loginServices = (function () {
    var id = 0;
    var nickName = "";
    var email = "";
    var passw = "";


    return{
        signUp: function () {
            if ( loginServices.checkPass() ){
                var user = {id: 0, nickname: $("#nickName").val(), email: $("#email").val(), password: $("#passwd").val()};
                console.info("send: " + JSON.stringify(user));
                jQuery.ajax({
                url: "/users",
                type: "POST",
                data: JSON.stringify(user),
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function () {
                    //
                }
            });
                
            }
        }, checkPass : function () {
            var password = document.getElementById("passwd"), confirm_password = document.getElementById("repasswd");
            if (password.value == confirm_password.value) {
                confirm_password.setCustomValidity(''); return true;
            } else {
                confirm_password.setCustomValidity("Passwords Don't Match"); return false;
            }
        }
    };

})();