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
    private ImageView detailImage;
    private TextView detailName, detailCost, lotStatus, proximityData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lot_detail);

        detailImage = findViewById(R.id.imageView8);
        detailName = findViewById(R.id.textView36);
        detailCost = findViewById(R.id.textView41);
        lotStatus = findViewById(R.id.textView37);
        proximityData = findViewById(R.id.textView42);

      Intent i = getIntent();
      String lotName = i.getStringExtra("lot_name");
      String lotCost = i .getStringExtra("lot_cost");
     int lotImage = i.getIntExtra("lot_image", 0);

      detailName.setText(lotName);
      detailCost.setText(lotCost);

      detailImage.setImageResource(lotImage);





    }
}
