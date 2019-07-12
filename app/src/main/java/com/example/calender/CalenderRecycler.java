package com.example.calender;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CalenderRecycler extends RecyclerView.Adapter<CalenderRecycler.ViewHolder>{

    private Context mContext;
    private int daysInCurrentMonth;
    private int previousMonthDays;
    private int firstDayOfCurrentMonth;
    private String mToday;
    private Calendar mCalender;
    private CalenderDayClickListener clickListener;
    private static final String[] MONTH_NAMES = {"January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"};
    private Map<String, Integer> bookingsList;

    public CalenderRecycler(Context mContext, Calendar mCalender, String mToday) {
        this.mContext = mContext;
        this.mCalender = mCalender;
        this.mToday = mToday;
        daysInCurrentMonth = mCalender.getActualMaximum(Calendar.DAY_OF_MONTH);
        firstDayOfCurrentMonth = mCalender.get(Calendar.DAY_OF_WEEK);
        previousMonthDays = firstDayOfCurrentMonth - 1;
        bookingsList = new HashMap<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.day_layout, parent, false));
    }

    public void setDateClickListener(CalenderDayClickListener clickListener){
        this.clickListener = clickListener;
    }

    public void changeMonth(Calendar mCalender){
        this.mCalender = mCalender;
        daysInCurrentMonth = mCalender.getActualMaximum(Calendar.DAY_OF_MONTH);
        firstDayOfCurrentMonth = mCalender.get(Calendar.DAY_OF_WEEK);
        previousMonthDays = firstDayOfCurrentMonth - 1;
        bookingsList.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        if(position <= previousMonthDays-1){
            holder.day.setVisibility(View.INVISIBLE);
        }
        else{
            holder.day.setText(String.valueOf(position - (previousMonthDays - 1)));
        }

        if((holder.day.getText() + " " +MONTH_NAMES[mCalender.get(Calendar.MONTH)] + " " + mCalender.get(Calendar.YEAR)).equals(mToday)){
            holder.day.setBackgroundResource(R.drawable.drawable_rectangular);
            holder.day.setTextColor(Color.WHITE);
        }

        holder.dayContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.OnClick(holder.day.getText() + " " + MONTH_NAMES[mCalender.get(Calendar.MONTH)] + " " + mCalender.get(Calendar.YEAR));
            }
        });

        if(bookingsList.size()!=0){
            if(bookingsList.containsKey(holder.day.getText() + " " + MONTH_NAMES[mCalender.get(Calendar.MONTH)] + " " + mCalender.get(Calendar.YEAR))){
                holder.bookingTxt.setVisibility(View.VISIBLE);
                holder.bookingTxt.setText(bookingsList.get(holder.day.getText() + " " + MONTH_NAMES[mCalender.get(Calendar.MONTH)] + " " + mCalender.get(Calendar.YEAR)) + " bookings");
            }
        }
        else{
            holder.bookingTxt.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return daysInCurrentMonth + previousMonthDays;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView day, bookingTxt;
        private ConstraintLayout dayContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.day);
            dayContainer = itemView.findViewById(R.id.dayContainer);
            bookingTxt = itemView.findViewById(R.id.booking_txt);
        }
    }

    interface CalenderDayClickListener{
        void OnClick(String date);
    }

    public void addEvents(HashMap<String, Integer> bookingsList){
        this.bookingsList = bookingsList;
        notifyDataSetChanged();
    }
}
