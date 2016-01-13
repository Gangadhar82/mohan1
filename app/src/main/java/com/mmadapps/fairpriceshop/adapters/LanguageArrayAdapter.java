package com.mmadapps.fairpriceshop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mmadapps.fairpriceshop.R;
import com.mmadapps.fairpriceshop.bean.LanguageDetails;

import java.util.List;

/**
 * Created by Baskar on 12/23/2015.
 */
public class LanguageArrayAdapter extends ArrayAdapter<LanguageDetails> {

    private List<LanguageDetails> languageDetails;
    private Context mContext;
    private LayoutInflater inflater;
    private String mSelectedLanguage;

    public LanguageArrayAdapter(Context context, int resource, List<LanguageDetails> objects) {
        super(context, resource, objects);
        this.languageDetails = objects;
        this.mContext = context;
        //this.mSelectedLanguage = selectedLanguage;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.adapter_language, parent, false);
        }
        TextView vT_apl_spinnerText = (TextView) convertView.findViewById(R.id.vT_apl_spinnerText);
        LanguageDetails language = languageDetails.get(position);
        vT_apl_spinnerText.setText(language.getmNameL1());
        if(language.getmIsDefault().equalsIgnoreCase("Y")) {
            convertView.setSelected(true);
        }
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.adapter_language, parent, false);
        }
        TextView vT_apl_spinnerText = (TextView) convertView.findViewById(R.id.vT_apl_spinnerText);
        LanguageDetails language = languageDetails.get(position);
        vT_apl_spinnerText.setText(language.getmNameL1());
        if(language.getmIsDefault().equalsIgnoreCase("Y")) {
            convertView.setSelected(true);
        }
        return convertView;
    }
}
