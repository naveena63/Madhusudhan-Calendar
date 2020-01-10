package zastrait.GSTIT.EventsHIstory;

import java.util.ArrayList;

/**
 * Created by pratap.kesaboyina on 30-11-2015.
 */
public class AllEventHistoryModel {

    private String headerTitle;
    private String totalcount;
    private ArrayList<EventsNamesModel> allItemsInSection;

    public AllEventHistoryModel() {

    }
    public AllEventHistoryModel(String headerTitle, String totalcount, ArrayList<EventsNamesModel> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.totalcount = totalcount;
        this.allItemsInSection = allItemsInSection;
    }


    public String getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(String totalcount) {
        this.totalcount = totalcount;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<EventsNamesModel> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<EventsNamesModel> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }


}
