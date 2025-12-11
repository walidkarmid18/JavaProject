// OCCCDate.java
// Walid Karmid
// OCCC 2025
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.io.Serializable;

public class OCCCDate implements Serializable {
    private static final long serialVersionUID = 1L;

    // Constants for formatting
    public static final boolean FORMAT_US = true;
    public static final boolean FORMAT_EURO = false;
    public static final boolean STYLE_NUMBERS = true;
    public static final boolean STYLE_NAMES = false;
    public static final boolean SHOW_DAY_NAME = true;
    public static final boolean HIDE_DAY_NAME = false;

    private int dayOfMonth;
    private int monthOfYear;
    private int year;
    private GregorianCalendar gc;

    private boolean dateFormat = FORMAT_US;
    private boolean dateStyle = STYLE_NUMBERS;
    private boolean dateDayName = SHOW_DAY_NAME;

    // Constructors
    public OCCCDate() { // today's date
        gc = new GregorianCalendar();
        setFromCalendar(gc);
    }

    public OCCCDate(int day, int month, int year) throws OCCCDateException {
        gc = new GregorianCalendar();
        gc.setLenient(true); // allow automatic roll over

        // Original OCCC special rules (optional)
        if (day == 29 && month == 2 && year == 2022) { day = 1; month = 3; }
        if (day == 365 && month == 1 && year == 2022) { day = 31; month = 12; }

        gc.set(year, month - 1, day);

        // Store validated values
        this.year = gc.get(Calendar.YEAR);
        this.monthOfYear = gc.get(Calendar.MONTH) + 1;
        this.dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);

        // Throw exception if original input doesn't match validated date
        if (day != this.dayOfMonth || month != this.monthOfYear || year != this.year) {
            throw new OCCCDateException("Invalid date: " + month + "/" + day + "/" + year);
        }
    }

    public OCCCDate(GregorianCalendar cal) {
        gc = new GregorianCalendar();
        gc.setTime(cal.getTime());
        setFromCalendar(gc);
    }

    public OCCCDate(OCCCDate d) { // copy constructor
        this.dayOfMonth = d.dayOfMonth;
        this.monthOfYear = d.monthOfYear;
        this.year = d.year;
        this.gc = new GregorianCalendar(year, monthOfYear - 1, dayOfMonth);
        this.dateFormat = d.dateFormat;
        this.dateStyle = d.dateStyle;
        this.dateDayName = d.dateDayName;
    }

    private void setFromCalendar(GregorianCalendar c) {
        this.dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        this.monthOfYear = c.get(Calendar.MONTH) + 1; //  0-based calendar so add one
        this.year = c.get(Calendar.YEAR);
        this.gc = c;
    }

    // Setters for format
    public void setDateFormat(boolean df) { this.dateFormat = df; }
    public void setStyleFormat(boolean sf) { this.dateStyle = sf; }
    public void setDayName(boolean dn) { this.dateDayName = dn; }

    // Accessors
    public int getDayofMonth() { return dayOfMonth; }
    public int getMonthNumber() { return monthOfYear; }
    public int getYear() { return year; }
    public String getDayName() {
        String[] dayNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        return dayNames[gc.get(Calendar.DAY_OF_WEEK) - 1];
    }

    public String getMonthName() {
        String[] monthNames = {"January", "February", "March", "April", "May", "June",
          "July", "August", "September", "October", "November", "December"};
        return monthNames[monthOfYear - 1];
    }

    //  difference in years
    public int getDifferenceInYears() {
        GregorianCalendar today = new GregorianCalendar();
        return getDifferenceInYears(new OCCCDate(today));
    }

    public int getDifferenceInYears(OCCCDate d) {
        return Math.abs(d.year - this.year);
    }

    public boolean equals(OCCCDate d) {
        return this.dayOfMonth == d.dayOfMonth &&
               this.monthOfYear == d.monthOfYear &&
               this.year == d.year;
    }

    // toString
    public String toString() {
        String dateStr = "";

        // Format style: numeric or names
        if (dateStyle == STYLE_NUMBERS) {
            if (dateFormat == FORMAT_US) {
                dateStr = String.format("%02d/%02d/%04d", monthOfYear, dayOfMonth, year);
            } else { // European
                dateStr = String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear, year);
            }
        } else { // names
            if (dateFormat == FORMAT_US) {
                dateStr = getMonthName() + " " + dayOfMonth + ", " + year;
            } else { // European
                dateStr = dayOfMonth + " " + getMonthName() + " " + year;
            }
        }

        // Include day name if enabled
        if (dateDayName) {
            dateStr += " (" + getDayName() + ")";
        }

        return dateStr;
    }
}
