Feature: Nöbetci Eczane Sorgulama
@collect
  Scenario: İstanbul ili Üsküdar ilceye ait nöbetci eczane sorgusu

  # curl --request GET \
  #--url 'https://api.collectapi.com/health/dutyPharmacy?ilce=%C3%87ankaya&il=Ankara' \
  #--header 'authorization: apikey 2ecC1yGMUbcPi9V1YuXKJ5:2TLu6uoUy3ZDCvIS2xIsOu' \
  #--header 'content-type: application/json'

    Given kullanici sorgu icin "collectApiUrl" adresine gider
    Then kullanici path parametresi yerine "health/dutyPharmacy" girer
    Then kullanici query parametlerine il icin "istanbul" ilce icin "uskudar" girer
    Then kullanici header degerleri ile bir GET request yapar ve response degeri kaydeder
    Then kullanici donen response degerinin yazdirir