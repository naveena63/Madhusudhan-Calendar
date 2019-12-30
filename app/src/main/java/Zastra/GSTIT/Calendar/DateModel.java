package Zastra.GSTIT.Calendar;

import java.util.ArrayList;

public class DateModel {
    String color_code;
    String calendarDate;
    private ArrayList<EventModel> allItemsInSection;

    public ArrayList<EventModel> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<EventModel> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }

    public String getColor_code() {
        return color_code;
    }

    public void setColor_code(String color_code) {
        this.color_code = color_code;
    }

    public String getCalendarDate() {
        return calendarDate;
    }

    public void setCalendarDate(String calendarDate) {
        this.calendarDate = calendarDate;
    }
}