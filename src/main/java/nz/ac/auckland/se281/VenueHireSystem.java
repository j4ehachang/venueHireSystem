package nz.ac.auckland.se281;

import java.util.ArrayList;
import nz.ac.auckland.se281.Types.CateringType;
import nz.ac.auckland.se281.Types.FloralType;

public class VenueHireSystem {
  public VenueHireSystem() {}

  // Create an empty array list to store all the venues
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
    // When the currentDate has not been set the next available date for the venues are all invalid
    if (currentDate == null) {
      for (Venue venue : venueList) {
        venue.set_nextAvailableDate("INVALID");
      }
    } else {
      // For venues that have no bookings the next available date is the current date
      for (Venue venue : venueList) {
        if (venue.get_nextAvailableDate() == null) {
          venue.set_nextAvailableDate(currentDate);
        }
      }

      // The next available date cannot be in the past (behind the current date)
      for (Venue venue : venueList) {
        if (beforeCurrentDate(currentDate, venue.get_nextAvailableDate())) {
          nextAvailableDate(venue);
        }
      }
    }
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
            venue.get_hireFeeInput(),
            venue.get_nextAvailableDate());
      }
      // If there are more than one and less than ten venues print the number of venues as a string
    } else if (venueList.size() < 10 && venueList.size() > 1) {
      MessageCli.NUMBER_VENUES.printMessage("are", integerToString(venueList.size()), "s");
      for (Venue venue : venueList) {
        MessageCli.VENUE_ENTRY.printMessage(
            venue.get_venueName(),
            venue.get_venueCode(),
            venue.get_capacityInput(),
            venue.get_hireFeeInput(),
            venue.get_nextAvailableDate());
      }
      // If there are more than or equal to ten venues print out the number of venues as a integer
    } else if (venueList.size() >= 10) {
      MessageCli.NUMBER_VENUES.printMessage("are", String.valueOf(venueList.size()), "s");
      for (Venue venue : venueList) {
        MessageCli.VENUE_ENTRY.printMessage(
            venue.get_venueName(),
            venue.get_venueCode(),
            venue.get_capacityInput(),
            venue.get_hireFeeInput(),
            venue.get_nextAvailableDate());
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

  private boolean beforeCurrentDate(String currentDate, String testDate) {
    // Split both dates and convert to integers
    String[] currentDateParts = currentDate.split("/");
    int currentDay = Integer.parseInt(currentDateParts[0]);
    int currentMonth = Integer.parseInt(currentDateParts[1]);
    int currentYear = Integer.parseInt(currentDateParts[2]);

    String[] testDateParts = testDate.split("/");
    int testDay = Integer.parseInt(testDateParts[0]);
    int testMonth = Integer.parseInt(testDateParts[1]);
    int testYear = Integer.parseInt(testDateParts[2]);

    if (testYear < currentYear
        || (testYear == currentYear && testMonth < currentMonth)
        || (testYear == currentYear && testMonth == currentMonth && testDay < currentDay)) {
      return true;
    } else {
      return false;
    }
  }

  private boolean venueCodeExists = false;
  private Venue bookedVenue;
  // Create new array list of type Booking to store all of the bookings
  private ArrayList<Booking> bookingList = new ArrayList<>();

  // Create new array list to store booking dates
  private ArrayList<String> bookingDatesList = new ArrayList<>();

  public void makeBooking(String[] options) {

    // Check if the venue code exists and assign variable of type venue as the specific venue being
    // booked
    for (Venue venue : venueList) {
      if (venue.get_venueCode().equals(options[0])) {
        venueCodeExists = true;
        bookedVenue = venue;
        break;
      }
    }

    // Print error messages for when a booking is not able to be made
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

    if (beforeCurrentDate(currentDate, options[1])) {
      MessageCli.BOOKING_NOT_MADE_PAST_DATE.printMessage(options[1], currentDate);
      return;
    }

    // Return error message if same venue is booked on the same date
    for (Booking booking : bookingList) {
      if (booking.get_venueName().equals(bookedVenue.get_venueName())
          && booking.get_bookingDate().equals(options[1])) {
        MessageCli.BOOKING_NOT_MADE_VENUE_ALREADY_BOOKED.printMessage(
            booking.get_venueName(), booking.get_bookingDate());
        return;
      }
    }

    // If number of attendees are not ideal adjust them to fit the capacity
    int originalNumberOfAttendees = Integer.parseInt(options[3]);
    int capacity = Integer.parseInt(bookedVenue.get_capacityInput());
    int newNumberOfAttendees = -1;
    if (originalNumberOfAttendees < 0.25 * capacity) {
      // Increase number of attendees when the number of attendees is too small compared to the
      // capacity of venue
      newNumberOfAttendees = (int) (0.25 * capacity);
      MessageCli.BOOKING_ATTENDEES_ADJUSTED.printMessage(
          String.valueOf(originalNumberOfAttendees),
          String.valueOf(newNumberOfAttendees),
          bookedVenue.get_capacityInput());
      options[3] = String.valueOf(newNumberOfAttendees);
    } else if (originalNumberOfAttendees > capacity) {
      // Decrease number of attendees when the number of attendees is too large compared to the
      // capacity of venue
      newNumberOfAttendees = capacity;
      MessageCli.BOOKING_ATTENDEES_ADJUSTED.printMessage(
          String.valueOf(originalNumberOfAttendees),
          String.valueOf(newNumberOfAttendees),
          bookedVenue.get_capacityInput());
      options[3] = String.valueOf(newNumberOfAttendees);
    }

    // If no errors occur a booking is made successfully
    String bookingReference = BookingReferenceGenerator.generateBookingReference();
    MessageCli.MAKE_BOOKING_SUCCESSFUL.printMessage(
        bookingReference, bookedVenue.get_venueName(), options[1], options[3]);
    // Create a new instance of this specific booking and add to the arraylist of bookings
    Booking newBooking =
        new Booking(bookingReference, bookedVenue.get_venueName(), options[1], options[3]);
    bookingList.add(newBooking);

    nextAvailableDate(bookedVenue);
    newBooking.set_dateOfBooking(currentDate);
    newBooking.set_customerEmail(options[2]);
    newBooking.set_hireFee(bookedVenue.get_hireFeeInput());
  }

  private void nextAvailableDate(Venue venue) {
    // Store all booking dates for this venue in EMPTY arraylist
    bookingDatesList.clear();
    for (Booking booking : bookingList) {
      if (booking.get_venueName().equals(venue.get_venueName())) {
        bookingDatesList.add(booking.get_bookingDate());
      }
    }

    // Calculate the next available date by incrementing the temporary date by 1 day and checking if
    // it is booked
    String tempDate = currentDate;
    for (Booking booking : bookingList) {
      if (booking.get_venueName().equals(venue.get_venueName())) {
        while (bookingDatesList.contains(tempDate)) {
          tempDate = nextDay(tempDate);
        }
      }
    }
    venue.set_nextAvailableDate(tempDate);
  }

  private String nextDay(String date) {
    // Split the date string into 3 integers for each part of the date
    String[] dateParts = date.split("/");
    int day = Integer.parseInt(dateParts[0]);
    int month = Integer.parseInt(dateParts[1]);
    int year = Integer.parseInt(dateParts[2]);

    day = day + 1;

    // Return the next day in the form of a string
    return String.format("%02d/%02d/%04d", day, month, year);
  }

  private boolean codeExists = false;
  private Venue printVenue;
  private int bookingCount = 0;

  public void printBookings(String venueCode) {
    // Check if the given code actually exists and assign variable of type venue to the venue
    // corresponding to the given code
    for (Venue venue : venueList) {
      if (venue.get_venueCode().equals(venueCode)) {
        codeExists = true;
        printVenue = venue;
      }
    }
    // Calculate how many bookings there are for the specific venue
    for (Booking booking : bookingList) {
      if (printVenue.get_venueName().equals(booking.get_venueName())) {
        bookingCount++;
      }
    }

    // Return an error message when the code does not exist
    if (codeExists == false) {
      MessageCli.PRINT_BOOKINGS_VENUE_NOT_FOUND.printMessage(venueCode);
      return;
    }

    // Return all of the bookings for that specific venue
    if (bookingCount == 0) {
      MessageCli.PRINT_BOOKINGS_HEADER.printMessage(printVenue.get_venueName());
      MessageCli.PRINT_BOOKINGS_NONE.printMessage(printVenue.get_venueName());
    } else if (bookingCount > 0) {
      MessageCli.PRINT_BOOKINGS_HEADER.printMessage(printVenue.get_venueName());
      for (Booking booking : bookingList) {
        if (printVenue.get_venueName().equals(booking.get_venueName())) {
          MessageCli.PRINT_BOOKINGS_ENTRY.printMessage(
              booking.get_bookingReference(), booking.get_bookingDate());
        }
      }
    }
  }

  private boolean bookingExist = false;

  public void addCateringService(String bookingReference, CateringType cateringType) {
    for (Booking booking : bookingList) {
      if (bookingReference.equals(booking.get_bookingReference())) {
        bookingExist = true;
        thisBooking = booking;
      }
    }

    // Return error message when the booking reference does not exist
    if (!bookingExist) {
      MessageCli.SERVICE_NOT_ADDED_BOOKING_NOT_FOUND.printMessage("Catering", bookingReference);
      return;
    }

    MessageCli.ADD_SERVICE_SUCCESSFUL.printMessage(
        "Catering (" + cateringType.getName() + ")", bookingReference);

    int numberOfAttendees = Integer.parseInt(thisBooking.get_numberOfAttendees());
    int cateringFee = numberOfAttendees * cateringType.getCostPerPerson();
    thisBooking.set_cateringFee(cateringFee);
    thisBooking.set_cateringType(cateringType.getName());
  }

  public void addServiceMusic(String bookingReference) {
    for (Booking booking : bookingList) {
      if (bookingReference.equals(booking.get_bookingReference())) {
        bookingExist = true;
        thisBooking = booking;
      }
    }
    // Return error message when the booking reference does not exist
    if (!bookingExist) {
      MessageCli.SERVICE_NOT_ADDED_BOOKING_NOT_FOUND.printMessage("Music", bookingReference);
      return;
    }

    MessageCli.ADD_SERVICE_SUCCESSFUL.printMessage("Music", bookingReference);
    thisBooking.set_musicfee("500");
  }

  public void addServiceFloral(String bookingReference, FloralType floralType) {
    for (Booking booking : bookingList) {
      if (bookingReference.equals(booking.get_bookingReference())) {
        bookingExist = true;
        thisBooking = booking;
      }
    }

    // Return error message when the booking reference does not exist
    if (!bookingExist) {
      MessageCli.SERVICE_NOT_ADDED_BOOKING_NOT_FOUND.printMessage("Floral", bookingReference);
      return;
    }

    MessageCli.ADD_SERVICE_SUCCESSFUL.printMessage(
        "Floral (" + floralType.getName() + ")", bookingReference);

    thisBooking.set_floralFee(floralType.getCost());
    thisBooking.set_floralType(floralType.getName());
  }

  private Booking thisBooking;
  private int cateringFee = 0;
  private int floralFee = 0;
  private int musicFee = 0;

  public void viewInvoice(String bookingReference) {
    for (Booking booking : bookingList) {
      if (bookingReference.equals(booking.get_bookingReference())) {
        bookingExist = true;
        thisBooking = booking;
      }
    }

    // Return error message when the booking reference does not exist
    if (!bookingExist) {
      MessageCli.VIEW_INVOICE_BOOKING_NOT_FOUND.printMessage(bookingReference);
      return;
    }

    MessageCli.INVOICE_CONTENT_TOP_HALF.printMessage(
        bookingReference,
        thisBooking.get_customerEmail(),
        thisBooking.get_dateOfBooking(),
        thisBooking.get_bookingDate(),
        thisBooking.get_numberOfAttendees(),
        thisBooking.get_venueName());

    MessageCli.INVOICE_CONTENT_VENUE_FEE.printMessage(thisBooking.get_hireFee());

    if (thisBooking.get_cateringFee() > 0) {
      MessageCli.INVOICE_CONTENT_CATERING_ENTRY.printMessage(
          thisBooking.get_cateringType(), String.valueOf(thisBooking.get_cateringFee()));
      cateringFee = thisBooking.get_cateringFee();
    }

    if (!(thisBooking.get_musicFee() == null)) {
      MessageCli.INVOICE_CONTENT_MUSIC_ENTRY.printMessage(thisBooking.get_musicFee());
      musicFee = 500;
    }

    if (thisBooking.get_floralFee() > 0) {
      MessageCli.INVOICE_CONTENT_FLORAL_ENTRY.printMessage(
          thisBooking.get_floralType(), String.valueOf(thisBooking.get_floralFee()));
      floralFee = thisBooking.get_floralFee();
    }

    // Calculating total fee
    int totalFee = floralFee + cateringFee + musicFee + Integer.parseInt(thisBooking.get_hireFee());

    MessageCli.INVOICE_CONTENT_BOTTOM_HALF.printMessage(String.valueOf(totalFee));
  }
}
