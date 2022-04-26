// 模拟后台接口

var datas;
var mysize = 10;
var myform;
var selectcurpage;
var myindex;
function getData(params) {
	//启用同步防止return出错

	$.ajax({
		type: 'get',
		async: false,
		url: '/user/getlist',
		data:{curpage : params.current},
		success: function(data) {
			var start = (params.current - 1) * params.size;
			var end = params.current *params.size;
			console.log('success', data);
			datas = data;
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			console.log('error', errorThrown);
		}
	});
	return {
		list : datas['list'],
		total : datas['total']
	}
}

//设置tbody的html
function setTbody (arr) {
	var html = '';
	for (var i = 0; i < arr.length; i++) {
		var item = arr[i];
		html += '<tr><td><img class="img-circle" src="' + item.avatarUrl + '">'  + '</td><td class="user_id">' + item.userId + '</td><td>' + item.userName + '</td><td>'
			+ item.userType + '</td><td>' + item.lastLoginTime + '</td><td>'
			+ item.userStatus + '</td><td>'
			//以下为按钮
			+ '<input type="button" value="删除" class=" remove_button btn-danger  btn-small"></input>'+ "  " + '<input type="button" value="详情" type="button" class="option_button   btn-small btn-danger"></button>'
			 + '</td></tr>';
	}
	$('.tbody').html(html);
}
//接收当前页数据
var curpage;
// 初始化分页

$('.box2').MyPaging({
	size: mysize,
	total: 0,
	current: 1,
	prevHtml: '上一页',
	nextHtml: '下一页',
	layout: 'total, totalPage, prev, pager, next, jumper',
	jump: function () {
		var _this = this;
		curpage = _this.current;

		var res = getData({
			size: _this.size,
			current: _this.current
		})

		setTbody(res.list);
		_this.setTotal(res.total);
	}
});

// 删除
$('.remove_button').each(function (index) {
	$(document).on("click", ".remove_button:eq("+ index + ")",
		function () {
			var user_id = $('.user_id')[index].innerHTML;
			var datas;
			$.ajax({
				type: 'get',
				async: false,
				url: '/user/remove',
				data: {curpage: curpage, userId: user_id},
			});
			$('.box2').MyPaging({
				size: mysize,
				current: curpage,
				prevHtml: '上一页',
				nextHtml: '下一页',
				layout: 'total, totalPage, prev, pager, next, jumper',
				jump: function () {
					var _this = this;
					curpage = _this.current;

					var res = getData({
						size: _this.size,
						current: _this.current
					})
					setTbody(res.list);
					_this.setTotal(res.total);
				}
			});
		}
	)
})

//编辑跳转显示
var user;
$('.option_button').each(function (index){

	$(document).on("click", ".option_button:eq("+ index + ")",
		function (){

		    myindex = index;
			var user_id =  $('.user_id')[index].innerHTML;
			$('#user_add').css("display",'none');
			$('#user_option').css("display",'block');
			$('.big_div').css('display','block');
			$.ajax({
				type: 'get',
				async: false,
				url: '/user/selectbyid',
				data:{userId : user_id},
				success: function(data) {
					console.log(user_id)
					user = data;
					console.log('success', data);
					$('#avatar').attr("src", data.avatarUrl);
					$('#name').val(data.userName);
					$('#email').val(data.email);
					$('#phone').val(data.phone);
					$('#group').val(data.userType);
					$('#status').val(data.userStatus);
					$('#credit').val(data.creditRating);
				},
			});
		}
	)
})
//保存修改
$('#save_button').click(function () {
	var newuser = {
		"userId" : user.userId,
		"userName" : $('#name').val(),
		"phone" : $('#phone').val(),
		"userType" : $('#group').val(),
		"creditRating" : $('#credit').val(),
		"email" : $('#email').val(),
		"userStatus" : $('#status').val()
	}
	$.ajax({
		type: 'post',
		async: false,
		contentType : 'application/json',
		url: '/user/update?curpage=' + curpage,
		data: JSON.stringify(newuser),
		success: function(data) {
			$('.box2').MyPaging({
				size: mysize,
				current: curpage,
				prevHtml: '上一页',
				nextHtml: '下一页',
				layout: 'total, totalPage, prev, pager, next, jumper',
				jump: function () {
					var _this = this;
					curpage = _this.current;
					var res = getData({
						size: _this.size,
						current: _this.current
					})
					setTbody(res.list);
					_this.setTotal(res.total);
				}
			});
		},
	});
})

