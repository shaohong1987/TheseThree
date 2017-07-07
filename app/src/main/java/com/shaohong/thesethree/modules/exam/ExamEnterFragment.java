package com.shaohong.thesethree.modules.exam;

import android.content.Intent;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.activity.CaptureActivity;
import com.shaohong.thesethree.R;
import com.shaohong.thesethree.bean.Exam;
import com.shaohong.thesethree.utils.ConstantUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class ExamEnterFragment extends Fragment {
    //打开扫描界面请求码
    private int REQUEST_CODE = 0x01;
    //扫描成功返回码
    private int RESULT_OK = 0xA1;
    TextView timer_text_view;
    Button sign_button;

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
        final Exam exam = (Exam) bundle.get(ConstantUtils.EXAM_INFO);
        timer_text_view = (TextView) view.findViewById(R.id.timer_exam);
        sign_button = (Button) view.findViewById(R.id.enter_exam_button);

        sign_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String arg = ((Button) v).getText().toString();
                if (arg.equals("开始答题")) {
                    Intent intent=new Intent(getActivity(),ExamActivity.class);
                    intent.putExtra(ConstantUtils.EXAM_INFO,exam);
                    startActivity(intent);
                }
                if (arg.equals("扫码签到")) {
                    Intent intent = new Intent(getActivity(), CaptureActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        });

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
            String scanResult = (String) bundle.get("qr_scan_result");
            sign_button.setText("开始答题");
            Toast.makeText(getActivity(), scanResult, Toast.LENGTH_LONG).show();
        }
    }

    private void initTimer() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date();
        Date endDate = df.parse("2017-05-11 10:42:00");
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
                                    timer_text_view.setText("开始答题");
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFinish() {
                    timer_text_view.setText("开始答题");
                }
            };
            timer.start();
        } else {
            timer_text_view.setText("开始答题");
        }
    }
}
