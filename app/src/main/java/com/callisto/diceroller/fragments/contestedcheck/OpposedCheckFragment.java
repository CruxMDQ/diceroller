package com.callisto.diceroller.fragments.contestedcheck;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.callisto.diceroller.R;
import com.callisto.diceroller.fragments.BaseFragment;
import com.callisto.diceroller.tools.Constants;

import static com.callisto.diceroller.tools.Constants.FragmentTags.TAG_FRAGMENT_OPPOSED_CHECK_OPPONENT;
import static com.callisto.diceroller.tools.Constants.FragmentTags.TAG_FRAGMENT_OPPOSED_CHECK_PERFORMER;
import static com.callisto.diceroller.tools.Constants.Parameters.CHECK_SUBTYPE_SKILL_CONTESTED;
import static com.callisto.diceroller.tools.Constants.Parameters.CHECK_SUBTYPE_SKILL_RESISTED;

public class OpposedCheckFragment
    extends BaseFragment
{
    private FrameLayout flPerformer, flOpponent;

    private Button btnPerformCheck;

    private CheckBox chkExtended;

    private OpposedCheckPresenter presenter;

    private String selectedCheckSubtype;

    private RadioButton rdbContestedCheck, rdbResistedCheck;

    @Override
    protected int getLayout()
    {
        return R.layout.fragment_opposed_check;
    }

    @Override
    protected void findViews()
    {
        flPerformer = rootView.findViewById(R.id.flPerformer);
        flOpponent = rootView.findViewById(R.id.flOpponent);

        btnPerformCheck = rootView.findViewById(R.id.btnPerformCheck);

        rdbContestedCheck = rootView.findViewById(R.id.rdbContestedCheck);
        rdbResistedCheck = rootView.findViewById(R.id.rdbResistedCheck);

        chkExtended = rootView.findViewById(R.id.chkExtendedRoll);

        setUpRollButton();

        setUpRadioButtons();
    }

    private void prepareUI()
    {
        btnPerformCheck.setVisibility(View.VISIBLE);
        flPerformer.setVisibility(View.VISIBLE);
        flOpponent.setVisibility(View.VISIBLE);

        if (selectedCheckSubtype.equals(CHECK_SUBTYPE_SKILL_CONTESTED.getText()))
        {
            chkExtended.setVisibility(View.VISIBLE);
        }
        else
        {
            chkExtended.setVisibility(View.GONE);
        }
    }

    private void setUpRadioButtons()
    {
        rdbContestedCheck.setChecked(false);
        rdbResistedCheck.setChecked(false);

        rdbContestedCheck.setOnCheckedChangeListener((buttonView, isChecked) ->
        {
            if (isChecked) {
                // The adapter on the opponent fragment should return all stats
                presenter.clearFilters();

                // Check should pit the rolls of the performer against those of the opponent

                // Extended rolls should be allowed
                chkExtended.setVisibility(View.VISIBLE);

                selectedCheckSubtype = CHECK_SUBTYPE_SKILL_CONTESTED.getText();

                prepareUI();
            }
        });

        rdbResistedCheck.setOnCheckedChangeListener((buttonView, isChecked) ->
        {
            if (isChecked)
            {
                // The adapter on the opponent fragment should return only resistance-related stats?
                presenter.addOrRemoveStatFilter(Constants.Keywords.RESISTANCE.getText());

                // Check only uses the performer's pool, sans # dice = opponent's resistance stat

                // Extended rolls should not be allowed
                chkExtended.setVisibility(View.GONE);
                chkExtended.setChecked(false);

                selectedCheckSubtype = CHECK_SUBTYPE_SKILL_RESISTED.getText();

                prepareUI();
            }
        });
    }

    private void setUpRollButton()
    {
        btnPerformCheck.setOnClickListener(v ->
        {
            if (selectedCheckSubtype.equals(CHECK_SUBTYPE_SKILL_CONTESTED.getText()))
            {
                presenter.performContestedCheck();
            }

            if (selectedCheckSubtype.equals(CHECK_SUBTYPE_SKILL_RESISTED.getText()))
            {
                presenter.performResistedCheck();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        loadCharacterFragments();
    }

    private void loadCharacterFragments()
    {
        FragmentManager fm = getChildFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();

        CharacterContestFragment fragmentPerformer = new CharacterContestFragment();
        CharacterContestFragment fragmentOpponent = new CharacterContestFragment();

        ft.replace(
            R.id.flPerformer,
            fragmentPerformer,
            TAG_FRAGMENT_OPPOSED_CHECK_PERFORMER.getText()
        );

        ft.replace(
            R.id.flOpponent,
            fragmentOpponent,
            TAG_FRAGMENT_OPPOSED_CHECK_OPPONENT.getText()
        );

        ft.commit();

        // This call doesn't really belong here, but putting it here means 2 less fields
        presenter = new OpposedCheckPresenter(this, fragmentPerformer, fragmentOpponent);
    }

    public void showRollButton(boolean visible)
    {
        if (visible)
        {
            btnPerformCheck.setVisibility(View.VISIBLE);
        }
        else
        {
            btnPerformCheck.setVisibility(View.GONE);
        }
    }

    void spawnContestedCheckResultsDialog(
        String successesPerformer,
        String rollsPerformer,
        String successesOpponent,
        String rollsOpponent)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());

        // Set dialog title
        dialogBuilder.setTitle(getString(R.string.title_dialog_contested_check_results));

        // Set dialog view
        @SuppressLint("InflateParams")
        final View view = getLayoutInflater().inflate(R.layout.dialog_opposed_check_results, null);
        dialogBuilder.setView(view);

        TextView txtPerformerSuccesses = view.findViewById(R.id.txtPerformerSuccesses);
        TextView txtOpponentSuccesses = view.findViewById(R.id.txtOpponentSuccesses);
        TextView txtPerformerRolls = view.findViewById(R.id.txtPerformerRolls);
        TextView txtOpponentRolls = view.findViewById(R.id.txtOpponentRolls);
        Button btnClose = view.findViewById(R.id.btnClose);

        txtPerformerSuccesses.setText(successesPerformer);
        txtPerformerRolls.setText(rollsPerformer);
        txtOpponentSuccesses.setText(successesOpponent);
        txtOpponentRolls.setText(rollsOpponent);

        // Store dialog reference to later be able to dismiss it
        final AlertDialog dialog = dialogBuilder.show();

        btnClose.setOnClickListener(v -> dialog.cancel());
    }

    public void spawnResistedCheckResultsDialog(
        int dicePerformer,
        String successesPerformer,
        String rollsPerformer,
        int opponentResistance)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());

        // Set dialog title
        dialogBuilder.setTitle(getString(R.string.title_dialog_contested_check_results));

        // Set dialog view
        @SuppressLint("InflateParams")
        final View view = getLayoutInflater().inflate(R.layout.dialog_resisted_check_results, null);
        dialogBuilder.setView(view);

        TextView txtOpponentResistance = view.findViewById(R.id.txtOpponentResistance);
        TextView txtPerformerDicePool = view.findViewById(R.id.txtPerformerDicePool);
        TextView txtPerformerSuccesses = view.findViewById(R.id.txtPerformerSuccesses);
        TextView txtPerformerRolls = view.findViewById(R.id.txtPerformerRolls);
        Button btnClose = view.findViewById(R.id.btnClose);

        txtPerformerDicePool.setText(String.valueOf(dicePerformer));
        txtOpponentResistance.setText(String.valueOf(opponentResistance));
        txtPerformerSuccesses.setText(successesPerformer);
        txtPerformerRolls.setText(rollsPerformer);

        // Store dialog reference to later be able to dismiss it
        final AlertDialog dialog = dialogBuilder.show();

        btnClose.setOnClickListener(v -> dialog.cancel());
    }
}
