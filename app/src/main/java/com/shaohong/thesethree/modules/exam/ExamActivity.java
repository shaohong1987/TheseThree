package com.shaohong.thesethree.modules.exam;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.bean.Exam;
import com.shaohong.thesethree.bean.Paper;
import com.shaohong.thesethree.bean.SimpleExam;
import com.shaohong.thesethree.bean.UserAnswer;
import com.shaohong.thesethree.database.DbManager;
import com.shaohong.thesethree.model.ExamModel;
import com.shaohong.thesethree.model.UserModel;
import com.shaohong.thesethree.modules.exam.adapter.ExaminationSubmitAdapter;
import com.shaohong.thesethree.myview.ExamViewPager;
import com.shaohong.thesethree.utils.ConstantUtils;
import com.shaohong.thesethree.utils.ContextUtils;
import com.shaohong.thesethree.utils.ViewPagerScroller;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ExamActivity extends Activity {
    ExamViewPager viewPager;
    ExaminationSubmitAdapter pagerAdapter;
    List<View> viewItems = new ArrayList<>();
    Exam mExam;
    Timer timer;
    TimerTask timerTask;
    int minute = 0;
    int second = 0;
    int isFirst;
    private Date outTime;
    private TextView right;
    private HashMap<String, String> userInfo;
    private boolean flag = true;
    private String result;
    private int type;

    Handler handlerTime = new Handler() {
        public void handleMessage(Message msg) {
            if (minute < 2) {
                right.setTextColor(Color.RED);
            } else {
                right.setTextColor(Color.WHITE);
            }
            if (minute == 0) {
                if (second == 0) {
                    isFirst += 1;
                    if (isFirst == 1) {
                        Toast.makeText(getApplicationContext(), "考试结束，正在进行自动交卷", Toast.LENGTH_SHORT).show();
                        type = 1;
                        uploadExamination();
                    }
                    right.setText("00:00");
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                    if (timerTask != null) {
                        timerTask = null;
                    }
                } else {
                    second--;
                    if (second >= 10) {
                        right.setText("0" + minute + ":" + second);
                    } else {
                        right.setText("0" + minute + ":0" + second);
                    }
                }
            } else {
                if (second == 0) {
                    second = 59;
                    minute--;
                    if (minute >= 10) {
                        right.setText(minute + ":" + second);
                    } else {
                        right.setText("0" + minute + ":" + second);
                    }
                } else {
                    second--;
                    if (second >= 10) {
                        if (minute >= 10) {
                            right.setText(minute + ":" + second);
                        } else {
                            right.setText("0" + minute + ":" + second);
                        }
                    } else {
                        if (minute >= 10) {
                            right.setText(minute + ":0" + second);
                        } else {
                            right.setText("0" + minute + ":0" + second);
                        }
                    }
                }
            }
        }
    };
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(getApplicationContext(), "交卷成功", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), ExamResultActivity.class);
                    intent.putExtra("result", result);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    break;
                case 1011:
                    second += 1;
                    int hour = second / (60 * 60);
                    int min = (second - hour * 60 * 60) / 60;
                    int sec = (second - hour * 60 * 60 - min * 60);
                    right.setText(hour + "时" + min + "分" + sec + "秒");
                    break;
                case 1012:
                    Bundle bundle = msg.getData();
                    String result = bundle.getString("data");
                    //这边是否需要解析
                    Toast.makeText(getApplicationContext(), "您已被监考官强制交卷", Toast.LENGTH_LONG).show();
                    type = 3;
                    uploadExamination();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_exam);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams
                .FLAG_KEEP_SCREEN_ON);
        Bundle bundle = getIntent().getExtras();
        mExam = (Exam) bundle.get(ConstantUtils.EXAM_INFO);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (mExam.getType() == 1) {
                //正计时
                new Thread(new MyThread()).start();
            } else if (mExam.getType() == 0) {
                //倒计时
                Date d1 = df.parse(mExam.getEndTime());
                Date d2 = new Date();
                long diff = d1.getTime() - d2.getTime();
                if (diff <= 0) {
                    Toast.makeText(getApplicationContext(), "该考试已结束", Toast.LENGTH_LONG).show();
                    finish();
                }
                long days = diff / (1000 * 60 * 60 * 24);
                long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                long min = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
                long sec = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - min * (1000 * 60)) /
                        (1000);
                minute = (int) min;
                second = (int) sec;
                startTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        userInfo = UserModel.getUserInfoMore(getApplicationContext());
        initView();
        for (int i = 0; i < ContextUtils.mPapers.size(); i++) {
            viewItems.add(getLayoutInflater().inflate(
                    R.layout.vote_submit_viewpager_item, null));
        }
        pagerAdapter = new ExaminationSubmitAdapter(ExamActivity.this, viewItems, mExam);
        viewPager.setAdapter(pagerAdapter);
        viewPager.getParent().requestDisallowInterceptTouchEvent(false);
        new QJJUdpUtils().start();
    }

    public void initView() {
        ImageView leftIv = (ImageView) findViewById(R.id.left);
        TextView titleTv = (TextView) findViewById(R.id.title);
        right = (TextView) findViewById(R.id.right);
        titleTv.setText("考试答题");
        viewPager = (ExamViewPager) findViewById(R.id.vote_submit_viewpager);
        leftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showTimeOutDialog();
            }
        });
        initViewPagerScroll();
    }

    private void initViewPagerScroll() {
        try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            ViewPagerScroller scroller = new ViewPagerScroller(viewPager.getContext());
            mScroller.set(viewPager, scroller);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ignored) {

        }
    }

    public void setCurrentView(int index) {
        viewPager.setCurrentItem(index);
    }

    @Override
    protected void onDestroy() {
        minute = -1;
        second = -1;
        super.onDestroy();
    }

    // 提交试卷
    public void uploadExamination() {
        int cent = 0;
        flag = false;
        List<UserAnswer> list = new ArrayList<>();
        DbManager db = new DbManager(getApplicationContext());
        db.openDB();

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
            if (userAnswer.isright > 0) {
                cent += userAnswer.score;
            }
            list.add(userAnswer);
        }
        SimpleExam exam = new SimpleExam();
        exam.setId(mExam.getId());
        exam.setExamName(mExam.getTitle());
        exam.setScore(cent);
        exam.setJigeScore(mExam.getJiGeScore());
        db.insertTest(exam);
        result = cent + "(" + mExam.getJiGeScore() + ")";
        ContextUtils.mUserAnswers = list;
        db.closeDB();
        if (ContextUtils.mUserAnswers != null && ContextUtils.mUserAnswers.size() > 0) {
            new UploadDataThread().start();
            new JJUdpUtils().start();
        }
    }

    // 弹出对话框通知用户答题时间到
    protected void showTimeOutDialog() {
        final Dialog builder = new Dialog(this, R.style.dialog);
        builder.setContentView(R.layout.my_dialog);
        TextView content = (TextView) builder.findViewById(R.id.dialog_content);
        content.setText("是否确认交卷？");
        final Button confirm_btn = (Button) builder.findViewById(R.id.dialog_sure);
        Button cancel_btn = (Button) builder.findViewById(R.id.dialog_cancle);
        confirm_btn.setText("退出");
        cancel_btn.setText("继续答题");
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                uploadExamination();
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        builder.setCanceledOnTouchOutside(false);
        builder.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return false;
            }
        });
        builder.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            showTimeOutDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        flag = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (outTime != null) {
            long between = (new Date().getTime() - outTime.getTime()) / 1000;
            if (between > 5) {
                type = 2;
                uploadExamination();
                Toast.makeText(this, "退出时间大于5秒，系统已自动交卷", Toast.LENGTH_LONG).show();
                finish();
            } else
                Toast.makeText(this, "退出考试一次，该行为将被记录", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        outTime = new Date();
    }

    private void startTime() {
        if (timer == null) {
            timer = new Timer();
        }
        if (timerTask == null) {
            timerTask = new TimerTask() {

                @Override
                public void run() {
                    Message msg = new Message();
                    msg.what = 0;
                    handlerTime.sendMessage(msg);
                }
            };
        }
        if (timer != null) {
            timer.schedule(timerTask, 0, 1000);
        }
    }

    private class UploadDataThread extends Thread {
        @Override
        public void run() {
            try {
                if (ContextUtils.isLogin) {
                    ExamModel.UploadPaper(getApplicationContext(), type);
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(1);
        }
    }

    private class JJUdpUtils extends Thread {
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
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class QJJUdpUtils extends Thread {
        @Override
        public void run() {
            try {
                InetAddress address = InetAddress.getByName(ConstantUtils.UDP_SERVER_URL);
                JSONObject object = new JSONObject();
                object.put("type", 5);
                object.put("code", 2);
                object.put("usertype", 2);
                object.put("testcode", mExam.getId());
                object.put("userid", userInfo.get("userid"));
                object.put("name", userInfo.get("name"));
                byte[] data = object.toString().getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, address, ConstantUtils.UDP_PORT);
                DatagramSocket socket = new DatagramSocket();
                socket.send(packet);
                packet.setData(new byte[1024]);
                packet.setLength(1024);

                while (true) {
                    socket.receive(packet);
                    String result = new String(packet.getData(), packet.getOffset(), packet.getLength());
                    Message message = new Message();
                    message.what = 1012;
                    Bundle bundle = new Bundle();
                    bundle.putString("data", result);
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class MyThread implements Runnable {      // thread
        @Override
        public void run() {
            while (flag) {
                try {
                    Thread.sleep(1000);     // sleep 1000ms
                    Message message = new Message();
                    message.what = 1011;
                    handler.sendMessage(message);
                } catch (Exception ignored) {
                }
            }
        }
    }
}