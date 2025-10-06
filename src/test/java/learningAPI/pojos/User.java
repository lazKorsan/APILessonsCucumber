package learningAPI.pojos;

// Bu bir POJO (Plain Old Java Object) sınıfıdır.
// API'nin istek ve yanıt gövdelerindeki veri yapısını modellemek için kullanılır.
// POJO kullanmak, kodu ham JSON dizeleri kullanmaya göre daha güvenli, okunabilir ve sürdürülebilir hale getirir.
public class User {

    private String name;
    private String job;

    // Rest-Assured tarafından kullanılan Jackson/GSON gibi kütüphaneler,
    // JSON'dan nesne oluştururken (deserialization) bu boş yapıcı metoda ihtiyaç duyar.
    public User() {
    }

    public User(String name, String job) {
        this.name = name;
        this.job = job;
    }

    // Alanlara erişim için getter ve setter metotları gereklidir.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
