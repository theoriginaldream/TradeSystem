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

function f2() {
  console.log("f2");
  // 获取个人信息
  $.ajax({
      url: "http://101.133.239.170:8080/user/user",
      type: 'get',
      dataType: 'json',
      // data: {name:"你好"},
      success: function(json){
          if (json.headpicture==null) {
              json.headpicture={
                  headpicture: "搜索.png"
              }
          }
          Getinfo(json.user.userid,json.headpicture.headpicture,
              json.user.phone,json.user.name,json.user.schoolzone,json.user.introduce);
      }
  });
  // function return_json(json){
  //   Getinfo(json.user.userid,json.headpicture.headpicture,
  //     json.user.phone,json.user.name,json.user.schoolzone,json.user.introduce);
  //   }
  }

// 添加表单
function Getinfo(id,headpicture,phone,nickname,campus,desc){
  var ID = document.getElementById("ID");
  var Headpicture = document.getElementById("headpicture");
  var Phone = document.getElementById("phone");
  var Nickname = document.getElementById("nickname");
  var Campus = document.getElementById("campus"); //下拉框
  var Desc = document.getElementById("desc");

  ID.innerHTML=id;
  Phone.placeholder=phone;
  Nickname.placeholder=nickname;
  Campus.value=campus;
  Campus.innerHTML=campus; //默认显示原先输入的校区
  Desc.innerHTML=desc;
  Headpicture.src="../../pictures/"+headpicture;
}


// 确认修改信息功能实现，部分不发生修改的情况传送原来的值
function changeInfo(){
  var ID = document.getElementById("ID");
  var Headpicture = document.getElementById("FileImg");
  var Phone = document.getElementById("phone");
  var Nickname = document.getElementById("nickname");

  var Campus = document.getElementById("CAM"); //校区 下拉框
  var index = Campus.selectedIndex;

  var Desc = document.getElementById("desc");

  //前端界面同步修改默认（若自动刷新，不需要修改）
  if(Phone.value.length == 11){ //有修改的情况
      Phone.placeholder=Phone.value;
  }
  if(Nickname.value.length != 0){
    Nickname.placeholder=Nickname.value;
  }
  if(Campus.value.length != 0){ //有修改的情况
      Campus.placeholder=Campus.value;
  }
  if(Desc.value.length != 0){ //有修改的情况
      Desc.innerHTML=Desc.value;
  }
  //下拉框修改的是原先默认选择的，比较特殊
  var DF = document.getElementById("campus"); //下拉框默认
  DF.value=Campus.options[index].value;
  DF.innerHTML=Campus.options[index].value; //默认显示原先输入的校区
  
  var desc = document.getElementById("desc").value;
  
  // var file = $('#headpicture')[0].files[0]
  // var file = document.getElementById("headpicture").files[0];

  const formData = new FormData();
  formData.append("userid",ID.innerHTML.valueOf());
  // formData.append("headpicture",$("#headpicture").prop('files'));
  formData.append("headpicture",Headpicture.files[0]);
  formData.append("phone",Phone.placeholder);
  formData.append("name",Nickname.placeholder);
  formData.append("schoolzone",Campus.options[index].value);
  formData.append("introduce",desc);
  
  console.log(ID.innerHTML.valueOf());
  console.log(Headpicture.src)
  console.log(Phone.placeholder)
  console.log(Nickname.placeholder)
  console.log(Campus.options[index].value)
  console.log(Desc.value)
  $.ajax({
      url: "http://101.133.239.170:8080/user/update/user",
      type: "post",
      data: formData,
      // contentType:'multipart/form-data',
      contentType: false,
      processData: false,
      cache: false,
      async: false,
      //post接口测试用例
      // url:'https://jsonplaceholder.typicode.com/posts',
      // data:[{
      //   "userId": 1,
      //   "id": 1,
      //   "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
      //   "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
      // }],
      // dataType: "JSON", 
      success: function (data) {
          console.log("success");
          alert("信息修改成功！");
          document.getElementById("submit1").disabled = false;
      },
      error: function (errorMsg) {
          console.log("fail");
      }
  });
}

// 密码要求：一致，长度适中
function validate() {
  var pw = document.getElementById("pw").value;
  var pw1 = document.getElementById("pw1").value;
  var pw2 = document.getElementById("pw2").value;

  if(pw1 == pw2) {
      if(pw1.length<6 || pw1.length>16){
          alert("密码长度不符合要求，请重新输入！")
          // document.getElementById("submit").disabled = true;
      }
      else{
          console.log(JSON.stringify({"oldpwd":pw,"password": pw1,"password2":pw2}));
          f3(pw,pw1,pw2);
          alert("密码修改成功！")
          // document.getElementById("submit").disabled = false;
      }
  }
  else {
      alert("两次输入密码不一致，请重新输入！")
      document.getElementById("submit").disabled = true;
  }

}

function f3(pw,pw1,pw2){
  console.log("f3");
  $.ajax({
    url:'http://101.133.239.170:8080/user/update/pwd',
    type:'post',
    dataType:'text',
    contentType:'application/x-www-form-urlencoded',
    data:{"oldpwd":pw, "password": pw1, "password2":pw2},
    //post接口测试用例
    // url:'https://jsonplaceholder.typicode.com/posts',
    // data:[{
    //   "userId": 1,
    //   "id": 1,
    //   "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
    //   "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
    // }],
    success:function (data) {
      console.log("success");
    },
    error:function (data) {
      console.log("failed");
    }
  })
}


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