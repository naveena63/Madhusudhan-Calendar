package zastrait.GSTIT.EventsHIstory;

/**
 * Created by pratap.kesaboyina on 01-12-2015.
 */
public class EventsNamesModel {

    private String event;
    private String desc;

    public EventsNamesModel(String event, String desc) {
        this.event = event;
        this.desc = desc;

    }

    public EventsNamesModel(String eventName) {

    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
