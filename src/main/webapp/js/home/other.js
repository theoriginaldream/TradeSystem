function change_page(){
  // JS实现选项卡切换
  var myTab1 = document.getElementById("m_head_collapse");    //整个div
  var myTab2 = document.getElementById("commodity_list");
  var myA = myTab1.getElementsByTagName("a");    //数组
  var myUl = myTab2.getElementsByTagName("ul");//数组
  for(var i = 0; i<myA.length;i++){
    myA[i].index = i;
    myA[i].onclick = function(){
      cancelBubble =true;
      for(var j = 0; j < myA.length; j++){
        myUl[j].className="hide"
        myA[j].className="m_head_l"
      }
      myUl[this.index].className = "show";
      myA[this.index].className="m_head_l on"
    }
  }
}

change_page()

// 从URL中获取userid
function getQueryString(userid) {
  var reg = new RegExp("(^|&)"+ userid +"=([^&]*)(&|$)");
  var r = window.location.search.substr(1).match(reg);
  if(r!=null){
    return  decodeURI(r[2]);
  }
  return null;
}



// 点击主页键就从数据库获取所有的商品信息并加载
window.onload = function(){
  // 获取userid
  var userid = getQueryString("userid");
  // 加载用户个人信息
  $.ajax({
    'url':"http://localhost:8080/user/query/" + userid,
    'type':'GET',
    // 'dataType':'json',
    'success':function (res) {
      if (res.headpicture==null){
        res.headpicture = {
          headpicture: "搜索.png"
        }
      }
      document.getElementsByClassName("headportrait")[0].setAttribute("src","../pictures/"+res.headpicture.headpicture);
      document.getElementsByClassName("info3")[0].innerHTML=res.user.userid;
      document.getElementsByClassName("info3")[1].innerHTML=res.user.phone;
      document.getElementById("nickname").innerHTML=res.user.name;
      document.getElementById("campus").innerHTML=res.user.schoolzone;
      document.getElementById("brief").innerHTML=res.user.introduce;
    }
  })
  // 加载用户的求购商品
  $.ajax({
    'url':"http://localhost:8080/requires/otherRequire/" + userid,
    'type':'GET',
    // 'dataType':'json',
    'success':function (res) {
      for(i=0;i<res.length;i++){
        src = res[i].itemPicture
        if(src != null){
          addLi("commodity_list_qiu",res[i].ritemid,src.itempicture,"",res[i].schoolzone,res[i].ritemname)
        }else{
          src = "增加图片例图.png"
          addLi("commodity_list_qiu",res[i].ritemid,src,"",res[i].schoolzone,res[i].ritemname)
        }
      }
      
    }
  })
  // 加载用户的出售商品
  $.ajax({
    'url':"http://localhost:8080/items/otherItem/" + userid,
    'type':'GET',
    // 'dataType':'json',
    'success':function (res) {
      for(i=0;i<res.length;i++){
        src = res[i].itempicture
        if(src != null){
          addLi("commodity_list_chu",res[i].itemid,src.itempicture,res[i].price,res[i].schoolzone,res[i].itemname)
        }else{
          src = "增加图片例图.png"
          addLi("commodity_list_chu",res[i].itemid,src,res[i].price,res[i].schoolzone,res[i].itemname)
        }
      }
    }
  })
}

//用createElement创建li元素，再通过setAttribute设置元素属性，最后通过appendChild()方法添加在父元素的最后一个子节点上。
//创建li标签，包含显示姓名，邮箱，电话号码及删除按钮
function addLi(ul_id,itemid,src,price,area,desc){
  var li_1 = document.createElement("li");
  li_1.setAttribute("id",itemid)
  var As = createA();
  var Divs = createDiv();
  var spans = createSpan();
  addImg(Divs.Div1,src)
  spans.span1.innerHTML=price;
  spans.span2.innerHTML=area;
  spans.span3.innerHTML=desc;
  Divs.Div2.appendChild(spans.span1)
  Divs.Div2.appendChild(spans.span2)
  As.A1.appendChild(Divs.Div1)
  As.A1.appendChild(Divs.Div2)
  As.A1.appendChild(spans.span3)
  if(ul_id=="commodity_list_qiu"){
    As.A1.setAttribute("href","rePurchaseDt.html?itemid="+itemid.toString());
    li_1.appendChild(As.A1)
    document.getElementById(ul_id).appendChild(li_1);
  }else{
    //   var p_1 = document.createElement("p");
    //   p_1.setAttribute("class","buy")
    //   spans.span4.setAttribute("class","add_cart")
    //   As.A2.appendChild(spans.span4)
    //   p_1.appendChild(As.A2)
    //   As.A1.appendChild(As.A2)
    As.A1.setAttribute("href","reSellDt.html?itemid="+itemid.toString());
    li_1.appendChild(As.A1)
    document.getElementById(ul_id).appendChild(li_1);
  }
}

function createA(){
  var A1=document.createElement("a");
  var A2=document.createElement("a");
  A1.setAttribute("class","commodity-item");
  A1.setAttribute("target","_self");
  A1.setAttribute("style","display:block;color:#000")
  A2.setAttribute("href","#")
  return {A1:A1,A2:A2};
}

function createDiv(){
  var Div1=document.createElement("div");
  var Div2=document.createElement("div");
  Div1.setAttribute("class","figure");
  return {Div1:Div1,Div2:Div2};
}

function createSpan(text){
  var span1=document.createElement("span");
  var span2=document.createElement("span");
  var span3=document.createElement("span");
  var span4=document.createElement("span");
  span1.setAttribute("class","price");
  span2.setAttribute("class","area");
  span3.setAttribute("class","desc");
  span4.innerHTML='加入购物车';
  return {span1:span1,span2:span2,span3:span3,span4:span4};
}

function addImg(obj,src){
  var Img=document.createElement("Img")
  Img.setAttribute("style" ,"width:150px;height:160px;");
  Img.setAttribute("src","../pictures/"+src)
  obj.appendChild(Img);
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