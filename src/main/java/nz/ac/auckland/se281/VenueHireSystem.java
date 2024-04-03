package nz.ac.auckland.se281;

import java.util.ArrayList;
import nz.ac.auckland.se281.Types.CateringType;
import nz.ac.auckland.se281.Types.FloralType;

public class VenueHireSystem {
  public VenueHireSystem() {}

  private ArrayList<Venue> venueList = new ArrayList<>();

  private String integerToString(int size) {
    // Return corresponding integer in the format of a string
    switch (size) {
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
    // If there are no venues in the system tell the user to create a venue first
    if (venueList.isEmpty()) {
      MessageCli.NO_VENUES.printMessage();

      // If there is one venue in the system print out that there is one venue
    } else if (venueList.size() == 1) {
      MessageCli.NUMBER_VENUES.printMessage("is", "one", "");
      // Print out the details of the venues in the system
      for (Venue venue : venueList) {
        MessageCli.VENUE_ENTRY.printMessage(
            venue.get_venueName(),
            venue.get_venueCode(),
            venue.get_capacityInput(),
            venue.get_hireFeeInput());
      }
      // If there are more than one and less than ten venues print the number of venues as a string
    } else if (venueList.size() < 10 && venueList.size() > 1) {
      MessageCli.NUMBER_VENUES.printMessage("are", integerToString(venueList.size()), "s");
      for (Venue venue : venueList) {
        MessageCli.VENUE_ENTRY.printMessage(
            venue.get_venueName(),
            venue.get_venueCode(),
            venue.get_capacityInput(),
            venue.get_hireFeeInput());
      }
      // If there are more than or equal to ten venues print out the number of venues as a integer
    } else if (venueList.size() >= 10) {
      MessageCli.NUMBER_VENUES.printMessage("are", String.valueOf(venueList.size()), "s");
      for (Venue venue : venueList) {
        MessageCli.VENUE_ENTRY.printMessage(
            venue.get_venueName(),
            venue.get_venueCode(),
            venue.get_capacityInput(),
            venue.get_hireFeeInput());
      }
    }
  }

  public void createVenue(
      String venueName, String venueCode, String capacityInput, String hireFeeInput) {
    Venue newVenue = new Venue(venueName, venueCode, capacityInput, hireFeeInput);

    // Return error message if the capacity given by the user is negative or not a number
    try {
      int capacity = Integer.parseInt(capacityInput);
      if (capacity < 0) {
        MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("capacity", " positive");
        return;
      }
    } catch (Exception e) {
      MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("capacity", "");
      return;
    }

    // Return error message if the hire fee given by the user is negative or not a number
    try {
      int hireFee = Integer.parseInt(hireFeeInput);
      if (hireFee < 0) {
        MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("hire fee", " positive");
        return;
      }
    } catch (Exception e) {
      MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("hire fee", "");
      return;
    }

    // Return error message if the venue code given by the user already exists for another venue
    for (Venue venue : venueList) {
      if (venue.get_venueCode().equals(venueCode)) {
        MessageCli.VENUE_NOT_CREATED_CODE_EXISTS.printMessage(venueCode, venue.get_venueName());
        return;
      }
    }

    // Return error message if the venue name is not given
    if (venueName.isEmpty()) {
      MessageCli.VENUE_NOT_CREATED_EMPTY_NAME.printMessage();
    } else {
      // If no errors were found create venue and add to the arraylist of venues
      MessageCli.VENUE_SUCCESSFULLY_CREATED.printMessage(venueName, venueCode);
      venueList.add(newVenue);
    }
  }

  // Create string to save the current date
  private String currentDate = null;

  public void setSystemDate(String dateInput) {
    MessageCli.DATE_SET.printMessage(dateInput);
    currentDate = dateInput;
  }

  public void printSystemDate() {
    // Print error message if system date is not set
    if (currentDate == null) {
      MessageCli.CURRENT_DATE.printMessage("not set");
    } else {
      // Print current date if it was previously set by user
      MessageCli.CURRENT_DATE.printMessage(currentDate);
    }
  }

  private boolean venueCodeExists = false;
  private String bookedVenueName = "";
  private ArrayList<Booking> bookingList = new ArrayList<>();

  public void makeBooking(String[] options) {

    for (Venue venue : venueList) {
      if (venue.get_venueCode().equals(options[0])) {
        venueCodeExists = true;
        bookedVenueName = venue.get_venueName();
        break;
      }
    }

    if (currentDate == null) {
      MessageCli.BOOKING_NOT_MADE_DATE_NOT_SET.printMessage("not set");
      return;
    } else if (venueList.size() < 1) {
      MessageCli.BOOKING_NOT_MADE_NO_VENUES.printMessage();
      return;
    } else if (venueCodeExists == false) {
      MessageCli.BOOKING_NOT_MADE_VENUE_NOT_FOUND.printMessage(options[0]);
      return;
    }

    // Split current date and date given by user and convert to integers
    String[] currentDateParts = currentDate.split("/");
    int currentDay = Integer.parseInt(currentDateParts[0]);
    int currentMonth = Integer.parseInt(currentDateParts[1]);
    int currentYear = Integer.parseInt(currentDateParts[2]);

    String[] bookingDateParts = options[1].split("/");
    int bookingDay = Integer.parseInt(bookingDateParts[0]);
    int bookingMonth = Integer.parseInt(bookingDateParts[1]);
    int bookingYear = Integer.parseInt(bookingDateParts[2]);

    // Booking date cannot be in the past
    if (bookingYear < currentYear
        || (bookingYear == currentYear && bookingMonth < currentMonth)
        || (bookingYear == currentYear
            && bookingMonth == currentMonth
            && bookingDay < currentDay)) {

      MessageCli.BOOKING_NOT_MADE_PAST_DATE.printMessage(options[1], currentDate);
      return;
    }

    // If no errors occur a booking is made successfully
    String bookingReference = BookingReferenceGenerator.generateBookingReference();
    MessageCli.MAKE_BOOKING_SUCCESSFUL.printMessage(
        bookingReference, bookedVenueName, options[1], options[3]);
    Booking newBooking = new Booking(bookingReference, bookedVenueName, options[1], options[3]);
    bookingList.add(newBooking);
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
