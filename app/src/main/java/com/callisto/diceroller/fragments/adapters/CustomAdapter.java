package com.callisto.diceroller.fragments.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

abstract public class CustomAdapter<T>
    extends BaseAdapter
{
    private List<T> contents;
    List<Integer> selectedIndexes;

    protected CustomAdapter(List<T> contents)
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

    public void addOrRemoveSelection(Integer index)
    {
        if (!selectedIndexes.contains(index))
        {
            selectedIndexes.add(index);
        }
        else
        {
            selectedIndexes.remove(index);
        }
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
