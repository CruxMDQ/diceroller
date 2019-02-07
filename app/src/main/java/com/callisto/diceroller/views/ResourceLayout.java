package com.callisto.diceroller.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.callisto.diceroller.R;
import com.callisto.diceroller.bus.BusProvider;
import com.callisto.diceroller.bus.events.StatUpdatedEvent;
import com.callisto.diceroller.interfaces.StatContainer;
import com.callisto.diceroller.interfaces.ViewWatcher;
import com.callisto.diceroller.persistence.objects.Stat;
import com.callisto.diceroller.tools.Constants;
import com.callisto.diceroller.tools.TypefaceSpanBuilder;
import com.squareup.otto.Subscribe;

public class ResourceLayout
    extends UnfoldingLayout
    implements StatContainer
{
    private Boolean isOpen;

    private Boolean boxesHaveMultipleStates;

    private String statName;
    private int statValue;

    private LinearLayout boxContainer;

    private TextView lblTitle;

    private ViewWatcher viewWatcher;

    public ResourceLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        TypedArray args = context.obtainStyledAttributes(
            attrs,
            R.styleable.ResourceLayout,
            0,
            0);

        init(args);

        args.recycle();

        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);

        inflateLayout();

        resolveViews();
    }

    private void resolveViews()
    {
        lblTitle = findViewById(R.id.lblTitle);

        boxContainer = findViewById(R.id.boxContainer);
    }

    private void inflateLayout()
    {
        inflate(this.getContext(), getLayout(), this);
    }

    public ResourceLayout setViewWatcher(ViewWatcher watcher)
    {
        this.viewWatcher = watcher;
        this.viewWatcher.setStatOnView(this.getTag());

        subscribeToEvents();

        return this;
    }

    private void subscribeToEvents()
    {
        BusProvider.getInstance().register(this);
    }

    @Subscribe
    public void updateStatValue(StatUpdatedEvent event)
    {
        try
        {
            if (event.name.equals(this.statName))
            {
                this.statValue = event.value;

                refreshValuePanel();
            }
        }
        catch (NullPointerException ignored)
        {

        }
    }

    private int getLayout()
    {
        return R.layout.view_resource_container;
    }

    public Boolean isOpen()
    {
        return isOpen;
    }

    public void setOpen(Boolean open)
    {
        isOpen = open;
    }

    public Boolean boxesHaveMultipleStates()
    {
        return boxesHaveMultipleStates;
    }

    public void setBoxesHaveMultipleStates(Boolean boxesHaveMultipleStates)
    {
        this.boxesHaveMultipleStates = boxesHaveMultipleStates;
    }

    public String getStatName()
    {
        return statName;
    }

    public void setStatName(String statName)
    {
        this.statName = statName;
    }

    public Integer getValue()
    {
        return statValue;
    }

    @Override
    public void setValue(int statValue)
    {
        this.statValue = statValue;
    }

    @Override
    public void setStat(Stat stat)
    {
        statName = stat.getName();
        statValue = stat.getValue();

        lblTitle.setText(statName);

        TypefaceSpanBuilder.setTypefacedTitle(
            lblTitle,
            statName,
            Constants.Fonts.CEZANNE.getText(),
            Constants.Values.STAT_CONTAINER_FONT_TITLE.getValue()
        );

        refreshValuePanel();
    }

    public LinearLayout getPanel()
    {
        return boxContainer;
    }

    private void init(@Nullable TypedArray args)
    {
        if (args != null)
        {
            isOpen = args.getBoolean(R.styleable.ResourceLayout_isPanelOpen, false);
        } else
        {
            isOpen = false;
        }

        boxesHaveMultipleStates = args.getBoolean(R.styleable.ResourceLayout_boxesHaveMultipleStates, true);

        statName = args.getString(R.styleable.ResourceLayout_panelName);
        statValue = args.getInteger(R.styleable.ResourceLayout_panelValue, 5);

        setOnClickListener(v ->
        {
            if (!isOpen)
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
        });

        setTag(args.getString(R.styleable.ResourceLayout_panelName));
    }

    public void performValueChange(int value)
    {
        setValue(value);

        refreshValuePanel();
    }

    @Override
    public void refreshValuePanel()
    {
        boxContainer.removeAllViews();

        for (int i = 0; i < statValue; i++)
        {
            ResourceBox resourceBox = new ResourceBox(getContext());

            resourceBox.setHasMultipleStates(boxesHaveMultipleStates);

            resourceBox.init();

            boxContainer.addView(resourceBox);
        }
    }

    public Parcelable onSaveInstanceState()
    {
        Parcelable superState = super.onSaveInstanceState();

        SavedState ss = new SavedState(superState);

        ss.isOpen = isOpen;
        ss.name = statName;
        ss.value = statValue;

        return ss;
    }

    public void onRestoreInstanceState(Parcelable state)
    {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setOpen(ss.isOpen);
        setStatName(ss.name);
        setValue(ss.value);
        requestLayout();
        refreshValuePanel();
    }

    static class SavedState extends BaseSavedState
    {
        boolean isOpen;
        String name;
        int value;

        SavedState(Parcelable superState)
        {
            super(superState);
        }

        private SavedState(Parcel in)
        {
            super(in);
            isOpen = in.readByte() != 0;
            name = in.readString();
            value = in.readInt();
        }

        public void writeToParcel(Parcel out, int flags)
        {
            super.writeToParcel(out, flags);
            out.writeByte((byte) (isOpen ? 1 : 0));
            out.writeString(name);
            out.writeInt(value);
        }

        @NonNull
        public String toString()
        {
            return "ResourceLayout.SavedState{"
                + Integer.toHexString(System.identityHashCode(this))
                + " isOpen=" + isOpen + ","
                + " name=" + name + ","
                + " value=" + value + "}";
        }

        public static final Parcelable.Creator<SavedState> CREATOR =
            new Parcelable.Creator<SavedState>()
            {

                @Override
                public SavedState createFromParcel(Parcel source)
                {
                    return new SavedState(source);
                }

                @Override
                public SavedState[] newArray(int size)
                {
                    return new SavedState[0];
                }
            };
    }
}
