// 从URL中获取商品id
function getQueryString(itemid) {
    var reg = new RegExp("(^|&)"+ itemid +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null){
        return  decodeURI(r[2]);
    }
    return null;
}


// 加载商品信息
window.onload = function(){
    // 获取商品id
    var itemid = getQueryString("itemid");
    // 添加图片
    function addImg(obj,src){
        var Img=document.createElement("Img");
        Img.setAttribute("style","width:150px;height:150px;padding:0 0 0 5px");
        Img.setAttribute("src","../pictures/"+src);
        obj.appendChild(Img);
    }
    
    $.ajax({
        'url':"http://101.133.239.170:8080/requires/require/" + itemid,
        'type':'GET',
        // 'dataType':'json',
        success:function (res) {
            // 导入商品信息
            document.getElementsByClassName("goods_det")[0].innerHTML=res.requireItem.ritemname;
            document.getElementsByClassName("send_date")[0].innerHTML=res.requireItem.date;
            var A_Img = document.getElementsByClassName("owner_image")[0];
            var srcs = res.requireItem.itemPicture
            if(srcs != null){
                for(key in srcs)
                {
                    if(key != "itemid" && srcs[key]!= null){
                        addImg(A_Img,srcs[key])
                    }
                }
            }else{
                srcs = "增加图片例图.png"
                addImg(A_Img,srcs)
            }
            // 导入用户信息
            document.getElementById("user_name").setAttribute("href","../templates/other.html?userid="+res.requireItem.host)
            document.getElementById("user_n").innerHTML=res.user.name;
            document.getElementById("user_a").innerHTML=res.user.schoolzone;
            var A_Img = document.getElementById("user_image");
            A_Img.setAttribute("href","../templates/other.html?userid="+res.requireItem.host);
            
            var Img=document.createElement("Img");
            Img.setAttribute("style","width:20px;height:20px; border-radius: 50%;");
            if(res.headpicture != null){
                var src = res.headpicture.headpicture
                Img.setAttribute("src","../pictures/"+src);
            }else{
                var src = "增加图片例图.png"
                Img.setAttribute("src","../pictures/"+src);
            }
            A_Img.appendChild(Img);
            // 导入评论信息
            for(i=0;i<res.comments.length;i++){
                var mes = res.comments[i].comment;
                var userid = res.comments[i].userid;
                var date = res.comments[i].datetime;
                
                if(res.comments[i].headpicture!=null && res.comments[i].name!=null){
                    var src = res.comments[i].headpicture;
                    var name = res.comments[i].name;
                    addLi(src,name,mes,date,userid)
                }else if(res.comments[i].headpicture==null && res.comments[i].name!=null){
                    var src= "增加图片例图.png";
                    var name = res.comments[i].name;
                    addLi(src,name,mes,date,userid)
                }else if(res.comments[i].name == null && res.comments[i].headpicture!=null){
                    var src = res.comments[i].headpicture;
                    var name = "佚名";
                    addLi(src,name,mes,date,userid)
                }else{
                    var src= "增加图片例图.png";
                    var name = "佚名";
                    addLi(src,name,mes,date,userid)
                }
            }
        },
        error:function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("请求失败")
            alert(XMLHttpRequest.status);
            alert(XMLHttpRequest.readyState);
            alert(textStatus);
        }
    })
}


// 定位到“发送”按钮
var sendbtn = document.getElementById("send_button")

// 点击事件
sendbtn.onclick=function(){
  var mes = send_words();
  // 获取商品id
  var itemid = getQueryString("itemid");
// 点击发送按钮后向后端发送留言内容，留言时间
$.ajax({
    'url':"http://101.133.239.170:8080/items/add/comment",
    'type':'POST',
    "data":{
      "itemid":itemid,
      "comment":mes
    },
    'dataType':'json',
    success:function () {
      // alert("success");
      window.location.href = "http://101.133.239.170:8080/templates/rePurchaseDt.html?itemid=" + itemid
    },
    error:function () {
      console.log("请求失败")
    }
  })
}

// 获取框中的留言
function send_words() {
    cancelBubble =true;
    var inputDom = document.getElementById("send_input");
    var words = inputDom.value;
    return words;
}

//创建li标签，包含显示姓名，邮箱，电话号码及删除按钮
function addLi(photo,name,mes,date,userid){
    var li_1=document.createElement("li");
    li_1.setAttribute("class","inf_list")
    var A_1 = createA()
    A_1.setAttribute("href","other.html?userid="+userid)
    addImg(A_1,photo)
    var span_1 = createSpan()
    span_1.setAttribute("class","user_name")
    span_1.innerHTML=name;
    var  A_2 = createA()
    A_2.appendChild(span_1)
    A_2.setAttribute("href","other.html?userid="+userid)
    var span_2 =createSpan()
    span_2.setAttribute("class","user_date")
    span_2.innerHTML=date;
    var p_1=document.createElement("p");
    var span_3 = createSpan();
    span_3.setAttribute("class","user_message");
    span_3.innerHTML=mes;
    p_1.appendChild(span_3);
    li_1.appendChild(A_1);
    li_1.appendChild(A_2);
    li_1.appendChild(span_2);
    li_1.appendChild(p_1);
    var ul_1 = document.getElementsByClassName("m_list_inf1")[0];
    ul_1.appendChild(li_1);
    
}

function createA(){
    var A=document.createElement("a");
    A.setAttribute("class","user");
    A.setAttribute("href","#");
    return A;
}

function createSpan(){
    var span=document.createElement("span");
    return span;
}



function addImg(obj,srcs){
    var Img=document.createElement("Img")
    Img.setAttribute("style" ,"width:20px;height:20px; border-radius: 50%");
    Img.setAttribute("src","../pictures/"+srcs)
    obj.appendChild(Img);
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