var btn = document.querySelector(".btn");
var tele = document.getElementById("tele");
var stuid = document.getElementById("stuid");
var password = document.getElementById("password");
var password2 = document.getElementById("password2");
window.onload = function(){
    btn.onclick=function(){
        if(tele.value == ""){
            alert("请输入手机号！");
            return false;
        }
        else if(stuid.value == ""){
            alert("请输入学号！");
            return false;
        }
        else if(password.value == ""){
            alert("请输入您的密码！");
            return false;
        }
        else if(password2.value == ""){
            alert("请确认您的密码！");
            return false;
        }
        else{
            $.ajax({
                'url':'http://101.133.239.170:8080/login/register',
                'type':'post',
                contentType:'application/json',
                'dataType':'text',
                'data':JSON.stringify({'phone':tele.value,'userid':stuid.value,'password':password.value,'password2':password2.value}),
                success:function (data) {
                    if(data == 'success'){
                        alert("注册成功");
                        window.location.href = "http://101.133.239.170:8080/templates/userLogin.html";
                    }
                    else
                        alert("注册失败")
                },
                error:function () {
                    alert("未知错误，请重试！")
                }
            });
        }
    }
}