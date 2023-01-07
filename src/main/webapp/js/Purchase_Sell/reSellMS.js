
//存放图片的名称、url地址、图片流信息


/**
 * [fileCountCheck 上传文件数量检测]
 * @param  {[Object]} filesObj [文件对象]
 * @param  {[Number]} minFileNum  [文件数量下限]
 * @param  {[Number]} maxFileNum  [文件数量上限]
 * @return {[Boolean]}          [真假]
 */
// function fileCountCheck(filesObj, minFileNum, maxFileNum) {



//     // console.log(filesObj.files); // 文件对象
//     if (window.File && window.FileList) {

//         var fileCount = filesObj.files.length;
//         if (fileCount < minFileNum || fileCount > maxFileNum) {

//         // 不符合数量的处理
//             window.alert('图片数不能超过' + maxFileNum + '个，你选择了' + fileCount + '个');
//             return false;
//                 } 
//         else {

//             // 符合数量的处理
//             window.alert('');
//             return true;
//             }
//         }
//     else {
//         // 不支持FileAPI
//         window.alert('抱歉,你的浏览器不支持FileAPI,请升级浏览器');
//             return false;
//         }

// }

var files_base64 = [];

 //下面用于多图片上传预览功能
 function setImagePreviews(avalue) {
    var docObj = document.getElementById("pic_id");
    var dd = document.getElementById("pic_area");
    dd.innerHTML = "";
    var fileList = docObj.files;
    for(i=0;i<fileList.length;i++){
        var  reader = new FileReader();
        reader.onload = function(e){
            files_base64[i] = e.target.result;
        }
        reader.readAsDataURL(fileList[i]);
    }
    for (var i = 0; i < fileList.length; i++) {
        dd.innerHTML += "<div style='float:left' > <img id='img" + i + "'  />"+"<div class='list'  >" +"<span class='delete'style='font-size:15px; margin-left:1rem' >删除</span></div>"+" </div>";
        var imgObjPreview = document.getElementById("img"+i);
        if (docObj.files && docObj.files[i]) {
            //火狐下，直接设img属性
            imgObjPreview.style.display = 'block';
            //控制缩略图大小
            imgObjPreview.style.width = '80px';
            imgObjPreview.style.height = '80px';
            imgObjPreview.style.marginLeft = '1rem';
            //imgObjPreview.src = docObj.files[0].getAsDataURL();
            //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
            imgObjPreview.src = window.URL.createObjectURL(docObj.files[i]);
        }
        else {
            //IE下，使用滤镜
            docObj.select();
            var imgSrc = document.selection.createRange().text;
            alert(imgSrc)
            var localImagId = document.getElementById("img" + i);
            //必须设置初始大小
            localImagId.style.width = "5rem";
            localImagId.style.height = "5rem";
            //图片异常的捕捉，防止用户修改后缀来伪造图片
            try {
                localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
                localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
            }
            catch (e) {
                alert("您上传的图片格式不正确，请重新选择!");
                return false;
            }
            imgObjPreview.style.display = 'none';
            document.selection.empty();
        }
        if(docObj.files.length > 5){
            alert('最多只能添加5张图片,请重新选择');
            return;
        }
    }
    $(".delete").click(function(e){
    
        $(this).parent().parent().remove();
        formData.delete("file");
        for (i = 0; i < document.getElementById("pic_id").files.length; i++){
            formData.append("pictures",document.getElementById("pic_id").files[i])
        }
    })
    return true;
}

// 上传商品详情信息
var subtn = document.getElementById("button1");
var filelist = document.getElementsByClassName("filelist");
console.log(filelist);

subtn.onclick = function() {
    
    const formData = new FormData();
    formData.append("itemname",document.getElementById("S_detail").value)
    formData.append("schoolzone",document.getElementById("area").value)
    formData.append("type",document.getElementById("way").value)
    formData.append("price",document.getElementById("price").value)
    // formData.append("pictures",document.getElementById("pic_id").files[0])
    
    for (i = 0; i < document.getElementById("pic_id").files.length; i++){
        formData.append("pictures",document.getElementById("pic_id").files[i])
    }
    
    $.ajax({
        url: "http://101.133.239.170:8080/items/add/item",
        type: "post",
        data: formData,
        // contentType:'multipart/form-data',
        contentType: false,
        processData: false,
        cache: false,
        async: false,
        // data:JSON.stringify({
        //     "S_detail":document.getElementById("S_detail").innerHTML,
        //     "S_price":document.getElementById("price").innerHTML,
        //     "S_area":document.getElementById("area").innerHTML,
        //     "S_way":document.getElementById("way").innerHTML,
        //     "filelist":files_base64
        // }),
        success:function (data){
            console.log("提交成功！")
            window.location.href = "http://101.133.239.170:8080/templates/headpicture.html"
        },
        error:function(){
            console.log("提交失败！")
        }
    });
};

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