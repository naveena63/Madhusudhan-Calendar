package zastrait.GSTIT.Calendar;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import zastrait.GSTIT.R;
import zastrait.GSTIT.Utils.AppConstants;
import zastrait.GSTIT.Utils.PrefManager;

public class CalendarFragment extends Fragment implements RecyclerViewListener {

    ArrayList<DateModel> allSampleData;
    ArrayList<EventModel> eventModelList;
    RecyclerView my_recycler_view;
    RequestQueue requestQueue;
    DateAdapter dateadapter;
    EventAdapter eventADapter;
    ImageButton Ib_next, ib_prev;
    String inputdate, inputmnth;
    int inputyear;
    String output = "";
    String prev_button, next_button;
    int type = 2;
    TextView tv_month, tv_year;
    PrefManager prefManager;
    View view;
    ProgressDialog progressBar;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main, container, false);
        requestQueue = Volley.newRequestQueue(getActivity());
        prefManager = new PrefManager(getContext());

        //declartn
        my_recycler_view = view.findViewById(R.id.recyclerView);
        Ib_next = view.findViewById(R.id.Ib_next);
        tv_month = view.findViewById(R.id.tv_month);
        ib_prev = view.findViewById(R.id.ib_prev);
        tv_year = view.findViewById(R.id.tv_year);
        progressBar = new ProgressDialog(getContext());

        //model list
        allSampleData = new ArrayList<>();
        eventModelList = new ArrayList<>();

        //get Present Date
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        output = df.format(c);
        System.out.println("outpudffrjickicyt" + output);
        getData(output, true);

        //setting recyclerview view
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 7);
        my_recycler_view.setLayoutManager(layoutManager);

        my_recycler_view.setHasFixedSize(true);
        RecyclerViewListener listener = (view, position) -> {
            // Toast.makeText(getContext(), "Position " + position, Toast.LENGTH_SHORT).show();
        };
        dateadapter = new DateAdapter(getContext(), allSampleData, type, listener);
        eventADapter = new EventAdapter(getContext(), eventModelList, listener);
        Ib_next.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                //   clearData();
                allSampleData.clear();
                eventModelList.clear();

