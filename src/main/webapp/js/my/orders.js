const showMenu = (toggleId,navbarId,bodyId)=>{
  const toggle = document.getElementById(toggleId),
  navbar = document.getElementById(navbarId);
  bodypadding = document.getElementById(bodyId);

  if(toggle && navbar){
      toggle.addEventListener('click',()=>{
          navbar.classList.toggle('expander')
          bodypadding.classList.toggle('body-pd')
      })
  }
}

showMenu('nav-toggle','navbar','body-pd')


const linkColor = document.querySelectorAll(".nav_link")
function colorLink(){
  linkColor.forEach(l=> l.classList.remove('active'))
  this.classList.add('active')
}
linkColor.forEach(l=> l.addEventListener('click',colorLink))

const linkCollapse = document.getElementsByClassName('collapse__link')
var i

for(i=0;i<linkCollapse.length;i++){
  linkCollapse[i].addEventListener('click',function(){
      const collapseMenu = this.nextElementSibling
      collapseMenu.classList.toggle('showCollapse')

      const rotate = collapseMenu.previousElementSibling
      rotate.classList.toggle("")
  })
}

// 获取所有加减按钮(得到的是一个数组)
var add = document.querySelectorAll(".Increase");
var reduce = document.querySelectorAll(".Reduce");
// 获取所有文本框
var inputs = document.querySelectorAll(".unum");
// 获取所有行
var rows = document.querySelectorAll(".row");
// 获取所有单选框 
var chooses = document.querySelectorAll(".choose");
// 获取所有全选按钮
var choose_alls = document.querySelectorAll(".choose_all");
// 删除选中的商品
var del_check = document.querySelector(".del_check");


// 获取表单数据
window.onload = function () {
  $.ajax({
      url: "http://101.133.239.170:8080/order/mine",
      type: 'get',
      dataType: 'json',
      // data: {name:"你好"},
      success:return_json
  });
  function return_json(json){
      console.log(json.length);
      for(var i = 0; i < json.length; i++) {
          if (json[i].itempicture==null){
              json[i].itempicture={
                  itempicture: "搜索.png"
              }
          }
         addlist(json[i].orderid,json[i].itempicture.itempicture,json[i].itemname,json[i].price,
         json[i].type,json[i].status,json[i].datetime);
      }
          // 获取所有删除按钮
      var btn_dels = document.querySelectorAll(".btn-del");
      // console.log(btn_dels.length);
      // 删除当前行
      for(var i=0; i<btn_dels.length; i++){
        if(btn_dels[i].innerText === "确认收货"){
          btn_dels[i].onclick = function(){
            con = confirm( "是否确认收货?" );
            if(con == true){
              // console.log(this.parentNode.parentNode.querySelector(".order_id").innerHTML);
              received(this.parentNode.parentNode.querySelector(".order_id").innerHTML); //确认收货的订单号
              var tr = this.parentNode.parentNode;
              var tds = tr.querySelector(".btn-del");
              tds.innerText ="移除";
              secendclick();
            }
          }
        }
        else{
          btn_dels[i].onclick = function(){
            console.log(this.parentNode.parentNode.querySelector(".order_id").innerHTML); //获取当前订单号
            del_order(this.parentNode.parentNode.querySelector(".order_id").innerHTML); //删除订单
            var tr = this.parentNode.parentNode;
            tr.parentNode.removeChild(tr);
          }  
        }   
      }
      // 一次点击：确认收货变为移除，二次点击：移除订单
      function secendclick(){
        for(var i=0; i<btn_dels.length; i++){
          btn_dels[i].onclick = function(){
            console.log(this.parentNode.parentNode.querySelector(".order_id").innerHTML); //获取当前订单号
            del_order(this.parentNode.parentNode.querySelector(".order_id").innerHTML); //删除订单
            var tr = this.parentNode.parentNode;
            tr.parentNode.removeChild(tr);
          }   
        }
      }

      // 删除选中行
      del_check.onclick = function(){
        rows = document.querySelectorAll(".row");
        for(var i=0; i<rows.length; i++){
            var checkbox = rows[i].querySelector(".choose");
            var order_id = rows[i].querySelector(".order_id");
            console.log(order_id);
            if(checkbox.checked){
                del_order(order_id.innerHTML);
                rows[i].parentNode.removeChild(rows[i]);
            }
        }
      }
    }
  }
// 添加表单
function addlist(ID,IMG,CONTENT,PRICE,WAY,STATE,TIME){
  var lists = document.getElementById('lists');
  var tr1 = document.createElement("tr");
  tr1.className="row";

  var td1 = document.createElement("td");
  var input = document.createElement("input");
  input.type="checkbox";
  input.name="checkOne";
  input.className="choose";
  input.onclick="check()";
  
  var td2 = document.createElement("td");
  td2.className="order_id";

  var td3 = document.createElement("td");
  var img = document.createElement("img");
  img.className="imgclass";
  
  var td4 = document.createElement("td");
  var a4 = document.createElement("a");
  a4.class="content";
  
  
  var td5 = document.createElement("td");
  td5.className="subtotal";
  td5.name="price";
  
  var td6 = document.createElement("td");
  td6.className="delivery";

  var td7 = document.createElement("td");
  
  var td8 = document.createElement("td");
  var p8 = document.createElement("p");
  p8.id="time";
  
  var td9 = document.createElement("td");
  var a9 = document.createElement("a");
  a9.href="#";
  a9.className="btn-del";

  //根据商品状态显示"确认收货"或者"移除"
  if(STATE === "未完成"){ 
    a9.innerHTML="确认收货";
  }
  else{
    a9.innerHTML="移除";
  }

  img.src="../../pictures/"+IMG;
  td2.innerHTML=ID;
  a4.innerHTML=CONTENT;
  td5.innerHTML=PRICE;
  td6.innerHTML=WAY;
  td7.innerHTML=STATE;
  p8.innerHTML=TIME;

  td1.appendChild(input);td3.appendChild(img);td4.appendChild(a4);
  td8.appendChild(p8);td9.appendChild(a9);

  tr1.appendChild(td1);tr1.appendChild(td2);tr1.appendChild(td3);tr1.appendChild(td4);
  tr1.appendChild(td5);tr1.appendChild(td6);tr1.appendChild(td7);tr1.appendChild(td8);
  tr1.appendChild(td9);

  lists.appendChild(tr1);
  console.log(tr1); 
}

// 删除订单
function del_order(order_id){
  $.ajax({
    url: "http://101.133.239.170:8080/order/delete/" + order_id,
    type: "post",
    // data: order_id,
    // contentType:'multipart/form-data',
    // dataType: "JSON", 
    
    //post接口测试用例
    // url:'https://jsonplaceholder.typicode.com/posts',
    // data:[{
    //   "userId": 1,
    //   "id": 1,
    //   "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
    //   "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
    // }],
    success: function (data) {
        console.log("success");
    },
    error: function (errorMsg) {
        console.log("fail");
    }
});
}

// 确认收货
function received(order_id){
  $.ajax({
    url: "http://101.133.239.170:8080/order/update/status/"+order_id,
    type: "post",
    // data: order_id,
    // contentType:'multipart/form-data',
    // dataType: "JSON", 
    
    // post接口测试用例
    // url:'https://jsonplaceholder.typicode.com/posts',
    // data:[{
    //   "userId": 1,
    //   "id": 1,
    //   "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
    //   "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
    // }],
    success: function (data) {
        console.log("success");
    },
    error: function (errorMsg) {
        console.log("fail");
    }
});
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