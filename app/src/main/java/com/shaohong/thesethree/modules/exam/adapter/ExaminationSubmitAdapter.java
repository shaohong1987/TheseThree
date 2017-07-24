package com.shaohong.thesethree.modules.exam.adapter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.activities.MainActivity;
import com.shaohong.thesethree.bean.Exam;
import com.shaohong.thesethree.bean.Paper;
import com.shaohong.thesethree.bean.UserAnswer;
import com.shaohong.thesethree.database.DbManager;
import com.shaohong.thesethree.model.ExamModel;
import com.shaohong.thesethree.model.UserModel;
import com.shaohong.thesethree.modules.exam.ExamActivity;
import com.shaohong.thesethree.myview.MyGridView;
import com.shaohong.thesethree.utils.ConstantUtils;
import com.shaohong.thesethree.utils.ContextUtils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ExaminationSubmitAdapter extends PagerAdapter {

    ExamActivity mContext;
    List<View> viewItems;
    View convertView;
    Exam mExam;
    private HashMap<String, String> userInfo;

    public ExaminationSubmitAdapter(ExamActivity context, List<View> viewItems, Exam exam) {
        mContext = context;
        this.viewItems = viewItems;
        userInfo = UserModel.getUserInfoMore(mContext);
        mExam = exam;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewItems.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final ViewHolder holder = new ViewHolder();
        convertView = viewItems.get(position);
        holder.questionType = (TextView) convertView.findViewById(R.id.activity_prepare_test_no);
        holder.question = (TextView) convertView.findViewById(R.id.activity_prepare_test_question);
        holder.previousBtn = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_upLayout);
        holder.nextBtn = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_nextLayout);
        holder.totalBtn = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_totalLayout);
        holder.nextText = (TextView) convertView.findViewById(R.id.menu_bottom_nextTV);
        holder.totalText = (TextView) convertView.findViewById(R.id.activity_prepare_test_totalTv);
        holder.nextImage = (ImageView) convertView.findViewById(R.id.menu_bottom_nextIV);
        holder.questionBtn = (LinearLayout) convertView.findViewById(R.id.activity_question_test_totalLayout);
        holder.layoutA = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_layout_a);
        holder.layoutB = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_layout_b);
        holder.layoutC = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_layout_c);
        holder.layoutD = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_layout_d);
        holder.layoutE = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_layout_e);
        holder.layoutF = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_layout_f);
        holder.layoutG = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_layout_g);
        holder.layoutH = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_layout_h);
        holder.layoutI = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_layout_i);
        holder.layoutJ = (LinearLayout) convertView.findViewById(R.id.activity_prepare_test_layout_j);
        holder.cbA = (CheckBox) convertView.findViewById(R.id.vote_submit_select_image_a);
        holder.cbB = (CheckBox) convertView.findViewById(R.id.vote_submit_select_image_b);
        holder.cbC = (CheckBox) convertView.findViewById(R.id.vote_submit_select_image_c);
        holder.cbD = (CheckBox) convertView.findViewById(R.id.vote_submit_select_image_d);
        holder.cbE = (CheckBox) convertView.findViewById(R.id.vote_submit_select_image_e);
        holder.cbF = (CheckBox) convertView.findViewById(R.id.vote_submit_select_image_f);
        holder.cbG = (CheckBox) convertView.findViewById(R.id.vote_submit_select_image_g);
        holder.cbH = (CheckBox) convertView.findViewById(R.id.vote_submit_select_image_h);
        holder.cbI = (CheckBox) convertView.findViewById(R.id.vote_submit_select_image_i);
        holder.cbJ = (CheckBox) convertView.findViewById(R.id.vote_submit_select_image_j);
        holder.tvA = (TextView) convertView.findViewById(R.id.vote_submit_select_text_a);
        holder.tvB = (TextView) convertView.findViewById(R.id.vote_submit_select_text_b);
        holder.tvC = (TextView) convertView.findViewById(R.id.vote_submit_select_text_c);
        holder.tvD = (TextView) convertView.findViewById(R.id.vote_submit_select_text_d);
        holder.tvE = (TextView) convertView.findViewById(R.id.vote_submit_select_text_e);
        holder.tvF = (TextView) convertView.findViewById(R.id.vote_submit_select_text_f);
        holder.tvG = (TextView) convertView.findViewById(R.id.vote_submit_select_text_g);
        holder.tvH = (TextView) convertView.findViewById(R.id.vote_submit_select_text_h);
        holder.tvI = (TextView) convertView.findViewById(R.id.vote_submit_select_text_i);
        holder.tvJ = (TextView) convertView.findViewById(R.id.vote_submit_select_text_j);
        holder.totalText.setText(position + 1 + "/" + ContextUtils.mPapers.size());

        if (ContextUtils.mPapers.get(position).getItemA().equals("")) {
            holder.layoutA.setVisibility(View.GONE);
        }
        if (ContextUtils.mPapers.get(position).getItemB().equals("")) {
            holder.layoutB.setVisibility(View.GONE);
        }
        if (ContextUtils.mPapers.get(position).getItemC().equals("")) {
            holder.layoutC.setVisibility(View.GONE);
        }
        if (ContextUtils.mPapers.get(position).getItemD().equals("")) {
            holder.layoutD.setVisibility(View.GONE);
        }
        if (ContextUtils.mPapers.get(position).getItemE().equals("")) {
            holder.layoutE.setVisibility(View.GONE);
        }
        if (ContextUtils.mPapers.get(position).getItemF().equals("")) {
            holder.layoutF.setVisibility(View.GONE);
        }
        if (ContextUtils.mPapers.get(position).getItemG().equals("")) {
            holder.layoutG.setVisibility(View.GONE);
        }
        if (ContextUtils.mPapers.get(position).getItemH().equals("")) {
            holder.layoutH.setVisibility(View.GONE);
        }
        if (ContextUtils.mPapers.get(position).getItemI().equals("")) {
            holder.layoutI.setVisibility(View.GONE);
        }
        if (ContextUtils.mPapers.get(position).getItemJ().equals("")) {
            holder.layoutJ.setVisibility(View.GONE);
        }

        holder.tvA.setVisibility(View.VISIBLE);
        holder.tvB.setVisibility(View.VISIBLE);
        holder.tvC.setVisibility(View.VISIBLE);
        holder.tvD.setVisibility(View.VISIBLE);
        holder.tvE.setVisibility(View.VISIBLE);
        holder.tvF.setVisibility(View.VISIBLE);
        holder.tvG.setVisibility(View.VISIBLE);
        holder.tvH.setVisibility(View.VISIBLE);
        holder.tvI.setVisibility(View.VISIBLE);
        holder.tvJ.setVisibility(View.VISIBLE);

        holder.tvA.setText("A." + ContextUtils.mPapers.get(position).getItemA());
        holder.tvB.setText("B." + ContextUtils.mPapers.get(position).getItemB());
        holder.tvC.setText("C." + ContextUtils.mPapers.get(position).getItemC());
        holder.tvD.setText("D." + ContextUtils.mPapers.get(position).getItemD());
        holder.tvE.setText("E." + ContextUtils.mPapers.get(position).getItemE());
        holder.tvE.setText("F." + ContextUtils.mPapers.get(position).getItemF());
        holder.tvE.setText("G." + ContextUtils.mPapers.get(position).getItemG());
        holder.tvE.setText("H." + ContextUtils.mPapers.get(position).getItemH());
        holder.tvE.setText("I." + ContextUtils.mPapers.get(position).getItemI());
        holder.tvE.setText("J." + ContextUtils.mPapers.get(position).getItemJ());

        String answer = ContextUtils.mPapers.get(position).getUserAnswer();
        if (!answer.isEmpty() && answer.length() > 0) {
            holder.cbA.setChecked(answer.contains("A"));
            holder.cbB.setChecked(answer.contains("B"));
            holder.cbC.setChecked(answer.contains("C"));
            holder.cbD.setChecked(answer.contains("D"));
            holder.cbE.setChecked(answer.contains("E"));
            holder.cbF.setChecked(answer.contains("F"));
            holder.cbG.setChecked(answer.contains("G"));
            holder.cbH.setChecked(answer.contains("H"));
            holder.cbI.setChecked(answer.contains("I"));
            holder.cbJ.setChecked(answer.contains("J"));
        }

        final int eT = ContextUtils.mPapers.get(position).getExerciseType();
        if (eT == 1) {
            holder.question.setText("(单选题)" + ContextUtils.mPapers.get(position).getQuestion());
        } else if (eT == 2) {
            holder.question.setText("(多选题)" + ContextUtils.mPapers.get(position).getQuestion());
        } else if (eT == 3) {
            holder.question.setText("(判断题)" + ContextUtils.mPapers.get(position).getQuestion());
        }
        holder.layoutA.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                boolean cbState = !holder.cbA.isChecked();
                holder.cbA.setChecked(cbState);
                if (cbState && (eT == 1) || eT == 3) {
                    holder.cbB.setChecked(!cbState);
                    holder.cbC.setChecked(!cbState);
                    holder.cbD.setChecked(!cbState);
                    holder.cbE.setChecked(!cbState);
                    holder.cbF.setChecked(!cbState);
                    holder.cbG.setChecked(!cbState);
                    holder.cbH.setChecked(!cbState);
                    holder.cbI.setChecked(!cbState);
                    holder.cbJ.setChecked(!cbState);
                }
            }
        });
        holder.layoutB.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                boolean cbState = !holder.cbB.isChecked();
                holder.cbB.setChecked(cbState);
                if (cbState && (eT == 1) || eT == 3) {
                    holder.cbA.setChecked(!cbState);
                    holder.cbC.setChecked(!cbState);
                    holder.cbD.setChecked(!cbState);
                    holder.cbE.setChecked(!cbState);
                    holder.cbF.setChecked(!cbState);
                    holder.cbG.setChecked(!cbState);
                    holder.cbH.setChecked(!cbState);
                    holder.cbI.setChecked(!cbState);
                    holder.cbJ.setChecked(!cbState);
                }
            }
        });
        holder.layoutC.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                boolean cbState = !holder.cbC.isChecked();
                holder.cbC.setChecked(cbState);
                if (cbState && (eT == 1) || eT == 3) {
                    holder.cbA.setChecked(!cbState);
                    holder.cbB.setChecked(!cbState);
                    holder.cbD.setChecked(!cbState);
                    holder.cbE.setChecked(!cbState);
                    holder.cbF.setChecked(!cbState);
                    holder.cbG.setChecked(!cbState);
                    holder.cbH.setChecked(!cbState);
                    holder.cbI.setChecked(!cbState);
                    holder.cbJ.setChecked(!cbState);
                }
            }
        });
        holder.layoutD.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                boolean cbState = !holder.cbD.isChecked();
                holder.cbD.setChecked(cbState);
                if (cbState && (eT == 1) || eT == 3) {
                    holder.cbA.setChecked(!cbState);
                    holder.cbB.setChecked(!cbState);
                    holder.cbC.setChecked(!cbState);
                    holder.cbE.setChecked(!cbState);
                    holder.cbF.setChecked(!cbState);
                    holder.cbG.setChecked(!cbState);
                    holder.cbH.setChecked(!cbState);
                    holder.cbI.setChecked(!cbState);
                    holder.cbJ.setChecked(!cbState);
                }
            }
        });
        holder.layoutE.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                boolean cbState = !holder.cbE.isChecked();
                holder.cbE.setChecked(cbState);
                if (cbState && (eT == 1) || eT == 3) {
                    holder.cbA.setChecked(!cbState);
                    holder.cbB.setChecked(!cbState);
                    holder.cbC.setChecked(!cbState);
                    holder.cbD.setChecked(!cbState);
                    holder.cbF.setChecked(!cbState);
                    holder.cbG.setChecked(!cbState);
                    holder.cbH.setChecked(!cbState);
                    holder.cbI.setChecked(!cbState);
                    holder.cbJ.setChecked(!cbState);
                }
            }
        });
        holder.layoutF.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                boolean cbState = !holder.cbF.isChecked();
                holder.cbF.setChecked(cbState);
                if (cbState && (eT == 1) || eT == 3) {
                    holder.cbA.setChecked(!cbState);
                    holder.cbB.setChecked(!cbState);
                    holder.cbC.setChecked(!cbState);
                    holder.cbD.setChecked(!cbState);
                    holder.cbE.setChecked(!cbState);
                    holder.cbG.setChecked(!cbState);
                    holder.cbH.setChecked(!cbState);
                    holder.cbI.setChecked(!cbState);
                    holder.cbJ.setChecked(!cbState);
                }
            }
        });
        holder.layoutG.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                boolean cbState = !holder.cbG.isChecked();
                holder.cbG.setChecked(cbState);
                if (cbState && (eT == 1) || eT == 3) {
                    holder.cbA.setChecked(!cbState);
                    holder.cbB.setChecked(!cbState);
                    holder.cbC.setChecked(!cbState);
                    holder.cbD.setChecked(!cbState);
                    holder.cbE.setChecked(!cbState);
                    holder.cbF.setChecked(!cbState);
                    holder.cbH.setChecked(!cbState);
                    holder.cbI.setChecked(!cbState);
                    holder.cbJ.setChecked(!cbState);
                }
            }
        });
        holder.layoutH.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                boolean cbState = !holder.cbH.isChecked();
                holder.cbH.setChecked(cbState);
                if (cbState && (eT == 1) || eT == 3) {
                    holder.cbA.setChecked(!cbState);
                    holder.cbB.setChecked(!cbState);
                    holder.cbC.setChecked(!cbState);
                    holder.cbD.setChecked(!cbState);
                    holder.cbE.setChecked(!cbState);
                    holder.cbF.setChecked(!cbState);
                    holder.cbG.setChecked(!cbState);
                    holder.cbI.setChecked(!cbState);
                    holder.cbJ.setChecked(!cbState);
                }
            }
        });
        holder.layoutI.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                boolean cbState = !holder.cbI.isChecked();
                holder.cbI.setChecked(cbState);
                if (cbState && (eT == 1) || eT == 3) {
                    holder.cbA.setChecked(!cbState);
                    holder.cbB.setChecked(!cbState);
                    holder.cbC.setChecked(!cbState);
                    holder.cbD.setChecked(!cbState);
                    holder.cbE.setChecked(!cbState);
                    holder.cbF.setChecked(!cbState);
                    holder.cbG.setChecked(!cbState);
                    holder.cbH.setChecked(!cbState);
                    holder.cbJ.setChecked(!cbState);
                }
            }
        });
        holder.layoutJ.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                boolean cbState = !holder.cbJ.isChecked();
                holder.cbJ.setChecked(cbState);
                if (cbState && (eT == 1) || eT == 3) {
                    holder.cbA.setChecked(!cbState);
                    holder.cbB.setChecked(!cbState);
                    holder.cbC.setChecked(!cbState);
                    holder.cbD.setChecked(!cbState);
                    holder.cbE.setChecked(!cbState);
                    holder.cbF.setChecked(!cbState);
                    holder.cbG.setChecked(!cbState);
                    holder.cbH.setChecked(!cbState);
                    holder.cbI.setChecked(!cbState);
                }
            }
        });
        ForegroundColorSpan blueSpan = new ForegroundColorSpan(Color.parseColor("#2b89e9"));
        SpannableStringBuilder builder1 = new SpannableStringBuilder(holder.question.getText().toString());
        builder1.setSpan(blueSpan, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.question.setText(builder1);

        if (position == viewItems.size() - 1) {
            holder.nextText.setText("提交");
            holder.nextImage.setImageResource(R.drawable.vote_submit_finish);
        }
        holder.previousBtn.setOnClickListener(new LinearOnClickListener(position - 1, position, holder, 0));
        holder.nextBtn.setOnClickListener(new LinearOnClickListener(position + 1, position, holder, 0));
        holder.questionBtn.setOnClickListener(new LinearOnClickListener(position + 1, position, holder, 1));
        holder.totalBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Save(holder, position,0);
                View popupView = mContext.getLayoutInflater().inflate(R.layout.popupwindow, null);
                PopupWindow window = new PopupWindow(popupView, RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT, true);
                MyGridView gridView = (MyGridView) popupView.findViewById(R.id.item_grid_view_paper);
                gridView.setAdapter(new MyAdapter(mContext, ContextUtils.mPapers, window));
                //window.setAnimationStyle(R.style.popup_window_anim);
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
                window.setFocusable(true);
                window.setOutsideTouchable(true);
                window.update();
                window.showAtLocation(mContext.getLayoutInflater().inflate(R.layout.activity_exam, null), Gravity
                        .BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 144);
            }
        });

        container.addView(viewItems.get(position));
        return viewItems.get(position);
    }

    private void Save(ViewHolder viewHolder, int position, int type) {
        String result = "";
        if (viewHolder.cbA.isChecked()) {
            result += "A";
        }
        if (viewHolder.cbB.isChecked()) {
            result += "B";
        }
        if (viewHolder.cbC.isChecked()) {
            result += "C";
        }
        if (viewHolder.cbD.isChecked()) {
            result += "D";
        }
        if (viewHolder.cbE.isChecked()) {
            result += "E";
        }
        if (viewHolder.cbF.isChecked()) {
            result += "F";
        }
        if (viewHolder.cbG.isChecked()) {
            result += "G";
        }
        if (viewHolder.cbH.isChecked()) {
            result += "H";
        }
        if (viewHolder.cbI.isChecked()) {
            result += "I";
        }
        if (viewHolder.cbJ.isChecked()) {
            result += "J";
        }
        ContextUtils.mPapers.get(position).setN(type);
        ContextUtils.mPapers.get(position).setUserAnswer(result);
    }

    private void SaveAndUpload() {
        List<UserAnswer> list = new ArrayList<>();
        DbManager db = new DbManager(mContext);
        db.openDB();
        db.deleteLibraryAllData(ContextUtils.testId);
        for (int i = 0; i < ContextUtils.mPapers.size(); i++) {
            Paper paper = ContextUtils.mPapers.get(i);
            //写本地数据库
            db.insertLibrary(paper);
            //生成答案列表
            UserAnswer userAnswer = new UserAnswer();
            userAnswer.answer = paper.getUserAnswer();
            userAnswer.isright = paper.getAnswer().equals(paper.getUserAnswer()) ? 1 : 0;
            userAnswer.score = paper.getScroe();
            userAnswer.seq = paper.getSeq();
            userAnswer.testid = ContextUtils.testId;
            userAnswer.timuid = paper.getId();
            list.add(userAnswer);
        }
        ContextUtils.mUserAnswers = list;
        db.closeDB();
        if (ContextUtils.mUserAnswers != null && ContextUtils.mUserAnswers.size() > 0) {
            new UploadDataThread().start();
        }
    }

    class LinearOnClickListener implements OnClickListener {
        private int mPosition;
        private int mPosition1;
        private ViewHolder viewHolder;
        private int t;

        public LinearOnClickListener(int position, int position1, ViewHolder viewHolder, int type) {
            mPosition = position;
            mPosition1 = position1;
            this.viewHolder = viewHolder;
            t = type;
        }

        @Override
        public void onClick(View v) {
            Save(viewHolder, mPosition1, t);
            if (mPosition == viewItems.size()) {
                SaveAndUpload();
            } else {
                if (mPosition == -1) {
                    Toast.makeText(mContext, "已经是第一题", Toast.LENGTH_SHORT).show();
                } else {
                    mContext.setCurrentView(mPosition);
                }
            }
        }
    }

    @Override
    public int getCount() {
        if (viewItems == null)
            return 0;
        return viewItems.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    public class ViewHolder {
        TextView questionType;
        TextView question;
        LinearLayout previousBtn, nextBtn, totalBtn, questionBtn;
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
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(mContext, "交卷成功", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的activity
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//设置不要刷新将要跳转的界面
                    mContext.startActivity(intent);
                    break;
            }
        }
    };

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
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = layoutInflater.inflate(R.layout.item_grid_view_paper, null);
            Button button = (Button) v.findViewById(R.id.no_tm_button);
            button.setText(String.valueOf(position + 1));
            Paper paper = data.get(position);
            if (paper.getUserAnswer().isEmpty()) {
                if (paper.getN() == (1)) {
                    button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.yellow));
                } else
                    button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorWhite));
            } else {
                if (paper.getN() == (1)) {
                    button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.yellow));
                } else
                    button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.status_timu_1));
            }
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mView.dismiss();
                    mContext.setCurrentView(position);
                }
            });
            return v;
        }

    }

    class UploadDataThread extends Thread {
        @Override
        public void run() {
            try {
                if (ContextUtils.isLogin) {
                    ExamModel.UploadPaper(mContext);
                    new JJUdpUtils().start();
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(1);//通过handler发送一个更新数据的标记
        }
    }

    class JJUdpUtils extends Thread {
        @Override
        public void run() {
            try {
                InetAddress address = InetAddress.getByName(ConstantUtils.UDP_SERVER_URL);
                JSONObject object = new JSONObject();
                object.put("type", 3);
                object.put("code", 2);
                object.put("usertype", 3);
                object.put("testcode", mExam.getId());
                object.put("userid", userInfo.get("userid"));
                object.put("name", userInfo.get("name"));
                byte[] data = object.toString().getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, address, ConstantUtils.UDP_PORT);
                DatagramSocket socket = new DatagramSocket();
                socket.send(packet);
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
