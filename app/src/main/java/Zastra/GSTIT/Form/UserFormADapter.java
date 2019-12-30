package Zastra.GSTIT.Form;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import Zastra.GSTIT.R;
import Zastra.GSTIT.Utils.CustomTextViewNormal;

public class UserFormADapter extends RecyclerView.Adapter<UserFormADapter.ViewHolder>  {

    private Context context;
    private ArrayList<UserFormModel> userFormModelArrayList;
    ViewGroup viewGroup;

    public UserFormADapter(Context userFormActivity, ArrayList<UserFormModel> userFormModels) {
        this.userFormModelArrayList = userFormModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.user_form_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        viewHolder.evntname.setText(userFormModelArrayList.get(i).getEventName());
        viewHolder.eventDesc.setText("\u2713"+userFormModelArrayList.get(i).getEventDesc());

    }
    @Override
    public int getItemCount() {
        return userFormModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CustomTextViewNormal evntname,eventDesc;
        ViewHolder(View itemView) {
            super(itemView);
            evntname=itemView.findViewById(R.id.eventNme);
            eventDesc=itemView.findViewById(R.id.eventDesc);
        }
    }
}

