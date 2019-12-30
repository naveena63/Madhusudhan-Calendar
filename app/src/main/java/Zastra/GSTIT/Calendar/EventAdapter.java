package Zastra.GSTIT.Calendar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Zastra.GSTIT.Utils.CustomTextViewNormal;
import Zastra.GSTIT.R;
import Zastra.GSTIT.Utils.PrefManager;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.SingleItemRowHolder> {

    private ArrayList<EventModel> itemsList;
    private Context mContext;
RecyclerViewListener listener;

    PrefManager prefManager;
    protected CustomTextViewNormal tv_event;

    public EventAdapter(Context context, ArrayList<EventModel> itemsList, RecyclerViewListener listener) {
        this.itemsList = itemsList;
        this.mContext = context;
this.listener=listener;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.eventname_layout, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        prefManager = new PrefManager(mContext);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {


        EventModel singleItem = itemsList.get(i);

        String event=(itemsList.get(i).getEventName());
        String upToNCharacters = event.substring(0, Math.min(event.length(), 4));

        tv_event.setText(upToNCharacters);



        String color=singleItem.getEventColor();
        Log.e("colorevt",color);
        if (color.equals("#0000FF")){
            tv_event.setBackgroundResource(R.color.Blue);

        } if (color.equals("#FF0000")){
            tv_event.setBackgroundResource(R.color.red);

        } if (color.equals("#00FF00")){
            tv_event.setBackgroundResource(R.color.green);

        } if (color.equals("#FFA500")){
            tv_event.setBackgroundResource(R.color.orange);

        } if (color.equals("#9B870C")){
            tv_event.setBackgroundResource(R.color.yellow);
        }if (color.equals("#006699")){
            tv_event.setBackgroundResource(R.color.navBlue);
        }

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        public SingleItemRowHolder(View view) {
            super(view);

            tv_event = view.findViewById(R.id.eventNme);

            tv_event.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
  });


        }
    }
}