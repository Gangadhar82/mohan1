package com.mmadapps.fairpriceshop.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.mmadapps.fairpriceshop.R;
import com.mmadapps.fairpriceshop.bean.RationLiftingMemberDetails;
import com.mmadapps.fairpriceshop.main.FPSApplication;
import com.mmadapps.fairpriceshop.services.AsyncServiceCall;
import com.mmadapps.fairpriceshop.services.ServiceInteractor;
import com.mmadapps.fairpriceshop.utils.UtilsClass;

import java.util.List;

/**
 * Created by Baskar on 12/15/2015.
 */
public class FamilyMembersAdapter extends BaseAdapter implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater inflater;
    private int mSelectedRow = -1;
    private String mClassName;
    private PopupWindow familyMembersPopup= null;
    private Activity mActivity;
    private TextView vT_pfm_lblEnterAadharNumber,vT_pfm_continueButton,vT_pfm_lblOr,vT_pfm_issueRationButton,
                    vT_pfm_nameValue,vT_pfm_genderValue,vT_pfm_relationValue,vT_pfm_aadharStatusValue; //,vT_pfm_cancelButton;
    private EditText vE_pfm_aadharNumber;
    private List<RationLiftingMemberDetails> rationLiftingMemberDetails;
    private ServiceInteractor interactor;
    private double wavierApproved = 0, wavierUsed = 0;

    public FamilyMembersAdapter(Context context, String mName, Activity activity, List<RationLiftingMemberDetails> rationLiftingMemberDetails, String wavierUsed, String wavierApproved){
        this.mContext = context;
        this.mActivity = activity;
        this.interactor = (ServiceInteractor) activity;
        this.mClassName = mName;
        this.rationLiftingMemberDetails = rationLiftingMemberDetails;
        if(mClassName.equals(UtilsClass.ISSUERATION)){
            if(wavierApproved != null && wavierApproved.length()>0){
                this.wavierApproved = Double.parseDouble(wavierApproved);
            }if(wavierUsed != null && wavierUsed.length()>0){
                this.wavierUsed = Double.parseDouble(wavierUsed);
            }
        }
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public RationLiftingMemberDetails getSelectedMemberDetails(){
        if(mSelectedRow != -1){
            return rationLiftingMemberDetails.get(mSelectedRow);
        }
        return null;
    }

    @Override
    public int getCount() {
        if(rationLiftingMemberDetails == null)
            return 0;
        return rationLiftingMemberDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_family_members, parent, false);
        }
        final ImageView vI_afm_selectedRow = (ImageView) convertView.findViewById(R.id.vI_afm_selectedRow);
        LinearLayout vL_afm_familyMembers = (LinearLayout) convertView.findViewById(R.id.vL_afm_familyMembers);

        final TextView vT_afm_lblName,vT_afm_lblAge,vT_afm_lblGender,vT_afm_lblRelation,vT_afm_lblAadharStatus;
        vT_afm_lblName = (TextView)convertView.findViewById(R.id.vT_afm_lblName);
        vT_afm_lblAge = (TextView)convertView.findViewById(R.id.vT_afm_lblAge);
        vT_afm_lblGender = (TextView)convertView.findViewById(R.id.vT_afm_lblGender);
        vT_afm_lblRelation = (TextView)convertView.findViewById(R.id.vT_afm_lblRelation);
        vT_afm_lblAadharStatus = (TextView)convertView.findViewById(R.id.vT_afm_lblAadharStatus);

        new Runnable() {
            @Override
            public void run() {
                vT_afm_lblName.setTypeface(FPSApplication.fSegoeui);
                vT_afm_lblAge.setTypeface(FPSApplication.fSegoeui);
                vT_afm_lblGender.setTypeface(FPSApplication.fSegoeui);
                vT_afm_lblRelation.setTypeface(FPSApplication.fSegoeui);
                vT_afm_lblAadharStatus.setTypeface(FPSApplication.fSegoeui);
            }
        }.run();

        final RationLiftingMemberDetails memberDetails = rationLiftingMemberDetails.get(position);
        String memberName = memberDetails.getmMember_Name_EN();
        if(memberDetails.getmIsHOF().equalsIgnoreCase("Y")){
            memberName += " (HOF)";
        }
        if(memberDetails.getmIsNFSA().equalsIgnoreCase("Y")){
            memberName += " (NFSA)";
        }
        vT_afm_lblName.setText(memberName);
        vT_afm_lblAge.setText(memberDetails.getmAgeInYears());
        vT_afm_lblGender.setText(memberDetails.getmGenderName());
        vT_afm_lblRelation.setText(memberDetails.getmRelation());
        vT_afm_lblAadharStatus.setText(memberDetails.getmStatus());

        if(position%2==0){
            vL_afm_familyMembers.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }else{
            if(mClassName.equals(UtilsClass.ISSUERATION)) {
                vL_afm_familyMembers.setBackgroundColor(mContext.getResources().getColor(R.color.red_light));
            }else {
                vL_afm_familyMembers.setBackgroundColor(mContext.getResources().getColor(R.color.yellow_light));
            }
        }

        if(mSelectedRow == position){
            if(mClassName.equals(UtilsClass.ISSUERATION)) {
                vI_afm_selectedRow.setImageResource(R.drawable.ir_selected);
            }else {
                vI_afm_selectedRow.setImageResource(R.drawable.as_selected);
            }
        }else{
            if(mClassName.equals(UtilsClass.ISSUERATION)) {
                vI_afm_selectedRow.setImageResource(R.drawable.ir_unselected);
            }else{
                vI_afm_selectedRow.setImageResource(R.drawable.as_unselected);
            }
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSelectedRow == position)
                    mSelectedRow = -1;
                else {
                    showFamilyMembersPopUp(memberDetails);
                    mSelectedRow = position;
                    vI_afm_selectedRow.setImageResource(R.drawable.selected);
                }
                refreshViews();
            }
        });
        return convertView;
    }

    private void refreshViews() {
        this.notifyDataSetChanged();
    }

    private void showFamilyMembersPopUp(RationLiftingMemberDetails memberDetails) {
        familyMembersPopup = new PopupWindow(mContext);
        LinearLayout viewGroup = (LinearLayout) mActivity.findViewById(R.id.vL_pfm_root);
        View layout=inflater.inflate(R.layout.popup_family_member ,viewGroup,false);
        familyMembersPopup.setContentView(layout);
        familyMembersPopup.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        familyMembersPopup.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        familyMembersPopup.setFocusable(true);
        familyMembersPopup.setOutsideTouchable(true);

        //familyMembersPopup.setBackgroundDrawable(new BitmapDrawable());
        familyMembersPopup.showAtLocation(layout, Gravity.CENTER, 0, 0);
        //popup.setFocusable(true);
        familyMembersPopup.update();

        initializeViews(layout, memberDetails);
    }

    private void initializeViews(final View layout, RationLiftingMemberDetails memberDetails) {
        ImageView vI_pfm_closeButton;

        vT_pfm_lblEnterAadharNumber = (TextView) layout.findViewById(R.id.vT_pfm_lblEnterAadharNumber);
        vE_pfm_aadharNumber = (EditText) layout.findViewById(R.id.vE_pfm_aadharNumber);
        vT_pfm_continueButton = (TextView) layout.findViewById(R.id.vT_pfm_continueButton);
        vT_pfm_lblOr = (TextView) layout.findViewById(R.id.vT_pfm_lblOr);
        vT_pfm_issueRationButton = (TextView) layout.findViewById(R.id.vT_pfm_issueRationButton);
        vI_pfm_closeButton = (ImageView) layout.findViewById(R.id.vI_pfm_closeButton);
        vT_pfm_nameValue = (TextView) layout.findViewById(R.id.vT_pfm_nameValue);
        vT_pfm_genderValue = (TextView) layout.findViewById(R.id.vT_pfm_genderValue);
        vT_pfm_relationValue = (TextView) layout.findViewById(R.id.vT_pfm_relationValue);
        vT_pfm_aadharStatusValue = (TextView) layout.findViewById(R.id.vT_pfm_aadharStatusValue);
        //vT_pfm_cancelButton = (TextView) layout.findViewById(R.id.vT_pfm_cancelButton);

        vT_pfm_nameValue.setText(memberDetails.getmMember_Name_EN());
        vT_pfm_genderValue.setText(memberDetails.getmGenderName());
        vT_pfm_relationValue.setText(memberDetails.getmRelation());
        vT_pfm_aadharStatusValue.setText(memberDetails.getmStatus());

        new Runnable() {
            @Override
            public void run() {
                TextView vT_pfm_lblMemberDetails,vT_pfm_lblName,vT_pfm_lblGender,vT_pfm_lblRelation,
                        vT_pfm_lblAadharStatus;

                vT_pfm_lblEnterAadharNumber.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_pfm_continueButton.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_pfm_lblOr.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_pfm_issueRationButton.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vE_pfm_aadharNumber.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_pfm_nameValue.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_pfm_genderValue.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_pfm_relationValue.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_pfm_aadharStatusValue.setTypeface(FPSApplication.fMyriadPro_Semibold);

                vT_pfm_lblMemberDetails = (TextView) layout.findViewById(R.id.vT_pfm_lblMemberDetails);
                vT_pfm_lblName = (TextView) layout.findViewById(R.id.vT_pfm_lblName);
                vT_pfm_lblGender = (TextView) layout.findViewById(R.id.vT_pfm_lblGender);
                vT_pfm_lblRelation = (TextView) layout.findViewById(R.id.vT_pfm_lblRelation);
                vT_pfm_lblAadharStatus = (TextView) layout.findViewById(R.id.vT_pfm_lblAadharStatus);

                vT_pfm_lblMemberDetails.setTypeface(FPSApplication.fMyriadPro_Semibold);
                vT_pfm_lblName.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_pfm_lblGender.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_pfm_lblRelation.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_pfm_lblAadharStatus.setTypeface(FPSApplication.fMyriadPro_Regular);
            }
        }.run();
        String status;
        if(memberDetails != null){
            status = ""+memberDetails.getmStatus();
            if(mClassName.equals(UtilsClass.ISSUERATION)) {
                if (status.equals("Pending")) {
                    vT_pfm_lblEnterAadharNumber.setVisibility(View.VISIBLE);
                    vT_pfm_continueButton.setVisibility(View.VISIBLE);
                    //vT_pfm_cancelButton.setVisibility(View.VISIBLE);
                    vE_pfm_aadharNumber.setVisibility(View.VISIBLE);
                    if(wavierUsed < wavierApproved) {
                        vT_pfm_lblOr.setVisibility(View.VISIBLE);
                        vT_pfm_issueRationButton.setVisibility(View.VISIBLE);
                    }else{
                        vT_pfm_lblOr.setVisibility(View.INVISIBLE);
                        vT_pfm_issueRationButton.setVisibility(View.INVISIBLE);
                    }
                }else{

                }
            }else if(mClassName.equals(UtilsClass.AADHARSEEDING)){
                vT_pfm_issueRationButton.setVisibility(View.INVISIBLE);
                if (status.equals("Pending")) {
                    vT_pfm_issueRationButton.setVisibility(View.INVISIBLE);
                    vT_pfm_lblEnterAadharNumber.setVisibility(View.VISIBLE);
                    vE_pfm_aadharNumber.setVisibility(View.VISIBLE);
                    vT_pfm_continueButton.setVisibility(View.VISIBLE);
                }
            }
        }


        //vT_pfm_cancelButton.setOnClickListener(this);
        vT_pfm_issueRationButton.setOnClickListener(this);
        vT_pfm_continueButton.setOnClickListener(this);
        vI_pfm_closeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.vT_pfm_cancelButton:
                cancelPopup();
                break;
            case R.id.vT_pfm_issueRationButton:
                vT_pfm_issueRationButton.setEnabled(false);
                //if(validateAadharNo()){
                    new AsyncServiceCall(mContext, interactor, UtilsClass.GET_SCHEME_DETAILS_FOR_RATION_CARD);
                    cancelPopup();
                //}
                vT_pfm_issueRationButton.setEnabled(true);
                break;
            case R.id.vT_pfm_continueButton:
                vT_pfm_continueButton.setEnabled(false);
                if(validateAadharNo()){
                    new AsyncServiceCall(mContext, interactor, UtilsClass.POST_CREATE_AADHAARSEED);
                    cancelPopup();
                }
                vT_pfm_continueButton.setEnabled(true);
                break;
            case R.id.vI_pfm_closeButton:
                cancelPopup();
                break;
        }
    }

    private boolean validateAadharNo() {
        if(vE_pfm_aadharNumber.getVisibility() == View.VISIBLE){
            String aadharNo = ""+vE_pfm_aadharNumber.getText().toString();
            if(aadharNo.length() == 0){
                Toast.makeText(mContext, "Please enter aadhar number", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void cancelPopup() {
        if(familyMembersPopup!= null && familyMembersPopup.isShowing())
            familyMembersPopup.dismiss();
    }
}
