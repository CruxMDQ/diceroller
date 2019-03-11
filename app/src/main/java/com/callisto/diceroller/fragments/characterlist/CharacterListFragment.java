package com.callisto.diceroller.fragments.characterlist;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.callisto.diceroller.R;
import com.callisto.diceroller.application.App;
import com.callisto.diceroller.fragments.BaseFragment;
import com.callisto.diceroller.persistence.objects.Character;
import com.callisto.diceroller.persistence.objects.Template;

import java.util.List;

public class CharacterListFragment
    extends BaseFragment
    implements CharacterListView
{
    CharacterListPresenter presenter;

    GridView gv;

//    private Toolbar toolbar;

    FloatingActionButton fabNewCharacter;
    TextView labelNewCharacter;
    LinearLayout panelNewCharacter;

    FloatingActionButton fabSettings;
    TextView labelSettings;
    LinearLayout panelSettings;

    FloatingActionButton fabOpposedCheck;
    TextView labelOpposedCheck;
    LinearLayout panelOpposedCheck;


    private List<Character> characters;

    private boolean isFABOpen;

    @Override
    public void onViewCreated
        (@NonNull android.view.View view,
         @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        presenter = new CharacterListPresenter(this);

        bindGridView();

        setClickListenerOnGridView();
    }

    private void bindGridView()
    {
        characters = presenter.getCharacters();

        gv.setAdapter(new CharacterListAdapter(characters));
    }

    private void setClickListenerOnGridView()
    {
        gv.setOnItemClickListener((parent, view, position, id) ->
        {
            Character character = characters.get(position);

            presenter.requestCharacterEditor(character.getName(), character.getTemplate().getFont());
        });
    }

    @Override
    protected int getLayout()
    {
        return R.layout.fragment_character_list;
    }

    @Override
    protected void findViews()
    {
        gv = rootView.findViewById(R.id.gv);

        setUpFAB();

//        setUpToolbar();
    }

//    private void setUpToolbar()
//    {
//        toolbar = rootView.findViewById(R.id.toolbar);
//
//        if (toolbar != null) {
//            ((AppCompatActivity) Objects.requireNonNull(getActivity()))
//                .setSupportActionBar(toolbar);
//        }
//
//        Objects.requireNonNull(toolbar).setOnMenuItemClickListener(menuItem ->
//        {
//            switch (menuItem.getItemId())
//            {
//                case R.id.action_settings:
//                {
//                    return true;
//                }
//                default:
//                {
//                    return false;
//                }
//            }
//        });
//    }

    private void setUpFAB()
    {
        FloatingActionButton fabUnfold = rootView.findViewById(R.id.fabUnfold);

        fabNewCharacter = rootView.findViewById(R.id.fabNewCharacter);
        labelNewCharacter = rootView.findViewById(R.id.labelNewCharacter);
        panelNewCharacter = rootView.findViewById(R.id.panelNewCharacter);

        fabOpposedCheck = rootView.findViewById(R.id.fabOpposedCheck);
        labelOpposedCheck = rootView.findViewById(R.id.labelOpposedCheck);
        panelOpposedCheck = rootView.findViewById(R.id.panelOpposedCheck);
        
        fabSettings = rootView.findViewById(R.id.fabSettings);
        labelSettings = rootView.findViewById(R.id.labelSettings);
        panelSettings = rootView.findViewById(R.id.panelSettings);

        fabUnfold.setOnClickListener(v -> {
            if (!isFABOpen)
            {
                showFABMenu();
            }
            else
            {
                closeFABMenu();
            }
        });

        fabNewCharacter.setOnClickListener(v -> spawnCharacterCreationDialog());

        fabSettings.setOnClickListener(v -> invokeSettingsScreen());
    }

    private void invokeSettingsScreen()
    {
        // TODO Invoke settings fragment launch here
    }

    private void spawnCharacterCreationDialog()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        // Set dialog title
        dialogBuilder.setTitle("Character creation");

        final Template[] template = new Template[1];

        // Set dialog view
        final View view = getLayoutInflater().inflate(R.layout.dialog_character_create, null);
        dialogBuilder.setView(view);

        // Find dialog components
        final EditText inputName = view.findViewById(R.id.inputName);
        Spinner pickerTemplate = view.findViewById(R.id.pickerTemplate);
        Button btnCreate = view.findViewById(R.id.btnCreate);

        TemplateAdapter templateAdapter = new TemplateAdapter();

        pickerTemplate.setAdapter(templateAdapter);

        pickerTemplate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Template selectedItem = (Template) templateAdapter.getItem(position);
                Toast.makeText(
                    getContext(),
                    selectedItem.getName(),
                    Toast.LENGTH_SHORT).show();

                template[0] = selectedItem;

                templateAdapter.clearSelection();

                templateAdapter.addSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        // Store dialog reference to later be able to dismiss it
        final AlertDialog dialog = dialogBuilder.show();

        btnCreate.setOnClickListener(view1 ->
        {
            String characterName = inputName.getText().toString();

            presenter.createNewCharacter(
                characterName,
                // TODO Create support for setting player
                template[0].getName()
            );

            presenter.requestCharacterEditor(characterName, template[0].getFont());

            dialog.cancel();
        });

    }

    private void showFABMenu()
    {
        isFABOpen = true;

        panelNewCharacter.animate().translationY(-App.getRes().getDimension(R.dimen.fab_offset_1));
        labelNewCharacter.setVisibility(View.VISIBLE);

        panelOpposedCheck.animate().translationY(-App.getRes().getDimension(R.dimen.fab_offset_2));
        labelOpposedCheck.setVisibility(View.VISIBLE);

        panelSettings.animate().translationY(-App.getRes().getDimension(R.dimen.fab_offset_3));
        labelSettings.setVisibility(View.VISIBLE);

    }

    private void closeFABMenu()
    {
        isFABOpen = false;

        panelNewCharacter.animate().translationY(0);
        labelNewCharacter.setVisibility(View.GONE);

        panelOpposedCheck.animate().translationY(0);
        labelOpposedCheck.setVisibility(View.GONE);

        panelSettings.animate().translationY(0);
        labelSettings.setVisibility(View.GONE);
    }
}
