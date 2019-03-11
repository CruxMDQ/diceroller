package com.callisto.diceroller.fragments.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.callisto.diceroller.R;
import com.callisto.diceroller.application.App;
import com.callisto.diceroller.persistence.objects.Template;
import com.callisto.diceroller.tools.Constants;
import com.callisto.diceroller.tools.TypefaceSpanBuilder;

import java.util.List;

public class TemplateAdapter
    extends SelectionHidingAdapter
{
    public TemplateAdapter(List<Template> templates)
    {
//        // TODO Implement filtering by game system
//        this.templates = RealmHelper.getInstance().getList(Template.class);
        super(templates);
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
}
