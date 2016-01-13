package com.mmadapps.fairpriceshop.fieldinspection;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mmadapps.fairpriceshop.R;
import com.mmadapps.fairpriceshop.actionbars.ActionbarActivity;
import com.mmadapps.fairpriceshop.bean.AgentDetails;
import com.mmadapps.fairpriceshop.bean.FeedBackAnswerDetails;
import com.mmadapps.fairpriceshop.bean.FeedBackDetails;
import com.mmadapps.fairpriceshop.main.FPSApplication;
import com.mmadapps.fairpriceshop.services.AsyncServiceCall;
import com.mmadapps.fairpriceshop.services.WebService;
import com.mmadapps.fairpriceshop.utils.CaptionsKey;
import com.mmadapps.fairpriceshop.utils.Helper;
import com.mmadapps.fairpriceshop.utils.JParser;
import com.mmadapps.fairpriceshop.utils.UtilsClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Baskar on 12/15/2015.
 */
public class FieldInspectionActivity extends ActionbarActivity {

    private final String TAG = "Inspection Activity";
    //views
    private EditText vE_afi_addcomplaintdetails;
    private TextView vT_afi_btnSubmit;
    private ListView vL_afi_questionsList;

    //Variables
    private List<FeedBackDetails> mFeedBackDetailsList;
    private Map<String, List<FeedBackAnswerDetails>> mFeedBackAnswersMap;
    private Map<String, String> mAnswersMap;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        setContentView(R.layout.activity_field_inspection);
        super.onCreate(savedInstanceState);
        setPageTitle(R.string.title_field_inspection,true);
        initializeViews();
        updateCaptions();
        setActionEvents();
        setFonts();
        callServiceClass(UtilsClass.GET_FEEDBACK_DETAILS);
    }

    private void setActionEvents() {
        vT_afi_btnSubmit.setOnClickListener(this);
    }

    private void setFonts() {
        new Runnable() {
            @Override
            public void run() {
                vE_afi_addcomplaintdetails.setTypeface(FPSApplication.fMyriadPro_Regular);
                vT_afi_btnSubmit.setTypeface(FPSApplication.fMyriadPro_Semibold);
            }
        }.run();
    }

    private void initializeViews() {
        vL_afi_questionsList = (ListView) findViewById(R.id.vL_afi_questionsList);
        vT_afi_btnSubmit = (TextView) findViewById(R.id.vT_afi_btnSubmit);
        vE_afi_addcomplaintdetails = (EditText) findViewById(R.id.vE_afi_addcomplaintdetails);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.vT_afi_btnSubmit:
                vT_afi_btnSubmit.setEnabled(false);
                if(validateAnswers()) {
                    callServiceClass(UtilsClass.POST_SAVE_FEEDBACK);
                }else{
                    Toast.makeText(FieldInspectionActivity.this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                }
                vT_afi_btnSubmit.setEnabled(true);
                break;
        }
    }

    private boolean validateAnswers() {
        if(mAnswersMap == null || mAnswersMap.size() == 0){
            return false;
        }
        if(mFeedBackDetailsList == null){
            return true;
        }
        boolean isAnswered = true;
        for(FeedBackDetails questions : mFeedBackDetailsList){
            String key = questions.getmId();
            if(!mAnswersMap.containsKey(key)){
                isAnswered = false;
                break;
            }
        }
        if(!isAnswered) {
            return false;
        }else{
            return true;
        }
    }

    private void callServiceClass(String mService){
        new AsyncServiceCall(this,this,mService);
    }

    @Override
    public boolean callService(String service) {
        WebService webService = new WebService();
        JParser jParser = new JParser();
        String inParam;
        String result;
        if(service.equals(UtilsClass.GET_FEEDBACK_DETAILS)){
            inParam = "stateId=02";
            result = webService.callService(WebService.ServiceAPI.GET_FEEDBACK_DETAILS,inParam);
            if(result == null || result.length() == 0){
                Log.e(TAG,"Getfeedback Response null");
            }else{
                mFeedBackDetailsList = jParser.parsingFeedbackdetails(result);
                if(mFeedBackDetailsList == null || mFeedBackDetailsList.size() == 0){
                    Log.e(TAG,"Feedback List is empty");
                }else {
                    for (FeedBackDetails question : mFeedBackDetailsList) {
                        result = null;
                        inParam = question.getmId();
                        result = webService.callService(WebService.ServiceAPI.GET_FEEDBACK_ANSWER_DETAILS, "feedBackId="+inParam);
                        if(result == null || result.length() == 0){
                            Log.e(TAG,"Result null for "+inParam);
                        }else{
                            mFeedBackAnswersMap = new HashMap<>();
                            List<FeedBackAnswerDetails> answerList = jParser.parsingAnswerFormatDetails(result);
                            if(answerList == null || answerList.size() == 0){
                                Log.e(TAG,"No Answr For "+inParam);
                                mFeedBackAnswersMap.put(inParam,null);
                            }else{
                                mFeedBackAnswersMap.put(inParam,answerList);
                            }
                        }
                    }
                    return true;
                }
            }
        }else if(service.equals(UtilsClass.POST_SAVE_FEEDBACK)){
            Helper mHelper = new Helper(this);
            mHelper.openDataBase();
            AgentDetails mAgentDetails = mHelper.getAgentDetails();
            mHelper.close();
            inParam = webService.CreateJsonForFeedBackDetails(mAgentDetails,mAnswersMap);
            result = webService.callService(WebService.ServiceAPI.POST_SAVE_FEEDBACK,inParam);
            if(result == null || result.length() == 0){
                Log.e(TAG,"PostFeedback result is null");
            }else{
                String response = jParser.parsingFeedbackSaveResults(result);
                if(response == null || response.length() == 0 || response.equals("false")){
                    Log.e(TAG,"SaveFB Response is null");
                }else{
                    return true;
                }
            }
        }
        return super.callService(service);
    }


    @Override
    public void updateUI(boolean isCompleted, String mService) {
        super.updateUI(isCompleted,mService);
        if(isCompleted) {
            if (mService.equals(UtilsClass.GET_FEEDBACK_DETAILS)) {
                setQuestionsListAdapter();
            }else if(mService.equals(UtilsClass.POST_SAVE_FEEDBACK)){
                Toast.makeText(FieldInspectionActivity.this, "Feedback Saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        }else{
            Toast.makeText(FieldInspectionActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void setQuestionsListAdapter() {
        vL_afi_questionsList.setAdapter(new QuestionsAdapter());
        setListViewHeightBasedOnChildren(vL_afi_questionsList);
    }

    private class QuestionsAdapter extends BaseAdapter{
        ViewGroup.LayoutParams layoutParam;

        public QuestionsAdapter(){
            mAnswersMap = new HashMap<>();
            layoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        @Override
        public int getCount() {
            if(mFeedBackDetailsList == null)
                return 0;
            return mFeedBackDetailsList.size();
        }

        @Override
        public FeedBackDetails getItem(int position) {
            return mFeedBackDetailsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.adapter_inspection,parent, false);
            }

            TextView vT_ai_questionLbl = (TextView) convertView.findViewById(R.id.vT_ai_questionLbl);
            RadioGroup vR_ai_radioGroup = (RadioGroup) convertView.findViewById(R.id.vR_ai_radioGroup);
            LinearLayout vL_ai_multipleChoiseLayout = (LinearLayout) convertView.findViewById(R.id.vL_ai_multipleChoiseLayout);
            LinearLayout vL_ai_subjectiveLayout = (LinearLayout) convertView.findViewById(R.id.vL_ai_subjectiveLayout);
            EditText vE_ai_subjectiveAnswers = (EditText) convertView.findViewById(R.id.vE_ai_subjectiveAnswers);

            FeedBackDetails fd = getItem(position);
            vT_ai_questionLbl.setText(fd.getmQuestion());

            final String mQuestionId = fd.getmId();
            final String mQuestionFromat = fd.getmAnswerFormat();
            if(mFeedBackAnswersMap.containsKey(mQuestionId)) {
                List<FeedBackAnswerDetails> mQuestionsAnswers = mFeedBackAnswersMap.get(mQuestionId);
                if(mQuestionsAnswers == null || mQuestionsAnswers.size() == 0){
                    Log.e(TAG,"In List Answers for"+mQuestionId);
                }else {
                    for (final FeedBackAnswerDetails answer : mQuestionsAnswers) {
                        if (UtilsClass.Question_Format_SC.equals(mQuestionFromat)) {       //Single Choise Question
                            vR_ai_radioGroup.setVisibility(View.VISIBLE);
                            vR_ai_radioGroup.addView(getRadioButton(answer), layoutParam);

                            vR_ai_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    mAnswersMap.put(mQuestionId,""+checkedId);
                                }
                            });
                        } else if (UtilsClass.Question_Format_MC.equals(mQuestionFromat)) { //Multiple Choise Question
                            vL_ai_multipleChoiseLayout.setVisibility(View.VISIBLE);
                            CheckBox checkBox = getChackBox(answer);
                            vL_ai_multipleChoiseLayout.addView(checkBox, layoutParam);
                            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if(isChecked){
                                        if(mAnswersMap.containsKey(mQuestionId)){
                                            String answers = mAnswersMap.get(mQuestionId);
                                            mAnswersMap.put(mQuestionId,answers+","+answer.getmId());
                                        }else {
                                            mAnswersMap.put(mQuestionId, answer.getmId());
                                        }
                                    }else{
                                        if(mAnswersMap.containsKey(mQuestionId)){
                                            mAnswersMap.remove(mQuestionId);
                                        }
                                    }
                                }
                            });
                        } else if (UtilsClass.Question_Format_SQ.equals(mQuestionFromat)) {  //Single Choise Question
                            vL_ai_subjectiveLayout.setVisibility(View.VISIBLE);
                            vE_ai_subjectiveAnswers.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                    mAnswersMap.put(mQuestionId,s.toString());
                                }
                            });
                        }
                    }
                }
            }
            return convertView;
        }

        private CheckBox getChackBox(FeedBackAnswerDetails answer) {
            int checkboxId = Integer.parseInt(answer.getmId());
            CheckBox cb = new CheckBox(FieldInspectionActivity.this);
            cb.setId(checkboxId);
            cb.setTextColor(getResources().getColor(R.color.white));
            cb.setText(answer.getmAnswer());
            return cb;
        }

        private RadioButton getRadioButton(FeedBackAnswerDetails answer) {
            int radioButtonId = Integer.parseInt(answer.getmId());
            RadioButton rb = new RadioButton(FieldInspectionActivity.this);
            rb.setId(radioButtonId);
            rb.setTextColor(getResources().getColor(R.color.white));
            rb.setText(answer.getmAnswer());
            return rb;
        }
    }

    @Override
    protected void updateCaptions() {
        //Toast.makeText(FieldInspectionActivity.this, "Language is Applying", Toast.LENGTH_SHORT).show();
        if(languageCaptionsMap == null || languageCaptionsMap.size() == 0){
            return;
        }
        if(languageCaptionsMap.containsKey(CaptionsKey.KEY_SUBMIT)){
            vT_afi_btnSubmit.setText(languageCaptionsMap.get(CaptionsKey.KEY_SUBMIT));
        }
    }

}
