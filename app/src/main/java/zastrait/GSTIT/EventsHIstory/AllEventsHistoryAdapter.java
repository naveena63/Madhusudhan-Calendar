package zastrait.GSTIT.EventsHIstory;

/**
 * Created by pratap.kesaboyina on 24-12-2014.
 */

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import zastrait.GSTIT.Utils.CustomTextViewNormal;
import zastrait.GSTIT.R;

public class AllEventsHistoryAdapter extends RecyclerView.Adapter<AllEventsHistoryAdapter.ItemRowHolder> {

    private ArrayList<AllEventHistoryModel> dataList;
    private Context mContext;

    public AllEventsHistoryAdapter(Context context, ArrayList<AllEventHistoryModel> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {

        final String sectionName = dataList.get(i).getHeaderTitle();
     //   final String totalcount = dataList.get(i).getTotalcount();

        ArrayList singleSectionItems = dataList.get(i).getAllItemsInSection();

        itemRowHolder.itemTitle.setText(sectionName);
       // itemRowHolder.totalcount.setText(totalcount);

        EventNamesAdapter itemListDataAdapter = new EventNamesAdapter(mContext, singleSectionItems);
        itemRowHolder.recycler_view_list.setHasFixedSize(true);
        itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);
         itemRowHolder.recycler_view_list.setNestedScrollingEnabled(false);
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected CustomTextViewNormal itemTitle;
        protected RecyclerView recycler_view_list;


        public ItemRowHolder(View view) {
            super(view);
            this.itemTitle =  view.findViewById(R.id.date);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);

        }
    }
}