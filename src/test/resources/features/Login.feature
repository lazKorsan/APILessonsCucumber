Feature: Login
  Otomatik üretilmiş feature dosyası

  @TestNG=LoginTest::testEmpty
  Scenario: Empty credentials
    Given Test ortamı hazırlanır
    When Empty credentials
    Then Sonuçlar doğrulanır

  @TestNG=LoginTest::testSuccess
  Scenario: Successful login
    Given Kullanıcı login sayfasında
    When Successful login
    Then Giriş işlemi sonucu kontrol edilir

  @TestNG=LoginTest::testInvalid
  Scenario: Invalid credentials
    Given Test ortamı hazırlanır
    When Invalid credentials
    Then Sonuçlar doğrulanır

