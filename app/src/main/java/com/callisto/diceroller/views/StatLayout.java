package com.callisto.diceroller.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.callisto.diceroller.R;
import com.callisto.diceroller.beans.Stat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class StatLayout extends LinearLayout {

    private Boolean isOpen;

    private ArrayList<Stat> containedStats;
    private ArrayList<Stat> pickedStats;

    private LinearLayout panelStats;

    private TextView lblSelectedStats;
    private TextView txtSelectedStats;

    public StatLayout(
        Context context,
        AttributeSet attrs,
        int defStyleAttr,
        int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);

        TypedArray args = context.obtainStyledAttributes(
            attrs,
            R.styleable.StatLayout,
            0,
            0);

        init(args);

        args.recycle();
    }

    public StatLayout(
        Context context,
        AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray args = context.obtainStyledAttributes(
            attrs,
            R.styleable.StatLayout,
            0,
            0);

        init(args);

        args.recycle();
    }

    public StatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray args = context.obtainStyledAttributes(
            attrs,
            R.styleable.StatLayout,
            0,
            0);

        init(args);

        args.recycle();
    }

    public StatLayout(Context context) {
        super(context);
        init(null);
    }

    private void init(@Nullable TypedArray args) {
        if (args != null) {
            isOpen = args.getBoolean(R.styleable.StatLayout_isOpen, false);
        } else {
            isOpen = false;
        }

        containedStats = new ArrayList<>();
        pickedStats = new ArrayList<>();

        setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                StatLayout t = (StatLayout) v;

                if (t.getPickedStatsCount() == 0)
                {
                    if (!(t.isOpen()))
                    {
                        toggleStatPanel
                            (
                                View.VISIBLE,
                                true,
                                R.color.color_light_gray
                            );
                    } else
                    {
                        toggleStatPanel
                            (
                                View.GONE,
                                false,
                                R.color.color_dark_gray
                            );
                    }
                }
                else
                {
                    if (!(t.isOpen()))
                    {
                        toggleStatPanel
                            (
                                View.VISIBLE,
                                true
                            );
                    } else
                    {
                        toggleStatPanel
                            (
                                View.GONE,
                                false
                            );
                    }
                }
            }
        });
    }

    private void toggleStatPanel(int visible, boolean isOpen)
    {
        getPanelStats().setVisibility(visible);
        setOpen(isOpen);
    }

    private void toggleStatPanel(
        int visible,
        boolean isOpen,
        int color)
    {
        toggleStatPanel(visible, isOpen);
        int targetColor = ContextCompat.getColor(
            Objects.requireNonNull(getContext()), color);
        
//        setBackgroundColor(targetColor);

        setBackgroundDrawableColor(targetColor);
    }

    private void setBackgroundDrawableColor(int targetColor)
    {
        Drawable background = getBackground();
        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable)background).getPaint().setColor(targetColor);
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable)background).setColor(targetColor);
        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable)background).setColor(targetColor);
        }
    }

    public Boolean isOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        this.isOpen = open;
    }

    public void addOrRemoveContainedStat(Stat stat)
    {
        if (containedStats.contains(stat))
        {
            containedStats.remove(stat);
        }
        else
        {
            containedStats.add(stat);
        }
    }

    public void addOrRemovePickedStat(Stat stat)
    {
        if (pickedStats.contains(stat))
        {
            pickedStats.remove(stat);
        }
        else
        {
            pickedStats.add(stat);
        }

        updateSelectedStatLabel();
    }

    private void updateSelectedStatLabel()
    {
        if (getPickedStatsCount() > 0)
        {
            txtSelectedStats.setVisibility(VISIBLE);
            txtSelectedStats.setText(getSelectedStatsString());
        }
        else
        {
            txtSelectedStats.setVisibility(GONE);
        }
    }

    public int getPickedStatsCount() {
        return pickedStats.size();
    }

    public boolean shouldContain(Stat stat)
    {
        return containedStats.contains(stat);
    }

    public String getSelectedStatsString()
    {
        Iterator iterator = pickedStats.iterator();

        String result = "(";

        while (iterator.hasNext()) {
            Stat stat = (Stat) iterator.next();

            result = result.concat(String.valueOf(stat.getName()));

            if (iterator.hasNext()) {
                result = result.concat(", ");
            }
        }

        result = result.concat(")");

        return result;
    }

    public LinearLayout getPanelStats()
    {
        return panelStats;
    }

    public void setPanelStats(LinearLayout panelStats)
    {
        this.panelStats = panelStats;
    }

    public void setTxtSelectedStats(TextView txtSelectedStats)
    {
        this.txtSelectedStats = txtSelectedStats;
    }

    public void setPanelColor() {
        int tan = ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.color_tan);
        int gray = ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.color_light_gray);

        int selectedStatCount = getPickedStatsCount();

        if (selectedStatCount == 1)
        {
            Stat stat = pickedStats.get(0);
            setBackgroundDrawableColor(stat.getColor());
            txtSelectedStats.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.color_white));
            lblSelectedStats.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.color_white));
        }
        else if (selectedStatCount > 1)
        {
            setBackgroundDrawableColor(tan);
            txtSelectedStats.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.color_white));
            lblSelectedStats.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.color_white));
        }
        else if (selectedStatCount == 0)
        {
            setBackgroundDrawableColor(gray);
            txtSelectedStats.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.color_black));
            lblSelectedStats.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.color_black));
        }
    }

    public void setLblSelectedStats(TextView lblSelectedStats)
    {
        this.lblSelectedStats = lblSelectedStats;
    }
}
