package com.shaohong.thesethree.modules.exam;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.acker.simplezxing.activity.CaptureActivity;
import com.shaohong.thesethree.R;
import com.shaohong.thesethree.bean.Exam;
import com.shaohong.thesethree.model.ExamModel;
import com.shaohong.thesethree.model.UserModel;
import com.shaohong.thesethree.utils.ConstantUtils;
import com.shaohong.thesethree.utils.ContextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;

import static android.app.Activity.RESULT_OK;

public class ExamEnterFragment extends Fragment {

    TextView timer_text_view;
    Button sign_button;
    private Exam mExam;
    private HashMap<String, String> userInfo;
    private int status = 0;//0:未签到,1:签到失败,2:签到成功，下载试卷失败,3:下载试卷成功

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam_enter, container, false);
        Bundle bundle = getArguments();
        mExam = (Exam) bundle.get(ConstantUtils.EXAM_INFO);
        timer_text_view = (TextView) view.findViewById(R.id.timer_exam);
        sign_button = (Button) view.findViewById(R.id.enter_exam_button);
        sign_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String arg = ((Button) v).getText().toString();
                String t = timer_text_view.getText().toString();
                if (arg.equals("开始答题")) {
                    if(mExam.getType()==0){
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            Date d1 = df.parse(mExam.getStartTime());
                            Date d2 = new Date();
                            long diff = d1.getTime() - d2.getTime();
                            if(diff>0){
                                Toast.makeText(getContext(),"考试未开始",Toast.LENGTH_LONG).show();
                            }else{
                                new LoadDataThread().start();
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }else{
                        //去判断考试状态，应该调用接口
                    }
                }
                if (arg.equals("开始答题") && t.equals("考试开始")) {
                    Intent intent = new Intent(getActivity(), ExamActivity.class);
                    intent.putExtra(ConstantUtils.EXAM_INFO, mExam);
                    startActivity(intent);
                }
                if (arg.equals("扫码签到")) {
                    Intent intent = new Intent(getActivity(), CaptureActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(CaptureActivity.KEY_NEED_BEEP, CaptureActivity.VALUE_BEEP);
                    bundle.putBoolean(CaptureActivity.KEY_NEED_VIBRATION, CaptureActivity.VALUE_VIBRATION);
                    bundle.putBoolean(CaptureActivity.KEY_NEED_EXPOSURE, CaptureActivity.VALUE_NO_EXPOSURE);
                    bundle.putByte(CaptureActivity.KEY_FLASHLIGHT_MODE, CaptureActivity.VALUE_FLASHLIGHT_OFF);
                    bundle.putByte(CaptureActivity.KEY_ORIENTATION_MODE, CaptureActivity.VALUE_ORIENTATION_AUTO);
                    bundle.putBoolean(CaptureActivity.KEY_SCAN_AREA_FULL_SCREEN, CaptureActivity.VALUE_SCAN_AREA_FULL_SCREEN);
                    bundle.putBoolean(CaptureActivity.KEY_NEED_SCAN_HINT_TEXT, CaptureActivity.VALUE_SCAN_HINT_TEXT);
                    intent.putExtra(CaptureActivity.EXTRA_SETTING_BUNDLE, bundle);
                    startActivityForResult(intent, CaptureActivity.REQ_CODE);

                }
                if (arg.equals("下载试卷")) {
                    Toast.makeText(getContext(), "开始现在试卷，请耐心等待", Toast.LENGTH_LONG).show();
                    new LoadDataThread().start();
                }
            }
        });

        //考试信息
        TextView eName = (TextView) view.findViewById(R.id.name_exam_text_view);
        eName.setText("名称：" + mExam.getTitle());
        TextView eTime = (TextView) view.findViewById(R.id.time_exam_text_view);
        eTime.setText("时间：" + mExam.getStartTime());
        TextView eAddress = (TextView) view.findViewById(R.id.address_exam_text_view);
        eAddress.setText("地点：" + mExam.getAddress());
        //考生信息
        userInfo = UserModel.getUserInfoMore(getContext());
        TextView uName = (TextView) view.findViewById(R.id.name_user_text_view);
        uName.setText("姓名：" + userInfo.get("name"));
        TextView uLoginID = (TextView) view.findViewById(R.id.loginId_user_text_view);
        uLoginID.setText("工号：" + userInfo.get("loginID"));
        TextView uDeptName = (TextView) view.findViewById(R.id.deptname_user_text_view);
        uDeptName.setText("科室：" + userInfo.get("deptname"));

        try {
            initTimer();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = (String) bundle.get("SCAN_RESULT");
            if (String.valueOf(mExam.getId()).equals(scanResult)) {
                new LoadDataThread().start();
                new QdUdpUtils().start();
            } else {
                new AlertDialog.Builder(getContext())
                        .setTitle("提示")
                        .setMessage("签到失败，请确认是否进错考场!")
                        .setPositiveButton("确定", null)
                        .show();
            }
        }
    }

    private void initTimer() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date();
        Date endDate = df.parse(mExam.getStartTime());
        long between = (endDate.getTime() - curDate.getTime());
        if (between > 0) {
            CountDownTimer timer = new CountDownTimer(between, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long between = millisUntilFinished / 1000;
                    long day = between / (24 * 3600);
                    long hour = between % (24 * 3600) / 3600;
                    long minute = between % 3600 / 60;
                    long second = between % 60;
                    if (day > 0) {
                        timer_text_view.setText(day + "天" + hour + "小时" + minute + "分" + second + "秒");
                    } else {
                        if (hour > 0)
                            timer_text_view.setText(hour + "小时" + minute + "分" + second + "秒");
                        else {
                            if (minute > 0) {
                                timer_text_view.setText(minute + "分" + second + "秒");
                            } else {
                                if (second >= 1) {
                                    timer_text_view.setText(second + "秒");
                                } else {
                                    timer_text_view.setText("考试开始");
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFinish() {
                    timer_text_view.setText("考试开始");
                }
            };
            timer.start();
        } else {
            timer_text_view.setText("考试开始");
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(getContext(), "试卷下载完成", Toast.LENGTH_LONG).show();
                    sign_button.setText("开始答题");
                    break;
                case 2:
                    Toast.makeText(getContext(), "签到成功,开始下载试卷", Toast.LENGTH_LONG).show();
                    sign_button.setText("下载试卷");
                    break;
                case -1:
                    new AlertDialog.Builder(getContext())
                            .setTitle("提示")
                            .setMessage("签到失败，请稍后再试!")
                            .setPositiveButton("确定", null)
                            .show();
                    sign_button.setText("扫码签到");
                    break;
                case 0:
                    new AlertDialog.Builder(getContext())
                            .setTitle("提示")
                            .setMessage("下载试卷失败，请稍后再试!")
                            .setPositiveButton("确定", null)
                            .show();
                    sign_button.setText("下载试卷");
                    break;
                case 10:
                    //解析数据，如果是开始考试，或是其他
                    break;
            }
        }
    };

    class LoadDataThread extends Thread {
        @Override
        public void run() {
            try {
                if (ContextUtils.isLogin) {
                    if ((status == 0 || status == 1)) {
                        if (ExamModel.Qd(getContext(), mExam.getId())) {
                            handler.sendEmptyMessage(2);
                            status = 2;
                        } else {
                            handler.sendEmptyMessage(-1);
                            status = 1;
                        }
                    }
                    if (status == 2) {
                        ContextUtils.mPapers = null;
                        ContextUtils.mPapers = ExamModel.DownloadPaper(mExam.getId());
                        ContextUtils.testId = mExam.getId();
                        if (ContextUtils.mPapers != null && ContextUtils.mPapers.size() > 0) {
                            status = 3;
                            handler.sendEmptyMessage(1);
                        } else {
                            status = 2;
                            handler.sendEmptyMessage(0);
                        }
                    }
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class QdUdpUtils extends Thread {
        @Override
        public void run() {
            try {
                InetAddress address = InetAddress.getByName(ConstantUtils.UDP_SERVER_URL);
                JSONObject object = new JSONObject();
                object.put("type", 1);
                object.put("code", 2);
                object.put("usertype", 1);
                object.put("testcode",mExam.getId());
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
                    message.what = 10;
                    Bundle bundle = new Bundle();
                    bundle.putString("data", result);
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
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
