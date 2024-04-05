package nz.ac.auckland.se281;

public abstract class Service {

  protected String bookingReference;
  protected String typeName;
  protected int serviceFee;

  public Service(String bookingReference, String typeName, int serviceFee) {
    this.bookingReference = bookingReference;
    this.typeName = typeName;
    this.serviceFee = serviceFee;
  }

  public String get_bookingReference() {
    return this.bookingReference;
  }

  public String get_typeName() {
    return this.typeName;
  }

  public int get_serviceFee() {
    return this.serviceFee;
  }
}

