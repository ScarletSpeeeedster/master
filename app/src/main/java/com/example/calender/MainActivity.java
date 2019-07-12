package com.example.calender;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Calender calender;
    private HashMap<String, Integer> map = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calender = findViewById(R.id.calender);

        map.put("10 July 2019",  10);
        map.put("11 July 2019",  10);
        map.put("18 July 2019", 5);
        map.put("29 July 2019", 6);
        map.put("20 July 2019", 10);

        calender.addEvents(map);

        calender.setMonthChangedListener(new Calender.MonthChangedListener() {
            @Override
            public void onChanged(String month) {
                Toast.makeText(MainActivity.this, "Month changed to " + month, Toast.LENGTH_SHORT).show();
                if(month.equalsIgnoreCase("July")){
                    map.put("10 July 2019",  10);
                    map.put("11 July 2019",  10);
                    map.put("18 July 2019", 5);
                    map.put("29 July 2019", 6);
                    map.put("20 July 2019", 10);
                    calender.addEvents(map);
                }
            }
        });
    }
}
