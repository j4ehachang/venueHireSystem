package nz.ac.auckland.se281;

public class Booking {
  private String bookingReference;
  private String venueName;
  private String bookingDate;
  private String numberOfAttendees;
  private String dateOfBooking;

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
}
