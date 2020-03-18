package zastrait.GSTIT;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zastrait.GSTIT.Utils.AppConstants;
import zastrait.GSTIT.Utils.CustomTextViewNormal;
import zastrait.GSTIT.Utils.PrefManager;

public class FormActivity extends AppCompatActivity {
    EditText eteventName, etDescrption;
    CustomTextViewNormal tveventDate, buttonShowDropDown;

    Button btnSave, btnClose;
    PrefManager prefManager;
    String popUpContents[];
    PopupWindow popupWindowColors;
    String response_date;
    String col;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        prefManager = new PrefManager(this);
        response_date = getIntent().getStringExtra("response_date");
        System.out.println("response_date" + response_date);
        buttonShowDropDown = findViewById(R.id.tvThemeColor);
        eteventName = findViewById(R.id.etSubject);
        etDescrption = findViewById(R.id.etDescrption);
        tveventDate = findViewById(R.id.tvStartEvent);
        tveventDate.setText(response_date);
        Log.i("event", "event" + prefManager.getEventname());
        btnSave = findViewById(R.id.btnSave);
        btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormActivity.this, BottomNavigtnActivity.class);
                startActivity(intent);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int verify = validate();
                if (verify == 0) {
                    getForm();
                }
            }
        });
        List<String> colorsList = new ArrayList<String>();
        colorsList.add("blue");
        colorsList.add("red");
        colorsList.add("green");
        colorsList.add("orange");
        colorsList.add("yellow");
        colorsList.add("navBlue");

        popUpContents = new String[colorsList.size()];
        colorsList.toArray(popUpContents);
        popupWindowColors = popupWindowColors();
        View.OnClickListener handler = new View.OnClickListener() {
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.tvThemeColor:
                        popupWindowColors.showAsDropDown(v, -5, 0);
                        break;
                }
            }
        };
        buttonShowDropDown.setOnClickListener(handler);

//        String color = buttonShowDropDown.getText().toString();
//        Log.i("charanevent","colr"+color);
//        if (color.equals("blue")) {
//            col="#0000FF";
//        }
//        if (color.equals("red")) {
//             col="#FF0000";
//        }
//        if (color.equals("green")) {
//             col="#00FF00";
//        }
//        if (color.equals("orange")) {
//             col="#FFA500";
//        }
//        if (color.equals("yellow")) {
//             col="#9B870C";
//        }
//            Log.i("colorcode","col"+col);
//    }
    }
    public PopupWindow popupWindowColors()
    {
        PopupWindow popupWindow = new PopupWindow(this);
        ListView listViewDogs = new ListView(this);
        listViewDogs.setAdapter(dogsAdapter(popUpContents));
        listViewDogs.setOnItemClickListener(new ColorsDropdownOnItemClickListener());
        popupWindow.setFocusable(true);
        popupWindow.setWidth(1000);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(listViewDogs);
        return popupWindow;
    }

    private ArrayAdapter<String> dogsAdapter(String dogsArray[])
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dogsArray) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                String item = getItem(position);
                String[] itemArr = item.split("::");
                String text = itemArr[0];
                String id = itemArr[0];
                TextView listItem = new TextView(FormActivity.this);
                listItem.setText(text);
                listItem.setTag(id);
                listItem.setTextSize(15);
                listItem.setPadding(10, 10, 10, 10);
                listItem.setTextColor(Color.BLACK);
                listItem.setBackgroundColor(Color.WHITE);
//                switch (position) {
//                    case 0:
//                        eteventName.setTextColor(Color.BLUE);
//                        break;
//                    case 1:
//                        eteventName.setTextColor(Color.YELLOW);
//                        break;
//                    case 2:
//                        eteventName.setTextColor(Color.RED);
//                        break;
//                    case 3:
//                        eteventName.setTextColor(Color.GREEN);
//                        break;
//                }
                return listItem;
            }
        };
        return adapter;
    }
    private void getForm() {

        final String eventName = eteventName.getText().toString();
        final String eventDate = tveventDate.getText().toString();
        final String eventDesc = etDescrption.getText().toString();

        String color = buttonShowDropDown.getText().toString();
        Log.i("event","colr"+color);
        if (color.equals("blue")) {
            col="#0000FF";
        }
        if (color.equals("red")) {
            col="#FF0000";
        }
        if (color.equals("green")) {
            col="#00FF00";
        }
        if (color.equals("orange")) {
            col="#FFA500";
        }
        if (color.equals("yellow")) {
            col="#9B870C";
        }
        if (color.equals("navBlue")) {
            col="#006699";
        }
        Log.i("colorcode","col"+col);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.BASE_URL + AppConstants.FORM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            Log.i("Form Response", "register" + response);
                            String status = object.getString("status");
                            String message = object.getString("message");

                            if (status.equals("true")) {

                                Toast.makeText(FormActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(FormActivity.this, BottomNavigtnActivity.class);
                                startActivity(intent);

                            } else if (status.equals("false")) {
                                Toast.makeText(FormActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i("Registration", "_--------------Registration Response----------------" + response);
                        // Toast.makeText(RegistrationActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FormActivity.this,"Couldnt find Network", Toast.LENGTH_LONG).show();
                        Log.i("An", "_-------------Error--------------------" + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
              //  map.put("event_name", eventName);
                map.put("event_short", eventName);
                map.put("event_date", response_date);
                Log.i("date", "date" + eventDate);
                map.put("event_description", eventDesc);
                map.put("color_code",col);
                Log.e("colorcode","colorcode"+eventName+response_date+eventDate+eventDesc+col);


                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private int validate() {
        int flag = 0;
        if (eteventName.getText().toString().length() == 0) {
            eteventName.setError("Enter Subject");
            eteventName.requestFocus();
            flag = 1;
        } else if (tveventDate.getText().toString().length() == 0) {
            tveventDate.setError("Select Start Event");
            tveventDate.requestFocus();
            flag = 1;
        } else if (etDescrption.getText().toString().length() == 0) {
            etDescrption.setError("Enter Description");
            etDescrption.requestFocus();
            flag = 1;
        } else if (buttonShowDropDown.getText().toString().length() == 0) {
            buttonShowDropDown.setError("Select Theme Color");
            buttonShowDropDown.requestFocus();
            flag = 1;
        }
        return flag;
    }

}
