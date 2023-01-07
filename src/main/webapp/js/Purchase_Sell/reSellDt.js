
// 获取弹窗
var modal = document.getElementById('myModal');

// 打开弹窗的按钮对象
var btn = document.getElementById("button_sale");

// 获取 <span> 元素，用于关闭弹窗
var span = document.querySelector('.close');
// 点击按钮打开弹窗
btn.onclick = function() {
    modal.style.display = "block";
}

// 点击 <span> (x), 关闭弹窗
span.onclick = function() {
    modal.style.display = "none";
}

// 在用户点击其他地方时，关闭弹窗
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
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
          window.location.href = "http://101.133.239.170:8080/templates/reSellDt.html?itemid=" + itemid
        },
        error:function () {
          console.log("请求失败")
          // alert(XMLHttpRequest.status);
          // alert(XMLHttpRequest.readyState);
          // alert(textStatus);
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
        'url':"http://101.133.239.170:8080/items/item/" + itemid,
        'type':'GET',
        // 'dataType':'json',
        success:function (res) {
            // 导入商品信息
            document.getElementsByClassName("goods_det")[0].innerHTML=res.item.itemname;
            document.getElementsByClassName("send_date")[0].innerHTML=res.item.datetime;
            var A_Img = document.getElementsByClassName("owner_image")[0];
            var srcs = res.item.itempicture
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
            document.getElementById("user_name").setAttribute("href","../templates/other.html?userid="+res.item.host)
            document.getElementById("user_n").innerHTML=res.user.name;
            document.getElementById("user_a").innerHTML=res.user.schoolzone;
            document.getElementsByClassName("goods_way")[0].innerHTML=res.item.type;
            document.getElementsByClassName("b_name")[0].innerHTML=res.item.price;
            var A_Img = document.getElementById("user_image");
            A_Img.setAttribute("href","../templates/other.html?userid="+res.item.host);
            
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

//创建li标签
function addLi(photo,name,mes,date,userid){
    var li_1=document.createElement("li");
    li_1.setAttribute("class","inf_list")
    var A_1 = createA()
    addImg(A_1,photo)
    A_1.setAttribute("href","other.html?userid="+userid)
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




// 从URL中获取商品id
function getQueryString(itemid) {
    var reg = new RegExp("(^|&)"+ itemid +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null){
        return  decodeURI(r[2]);
    }
    return null;
}



// 点击加入购物车后向后端发送商品ID
var cartbtn = document.getElementById("button_cart");
cartbtn.onclick = function(){
  // 获取商品id
  var itemid = getQueryString("itemid");
  // 向后台发送要加入购物车的商品id
  $.ajax({
    'url':"http://101.133.239.170:8080/items/add/shoppingCart/" + itemid,
    'type':'POST',
    // "data":{
    //   "itemid":itemid
    // },
    'dataType':'text',
    success: function () {
      alert("添加成功");
    },
    error: function () {
      alert("请求失败")
    }
  })
}

// 点击立即购买后向后端发送商品ID
var salebtn = document.getElementById("button_sale");
salebtn.onclick = function(){
  // 获取商品id
  var itemid = getQueryString("itemid");
  // 向后台发送要加入购物车的商品id
  $.ajax({
    'url':"http://101.133.239.170:8080/order/add/order/" + itemid,
    'type':'POST',
    // "data":{
    //   "itemid":itemid
    // },
    'dataType':'json',
    'success':function () {
      alert("success");
    },
    error:function (XMLHttpRequest, textStatus, errorThrown) {
      console.log("请求失败")
      alert(XMLHttpRequest.status);
      alert(XMLHttpRequest.readyState);
      alert(textStatus);
    }
  })
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