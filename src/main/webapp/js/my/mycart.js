const showMenu = (toggleId, navbarId, bodyId) => {
  const toggle = document.getElementById(toggleId),
    navbar = document.getElementById(navbarId);
  bodypadding = document.getElementById(bodyId);

  if (toggle && navbar) {
    toggle.addEventListener('click', () => {
      navbar.classList.toggle('expander')
      bodypadding.classList.toggle('body-pd')
    })
  }
}

showMenu('nav-toggle', 'navbar', 'body-pd')


const linkColor = document.querySelectorAll(".nav_link")
function colorLink() {
  linkColor.forEach(l => l.classList.remove('active'))
  this.classList.add('active')
}
linkColor.forEach(l => l.addEventListener('click', colorLink))

const linkCollapse = document.getElementsByClassName('collapse__link')
var i

for (i = 0; i < linkCollapse.length; i++) {
  linkCollapse[i].addEventListener('click', function () {
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
// 获取所有删除按钮
var btn_dels = document.querySelectorAll(".btn-del");
// 删除选中的商品
var del_check = document.querySelector(".del_check");

// 计算总计价格 & 计算选中的商品总数 & 同时判断是否全选
function setTotal(){
  var total = 0;  // 商品总价
  var allNum = 0; // 商品总数
  // 重新获取行
  rows = document.querySelectorAll(".row");
  // 遍历所有行
  for(var i=0; i<rows.length; i++){
      // 查找被选中的行
      var checkbox = rows[i].querySelector(".choose");
      if(checkbox.checked){
          
          // 获取小计价格（得到的是字符串，不是数字，需要转化）
          var smallTotal = rows[i].querySelector(".subtotal").innerHTML;
          // 把小计价格转化为数字
          smallTotal = Number(smallTotal);
          total += smallTotal;
          // 计算商品总数
          allNum += 1;
      }
  }
  
  // 把总计放在它应在的位置 
  var totalPrice = document.querySelector(".t-price");
  totalPrice.innerHTML = total.toFixed(2);
  // 设置商品总数
  document.querySelector(".t-number").innerHTML = allNum;

}

// 获取弹窗
var modal = document.getElementById('myModal');
 
// 打开弹窗的按钮对象
var btn = document.getElementById("myBtn");
 
// 获取 <span> 元素，用于关闭弹窗
var span = document.querySelector('.close');
 
// 点击按钮打开弹窗
btn.onclick = function() {
    modal.style.display = "block";
    rows = document.querySelectorAll(".row");
    // 遍历所有行
    for(var i=0; i<rows.length; i++){
      // 查找被选中的行
      var checkbox = rows[i].querySelector(".choose");
      var itemID = rows[i].querySelector(".item_id");
      var shoppingcart_id = rows[i].querySelector(".shoppingcartid");
      var del_btn = rows[i].querySelector(".btn-del");
      // console.log(shoppingcart_id);
      if(checkbox.checked){
          console.log(itemID.id); //下单的商品id
          buy(itemID.id); //商品下单
          // del_shoppingcart(shoppingcart_id.id); //同时将商品移除购物车
          del_btn.click(); //页面移除商品
      }
   }
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

// 获取表单数据
window.onload = function () {
  $.ajax({
      url: "http://localhost:8080/items/myShoppingCart",
      type: 'get',
      dataType: 'json',
      // data: {name:"你好"},
      success:return_json
  });
  function return_json(json){
      console.log(json.length);
      for(var i = 0; i < json.length; i++) {
          if (json[i].item.itempicture==null){
              json[i].item.itempicture={
                  itempicture: "搜索.png"
              };
          }
          addlist(json[i].shoppingcartid,json[i].item.itemid,json[i].item.itempicture.itempicture,
              json[i].item.itemname,json[i].item.price,json[i].item.type,json[i].item.datetime);
      }
      // 获取所有删除按钮
      var btn_dels = document.querySelectorAll(".btn-del");
      // console.log(btn_dels.length);
      // 删除当前行
      for(var i=0; i<btn_dels.length; i++){
        btn_dels[i].onclick = function(){
            console.log(this.parentNode.parentNode.querySelector(".shoppingcartid").id);
            del_shoppingcart(this.parentNode.parentNode.querySelector(".shoppingcartid").id);
            var tr = this.parentNode.parentNode;
            tr.parentNode.removeChild(tr);
            setTotal();
        }
      }

      // 删除选中行
      del_check.onclick = function(){
        rows = document.querySelectorAll(".row");
        for(var i=0; i<rows.length; i++){
            var checkbox = rows[i].querySelector(".choose");
            var shoppingcart_id = rows[i].querySelector(".shoppingcartid");
            console.log(shoppingcart_id);
            if(checkbox.checked){
              del_shoppingcart(shoppingcart_id.id);
              rows[i].parentNode.removeChild(rows[i]);
              setTotal();
            }
        }
      }
    }
  }
// 添加表单
function addlist(CART_ID,ITEM_ID,IMG,CONTENT,PRICE,WAY,TIME){
  var lists = document.getElementById('lists');
  var tr1 = document.createElement("tr");
  tr1.className="row";

  var td1 = document.createElement("td");
  var input1 = document.createElement("input");
  input1.type="checkbox";
  input1.name="checkOne";
  input1.className="choose";
  //绑定onclick事件，直接用setAttribute增加一个指定名称和值的新属性
  input1.setAttribute("onclick","check()"); 
  
  var td2 = document.createElement("td");
  var img = document.createElement("img");
  img.className="imgclass";
  
  
  var td3 = document.createElement("td");
  var a3 = document.createElement("a");
  a3.class="content";
  
  
  var td4 = document.createElement("td");
  td4.className="subtotal";
  td4.name="price";
  
  var td5 = document.createElement("td");
  td5.className="delivery";
  
  var td6 = document.createElement("td");
  var p6 = document.createElement("p");
  p6.id="time";
  
  var td7 = document.createElement("td");
  var a7= document.createElement("a");
  a7.href="#";
  a7.className="btn-del";
  a7.innerHTML="移除";
  var span71= document.createElement("span");
  span71.className="shoppingcartid";
  var span72= document.createElement("span");
  span72.className="item_id";

  span71.id=CART_ID; //要删除的购物车物品的id
  span72.id=ITEM_ID; //要下单的商品id
  img.src="../../pictures/"+IMG;
  a3.innerHTML=CONTENT;
  td4.innerHTML=PRICE;
  td5.innerHTML=WAY;
  p6.innerHTML=TIME;

  td1.appendChild(input1);td2.appendChild(img);td3.appendChild(a3);
  td6.appendChild(p6);td7.appendChild(a7);td7.appendChild(span71);td7.appendChild(span72);

  tr1.appendChild(td1);tr1.appendChild(td2);tr1.appendChild(td3);tr1.appendChild(td4);
  tr1.appendChild(td5);tr1.appendChild(td6);tr1.appendChild(td7);

  lists.appendChild(tr1);
  console.log(tr1); 
}

  // 删除购物车商品
function del_shoppingcart(shoppingcartid){
    $.ajax({
      url: "http://localhost:8080/items/delete/shoppingCart/" + shoppingcartid,
      //post接口测试用例
      // url:"https://jsonplaceholder.typicode.com/posts",
      // data:{
      //   "userId": 1,
      //   "id": 1,
      //   "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
      //   "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
      // },
      type: "post",
      // data: shoppingcartid,
      // contentType:'multipart/form-data',
      // dataType: "JSON",
      success: function (data) {
          console.log("success");
      },
      error: function (errorMsg) {
          console.log("fail");
      }
  });
  }
  // 购物车下单功能接口
function buy(itemid){
    $.ajax({
      // post 接口测试用例
      // url:"https://jsonplaceholder.typicode.com/posts",
      // data:{
      //   "userId": 1,
      //   "id": 1,
      //   "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
      //   "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
      // },
      url: "http://localhost:8080/order/add/order/" + itemid,
      type: "post",
      // data: itemid,
      // contentType:'multipart/form-data',
      // dataType: "JSON",
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
        'url':'http://localhost:8080/login/logout',
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