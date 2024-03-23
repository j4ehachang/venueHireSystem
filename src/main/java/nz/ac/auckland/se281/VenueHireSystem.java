package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Types.CateringType;
import nz.ac.auckland.se281.Types.FloralType;
import java.util.ArrayList;

public class VenueHireSystem {
  public VenueHireSystem() {}
  ArrayList<Venue> venueList = new ArrayList<>();
  
  public String switchCase(int size){
    
    switch(size){
      case 2:
        return "two";
      case 3:
        return "three";
      case 4:
        return "four";
      case 5:
        return "five";
      case 6:
        return "six";
      case 7:
        return "seven";
      case 8:
        return "eight";
      case 9:
        return "nine";
      default:
        return "INVALID";
    }
  }

  public void printVenues() {
    if (venueList.isEmpty()) { 
      MessageCli.NO_VENUES.printMessage();
    } else if (venueList.size() == 1) {
      MessageCli.NUMBER_VENUES.printMessage("is", "one", "");
      for (Venue venue : venueList) {
        MessageCli.VENUE_ENTRY.printMessage(venue.get_venueName(), venue.get_venueCode(), venue.get_capacityInput(), venue.get_hireFeeInput());
      }

    } else if (venueList.size() < 10 && venueList.size() > 1) {
      MessageCli.NUMBER_VENUES.printMessage("are", switchCase(venueList.size()), "s");
      for (Venue venue : venueList) {
        MessageCli.VENUE_ENTRY.printMessage(venue.get_venueName(), venue.get_venueCode(), venue.get_capacityInput(), venue.get_hireFeeInput());
      }

    } else if (venueList.size() >= 10) {
      MessageCli.NUMBER_VENUES.printMessage("are", String.valueOf(venueList.size()), "s");
      for (Venue venue : venueList) {
        MessageCli.VENUE_ENTRY.printMessage(venue.get_venueName(), venue.get_venueCode(), venue.get_capacityInput(), venue.get_hireFeeInput());
      }
    }
  }

  public void createVenue(
      String venueName, String venueCode, String capacityInput, String hireFeeInput) {
      Venue newVenue = new Venue(venueName, venueCode, capacityInput, hireFeeInput);
      
      try {
      int capacity = Integer.parseInt(capacityInput);
      if (capacity < 0) {
        MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("capacity", " positive");
        return;
      }
      } catch(Exception e){
        MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("capacity", "");
        return;
      }

      try {
        int hireFee = Integer.parseInt(hireFeeInput);
        if (hireFee < 0) {
          MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("hire fee", " positive");
          return;
        }
        } catch(Exception e){
          MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("hire fee", "");
          return;
        }

      for (Venue venue : venueList) {
        if (venue.get_venueCode().equals(venueCode)){
          MessageCli.VENUE_NOT_CREATED_CODE_EXISTS.printMessage(venueCode, venue.get_venueName());
          return;
        }
      }

      if (venueName.isEmpty()) {
        MessageCli.VENUE_NOT_CREATED_EMPTY_NAME.printMessage();
      } else {
        MessageCli.VENUE_SUCCESSFULLY_CREATED.printMessage(venueName, venueCode);
        venueList.add(newVenue);
      }
  }

  public void setSystemDate(String dateInput) {
    // TODO implement this method
  }

  public void printSystemDate() {
    // TODO implement this method
  }

  public void makeBooking(String[] options) {
    // TODO implement this method
  }

  public void printBookings(String venueCode) {
    // TODO implement this method
  }

  public void addCateringService(String bookingReference, CateringType cateringType) {
    // TODO implement this method
  }

  public void addServiceMusic(String bookingReference) {
    // TODO implement this method
  }

  public void addServiceFloral(String bookingReference, FloralType floralType) {
    // TODO implement this method
  }

  public void viewInvoice(String bookingReference) {
    // TODO implement this method
  }
}
