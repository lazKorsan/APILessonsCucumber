Feature: US001_JPH API GET REQUEST

# https://jsonplaceholder.typicode.com/posts/44 endpointine
# bir GET request gonderdigimizde donen response'un
# status code'unun 200 olduğunu,
# content type degerinin "application/json; charset=utf-8 " ve
# "title" degerinin "optio dolor molestias sit" oldugunu test edin
  @test
  Scenario: JPH Get Request Testi

    * kullanici "JPHBaseUrl" adresine gider
    # Burada endpoint base url oluşturuldu
    * kullanici path parametresi olarak "posts/44" girer
    # Burada endpoint parametresi girilerek enpoint tamamlandı
    * kullanici GET request yaparak donen response bilgilerini kaydeder
    * kullanici response statusCode degerinin 200 oldugunu test eder
    * kullanici content type degerinin "application/json; charset=utf-8" oldugunu test eder
    * kullanici response degerini jsonpath olarak kaydeder
    * kullanici response "title" degerinin "optio dolor molestias sit" oldugunu test eder

#    https://jsonplaceholder.typicode.com/posts/70 url'ine asagidaki body’e sahip bir
#    POST request yolladigimizda donen response’in
#    status kodunun 200, content type’inin “application/json; charset=utf-8”,
#    Connection header degerinin “keep-alive”
#    ve response body’sinin asagida verilen ile ayni oldugunu test ediniz

#   Request Body                      Expected Data :
#   {                                     {
#    "title": "Ahmet",                      "title": "Ahmet",
#    "body": "Merhaba",                     "body": "Merhaba",
#    "userId": 10,                          "userId": 10,
#    "id": 70                               "id": 70
#   }                                     }
@test
  Scenario: JPH PUT Request yapma

    * kullanici "JPHBaseUrl" adresine gider
    * kullanici path parametresi olarak "posts/70" girer
    * kullanici PUT request yapabilmek için "Ahmet","Merhaba" , 10 70 bilgileri ile reqBody olusturur
    * kullanici PUT request yaparak response bilgilerini kaydeder
    * kullanici response statusCode degerinin 200 oldugunu test eder
    * kullanici content type degerinin "application/json; charset=utf-8" oldugunu test eder
    * kullanici "Connection" header dergerinin "keep-alive" oldugunu test eder
    * kullanici response degerini jsonpath olarak kaydeder
    * kullanici response id degerinin 70 oldugunu test eder
    * kullanici response userId degerinin 10 oldugunu test eder
    * kullanici response "title" degerinin "Ahmet" oldugunu test eder
    * kullanici response "body" degerinin "Merhaba" oldugunu test eder



