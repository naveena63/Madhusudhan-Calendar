package zastrait.GSTIT.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    private static final String TOKEN = "token";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "B5";
    private static final String ADMIN_ID = "user_id";
    private static final String EVENTNAME = "user_id";
    private static final String EVENTDATE = "date";
    private static final String DEVICE_ID = "deviceid";
    private static final String userid = "userid";


    public PrefManager(Context context) {
        int PRIVATE_MODE = 0;
        pref = context.getApplicationContext().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.commit();
    }

    public void storeValue(String key, Object object) {
        if (object instanceof String) {
            editor.putString(key, object.toString());
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        }
        editor.apply();
    }
    public boolean getBoolean(String key) {
        return pref.getBoolean(key, false);
    }

    public String getAdminId() {
        return pref.getString(ADMIN_ID, "");
    }
    public void setAdminId(String adminId) {
        editor.putString(ADMIN_ID, adminId);
        editor.commit();
    }
 public String getEventname() {
        return pref.getString(EVENTNAME, "");
    }
    public void setEventname(String eventname) {
        editor.putString(EVENTNAME, "");
        editor.commit();
    }
 public String getEventdate() {
        return pref.getString(EVENTDATE, "");
    }
    public void setEventdate(String eventdate) {
        editor.putString(EVENTDATE, "");
        editor.commit();
    }

    public String getDeviceId() {
        return pref.getString(DEVICE_ID, "");
    }

    public void setDeviceId(String deviceId) {
        editor.putString(DEVICE_ID, deviceId);
        editor.commit();
    }
    public String getUserid() {
        return pref.getString(userid, "");
    }

    public void setUserid(String userid) {
        editor.putString(userid, userid);
        editor.commit();
    }

    public String getToken() {
        return pref.getString(TOKEN, "");
    }

    public void setToken(String token) {
        editor.putString(TOKEN, token);
        editor.commit();
    }
    public void logout() {
        editor = pref.edit();
        editor.clear();
        editor.apply();
    }


}
