package com.mmadapps.fairpriceshop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mmadapps.fairpriceshop.R;
import com.mmadapps.fairpriceshop.bean.SchemeDetailsOnRationCardNumber;
import com.mmadapps.fairpriceshop.main.FPSApplication;
import com.mmadapps.fairpriceshop.utils.UtilsClass;

import java.util.List;

/**
 * Created by Baskar on 1/5/2016.
 */
public class CommoditySalesReceiptAdapter extends BaseAdapter {

    private Context mContext;
    private List<SchemeDetailsOnRationCardNumber> commodityList;

    public CommoditySalesReceiptAdapter(Context context, List<SchemeDetailsOnRationCardNumber> commodityDetails) {
        this.mContext = context;
        this.commodityList = commodityDetails;
    }

    @Override
    public int getCount() {
        if(commodityList == null)
            return 0;
        return commodityList.size();
    }

    @Override
    public SchemeDetailsOnRationCardNumber getItem(int position) {
        return commodityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView ==null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.receipt_adapter_ration_taken,parent,false);
        }
        final TextView vT_RART_commodityName,vT_RART_soldQty,vT_RART_ecoPrice,vT_RART_cipOrSipRs,vT_RART_subsidy,vT_RART_totalPrice;

        vT_RART_commodityName = (TextView)convertView.findViewById(R.id.vT_RART_commodityName);
        vT_RART_soldQty = (TextView)convertView.findViewById(R.id.vT_RART_soldQty) ;
        vT_RART_ecoPrice = (TextView)convertView.findViewById(R.id.vT_RART_ecoPrice) ;
        vT_RART_cipOrSipRs = (TextView)convertView.findViewById(R.id.vT_RART_cipOrSipRs) ;
        vT_RART_subsidy = (TextView)convertView.findViewById(R.id.vT_RART_subsidy) ;
        vT_RART_totalPrice = (TextView)convertView.findViewById(R.id.vT_RART_totalPrice) ;

        final SchemeDetailsOnRationCardNumber cd = getItem(position);

        String soldQty = cd.getmSoldQty();
        String ecoPrice = cd.getmRates();
        String cipPrice = cd.getmRates();
        String subsidy = cd.getmRates();
        String total = cd.getmTotalAmount();

        double sQty = 0, ePrice = 0, cPrice = 0, subPrice = 0, tPrice = 0;
        if(soldQty != null && soldQty.length()>0){
            soldQty = soldQty.trim();
            sQty = Double.parseDouble(soldQty);
            soldQty = UtilsClass.df.format(sQty);
        }if(ecoPrice != null && ecoPrice.length() >0){
            ecoPrice = ecoPrice.trim();
            ePrice = Double.parseDouble(ecoPrice);
            ecoPrice = UtilsClass.df.format(ePrice);
        }if(cipPrice != null && cipPrice.length()>0){
            cipPrice = cipPrice.trim();
            cPrice = Double.parseDouble(cipPrice);
            cipPrice = UtilsClass.df.format(cPrice);
        }if(subsidy != null && subsidy.length()>0){
            subsidy = subsidy.trim();
            subPrice = Double.parseDouble(subsidy);
            subsidy = UtilsClass.df.format(subPrice);
        }if(total != null && total.length() >0){
            total = total.trim();
            tPrice = Double.parseDouble(total);
            total = UtilsClass.df.format(tPrice);
        }

        vT_RART_commodityName.setText(cd.getmCommodityName());
        vT_RART_soldQty.setText(soldQty);
        vT_RART_ecoPrice.setText(ecoPrice);
        vT_RART_cipOrSipRs.setText(cipPrice);
        vT_RART_subsidy.setText(subsidy);
        vT_RART_totalPrice.setText(total);

        new Runnable() {
            @Override
            public void run() {
                vT_RART_commodityName.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_RART_soldQty.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_RART_ecoPrice.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_RART_cipOrSipRs.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_RART_subsidy.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_RART_totalPrice.setTypeface(FPSApplication.fMyriadPro_Regular);
            }
        }.run();

        return convertView;
    }
}
