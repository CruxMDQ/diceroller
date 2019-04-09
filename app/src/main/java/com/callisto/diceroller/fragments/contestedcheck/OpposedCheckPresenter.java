package com.callisto.diceroller.fragments.contestedcheck;

import android.support.annotation.NonNull;

import com.callisto.diceroller.R;
import com.callisto.diceroller.application.App;
import com.callisto.diceroller.bus.BusProvider;
import com.callisto.diceroller.bus.events.CheckForContestReadinessEvent;
import com.callisto.diceroller.persistence.objects.Maneuver;
import com.squareup.otto.Subscribe;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static com.callisto.diceroller.tools.Constants.Parameters.CHECK_SUBTYPE_COMBAT_UNARMED;

class OpposedCheckPresenter
{
    private final OpposedCheckModel model;
    private final OpposedCheckFragment view;

    private final CharacterContestFragment fragmentPerformer;
    private final CharacterContestFragment fragmentOpponent;

    OpposedCheckPresenter(OpposedCheckFragment view, CharacterContestFragment fragmentPerformer, CharacterContestFragment fragmentOpponent)
    {
        this.model = new OpposedCheckModel();
        this.view = view;
        this.fragmentPerformer = fragmentPerformer;
        this.fragmentOpponent = fragmentOpponent;

        BusProvider.getInstance().register(this);
    }

    void performResistedCheck()
    {
        int opponentResistance = Objects.requireNonNull(fragmentOpponent).getDicePoolSize();

        int performersDicePool = Objects.requireNonNull(fragmentPerformer).getDicePoolSize();

        Objects.requireNonNull(fragmentPerformer).performRoll(0 - opponentResistance);

        String successesPerformer =
            String.valueOf(App.getRes().getString(R.string.label_successes_report,
                Objects.requireNonNull(fragmentPerformer).getSuccesses()));

        String rollsPerformer = buildRollsString(fragmentPerformer.getRolls());

        view.spawnResistedCheckResultsDialog(
            performersDicePool,
            successesPerformer,
            rollsPerformer,
            opponentResistance
        );
    }

    void performContestedCheck()
    {
        Objects.requireNonNull(fragmentPerformer).performRoll();
        Objects.requireNonNull(fragmentOpponent).performRoll();

        String successesPerformer =
            String.valueOf(App.getRes().getString(R.string.label_successes_report,
                Objects.requireNonNull(fragmentPerformer).getSuccesses()));
        String successesOpponent =
            String.valueOf(App.getRes().getString(R.string.label_successes_report,
                Objects.requireNonNull(fragmentOpponent).getSuccesses()));

        String rollsPerformer = buildRollsString(fragmentPerformer.getRolls());
        String rollsOpponent = buildRollsString(fragmentOpponent.getRolls());

        view.spawnContestedCheckResultsDialog(
            successesPerformer, rollsPerformer,
            successesOpponent, rollsOpponent
        );
    }

    private String buildRollsString(@NonNull List<Integer> rolls)
    {
        String string = "";

        Iterator iterator = rolls.iterator();

        while (iterator.hasNext())
        {
            Integer roll = (Integer) iterator.next();

            string = string.concat(String.valueOf(roll));

            if (iterator.hasNext())
            {
                string = string.concat(", ");
            }
        }

        return string;
    }

    void addOrRemoveStatFilter(String keyword)
    {
        fragmentOpponent.addOrRemoveStatFilter(keyword);
    }

    void clearFilters()
    {
        fragmentOpponent.clearFilters();
    }

    void setUpCombatFilters(String combatType)
    {
        if (combatType.equals(CHECK_SUBTYPE_COMBAT_UNARMED.getText()))
        {

        }
    }

    public List<Maneuver> getManeuvers()
    {
        return model.getManeuvers();
    }

    @Subscribe public void checkForContestReadiness(CheckForContestReadinessEvent event)
    {
        if (fragmentOpponent.isReady() && fragmentPerformer.isReady())
        {
            view.showRollButton(true);
        }
        else
        {
            view.showRollButton(false);
        }
    }
}
