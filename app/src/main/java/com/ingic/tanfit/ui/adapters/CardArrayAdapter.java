package com.ingic.tanfit.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ingic.tanfit.ui.viewbinders.abstracts.ViewBinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saeedhyder on 11/21/2017.
 */

public class CardArrayAdapter<T> extends ArrayAdapter<T> {
    protected Activity mContext;
    protected List<T> arrayList;
    protected ViewBinder<T> viewBinder;

    public CardArrayAdapter(@NonNull Context context, int resource, ViewBinder<T> viewBinder, ArrayList<T> testData) {
        super(context, resource);
        this.viewBinder = viewBinder;
        this.arrayList = testData;
    }

    public void setArrayList(List<T> arrayList) {
        this.arrayList = arrayList;
    }

    /**
     * Clears the internal list
     */
    public void clearList() {
        arrayList.clear();
        notifyDataSetChanged();
    }

    /**
     * Adds a entity to the list and calls {@link #notifyDataSetChanged()}.
     * Should not be used if lots of NotificationDummy are added.
     *
     * @see #addAll(List)
     */
    public void add(T entity) {
        arrayList.add(entity);
        notifyDataSetChanged();
    }

    /**
     * Adds a NotificationDummy to the list and calls
     * {@link #notifyDataSetChanged()}. Can be used {
     * {@link List#subList(int, int)}.
     *
     * @see #addAll(List)
     */
    public void addAll(List<T> entityList) {
        arrayList.addAll(entityList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Nullable
    @Override
    public T getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup arg2) {

        View convertView = view;

        if (convertView == null) {
            convertView = viewBinder.createView(mContext);
        }
        if (convertView.getTag() == null)
            convertView.setTag(viewBinder.createViewHolder(convertView));

        viewBinder.bindView(arrayList.get(position), position, 0,
                convertView, mContext);

        return convertView;
    }

    public T getItemFromList(int index) {
        return arrayList.get(index);
    }

    public List<T> getList() {
        return arrayList;
    }
}
