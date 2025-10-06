Feature: Usermanagement
  Kullanıcı yönetimi işlemleri

  @TestNG=UserTest::testCreateUser
  Scenario: Create new user
    Given Test verileri hazırlanır
    When Create new user
    Then İşlem sonucu doğrulanır

  @TestNG=UserTest::testUpdateUser
  Scenario: Update user info
    Given Test verileri hazırlanır
    When Update user info
    Then İşlem sonucu doğrulanır