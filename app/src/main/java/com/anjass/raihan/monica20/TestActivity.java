package com.anjass.raihan.monica20;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestActivity extends AppCompatActivity {

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    private TextView result;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button dialogBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        datePicker = (DatePicker)findViewById(R.id.date_picker);
        result = (TextView)findViewById(R.id.result);
        timePicker = (TimePicker)findViewById(R.id.time_picker);


        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // display a toast with changed values of time picker
                Toast.makeText(getApplicationContext(), hourOfDay + "  " + minute, Toast.LENGTH_SHORT).show();
                result.setText("TimePicker : " + hourOfDay + " : " + minute); // set the current time in text view
            }
        });

        dialogBtn = (Button)findViewById(R.id.dialog);
    }

    public void getDate(View v){
        int correctMonth = datePicker.getMonth() + 1;
        String text = "" +datePicker.getDayOfMonth() +"-" +correctMonth
                +"-" +datePicker.getYear();

        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        //int difference = Math.abs(datePicker.getDayOfMonth() - Integer.parseInt(sdf.format(currentDate)));

        SimpleDateFormat testOutput = new SimpleDateFormat("dd/MM/yyyy -- EEE");

        try{
            int hour = timePicker.getHour();
            Date date1 = sdf.parse(text);

            result.setText(testOutput.format(date1));
        }catch (Exception e){
            result.setText("Gagal");
        }

        //result.setText("" +difference);
    }

}
