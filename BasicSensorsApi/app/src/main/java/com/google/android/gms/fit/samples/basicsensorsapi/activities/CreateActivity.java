package com.google.android.gms.fit.samples.basicsensorsapi.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.fit.samples.basicsensorsapi.CheckData;
import com.google.android.gms.fit.samples.basicsensorsapi.R;

import java.util.Locale;

public class CreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        setTitle("Создать аккаунт");
        int orange = getResources().getColor(R.color.white);
        String htmlColor = String.format(Locale.US, "#%06X", (0xFFFFFF & Color.argb(0, Color.red(orange), Color.green(orange), Color.blue(orange))));

        findViewById(R.id.b_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateActivity.this, CheckData.class);
                startActivity(intent);
            }
        });
    }
}