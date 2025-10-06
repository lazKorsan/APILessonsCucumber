package learningAPI.pojos;

// POST /booking isteği sonrası dönen yanıtın tamamını modelleyen POJO.
public class BookingResponse {

    private int bookingid;
    private Booking booking; // Yanıtın içinde, daha önce oluşturduğumuz Booking POJO'sunu içeren bir nesne var.

    public BookingResponse() {
    }

    public BookingResponse(int bookingid, Booking booking) {
        this.bookingid = bookingid;
        this.booking = booking;
    }

    // Getter ve Setter metotları
    public int getBookingid() {
        return bookingid;
    }

    public void setBookingid(int bookingid) {
        this.bookingid = bookingid;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
