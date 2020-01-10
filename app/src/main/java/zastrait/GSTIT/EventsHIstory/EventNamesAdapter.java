package zastrait.GSTIT.EventsHIstory;

/**
 * Created by pratap.kesaboyina on 24-12-2014.
 */

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import zastrait.GSTIT.Utils.CustomTextViewNormal;
import zastrait.GSTIT.R;


public class EventNamesAdapter extends RecyclerView.Adapter<EventNamesAdapter.SingleItemRowHolder> {

    private ArrayList<EventsNamesModel> itemsList;
    private Context mContext;

    public EventNamesAdapter(Context context, ArrayList<EventsNamesModel> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.events_name_history, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        EventsNamesModel singleItem = itemsList.get(i);

        holder.tv_event.setText(singleItem.getEvent());
        holder.tvDesc.setText(singleItem.getDesc());



    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected CustomTextViewNormal tv_event,tvDesc;

        public SingleItemRowHolder(View view) {
            super(view);

            this.tv_event = view.findViewById(R.id.tvEventName);
            this.tvDesc = view.findViewById(R.id.tvDesc);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "success", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}