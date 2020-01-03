package Zastra.GSTIT.Calendar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import Zastra.GSTIT.Form.UserFormADapter;
import Zastra.GSTIT.Form.UserFormModel;
import Zastra.GSTIT.Utils.AppConstants;
import Zastra.GSTIT.Utils.PrefManager;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Zastra.GSTIT.Utils.CustomTextViewNormal;
import Zastra.GSTIT.FormActivity;
import Zastra.GSTIT.R;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ItemRowHolder> {
    private RecyclerViewListener mListener;
    public ArrayList<DateModel> dataList;
    int type;
    public static CustomTextViewNormal date;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String PREFERENCE = "AGENT";
    String logintype;
    PrefManager prefManager;
    private Context mContext;
    String output = "";
    RequestQueue requestQueue;
    ArrayList<UserFormModel> userFormModelArrayList;
    UserFormModel userFormModel;
    UserFormADapter userFormADapter;
    TextView no_packages_available;
    LinearLayout parentlayout;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DateAdapter(Context context, ArrayList<DateModel> dataList, int type,RecyclerViewListener  listener) {
        this.dataList = dataList;
        this.type = type;
        this.mContext = context;
        mListener=listener;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.calendar_item, null);
        ItemRowHolder mh = new ItemRowHolder(v,mListener);
        prefManager = new PrefManager(mContext);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, final int i) {

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        final String output = df.format(c);
        System.out.println("dategggggggg" + output);
        final String calendarDate = dataList.get(i).getCalendarDate();
        String upToNCharacters = calendarDate.substring(0, Math.min(calendarDate.length(), 2));
        System.out.println("upToNCharacters" + upToNCharacters);
        prefManager.storeValue(AppConstants.EEVENTDATE, upToNCharacters);
        prefManager.setEventdate(upToNCharacters);

        Log.i("hnsvd", "jklasdh" + prefManager.getEventdate());

        final ArrayList singleSectionItems = dataList.get(i).getAllItemsInSection();
        date.setText(upToNCharacters);
        Log.e("type", String.valueOf(type));

        if (singleSectionItems.size() > 0) {
            date.setBackgroundResource(R.drawable.button_background);
            date.setTextColor(Color.BLACK);
        }

        parentlayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                if (type == 2) {
                    Log.e("type", String.valueOf(type));
                    sharedpreferences = mContext.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
                    editor = sharedpreferences.edit();
                    logintype = sharedpreferences.getString("login_type", "");
                    if (logintype.equalsIgnoreCase("1")) {

                        if (singleSectionItems.size() > 0)
                        {
                            final Dialog dialog = new Dialog(mContext);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.activity_user_form);
                            requestQueue = Volley.newRequestQueue(mContext);
                            final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_view_list);
                            TextView eventDate = (TextView) dialog.findViewById(R.id.eventDate);
                            no_packages_available = dialog.findViewById(R.id.no_packages_available);
                            requestQueue = Volley.newRequestQueue(mContext);
                            eventDate.setText(calendarDate);

                            recyclerView.setLayoutManager(new LinearLayoutManager(mContext,
                                    LinearLayoutManager.VERTICAL, false));

                            String url_formation = AppConstants.BASE_URL + AppConstants.GETCALENDER + "date=" + calendarDate + "&type=day";
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_formation, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("response", "response" + response);

                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String status = jsonObject.getString("status");

                                        if (status.equalsIgnoreCase("true")) {
                                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                            String monthname = jsonObject1.getString("monthname");
                                            String year = jsonObject1.getString("year");
                                            JSONArray jsonArray = jsonObject1.getJSONArray("data");

                                            for (int i = 0; i < jsonArray.length(); i++) {

                                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                                String eventDtae = jsonObject2.getString("event_date");
                                                JSONArray jsonArray1 = jsonObject2.getJSONArray("event_names");
                                                userFormModelArrayList = new ArrayList<>();
                                                for (int j = 0; j < jsonArray1.length(); j++) {

                                                    JSONObject jsonObject3 = jsonArray1.getJSONObject(j);
                                                    String evenname = jsonObject3.getString("event_name");
                                                    String eventDesc = jsonObject3.getString("event_description");

                                                    userFormModel = new UserFormModel();
                                                    userFormModel.setEventName(evenname);
                                                    userFormModel.setEventDesc(eventDesc);
                                                    userFormModelArrayList.add(userFormModel);
                                                }

                                            }
                                            if (userFormModelArrayList.size() > 0) {
                                                userFormADapter = new UserFormADapter(mContext, userFormModelArrayList);
                                                userFormADapter.notifyDataSetChanged();
                                                recyclerView.setAdapter(userFormADapter);
                                                no_packages_available.setVisibility(View.GONE);
                                            } else {
                                                no_packages_available.setText("No events in this date");
                                                no_packages_available.setVisibility(View.VISIBLE);

                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(mContext, "Couldn't Find Network", Toast.LENGTH_LONG).show();

                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> param = new HashMap<>();
                                    return param;
                                }
                            };
                            requestQueue.add(stringRequest);
                            Button dialogButton = (Button) dialog.findViewById(R.id.btnClose);
                            dialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                            date.setBackgroundResource(R.drawable.button_background);
                            date.setTextColor(Color.BLACK);
                        } else {
                            Intent intent = new Intent(mContext, FormActivity.class);
                            intent.putExtra("response_date", calendarDate);
                            mContext.startActivity(intent);
                        }

                    }

                    if (logintype.equalsIgnoreCase("2")) {

                        final Dialog dialog = new Dialog(mContext);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.activity_user_form);
                        requestQueue = Volley.newRequestQueue(mContext);
                        final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_view_list);
                        TextView eventDate = (TextView) dialog.findViewById(R.id.eventDate);
                        no_packages_available = dialog.findViewById(R.id.no_packages_available);
                        requestQueue = Volley.newRequestQueue(mContext);
                        eventDate.setText(calendarDate);

                        recyclerView.setLayoutManager(new LinearLayoutManager(mContext,
                                LinearLayoutManager.VERTICAL, false));

                        String url_formation = AppConstants.BASE_URL + AppConstants.GETCALENDER + "date=" + calendarDate + "&type=day";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_formation, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("response", "response" + response);

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String status = jsonObject.getString("status");

                                    if (status.equalsIgnoreCase("true")) {
                                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                        String monthname = jsonObject1.getString("monthname");
                                        String year = jsonObject1.getString("year");
                                        JSONArray jsonArray = jsonObject1.getJSONArray("data");

                                        for (int i = 0; i < jsonArray.length(); i++) {

                                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                            String eventDtae = jsonObject2.getString("event_date");
                                            JSONArray jsonArray1 = jsonObject2.getJSONArray("event_names");
                                            userFormModelArrayList = new ArrayList<>();
                                            for (int j = 0; j < jsonArray1.length(); j++) {

                                                JSONObject jsonObject3 = jsonArray1.getJSONObject(j);
                                                String evenname = jsonObject3.getString("event_name");
                                                String eventDesc = jsonObject3.getString("event_description");

                                                userFormModel = new UserFormModel();
                                                userFormModel.setEventName(evenname);
                                               userFormModel.setEventDesc(eventDesc);
                                                userFormModelArrayList.add(userFormModel);
                                            }

                                        }
                                        if (userFormModelArrayList.size() > 0) {
                                            userFormADapter = new UserFormADapter(mContext, userFormModelArrayList);
                                            userFormADapter.notifyDataSetChanged();
                                            recyclerView.setAdapter(userFormADapter);
                                            no_packages_available.setVisibility(View.GONE);
                                        } else {
                                            no_packages_available.setText("No events in this date");
                                            no_packages_available.setVisibility(View.VISIBLE);

                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(mContext, "Couldn't Find Network", Toast.LENGTH_LONG).show();

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> param = new HashMap<>();
                                return param;
                            }
                        };
                        requestQueue.add(stringRequest);
                        ImageView dialogButton = (ImageView) dialog.findViewById(R.id.btnClose);
                        dialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        date.setBackgroundResource(R.drawable.button_background);
                        date.setTextColor(Color.BLACK);
                    }


