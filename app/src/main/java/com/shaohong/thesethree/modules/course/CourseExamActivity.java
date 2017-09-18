package com.shaohong.thesethree.modules.course;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.bean.Paper;
import com.shaohong.thesethree.model.CourseModel;
import com.shaohong.thesethree.myview.MyGridView;
import com.shaohong.thesethree.utils.ConstantUtils;
import com.shaohong.thesethree.utils.ContextUtils;
import com.shaohong.thesethree.utils.SharedPreferencesHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class CourseExamActivity extends AppCompatActivity {

    private List<Paper> mPapers;
    private int userid;
    private int eduid;
    TextView questionAnli;
    TextView questionType;
    TextView question;
    LinearLayout previousBtn, nextBtn, totalBtn, questionBtn;
    TextView nextText;
    TextView totalText;
    ImageView nextImage;
    TextView questionButtonT;
    LinearLayout layoutA;
    LinearLayout layoutB;
    LinearLayout layoutC;
    LinearLayout layoutD;
    LinearLayout layoutE;
    LinearLayout layoutF;
    LinearLayout layoutG;
    LinearLayout layoutH;
    LinearLayout layoutI;
    LinearLayout layoutJ;
    CheckBox cbA;
    CheckBox cbB;
    CheckBox cbC;
    CheckBox cbD;
    CheckBox cbE;
    CheckBox cbF;
    CheckBox cbG;
    CheckBox cbH;
    CheckBox cbI;
    CheckBox cbJ;
    TextView tvA;
    TextView tvB;
    TextView tvC;
    TextView tvD;
    TextView tvE;
    TextView tvF;
    TextView tvG;
    TextView tvH;
    TextView tvI;
    TextView tvJ;
    public int position = 0;
    private int cent = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        mPapers = (List<Paper>) bundle.get(ConstantUtils.EXAM_INFO);
        setContentView(R.layout.activity_course_exam);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(getApplicationContext());
        userid = (int) sharedPreferencesHelper.get("userid", -1);
        eduid = mPapers.get(0).getTestId();
        questionAnli = (TextView) findViewById(R.id.activity_prepare_test_anli_course);
        questionType = (TextView) findViewById(R.id.activity_prepare_test_no_course);
        question = (TextView) findViewById(R.id.activity_prepare_test_question_course);
        previousBtn = (LinearLayout) findViewById(R.id.activity_prepare_test_upLayout_course);
        nextBtn = (LinearLayout) findViewById(R.id.activity_prepare_test_nextLayout_course);
        totalBtn = (LinearLayout) findViewById(R.id.activity_prepare_test_totalLayout_course);
        questionBtn = (LinearLayout) findViewById(R.id.activity_question_course_totalLayout);
        questionButtonT = (TextView) findViewById(R.id.question_course_button);
        nextText = (TextView) findViewById(R.id.menu_bottom_nextTV_course);
        totalText = (TextView) findViewById(R.id.activity_prepare_test_totalTv_course);
        nextImage = (ImageView) findViewById(R.id.menu_bottom_nextIV_course);
        layoutA = (LinearLayout) findViewById(R.id.activity_prepare_test_layout_a_course);
        layoutB = (LinearLayout) findViewById(R.id.activity_prepare_test_layout_b_course);
        layoutC = (LinearLayout) findViewById(R.id.activity_prepare_test_layout_c_course);
        layoutD = (LinearLayout) findViewById(R.id.activity_prepare_test_layout_d_course);
        layoutE = (LinearLayout) findViewById(R.id.activity_prepare_test_layout_e_course);
        layoutF = (LinearLayout) findViewById(R.id.activity_prepare_test_layout_f_course);
        layoutG = (LinearLayout) findViewById(R.id.activity_prepare_test_layout_g_course);
        layoutH = (LinearLayout) findViewById(R.id.activity_prepare_test_layout_h_course);
        layoutI = (LinearLayout) findViewById(R.id.activity_prepare_test_layout_i_course);
        layoutJ = (LinearLayout) findViewById(R.id.activity_prepare_test_layout_j_course);
        cbA = (CheckBox) findViewById(R.id.vote_submit_select_image_a_course);
        cbB = (CheckBox) findViewById(R.id.vote_submit_select_image_b_course);
        cbC = (CheckBox) findViewById(R.id.vote_submit_select_image_c_course);
        cbD = (CheckBox) findViewById(R.id.vote_submit_select_image_d_course);
        cbE = (CheckBox) findViewById(R.id.vote_submit_select_image_e_course);
        cbF = (CheckBox) findViewById(R.id.vote_submit_select_image_f_course);
        cbG = (CheckBox) findViewById(R.id.vote_submit_select_image_g_course);
        cbH = (CheckBox) findViewById(R.id.vote_submit_select_image_h_course);
        cbI = (CheckBox) findViewById(R.id.vote_submit_select_image_i_course);
        cbJ = (CheckBox) findViewById(R.id.vote_submit_select_image_j_course);
        tvA = (TextView) findViewById(R.id.vote_submit_select_text_a_course);
        tvB = (TextView) findViewById(R.id.vote_submit_select_text_b_course);
        tvC = (TextView) findViewById(R.id.vote_submit_select_text_c_course);
        tvD = (TextView) findViewById(R.id.vote_submit_select_text_d_course);
        tvE = (TextView) findViewById(R.id.vote_submit_select_text_e_course);
        tvF = (TextView) findViewById(R.id.vote_submit_select_text_f_course);
        tvG = (TextView) findViewById(R.id.vote_submit_select_text_g_course);
        tvH = (TextView) findViewById(R.id.vote_submit_select_text_h_course);
        tvI = (TextView) findViewById(R.id.vote_submit_select_text_i_course);
        tvJ = (TextView) findViewById(R.id.vote_submit_select_text_j_course);
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "";
                if (cbA.isChecked()) {
                    result += "A";
                }
                if (cbB.isChecked()) {
                    result += "B";
                }
                if (cbC.isChecked()) {
                    result += "C";
                }
                if (cbD.isChecked()) {
                    result += "D";
                }
                if (cbE.isChecked()) {
                    result += "E";
                }
                if (cbF.isChecked()) {
                    result += "F";
                }
                if (cbG.isChecked()) {
                    result += "G";
                }
                if (cbH.isChecked()) {
                    result += "H";
                }
                if (cbI.isChecked()) {
                    result += "I";
                }
                if (cbJ.isChecked()) {
                    result += "J";
                }
                mPapers.get(position).setUserAnswer(result);
                if (position == 0) {
                    Toast.makeText(getApplicationContext(), "已经是第一题", Toast.LENGTH_SHORT).show();
                } else {
                    position -= 1;
                    if (position < mPapers.size() - 1) {
                        nextText.setText("下一题");
                        nextImage.setImageResource(R.drawable.ic_arrow_forward_black_24dp);
                    }
                    initView();
                }
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "";
                if (cbA.isChecked()) {
                    result += "A";
                }
                if (cbB.isChecked()) {
                    result += "B";
                }
                if (cbC.isChecked()) {
                    result += "C";
                }
                if (cbD.isChecked()) {
                    result += "D";
                }
                if (cbE.isChecked()) {
                    result += "E";
                }
                if (cbF.isChecked()) {
                    result += "F";
                }
                if (cbG.isChecked()) {
                    result += "G";
                }
                if (cbH.isChecked()) {
                    result += "H";
                }
                if (cbI.isChecked()) {
                    result += "I";
                }
                if (cbJ.isChecked()) {
                    result += "J";
                }
                mPapers.get(position).setUserAnswer(result);
                if (position == mPapers.size() - 1) {
                    new LoadDataThread().start();
                } else {
                    position += 1;
                    if (position == mPapers.size() - 1) {
                        nextText.setText("提交");
                        nextImage.setImageResource(R.drawable.vote_submit_finish);
                    }
                    initView();
                }
            }
        });
        questionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "";
                if (cbA.isChecked()) {
                    result += "A";
                }
                if (cbB.isChecked()) {
                    result += "B";
                }
                if (cbC.isChecked()) {
                    result += "C";
                }
                if (cbD.isChecked()) {
                    result += "D";
                }
                if (cbE.isChecked()) {
                    result += "E";
                }
                if (cbF.isChecked()) {
                    result += "F";
                }
                if (cbG.isChecked()) {
                    result += "G";
                }
                if (cbH.isChecked()) {
                    result += "H";
                }
                if (cbI.isChecked()) {
                    result += "I";
                }
                if (cbJ.isChecked()) {
                    result += "J";
                }
                mPapers.get(position).setUserAnswer(result);
                if (mPapers.get(position).getN() == (1)) {
                    mPapers.get(position).setN(0);
                } else
                    mPapers.get(position).setN(1);
                initView();
            }
        });
        totalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupView = getLayoutInflater().inflate(R.layout.popupwindow, null);
                PopupWindow window = new PopupWindow(popupView, RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT, true);
                MyGridView gridView = (MyGridView) popupView.findViewById(R.id.item_grid_view_paper);
                gridView.setAdapter(new MyAdapter(getApplicationContext(), mPapers, window));
                //window.setAnimationStyle(R.style.popup_window_anim);
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
                window.setFocusable(true);
                window.setOutsideTouchable(true);
                window.update();
                window.showAtLocation(getLayoutInflater().inflate(R.layout.activity_course_exam, null), Gravity
                        .BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 144);
            }
        });
        initView();
    }


    public void initView() {
        totalText.setText(position + 1 + "/" + mPapers.size());
        if (!mPapers.get(position).getItemA().isEmpty()) {
            layoutA.setVisibility(View.VISIBLE);
            tvA.setText("A." + mPapers.get(position).getItemA());
        } else {
            layoutA.setVisibility(View.GONE);
            tvA.setText("");
        }
        if (!mPapers.get(position).getItemB().isEmpty()) {
            layoutB.setVisibility(View.VISIBLE);
            tvB.setText("B." + mPapers.get(position).getItemB());
        } else {
            layoutB.setVisibility(View.GONE);
            tvB.setText("");
        }
        if (!mPapers.get(position).getItemC().isEmpty()) {
            layoutC.setVisibility(View.VISIBLE);
            tvC.setText("C." + mPapers.get(position).getItemC());
        } else {
            layoutC.setVisibility(View.GONE);
            tvC.setText("");
        }
        if (!mPapers.get(position).getItemD().isEmpty()) {
            layoutD.setVisibility(View.VISIBLE);
            tvD.setText("D." + mPapers.get(position).getItemD());
        } else {
            layoutD.setVisibility(View.GONE);
            tvD.setText("");
        }
        if (!mPapers.get(position).getItemE().isEmpty()) {
            layoutE.setVisibility(View.VISIBLE);
            tvE.setText("E." + mPapers.get(position).getItemE());
        } else {
            layoutE.setVisibility(View.GONE);
            tvE.setText("");
        }
        if (!mPapers.get(position).getItemF().isEmpty()) {
            layoutF.setVisibility(View.VISIBLE);
            tvF.setText("F." + mPapers.get(position).getItemF());
        } else {
            layoutF.setVisibility(View.GONE);
            tvF.setText("");
        }
        if (!mPapers.get(position).getItemG().isEmpty()) {
            layoutG.setVisibility(View.VISIBLE);
            tvG.setText("G." + mPapers.get(position).getItemG());
        } else {
            layoutG.setVisibility(View.GONE);
            tvG.setText("");
        }
        if (!mPapers.get(position).getItemH().isEmpty()) {
            layoutH.setVisibility(View.VISIBLE);
            tvH.setText("H." + mPapers.get(position).getItemH());
        } else {
            layoutH.setVisibility(View.GONE);
            tvH.setText("");
        }
        if (!mPapers.get(position).getItemI().isEmpty()) {
            layoutI.setVisibility(View.VISIBLE);
            tvI.setText("I." + mPapers.get(position).getItemI());
        } else {
            layoutI.setVisibility(View.GONE);
            tvI.setText("");
        }
        if (!mPapers.get(position).getItemJ().isEmpty()) {
            layoutJ.setVisibility(View.VISIBLE);
            tvJ.setText("J." + mPapers.get(position).getItemJ());
        } else {
            layoutJ.setVisibility(View.GONE);
            tvJ.setText("");
        }
        if (!mPapers.get(position).getAnli().isEmpty()) {
            questionAnli.setText(mPapers.get(position).getAnli());
        } else {
            questionAnli.setText("");
        }
        final int eT = mPapers.get(position).getExerciseType();
        if (eT == 1) {
            question.setText((position + 1) + ".(单选题)" + mPapers.get(position).getQuestion());
        } else if (eT == 2) {
            question.setText((position + 1) + ".(多选题)" + mPapers.get(position).getQuestion());
        } else if (eT == 3) {
            question.setText((position + 1) + ".(判断题)" + mPapers.get(position).getQuestion());
        }
        String a = mPapers.get(position).getUserAnswer();
        if (mPapers.get(position).getUserAnswer() != null && mPapers.get(position).getUserAnswer().contains("A")) {
            cbA.setChecked(true);
        } else {
            cbA.setChecked(false);
        }
        if (mPapers.get(position).getUserAnswer() != null && mPapers.get(position).getUserAnswer().contains("B")) {
            cbB.setChecked(true);
        } else {
            cbB.setChecked(false);
        }
        if (mPapers.get(position).getUserAnswer() != null && mPapers.get(position).getUserAnswer().contains("C")) {
            cbC.setChecked(true);
        } else {
            cbC.setChecked(false);
        }
        if (mPapers.get(position).getUserAnswer() != null && mPapers.get(position).getUserAnswer().contains("D")) {
            cbD.setChecked(true);
        } else {
            cbD.setChecked(false);
        }
        if (mPapers.get(position).getUserAnswer() != null && mPapers.get(position).getUserAnswer().contains("E")) {
            cbE.setChecked(true);
        } else {
            cbE.setChecked(false);
        }
        if (mPapers.get(position).getUserAnswer() != null && mPapers.get(position).getUserAnswer().contains("F")) {
            cbF.setChecked(true);
        } else {
            cbF.setChecked(false);
        }
        if (mPapers.get(position).getUserAnswer() != null && mPapers.get(position).getUserAnswer().contains("G")) {
            cbG.setChecked(true);
        } else {
            cbG.setChecked(false);
        }
        if (mPapers.get(position).getUserAnswer() != null && mPapers.get(position).getUserAnswer().contains("H")) {
            cbH.setChecked(true);
        } else {
            cbH.setChecked(false);
        }
        if (mPapers.get(position).getUserAnswer() != null && mPapers.get(position).getUserAnswer().contains("I")) {
            cbI.setChecked(true);
        } else {
            cbI.setChecked(false);
        }
        if (mPapers.get(position).getUserAnswer() != null && mPapers.get(position).getUserAnswer().contains("J")) {
            cbJ.setChecked(true);
        } else {
            cbJ.setChecked(false);
        }

        if (mPapers.get(position).getN() == (1)) {
            questionButtonT.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
        } else
            questionButtonT.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            final Dialog builder = new Dialog(this, R.style.dialog);
            builder.setContentView(R.layout.my_dialog);
            TextView content = (TextView) builder.findViewById(R.id.dialog_content);
            content.setText("您要结束本次考试吗？");
            final Button confirm_btn = (Button) builder.findViewById(R.id.dialog_sure);
            Button cancel_btn = (Button) builder.findViewById(R.id.dialog_cancle);
            confirm_btn.setText("退出");
            cancel_btn.setText("继续答题");
            confirm_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new LoadDataThread().start();
                }
            });

            cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder.dismiss();
                }
            });
            builder.setCanceledOnTouchOutside(false);
            builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    return false;
                }
            });
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), CourseExamResultActivity.class);
                    intent.putExtra(ConstantUtils.EXAM_INFO, cent);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    break;
            }
        }
    };

    class LoadDataThread extends Thread {
        @Override
        public void run() {
            try {
                if (ContextUtils.isLogin) {
                    JSONArray jsonArray = new JSONArray();
                    int count = mPapers.size();
                    for (int i = 0; i < count; i++) {
                        JSONObject tmpObj = new JSONObject();
                        try {
                            tmpObj.put("answer", mPapers.get(i).getUserAnswer());
                            tmpObj.put("eduid", mPapers.get(i).getTestId());
                            tmpObj.put("isright", (mPapers.get(i).getAnswer().equals(mPapers.get(i).getUserAnswer())
                                    ? 1 : 0));
                            tmpObj.put("score", mPapers.get(i).getScroe());
                            tmpObj.put("seq", i);
                            tmpObj.put("timuid", mPapers.get(i).getId());
                            tmpObj.put("userid", userid);
                            cent += mPapers.get(i).getAnswer().equals(mPapers.get(i).getUserAnswer()) ? mPapers.get
                                    (i).getScroe() : 0;
                            jsonArray.put(tmpObj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    String data = jsonArray.toString();
                    CourseModel.PxJiaoJuan(data, String.valueOf(userid), String.valueOf(eduid));
                }
                Thread.sleep(1000);
            } catch (InterruptedException | JSONException | IOException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(1);
        }
    }

    class MyAdapter extends BaseAdapter {
        private List<Paper> data;
        private LayoutInflater layoutInflater;
        private PopupWindow mView;

        public MyAdapter(Context context, List<Paper> papers, PopupWindow view) {
            layoutInflater = LayoutInflater.from(context);
            data = papers;
            mView = view;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int p, View convertView, ViewGroup parent) {
            View v = layoutInflater.inflate(R.layout.item_grid_view_paper, null);
            Button button = (Button) v.findViewById(R.id.no_tm_button);
            button.setText(String.valueOf(p + 1));
            Paper paper = data.get(p);
            if (paper.getUserAnswer().isEmpty()) {
                if (paper.getN() == (1)) {
                    button.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                } else
                    button.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
            } else {
                if (paper.getN() == (1)) {
                    button.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                } else
                    button.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.status_timu_1));
            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mView.dismiss();
                    position = p;
                    initView();
                }
            });
            return v;
        }

    }
}
