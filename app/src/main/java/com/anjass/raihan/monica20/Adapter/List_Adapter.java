package com.anjass.raihan.monica20.Adapter;

import android.app.Activity;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anjass.raihan.monica20.Class.List_Class;
import com.anjass.raihan.monica20.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class List_Adapter extends ArrayAdapter<List_Class> {
    private Activity context;
    private List<List_Class> taskList;
    private CheckBox isiPesan;
    private TextView time;
    private List_Class pesan;
    private LinearLayout linearLayout;

    private int temp;

    public List_Adapter (Activity context, List<List_Class> taskList){
        super(context, R.layout.list_layout, taskList);
        this.context = context;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public View getView (int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem;
        temp = position;

        pesan = taskList.get(temp);
        String id = taskList.get(position).getId();
        listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        linearLayout = (LinearLayout) listViewItem.findViewById(R.id.linearLayoutID);
        isiPesan = (CheckBox) listViewItem.findViewById(R.id.pesan);
        isiPesan.setText(pesan.getIsiPesan());
        isiPesan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {

                if ( isChecked ){
                    //isiPesan.setChecked(! isiPesan.isChecked());
                    if(! taskList.get(temp).getIsChecked())
                        taskList.get(temp).setIsChecked(true);
                    else
                        taskList.get(temp).setIsChecked(false);
                }
            }
        });

        // Logic if a task is checked
        isiPesan.postDelayed(new Runnable() {
            @Override
            public void run() {
                isiPesan.setChecked(pesan.getIsChecked());
            }
        }, 1);
        if (! pesan.getIsChecked())
            isiPesan.setText(pesan.getIsiPesan());
        else {
            isiPesan.setText(pesan.getIsiPesan());
            isiPesan.setPaintFlags(isiPesan.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        // Setting the date
        time = (TextView) listViewItem.findViewById(R.id.time);
        try{
            Date date = pesan.getDueDate();
            SimpleDateFormat sfd = new SimpleDateFormat("EEE, MMM d, hh aaa");

            time.setText(sfd.format(date));
        }
        catch (Exception e){
            time.setText("Failed");
        }

        return listViewItem;
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = isiPesan.isChecked();

        // Check which checkbox was clicked
        if (checked)
            isiPesan.toggle();
        else{
            isiPesan.setPaintFlags(isiPesan.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            isiPesan.toggle();
        }
    }
}