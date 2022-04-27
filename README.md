# Star-Project
* 一个尽可能整合已有的知识点开发的商城项目
* 管理员module存在一些不符合开发规范的代码
* 商城module开始注意开发规范并加以运用
###### 前端使用技术
* html5
* css
* Jqury
* bootstrap
###### 后端使用技术
* springBoot
* mySql
* myBatis
* springMvc
* springSecurity
* redis
* nacos
* nginx
* Druid
* Docker
* maven
* Sentinel
###### 页面分析
##### 共通点
* security实现用户登录审计，审计用户的账号密码是否正确以及账号是否处于封禁状态
* mysql、druid 实现数据查询
* nacos 服务注册
* nginx 代理
##### 管理员界面
* 用户账号管理功能(CRUD)，管理员账号管理功能(CRUD)，用户上传商品审查功能，网站流水统计功能
![图片](https://user-images.githubusercontent.com/61684007/165207883-2ca24861-745d-4bc1-9bf7-b5f64f0adffd.png)
![图片](https://user-images.githubusercontent.com/61684007/165207895-0a5f8ecb-4f98-472b-af2d-294d02571fe6.png)

##### 用户界面
* 商品购买功能，用户修改资料功能，用户上传商品功能，用户查看资金流动汇总功能
* redis实现秒杀功能，在开售后20分钟内服务会自动会redis中查询库存，20分钟之后会转而从mysql中查询库存
* 利用对开售时间的判断查询符合条件的商品
* 秒杀和普通购买共用一个html模板，但都单独写了一份JS
* 这里比较难的是前端排版，分页数据展示套用了管理员界面的代码
* 支付系统的实现比较简单，使用了页面的延时跳转
![图片](https://user-images.githubusercontent.com/61684007/165209267-b65165fd-ea3d-4d63-80dc-1d3ca004dd5e.png)
![图片](https://user-images.githubusercontent.com/61684007/165414656-5bf1ccf4-b9a4-4919-ad0b-93b3ac6a1046.png)
* @requestpart参数分为对象 和 file文件 传输，
![图片](https://user-images.githubusercontent.com/61684007/165413129-7c76fd01-1078-49b5-960c-bca5432e07b6.png)
![图片](https://user-images.githubusercontent.com/61684007/165413246-c4dda3df-4eba-4589-8bb9-d8c578135ef3.png)
![图片](https://user-images.githubusercontent.com/61684007/165413262-27296e96-23eb-4efc-a973-553fcc6208ac.png)
