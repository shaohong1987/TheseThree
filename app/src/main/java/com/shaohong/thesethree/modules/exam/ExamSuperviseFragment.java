package com.shaohong.thesethree.modules.exam;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.bean.Exam;
import com.shaohong.thesethree.utils.BarCodeUtils;
import com.shaohong.thesethree.utils.ConstantUtils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import static com.shaohong.thesethree.R.id.barcode_enter_exam;

public class ExamSuperviseFragment extends Fragment {

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
        Exam exam = (Exam) bundle.get(ConstantUtils.EXAM_INFO);
        generateBarCode(view, String.valueOf(exam.getId()));
        new LoadDataThread().start();
        return view;
    }

    public void generateBarCode(View view, String content) {
        ImageView imageView = (ImageView) view.findViewById(barcode_enter_exam);
        Bitmap qrBitmap = new BarCodeUtils().generateBitmap(content, 400, 400);
        imageView.setImageBitmap(qrBitmap);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Bundle bundle=msg.getData();
                    Toast.makeText(getContext(),bundle.get("data").toString(),Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    class LoadDataThread extends Thread {
        DatagramSocket udpSocket;
        DatagramPacket udpPacket = null;
        byte[] data = new byte[1024];

        @Override
        public void run() {
            try {
                udpSocket = new DatagramSocket();
                udpPacket = new DatagramPacket(data, data.length);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (SocketException e) {
                e.printStackTrace();
            }
            while (true) {
                try {
                    udpSocket.receive(udpPacket);
                } catch (Exception e) {

                }
                String result = new String(udpPacket.getData(), udpPacket.getOffset(), udpPacket.getLength());
                Message message = new Message();
                message.what = 1;
                Bundle bundle = new Bundle();
                bundle.putString("data", result);
                message.setData(bundle);
                handler.sendMessage(message);
            }
            //udpSocket.close();
        }
    }
}
