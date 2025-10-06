Feature: Booking
  Rezervasyon işlemleri

  @TestNG=BookingTest::testCancel
  Scenario: Cancel booking
    Given Test verileri hazırlanır
    When Cancel booking
    Then İşlem sonucu doğrulanır

  @TestNG=BookingTest::testUpdate
  Scenario: Update booking
    Given Test verileri hazırlanır
    When Update booking
    Then İşlem sonucu doğrulanır

  @TestNG=BookingTest::testCreate
  Scenario: Create booking
    Given Test verileri hazırlanır
    When Create booking
    Then İşlem sonucu doğrulanır

