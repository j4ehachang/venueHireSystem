package nz.ac.auckland.se281;

public class Booking {
  private String bookingReference;
  private String venueName;
  private String bookingDate;
  private String numberOfAttendees;

  public Booking(String bookingReference, String venueName, String bookingDate, String numberofAttendees) {
    this.bookingReference = bookingReference;
    this.venueName = venueName;
    this.bookingDate = bookingDate;
    this.numberOfAttendees = numberofAttendees;
  }

}
