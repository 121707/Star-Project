var curpage;
// 初始化分页
var mysize = 8;
var baseUrl = 'http://star.pro.com:8082'

//设置tbody的html
function setTbody (arr) {
    var html = '';
    for (var i = 0; i < arr.length; i++) {
        var item = arr[i];
        html += '<li><div class="list-infoBox"><a class="imgtype" href="/shop/show/'+item.productId + '"> <img src ="' + item.pictureUrl +
            '"> </a><p class="infoBox"><a style="font-weight: bold"  href="infor.html"class="info-title">' + item.name + '</a></p><p class="priType-s fc-org priType">' + item.price + '元</p>' +
            '<br><h4>开售时间：'+ item.sellTime +'</h4></div></li>';
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
                    key : $('#search_key').val(),
                    minPrice : $('#search_minPrice').val(),
                    maxPrice : $('#search_maxPrice').val()
                }
            }
            $.ajax({
                    contentType: "application/json",
                    type: 'post',
                    async: false,
                    url: baseUrl + '/productService/secKillList',
                    data: JSON.stringify(data),
                    success: function (data) {
                        setTbody(data['list']);
                        $('#product_total').html(data['total'])
                        $('#key').text($('#search_key').val());
                        _this.setTotal(data['total']);
                    }
                }
            )
        }
    });
}

$('.search-btn').click(function (){
    getList(1);
})
