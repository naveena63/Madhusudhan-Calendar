package zastrait.GSTIT.EventsHIstory;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import zastrait.GSTIT.R;
import zastrait.GSTIT.Utils.AppConstants;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventHistoryFragment extends Fragment {

    ArrayList<AllEventHistoryModel> allSampleData;
    View view;
    RequestQueue requestQueue;
    RecyclerView my_recycler_view;
    AllEventsHistoryAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the calendar_item for this fragment
        view = inflater.inflate(R.layout.fragment_event_history, container, false);
        requestQueue = Volley.newRequestQueue(getActivity());
        allSampleData = new ArrayList<AllEventHistoryModel>();

        my_recycler_view= (RecyclerView) view.findViewById(R.id.my_recycler_view);

        my_recycler_view.setHasFixedSize(true);

        adapter = new AllEventsHistoryAdapter(getActivity(), allSampleData);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        createDummyData();

        return view;
    }

    public void createDummyData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConstants.BASE_URL+AppConstants.EVENTHISTORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("eventHistory","eventhiostory"+response);
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            if (status.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    AllEventHistoryModel allEventHistoryModel = new AllEventHistoryModel();
                                    String date = jsonObject1.getString("event_date");
                                    allEventHistoryModel.setHeaderTitle(date);
                                    JSONArray jsonArray1 = jsonObject1.getJSONArray("event_names");

                                    ArrayList<EventsNamesModel> singleItem = new ArrayList<>();
                                    for (int j = 0; j < jsonArray1.length(); j++) {
                                        JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
                                        String eventName= jsonObject2.getString("event_name");
                                        String desc= jsonObject2.getString("event_description");
                                        singleItem.add(new EventsNamesModel(eventName,desc));
                                    }

                                    allEventHistoryModel.setAllItemsInSection(singleItem);
                                    allSampleData.add(allEventHistoryModel);
                                }
                                my_recycler_view.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Couldn't Find Network", Toast.LENGTH_LONG).show();

            }
        });
        requestQueue.add(stringRequest);
    }
}


//        for (int i = 1; i <= 5; i++) {
//
//            AllEventHistoryModel dm = new AllEventHistoryModel();
//
//            dm.setHeaderTitle("Date :20-2-2109");
//            dm.setHeaderTitle("Date :12-2-2109");
//            dm.setHeaderTitle("Date :15-2-2109");
//            dm.setHeaderTitle("Date :17-2-2109");
//            dm.setHeaderTitle("Date :6-2-2109");
//            dm.setTotalcount("Total Count :9");
////
//
//
//            ArrayList<EventsNamesModel> singleItem = new ArrayList<EventsNamesModel>();
//            for (int j = 0; j <= 5; j++) {
//                singleItem.add(new EventsNamesModel("Event :Pay Integrtaed Service Tax", "Date :12-5-2019"
//                        ,"Type:Tax ","Category:tax"));
//            }
//
//            dm.setAllItemsInSection(singleItem);
//
//            allSampleData.add(dm);
//
//        }
//    }

