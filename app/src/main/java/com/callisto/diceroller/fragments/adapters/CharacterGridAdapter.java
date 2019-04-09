package com.callisto.diceroller.fragments.adapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.callisto.diceroller.R;
import com.callisto.diceroller.application.App;
import com.callisto.diceroller.persistence.objects.Character;

import java.util.List;

public class CharacterGridAdapter
    extends CustomAdapter
{
    public CharacterGridAdapter(@NonNull List<Character> objects)
    {
        super(objects);
    }

    @Override
    public long getItemId(int position)
    {
        return ((Character) getItem(position)).getId();
    }

    @NonNull
    public View getView
        (
            int position,
            View convertView,
            @NonNull ViewGroup parent
        )
    {
        Character character = (Character) getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater
                .from(App.getInstance().getApplicationContext())
                .inflate(R.layout.grid_view_item, parent, false);

            TextView name = convertView.findViewById(R.id.txtName);

            if (character != null)
            {
                name.setText(character.getName());
            }

        }
        return convertView;
    }
}
