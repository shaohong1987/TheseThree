package com.shaohong.thesethree.modules.personal;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.bean.Paper;
import com.shaohong.thesethree.utils.ConstantUtils;

import java.util.List;

public class MistakeBookActivity extends AppCompatActivity {

    private List<Paper> mPapers;
    TextView questionAnli;
    TextView questionType;
    TextView question;
    LinearLayout previousBtn, nextBtn, totalBtn;
    TextView nextText;
    TextView totalText;
    ImageView nextImage;
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
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mistake_book);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        mPapers = (List<Paper>) bundle.get(ConstantUtils.EXAM_INFO);
        questionAnli = (TextView) findViewById(R.id.activity_prepare_test_anli_mistake);
        questionType = (TextView) findViewById(R.id.activity_prepare_test_no_mistake);
        question = (TextView) findViewById(R.id.activity_prepare_test_question_mistake);
        previousBtn = (LinearLayout) findViewById(R.id.activity_prepare_test_upLayout_mistake);
        nextBtn = (LinearLayout) findViewById(R.id.activity_prepare_test_nextLayout_mistake);
        totalBtn = (LinearLayout) findViewById(R.id.activity_prepare_test_totalLayout_mistake);
        nextText = (TextView) findViewById(R.id.menu_bottom_nextTV_mistake);
        totalText = (TextView) findViewById(R.id.activity_prepare_test_totalTv_mistake);
        nextImage = (ImageView) findViewById(R.id.menu_bottom_nextIV_mistake);
        layoutA = (LinearLayout) findViewById(R.id.activity_prepare_test_layout_a_mistake);
        layoutB = (LinearLayout) findViewById(R.id.activity_prepare_test_layout_b_mistake);
        layoutC = (LinearLayout) findViewById(R.id.activity_prepare_test_layout_c_mistake);
        layoutD = (LinearLayout) findViewById(R.id.activity_prepare_test_layout_d_mistake);
        layoutE = (LinearLayout) findViewById(R.id.activity_prepare_test_layout_e_mistake);
        layoutF = (LinearLayout) findViewById(R.id.activity_prepare_test_layout_f_mistake);
        layoutG = (LinearLayout) findViewById(R.id.activity_prepare_test_layout_g_mistake);
        layoutH = (LinearLayout) findViewById(R.id.activity_prepare_test_layout_h_mistake);
        layoutI = (LinearLayout) findViewById(R.id.activity_prepare_test_layout_i_mistake);
        layoutJ = (LinearLayout) findViewById(R.id.activity_prepare_test_layout_j_mistake);
        cbA = (CheckBox) findViewById(R.id.vote_submit_select_image_a_mistake);
        cbB = (CheckBox) findViewById(R.id.vote_submit_select_image_b_mistake);
        cbC = (CheckBox) findViewById(R.id.vote_submit_select_image_c_mistake);
        cbD = (CheckBox) findViewById(R.id.vote_submit_select_image_d_mistake);
        cbE = (CheckBox) findViewById(R.id.vote_submit_select_image_e_mistake);
        cbF = (CheckBox) findViewById(R.id.vote_submit_select_image_f_mistake);
        cbG = (CheckBox) findViewById(R.id.vote_submit_select_image_g_mistake);
        cbH = (CheckBox) findViewById(R.id.vote_submit_select_image_h_mistake);
        cbI = (CheckBox) findViewById(R.id.vote_submit_select_image_i_mistake);
        cbJ = (CheckBox) findViewById(R.id.vote_submit_select_image_j_mistake);
        cbA.setEnabled(false);
        cbB.setEnabled(false);
        cbC.setEnabled(false);
        cbD.setEnabled(false);
        cbE.setEnabled(false);
        cbF.setEnabled(false);
        cbG.setEnabled(false);
        cbH.setEnabled(false);
        cbI.setEnabled(false);
        cbJ.setEnabled(false);
        tvA = (TextView) findViewById(R.id.vote_submit_select_text_a_mistake);
        tvB = (TextView) findViewById(R.id.vote_submit_select_text_b_mistake);
        tvC = (TextView) findViewById(R.id.vote_submit_select_text_c_mistake);
        tvD = (TextView) findViewById(R.id.vote_submit_select_text_d_mistake);
        tvE = (TextView) findViewById(R.id.vote_submit_select_text_e_mistake);
        tvF = (TextView) findViewById(R.id.vote_submit_select_text_f_mistake);
        tvG = (TextView) findViewById(R.id.vote_submit_select_text_g_mistake);
        tvH = (TextView) findViewById(R.id.vote_submit_select_text_h_mistake);
        tvI = (TextView) findViewById(R.id.vote_submit_select_text_i_mistake);
        tvJ = (TextView) findViewById(R.id.vote_submit_select_text_j_mistake);
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    Toast.makeText(getApplicationContext(), "已经是第一题", Toast.LENGTH_SHORT).show();
                } else {
                    position -= 1;
                    initView();
                }
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == mPapers.size() - 1) {
                    Toast.makeText(getApplicationContext(), "已经是最后一题", Toast.LENGTH_SHORT).show();
                } else {
                    position += 1;
                    initView();
                }
            }
        });
        initView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
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
        String userAnswer = mPapers.get(position).getUserAnswer();
        String answer = mPapers.get(position).getAnswer();

        if (userAnswer.contains("A")) {
            tvA.setTextColor(Color.RED);
        } else {
            tvA.setTextColor(Color.DKGRAY);
        }
        if (userAnswer.contains("B")) {
            tvB.setTextColor(Color.RED);
        } else {
            tvB.setTextColor(Color.DKGRAY);
        }
        if (userAnswer.contains("C")) {
            tvC.setTextColor(Color.RED);
        } else {
            tvC.setTextColor(Color.DKGRAY);
        }
        if (userAnswer.contains("D")) {
            tvD.setTextColor(Color.RED);
        } else {
            tvD.setTextColor(Color.DKGRAY);
        }
        if (userAnswer.contains("E")) {
            tvE.setTextColor(Color.RED);
        } else {
            tvE.setTextColor(Color.DKGRAY);
        }
        if (userAnswer.contains("F")) {
            tvF.setTextColor(Color.RED);
        } else {
            tvF.setTextColor(Color.DKGRAY);
        }
        if (userAnswer.contains("G")) {
            tvG.setTextColor(Color.RED);
        } else {
            tvG.setTextColor(Color.DKGRAY);
        }
        if (userAnswer.contains("H")) {
            tvH.setTextColor(Color.RED);
        } else {
            tvH.setTextColor(Color.DKGRAY);
        }
        if (userAnswer.contains("I")) {
            tvI.setTextColor(Color.RED);
        } else {
            tvI.setTextColor(Color.DKGRAY);
        }
        if (userAnswer.contains("J")) {
            tvJ.setTextColor(Color.RED);
        } else {
            tvJ.setTextColor(Color.DKGRAY);
        }
        if (answer.contains("A")) {
            cbA.setChecked(true);
        } else {
            cbA.setChecked(false);
        }
        if (answer.contains("B")) {
            cbB.setChecked(true);
        } else {
            cbB.setChecked(false);
        }
        if (answer.contains("C")) {
            cbC.setChecked(true);
        } else {
            cbC.setChecked(false);
        }
        if (answer.contains("D")) {
            cbD.setChecked(true);
        } else {
            cbD.setChecked(false);
        }
        if (answer.contains("E")) {
            cbE.setChecked(true);
        } else {
            cbE.setChecked(false);
        }
        if (answer.contains("F")) {
            cbF.setChecked(true);
        } else {
            cbF.setChecked(false);
        }
        if (answer.contains("G")) {
            cbG.setChecked(true);
        } else {
            cbG.setChecked(false);
        }
        if (answer.contains("H")) {
            cbH.setChecked(true);
        } else {
            cbH.setChecked(false);
        }
        if (answer.contains("I")) {
            cbI.setChecked(true);
        } else {
            cbI.setChecked(false);
        }
        if (answer.contains("J")) {
            cbJ.setChecked(true);
        } else {
            cbJ.setChecked(false);
        }
    }
}
