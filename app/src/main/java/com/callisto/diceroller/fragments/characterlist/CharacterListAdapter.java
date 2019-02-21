package com.callisto.diceroller.fragments.characterlist;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.callisto.diceroller.R;
import com.callisto.diceroller.application.App;
import com.callisto.diceroller.persistence.objects.Character;

import java.util.List;

public class CharacterListAdapter
    extends BaseAdapter
{
    private List<Character> characters;

    CharacterListAdapter(@NonNull List<Character> objects)
    {
        this.characters = objects;
    }

    @Override
    public int getCount()
    {
        return characters.size();
    }

    @Override
    public Object getItem(int position)
    {
        return characters.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
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
//            TextView template = convertView.findViewById(R.id.txtTemplate);

            if (character != null)
            {
                name.setText(character.getName());
            }
//            template.setText(character.getTemplate());

        }
        return convertView;
    }
}
