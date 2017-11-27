package com.github.jhonnyx2012.horizontalpicker;


import android.app.AlarmManager;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by jhonn on 22/02/2017.
 */

public class HorizontalPickerAdapter extends RecyclerView.Adapter<HorizontalPickerAdapter.ViewHolder> {

    private static final long DAY_MILLIS = AlarmManager.INTERVAL_DAY;
    private final OnItemClickedListener listener;
    private final int primaryTextColor;
    private int itemWidth;
    private ArrayList<Day> items;

    public HorizontalPickerAdapter(int itemWidth, OnItemClickedListener listener, Context context, int daysToCreate, int offset) {
        items = new ArrayList<>();
        this.itemWidth = itemWidth;
        this.listener = listener;
        this.primaryTextColor = context.getResources().getColor(R.color.primaryTextColor);
        generateDays(daysToCreate, new DateTime().minusDays(offset).getMillis(), false);
    }

    public void generateDays(int n, long initialDate, boolean cleanArray) {
        if (cleanArray)
            items.clear();
        int i = 0;
        while (i < n) {
            DateTime actualDate = new DateTime(initialDate + (DAY_MILLIS * i++));
            items.add(new Day(actualDate));
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_day, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Day item = getItem(position);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/yy", Locale.ENGLISH);
        holder.tvDay.setText(simpleDateFormat.format(item.getDate().toDate()));
        holder.tvWeekDay.setText(item.getWeekDay());
        if (item.isSelected()) {
            holder.itemView.setBackgroundResource(R.drawable.background_day_selected);
            holder.tvDay.setTextColor(Color.WHITE);
            holder.tvWeekDay.setTextColor(Color.WHITE);
        } else if (item.isToday()) {
            holder.tvDay.setTextColor(primaryTextColor);
            holder.itemView.setBackgroundResource(R.drawable.background_day_today);
            holder.tvWeekDay.setTextColor(primaryTextColor);
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            holder.tvDay.setTextColor(primaryTextColor);
            holder.tvWeekDay.setTextColor(primaryTextColor);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Day getItem(int position) {

        return items.get(position);
//        return items.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvDay, tvWeekDay;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDay = (TextView) itemView.findViewById(R.id.tvDay);
            tvDay.setWidth(itemWidth);
            tvWeekDay = (TextView) itemView.findViewById(R.id.tvWeekDay);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClickView(v, getAdapterPosition());
        }
    }
}