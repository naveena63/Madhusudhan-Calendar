package zastrait.GSTIT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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


public class RegistrationActivity extends AppCompatActivity {
    EditText userName, phoneNumber, email, password, cnfrmPaswrd;
    CustomTextViewNormal goToLogin, dropDownState;
    Button buttonRegister;
    String popUpContents[];
    PopupWindow popupWindowColors;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        // getSupportActionBar().hide();
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Raleway-Medium.ttf");
        userName = findViewById(R.id.userName);
        phoneNumber = findViewById(R.id.phoneNumber);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        cnfrmPaswrd = findViewById(R.id.cnfrmPaswrd);
        buttonRegister = findViewById(R.id.buttonRegister);
        goToLogin = findViewById(R.id.goToLogin);
        dropDownState = findViewById(R.id.dropDownState);
        prefManager=new PrefManager(this);
        userName.setTypeface(typeface);
        phoneNumber.setTypeface(typeface);
        email.setTypeface(typeface);
        password.setTypeface(typeface);
        cnfrmPaswrd.setTypeface(typeface);

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int verify = validate();
                if (verify == 0) {

                    signUp();
                }

            }
        });


        List<String> stateList = new ArrayList<String>();
        stateList.add("Andra Pradesh");
        stateList.add("Telagana");
        stateList.add("Tamilnadu");
        stateList.add("Kerala");

        // convert to simple array
        popUpContents = new String[stateList.size()];
        stateList.toArray(popUpContents);


        // initialize pop up window
        popupWindowColors = popupWindowColors();


        // button on click listener

        View.OnClickListener handler = new View.OnClickListener() {
            public void onClick(View v) {

                switch (v.getId()) {

                    case R.id.dropDownState:
                        // show the list view as dropdown
                        popupWindowColors.showAsDropDown(v, -5, 0);
                        break;
                }
            }
        };

        // our button

        dropDownState.setOnClickListener(handler);
    }


    private void signUp() {

        final String name = userName.getText().toString();
        final String emaill = email.getText().toString();
        final String pasword = password.getText().toString();
        final String cpasword = cnfrmPaswrd.getText().toString();
        final String phone = phoneNumber.getText().toString();
        final String state = dropDownState.getText().toString();
        //AppConstants.BASE_URL+AppConstants.SIGNUP http://13.233.160.145/calendar/rest/index.php/Signup/postuser
        StringRequest stringRequest = new StringRequest(Request.Method.POST,AppConstants.BASE_URL+AppConstants.SIGNUP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            Log.i("register Response", "register" + response);
                             String status = object.getString("status");
                             String message = object.getString("message");

                            if (status.equals("true")) {
                                Toast.makeText(RegistrationActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else if (status.equals("false")) {
                                Toast.makeText(RegistrationActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i("Registration", "_--------------Registration Response----------------" + response);
                     //   Toast.makeText(RegistrationActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(RegistrationActivity.this, "Coudlnt find network", Toast.LENGTH_LONG).show();
                        Log.i("An", "_-------------Error--------------------" + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("user_name", name);
                map.put("password", pasword);
                map.put("cpassword", cpasword);
                map.put("email", emaill);
                map.put("phone_number", phone);
                map.put("state", state);
                map.put("device_id",prefManager.getToken());
                Log.i("tokn","tokn"+prefManager.getToken());

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    public PopupWindow popupWindowColors() {

        // initialize a pop up window type
        PopupWindow popupWindow = new PopupWindow(this);

        // the drop down list is a list view
        ListView listViewDogs = new ListView(this);

        // set our adapter and pass our pop up window contents
        listViewDogs.setAdapter(dogsAdapter(popUpContents));

        // set the item click listener
        listViewDogs.setOnItemClickListener(new StateDropDownOnItemClickListener());

        // some other visual settings
        popupWindow.setFocusable(true);
        popupWindow.setWidth(900);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // set the list view as pop up window content
        popupWindow.setContentView(listViewDogs);

        return popupWindow;
    }

    /*
     * adapter where the list values will be set
     */
    private ArrayAdapter<String> dogsAdapter(String dogsArray[]) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dogsArray) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                String item = getItem(position);
                String[] itemArr = item.split("::");
                String text = itemArr[0];
                String id = itemArr[0];

                TextView listItem = new TextView(RegistrationActivity.this);
                listItem.setText(text);
                listItem.setTag(id);
                listItem.setTextSize(15);
                listItem.setPadding(10, 10, 10, 10);
                listItem.setTextColor(Color.BLACK);
                listItem.setBackgroundColor(Color.WHITE);


                return listItem;
            }
        };

        return adapter;
    }


    private int validate() {
        int flag = 0;

        if (userName.getText().toString().length() == 0) {
            userName.setError("Username is Required");
            userName.requestFocus();
            flag = 1;
        } else if (phoneNumber.getText().toString().length() == 0) {
            phoneNumber.setError("PhoneNumber is Required");
            phoneNumber.requestFocus();
            flag = 1;
        } else if (email.getText().toString().length() == 0) {
            email.setError("Email not entered");
            email.requestFocus();
            flag = 1;
        } else if (password.getText().toString().length() == 0) {
            password.setError("Password not entered");
            password.requestFocus();
            flag = 1;
        } else if (cnfrmPaswrd.getText().toString().length() == 0) {
            cnfrmPaswrd.setError("Please confirm password");
            cnfrmPaswrd.requestFocus();
            flag = 1;
        } else if (!password.getText().toString().equals(cnfrmPaswrd.getText().toString())) {
            cnfrmPaswrd.setError("Password Not matched");
            cnfrmPaswrd.requestFocus();
            flag = 1;
        } else if (dropDownState.getText().toString().length() == 0) {
            dropDownState.setError("Select City");
            dropDownState.requestFocus();
            flag = 1;
        }
        return flag;

    }
}