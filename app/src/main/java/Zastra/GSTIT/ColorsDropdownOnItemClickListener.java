package Zastra.GSTIT;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.TextView;

public class ColorsDropdownOnItemClickListener implements AdapterView.OnItemClickListener {

    String TAG = "ColorsDropdownOnItemClickListener.java";

    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {

        Context mContext = v.getContext();

        FormActivity mainActivity = ((FormActivity) mContext);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(v.getContext(), android.R.anim.fade_in);
        fadeInAnimation.setDuration(10);
        v.startAnimation(fadeInAnimation);
        mainActivity.popupWindowColors.dismiss();
        String selectedItemText = ((TextView) v).getText().toString();
        mainActivity.buttonShowDropDown.setText(selectedItemText);
        String selectedItemTag = ((TextView) v).getTag().toString();
      //  Toast.makeText(mContext, "Color ID is: " + selectedItemTag, Toast.LENGTH_SHORT).show();
        if(selectedItemTag.equals("#0000FF")){
          String blue="#0000ff";
          Log.e("blue","blue"+blue);

        }
        if(selectedItemTag.equals("#FF0000")){
            String red="#ff0000";
            Log.e("red","red"+red);
        }
        if(selectedItemTag.equals("#00FF00")){
            String green="#00ff00";

            Log.e("green","green"+green);
        }
        if(selectedItemTag.equals("#ffa500")){
            String orange="#FFA500";
            Log.e("orange","orange"+orange);

        }
        if(selectedItemTag.equals("#9B870C")){
            String yellow="#C2AA1F";
            Log.e("yellow","yellow"+yellow);

        } if(selectedItemTag.equals("#006699")){
            String navBlue="#006699";
            Log.e("navBlue","navBlue"+navBlue);

        }

    }

}