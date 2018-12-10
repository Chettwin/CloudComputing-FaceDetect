$(function () {
    mytime();
    getPassengerFlow();
});

setInterval(function() {mytime()},1000);
function mytime(){
    var a = new Date();
    var b = a.toLocaleTimeString();
    var c = a.toLocaleDateString();
    document.getElementById("currentTime").innerHTML = c+"&nbsp;&nbsp"+b;
}

function getPassengerFlow() {
    var flow = 0;
    var a = new Date();
    var userId = $("#userId").val();
    var curr_date = a.toLocaleDateString().split("/").join("-");
    $.ajax({
        type: "post",
        url: "/user/passflowanalysis/passFlowAnalysisById",
        data: JSON.stringify({"userId": userId, "startDate": curr_date,"endDate": curr_date}),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data, status, xhr) {
            if (data.code == 200) {
                if(data.data == "null")
                {
                    document.getElementById("passengerFlow").innerHTML = "0人";
                }
                else
                {
                    var passengerFlow = data.data[0].passengerFolw;
                    document.getElementById("passengerFlow").innerHTML = passengerFlow+"人";
                }
            } else if (data.code == 500) {
                alert("客流量获取失败！");
            }
        },
    })
}

var video = document.querySelector('video');
var audio, audioType;

var canvas1 = document.getElementById('canvas1');
var context1 = canvas1.getContext('2d');

navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia;
window.URL = window.URL || window.webkitURL || window.mozURL || window.msURL;

var exArray = []; //存储设备源ID
var camera;

function openCamera() {

    document.getElementById("getPhoto").style.visibility = "visible";
    if (navigator.getUserMedia) {
        navigator.getUserMedia({
            'video': {
                'optional': [{
                    'sourceId': exArray[1] //0为前置摄像头，1为后置
                }]
            },
            'audio':true
        }, successFunc, errorFunc);    //success是获取成功的回调函数
    }
    else {
        alert('Native device media var track = stream.getTracks()[0];streaming (getUserMedia) not supported in this browser.');
    }
    document.getElementById("camera").style.visibility = "visible";
}

function successFunc(stream) {
    //alert('Succeed to get media!');
    camera = stream;
    if (video.mozSrcObject !== undefined) {
        //Firefox中，video.mozSrcObject最初为null，而不是未定义的，我们可以靠这个来检测Firefox的支持
        video.mozSrcObject = stream;
        getPhoto();
    }
    else {
        video.src = window.URL && window.URL.createObjectURL(stream) || stream;
    }
}
function errorFunc(e) {
    alert('Error！'+e);
}

//拍照
function getPhoto() {
    context1.drawImage(video, 0, 0,300,300); //将video对象内指定的区域捕捉绘制到画布上指定的区域，实现拍照。
    var base64Data = document.getElementById('canvas1').toDataURL();//得到base64数据
    //document.getElementById('canvas1').remove();
    base64Data = base64Data.substr(22);//得到22位之后的字符串作为图像数据
    // private String imageBase64;
    // private String entryDate;
    // private String entryHour;
    // private String entryMinute;
    // private String userId;
    //
    var a = new Date();

    var date = a.toLocaleDateString().split("/").join('-');
    var hour = a.getHours();
    var minute = a.getMinutes();
    var second = a.getSeconds();
    var userId = $("#userId").val();
    //console.log(hour+"\n"+minute+'\n'+second+"\n"+userId);
    $.ajax({
        type: "post",
        url: "/faceMonitor/getFaceDetectResponse",
        data: JSON.stringify({"imageBase64": base64Data,"entryDate":date,"entryHour":hour,"entryMinute":minute,"entrySecond":second,"userId":userId}),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (data, status, xhr) {
            if (data.code == 200) {

                //一张图片中只有一张脸
                if(data.data.length==1)
                {
                    var showBlock = document.getElementById('oneFace');
                    var faceImage = document.getElementById('face');
                    var faceAttribute = document.getElementById('faceAttribute');
                    var faceBase64 = "data:image/png;base64,"+data.data[0].imageBase64;
                    var faceAge = data.data[0].age;
                    var faceSex = data.data[0].sex;
                    faceImage.src = faceBase64;
                    faceSex = faceSex=="male" ? "男" : "女";
                    faceAttribute.innerHTML = "性别："+faceSex+"</br>年龄："+faceAge;
                    document.getElementById('twoFaces').style.display = "none";
                    showBlock.style.display = "block";
                }
                //一张图中有两张脸以上
                else
                {
                    var showBlock = document.getElementById('twoFaces');
                    var face1Image = document.getElementById('face1');
                    var face2Image = document.getElementById('face2');
                    var face1Attribute = document.getElementById('face1Attribute');
                    var face2Attribute = document.getElementById('face2Attribute');
                    var face1Baes64 = "data:image/png;base64,"+data.data[0].imageBase64;
                    var face1Age = data.data[0].age;
                    var face1Sex = data.data[0].sex;
                    var face2Base64 = "data:image/png;base64,"+data.data[1].imageBase64;
                    var face2Age = data.data[1].age;
                    var face2Sex = data.data[1].sex;
                    face1Sex = face1Sex=="male" ? "男" : "女";
                    face2Sex = face2Sex=="male" ? "男" : "女";
                    face1Image.src = face1Baes64;
                    face1Attribute.innerHTML = "性别："+face1Sex+"</br>年龄："+face1Age;
                    face2Image.src = face2Base64;
                    face2Attribute.innerHTML = "性别："+face2Sex+"</br>年龄："+face2Age;
                    //展示出结果
                    document.getElementById('oneFace').style.display = "none";
                    showBlock.style.display="block";
                }
                getPassengerFlow();
            } else if (data.code == 400) {
                //swal("登录失败", resp.msg, "error");
            } else {
                // $("#changeBtn").attr("src", "/admin/captcha.do");
                // swal("登录失败", resp.msg, "error");
            }
        },
    })
}

//关闭摄像头
function closeCamera() {
    video.src = window.URL.createObjectURL(camera);
    camera.getVideoTracks()[0].stop();
    document.getElementById("camera").style.visibility = "hidden";
    document.getElementById('twoFaces').style.display = "none";
    document.getElementById('oneFace').style.display = "none";
    document.getElementById('getPhoto').style.visibility = "hidden";
}