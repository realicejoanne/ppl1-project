package com.anjass.raihan.monica20;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestActivity extends AppCompatActivity {

    AlertDialog finishDialog, loadingFinishDialog;
    AlertDialog.Builder archiveDialog, loadingDialog;
    LayoutInflater inflater;
    View dialogView;

    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button dialogBtn;
    private TextView result, progressText, archivingStatusText,
        askDialogYes_btn, askDialogNo_btn;
    private ProgressBar progressBar;

    private int currentProgress;

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

    public void finishTestDialog(View v){
        // Setup the alert builder
        archiveDialog = new AlertDialog.Builder(TestActivity.this);

        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.finish_committee_archiving_ask, null);

        archiveDialog.setView(dialogView);
        archiveDialog.setCancelable(true);

        finishDialog = archiveDialog.create();
        finishDialog.show();


        // Codes ============================

        askDialogYes_btn = (TextView) dialogView.findViewById(R.id.askDialogYes_btn);
        askDialogYes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog();
            }
        });

        askDialogNo_btn = (TextView) dialogView.findViewById(R.id.askDialogNo_btn);
        askDialogNo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishDialog.cancel();
            }
        });

    }

    private void loadingDialog(){
        // Setup the alert builder
        loadingDialog = new AlertDialog.Builder(TestActivity.this);

        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.finish_committee_archiving_loading, null);

        loadingDialog.setView(dialogView);
        loadingDialog.setCancelable(true);

        loadingFinishDialog = loadingDialog.create();
        loadingFinishDialog.show();


        // Codes Asyique =================

        progressBar = (ProgressBar) dialogView.findViewById(R.id.progressBar);
        progressText = (TextView) dialogView.findViewById(R.id.progressText);
        archivingStatusText = (TextView) dialogView.findViewById(R.id.archivingStatusText);

        currentProgress = 0;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(currentProgress <= 100){
                        progressBar.setProgress(currentProgress);
                        progressText.setText(currentProgress +"%");
                        currentProgress += 1;
                        Thread.sleep(30);
                    }

                    progressText.setText("Complete!");
                    archivingStatusText.setText("Close this dialog");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        archivingStatusText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishDialog.cancel();
                loadingFinishDialog.cancel();
                finish();
            }
        });
    }
}
