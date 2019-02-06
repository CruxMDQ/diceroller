package com.callisto.diceroller.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.callisto.diceroller.R;
import com.callisto.diceroller.beans.Stat;
import com.callisto.diceroller.bus.BusProvider;
import com.callisto.diceroller.bus.events.StatChangedEvent;
import com.callisto.diceroller.bus.events.StatUpdatedEvent;
import com.callisto.diceroller.interfaces.StatContainer;
import com.callisto.diceroller.interfaces.ViewWatcher;
import com.squareup.otto.Subscribe;

public class StatBox
    extends LinearLayout
    implements
    StatContainer
{

    private TextView lblStat;

    private LinearLayout panelValue;

    private int colorSelected;

    private boolean isSelected = false;

    private int statValue;
    private String statBoxName;

    private boolean isEditionAllowed = true;

    private ViewWatcher viewWatcher;

    public StatBox(final Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray args = context.obtainStyledAttributes(
            attrs,
            R.styleable.StatBox,
            0,
            0);

        colorSelected = args.getColor(
            R.styleable.StatBox_colorSelected,
            ContextCompat.getColor(
                getContext(),
                R.color.color_purple_dark)
        );

        setTag(args.getString(R.styleable.StatBox_statName));
        statBoxName = args.getString(R.styleable.StatBox_statName);

        colorSelected = args.getColor(
            R.styleable.StatBox_colorSelected,
            ContextCompat.getColor(
                getContext(),
                R.color.color_purple_dark)
        );

        statValue = Integer.parseInt(args.getString(R.styleable.StatBox_statValue));

        isEditionAllowed = args.getBoolean(R.styleable.StatBox_isEditionAllowed, true);

        args.recycle();

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        inflateLayout();

        resolveViews();

        setBackgroundColor(ContextCompat.getColor(context, R.color.color_light_gray));

        setOnClickListener(v ->
        {
//                if (isEditionAllowed())
//                {
                toggleSelected(context);
//                }
        });

        setOnLongClickListener(v ->
        {
            if (isEditionAllowed())
            {
                viewWatcher.spawnStatEditionDialog(v.getId(), statBoxName);
            }
            return true;
        });
    }

    public void performStatChange(String statValue)
    {
        setValue(statValue);

        refreshPointsPanel();

        postStatChange();
    }

    public void setValue(String statValue) {
        setValue(Integer.parseInt(statValue));
    }

    @Override
    public void setValue(int statValue) {
        this.statValue = statValue;
    }

    @Override
    public void setStat(Stat stat)
    {
        statBoxName = stat.getName();
        statValue = stat.getValue();

        setName(statBoxName);
        setValue(statValue);

        postStatChange();

        refreshPointsPanel();
    }

    public void postStatChange()
    {
        BusProvider.getInstance().post(new StatChangedEvent(statBoxName, statValue));
    }

    private void setName(String statName) {
        lblStat.setText(statName);
    }

    private void toggleSelected(Context context)
    {
        if (isSelected)
        {
            setBackgroundColor(ContextCompat.getColor(context, R.color.color_light_gray));
            lblStat.setTextColor(ContextCompat.getColor(context, R.color.color_black));
            lblStat.setTypeface(Typeface.DEFAULT);

            isSelected = false;
        }
        else
        {
            setBackgroundColor(colorSelected);
            lblStat.setTextColor(ContextCompat.getColor(context, R.color.color_white));
            lblStat.setTypeface(Typeface.DEFAULT_BOLD);

            isSelected = true;
        }

        refreshPointsPanel();

        changeDicePool();
    }

    private void changeDicePool() {
        viewWatcher.changeDicePool(
            lblStat.getText().toString(),
            statValue,
            colorSelected);
    }

    private void inflateLayout() {
        inflate(this.getContext(), getLayout(), this);
    }

    private int getLayout() {
        return R.layout.view_stat_box;
    }

    private void resolveViews() {
        lblStat = findViewById(R.id.lblStat);

        panelValue = findViewById(R.id.panelValue);
    }

    public StatBox setViewWatcher(ViewWatcher viewWatcher) {
        this.viewWatcher = viewWatcher;
        this.viewWatcher.setStatOnView(this.getTag());

        subscribeToEvents();

        return this;
    }

    public void refreshPointsPanel() {
        panelValue.removeAllViews();

        for (int i = 0; i < statValue; i++) {
            RadioButton rdb = new RadioButton(getContext());

            rdb.setChecked(!isSelected);

            rdb.setButtonDrawable(ContextCompat.getDrawable(getContext(), R.drawable.selector_points));

            panelValue.addView(rdb);
        }
    }

    public boolean isEditionAllowed()
    {
        return isEditionAllowed;
    }

    public StatBox setEditionAllowed(boolean editionAllowed)
    {
        isEditionAllowed = editionAllowed;

        return this;
    }

    @Subscribe public void updateStatValue(StatUpdatedEvent event)
    {
        try
        {
            if (event.name.equals(statBoxName))
            {
                this.statValue = event.value;

                refreshPointsPanel();
            }
        }
        catch (NullPointerException ignored)
        {

        }
    }

    private void subscribeToEvents()
    {
        BusProvider.getInstance().register(this);
    }
}
