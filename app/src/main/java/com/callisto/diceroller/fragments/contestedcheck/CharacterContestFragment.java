package com.callisto.diceroller.fragments.contestedcheck;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.callisto.diceroller.R;
import com.callisto.diceroller.application.App;
import com.callisto.diceroller.fragments.BaseFragment;
import com.callisto.diceroller.fragments.adapters.CharacterGridAdapter;
import com.callisto.diceroller.fragments.adapters.CustomAdapter;
import com.callisto.diceroller.persistence.objects.Character;
import com.callisto.diceroller.persistence.objects.Stat;
import com.callisto.diceroller.tools.Constants;
import com.callisto.diceroller.tools.TypefaceSpanBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.callisto.diceroller.application.App.getInstance;

// TODO Polish stat picking popup
// DONE Add 'tap to choose' legend to dice pool text field
public class CharacterContestFragment
    extends BaseFragment
{
    private TextView txtDicePool;
    private FloatingActionButton fabClearStats;
    private EditText inputRerollThreshold;

    private TextView titleCharacter;

    private CharacterContestPresenter presenter;

    private Button btnPickCharacter;

    @Override
    protected int getLayout()
    {
        return R.layout.fragment_character_contested_check;
    }

    @Override
    protected void findViews()
    {
        txtDicePool = rootView.findViewById(R.id.txtDicePool);
        fabClearStats = rootView.findViewById(R.id.fabClearStats);
        inputRerollThreshold = rootView.findViewById(R.id.inputPerformerRerollThreshold);

        titleCharacter = rootView.findViewById(R.id.titleCharacter);

        btnPickCharacter = rootView.findViewById(R.id.btnPickCharacter);
    }

    private void setUpFAB()
    {
        fabClearStats.setOnClickListener(v ->
        {
            presenter.clearSelections();

            txtDicePool.setText(getDicePoolString(presenter.getSelectedStats()));
        });
    }

    private String getDicePoolString(@NonNull List<Stat> stats)
    {
        if (stats.size() > 0)
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

            return statList;
        }
        else
        {
            return App.getInstance().getText(R.string.tooltop_tap_select_stat).toString();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        presenter = new CharacterContestPresenter(this);

        titleCharacter.setOnClickListener(v ->
            spawnCharacterSelectionDialog()
        );

        btnPickCharacter.setOnClickListener(v ->
            spawnCharacterSelectionDialog()
        );

        txtDicePool.setOnClickListener(v ->
            spawnStatSelectionDialog()
        );

        setUpFAB();
    }

    private void spawnStatSelectionDialog()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        dialogBuilder.setTitle("Pick stats");

        @SuppressLint("InflateParams") final View view = getLayoutInflater()
            .inflate(R.layout.dialog_stats_pick, null);
        dialogBuilder.setView(view);

        int color = ContextCompat
            .getColor(getInstance().getApplicationContext(), R.color.color_red_light);
        int white = ContextCompat
            .getColor(getInstance().getApplicationContext(), android.R.color.white);

        TextView txtPanelDicePool = view.findViewById(R.id.txtDicePool);
        GridView gvStats = view.findViewById(R.id.gvStats);
        FloatingActionButton fabClearStats = view.findViewById(R.id.fabClearStats);

        txtPanelDicePool.setText(getDicePoolString(presenter.getSelectedStats()));

        final AlertDialog dialog = dialogBuilder.create();

        CustomAdapter<Stat> gridAdapter = new CustomAdapter<Stat>(presenter.getCharacterStats())
        {
            @Override
            public long getItemId(int position)
            {
                return ((Stat) getItem(position)).getId();
            }

            @NonNull
            public View getView
                (
                    int position,
                    View convertView,
                    @NonNull ViewGroup parent
                )
            {
                if (convertView == null)
                {
                    convertView = LayoutInflater
                        .from(getContext())
                        .inflate(R.layout.grid_view_item, parent, false);

                    Stat stat = (Stat) getItem(position);
                    LinearLayout panelItem = convertView.findViewById(R.id.panelItem);
                    TextView name = convertView.findViewById(R.id.txtName);

                    if (stat != null)
                    {
                        name.setText(stat.getName());
                    }

                    //noinspection SuspiciousMethodCalls
                    if (presenter.getSelectedStats().contains(stat))
                    {
                        panelItem.setBackgroundColor(color);
                    }
                    else
                    {
                        panelItem.setBackgroundColor(white);
                    }
                }
                return convertView;
            }
        };

        fabClearStats.setOnClickListener(v ->
        {
            presenter.clearSelections();

            txtDicePool.setText(getDicePoolString(presenter.getSelectedStats()));
            txtPanelDicePool.setText(getDicePoolString(presenter.getSelectedStats()));

            gridAdapter.notifyDataSetChanged();
            gvStats.setAdapter(gridAdapter);

            gvStats.invalidateViews();

            presenter.setHasPickedStats(false);
        });

        gvStats.setAdapter(gridAdapter);

        gvStats.setOnItemClickListener((parent, view1, position, id) ->
        {
            presenter.addOrRemoveSelectedStat((Stat) gridAdapter.getItem(position));

            gridAdapter.addOrRemoveSelection(position);

            txtDicePool.setText(getDicePoolString(presenter.getSelectedStats()));
            txtPanelDicePool.setText(getDicePoolString(presenter.getSelectedStats()));

            LinearLayout panelItem = view1.findViewById(R.id.panelItem);

            //noinspection SuspiciousMethodCalls
            if (presenter.getSelectedStats().contains(gridAdapter.getItem(position)))
            {
                panelItem.setBackgroundColor(color);
            }
            else
            {
                panelItem.setBackgroundColor(white);
            }

            if (presenter.getSelectedStats().size() > 0)
            {
                presenter.setHasPickedStats(true);
            }
            else
            {
                presenter.setHasPickedStats(false);
            }
        });

        dialog.show();
    }

    private void spawnCharacterSelectionDialog()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        dialogBuilder.setTitle(getString(R.string.label_btn_pick_character));

        @SuppressLint("InflateParams") final View view = getLayoutInflater()
            .inflate(R.layout.dialog_character_pick, null);
        dialogBuilder.setView(view);

        GridView gvCharacters = view.findViewById(R.id.gvCharacters);

        CharacterGridAdapter adapter = new CharacterGridAdapter(presenter.getCharacters());

        final AlertDialog dialog = dialogBuilder.create();

        gvCharacters.setAdapter(adapter);

        gvCharacters.setOnItemClickListener((parent, view1, position, id) ->
        {
            Character selectedCharacter = (Character) adapter.getItem(position);

            presenter.setSelectedCharacter(selectedCharacter);

            adapter.addOrRemoveSelection(position);

            TypefaceSpanBuilder.setTypefacedTitle(
                titleCharacter,
                selectedCharacter.getName(),
                selectedCharacter.getTemplate().getFont(),
                Constants.Values.STAT_CONTAINER_FONT_TITLE.getValue()
            );

            btnPickCharacter.setVisibility(View.GONE);

            presenter.setHasPickedCharacter(true);

            dialog.cancel();
        });

        dialog.show();
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

    public Integer getDicePoolSize()
    {
        return presenter.getDicePoolSize();
    }

    public void performRoll()
    {
        presenter.performRoll(getDiceRerollThreshold(), 0);
    }

    public void performRoll(int miscMod)
    {
        presenter.performRoll(getDiceRerollThreshold(), miscMod);
    }

    public void addOrRemoveStatFilter(String filter)
    {
        presenter.addOrRemoveStatFilter(filter);
    }

    public void clearFilters()
    {
        presenter.clearFilters();
    }

    public boolean isReady()
    {
        return presenter.hasPickedCharacter() && presenter.hasPickedStats();
    }
}
