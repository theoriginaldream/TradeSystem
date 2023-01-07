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
        url: "http://101.133.239.170:8080/items/mine",
        type: 'get',
        dataType: 'json',
        // data: {name:"你好"},
        success:return_json
    });
    function return_json(json){
        console.log(json.length);
        for(var i = 0; i < json.length; i++) {
            // if(json[i].itempicture==null){
            //     addlist(json[i].itemid,'微信图片_20210313134536.jpg',json[i].itemname,json[i].price,
            //         json[i].type,'已完成',json[i].datetime,json[i].schoolzone);
            // }
            // else{
            //     addlist(json[i].itemid,json[i].itempicture.itempicture,json[i].itemname,json[i].price,
            //     json[i].type,'已完成',json[i].datetime,json[i].schoolzone);
            // }
            addlist(json[i].itemid,json[i].itempicture,json[i].itemname,json[i].price,
                json[i].type,json[i].status,json[i].datetime,json[i].schoolzone);
        }
        // 获取所有删除按钮
        var btn_dels = document.querySelectorAll(".btn-del");
        // console.log(btn_dels.length);
        // 删除当前行
        for(var i=0; i<btn_dels.length; i++){
            btn_dels[i].onclick = function(){
                console.log(this.parentNode.parentNode.querySelector(".item_id").innerHTML);
                del_item(this.parentNode.parentNode.querySelector(".item_id").innerHTML)
                var tr = this.parentNode.parentNode;
                tr.parentNode.removeChild(tr);
            }
        }
        
        // 删除选中行
        del_check.onclick = function(){
            rows = document.querySelectorAll(".row");
            for(var i=0; i<rows.length; i++){
                var checkbox = rows[i].querySelector(".choose");
                var item_id = rows[i].querySelector(".item_id");
                console.log(item_id);
                if(checkbox.checked){
                    del_item(item_id.innerHTML);
                    rows[i].parentNode.removeChild(rows[i]);
                }
            }
        }
        
        // 修改求购信息
        var modal = document.getElementById('myModal');// 获取弹窗
        // 获取 <span> 元素，用于关闭弹窗
        var span = document.querySelector('.sure'); //确认
        // 获取所有编辑按钮
        var btn_edits = document.querySelectorAll(".btn-edit");
        console.log(btn_edits.length);
        // 编辑当前行
        rows = document.querySelectorAll(".row");
        
        for(var i=0; i<btn_edits.length; i++){
            // 获取商品id
            //   var item_id =  rows[i].querySelector(".item_id");
            btn_edits[i].onclick = function(){
                //商品内容
                var ritemname = document.getElementById("ritemname"); //弹窗商品内容显示
                // console.log(this.parentNode.parentNode.querySelector(".content").innerHTML);
                ritemname.placeholder = this.parentNode.parentNode.querySelector(".content").innerHTML;
                
                //商品id
                var Id = document.getElementById("ID");
                Id.className = this.parentNode.parentNode.querySelector(".item_id").innerHTML; //弹窗放置商品id
                // console.log(this.parentNode.parentNode.querySelector(".item_id").innerHTML);
                
                // 商品价格
                var PRICE = document.getElementById("item_price");
                PRICE.placeholder = this.parentNode.parentNode.querySelector(".subtotal").innerHTML;
                
                //所在校区
                var CAMPUS = document.getElementById("campus");
                // var index = CAMPUS.selectedIndex;
                // CAMPUS.options[index].value=this.parentNode.parentNode.querySelector(".schoolzone").getAttribute("schooldistrict");
                CAMPUS.value=this.parentNode.parentNode.querySelector(".schoolzone").getAttribute("schooldistrict"); //默认显示原先输入的校区
                CAMPUS.innerHTML=this.parentNode.parentNode.querySelector(".schoolzone").getAttribute("schooldistrict");
                
                // 商品图片
                // console.log(this.parentNode.parentNode.querySelector(".imgclass").src);
                var IMG1 = document.getElementById("pictures");
                IMG1.src = this.parentNode.parentNode.querySelectorAll(".imgclass")[0].src;
                var IMG2 = document.getElementById("pictures2");
                IMG2.src = this.parentNode.parentNode.querySelectorAll(".imgclass")[1].src;
                var IMG3 = document.getElementById("pictures3");
                IMG3.src = this.parentNode.parentNode.querySelectorAll(".imgclass")[2].src;
                var IMG4 = document.getElementById("pictures4");
                IMG4.src = this.parentNode.parentNode.querySelectorAll(".imgclass")[3].src;
                var IMG5 = document.getElementById("pictures5");
                IMG5.src = this.parentNode.parentNode.querySelectorAll(".imgclass")[4].src;
                
                modal.style.display = "block";
            }
            // 点击 <span> (x), 确认修改
            span.onclick = function() {
                modal.style.display = "none";
                edit_item();
            }
            // 在用户点击其他地方时，关闭弹窗
            window.onclick = function(event) {
                if (event.target == modal) {
                    modal.style.display = "none";
                }
            }
        }
    }
}
// 添加表单
function addlist(ID,IMG,CONTENT,PRICE,WAY,STATE,TIME,CAMPUS){
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
    td2.className="item_id";
    
    var td3 = document.createElement("td");
    var img = document.createElement("img");
    var img2 = document.createElement("img");
    var img3 = document.createElement("img");
    var img4 = document.createElement("img");
    var img5 = document.createElement("img");
    img.className="imgclass";
    img2.className="imgclass";
    img3.className="imgclass";
    img4.className="imgclass";
    img5.className="imgclass";
    //只显示第一个，其余隐藏
    img2.style.display='none';
    img3.style.display='none';
    img4.style.display='none';
    img5.style.display='none';
    
    var td4 = document.createElement("td");
    var a4 = document.createElement("a");
    a4.className="content";
    
    
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
    var a91= document.createElement("a");
    a91.href="#";
    a91.innerHTML="编辑 ";
    a91.className="btn-edit";
    var a92 = document.createElement("a");
    a92.href="#";
    a92.className="btn-del";
    a92.innerHTML="移除";
    var a93 = document.createElement("a"); //保存校区
    a93.className="schoolzone";
    
    a93.setAttribute("schooldistrict",CAMPUS); //存放校区信息
    
    if(IMG!=null){
        //多图
        if (IMG.itempicture!=null){
            img.src="../../pictures/"+IMG.itempicture;
        }else {
            img.src="../../pictures/增加图片例图.png"
        }
        if (IMG.itempicture2!=null){
            img2.src="../../pictures/"+IMG.itempicture2;
        }else {
            img2.src="../../pictures/增加图片例图.png"
        }
        if (IMG.itempicture3!=null){
            img3.src="../../pictures/"+IMG.itempicture3;
        }else {
            img3.src="../../pictures/增加图片例图.png"
        }
        if (IMG.itempicture4!=null){
            img4.src="../../pictures/"+IMG.itempicture4;
        }else {
            img4.src="../../pictures/增加图片例图.png"
        }
        if (IMG.itempicture5!=null){
            img5.src="../../pictures/"+IMG.itempicture5;
        }else {
            img5.src="../../pictures/增加图片例图.png"
        }
        
        // img2.src="../../pictures/"+IMG.itempicture2;
        // img3.src="../../pictures/"+IMG.itempicture3;
        // img4.src="../../pictures/"+IMG.itempicture4;
        // img5.src="../../pictures/"+IMG.itempicture5;
    }
    td2.innerHTML = ID; //显示订单号
    td2.id = ID; //存放订单号，以便查询
    a4.innerHTML=CONTENT;
    td5.innerHTML=PRICE;
    td6.innerHTML=WAY;
    td7.innerHTML=STATE;
    p8.innerHTML=TIME;
    
    td1.appendChild(input);td3.appendChild(img);td3.appendChild(img2);td3.appendChild(img3);
    td3.appendChild(img4);td3.appendChild(img5);td4.appendChild(a4);
    td8.appendChild(p8);td9.appendChild(a91);td9.appendChild(a92);td9.appendChild(a93);
    
    tr1.appendChild(td1);tr1.appendChild(td2);tr1.appendChild(td3);tr1.appendChild(td4);
    tr1.appendChild(td5);tr1.appendChild(td6);tr1.appendChild(td7);tr1.appendChild(td8);
    tr1.appendChild(td9);
    
    lists.appendChild(tr1);
    console.log(tr1);
}

