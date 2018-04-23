package com.example.abdulrahman.project6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView msg = findViewById(R.id.msg);
        String s = getIntent().getExtras().getString("msg");
        msg.setText(s);
    }
}
