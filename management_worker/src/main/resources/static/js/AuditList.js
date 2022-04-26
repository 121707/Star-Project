var curpage;
var baseurl = "http://localhost:8080"
// 初始化分页
var mysize = 10;
//设置tbody的html
function setTbody (arr) {
    var html = '';
    for (var i = 0; i < arr.length; i++) {
        var item = arr[i];
        html += '<tr><td class="productId">' + item.productId + '</td><td>' + item.userId + '</td><td>'
            + item.name + '</td><td>'+ item.applyTime + '</td><td>'
            //以下为按钮
            + '<button class="informButton btn-danger   btn-small">详情</button>'+ "  " + '<button class="option_button   btn-small btn-danger">退回</button>'
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
            $.ajax({

                    type: 'post',
                    async: false,
                    url:  "/product/pageAuditList",
                    data: {
                        curpage: _this.current
                    },
                    success: function (data) {
                        setTbody(data['list']);
                        _this.setTotal(data['total']);
                    }
                }
            )
        }
    });
}

//审批界面睡眠
$('#cancel_audit_button').click(function (){
    $('.big_div').css("display","none");
})

//唤醒审批页面
$('.informButton').each(function (index) {
    $(document).on("click", ".informButton:eq("+ index + ")",
        function () {
            $('#auditProductPage').css("display","block");
            $('.big_div').css("display","block");
            var productId = $('.productId')[index].innerHTML;
            $.ajax({
                    type: 'post',
                    async: false,
                    data: {productId : productId},
                    url: '/product/getByPid',
                    success: function (p) {
                        $('#auditPpicture').attr("src",p.pictureUrl);
                        $('#auditProductId').val(p.productId);
                        $('#auditUserId').val(p.userId);
                        $('#auditName').val(p.name);
                        $('#auditPrice').val(p.price);
                        $('#auditCategory').val(p.category);
                        $('#auditBrand').val(p.brand);
                        $('#auditproductInform').val(p.productInform)

                    }
                }
            )
        })

})

//发送审批结果
//1为失败，2为成功
var pass = 0;
$('#audit_pass_button').click(function (){
    pass = 2;
});
$('#audit_fail_button').click(function (){
    pass = 1;
});

$('.audit_button').click(function (){
    $.ajax({
            type: 'post',
            async: false,
            data: {
                productId : $('#auditProductId').val(),
                auditStatus: pass
            },
            url: baseurl + '/product/uAuditStatus',
            success: function (p) {
                $('.big_div').css("display","none");
                getList(curpage);
            }
        }
    )
})