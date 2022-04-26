var curpage;
// 初始化分页
var mysize = 10;
//设置tbody的html
function setTbody (arr) {
    var html = '';
    for (var i = 0; i < arr.length; i++) {
        var item = arr[i];
        html += '<tr><td class="adminId">' + item.adminId + '</td><td>' + item.employeeId + '</td><td>'
            + item.adminType + '</td><td>'+ item.registerTime + '</td><td>' + item.lastLoginTime + '</td><td>'
            + item.adminStatus + '</td><td>'+ item.registerId + '</td><td>'
            //以下为按钮
            + '<button class="remove_button btn-danger   btn-small">删除</button>'+ "  " + '<button class="option_button   btn-small btn-danger">编辑</button>'
            + '</td></tr>';
    }
    $('.tbody').html(html);
}

//初始化
getList(1);

function getList(current) {
    $('.box2').MyPaging({
        size: mysize,
        total: 0,
        current: current,
        prevHtml: '上一页',
        nextHtml: '下一页',
        layout: 'total, totalPage, prev, pager, next, jumper',
        jump: function () {
            var _this = this;
            curpage = _this.current;
            data = {
                curpage: _this.current,
                entity: {
                    adminId: $('#search_adminId').val(),
                    employeeId: $('#search_employeeId').val(),
                    registerId: $('#search_registerId').val(),
                    adminType: $('#search_adminType').val(),
                    adminStatus: $('#search_adminStatus').val(),
                    lastLoginTimeFirst: $('#search_lastLoginTimeFirst').val(),
                    lastLoginTimeLast: $('#search_lastLoginTimeLast').val(),
                    registerTimeFirst: $('#search_registerTimeFirst').val(),
                    registerTimeLast: $('#search_registerTimeLast').val()
                }
            }
            $.ajax({
                    contentType: "application/json",
                    type: 'post',
                    async: false,
                    url: '/admin/pagelist',
                    data: JSON.stringify(data),
                    success: function (data) {
                        setTbody(data['list']);
                        _this.setTotal(data['total']);
                    }
                }
            )
        }
    });
}
//删除
$('.remove_button').each(function (index) {
    $(document).on("click", ".remove_button:eq("+ index + ")",
        function () {
        var adminId = $('.adminId')[index].innerHTML;
            $('.box2').MyPaging({
                size: mysize,
                current: curpage,
                prevHtml: '上一页',
                nextHtml: '下一页',
                layout: 'total, totalPage, prev, pager, next, jumper',
                jump: function () {
                    var _this = this;
                    curpage = _this.current;
                    $.ajax({
                        type: 'post',
                        async: false,
                        url: '/admin/removebyid',
                        data: {adminId:adminId},
                        success:function (){
                            getList(curpage)
                        }
                    })
                }
            });
        }
    )
})

//根据条件参数查询
$('#select_button').click(function (){
    getList(1);
});

//新增账户页面唤醒
$('#wave_addadmin_buttom').click(function (){
    $('#admin_add').css('display','block');
    $('.big_div').css('display','block');
});
//隐藏
$('#cancle_add_button').click(function (){
    $('#admin_add').css('display','none');
    $('.big_div').css('display','none');
});

//新增管理员
$('#save_add_button').click(function (){
    data = {
        employeeId: $("#add_empolyeeId").val(),
        adminPassword: $("#add_password").val(),
        phone: $("#add_phone").val(),
        adminType: $("#add_type").val(),
        adminStatus: $("#add_status").val(),
    }
    $.ajax({
            contentType: "application/json",
            type: 'post',
            async: false,
            url: '/admin/addadmin',
            data: JSON.stringify(data),
            success: function () {
                getList(1)
            }
        }
    )
});