//头像专用保存修改
$('#avater_save_button').click(function (){
	var formData = new FormData();
	var avatar = $('#user_avater')[0].files[0];
	formData.append("avatar", avatar);
	formData.append('curpage',curpage);
	formData.append("avatarSrc", $('#avatar').attr('src')),
	formData.append('userId', $('.user_id')[myindex].innerHTML);
	$.ajax({
		contentType : false,
		processData : false,
		type: 'post',
		async: false,
		url: '/user/updateavatar',
		data: formData,
		success: function (){
			console.log(formData)
			$('#select_button').click();
		}
	});
})

//取消修改关闭修改界面
$('.cancel_button').click(function () {
	$('.big_div').css('display','none');
	$('#user_option').css("display",'none');
	$('#user_add').css("display",'none');
})

//筛选条件输入检测
$('#select_button').click(
	function selectBySome(){
		$('.box2').MyPaging({
			size: mysize,
			current: curpage,
			prevHtml: '上一页',
			nextHtml: '下一页',
			layout: 'total, totalPage, prev, pager, next, jumper',
			jump: function () {
				var _this = this;
				$.ajax({
					type: 'post',
					async: false,
					url: '/user/selectlist',
					data: {
						"userId" : $('#search_userId').val(),
						"userName": $('#search_userName').val(),
						"lastLoginTimeFirst": $('#search_lastLoginTimeFirst').val(),
						"lastLoginTimeLast": $('#search_lastLoginTimeLast').val(),
						"userType": $('#search_userType').val(),
						"userStatus": $('#search_userStatus').val(),
						"registerTimeFirst" : $('#search_registerTimeFirst').val(),
						"registerTimeLast" :$('#search_registerTimeLast').val(),
						"curpage": _this.current
					},
					success:function (data){
						setTbody(data['list']);
						_this.setTotal(data['total']);
					}
				})
			}
		});
	}
);

//清空头像
$('#clear_avater').click(function (){
	$('#avatar').attr('src','/default.png');
})


//新增跳转显示
$('#user_register_buttom').click(function (){
	$('#user_option').css("display",'none');
	$('#user_add').css("display",'block');
	$('.big_div').css('display','block');
})

//提交新增用户数据
$('#user_register_submit').click(function (){
	var newuser = {
		"userName" : $('#register_name').val(),
		"userPassword" : $('#register_password').val(),
		"phone" : $('#register_phone').val(),
		"userType" : $('#register_type').val(),
		"creditRating" : $('#register_credit').val(),
		"email" : $('#register_email').val(),
		"userStatus" : $('#register_status').val(),
		"balance" : $('#register_balance').val(),
	}
	$.ajax({
		type: 'post',
		async: false,
		contentType : 'application/json',
		url: '/user/adduser',
		data: JSON.stringify(newuser),
		success: function(data) {
			$('.box2').MyPaging({
				size: mysize,
				current: 1,
				prevHtml: '上一页',
				nextHtml: '下一页',
				layout: 'total, totalPage, prev, pager, next, jumper',
				jump: function () {
					var _this = this;
					curpage = _this.current;
					var res = getData({
						size: _this.size,
						current: _this.current
					})
					setTbody(res.list);
					_this.setTotal(res.total);
				}
			});
		},
	});
	}
)


