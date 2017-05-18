package com.shaohong.thesethree.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.shaohong.thesethree.R;
import com.shaohong.thesethree.base.BaseActivity;
import com.shaohong.thesethree.modules.course.CourseFragment;
import com.shaohong.thesethree.modules.exam.ExamFragment;
import com.shaohong.thesethree.modules.home.HomeFragment;
import com.shaohong.thesethree.modules.personal.PersonalFragment;
import com.shaohong.thesethree.utils.UpdateManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    @BindView(R.id.bottom_navigation)
    BottomNavigationBar bottomNavigation;

    private FragmentManager fragmentManager;
    private Fragment currentFragment;
    private Fragment homeFragment;
    private Fragment courseFragment;
    private Fragment examFragment;
    private Fragment personalFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();

        initView();
        initNavigation();

        //检查更新
        //UpdateManager updateManager=new UpdateManager(MainActivity.this);
        //updateManager.update();
    }

    private void initView() {
        homeFragment = new HomeFragment();
        courseFragment = new CourseFragment();
        examFragment = new ExamFragment();
        personalFragment = new PersonalFragment();
        switchContent(homeFragment);
    }

    private void initNavigation() {
        bottomNavigation
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE)
                .setMode(BottomNavigationBar.MODE_FIXED)
                .addItem(new BottomNavigationItem(R.drawable.ic_home_black_24dp, getString(R.string.nav_title_home))
                        .setActiveColorResource(R.color.colorDarkBlue))
                .addItem(new BottomNavigationItem(R.drawable.ic_dashboard_black_24dp, getString(R.string
                        .nav_title_course))
                        .setActiveColorResource(R.color.colorDarkBlue))
                .addItem(new BottomNavigationItem(R.drawable.ic_assignment_turned_in_black_24dp, getString(R.string
                        .nav_title_exam))
                        .setActiveColorResource(R.color.colorDarkBlue))
                .addItem(new BottomNavigationItem(R.drawable.ic_person_black_24dp, getString(R.string
                        .nav_title_personal))
                        .setActiveColorResource(R.color.colorDarkBlue))
                .setFirstSelectedPosition(0)
                .setTabSelectedListener(this)
                .initialise();
    }

    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case 0:
                switchContent(homeFragment);
                break;
            case 1:
                switchContent(courseFragment);
                break;
            case 2:
                switchContent(examFragment);
                break;
            case 3:
                switchContent(personalFragment);
                break;
            default:
                break;
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    public void switchContent(Fragment to) {
        if (currentFragment != to) {
            if (!to.isAdded()) {
                if (currentFragment != null) {
                    fragmentManager.beginTransaction().hide(currentFragment).commit();
                }
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container, to)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(to).commit();
            }
            currentFragment = to;
        }
    }
}
