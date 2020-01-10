package zastrait.GSTIT;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


import zastrait.GSTIT.Utils.AppConstants;
import zastrait.GSTIT.Utils.CustomTextViewNormal;
import zastrait.GSTIT.Utils.PrefManager;

public class LoginActivity extends AppCompatActivity {
    EditText mobile, loginPaswrd;
    Button buttonLogin;
    CustomTextViewNormal createNewAccount;

    PrefManager prefManager;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String PREFERENCE = "AGENT";
    ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getSupportActionBar().hide();
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/segoeui.ttf");
 progressBar=new ProgressDialog(this);
        mobile = findViewById(R.id.loginMobile);
        loginPaswrd = findViewById(R.id.loginPaswrd);
        buttonLogin = findViewById(R.id.buttonLogin);
        createNewAccount = findViewById(R.id.createNewAccount);
        mobile.setTypeface(typeface);
        loginPaswrd.setTypeface(typeface);
        buttonLogin.setTypeface(typeface);
        sharedpreferences = this.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        prefManager = new PrefManager(this);
        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int verify = validate();
                if (verify == 0) {

                    loginIn();
                }
            }
        });
    }




public  void loginIn(){
        final String phone = mobile.getText().toString();
        final String paswrd = loginPaswrd.getText().toString();
  progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.BASE_URL + AppConstants.LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressBar.dismiss();

                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    if (status.equals("true")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        String user_role_id = jsonObject1.getString("user_role_id");
                        String name = jsonObject1.getString("user_name");
                        String email = jsonObject1.getString("email");
                        String mobile = jsonObject1.getString("phone_number");
                        String user_id = jsonObject1.getString("id_user");
                         prefManager.storeValue(AppConstants.USER_ID,user_id);
                        prefManager.setUserid(user_id);
                        Log.i("userid","userid"+prefManager.getUserid());


                        String login_type = jsonObject1.getString("user_role_id");

                        editor.putString("login_type", login_type);
                        editor.commit();

                        prefManager.storeValue(AppConstants.APP_USER_LOGIN, true);
                        Log.e("b5", sharedpreferences.getString("login_type", ""));
                        //admin
                        if (login_type.equalsIgnoreCase("1")) {

                            Intent intent = new Intent(LoginActivity.this, BottomNavigtnActivity.class);
                            startActivity(intent);
                        }
                        //user
                        else if (login_type.equalsIgnoreCase("2")) {
                            Intent intent = new Intent(LoginActivity.this, BottomNavigtnActivity.class);
                            startActivity(intent);
                        }
                    } else if (status.equalsIgnoreCase("false")) {

                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("Admin_login", "_-------------Admin Login Response----------------" + response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressBar.dismiss();

                        Toast.makeText(LoginActivity.this, "No network ", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("phone_number", phone);
                map.put("password", paswrd);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private int validate() {
        int flag = 0;
        if (mobile.getText().toString().length() != 10) {
            mobile.setError("Phone Number required 10 digits only");
            mobile.requestFocus();
            flag = 1;
        }
//        else if (loginPaswrd.getText().toString().length() <8) {
//            loginPaswrd.setError("Password atleast 8 characters long");
//            loginPaswrd.requestFocus();
//            flag = 1;
//        }

        return flag;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                }).create().show();
    }
}