//                String dt = "2012-01-04";  // Start date
//
//                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//                Calendar c = Calendar.getInstance();
//                try {
//
//                        c.setTime(sdf.parse(next_button));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
////                output = sdf1.format(c.getTime());
////                System.out.println("output" + output);
//
////                c.add(Calendar.MONTH, 1);
//                int next_month = c.get(Calendar.MONTH) + 1;
//                c.add(Calendar.MONTH, next_month);
//                output = sdf1.format(c.getTime());
//                System.out.println("output" + output);
//                getData(output, true);
                String dt = "2012-01-04";  // Start date
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(sdf.parse(next_button));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                output = sdf1.format(c.getTime());
                System.out.println("output" + output);
                getData(inputdate, false);
            }
        });
        ib_prev.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                allSampleData.clear();
                eventModelList.clear();

                String dt = "2012-01-04";  // Start date
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(sdf.parse(prev_button));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                output = sdf1.format(c.getTime());
                System.out.println("output" + output);
                getData(inputdate, false);
            }
        });
        return view;
    }

    private void getData(String date, boolean nextMonth) {

        String url_formation = AppConstants.BASE_URL + AppConstants.GETCALENDER + "date=" + output + "&type=month";
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_formation, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("response", "response" + response);
                Calendar c = Calendar.getInstance();
                try {
                    progressBar.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    inputyear = Integer.parseInt(jsonObject1.optString("year"));
                    inputmnth = jsonObject1.getString("monthname");
                    prev_button = jsonObject1.getString("prev");
                    next_button = jsonObject1.getString("next");
                    tv_month.setText(inputmnth);
                    tv_year.setText(String.valueOf(inputyear));
                    Log.i("response", "response" + response);

                    if (status.equalsIgnoreCase("true")) {
                        JSONArray jsonArray = jsonObject1.getJSONArray("data");
                        JSONObject json2 = jsonArray.getJSONObject(10);
                        inputdate = json2.getString("event_date");
                        System.out.println("inputdate" + inputdate);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject json = jsonArray.getJSONObject(i);
                            DateModel dateModel = new DateModel();
                            String date = json.getString("event_date");
                            inputdate = date;
                            dateModel.setCalendarDate(inputdate);
                            JSONArray jsonArray1 = json.getJSONArray("event_names");
                            eventModelList = new ArrayList<>();
                            for (int j = 0; j < jsonArray1.length(); j++) {
                                JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
                                String eventName = jsonObject2.getString("event_short");
                                String eventColor = jsonObject2.getString("color_code");
                                Log.e("color", eventColor);
                                eventModelList.add(new EventModel(eventName, eventColor));
                            }
                            dateModel.setAllItemsInSection(eventModelList);
                            allSampleData.add(dateModel);

                        }

                        if (nextMonth) {
                            if (inputmnth.equalsIgnoreCase("Jan")) {
                                c.add(Calendar.DATE, 31);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

                            } else if (inputmnth.equalsIgnoreCase("Feb")) {

                                if ((inputyear % 400 == 0) || (inputyear % 100 == 0) || (inputyear % 4 == 0)) {
                                    c.add(Calendar.DATE, 29);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

                                } else {
                                    c.add(Calendar.DATE, 29);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
                                }

                            } else if (inputmnth.equalsIgnoreCase("Mar")) {
                                c.add(Calendar.DATE, 31);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

                            } else if (inputmnth.equalsIgnoreCase("Apr")) {
                                c.add(Calendar.DATE, 30);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

                            } else if (inputmnth.equalsIgnoreCase("May")) {
                                c.add(Calendar.DATE, 31);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

                            } else if (inputmnth.equalsIgnoreCase("Jun")) {
                                c.add(Calendar.DATE, 30);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

                            } else if (inputmnth.equalsIgnoreCase("Jul")) {
                                c.add(Calendar.DATE, 31);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

                            } else if (inputmnth.equalsIgnoreCase("Aug")) {
                                c.add(Calendar.DATE, 31);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

                            } else if (inputmnth.equalsIgnoreCase("Sep")) {
                                c.add(Calendar.DATE, 30);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

                            } else if (inputmnth.equalsIgnoreCase("Oct")) {
                                c.add(Calendar.DATE, 31);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

                            } else if (inputmnth.equalsIgnoreCase("Nov")) {
                                c.add(Calendar.DATE, 30);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

                            } else if (inputmnth.equalsIgnoreCase("Dec")) {
                                c.add(Calendar.DATE, 31);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

                            }
                        } else {
                            if (inputmnth.equalsIgnoreCase("Jan")) {
                                c.add(Calendar.DATE, -31);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

                            } else if (inputmnth.equalsIgnoreCase("Feb")) {
                                c.add(Calendar.DATE, -31);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

                                if ((inputyear % 400 == 0) || (inputyear % 100 == 0) || (inputyear % 4 == 0)) {

                                } else {
                                    c.add(Calendar.DATE, -28);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
                                }  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE


                            } else if (inputmnth.equalsIgnoreCase("Mar")) {
                                if ((inputyear % 400 == 0) || (inputyear % 100 == 0) || (inputyear % 4 == 0)) {
                                    c.add(Calendar.DATE, -29);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
                                } else {
                                    c.add(Calendar.DATE, -28);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
                                }
                            } else if (inputmnth.equalsIgnoreCase("Apr")) {
                                c.add(Calendar.DATE, -31);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

                            } else if (inputmnth.equalsIgnoreCase("May")) {
                                c.add(Calendar.DATE, -30);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

                            } else if (inputmnth.equalsIgnoreCase("Jun")) {
                                c.add(Calendar.DATE, -31);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

                            } else if (inputmnth.equalsIgnoreCase("Jul")) {
                                c.add(Calendar.DATE, -30);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

                            } else if (inputmnth.equalsIgnoreCase("Aug")) {
                                c.add(Calendar.DATE, -31);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

                            } else if (inputmnth.equalsIgnoreCase("Sep")) {
                                c.add(Calendar.DATE, -31);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

                            } else if (inputmnth.equalsIgnoreCase("Oct")) {
                                c.add(Calendar.DATE, -30);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

                            } else if (inputmnth.equalsIgnoreCase("Nov")) {
                                c.add(Calendar.DATE, -31);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

                            } else if (inputmnth.equalsIgnoreCase("Dec")) {
                                c.add(Calendar.DATE, -30);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE

                            }
                        }

                        my_recycler_view.setAdapter(dateadapter);

                        dateadapter.notifyDataSetChanged();
                        my_recycler_view.setFocusable(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View view, int position) {

    }
}