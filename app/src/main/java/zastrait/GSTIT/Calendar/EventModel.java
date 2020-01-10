package zastrait.GSTIT.Calendar;

public class EventModel {
    String eventName;
    String eventDesc;
    String eventColor;

    public String getEventColor() {
        return eventColor;
    }

    public void setEventColor(String eventColor) {
        this.eventColor = eventColor;
    }

    public EventModel(String event_name,String eventColor) {
        this.eventName=event_name;
        this.eventColor=eventColor;
    }

//    public EventModel(String eventnam) {
//        this.eventName=eventnam;
//
//    }


    public String getEventName() {


        return eventName;
    }


    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }
}
