package com.example.watechpark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class LotDetailActivity extends AppCompatActivity {
    public static final String PARKING_LOT = "parking_lot";
    private TextView detailName, lotStatus, proximityData;
    private TextView slot1,slot2,slot3,slot4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lot_detail);


        detailName = findViewById(R.id.textView36);

        lotStatus = findViewById(R.id.textView37);
        proximityData = findViewById(R.id.textView42);

        slot1 = findViewById(R.id.textSlot1);
        slot2 = findViewById(R.id.textSlot2);
        slot3 = findViewById(R.id.textSlot3);
        slot4 = findViewById(R.id.textSlot4);

      Intent i = getIntent();
      String lotName = i.getStringExtra("lot_name");





      detailName.setText(lotName);




    }
}
