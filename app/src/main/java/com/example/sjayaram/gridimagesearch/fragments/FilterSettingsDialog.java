package com.example.sjayaram.gridimagesearch.fragments;

import android.app.Activity;
import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sjayaram.gridimagesearch.R;
import com.example.sjayaram.gridimagesearch.models.SearchFilter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class FilterSettingsDialog extends DialogFragment {

    Spinner spColor;
    EditText etSite;
    Spinner spSize;
    Spinner spType;

    public interface FilterSettingDialogListener {
        void onFinishDialog(SearchFilter filter);
    }

    FilterSettingDialogListener filterSettingDialogListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            filterSettingDialogListener = (FilterSettingDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onFinishDialog");
        }
    }

    public FilterSettingsDialog() {
        // Empty constructor required for DialogFragment
    }

    public static FilterSettingsDialog newInstance(String title) {
        FilterSettingsDialog frag = new FilterSettingsDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_filter_settings, container);

        spColor = (Spinner)view.findViewById(R.id.spColor);
        etSite = (EditText)view.findViewById(R.id.etSite);
        spSize = (Spinner)view.findViewById(R.id.spSize);
        spType = (Spinner)view.findViewById(R.id.spType);

        ArrayAdapter adapter_category = ArrayAdapter.createFromResource(getActivity(), R.array.size_arrays, R.layout.my_spinner_item);
        adapter_category.setDropDownViewResource(R.layout.my_spinner_dropdown_item);
        spSize.setAdapter(adapter_category);

        adapter_category = ArrayAdapter.createFromResource(getActivity(), R.array.type_arrays, R.layout.my_spinner_item);
        adapter_category.setDropDownViewResource(R.layout.my_spinner_dropdown_item);
        spType.setAdapter(adapter_category);

        adapter_category = ArrayAdapter.createFromResource(getActivity(), R.array.color_arrays, R.layout.my_spinner_item);
        adapter_category.setDropDownViewResource(R.layout.my_spinner_dropdown_item);
        spColor.setAdapter(adapter_category);

        SearchFilter filter = (SearchFilter)getArguments().getParcelable("filter");

        if(filter.colorFilter!= null && !"".equals(filter.colorFilter))
            SelectSpinnerItemByValue(spColor, filter.colorFilter);

        if(filter.siteFilter!=null && !"".equals(filter.siteFilter))
            etSite.setText(filter.siteFilter);

        if(filter.imageSize!=null && !"".equals(filter.imageSize))
            SelectSpinnerItemByValue(spSize, filter.imageSize);

        if(filter.imageType!=null && !"".equals(filter.imageType))
            SelectSpinnerItemByValue(spType, filter.imageType);

        String title = getArguments().getString("title", getResources().getString(R.string.filter_search_label));
        getDialog().setTitle(title);
        // Show soft keyboard automatically
        spSize.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        Button button = (Button) view.findViewById(R.id.btSubmit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SearchFilter filter = new SearchFilter(spSize.getSelectedItem().toString(), spColor.getSelectedItem().toString(), spType.getSelectedItem().toString()
                        , etSite.getText().toString());

                FilterSettingDialogListener listener = (FilterSettingDialogListener) getActivity();
                listener.onFinishDialog(filter);
                dismiss();

            }
        });

        return view;

    }

    public static void SelectSpinnerItemByValue(Spinner spnr, String value)
    {
        for (int i=0;i<spnr.getCount();i++)
        {
            if(spnr.getItemAtPosition(i).equals(value))
            {
                spnr.setSelection(i);
                return;
            }
        }
    }

}
