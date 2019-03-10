package com.callisto.diceroller.fragments.characterlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.callisto.diceroller.R;
import com.callisto.diceroller.application.App;
import com.callisto.diceroller.persistence.RealmHelper;
import com.callisto.diceroller.persistence.objects.Template;
import com.callisto.diceroller.tools.Constants;
import com.callisto.diceroller.tools.TypefaceSpanBuilder;

import java.util.ArrayList;
import java.util.List;

public class TemplateAdapter
    extends BaseAdapter
{
    private List<Template> templates;

    private List<Integer> selectedIndexes;

    TemplateAdapter()
    {
        selectedIndexes = new ArrayList<>();

        // TODO Implement filtering by game system
        this.templates = RealmHelper.getInstance().getList(Template.class);
    }

    @Override
    public int getCount()
    {
        return templates.size();
    }

    @Override
    public Object getItem(int position)
    {
        return templates.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return ((Template) getItem(position)).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Template template = (Template) getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater
                .from(App.getInstance().getApplicationContext())
                .inflate(R.layout.spinner_item, parent, false
                );

            TextView name = convertView.findViewById(R.id.text1);

            if (template != null)
            {
                TypefaceSpanBuilder.setTypefacedTitle(
                    name,
                    template.getName(),
                    template.getFont(),
                    Constants.Values.STAT_CONTAINER_FONT_TITLE.getValue()
                );
            }
        }

        return convertView;
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
}
