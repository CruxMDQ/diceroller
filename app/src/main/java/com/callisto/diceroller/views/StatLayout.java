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
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.callisto.diceroller.R;
import com.callisto.diceroller.bus.BusProvider;
import com.callisto.diceroller.bus.events.PanelTappedEvent;
import com.callisto.diceroller.interfaces.RefreshingView;
import com.callisto.diceroller.persistence.objects.Stat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import io.realm.RealmList;

public class StatLayout
    extends UnfoldingLayout
    implements RefreshingView
{
    private LinearLayout panelMain;
    private LinearLayout panelContainer;

    private TextView labelTitle;
    private TextView labelSummary;

    private ArrayList<Stat> containedStats;
    private ArrayList<Stat> pickedStats;

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

        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);

        inflateLayout();

        resolveViews();

        performViewRefresh();
    }

    public StatLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);

        inflateLayout();

        resolveViews();

        TypedArray args = context.obtainStyledAttributes(
            attrs,
            R.styleable.StatLayout,
            0,
            0);

        init(args);

        args.recycle();

        performViewRefresh();
    }

    public StatLayout(Context context)
    {
        super(context);
        init(null);
    }

    @Override
    public LinearLayout getPanel()
    {
        return panelContainer;
    }

    private void init(@Nullable TypedArray args)
    {
        if (args != null)
        {
            isOpen = args.getBoolean(R.styleable.StatLayout_isOpen, false);
            labelTitle.setText(args.getString(R.styleable.StatLayout_title));

            showTitle(args.getBoolean(R.styleable.StatLayout_isTitleVisible, true));

            setOpen(isOpen);
        }
        else
        {
            setOpen(false);
        }

        containedStats = new ArrayList<>();
        pickedStats = new ArrayList<>();

        setOnClickListener(v ->
        {
            int id = v.getId();

            postPanelTapped(id);

            StatLayout t = (StatLayout) v;

            if (pickedStats.size() == 0)
            {
                if (!(t.isOpen()))
                {
                    toggleStatPanel
                        (
                            View.VISIBLE,
                            true,
                            R.color.color_light_gray
                        );
                }
                else
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
                }
                else
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

    private void resolveViews()
    {
        panelMain = findViewById(R.id.panelMain);
        panelContainer = findViewById(R.id.panelContainer);

        labelTitle = findViewById(R.id.labelTitle);
        labelSummary = findViewById(R.id.labelSummary);
    }

    private void inflateLayout()
    {
        inflate(this.getContext(), getLayout(), this);
    }

    private int getLayout()
    {
        return R.layout.view_stat_layout;
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
        Drawable background = panelMain.getBackground();

        if (background instanceof ShapeDrawable)
        {
            ((ShapeDrawable) background).getPaint().setColor(targetColor);
        }
        else if (background instanceof GradientDrawable)
        {
            ((GradientDrawable) background).setColor(targetColor);
        }
        else if (background instanceof ColorDrawable)
        {
            ((ColorDrawable) background).setColor(targetColor);
        }
    }

    public void addSelectableStat(Stat stat)
    {
        addContainedStat(stat);
        performViewRefresh();
        toggleStatPanel(GONE, false);
    }

    public void addSelectableStats(RealmList<Stat> stats)
    {
        for (Stat stat : stats)
        {
            addSelectableStat(stat);
        }
    }

    public void performViewRefresh()
    {
        updateSummaryLabel();
        refreshStatPanel();
    }

    @Override
    public void unsubscribeFromEvents()
    {
        final int childCount = panelContainer.getChildCount();

        for (int i = 0; i < childCount; i++)
        {
            StatBox statBox = (StatBox) panelContainer.getChildAt(i);

            statBox.unsubscribeFromEvents();
        }

        panelContainer.removeAllViews();
    }

    @Override
    public void subscribeToEvents()
    {
        refreshStatPanel();
    }

    private void addContainedStat(Stat stat)
    {
        containedStats.add(stat);
    }

    public void refreshStatPanel()
    {
        panelContainer.removeAllViews();

        for (Stat stat : containedStats)
        {
            StatBox statBox = new StatBox(getContext());

            statBox.setId(View.generateViewId());

            statBox.setStat(stat);

            statBox.subscribeToEvents();

            panelContainer.addView(statBox);
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

        updateSummaryLabel();
    }

    public void updateSummaryLabel()
    {
        labelSummary.setVisibility(VISIBLE);

        if (pickedStats.size() > 0)
        {
            labelSummary.setText(getSelectedStatsString());
        }
        else
        {
            labelSummary.setText(getStatSummary());
        }
    }

    public String getStatSummary()
    {
        Iterator iterator = containedStats.iterator();

        String result = "(";

        try
        {
            while (iterator.hasNext())
            {
                Stat stat = (Stat) iterator.next();

                result = result.concat(String.valueOf(stat.getName()));

                result = result.concat(" ");

                result = result.concat(String.valueOf(stat.getValue()));

                if (iterator.hasNext())
                {
                    result = result.concat(", ");
                }
            }
        }
        catch (NullPointerException e)
        {
            Log.e(this.getClass().getSimpleName(), "Null array?");
        }

        result = result.concat(")");

        return result;
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

    public boolean shouldContain(Stat stat)
    {
        return containedStats.contains(stat);
    }

    public void setPanelColor()
    {
        int tan = ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.color_tan);
        int gray = ContextCompat
            .getColor(Objects.requireNonNull(getContext()), R.color.color_light_gray);

        int selectedStatCount = pickedStats.size();

        if (selectedStatCount == 1)
        {
            Stat stat = pickedStats.get(0);
            int color = stat.getColor();
            setBackgroundDrawableColor(color);
            setTextViewsColor(
                ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.color_white));
        }
        else if (selectedStatCount > 1)
        {
            setBackgroundDrawableColor(tan);
            setTextViewsColor(
                ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.color_white));
        }
        else
        {
            setBackgroundDrawableColor(gray);
            setTextViewsColor(
                ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.color_black));
        }
    }

    private void setTextViewsColor(int color)
    {
        labelTitle.setTextColor(color);
        labelSummary.setTextColor(color);
    }

    public void showTitle(boolean isVisible)
    {
        if (isVisible)
        {
            labelTitle.setVisibility(VISIBLE);
        }
        else
        {
            labelTitle.setVisibility(GONE);
        }
    }

    private void deselectAll()
    {
        final int childCount = panelContainer.getChildCount();

        for (int i = 0; i < childCount; i++)
        {
            StatBox v = (StatBox) panelContainer.getChildAt(i);

            v.setSelectedForDicePool(getContext(), false);
        }
    }

    public void flush()
    {
        pickedStats.clear();

        deselectAll();

        setPanelColor();
    }
}
