package nz.ac.auckland.se281;

public class Booking {
  private String bookingReference;
  private String venueName;
  private String bookingDate;
  private String numberOfAttendees;
  private String dateOfBooking;
  private String customerEmail;
  private int cateringFee;
  private int floralFee;
  private String hireFee;

  public Booking(String bookingReference, String venueName, String bookingDate, String numberofAttendees) {
    this.bookingReference = bookingReference;
    this.venueName = venueName;
    this.bookingDate = bookingDate;
    this.numberOfAttendees = numberofAttendees;
  }

  public String get_bookingReference() {
    return this.bookingReference;
  }

  public String get_venueName() {
    return this.venueName;
  }

  public String get_bookingDate() {
    return this.bookingDate;
  }

  public String get_numberOfAttendees() {
    return this.numberOfAttendees;
  }

  public String get_dateOfBooking() {
    return this.dateOfBooking;
  }

  public void set_dateOfBooking(String dateOfBooking) {
    this.dateOfBooking = dateOfBooking;
  }

  public String get_customerEmail() {
    return this.customerEmail;
  }

  public void set_customerEmail(String customerEmail) {
    this.customerEmail = customerEmail;
  }

  public int get_cateringFee() {
    return this.cateringFee;
  }

  public void set_cateringFee(int cateringFee) {
    this.cateringFee = cateringFee;
  }

  public int get_floralFee() {
    return this.floralFee;
  }

  public void set_floralFee(int floralFee) {
    this.floralFee = floralFee;
  }

  public String get_hireFee() {
    return this.hireFee;
  }

  public void set_hireFee(String hireFee) {
    this.hireFee = hireFee;
  }
}
