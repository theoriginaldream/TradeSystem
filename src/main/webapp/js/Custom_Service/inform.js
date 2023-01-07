function openwindow(userid){
    //获取弹窗得div
    var modal = document.getElementById('myModal');
    // 获取 <span> 元素，用于关闭弹窗 （X）
    var span = document.getElementsByClassName("close")[0];
    //获取弹窗中得确定按钮
    var ok=document.getElementsByClassName("ok")[0];
    var text = document.querySelector("textarea");
    ok.onclick = function(){
        if(text.value == ""){
            alert("请输入内容！");
            return false;
        }else{
            var text1 = document.getElementById("reply").value
            $.ajax({
                'url':'http://101.133.239.170:8080/consult/add/reply',
                contentType:'application/x-www-form-urlencoded',
                'type':'post',
                // 'dataType':'text',
                'data': {'reply':text1,'consultid':userid},
                success:function () {
                    console.log("请求成功")
                    window.location.href = "http://101.133.239.170:8080/templates/inform.html"
                },
                error:function () {
                    alert("请求失败")
                }
            });
        }
    }
    //获取弹窗中得取消按钮
    var no=document.getElementsByClassName("no")[0];
    //窗体弹出
    modal.style.display = "block";
    //点击窗体取消按钮
    no.onclick=function(){
        //直接关闭窗口
        modal.style.display = "none";
    }
    // 点击 <span> (x), 关闭弹窗
    span.onclick = function() {
        //直接关闭窗口
        modal.style.display = "none";
    }
    // 在用户点击其他地方时，关闭弹窗
    window.onclick = function(event) {
        //点击窗口外内容，关闭窗口
        if (event.target == modal) modal.style.display = "none";
    }
}
window.onload = function(){
    var ul = document.querySelector(".m_list_inf1");

    $.ajax({
      'url':"http://101.133.239.170:8080/consult/show",
      'type':'GET',
      'dataType':'json',
      'success':function (res) {
        console.log(res)
        for(i=0;i<res.length;i++){
            var str1 = res[i].userid;
            var str2 = res[i].consult;
            var str3 = res[i].datetime;
            var str4 = res[i].consultid;
            addElement(str1,str2,str3,str4,ul);
            }
        }
    })
    //测试往消息通知里添加信息的语句
    // var str1 = '00002';
    // var str2 = "商品详细内容";
    // var str3 = "2022.11.23";
    
    // addElement(str1,str2,str3,ul);
}
function addElement(str1,str2,str3,str4,ul){
    // 1.创建元素
    var li = document.createElement("li");
    li.innerHTML = '<button onclick="openwindow(\''+ str4 +'\')">客户'+str1+'咨询了一条消息，请回复!</button>';
    li.innerHTML = li.innerHTML + '<span>'+ str2 + '</span>'+'<span>' + str3 +'</span>';
    li.className = "inf_list";
    // 2.添加元素
    ul.appendChild(li);
}

// 实现两个按钮的退出登录
var logoutbtn1 = document.getElementById("logout1")
logoutbtn1.onclick = function (){
    $.ajax({
        'url':'http://101.133.239.170:8080/login/logout',
        'type':'post',
        success:function () {
            console.log("请求成功")
            window.location.href = "http://101.133.239.170:8080/templates/userLogin.html"
        },
        error:function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("请求失败")
            alert(XMLHttpRequest.status);
            alert(XMLHttpRequest.readyState);
            alert(textStatus);
        }
    })
}

var logoutbtn2 = document.getElementById("logout2")
logoutbtn2.onclick = function (){
    $.ajax({
        'url':'http://101.133.239.170:8080/login/logout',
        'type':'post',
        success:function () {
            console.log("请求成功")
            window.location.href = "http://101.133.239.170:8080/templates/userLogin.html"
        },
        error:function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("请求失败")
            alert(XMLHttpRequest.status);
            alert(XMLHttpRequest.readyState);
            alert(textStatus);
        }
    })
}