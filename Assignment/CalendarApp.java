import java.util.*;
import javax.swing.JOptionPane;

public class CalendarApp {
    public static void main(String[] args) {
        // Get user input for year and month
        String yearInput = JOptionPane.showInputDialog("Enter full year (e.g., 2024): ");
        int year = Integer.parseInt(yearInput); //convert String year to int
        System.out.println("Enter full year (e.g., 2024): " + year);

        String monthInput = JOptionPane.showInputDialog("Enter month as a number between 1 and 12: ");
        int month = Integer.parseInt(monthInput); //convert String month to int
        System.out.println("Enter month as a number between 1 and 12: " + month);

        // Validate the month input
        if (month < 1 || month > 12) {
            System.out.println("Invalid month. Please enter a number between 1 and 12.");
            return; //exit system
        }

        // Create a CalendarMonth object to print the calendar
        CalendarMonth calendarMonth = new CalendarMonth(month, year);
        calendarMonth.printCalendar();
    }
}

class CalendarMonth {
    int month;
    int year;
    int numOfDays;

    // Constructor
    CalendarMonth(int m, int y) {
        month = m;
        year = y;
        numOfDays = calculateDaysInMonth(); // Calculate days in the given month
    }

    // Calculate the number of days in a month
    int calculateDaysInMonth() {
        if (month == 2) { // Check for February
            if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
                return 29; // Leap year
            } else {
                return 28; // Non-leap year
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30; // Months with 30 days
        } else {
            return 31; // Months with 31 days
        }
    }

    // Print the calendar
    void printCalendar() {
        Calendar calendar = Calendar.getInstance(); // Create Calendar instance to determine the first day of the month
        calendar.set(year, month - 1, 1); // Set the calendar to the first day of the specified month
        int startDay = calendar.get(Calendar.DAY_OF_WEEK); // Day of the week (1 = Sunday, 2 = Monday, ..., 7 = Saturday)
        // starDay will hold the integer of the day of the week of the first day of the month

        //print month int to string (january/february ...)
        switch (month) {
            case 1:
                System.out.println(" January " + year);
                break;
            case 2:
                System.out.println(" February " + year); 
                break;
            case 3:
                System.out.println(" March " + year);
                break;
            case 4:
                System.out.println(" April " + year);
                break;
            case 5:
                System.out.println(" May " + year);
                break;
            case 6:
                System.out.println(" June " + year);
                break;
            case 7:
                System.out.println(" July " + year);
                break;
            case 8:
                System.out.println(" August " + year);
                break;
            case 9:
                System.out.println(" September " + year);
                break;
            case 10:
                System.out.println(" October " + year);
                break;
            case 11:
                System.out.println(" November " + year);
                break;
            case 12:
                System.out.println(" December " + year);
                break;
            default:
                break;
        }

        System.out.println("------------------------------------");

        System.out.println("Sun Mon Tue Wed Thu Fri Sat");

        // Print spaces for days before the first day of the month
        for (int i = 1; i < startDay; i++) {
            System.out.print("    ");
        }

        // Print the days of the month
        for (int day = 1; day <= numOfDays; day++) {
            System.out.printf("%3d ", day); // Print day number with padding
            if ((day + startDay - 1) % 7 == 0) { // Move to the next line after Saturday
                System.out.println();
            }
        }
        System.out.println(); 
    }
}
