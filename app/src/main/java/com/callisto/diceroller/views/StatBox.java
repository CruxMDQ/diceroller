package com.callisto.diceroller.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.callisto.diceroller.R;
import com.callisto.diceroller.interfaces.StatObserver;

public class StatBox extends LinearLayout {

    private TextView lblStat;
    private TextView txtStat;

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
        txtStat.setText(statValue);
    }

    private void setName(String statName) {
        lblStat.setText(statName);
    }

    private void toggleSelected(Context context) {
        if (isSelected) {
            setBackgroundColor(ContextCompat.getColor(context, R.color.color_white));
            lblStat.setTextColor(ContextCompat.getColor(context, R.color.color_black));
            txtStat.setTextColor(ContextCompat.getColor(context, R.color.color_black));
            lblStat.setTypeface(Typeface.DEFAULT);
            txtStat.setTypeface(Typeface.DEFAULT);
            isSelected = false;

            changeDicePool();
        } else {
            setBackgroundColor(ContextCompat.getColor(context, R.color.color_stat_box_selected));
            lblStat.setTextColor(ContextCompat.getColor(context, R.color.color_white));
            txtStat.setTextColor(ContextCompat.getColor(context, R.color.color_white));
            lblStat.setTypeface(Typeface.DEFAULT_BOLD);
            txtStat.setTypeface(Typeface.DEFAULT_BOLD);
            isSelected = true;

            changeDicePool();
        }
    }

    private void changeDicePool() {
        observer.changeDicePool(
            lblStat.getText().toString(),
            Integer.parseInt(txtStat.getText().toString()));
    }

    private void inflateLayout() {
        inflate(this.getContext(), getLayout(), this);
    }

    private int getLayout() {
        return R.layout.view_stat_box;
    }

    private void resolveViews() {
        lblStat = findViewById(R.id.lblStat);
        txtStat = findViewById(R.id.txtStat);
    }

    public void setObserver(StatObserver observer) {
        this.observer = observer;
    }
}
