package learningAPI.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

// Bu POJO, reqres.in API'sinin /users endpoint'inden dönen yanıtın tamamını temsil eder.
// Sayfalama bilgilerini ve kullanıcı listesini içerir.
public class UserListPage {

    private int page;

    @JsonProperty("per_page")
    private int perPage;

    private int total;

    @JsonProperty("total_pages")
    private int totalPages;

    // En önemli kısım: Yanıttaki "data" dizisini, bir ReqresUser nesneleri listesine çevirir.
    private List<ReqresUser> data;

    // Getter ve Setter metotları
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ReqresUser> getData() {
        return data;
    }

    public void setData(List<ReqresUser> data) {
        this.data = data;
    }
}
