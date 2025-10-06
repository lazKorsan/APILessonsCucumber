Feature: Paymentapı
  Ödeme API testleri

  @TestNG=PaymentTest::testRefund
  Scenario: Refund payment
    Given API endpoint hazırlanır
    When Refund payment
    Then Response doğrulanır

  @TestNG=PaymentTest::testProcess
  Scenario: Process payment
    Given API endpoint hazırlanır
    When Process payment
    Then Response doğrulanır

