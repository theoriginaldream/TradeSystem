var btn = document.querySelector(".btn");
var tele = document.getElementById("tele");
var password = document.getElementById("password");
var userid = "";
window.onload = function(){
    btn.onclick=function(){
        if(tele.value == ""){
            alert("请输入手机号！");
            return false;
        }
        else if(password.value == ""){
            alert("请输入密码！");
            return false;
        }
        else{
            $.ajax({
                'url':'http://localhost:8080/login/userLogin',
                'type':'post',
                'Content-Type':'application/x-www-form-urlencoded',
                'dataType':'text',
                'data':{"phone": tele.value,"password": password.value},
                success:function (data) {
                    if(data == 'error'){
                        alert("账号或密码错误")
                    }
                    else{
                        userid = data;
                        // console.log(userid);
                        if(userid == "00001"){
                            // window.open("../../templates/inform.html")
                            window.location.href = "http://localhost:8080/templates/inform.html"
                            }
                        else{
                            // window.open("../../templates/headpicture.html")
                            window.location.href = "http://localhost:8080/templates/headpicture.html"
                            }
                    }
                },
                error:function () {
                    alert("未知错误，请重试!")
                }
            });
        }
    }
}