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
                    url: "/user",
                    type: "POST",
                    data: JSON.stringify(user),
                    contentType: "application/json; charset=utf-8"
                }).fail( function (data){
                    console.log(data);
                });
            }
        }, checkPass : function () {
            var password = document.getElementById("passwd"), confirm_password = document.getElementById("repasswd");
            if (password.value == confirm_password.value) {
                confirm_password.setCustomValidity(''); return true;
            } else {
                confirm_password.setCustomValidity("Passwords Don't Match"); return false;
            }
        }, signIn : function () {
            var user = {id : 0, nickname: $("#nickName").val(), email : "", password: $("#passwd").val()};
            console.info("send: " + JSON.stringify(user));
            jQuery.ajax({
                url: "/login",
                type: "POST",
                data: JSON.stringify(user),
                contentType: "application/json; charset=utf-8"
            }).fail( function (data){
                console.log(data);
            });
            alert("logged");
        }
    };

})();