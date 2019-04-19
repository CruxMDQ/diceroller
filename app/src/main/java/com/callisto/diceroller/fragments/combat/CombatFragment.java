package com.callisto.diceroller.fragments.combat;

import android.widget.LinearLayout;
import android.widget.Spinner;

import com.callisto.diceroller.R;
import com.callisto.diceroller.fragments.BaseFragment;

public class CombatFragment
    extends BaseFragment
{
    private LinearLayout panelRollSubtype;

    private String selectedCheckType;

    private Spinner pickerRollType, pickerRollSubtype;

    @Override
    protected int getLayout()
    {
        return R.layout.fragment_combat;
    }

    @Override
    protected void findViews()
    {
        pickerRollType = rootView.findViewById(R.id.pickerRollType);
        pickerRollSubtype = rootView.findViewById(R.id.pickerRollSubtype);

        panelRollSubtype = rootView.findViewById(R.id.panelRollSubtype);
    }

//    private void setUpCharacterPickerAdapters()
//    {
//        if (selectedCheckType.equals(CHECK_TYPE_COMBAT.getText()))
//        {
//            presenter.setUpCombatFilters(selectedCheckSubtype);
//        }
//    }

//    private void setUpSpinnerType()
//    {
//        ArrayList<String> checkTypes = new ArrayList<>();
//
//        checkTypes.add(CHECK_TYPE_SKILL.getText());
//        checkTypes.add(CHECK_TYPE_COMBAT.getText());
//
//        SelectionHidingAdapter<String> adapterType = new SelectionHidingAdapter<String>(checkTypes)
//        {
//            @Override
//            public long getItemId(int position)
//            {
//                return 0;
//            }
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent)
//            {
//                String string = (String) getItem(position);
//
//                if (convertView == null)
//                {
//                    convertView = LayoutInflater
//                        .from(getContext())
//                        .inflate(R.layout.spinner_item, parent, false
//                        );
//
//                    TextView name = convertView.findViewById(R.id.text1);
//
//                    name.setText(string);
//                }
//
//                return convertView;
//            }
//        };
//
//        pickerRollType.setAdapter(adapterType);
//
//        pickerRollType.setSelection(0, false);
//
//        pickerRollType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
//        {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
//            {
////                hideAllItems();
//
//                selectedCheckType = (String) adapterType.getItem(position);
//
//                Toast.makeText(
//                    getContext(),
//                    selectedCheckType,
//                    Toast.LENGTH_SHORT).show();
//
//                adapterType.clearSelection();
//
//                adapterType.addOrRemoveSelection(position);
//
//                if (selectedCheckType.equals(CHECK_TYPE_SKILL.getText()))
//                {
//                    panelRollSubtype.setVisibility(View.GONE);
//                    rgCheckType.setVisibility(View.VISIBLE);
//                }
//
//                if (selectedCheckType.equals(CHECK_TYPE_COMBAT.getText()))
//                {
//                    panelRollSubtype.setVisibility(View.VISIBLE);
//                    rgCheckType.setVisibility(View.GONE);
//                    setUpPickerSubtype();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) { }
//        });
//    }
//
//    private void setUpPickerSubtype()
//    {
//        SelectionHidingAdapter<Maneuver> adapterSubtype =
//            new SelectionHidingAdapter<Maneuver>(presenter.getManeuvers())
//            {
//                @Override
//                public long getItemId(int position)
//                {
//                    return ((Maneuver) getItem(position)).getId();
//                }
//
//                @Override
//                public View getView(int position, View convertView, ViewGroup parent)
//                {
//                    Maneuver maneuver = (Maneuver) getItem(position);
//
//                    if (convertView == null)
//                    {
//                        convertView = LayoutInflater
//                            .from(getContext())
//                            .inflate(R.layout.spinner_item, parent, false
//                            );
//
//                        TextView name = convertView.findViewById(R.id.text1);
//
//                        name.setText(maneuver.getName());
//                    }
//
//                    return convertView;
//                }
//            };
//
//        pickerRollSubtype.setAdapter(adapterSubtype);
//
//        pickerRollSubtype.setSelection(0, false);
//
//        pickerRollSubtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
//        {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
//            {
//                selectedCheckSubtype = ((Maneuver) adapterSubtype.getItem(position)).getName();
//
//                setUpCharacterPickerAdapters();
//
//                Toast.makeText(
//                    getContext(),
//                    selectedCheckSubtype,
//                    Toast.LENGTH_SHORT).show();
//
//                adapterSubtype.clearSelection();
//
//                adapterSubtype.addOrRemoveSelection(position);
//
//                prepareUI();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) { }
//        });
//    }
}
