package com.callisto.diceroller.fragments.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.callisto.diceroller.application.App;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;

abstract class SelectionHidingAdapter<T extends RealmObject>
    extends BaseAdapter
{
    private List<T> contents;
    private List<Integer> selectedIndexes;

    SelectionHidingAdapter(List<T> contents)
    {
        this.contents = contents;
        this.selectedIndexes = new ArrayList<>();
    }

    @Override
    public int getCount()
    {
        return contents.size();
    }

    @Override
    public Object getItem(int position)
    {
        return contents.get(position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        View v;

        if (selectedIndexes.contains(position))
        {
            TextView tv = new TextView(App.getInstance().getApplicationContext());
            tv.setVisibility(View.GONE);
            v = tv;
        }
        else
        {
            v = super.getDropDownView(position, null, parent);
        }

        return v;
    }

    public void addSelection(int index)
    {
        selectedIndexes.add(index);
    }

    public void clearSelection()
    {
        selectedIndexes.clear();
    }

    @Override
    public abstract long getItemId(int position);

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

}
