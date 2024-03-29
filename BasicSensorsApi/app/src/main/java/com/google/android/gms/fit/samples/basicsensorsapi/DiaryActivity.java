package com.google.android.gms.fit.samples.basicsensorsapi;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.graphview.GraphView;
import com.graphview.series.DataPointGr;
import com.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class DiaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        String title = "Мой дневник";
        SpannableString s = new SpannableString(title);
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);

        GraphView graph = findViewById(R.id.graph);
        LineGraphSeries<DataPointGr> series = new LineGraphSeries<>(getDataPoint());

        graph.addSeries(series);

// custom paint to make a dotted line
        //new HTTPAsyncTask().execute("http://194.58.102.106/api/sensordata");




    }

    public DataPointGr[] getDataPoint()
    {
        DataPointGr[] dp = new DataPointGr[]
                {
                        new DataPointGr(0, 70),
                        new DataPointGr(1, 74),
                        new DataPointGr(2, 71),
                        new DataPointGr(3, 73),
                        new DataPointGr(4, 78),
                        new DataPointGr(5, 83),
                        new DataPointGr(6, 75),
                        new DataPointGr(7, 73),
                        new DataPointGr(8, 68),
                        new DataPointGr(9, 61),
                        new DataPointGr(10, 65),
                        new DataPointGr(11, 59),
                        new DataPointGr(12, 49)
                };
        return (dp);
    }
}
