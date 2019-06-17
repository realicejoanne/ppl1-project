package com.anjass.raihan.monica20.Fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.anjass.raihan.monica20.Adapter.List_Adapter;
import com.anjass.raihan.monica20.Class.List_Class;
import com.anjass.raihan.monica20.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ItemListFragment extends Fragment {

    private EditText addItem;
    private ListView toDoListView;
    private ImageButton startAddItem;
    private LinearLayout ifEmpty;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button confirmDate, confirmTime;

    DatabaseReference databaseToDoList;
    private boolean isListEmpty = true;
    private List<List_Class> itemList;
    private Date dueDate;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        databaseToDoList = FirebaseDatabase.getInstance().getReference("itemList");
        itemList = new ArrayList<>();

        toDoListView = (ListView) view.findViewById(R.id.itemList);
        ifEmpty = (LinearLayout) view.findViewById(R.id.ifEmpty);
        addItem = view.findViewById(R.id.addItem);
        datePicker = view.findViewById(R.id.datePicker);
        timePicker = view.findViewById(R.id.timePicker);

        confirmDate = view.findViewById(R.id.confirmDate);
        confirmDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSequenceAddTime();
            }
        });

        confirmTime = view.findViewById(R.id.confirmTime);
        confirmTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endSequenceaddItem();
            }
        });

        startAddItem = view.findViewById(R.id.startAddItem);
        startAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSequenceAddDate();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        toDoListView.setFriction(ViewConfiguration.getScrollFriction() *50);

        //Ambil data dari Firebase Database
        databaseToDoList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Kalo update, clear dulu biar ga numpuk
                itemList.clear();

                // Kirim data perchild ke kelas responden
                for(DataSnapshot respondenSnapshot : dataSnapshot.getChildren()){
                    List_Class list_class = respondenSnapshot.getValue(List_Class.class);

                    itemList.add(list_class);
                }

                // Finalizing
                List_Adapter adapter = new List_Adapter(getActivity(), itemList);
                toDoListView.setAdapter(adapter);
                isListEmpty = false;

                // If there is a task at least one, change the look
                if (! isListEmpty){
                    toDoListView.setVisibility(View.VISIBLE);
                    ifEmpty.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addNewTask(){
        // ADD NEW TASK



        // Getting ID push
        String id = databaseToDoList.push().getKey();

        // Getting current time in the server zone
        HashMap<String, Object> map = new HashMap();
        map.put("time", ServerValue.TIMESTAMP);

        // Getting data task
        String taskEntered = addItem.getText().toString();
        try{
            // Getting de date
            String dateString = datePicker.getDayOfMonth() +"-" +datePicker.getMonth()
                    +"-" +datePicker.getYear() +" -- " +timePicker.getHour();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy -- h");
            dueDate = dateFormat.parse(dateString);
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }



        // Check if taskEntered is not null
        if (TextUtils.isEmpty(taskEntered))
            Toast.makeText(getContext(), "What is your task?", Toast.LENGTH_SHORT).show();
        else{
            // Submit the data to Firebase database
            try{
                List_Class itemList = new List_Class(id, taskEntered, false, map, dueDate);
                databaseToDoList.child(id).setValue(itemList);
                Toast.makeText(getContext(), "Task added!", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            addItem.setText("");
        }
    }

    public void startSequenceAddDate(){
        // THE FIRST, confirm due date

        String taskEntered = addItem.getText().toString();
        if (TextUtils.isEmpty(taskEntered))
            Toast.makeText(getContext(), "What is your task?", Toast.LENGTH_SHORT).show();
        else{
            startAddItem.setImageResource(R.drawable.icon_calendar);
            datePicker.setVisibility(View.VISIBLE);
            confirmDate.setVisibility(View.VISIBLE);
        }
    }

    public void startSequenceAddTime() {
        // SECOND, confirm due time

        datePicker.setVisibility(View.GONE);
        confirmDate.setVisibility(View.GONE);

        timePicker.setVisibility(View.VISIBLE);
        confirmTime.setVisibility(View.VISIBLE);
    }

    public void endSequenceaddItem(){
        // LAST, back to normal

        timePicker.setVisibility(View.GONE);
        confirmTime.setVisibility(View.GONE);

        startAddItem.setVisibility(View.VISIBLE);
        startAddItem.setImageResource(R.drawable.icon_add);

        addNewTask();
    }
}
