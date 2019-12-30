package Zastra.GSTIT.Form;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

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

import Zastra.GSTIT.BottomNavigtnActivity;
import Zastra.GSTIT.R;
import Zastra.GSTIT.Utils.AppConstants;
import Zastra.GSTIT.Utils.CustomTextViewNormal;
import Zastra.GSTIT.Utils.PrefManager;

public class UserFormActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    CustomTextViewNormal eventDate;
    PrefManager prefManager;
    ArrayList<UserFormModel> userFormModelArrayList;
    UserFormModel userFormModel;
    UserFormADapter userFormADapter;
    RecyclerView recyclerView;
    String response_date;
    Button close;
    Toolbar toolbar;
    TextView no_packages_available;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form);

//        toolbar=findViewById(R.id.toolbar);
//        toolbar.setTitle("Event Details");
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        requestQueue = Volley.newRequestQueue(this);
        eventDate = findViewById(R.id.eventDate);
        no_packages_available = findViewById(R.id.no_packages_available);
        close=findViewById(R.id.btnClose);
        prefManager = new PrefManager(this);
        recyclerView=findViewById(R.id.recycler_view_list);
        response_date = getIntent().getStringExtra("response_date");
        eventDate.setText(response_date);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserFormActivity.this,
                LinearLayoutManager.VERTICAL, false));

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        output = df.format(c);
        System.out.println("outpudffrjickicyt"+output);
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        output = sdf1.format(c.getTime());
        System.out.println("output" + output);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserFormActivity.this, BottomNavigtnActivity.class);
                startActivity(intent);

            }
        });
        getUserData();
    }

    String output = "";

    private void getUserData() {
        String url_formation = AppConstants.BASE_URL + AppConstants.GETCALENDER + "date="+response_date + "&type=day";

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
                            eventDate.setText(eventDtae);

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
                            userFormADapter = new UserFormADapter(UserFormActivity.this, userFormModelArrayList);
                            userFormADapter.notifyDataSetChanged();
                            recyclerView.setAdapter(userFormADapter);
                            no_packages_available.setVisibility(View.GONE);
                        } else {
                            no_packages_available.setText("No events in this date");
                            no_packages_available.setVisibility(View.VISIBLE);

                        }

                    }
//                        userFormADapter = new UserFormADapter(UserFormActivity.this, userFormModelArrayList);
//                        userFormADapter.notifyDataSetChanged();
//                        recyclerView.setAdapter(userFormADapter);
//                        userFormADapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserFormActivity.this, "Couldn't Find Network", Toast.LENGTH_LONG).show();

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
    public void onBackPressed() {
        finish();
    }
}
