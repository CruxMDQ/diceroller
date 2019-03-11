package com.callisto.diceroller.fragments.contestedcheck;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.callisto.diceroller.R;
import com.callisto.diceroller.fragments.XmlFragment;
import com.callisto.diceroller.fragments.adapters.CharacterAdapter;
import com.callisto.diceroller.fragments.adapters.StatAdapter;
import com.callisto.diceroller.persistence.objects.Character;
import com.callisto.diceroller.persistence.objects.Stat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActionContestFragment
    extends XmlFragment
{
    private Spinner pickerCharacter;
    private Spinner pickerStat;
    private TextView txtDicePool;
    private FloatingActionButton fabClearStats;
    private EditText inputRerollThreshold;

    private ActionContestPresenter presenter;

    @Override
    protected int getLayout()
    {
        return R.layout.dialog_opposed_check_part;
    }

    @Override
    protected void findViews()
    {
        pickerCharacter = rootView.findViewById(R.id.pickerCharacter);
        pickerStat = rootView.findViewById(R.id.pickerStat);
        txtDicePool = rootView.findViewById(R.id.txtPerformerDicePool);
        fabClearStats = rootView.findViewById(R.id.fabClearPerformerStats);
        inputRerollThreshold = rootView.findViewById(R.id.inputPerformerRerollThreshold);
    }

    private void setUpFAB()
    {
        fabClearStats.setOnClickListener(v ->
        {
            presenter.clearSelections();

            updateDicePoolString(presenter.getSelectedStats());
        });
    }

    private void setUpCharacterPicker()
    {
        CharacterAdapter adapter = new CharacterAdapter(presenter.getCharacters());

        pickerCharacter.setAdapter(adapter);

        pickerCharacter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Character selectedCharacter = (Character) adapter.getItem(position);

                presenter.setSelectedCharacter(selectedCharacter);

                setUpStatsPicker(presenter.getCharacterStats());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void setUpStatsPicker(List<Stat> characterStats)
    {
        StatAdapter adapter = new StatAdapter(characterStats);

        pickerStat.setAdapter(adapter);

        pickerStat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Stat selectedStat = (Stat) adapter.getItem(position);

                presenter.addSelectedStat(selectedStat);

                presenter.performRoll(getDiceRerollThreshold());

                updateDicePoolString(presenter.getSelectedStats());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void updateDicePoolString(@NonNull List<Stat> stats)
    {
        String statList = "";

        Iterator iterator = stats.iterator();

        while (iterator.hasNext())
        {
            Stat stat = (Stat) iterator.next();

            statList = statList.concat(stat.getName());

            if (iterator.hasNext())
            {
                statList = statList.concat(" + ");
            }
        }

        txtDicePool.setText(statList);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        presenter = new ActionContestPresenter(this);

        setUpCharacterPicker();

        setUpFAB();
    }

    private int getDiceRerollThreshold()
    {
        return Integer.parseInt(inputRerollThreshold.getText().toString());
    }

    public ArrayList<Integer> getRolls()
    {
        return presenter.getRolls();
    }

    public Integer getSuccesses()
    {
        return presenter.getSuccesses();
    }
}
