package com.mmadapps.fairpriceshop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mmadapps.fairpriceshop.R;
import com.mmadapps.fairpriceshop.bean.AllocationDetails;

import java.util.List;

/**
 * Created by Baskar on 12/24/2015.
 */
public class AllocationNumberAdapter extends ArrayAdapter<AllocationDetails> {
    private List<AllocationDetails> mAllocationDetailsList = null;
    private Context mContext;
    private LayoutInflater inflater;

    public AllocationNumberAdapter(Context context, int resource, List<AllocationDetails> objects) {
        super(context, resource, objects);
        this.mContext = context;
        mAllocationDetailsList = objects;
        inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.adapter_language, parent, false);
        }
        TextView vT_apl_spinnerText = (TextView) convertView.findViewById(R.id.vT_apl_spinnerText);
        AllocationDetails allocationDetails = mAllocationDetailsList.get(position);
        vT_apl_spinnerText.setText(allocationDetails.getmAllocationOrderNo());
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.adapter_language, parent, false);
        }
        TextView vT_apl_spinnerText = (TextView) convertView.findViewById(R.id.vT_apl_spinnerText);
        AllocationDetails allocationDetails = mAllocationDetailsList.get(position);
        vT_apl_spinnerText.setText(allocationDetails.getmAllocationOrderNo());
        return convertView;
    }
}
