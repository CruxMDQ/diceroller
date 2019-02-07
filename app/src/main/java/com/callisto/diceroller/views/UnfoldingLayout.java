package com.callisto.diceroller.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

abstract public class UnfoldingLayout extends LinearLayout
{
    protected Boolean isOpen;

    public UnfoldingLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public UnfoldingLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public UnfoldingLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public UnfoldingLayout(Context context)
    {
        super(context);
    }

    public void toggleStatPanel(int visible, boolean isOpen)
    {
        getPanel().setVisibility(visible);
        setOpen(isOpen);
    }

    public void setOpen(Boolean open)
    {
        this.isOpen = open;
    }

    public Boolean isOpen()
    {
        return isOpen;
    }

    public abstract LinearLayout getPanel();
}
