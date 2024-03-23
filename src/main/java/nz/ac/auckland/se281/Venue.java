package nz.ac.auckland.se281;

public class Venue {
  private String venueName;
  private String venueCode;
  private String capacityInput;
  private String hireFeeInput;

  public Venue (String venueName, String venueCode, String capacityInput, String hireFeeInput) {
    this.venueName = venueName;
    this.venueCode = venueCode;
    this.capacityInput = capacityInput;
    this.hireFeeInput =  hireFeeInput;
  }

  public void get_venueName(){
    System.out.println(this.venueName);
  }  

  public void get_venueCode(){
    System.out.println(this.venueCode);
  }  

  public void get_capacityInput(){
    System.out.println(this.capacityInput);
  }  

  public void get_hireFeeInput(){
    System.out.println(this.hireFeeInput);
  }  
}
