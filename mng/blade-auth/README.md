## 目前主要支持的oauth协议
一、 授权码模式
授权码模式(authorization_code)主要针对第三方应用，是最为复杂也最为安全的一种模式，操作步骤如下
1. 访问地址：http://localhost:8100/oauth/authorize?client_id=blade&redirect_uri=http://example.com&code=233333&response_type=code
2. 获取跳转后的code值(http://example.com/?code=VhYNLR)之后，调用 http://localhost/blade-auth/oauth/token 传入对应的参数

请求头：
Authorization ： Basic YmxhZGU6YmxhZGU= （"YmxhZGU6YmxhZGU="为clientId:clientSecret串转换为的base64编码）

表单：
grant_type：authorization_code
scope：all
code：VhYNLR
redirect_uri： http://example.com

二、 密码模式
密码模式(password)主要针对自家应用，可信度较高，所以可以使用简便安全共存的模式，操作步骤如下
1. 直接调用 http://localhost/blade-auth/oauth/token 传入对应的参数

请求头：
Authorization ： Basic YmxhZGU6YmxhZGU= （"YmxhZGU6YmxhZGU="为clientId:clientSecret串转换为的base64编码）

表单：
grant_type：password
scope：all
username：admin
password：123456

## 获取到token后如何获取用户信息
1. 拼接请求头
Authorization ：bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0ZXN0IjoidGVzdCIsInVzZXJfbmFtZSI6ImFkbWluIiwic2NvcGUiOlsiYWxsIl0sImV4cCI6MTU1MzE2MTA5NSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6IjE0YmMyYjAyLTgxY2UtNDFiNC04ZTI3LTA5YWE0ZmU4ZWMwYyIsImNsaWVudF9pZCI6ImJsYWRlIn0.jTmioQDq-fSNNn7YCwl3wP0JE-etSWtzLDe545mDbP4
2. 调用 http://localhost/blade-auth/oauth/user-info 既可获得对应用户信息