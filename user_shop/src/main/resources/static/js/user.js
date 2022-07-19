
$('.mr-detail').css('display','none');
$('.select-btn').click(function (){
    $('.mr-detail').css('display','none');
    $('.select-btn').removeClass("on");
})

$('#b1').click(function (){
    $('#box1').css('display','block');
    $('#b1').addClass("on");

})
$('#b3').click(function (){
    $('#box3').css('display','block');
    $('#b3').addClass("on");
})

$('#b4').click(function (){
    $('#box4').css('display','block');
    $('#b4').addClass("on");
})

$('#b2').css("disabled",true)
// $('#b2').click(function (){
//     $('#box2').css('display','block');
//     $('#b2').addClass("on");
// })

$('#a1').click(function (){
    $('#a1').addClass("on");
    $('#a2').removeClass("on");
    $('#dl1').css("display",'block');
    $('#dl2').css("display",'none');
})
$('#a2').click(function (){
    $('#a2').addClass("on");
    $('#a1').removeClass("on");
    $('#dl2').css("display",'block');
    $('#dl1').css("display",'none');
})

//唤醒文件上传
$('#curAva').click(function (){
    $('#newAvatar').click();
})

//ajax提交信息
$('#submit').click(function (){
    var formData = new FormData();
    formData.append("avatar" , $('#newAvatar')[0].files[0]);
    userInfo = {
        name : $('#name').val(),
        email : $('#email').val()
    }
    formData.append('name' , $('#name').val()),
    formData.append( 'email' , $('#email').val()),
    $.ajax({
        contentType : false,
        processData : false,
        type: 'post',
        async: false,
        url: '/user/updateUserInfo',
        data: formData,
        success: function (data){
            $('#bbox').html(data);
        }
    });
});
// 修改密码
    $('#savepass').click(function (){
        data = {
            'curpass' : $('#curpass').val(),
            'newpass1' : $('#newpass1').val(),
            'newpass2' : $('#newpass2').val(),
        }
            $.ajax({
                contentType: "application/json",
                type: 'post',
                async: false,
                url: '/user/updatePassword',
                data: JSON.stringify(data),
                success: function (data){
                    $('#bbox').html(data);
                }
            });
});
//图片预览
$("#showpicture").click(function (){
    $("#ppicture").click();
})
$("#ppicture").change(function (){
    var file = $("#ppicture")[0].files[0];
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload=function(e){
        $("#showpicture").attr("src",e.target.result);
    }
})

//上传新商品
$("#uoloadbutton").click(function (){
    var formData = new FormData();
    formData.append("file",$("#ppicture")[0].files[0]);

    var data = JSON.stringify({
        "name": $("#pname").val(),
        "price": $("#pprice").val(),
        "sellTime": $("#psellTime").val(),
        "stock" : $("#pstock").val(),
        "category": $("#pcatetogy").val(),
        "brand": $("#pbrand").val(),
        "productInform" : $("#pinfo").val()
    });
    formData.append("productinfo",new Blob([data], {type: "application/json"}))
    $.ajax({
        contentType: false,
        processData: false,
        type: 'post',
        async: false,
        url: '/user/uploadProduct',
        data: formData,
        success: function (data){
            $("#box4").html(data);
        }
    });
})


