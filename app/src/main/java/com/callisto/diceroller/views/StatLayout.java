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
import com.callisto.diceroller.bus.BusProvider;
import com.callisto.diceroller.bus.events.PanelTappedEvent;
import com.callisto.diceroller.persistence.objects.Stat;
import com.callisto.diceroller.interfaces.StatusObserver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class StatLayout
    extends UnfoldingLayout
{

    private ArrayList<Stat> containedStats;
    private ArrayList<Stat> pickedStats;

    private LinearLayout panelStats;

    private TextView lblSelectedStats;
    private TextView txtSummary;

    private StatusObserver observer;

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
        int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        TypedArray args = context.obtainStyledAttributes(
            attrs,
            R.styleable.StatLayout,
            0,
            0);

        init(args);

        args.recycle();
    }

    public StatLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        TypedArray args = context.obtainStyledAttributes(
            attrs,
            R.styleable.StatLayout,
            0,
            0);

        init(args);

        args.recycle();
    }

    public StatLayout(Context context)
    {
        super(context);
        init(null);
    }

    private void init(@Nullable TypedArray args)
    {
        if (args != null)
        {
            setOpen(args.getBoolean(R.styleable.StatLayout_isOpen, false));
        } else
        {
            setOpen(false);
        }

        containedStats = new ArrayList<>();
        pickedStats = new ArrayList<>();

        setOnClickListener(v ->
        {
            postPanelTapped(v.getId());

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
            } else
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
        });
    }

    private void postPanelTapped(int id)
    {
        BusProvider.getInstance().post(new PanelTappedEvent(id));
    }

    private void toggleStatPanel(
        int visible,
        boolean isOpen,
        int color)
    {
        toggleStatPanel(visible, isOpen);
        int targetColor = ContextCompat.getColor(
            Objects.requireNonNull(getContext()), color);

        setBackgroundDrawableColor(targetColor);
    }

    private void setBackgroundDrawableColor(int targetColor)
    {
        Drawable background = getBackground();
        if (background instanceof ShapeDrawable)
        {
            ((ShapeDrawable) background).getPaint().setColor(targetColor);
        } else if (background instanceof GradientDrawable)
        {
            ((GradientDrawable) background).setColor(targetColor);
        } else if (background instanceof ColorDrawable)
        {
            ((ColorDrawable) background).setColor(targetColor);
        }
    }

    public void addOrRemoveContainedStat(Stat stat)
    {
        if (containedStats.contains(stat))
        {
            containedStats.remove(stat);
        } else
        {
            containedStats.add(stat);
        }
    }

    public void addOrRemovePickedStat(Stat stat)
    {
        if (pickedStats.contains(stat))
        {
            pickedStats.remove(stat);
        } else
        {
            pickedStats.add(stat);
        }

        updateSelectedStatLabel();
    }

    private void updateSelectedStatLabel()
    {
        if (getPickedStatsCount() > 0)
        {
            txtSummary.setVisibility(VISIBLE);
            txtSummary.setText(getSelectedStatsString());
        } else
        {
            txtSummary.setVisibility(GONE);
        }
    }

    public int getPickedStatsCount()
    {
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

        while (iterator.hasNext())
        {
            Stat stat = (Stat) iterator.next();

            result = result.concat(String.valueOf(stat.getName()));

            if (iterator.hasNext())
            {
                result = result.concat(", ");
            }
        }

        result = result.concat(")");

        return result;
    }

    @Override
    public LinearLayout getPanel()
    {
        return panelStats;
    }

    public void setPanelStats(LinearLayout panelStats)
    {
        this.panelStats = panelStats;
    }

    public void setTxtSummary(TextView txtSummary)
    {
        this.txtSummary = txtSummary;
    }

    public void setPanelColor()
    {
        int tan = ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.color_tan);
        int gray = ContextCompat
            .getColor(Objects.requireNonNull(getContext()), R.color.color_light_gray);

        int selectedStatCount = getPickedStatsCount();

        if (selectedStatCount == 1)
        {
            Stat stat = pickedStats.get(0);
            setBackgroundDrawableColor(stat.getColor());
            setTextViewsColor(
                ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.color_white));
        } else if (selectedStatCount > 1)
        {
            setBackgroundDrawableColor(tan);
            setTextViewsColor(
                ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.color_white));
        } else if (selectedStatCount == 0)
        {
            setBackgroundDrawableColor(gray);
            setTextViewsColor(
                ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.color_black));
        }
    }

    private void setTextViewsColor(int color)
    {
        txtSummary.setTextColor(color);
        lblSelectedStats.setTextColor(color);
    }

    public void setLblSelectedStats(TextView lblSelectedStats)
    {
        this.lblSelectedStats = lblSelectedStats;
    }

    public void setObserver(StatusObserver observer)
    {
        this.observer = observer;
    }
}
