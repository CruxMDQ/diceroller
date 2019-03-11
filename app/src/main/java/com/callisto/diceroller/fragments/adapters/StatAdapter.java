package com.callisto.diceroller.fragments.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.callisto.diceroller.R;
import com.callisto.diceroller.application.App;
import com.callisto.diceroller.persistence.objects.Stat;

import java.util.List;

public class StatAdapter
    extends SelectionHidingAdapter
{
    public StatAdapter(List<Stat> stats)
    {
        super(stats);
    }

    @Override
    public long getItemId(int position)
    {
        return ((Stat) getItem(position)).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Stat stat = (Stat) getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater
                .from(App.getInstance().getApplicationContext())
                .inflate(R.layout.spinner_item, parent, false
                );

            TextView name = convertView.findViewById(R.id.text1);

            name.setText(stat.getName());
        }

        return convertView;
    }
}
