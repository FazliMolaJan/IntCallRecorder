package com.adityagupta.intern2.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.adityagupta.intern2.R;
import com.adityagupta.intern2.utils.Preferences;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

public class MainRecordingActivity extends AppCompatActivity {

    Switch callRecord;
    CardView whitelist, background, accessibility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recording);
        setStatusBarGradiant(this);

        callRecord = findViewById(R.id.record_call);
        whitelist = findViewById(R.id.card_id_white);
        accessibility = findViewById(R.id.card_id_acc);
        background = findViewById(R.id.card_id_backg);

        Preferences.prefs = getApplicationContext().getSharedPreferences("call_info", MODE_PRIVATE);

        callRecord.setChecked(Preferences.prefs.getBoolean("call_record", false));
        callRecord.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Preferences.prefs.edit().putBoolean("call_record", b).apply();
            }
        });

        whitelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(MainRecordingActivity.this).title("WhiteList App")
                        .content(
                                "Please Check Call Recorder in the coming screen.\n\nHave Fun.\nIf you have already done this click Cancel.")
                        .positiveText("Ok")
                        .theme(Theme.LIGHT)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                try {
                                    Intent intent = new Intent();
                                    intent.setComponent(new ComponentName("com.iqoo.secure",
                                            "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity"));
                                    startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(MainRecordingActivity.this, "Error in WhiteListCard", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .negativeText("Cancel")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(MainRecordingActivity.this).title("Enable AutoStart")
                        .content(
                                "Goto \n1. App Manager\n2.Autostart manager\n3.Check Call Recorder\n\nHave Fun.\nIf you have already done this click Cancel.")
                        .positiveText("Ok")
                        .theme(Theme.LIGHT)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                try {
                                    Intent intent = new Intent();
                                    intent.setComponent(new ComponentName("com.iqoo.secure",
                                            "com.iqoo.secure.MainActivity"));
                                    startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(MainRecordingActivity.this, "Error in BackgroundCard", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .negativeText("Cancel")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.gradient_3);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }
}