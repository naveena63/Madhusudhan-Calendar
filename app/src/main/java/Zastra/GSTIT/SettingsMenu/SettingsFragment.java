package Zastra.GSTIT.SettingsMenu;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import Zastra.GSTIT.LoginActivity;
import Zastra.GSTIT.R;
import Zastra.GSTIT.Utils.CustomTextViewNormal;
import Zastra.GSTIT.Utils.PrefManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {
View view;
CustomTextViewNormal about,terms,help,logout,abbrvatons;
PrefManager prefManager;
Toolbar toolbar;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_settings, container, false);
        toolbar=view.findViewById(R.id.toolbar_setting);

        toolbar.setTitle("B5 Compliance Calendar");

        about=view.findViewById(R.id.about);
        help=view.findViewById(R.id.helpDesk);
        logout=view.findViewById(R.id.logOut);
        prefManager=new PrefManager(getContext());
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), HelpActivity.class);
                startActivity(intent);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), AboutActivity.class);
                startActivity(intent);
            }
        });
        terms=view.findViewById(R.id.terms_conditions);
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), TermsAndCondtionsActivity.class);
                startActivity(intent);
            }
        });
        abbrvatons=view.findViewById(R.id.abbreviation);
        abbrvatons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), AbbreviationsActivity.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               prefManager.logout();
               Intent intent = new Intent(getContext(), LoginActivity.class);
               startActivity(intent);
            }
        });
        return view;
    }

}
