package com.callisto.diceroller.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.callisto.diceroller.R;
import com.callisto.diceroller.interfaces.StatObserver;

public class StatBox extends LinearLayout {

    private TextView lblStat;

    private LinearLayout panelValue;

    private int currentValue;

    private boolean isSelected = false;

    private StatObserver observer;

    public StatBox(final Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray args = context.obtainStyledAttributes(
            attrs,
            R.styleable.StatBox,
            0,
            0);

        final String statName = args.getString(R.styleable.StatBox_statName);
        final String statValue = args.getString(R.styleable.StatBox_statValue);

        args.recycle();

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        inflateLayout();

        resolveViews();

        setName(statName);
        setValue(statValue);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSelected(context);
            }
        });

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                observer.spawnStatEditionDialog(v.getId(), statName);

                return true;
            }
        });
    }

    public void setValue(String statValue) {
        currentValue = Integer.parseInt(statValue);
        refreshPointsPanel();
    }

    private void setName(String statName) {
        lblStat.setText(statName);
    }

    private void toggleSelected(Context context) {
        if (isSelected) {
            setBackgroundColor(ContextCompat.getColor(context, R.color.color_white));
            lblStat.setTextColor(ContextCompat.getColor(context, R.color.color_black));
            lblStat.setTypeface(Typeface.DEFAULT);
            isSelected = false;

            changeDicePool();
        } else {
            setBackgroundColor(ContextCompat.getColor(context, R.color.color_stat_box_selected));
            lblStat.setTextColor(ContextCompat.getColor(context, R.color.color_white));
            lblStat.setTypeface(Typeface.DEFAULT_BOLD);
            isSelected = true;

            changeDicePool();
        }
    }

    private void changeDicePool() {
        observer.changeDicePool(
            lblStat.getText().toString(),
            currentValue);
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

    public void setObserver(StatObserver observer) {
        this.observer = observer;
    }

    private void refreshPointsPanel() {
        panelValue.removeAllViews();

        for (int i = 0; i < currentValue; i++) {
            RadioButton rdb = new RadioButton(getContext());

            rdb.setChecked(true);

            rdb.setButtonDrawable(ContextCompat.getDrawable(getContext(), R.drawable.selector_points));

            panelValue.addView(rdb);
        }
    }
}
