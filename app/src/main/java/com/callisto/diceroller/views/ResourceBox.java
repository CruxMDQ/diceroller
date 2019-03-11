package com.callisto.diceroller.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.callisto.diceroller.R;

public class ResourceBox
    extends android.support.v7.widget.AppCompatCheckBox
{
    static private final int UNSCATHED = 0;

    static private final int BASHING = 1;

    static private final int LETHAL = 2;

    static private final int AGGRAVATED = 3;

    static private final int EMPTY = 4;

    static private final int FULL = 5;

    private int state;

    private boolean hasMultipleStates;

    private final OnCheckedChangeListener privateListener = (buttonView, isChecked) ->
    {
        switch(state)
        {
            case UNSCATHED:
            {
                setState(BASHING);
                break;
            }
            case BASHING:
            {
                setState(LETHAL);
                break;
            }
            case LETHAL:
            {
                setState(AGGRAVATED);
                break;
            }
            case AGGRAVATED:
            {
                setState(UNSCATHED);
                break;
            }
            case EMPTY:
            {
                setState(FULL);
                break;
            }
            case FULL:
            {
                setState(EMPTY);
                break;
            }
        }
    };

    private OnCheckedChangeListener clientListener;

    private boolean restoring;

    public ResourceBox(Context context)
    {
        super(context);
        if (!isInEditMode())
        {
            init();
        }
    }

    public ResourceBox(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        if (!isInEditMode())
        {
            TypedArray args = context.obtainStyledAttributes(
                attrs,
                R.styleable.ResourceBox
            );

            init(args);
        }
    }

    public ResourceBox(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode())
        {
            TypedArray args = context.obtainStyledAttributes(
                attrs,
                R.styleable.ResourceBox
            );

            init(args);
        }
    }

    private void setState(int state)
    {
        if (!this.restoring && this.state != state)
        {
            this.state = state;

            if (this.clientListener != null)
            {
                this.clientListener.onCheckedChanged(this, this.isChecked());
            }

            updateBtn();
        }
    }

    public void setOnCheckedChangeListener(@Nullable OnCheckedChangeListener listener)
    {
        if (this.privateListener != listener)
        {
            this.clientListener = listener;
        }

        super.setOnCheckedChangeListener(privateListener);
    }

    public Parcelable onSaveInstanceState()
    {
        Parcelable superState = super.onSaveInstanceState();

        SavedState ss = new SavedState(superState);

        ss.state = state;
        ss.hasMultipleStates = hasMultipleStates;

        return ss;
    }

    public void onRestoreInstanceState(Parcelable state)
    {
        this.restoring = true;
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setState(ss.state);
        setHasMultipleStates(ss.hasMultipleStates);
        requestLayout();
        this.restoring = false;
    }

    void init()
    {
        if (hasMultipleStates)
        {
            state = UNSCATHED;
        }
        else
        {
            state = EMPTY;
        }
        updateBtn();
        setOnCheckedChangeListener(this.privateListener);
    }

    private void init(TypedArray args)
    {
        hasMultipleStates = args.getBoolean(R.styleable.ResourceBox_hasMultipleStates, false);

        if (hasMultipleStates)
        {
            state = UNSCATHED;
        }
        else
        {
            state = EMPTY;
        }
        updateBtn();
        setOnCheckedChangeListener(this.privateListener);
    }

    void updateBtn()
    {
        int btnDrawable = R.drawable.ic_health_box_unscathed;

        switch(state)
        {
            case UNSCATHED:
            {
                btnDrawable = R.drawable.ic_health_box_unscathed;
                break;
            }
            case BASHING:
            {
                btnDrawable = R.drawable.ic_health_box_bashing;
                break;
            }
            case LETHAL:
            {
                btnDrawable = R.drawable.ic_health_box_lethal;
                break;
            }
            case AGGRAVATED:
            {
                btnDrawable = R.drawable.ic_health_box_aggravated;
                break;
            }
            case EMPTY:
            {
                btnDrawable = R.drawable.ic_resource_box_empty;
                break;
            }
            case FULL:
            {
                btnDrawable = R.drawable.ic_resource_box_full;
                break;
            }
        }

        setButtonDrawable(btnDrawable);
    }

    public boolean hasMultipleStates()
    {
        return hasMultipleStates;
    }

    public void setHasMultipleStates(boolean hasMultipleStates)
    {
        this.hasMultipleStates = hasMultipleStates;
    }

    static class SavedState extends BaseSavedState
    {
        int state;
        boolean hasMultipleStates;

        SavedState(Parcelable superState)
        {
            super(superState);
        }

        private SavedState(Parcel in)
        {
            super(in);
            state = in.readInt();
            hasMultipleStates = in.readByte() != 0;
        }

        public void writeToParcel(Parcel out, int flags)
        {
            super.writeToParcel(out, flags);
            out.writeValue(state);
            out.writeByte((byte) (hasMultipleStates ? 1 : 0));
        }

        @NonNull
        public String toString()
        {
            return "HealthBox.SavedState{"
                + Integer.toHexString(System.identityHashCode(this))
                + " state=" + state + "}";
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
