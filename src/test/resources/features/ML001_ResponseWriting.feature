
  Feature: RestFull sayfasinda sorgulama sonuclarini yazdirma

    @xyzt1
    Scenario: RestFull sayfasi için sablon yazdirma testi hazirlanir.
    Ciktilar testdata olarak hazirlanir

      Given Kullanici url hazirlar
      Then Hazirladigi url e endpoint ekler
      Then test datalarini olusturacak kod blogunu hazirlar
      Then donen yaniti konsola yazdirir


      Scenario: RestFull Sayfasi icin schema Testi yapilir

