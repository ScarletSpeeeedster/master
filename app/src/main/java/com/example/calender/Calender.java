package com.example.calender;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class Calender extends LinearLayout implements View.OnClickListener{

    private int selectedColor;
    private Context mContext;
    private RecyclerView recyclerView;
    private ImageView btnPrevious, btnNext;
    private TextView month;
    private Calendar mCalendar;
    public CalenderRecycler mCalenderAdapter;
    private String mToday;
    private static final String[] MONTH_NAMES = {"January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"};
    public MonthChangedListener monthChangedListener;

    public Calender(Context context) {
        super(context);
    }

    public Calender(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        View view = inflate(context, R.layout.calender_view, null);
        recyclerView = view.findViewById(R.id.recyclerView);
        btnPrevious = view.findViewById(R.id.button_previous);
        btnNext = view.findViewById(R.id.button_next);
        month = view.findViewById(R.id.text_view_month_name);
        btnPrevious.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        addView(view);
        mCalendar = Calendar.getInstance();
        month.setText(MONTH_NAMES[mCalendar.get(Calendar.MONTH)] + " " + mCalendar.get(Calendar.YEAR));
        mToday = mCalendar.get(Calendar.DATE) + " " + MONTH_NAMES[mCalendar.get(Calendar.MONTH)] + " " + mCalendar.get(Calendar.YEAR);
        mCalenderAdapter = new CalenderRecycler(mContext, mCalendar, mToday);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 7));
        recyclerView.setAdapter(mCalenderAdapter);
        Log.d(TAG, "Today, " + mToday);
    }

    private void init(Context context, AttributeSet attrs) {
        if(attrs == null)
            return;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Calender);
        selectedColor = typedArray.getColor(R.styleable.Calender_color, Color.parseColor("#354395"));
        typedArray.recycle();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_next){
                goToNextMonth();
        }
        else if(view.getId() == R.id.button_previous){
            goToPreviousMonth();
        }
    }

    private void goToPreviousMonth() {
        if(mCalenderAdapter==null) return;

        int year1 = mCalendar.get(Calendar.YEAR);
        int month1 = mCalendar.get(Calendar.MONTH);

        if (month1 > 0) {
            mCalendar.set(year1, month1 - 1, 1);
        } else {
            mCalendar.set(year1 - 1, 11, 1);
        }

        month.setText(MONTH_NAMES[mCalendar.get(Calendar.MONTH)] + " " + mCalendar.get(Calendar.YEAR));
        mCalenderAdapter.changeMonth(mCalendar);
        monthChangedListener.onChanged(MONTH_NAMES[mCalendar.get(Calendar.MONTH)]);
    }

    private void goToNextMonth() {
        if(mCalenderAdapter==null) return;

        int year1 = mCalendar.get(Calendar.YEAR);
        int month1 = mCalendar.get(Calendar.MONTH);

        if (month1 < 11) {
            mCalendar.set(year1, month1 + 1, 1);
        } else {
            mCalendar.set(year1 + 1, 0, 1);
        }

        month.setText(MONTH_NAMES[mCalendar.get(Calendar.MONTH)] + " " + mCalendar.get(Calendar.YEAR));
        mCalenderAdapter.changeMonth(mCalendar);
        monthChangedListener.onChanged(MONTH_NAMES[mCalendar.get(Calendar.MONTH)]);
    }

    public void setMonthChangedListener(MonthChangedListener monthChangedListener){
        this.monthChangedListener = monthChangedListener;
    }

    public void addEvents(HashMap<String, Integer> bookingsList){
        if(mCalenderAdapter==null) return;
        mCalenderAdapter.addEvents(bookingsList);
    }

    public interface MonthChangedListener{
        void onChanged(String month);
    }
}
