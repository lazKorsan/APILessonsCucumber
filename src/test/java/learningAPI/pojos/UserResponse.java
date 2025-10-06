package learningAPI.pojos;

// Bu POJO, /users endpoint'ine yapılan POST isteği sonucunda dönen yanıtı modellemek için kullanılır.
public class UserResponse {

    // Yanıtta gelen alanları temsil eden private field'lar.
    private String name;
    private String job;
    private String id;
    private String createdAt;

    // Getter ve Setter metotları, Rest-Assured'un (veya altındaki Jackson/GSON kütüphanesinin)
    // JSON alanlarını bu sınıfın field'larına ataması (deserialization) için gereklidir.

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
