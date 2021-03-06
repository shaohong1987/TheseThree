package com.shaohong.thesethree.modules.exam;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.bean.Exam;
import com.shaohong.thesethree.bean.KaoSheng;
import com.shaohong.thesethree.model.ExamModel;
import com.shaohong.thesethree.model.UserModel;
import com.shaohong.thesethree.myview.MyGridView;
import com.shaohong.thesethree.utils.BarCodeUtils;
import com.shaohong.thesethree.utils.ConstantUtils;
import com.shaohong.thesethree.utils.ContextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.shaohong.thesethree.R.id.barcode_enter_exam;

public class ExamSuperviseFragment extends Fragment {
    Thread mThread;
    private HashMap<String, String> userInfo;
    private Exam mExam;
    private TextView djs_text_view;
    private TextView statistic_jiankao_text_view;
    private Button control_exam_button;
    private List<KaoSheng> yJJList;
    private List<KaoSheng> wJJList;
    private int index;
    private YJJAdapter mYJJAdapter;
    private WJJAdapter mWJJAdapter;
    private KaoSheng mKaoSheng;
    private int seconds = 0;
    private boolean flag = true;
    private String mString = "";
    private int yd;
    private int qj;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Bundle bundle = msg.getData();
                    String result = (String) bundle.get("data");
                    try {
                        boolean f = false;
                        JSONObject object = new JSONObject(result);
                        KaoSheng kaoSheng = new KaoSheng();
                        kaoSheng.userName = object.getString("name");
                        kaoSheng.code = object.getInt("code");
                        kaoSheng.type = object.getInt("type");
                        kaoSheng.userId = object.getInt("userid");
                        kaoSheng.userType = object.getInt("usertype");
                        kaoSheng.leiXing = object.getInt("leixing");
                        if (kaoSheng.leiXing == 1) {
                            for (int j = 0; j < wJJList.size(); j++) {
                                KaoSheng ks = wJJList.get(j);
                                if (ks.userName.equals(kaoSheng.userName)) {
                                    f = true;
                                    break;
                                }
                            }
                            if (!f) {
                                wJJList.add(kaoSheng);
                                mWJJAdapter.notifyDataSetChanged();
                            }
                        }
                        if (kaoSheng.leiXing == 2) {
                            f = false;
                            for (int i = 0; i < wJJList.size(); i++) {
                                KaoSheng ks = wJJList.get(i);
                                if (ks.userName.equals(kaoSheng.userName)) {
                                    f = true;
                                    break;
                                }
                            }
                            if (!f) {
                                wJJList.remove(kaoSheng);
                                mWJJAdapter.notifyDataSetChanged();
                            }
                            f = false;
                            for (int i = 0; i < yJJList.size(); i++) {
                                KaoSheng ks = yJJList.get(i);
                                if (ks.userName.equals(kaoSheng.userName)) {
                                    f = true;
                                    break;
                                }
                            }
                            if (!f) {
                                yJJList.add(kaoSheng);
                                mYJJAdapter.notifyDataSetChanged();
                            }
                        }
                        mString = "应到" + yd + "人，实到" + (yJJList.size() + wJJList.size()) + "人，请假" + qj + "人";
                        statistic_jiankao_text_view.setText(mString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1011:
                    seconds += 1;
                    int hour = seconds / (60 * 60);
                    int min = (seconds - hour * 60 * 60) / 60;
                    int sec = (seconds - hour * 60 * 60 - min * 60);
                    djs_text_view.setText(hour + "时" + min + "分" + sec + "秒");
                    break;
                case 1212:
                    mYJJAdapter.notifyDataSetChanged();
                    mWJJAdapter.notifyDataSetChanged();
                    statistic_jiankao_text_view.setText(mString);
                    break;
                case 1234:
                    Toast.makeText(getContext(), "已允许 " + mKaoSheng.userName + " 重新考试", Toast.LENGTH_SHORT).show();
                    break;
                case 1235:
                    Toast.makeText(getContext(), "操作失败，未能允许 " + mKaoSheng.userName + " 重新考试", Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam_supervise, container, false);
        Bundle bundle = getArguments();
        mExam = (Exam) bundle.get(ConstantUtils.EXAM_INFO);
        djs_text_view = (TextView) view.findViewById(R.id.djs_jiankao_text_view);
        statistic_jiankao_text_view = (TextView) view.findViewById(R.id.statistic_jiankao_text_view);
        statistic_jiankao_text_view.setText(mString);
        control_exam_button = (Button) view.findViewById(R.id.control_jiankao_button);
        if (mExam.getType() == 1) {
            control_exam_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (control_exam_button.getText().equals("开始考试")) {
                        new StartThread().start();
                        control_exam_button.setText("结束考试");
                        mThread = new Thread(new MyThread());
                        mThread.start();
                    } else if (control_exam_button.getText().equals("结束考试")) {
                        flag = false;
                        new EndThread().start();
                        control_exam_button.setText("已结束");
                    }
                }
            });
        } else {
            djs_text_view.setVisibility(View.GONE);
            control_exam_button.setVisibility(View.GONE);
        }
        yJJList = new ArrayList<>();
        wJJList = new ArrayList<>();
        userInfo = UserModel.getUserInfoMore(getContext());
        MyGridView yJJgridView = (MyGridView) view.findViewById(R.id.yi_jiao_juan_grid_view);
        mYJJAdapter = new YJJAdapter(getContext(), yJJList);
        yJJgridView.setAdapter(mYJJAdapter);
        MyGridView WJJaridView = (MyGridView) view.findViewById(R.id.wei_jiao_juan_grid_view);
        mWJJAdapter = new WJJAdapter(getContext(), wJJList);
        WJJaridView.setAdapter(mWJJAdapter);
        new GetKaoShengThread().start();
        new JianKaoThread().start();
        generateBarCode(view, String.valueOf(mExam.getId()));
        return view;
    }

    public void generateBarCode(View view, String content) {
        ImageView imageView = (ImageView) view.findViewById(barcode_enter_exam);
        Bitmap qrBitmap = new BarCodeUtils().generateBitmap(content, 400, 400);
        imageView.setImageBitmap(qrBitmap);
    }

    protected void showTimeOutDialog(final int type) {
        final Dialog builder = new Dialog(getContext(), R.style.dialog);
        builder.setContentView(R.layout.my_dialog);
        TextView content = (TextView) builder.findViewById(R.id.dialog_content);
        final Button confirm_btn = (Button) builder.findViewById(R.id.dialog_sure);
        Button cancel_btn = (Button) builder.findViewById(R.id.dialog_cancle);
        if (type == 0) {
            mKaoSheng = wJJList.get(index);
            content.setText("强制 " + mKaoSheng.userName + " 考试交卷？");
        } else {
            mKaoSheng = yJJList.get(index);
            content.setText("允许 " + mKaoSheng.userName + " 重考？");
        }
        confirm_btn.setText("确定");
        cancel_btn.setText("取消");
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 0) {
                    wJJList.remove(mKaoSheng);
                    yJJList.add(mKaoSheng);
                    mWJJAdapter.notifyDataSetChanged();
                    mYJJAdapter.notifyDataSetChanged();
                    //发送强制交卷请求
                    new JiaoJuanThread().start();

                } else {
                    yJJList.remove(mKaoSheng);
                    mYJJAdapter.notifyDataSetChanged();
                    //发送允许重考
                    new ChongKaoThread().start();
                }
                builder.dismiss();
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

    @Override
    public void onResume() {
        super.onResume();
        yJJList.clear();
        wJJList.clear();
        new GetKaoShengThread().start();
    }

    private class GetKaoShengThread extends Thread {
        @Override
        public void run() {
            try {
                if (ContextUtils.isLogin) {
                    String data = ExamModel.GetKaoSheng(mExam.getId());
                    if (data != null) {
                        JSONObject obj = new JSONObject(data);
                        yd = obj.getInt("yingdao");
                        qj = obj.getInt("qingjia");
                        JSONArray ek = obj.getJSONArray("endks");
                        for (int i = 0; i < ek.length(); i++) {
                            JSONObject o = ek.getJSONObject(i);
                            boolean f = false;
                            for (int j = 0; j < yJJList.size(); j++) {
                                KaoSheng ks = yJJList.get(j);
                                if (ks.userName.equals(o.getString("name"))) {
                                    f = true;
                                    break;
                                }
                            }
                            if (!f) {
                                KaoSheng kaoSheng = new KaoSheng();
                                kaoSheng.userName = o.getString("name");
                                kaoSheng.userId = o.getInt("userid");
                                yJJList.add(kaoSheng);
                            }
                        }
                        JSONArray ok = obj.getJSONArray("onks");
                        for (int i = 0; i < ok.length(); i++) {
                            JSONObject o = ek.getJSONObject(i);
                            boolean f = false;
                            for (int j = 0; j < wJJList.size(); j++) {
                                KaoSheng ks = wJJList.get(j);
                                if (ks.userName.equals(o.getString("name"))) {
                                    f = true;
                                    break;
                                }
                            }
                            if (!f) {
                                KaoSheng kaoSheng = new KaoSheng();
                                kaoSheng.userName = o.getString("name");
                                kaoSheng.userId = o.getInt("userid");
                                wJJList.add(kaoSheng);
                            }
                        }
                        mString = "应到" + yd + "人，实到" + (ok.length() + ek.length()) + "人，请假" + qj + "人";
                        handler.sendEmptyMessage(1212);
                    }
                }
                Thread.sleep(100);
            } catch (InterruptedException | JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class JianKaoThread extends Thread {
        @Override
        public void run() {
            try {
                InetAddress address = InetAddress.getByName(ConstantUtils.UDP_SERVER_URL);
                JSONObject object = new JSONObject();
                object.put("type", 1);
                object.put("code", 1);
                object.put("usertype", 1);
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
                    message.what = 1;
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

    private class JiaoJuanThread extends Thread {
        @Override
        public void run() {
            try {
                InetAddress address = InetAddress.getByName(ConstantUtils.UDP_SERVER_URL);
                JSONObject object = new JSONObject();
                object.put("type", 4);
                object.put("code", 2);
                object.put("usertype", 2);
                object.put("testcode", mExam.getId());
                object.put("userid", mKaoSheng.userId);
                object.put("name", mKaoSheng.userName);
                byte[] data = object.toString().getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, address, ConstantUtils.UDP_PORT);
                DatagramSocket socket = new DatagramSocket();
                socket.send(packet);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class ChongKaoThread extends Thread {
        @Override
        public void run() {
            boolean result = ExamModel.SetChongKao(mKaoSheng.userId, mExam.getId());
            handler.sendEmptyMessage(result ? 1234 : 1235);
        }
    }

    private class YJJAdapter extends BaseAdapter {
        private List<KaoSheng> data;
        private LayoutInflater layoutInflater;

        YJJAdapter(Context context, List<KaoSheng> kaoShengs) {
            layoutInflater = LayoutInflater.from(context);
            data = kaoShengs;
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
            KaoSheng kaoSheng = data.get(position);
            Button button = (Button) v.findViewById(R.id.no_tm_button);
            button.setText(kaoSheng.userName);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //在此弹出框，是否允许重考
                    index = position;
                    showTimeOutDialog(1);
                }
            });
            return v;
        }
    }

    private class WJJAdapter extends BaseAdapter {
        private List<KaoSheng> data;
        private LayoutInflater layoutInflater;

        WJJAdapter(Context context, List<KaoSheng> kaoShengs) {
            layoutInflater = LayoutInflater.from(context);
            data = kaoShengs;
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
            KaoSheng kaoSheng = data.get(position);
            Button button = (Button) v.findViewById(R.id.no_tm_button);
            button.setText(kaoSheng.userName);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //在此弹出框，是否强制交卷
                    index = position;
                    showTimeOutDialog(0);

                }
            });
            return v;
        }
    }

    private class StartThread extends Thread {
        @Override
        public void run() {
            try {
                InetAddress address = InetAddress.getByName(ConstantUtils.UDP_SERVER_URL);
                JSONObject object = new JSONObject();
                object.put("type", 2);
                object.put("code", 1);
                object.put("usertype", 2);
                object.put("testcode", mExam.getId());
                object.put("userid", userInfo.get("userid"));
                object.put("name", userInfo.get("name"));
                byte[] data = object.toString().getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, address, ConstantUtils.UDP_PORT);
                DatagramSocket socket = new DatagramSocket();
                socket.send(packet);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class EndThread extends Thread {
        @Override
        public void run() {
            try {
                InetAddress address = InetAddress.getByName(ConstantUtils.UDP_SERVER_URL);
                JSONObject object = new JSONObject();
                object.put("type", 2);
                object.put("code", 2);
                object.put("usertype", 2);
                object.put("testcode", mExam.getId());
                object.put("userid", userInfo.get("userid"));
                object.put("name", userInfo.get("name"));
                byte[] data = object.toString().getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, address, ConstantUtils.UDP_PORT);
                DatagramSocket socket = new DatagramSocket();
                socket.send(packet);
            } catch (JSONException | IOException e) {
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
