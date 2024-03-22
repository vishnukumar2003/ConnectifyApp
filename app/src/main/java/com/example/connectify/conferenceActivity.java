package com.example.connectify;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.zegocloud.uikit.prebuilt.videoconference.ZegoUIKitPrebuiltVideoConferenceConfig;
import com.zegocloud.uikit.prebuilt.videoconference.ZegoUIKitPrebuiltVideoConferenceFragment;


public class conferenceActivity extends AppCompatActivity {

    TextView meetingIDText;
    ImageView shareBtn;
    String meetingID,userID,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_conference);
        meetingIDText=findViewById(R.id.meeting_id_textview);
        shareBtn=findViewById(R.id.share_button);
   meetingID=getIntent().getStringExtra("meeting_ID");
   userID=getIntent().getStringExtra("user_id");
   name=getIntent().getStringExtra("name");
   meetingIDText.setText("MeetingID:"+meetingID);

   addFragment();

     shareBtn.setOnClickListener(v -> {
         Intent intent=new Intent();
         intent.setAction(Intent.ACTION_SEND);
         intent.setType("text/plain");
         intent.putExtra(Intent.EXTRA_TEXT,"Join The Meeting in Connectify App\n Meeting ID:"+meetingID);
         startActivity(Intent.createChooser(intent,"Share Via"));
     });




    }
    public void addFragment() {
        long appID = AppConstraints.appid;
        String appSign = AppConstraints.appSign;


        ZegoUIKitPrebuiltVideoConferenceConfig config = new ZegoUIKitPrebuiltVideoConferenceConfig();
        ZegoUIKitPrebuiltVideoConferenceFragment fragment = ZegoUIKitPrebuiltVideoConferenceFragment.newInstance(appID, appSign, userID, name,meetingID,config);
        config.turnOnCameraWhenJoining=false;
        config.turnOnMicrophoneWhenJoining=false;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commitNow();
    }

}