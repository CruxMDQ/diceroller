package com.callisto.diceroller.fragments.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.callisto.diceroller.R;
import com.callisto.diceroller.application.App;
import com.callisto.diceroller.persistence.objects.Character;

import java.util.List;

public class CharacterAdapter
    extends SelectionHidingAdapter
{
    public CharacterAdapter(List<Character> contents)
    {
        super(contents);
    }

    @Override
    public long getItemId(int position)
    {
        return ((Character) getItem(position)).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Character character = (Character) getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater
                .from(App.getInstance().getApplicationContext())
                .inflate(R.layout.spinner_item, parent, false);

            TextView name = convertView.findViewById(R.id.text1);

            name.setText(character.getName());

        }
        return convertView;
    }
}
