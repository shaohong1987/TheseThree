package com.shaohong.thesethree.modules.exam;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.bean.Exam;
import com.shaohong.thesethree.utils.BarCodeUtils;
import com.shaohong.thesethree.utils.ConstantUtils;

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
        View view= inflater.inflate(R.layout.fragment_exam_supervise, container, false);
        Bundle bundle=getArguments();
        Exam exam= (Exam) bundle.get(ConstantUtils.EXAM_INFO);
        generateBarCode(view);
        return view;
    }

    public void generateBarCode(View view) {
        ImageView imageView = (ImageView) view.findViewById(barcode_enter_exam);
        Bitmap qrBitmap = new BarCodeUtils().generateBitmap("http://www.csdn.net", 400, 400);
        imageView.setImageBitmap(qrBitmap);
    }


}
