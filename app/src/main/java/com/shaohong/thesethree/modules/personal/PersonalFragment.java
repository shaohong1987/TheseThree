package com.shaohong.thesethree.modules.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shaohong.thesethree.R;
import com.shaohong.thesethree.activities.CommonActivity;
import com.shaohong.thesethree.activities.LoginActivity;
import com.shaohong.thesethree.base.BaseFragment;
import com.shaohong.thesethree.model.UserModel;
import com.shaohong.thesethree.utils.ConstantUtils;
import com.shaohong.thesethree.utils.ContextUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalFragment extends BaseFragment implements View.OnClickListener{

    @BindView(R.id.login_in_button)
    Button login_in_button;
    @BindView(R.id.login_out_button)
    Button login_out_button;
    @BindView(R.id.personal_info_menu_layout)
    ViewGroup personal_info_menu_layout;
    @BindView(R.id.user_info_text_view)
    TextView user_info_text_view;
    @BindView(R.id.profile_image)
    CircleImageView profile_image;
    @BindView(R.id.personal_info_layout)
    ViewGroup personal_info_layout;
    @BindView(R.id.mistake_book_layout)
    ViewGroup mistake_book_layout;
    @BindView(R.id.help_feedback_layout)
    ViewGroup help_feedback_layout;
    @BindView(R.id.clear_cache_layout)
    ViewGroup clear_cache_layout;
    @BindView(R.id.about_us_layout)
    ViewGroup about_us_layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        ButterKnife.bind(this, view);
        isLogin();

        //登录
        login_in_button.setOnClickListener(this);
        //登出
        login_out_button.setOnClickListener(this);
        //个人资料
        personal_info_layout.setOnClickListener(this);
        //错题集
        mistake_book_layout.setOnClickListener(this);
        //帮助反馈
        help_feedback_layout.setOnClickListener(this);
        //清除缓存
        clear_cache_layout.setOnClickListener(this);
        //关于
        about_us_layout.setOnClickListener(this);

        return view;
    }

    private void isLogin() {

        if (ContextUtils.isLogin) {
            login_out_button.setVisibility(View.VISIBLE);
            login_in_button.setVisibility(View.GONE);
            personal_info_menu_layout.setVisibility(View.VISIBLE);
            user_info_text_view.setVisibility(View.VISIBLE);
            user_info_text_view.setText(UserModel.getUserInfo(getContext()));
            profile_image.setImageResource(R.drawable.user_image);
        } else {
            login_out_button.setVisibility(View.GONE);
            login_in_button.setVisibility(View.VISIBLE);
            personal_info_menu_layout.setVisibility(View.GONE);
            user_info_text_view.setVisibility(View.GONE);
            user_info_text_view.setText("");
            profile_image.setImageResource(R.drawable.ic_account_circle_black_24dp);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isLogin();
    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()){
            case R.id.login_in_button:
                 intent= new Intent(getActivity(), LoginActivity.class);
                break;
            case R.id.login_out_button:
                UserModel userModel = new UserModel();
                userModel.loginOut(getContext());
                isLogin();
                break;
            case R.id.personal_info_layout:
                intent= new Intent(getActivity(), CommonActivity.class);
                intent.putExtra(ConstantUtils.COMMON_ARG,v.getId());
                break;
            case R.id.mistake_book_layout:
                intent= new Intent(getActivity(), CommonActivity.class);
                intent.putExtra(ConstantUtils.COMMON_ARG,v.getId());
                break;
            case R.id.help_feedback_layout:
                intent= new Intent(getActivity(), CommonActivity.class);
                intent.putExtra(ConstantUtils.COMMON_ARG,v.getId());
                break;
            case R.id.clear_cache_layout:
                //再次实现清除缓存
                break;
            case R.id.about_us_layout:
                intent= new Intent(getActivity(), CommonActivity.class);
                intent.putExtra(ConstantUtils.COMMON_ARG,v.getId());
                break;
            default:
                intent=null;
                break;
        }
        if(intent!=null)
        {
            startActivity(intent);
        }
    }
}
