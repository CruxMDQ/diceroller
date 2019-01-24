package com.callisto.diceroller.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.callisto.diceroller.R;
import com.callisto.diceroller.interfaces.StatObserver;
import com.callisto.diceroller.views.StatBox;

public class CharacterSheetFragment
    extends BaseFragment
    implements StatObserver {

    StatBox statIntelligence;
    StatBox statWits;
    StatBox statResolve;
    StatBox statStrength;
    StatBox statDexterity;
    StatBox statStamina;
    StatBox statPresence;
    StatBox statManipulation;
    StatBox statComposure;

    @Override
    protected int getLayout() {
        return R.layout.fragment_char_sheet;
    }

    @Override
    public void onViewCreated
        (android.view.View view,
         @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        observeBoxes();
    }

    protected void findViews() {
        statIntelligence = rootView.findViewById(R.id.statIntelligence);
        statWits = rootView.findViewById(R.id.statWits);
        statResolve = rootView.findViewById(R.id.statResolve);
        statStrength = rootView.findViewById(R.id.statStrength);
        statDexterity = rootView.findViewById(R.id.statDexterity);
        statStamina = rootView.findViewById(R.id.statStamina);
        statPresence = rootView.findViewById(R.id.statPresence);
        statManipulation = rootView.findViewById(R.id.statManipulation);
        statComposure = rootView.findViewById(R.id.statComposure);
    }

    public void observeBoxes() {
        statIntelligence.setObserver(this);
        statWits.setObserver(this);
        statResolve.setObserver(this);
        statStrength.setObserver(this);
        statDexterity.setObserver(this);
        statStamina.setObserver(this);
        statPresence.setObserver(this);
        statManipulation.setObserver(this);
        statComposure.setObserver(this);
    }

    @Override
    public void spawnStatEditionDialog(final int id, String statName) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Stat edition");
        dialog.setMessage("Set new value for " + statName);

        final EditText input = new EditText(getContext());

        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        dialog.setView(input);

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newValue = input.getText().toString();

                StatBox statBox = rootView.findViewById(id);

                statBox.setValue(newValue);
            }
        });

        dialog.show();
    }
}
