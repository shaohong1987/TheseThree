package com.shaohong.thesethree.modules.course;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.activities.MainActivity;
import com.shaohong.thesethree.utils.ConstantUtils;

public class CourseExamResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_exam_result);
        int result = (int) getIntent().getExtras().get(ConstantUtils.EXAM_INFO);
        TextView resultTextView = (TextView) findViewById(R.id.result_course_exam_text_view);
        resultTextView.setText("本次得分：" + String.valueOf(result) + "分");
        Button button = (Button) findViewById(R.id.close_button_exam);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

    }
}
