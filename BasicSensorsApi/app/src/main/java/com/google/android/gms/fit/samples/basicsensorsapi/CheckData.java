package com.google.android.gms.fit.samples.basicsensorsapi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.DigitsKeyListener;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.fit.samples.basicsensorsapi.activities.ActivityAccountReady;
import com.redmadrobot.inputmask.MaskedTextChangedListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CheckData extends AppCompatActivity {
    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_data);

        String title = "Проверка данных";
        SpannableString s = new SpannableString(title);
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);

        EditText in_name = findViewById(R.id.input_name);
        final EditText in_date = findViewById(R.id.input_date);
        final EditText in_oms = findViewById(R.id.input_oms);

        findViewById(R.id.b_next1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject data = null;
                try {
                    data = buidJsonObject(in_oms.getText().toString(), in_date.getText().toString());
                    if (data != null)
                        new AsyncRequest(getApplicationContext(), "https://test-api.mosmedzdrav.ru/zabota/api/register", data).execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(CheckData.this, ActivityAccountReady.class);
                startActivity(intent);
            }
        });

        View.OnFocusChangeListener focuschange = new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                {
                    ((RelativeLayout)view.getParent()).setTranslationZ(5);
                }
                else
                {
                    ((RelativeLayout)view.getParent()).setTranslationZ(0);
                }
            }
        };

        in_date.setInputType(InputType.TYPE_CLASS_NUMBER);
        in_date.setKeyListener(DigitsKeyListener.getInstance("1234567890."));
        in_date.addTextChangedListener(new MaskedTextChangedListener("[00].[00].[0000]", in_date));

        in_oms.setInputType(InputType.TYPE_CLASS_NUMBER);
        in_oms.setKeyListener(DigitsKeyListener.getInstance("1234567890 "));
        in_oms.addTextChangedListener(new MaskedTextChangedListener("[0000] [0000] [0000] [0000]", in_oms));

        in_name.setOnFocusChangeListener(focuschange);
        in_date.setOnFocusChangeListener(focuschange);
        in_oms.setOnFocusChangeListener(focuschange);

    }


    private JSONObject buidJsonObject(String oms, String date) throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException {

        String value = oms.replace(" ", "") + date;

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String formattedDate = df.format(c);

        String check_val = value + formattedDate + "Zabota+";

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(check_val.getBytes("UTF-8"));
        byte[] cv = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : cv) {
            sb.append(String.format("%02x", b));
        }
        String ChallengeVerification = sb.toString();

        byte[] data = value.getBytes("UTF-8");
        String Base64Value = Base64.encodeToString(data, Base64.NO_WRAP);

        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("Challenge", Base64Value + "." + ChallengeVerification);

        return jsonObject;
    }
}
