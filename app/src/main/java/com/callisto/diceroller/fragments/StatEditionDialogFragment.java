package com.callisto.diceroller.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.callisto.diceroller.R;
import com.callisto.diceroller.interfaces.StatEditionCallbackHandler;
import com.callisto.diceroller.tools.Constants;
import com.callisto.diceroller.tools.TypefaceSpanBuilder;

public class StatEditionDialogFragment
    extends DialogFragment
{
    private long statId;
    int containerId;
    String statName;

    private StatEditionCallbackHandler callbackHandler;

    public StatEditionDialogFragment()
    {
    }

    public static StatEditionDialogFragment newInstance(int containerId, long statId, String statName)
    {
        StatEditionDialogFragment myFragment = new StatEditionDialogFragment();

        Bundle args = new Bundle();

        args.putInt(Constants.Parameters.ID.getText(), containerId);
        args.putLong(Constants.Parameters.STAT_ID.getText(), statId);
        args.putString(Constants.Parameters.STAT_NAME.getText(), statName);

        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        containerId = getArguments().getInt(Constants.Parameters.ID.getText());

        statId = getArguments().getLong(Constants.Parameters.STAT_ID.getText());

        statName = getArguments().getString(Constants.Parameters.STAT_NAME.getText());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        View view = getActivity().getLayoutInflater().inflate(
            R.layout.dialog_stat_edition, new LinearLayout(getActivity()), false);

        // Retrieve layout elements
        Button btnSetValue = view.findViewById(R.id.btnSetValue);
        EditText inputNewStatValue = view.findViewById(R.id.inputNewStatValue);
        TextView txtNewStatValue = view.findViewById(R.id.txtNewStatValue);

//        txtNewStatValue.setText(statName);
        TypefaceSpanBuilder.setTypefacedTitle(
            txtNewStatValue,
            statName,
            Constants.Fonts.CEZANNE.getText(),
            Constants.Values.STAT_CONTAINER_FONT_TITLE.getValue()
        );

        btnSetValue.setOnClickListener(v -> {
            int newValue = Integer.parseInt(inputNewStatValue.getText().toString());

            callbackHandler.updateContainer(containerId, statId, newValue);

            dismiss();
        });

        // Build dialog
        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        builder.setContentView(view);
        return builder;
    }

    public void setCallbackHandler(StatEditionCallbackHandler handler)
    {
        this.callbackHandler = handler;
    }
}
