@schema-validation @booking
Feature: Booking API JSON Schema Validasyonu

  API response'larının JSON Schema'ya uygun olduğunu doğrulama

  Background:
    Given API base URL'si "https://restful-booker.herokuapp.com" olarak ayarlanmıştır

  @smoke @schema
  Scenario: GET booking endpoint response schema validasyonu
    When "GET" request "booking/1" endpoint'ine gönderilir
    Then Status kod 200 olmalıdır
    And Response content type "application/json" olmalıdır
    And Response body booking JSON schema'sına uygun olmalıdır

  @schema @required-fields
  Scenario Outline: Zorunlu alanların schema validasyonu
    When "GET" request "booking/<id>" endpoint'ine gönderilir
    Then Response body aşağıdaki zorunlu alanları içermelidir:
      | field           |
      | firstname       |
      | lastname        |
      | totalprice      |
      | depositpaid     |
      | bookingdates    |
      | additionalneeds |

    Examples:
      | id |
      | 1  |
      | 2  |
      | 3  |

  @schema @data-types
  Scenario: Response data type validasyonu
    When "GET" request "booking/1" endpoint'ine gönderilir
    Then Response body'deki alan tipleri doğru olmalıdır:
      | field          | type     |
      | firstname      | string   |
      | lastname       | string   |
      | totalprice     | integer  |
      | depositpaid    | boolean  |
      | bookingdates   | object   |
      | additionalneeds| string   |

  @schema @nested-object
  Scenario: Bookingdates nested object schema validasyonu
    When "GET" request "booking/1" endpoint'ine gönderilir
    Then Response bookingdates object'i aşağıdaki alanları içermelidir:
      | field   | type   | format |
      | checkin | string | date   |
      | checkout| string | date   |

  @schema @negative
  Scenario: Geçersiz booking ID için schema validasyonu
    When "GET" request "booking/9999" endpoint'ine gönderilir
    Then Status kod 404 olmalıdır
    And Response body hata mesajı schema'sına uygun olmalıdır

  @schema @create-booking
  Scenario: POST booking request schema validasyonu
    Given Aşağıdaki booking data'sı hazırlanır:
      | firstname     | John               |
      | lastname      | Doe                |
      | totalprice    | 200                |
      | depositpaid   | true               |
      | checkin       | 2024-01-01         |
      | checkout      | 2024-01-05         |
      | additionalneeds| Breakfast         |
    When "POST" request "booking" endpoint'ine gönderilir
    Then Status kod 200 olmalıdır
    And Response body create booking JSON schema'sına uygun olmalıdır

  @schema @update-booking
  Scenario: PUT booking request ve response schema validasyonu
    Given Geçerli bir booking ID alınır
    And Aşağıdaki update data'sı hazırlanır:
      | firstname     | Jane               |
      | lastname      | Smith              |
      | totalprice    | 300                |
      | depositpaid   | false              |
      | checkin       | 2024-02-01         |
      | checkout      | 2024-02-05         |
      | additionalneeds| Lunch             |
    When "PUT" request "booking/<bookingId>" endpoint'ine gönderilir
    Then Status kod 200 olmalıdır
    And Response body update booking JSON schema'sına uygun olmalıdır