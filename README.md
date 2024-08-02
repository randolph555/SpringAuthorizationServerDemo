
# OAuth2 Authorization Code Grant
### https://docs.spring.io/spring-authorization-server/reference/getting-started.html


## 1.获取授权码

#### 1.1.项目启动后 springboot run

#### 1.2.浏览器请求这个地址：http://localhost:8080/oauth2/authorize?response_type=code&client_id=client&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/oidc-client&scope=openid

#### 1.3.授权码模式，用户登录后，授权码会被重定向到redirect_uri，并带上code参数
http://127.0.0.1:8080/login/oauth2/code/oidc-client?code=HHM9OZGylOy4NCUi_h7Ju7V6Q7bOuK6_6hDuYyD_rcozPUwar8cl42Ochge1BChIndt2s6xSQCdQXWR81wz94OhwWEfNH5I1pYZk4NsBq8WSwhZJBu58vJZVyesSQW08&continue

## 2.获取token

#### 2.1这是curl方式，浏览器获取到的code
curl --location 'http://localhost:8080/oauth2/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Authorization: Basic Y2xpZW50Om15c2VjcmV0MTAyOQ==' \
--data-urlencode 'grant_type=authorization_code' \
--data-urlencode 'code=UkShaYNHz5_8U0B3H8ZPJUFLSOovmoiSjgEmCracqfXkW9i0bJ5zVe3VI8jvMn_vynrHjDo9RhM1DmRhMWfvvXaKziLhf6ljEeZ4GhGcvPNWT63_xHd__MpG7KxaMaOF' \
--data-urlencode 'redirect_uri=http://127.0.0.1:8080/login/oauth2/code/oidc-client' \
--data-urlencode 'client_id=client' \
--data-urlencode 'client_secret=mysecret1029'

#### 2.2 这是从postman获取token
![img.png](img.png)
![img_1.png](img_1.png)


## 3.根据token获取用户信息，接口返回的access_token

#### 3.1这是curl方式
curl --location 'http://localhost:8080/api/user' \
--header 'Authorization: Bearer eyJraWQiOiJmY2I4Njc5ZC04MTk3LTRiZDEtYjY3NC03MjVhMjE0OWM4MjUiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXVkIjoiY2xpZW50IiwibmJmIjoxNzIyNTg3OTM3LCJzY29wZSI6WyJvcGVuaWQiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwIiwiZXhwIjoxNzIyNTg5NzM3LCJpYXQiOjE3MjI1ODc5MzcsImp0aSI6IjQ4NzlhYTMwLTQ2MDUtNGY2YS04MjViLTI0ZjFhOTNmMmRiNCJ9.p7bUsVDsjddLeOiYTHBGz61GUA0RV_pMy6RHhRM55pYuEz6M5jUeDCwDtJO_iQC26atK1HaG9jQHGoYCmpjjvEWqoYd6Qeo9D28FVwWFaKU-EN7Yvc_7AVAjWWR9AB2dkS-tTc3126NIHT9xRYqlucYY43NkNjL5bjKhLDiowyjTBiQ6xYdbvEq7WpadBikLw6fObu9M0MEQrQ9EUqVuIyjzvD_jM1KnEi9Qs2xdzPFkYXfCCFjK74XAmxtGVgdhY_wjsN6MVA_z2FEBCD5JiT7LdDRoJBRkGtbJ7UOjXsf2yHHePt3l1jFMwGwzPQh3xJ-msIYw7AgQC5ZGkhX7xg'

#### 3.2这是postman请求
![img_2.png](img_2.png)


################################################################################################################

# 刷新access_token
echo -n 'client:mysecret1029' | base64

curl -X POST http://localhost:8080/oauth2/token \
-H "Authorization: Basic Y2xpZW50Om15c2VjcmV0MTAyOQ==" \
-H "Content-Type: application/x-www-form-urlencoded" \
-d "grant_type=refresh_token&refresh_token=iXpWsE_8oAFxWRTCjfk8rdcc7cwUmvhV3fnkKM-Zxtq1ew7pZt0h3E2wHz4pdBEo5bCxAl7pffL0G8EEh1Ytz_9p8YrZJuRp7mJRnk7LCeD1mG9ippjAWc02nOp5mDHl"