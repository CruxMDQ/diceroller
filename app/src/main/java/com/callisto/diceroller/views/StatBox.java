package com.callisto.diceroller.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.callisto.diceroller.R;
import com.callisto.diceroller.bus.BusProvider;
import com.callisto.diceroller.bus.events.DicePoolChangedEvent;
import com.callisto.diceroller.bus.events.StatChangedEvent;
import com.callisto.diceroller.bus.events.StatEditionRequestedEvent;
import com.callisto.diceroller.bus.events.DerivedStatUpdatedEvent;
import com.callisto.diceroller.interfaces.RefreshingView;
import com.callisto.diceroller.interfaces.StatContainer;
import com.callisto.diceroller.persistence.objects.Stat;
import com.squareup.otto.Subscribe;

public class StatBox
    extends LinearLayout
    implements
    RefreshingView,
    StatContainer
{

    private TextView lblStat;

    private LinearLayout panelValue;

    private int colorSelected;

    private boolean isSelected = false;

    private long statId;
    private int statValue;
    private String statBoxName;

    private boolean isEditionAllowed = true;

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

        statValue = args.getInteger(R.styleable.StatBox_statValue, 0);

        isEditionAllowed = args.getBoolean(R.styleable.StatBox_isEditionAllowed, true);

        args.recycle();

        performInflation(context);
    }

    public StatBox(Context context)
    {
        super(context);

        performInflation(context);
    }

    private void performInflation(Context context)
    {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        inflateLayout();

        resolveViews();

        setBackgroundColor(ContextCompat.getColor(context, R.color.color_light_gray));

        setOnClickListener(v ->
            toggleSelected(context)
        );

        setOnLongClickListener(v ->
        {
            if (isEditionAllowed())
            {
                postStatEditionRequest(v.getId(), statId, statBoxName);
            }
            return true;
        });

//        subscribeToEvents();
    }

    public void performValueChange(int statValue)
    {
        setValue(statValue);

        performViewRefresh();

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
        statId = stat.getId();
        statValue = stat.getValue();
        colorSelected = stat.getColor();

        setName(statBoxName);
        setValue(statValue);

        postStatChange();

        performViewRefresh();
    }

    public void postDicePoolChange()
    {
        BusProvider.getInstance().post(new DicePoolChangedEvent(
            lblStat.getText().toString(),
            statValue,
            colorSelected
        ));
    }

    public void postStatChange()
    {
        BusProvider.getInstance().post(new StatChangedEvent(statId, statBoxName, statValue));
    }

    private void postStatEditionRequest(int viewId, long statId, String statBoxName)
    {
        Log.d("Stat tracking", "View: " + viewId + ", statId: " + statId + ", stat: " + statBoxName + ", value: " + statValue);

        BusProvider.getInstance().post(new StatEditionRequestedEvent(viewId, statId, statBoxName));
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

        performViewRefresh();

        postDicePoolChange();
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

    public void performViewRefresh() {
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

    @Subscribe public void updateStatValue(DerivedStatUpdatedEvent event)
    {
       try
        {
//            if (event.name.equals(statBoxName))
            if (event.statId == statId)
            {
                Log.d("Stat box", "DerivedStatUpdatedEvent captured, event statId = " + event.statId + ", container statId = " + statId);

                this.statValue = event.value;

                performViewRefresh();
            }
        }
        catch (NullPointerException ignored)
        {

        }
    }

    public void subscribeToEvents()
    {
        BusProvider.getInstance().register(this);
    }

    @Override
    public void unsubscribeFromEvents()
    {
        BusProvider.getInstance().unregister(this);
    }

    public String getStatBoxName()
    {
        return statBoxName;
    }
}
