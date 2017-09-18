package com.shaohong.thesethree.modules.exam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.activities.MainActivity;

public class ExamResultActivity extends AppCompatActivity {

    Button close_button;
    TextView result_text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_result);
        Bundle bundle = getIntent().getExtras();
        String result = bundle.getString("result");

        result_text_view = (TextView) findViewById(R.id.total_cent_text_view);
        result_text_view.setText(result);
        close_button = (Button) findViewById(R.id.close_button_exam_result);

        close_button.setOnClickListener(new View.OnClickListener() {
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