//                        Intent intent = new Intent(mContext, UserFormActivity.class);
//                        intent.putExtra("response_date", calendarDate);
//                        mContext.startActivity(intent);
                }
                if (type == 1) {
                    Log.e("type", String.valueOf(type));
                    Intent intent = new Intent(mContext, MyCalendarFormActivity.class);
                    intent.putExtra("response_date", calendarDate);
                    mContext.startActivity(intent);
                }
            }
        });
        itemRowHolder.recycler_view_list.setHasFixedSize(true);
        itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext));
        itemRowHolder.recycler_view_list.setNestedScrollingEnabled(false);
        RecyclerViewListener listener = (view, position) -> {
            Toast.makeText(mContext, "Position " + position, Toast.LENGTH_SHORT).show();
        };
        itemRowHolder.recycler_view_list.setFocusable(false);
        EventAdapter itemListDataAdapter = new EventAdapter(mContext, singleSectionItems,listener);
        itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private RecyclerViewListener mListener;
        protected RecyclerView recycler_view_list;

        public ItemRowHolder(View view,RecyclerViewListener listener) {
            super(view);
            date = view.findViewById(R.id.calendarDate);
            parentlayout=view.findViewById(R.id.parentLayout);
            mListener = listener;
            view.setOnClickListener(this);
            recycler_view_list = (RecyclerView) view.findViewById(R.id.eventRecyclerView);





    }

        @Override
        public void onClick(View v) {
            mListener.onClick(v, getAdapterPosition());
        }
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}


