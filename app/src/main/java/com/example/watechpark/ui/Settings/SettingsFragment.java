package com.example.watechpark.ui.Settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceFragmentCompat;

import com.example.watechpark.R;

import java.util.Locale;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;

    private Button btn2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loadLocale();
        settingsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        btn2 = root.findViewById(R.id.button2);


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLanguage();
            }
        });

        return root;
    }

    private void changeLanguage() {
        final String[] list = {"French", "English"};
        AlertDialog.Builder build = new AlertDialog.Builder(getContext());
        build.setTitle("Select Language");
                build.setSingleChoiceItems(list, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            setLocale("fr");
                            getActivity().recreate();
                        }
                        if (which == 1) {
                            setLocale("en");
                            getActivity().recreate();


                        }

                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = build.create();
        dialog.show();
    }


    private void setLocale(String choice){
            Locale locale = new Locale(choice);
            Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getContext().getResources().updateConfiguration(configuration, getContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getContext().getSharedPreferences("Settings", Context.MODE_PRIVATE).edit();
        editor.putString("lang_uage", choice);
        editor.apply();



        }

        public void loadLocale(){
        SharedPreferences pref = getContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String lang = pref.getString("lang_uage", "");
        setLocale(lang);
        }



}