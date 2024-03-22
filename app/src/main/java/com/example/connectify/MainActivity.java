package com.example.connectify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    TextInputEditText meetingIdInput,nameInput;
    MaterialButton joinbtn,createbtn;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences=getSharedPreferences("name_pref",MODE_PRIVATE);

        meetingIdInput = findViewById(R.id.meeting_id);
        nameInput = findViewById(R.id.yourname);
        joinbtn = findViewById(R.id.join_btn);
        createbtn = findViewById(R.id.create_btn);
        nameInput.setText(sharedPreferences.getString("name",""));

        joinbtn.setOnClickListener((v) ->
        {
            String meetingID = meetingIdInput.getText().toString();
            if (meetingID.length() != 10) {
                meetingIdInput.setError("Invalid meeting ID");
                meetingIdInput.requestFocus();
                return;
            }
            String name = nameInput.getText().toString();
            if (name.isEmpty()) {
                nameInput.setError("Name is Required for Meeting");
                nameInput.requestFocus();
                return;
            }
            startMeeting(meetingID,name);

        });
        createbtn.setOnClickListener(v ->
        {
            String name = nameInput.getText().toString();
            if (name.isEmpty()) {
                nameInput.setError("Name is Required for Create The Meeting");
                nameInput.requestFocus();
                return;
            }
            startMeeting(getRandomMeetingID(),name);

        });
    }

        void startMeeting(String meetingID,String name){
        sharedPreferences.edit().putString("name",name).apply();
        String userID= UUID.randomUUID().toString();

            Intent intent=new Intent(MainActivity.this,conferenceActivity.class);
            intent.putExtra("meeting_ID",meetingID);
            intent.putExtra("name",name);
            intent.putExtra("user_id",userID);
            startActivity(intent);
;

    }

    String getRandomMeetingID(){
        StringBuilder id=new StringBuilder();
        while(id.length()!=10)
        {
            int random =new Random().nextInt(10);
            id.append(random);
        }
        return id.toString();

    }


}