// 删除出售信息
function del_item(item_id){
    $.ajax({
        url: "http://101.133.239.170:8080/items/delete/" + item_id,
        type: "post",
        // data: item_id,
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

// 修改求购信息,不修改的默认传入原先的值
function edit_item(){
    const formData = new FormData();
    var id = document.getElementById("ID");
    var ritemname = document.getElementById("ritemname");
    //若有修改，传入placeholder
    if(ritemname.value.length != 0){
        ritemname.placeholder=ritemname.value;
    }
    
    var img = document.getElementById("FileImg");
    var img2 = document.getElementById("FileImg2");
    var img3 = document.getElementById("FileImg3");
    var img4 = document.getElementById("FileImg4");
    var img5 = document.getElementById("FileImg5");
    
    var cam = document.getElementById("CAM"); //校区 下拉框
    var index = cam.selectedIndex;
    console.log(cam.options[index].value);
    
    var price = document.getElementById("item_price");
    if(price.value.length != 0){ //有修改的情况
        price.placeholder = price.value;
    }
    
    formData.append("itemid",id.className);
    formData.append("itemname",ritemname.placeholder);
    formData.append("schoolzone",cam.options[index].value);
    formData.append("price",price.placeholder);
    if (img.files.length>0){
        formData.append("picture1",img.files[0]);
    }
    if (img2.files.length>0){
        formData.append("picture2",img2.files[0]);
    }
    if (img3.files.length>0){
        formData.append("picture3",img3.files[0]);
    }
    if (img4.files.length>0){
        formData.append("picture4",img4.files[0]);
    }
    if (img5.files.length>0){
        formData.append("picture5",img5.files[0]);
    }
    
    // console.log(document.getElementById(id.className).parentNode);
    // 前端商品信息替换
    var IMGCALSS = document.getElementById(id.className).parentNode.parentNode.querySelectorAll(".imgclass");
    IMGCALSS[0].src = img.src;
    IMGCALSS[1].src = img2.src;
    IMGCALSS[2].src = img3.src;
    IMGCALSS[3].src = img4.src;
    IMGCALSS[4].src = img5.src;
    
    var CONTENT = document.getElementById(id.className).parentNode.querySelector(".content");
    CONTENT.innerHTML = ritemname.placeholder;
    var SCHOOLZONE = document.getElementById(id.className).parentNode.
    querySelector(".schoolzone")
    SCHOOLZONE.setAttribute("schooldistrict",cam.options[index].value);
    var PRICE = document.getElementById(id.className).parentNode.querySelector(".subtotal");
    PRICE.innerHTML = price.placeholder;
    
    $.ajax({
        url: "http://101.133.239.170:8080/items/update/item",
        type: "post",
        data: formData,
        // contentType:'multipart/form-data',
        contentType: false,
        processData: false,
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
            alert("信息修改成功！");
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