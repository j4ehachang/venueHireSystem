package nz.ac.auckland.se281;

public class Venue {
  private String venueName;
  private String venueCode;
  private String capacityInput;
  private String hireFeeInput;
  private String nextAvailableDate;

  public Venue(String venueName, String venueCode, String capacityInput, String hireFeeInput) {
    this.venueName = venueName;
    this.venueCode = venueCode;
    this.capacityInput = capacityInput;
    this.hireFeeInput = hireFeeInput;
  }

  public String get_venueName() {
    return this.venueName;
  }

  public String get_venueCode() {
    return this.venueCode;
  }

  public String get_capacityInput() {
    return this.capacityInput;
  }

  public String get_hireFeeInput() {
    return this.hireFeeInput;
  }

  public String get_nextAvailableDate() {
    return this.nextAvailableDate;
  }

  public void set_nextAvailableDate(String nextAvailableDate) {
    this.nextAvailableDate = nextAvailableDate;
  }
}
