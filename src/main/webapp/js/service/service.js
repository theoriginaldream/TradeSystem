// var text = document.getElementById("textarea1").value;
var btn = document.querySelector(".m_list_btn");
var ul = document.querySelector(".m_list_content");
btn.onclick = function(){
    if(false){
        alert("请输入内容！");
        return false;
    }else{
        var text = document.getElementById("textarea1").value;
        $.ajax({
            'url':'http://localhost:8080/consult/add',
            'Content-Type':'multipart/form-data',
            'type':'post',
            'dataType':'text',
            'data':{"consult": text},
            success:function () {
                console.log("发送成功，请耐心等待客服回复")
            },
            error:function () {
                alert("请求失败")
            }
        });
        //1.创建元素
        var li = document.createElement("li");
        str = '<div class="m_list_time"> '+ formatDate() + '</div>';
        li.innerHTML = str + text;
        // li.innerHTML = str + text + '<a href="javascript:;">撤回</a>';
        li.className = "m_list_specific_content";
        // 2.添加元素
        ul.insertBefore(li,ul.children[0]);
    }
        // 3.删除元素  删除的是当前链接的li 即它的父亲
    // for (var i = 0;i<ul.children.length;i++){
    //     var a = ul.children[i].children[1];
    //     a.onclick = function(){
    //         ul.removeChild(this.parentNode);
    //         //从后端获取删除数据的consultid
    //         $.ajax({
    //             'url':'http://localhost:8080/consult/my',
    //             'type':'GET',
    //             'dataType':'json',
    //             'success':function (res) {
    //                 console.log(res[i].consultid)
    //                 var consultid=res[i].consultid
    //                 $.ajax({
    //                     'url':'http://localhost:8080/consult/delete/' + consultid,
    //                     'Content-Type':'application/x-www-form-urlencoded',
    //                     'type':'POST',
    //                     // 'dataType':'text',
    //                     // 'data':{'consultid' : consultid},
    //                     success:function () {
    //                         console.log("请求成功")
    //                     },
    //                     error:function () {
    //                         alert("请求失败")
    //                     }
    //                 });
    //               },
    //             error:function () {
    //                 alert("请求失败")
    //             }
    //         })
    //         // 向后端传回删除的命令
    //
    //     }
    // }
}
function formatDate(){
    //获取系统当前时间
    var  date = new  Date();
    //获取年月日时分秒
    var str = date.getFullYear()+"-"+(parseInt(date.getMonth())+1)+"-"+
                date.getDate()+" "+ date.getHours()+":"+ date.getMinutes()+":"+date.getSeconds();
    return  str;
}

// 实现两个按钮的退出登录
var logoutbtn1 = document.getElementById("logout1")
logoutbtn1.onclick = function (){
    $.ajax({
        'url':'http://localhost:8080/login/logout',
        'type':'post',
        success:function () {
            console.log("请求成功")
            window.location.href = "http://localhost:8080/templates/userLogin.html"
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
        'url':'http://localhost:8080/login/logout',
        'type':'post',
        success:function () {
            console.log("请求成功")
            window.location.href = "http://localhost:8080/templates/userLogin.html"
        },
        error:function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("请求失败")
            alert(XMLHttpRequest.status);
            alert(XMLHttpRequest.readyState);
            alert(textStatus);
        }
    })
}