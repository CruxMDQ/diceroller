package com.callisto.diceroller.fragments.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.callisto.diceroller.application.App;

import java.util.List;

abstract public class SelectionHidingAdapter<T>
    extends CustomAdapter
{
    protected SelectionHidingAdapter(List<T> contents)
    {
        super(contents);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        View v;

        if (selectedIndexes.contains(position))
        {
            TextView tv = new TextView(App.getInstance().getApplicationContext());
            tv.setHeight(0);
            tv.setVisibility(View.GONE);
            v = tv;
        }
        else
        {
            v = super.getDropDownView(position, null, parent);
        }

        return v;
    }
}
