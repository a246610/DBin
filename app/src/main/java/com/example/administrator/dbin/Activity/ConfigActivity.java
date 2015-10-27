package com.example.administrator.dbin.Activity;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.administrator.dbin.AlarmActivity.StartTimeAlarm;
import com.example.administrator.dbin.AlarmActivity.StopTimeAlarm;
import com.example.administrator.dbin.Fragment.AlertFragment;
import com.example.administrator.dbin.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ConfigActivity extends AppCompatActivity   {

    private Button btnSetTimeStart, btnSetTimeStop, btnSaveConfig;
    private EditText editFloorFirst, editFloorEnd, editIntervalTime, editMaxWeight;
    private Toolbar toolbar;

    @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_config);

                btnSetTimeStart = (Button)findViewById(R.id.btnSetTimeStart);
                btnSetTimeStop = (Button) findViewById(R.id.btnSetTimeStop);
                btnSaveConfig = (Button) findViewById(R.id.btnSaveConfig);

                editFloorFirst = (EditText) findViewById(R.id.editFloorFirst);
                editFloorEnd = (EditText) findViewById(R.id.editFloorEnd);
                editIntervalTime =(EditText) findViewById(R.id.editIntervalTime);
                editMaxWeight =(EditText) findViewById(R.id.editMaxWeight);

                toolbar = (Toolbar) findViewById(R.id.toolbar);
                toolbar.setLogo(R.mipmap.ic_settings_black_24dp__);
                setSupportActionBar(toolbar);

                initUI();
                setEventUI();
            }

            private void setEventUI() {
                btnSetTimeStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment fragment = new TimePickerFragment(btnSetTimeStart);
                        fragment.show(getSupportFragmentManager(), "timePicker");
                    }
                });

                btnSetTimeStop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment fragment = new TimePickerFragment(btnSetTimeStop);
                        fragment.show(getSupportFragmentManager(),"timePicker");
                    }
                });

                btnSaveConfig.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        int tmp = checkValid();

                        //if valid
                         if (tmp == 0){
                             List<String> str_list = new ArrayList<String>();
                             str_list.add(btnSetTimeStart.getText().toString());
                             str_list.add(btnSetTimeStop.getText().toString());
                             str_list.add(editFloorFirst.getText().toString());
                             str_list.add(editFloorEnd.getText().toString());
                             str_list.add(editIntervalTime.getText().toString());
                             str_list.add(editMaxWeight.getText().toString());


                            //Set alert
                            AlertFragment alertFragment = new AlertFragment(ConfigActivity.this, str_list);
                            alertFragment.show(getSupportFragmentManager(), "");


                    }
                    else if (tmp == 1){
                        Toast.makeText(getBaseContext(),R.string.str_invaild_empty,Toast.LENGTH_SHORT).show();
                    }
                    else if (tmp == 2){
                        Toast.makeText(getBaseContext(),R.string.str_invaild_logic_floor,Toast.LENGTH_SHORT).show();
                    }
                    else if (tmp == 3){
                        Toast.makeText(getBaseContext(),R.string.str_invalid_logic_time,Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }

    private void initUI()
    {
        SharedPreferences pre = getSharedPreferences(MainActivity.myPreferences,MODE_PRIVATE);
        int flag = pre.getInt("flag", 0);
        //if pre does not init
        if (flag == 0)
        {
            btnSetTimeStart.setText("SET TIME");
            btnSetTimeStop.setText("SET TIME");
        }
        else
        {
            btnSetTimeStart.setText(pre.getString("start_time",""));
            btnSetTimeStop.setText(pre.getString("end_time",""));

            editFloorFirst.setText("" + pre.getInt("floor_first",0));
            editFloorEnd.setText(""+ pre.getInt("floor_end",0));
            editIntervalTime.setText(("" + pre.getInt("interval_time", 0)));
            editMaxWeight.setText("" + pre.getInt("max_weight", 0));

        }
    }

    private int checkValid()
    {
        String str_btnTimeStart = btnSetTimeStart.getText().toString();
        String str_btnTimeStop = btnSetTimeStop.getText().toString();

        String str_edit_floor_first= editFloorFirst.getText().toString();
        String str_edit_floor_end = editFloorEnd.getText().toString();
        String str_edit_interval_time = editIntervalTime.getText().toString();
        String str_edit_max_weight = editMaxWeight.getText().toString();

        //check empty data
        if (str_btnTimeStart.equals("SET TIME") || str_btnTimeStop.equals("SET TIME"))
            return 1;
        if ( str_edit_floor_first.equals("") || str_edit_floor_end.equals(""))
            return 1;
        if (str_edit_interval_time.equals("") || str_edit_max_weight.equals(""))
            return 1;

        //check logic floor
        if (Integer.parseInt(str_edit_floor_first) > Integer.parseInt(str_edit_floor_end))
            return 2;

        //check logic time
        int hour_start =  Integer.parseInt(str_btnTimeStart.substring(0,2));
        int minute_start =  Integer.parseInt(str_btnTimeStart.substring(5));

        int hour_stop =  Integer.parseInt(str_btnTimeStop.substring(0,2));
        int minute_stop =  Integer.parseInt(str_btnTimeStop.substring(5));

        if (hour_start * 60 + minute_start > hour_stop * 60 + minute_stop)
            return 3;

        return 0;
    }

    //red line is a warning not error, but not way to slove problem
    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

        Button button;
        TimePickerFragment(Button button)
        {
            this.button = button;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute, true);
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String tmp_hour, tmp_mintue;

            if (hourOfDay < 10){
                tmp_hour = "0" + hourOfDay;
            }
            else{
                tmp_hour = "" + hourOfDay;
            }

            if (minute < 10){
                tmp_mintue = "0" + minute;
            }
            else{
                tmp_mintue = "" + minute;
            }

            String tmp = tmp_hour + " : " + tmp_mintue;
            this.button.setText(tmp);
        }
    }
}
