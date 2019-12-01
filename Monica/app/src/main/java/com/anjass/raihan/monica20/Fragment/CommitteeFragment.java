package com.anjass.raihan.monica20.Fragment;

import android.app.AlertDialog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.anjass.raihan.monica20.R;
import com.anjass.raihan.monica20.TestActivity;

public class CommitteeFragment extends Fragment {

    AlertDialog finishDialog, loadingFinishDialog;
    AlertDialog.Builder archiveDialog, loadingDialog;
    LayoutInflater inflater;
    View dialogView;

    private TextView completeCommitteeText,
            progressText, archivingStatusText,
            askDialogYes_btn, askDialogNo_btn;
    private ProgressBar progressBar;

    private int currentProgress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_committee, container, false);

        completeCommitteeText = (TextView) view.findViewById(R.id.completeCommittee);
        completeCommitteeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeCommitteeDialog();
            }
        });

        return view;
    }

    public void completeCommitteeDialog(){
        // Setup the alert builder
        archiveDialog = new AlertDialog.Builder(getActivity());

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
        loadingDialog = new AlertDialog.Builder(getActivity());

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
                getActivity().finish();
            }
        });
    }
}
