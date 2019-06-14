package com.anjass.raihan.monica20;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anjass.raihan.monica20.Home.HomeScreen;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

public class CreateCommittee extends AppCompatActivity {

    ImageButton imButt, imBut1, imBut2, imBut3;
    private static final int PICK_IMAGE = 100;
    Button button;
    Uri imageUri;
    EditText editText;
    TextView value, codeDate;
    Calendar calendar;
    Bitmap bitmap;
    int counter = 0;
    String
            currentDate,
            toast1 = "Project supervisor is title for management position that is primarily based on authority over a the project also to oversees the committee",
            toast2 = "Steering committee is a committee that provides guidance, direction and control to a project within an organization",
            toast3 = "Divide the structure of the project into some big division that manage more little division";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_committee);

        calendar = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
        currentDate = currentDate.replace("/","");

        button = (Button)findViewById(R.id.copyText);

        imButt = (ImageButton)findViewById(R.id.imageButton);
        imBut1 = (ImageButton)findViewById(R.id.imgHelp1);
        imBut2 = (ImageButton)findViewById(R.id.imgHelp2);
        imBut3 = (ImageButton)findViewById(R.id.imgHelp3);

        editText = (EditText)findViewById(R.id.editText);

        value = (TextView)findViewById(R.id.textValue);
        codeDate = (TextView)findViewById(R.id.txtId);
        codeDate.setText(currentDate);

        imButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        imBut1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),toast1,Toast.LENGTH_LONG).show();
            }
        });

        imBut2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),toast2,Toast.LENGTH_LONG).show();
            }
        });

        imBut3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),toast3,Toast.LENGTH_LONG).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("CodeInvitation", codeDate.getText().toString());
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(CreateCommittee.this, "copied", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void sendingData(View v){
        String name = editText.getText().toString();
        Toast.makeText(CreateCommittee.this,"Committee created",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(CreateCommittee.this, HomeActivity.class);
        /*
        intent.putExtra("ID01", name);
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
         */
        startActivity(intent);
        finish();
    }

    public void countIN(View view){
        if(counter<9)
            counter++;
        value.setText(Integer.toString(counter));
    }

    public void countDE(View view){
        if(counter>0)
            counter--;
        value.setText(Integer.toString(counter));
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data){
        super.onActivityResult(reqCode, resCode, data);
        if(resCode == RESULT_OK && reqCode == PICK_IMAGE){
            imageUri = data.getData();
            /*
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            */
            imButt.setImageURI(imageUri);
        }
    }
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBox1:
                if (checked) {
                    LinearLayout lyaout1 = (LinearLayout) findViewById(R.id.ProjectS);
                    lyaout1.setVisibility(View.VISIBLE);
                }
            else{
                    LinearLayout lyaout1 = (LinearLayout) findViewById(R.id.ProjectS);
                    lyaout1.setVisibility(View.GONE);
                }
                break;
            case R.id.checkBox2:
                if (checked){
                    LinearLayout lyaout2 = (LinearLayout) findViewById(R.id.steerCommitte);
                    lyaout2.setVisibility(View.VISIBLE);
                }
                else{
                    LinearLayout lyaout2 = (LinearLayout) findViewById(R.id.steerCommitte);
                    lyaout2.setVisibility(View.GONE);
                }
                break;
            case R.id.checkBox3:
                if (checked){
                    LinearLayout lyaout2 = (LinearLayout) findViewById(R.id.divSection);
                    lyaout2.setVisibility(View.VISIBLE);
                    counter = 0;
                    value.setText(Integer.toString(counter));
                }
                else{
                    LinearLayout lyaout2 = (LinearLayout) findViewById(R.id.divSection);
                    lyaout2.setVisibility(View.GONE);
                }
                break;
        }
    }

}
