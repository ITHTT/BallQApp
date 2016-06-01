package com.tysci.ballq.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.tysci.ballq.R;
import com.tysci.ballq.base.BaseActivity;
import com.tysci.ballq.base.BaseFragment;
import com.tysci.ballq.fragments.BallQHomeFragment;
import com.tysci.ballq.utils.CommonUtils;
import com.tysci.ballq.utils.UserInfoUtil;
import com.tysci.ballq.views.widgets.MainMenuItemView;
import com.tysci.ballq.views.widgets.slidingmenu.SlidingMenu;
import com.tysci.ballq.wxapi.WXEntryActivity;

public class MainActivity extends BaseActivity {
    private SlidingMenu slidingMenu=null;
    private View mainLeftMenu;
    private View mainRightMenu;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        initSlidingMenu();
        setTitleBarLeftIcon(R.mipmap.icon_main_left_menu);
        titleBar.setRightMenuIcon(R.mipmap.icon_main_right_menu, this);
        addMenusItemOnClickListener();

        setSelectedMenuItem(R.id.menu_index);
    }

    @Override
    protected void getIntentData(Intent intent) {

    }

    private void initSlidingMenu() {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        // 设置触摸屏幕的模式
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCH_MODE_FULLSCREEN);
        slidingMenu.setShadowWidth(200);
//        menu.setShadowDrawable(R.drawable.shadow);

        // 设置滑动菜单视图的宽度
        slidingMenu.setBehindOffset(CommonUtils.getScreenDisplayMetrics(this).widthPixels/4);
        // 设置渐入渐出效果的值
        slidingMenu.setFadeDegree(0.5f);
        mainLeftMenu= LayoutInflater.from(this).inflate(R.layout.layout_main_left_menu,null);
        mainRightMenu=LayoutInflater.from(this).inflate(R.layout.layout_main_right_menu,null);
        slidingMenu.setMenu(mainLeftMenu);
        slidingMenu.setSecondaryMenu(mainRightMenu);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

        mainRightMenu.findViewById(R.id.iv_user_header).setOnClickListener(this);
        mainRightMenu.findViewById(R.id.tv_user_name).setOnClickListener(this);
    }

    /**
     * 为左右菜单项添加点击事件
     */
    private void addMenusItemOnClickListener(){
        LinearLayout layoutLeftMenus= (LinearLayout) mainLeftMenu.findViewById(R.id.layout_left_menus);
        int size=layoutLeftMenus.getChildCount();
        for(int i=0;i<size;i++){
            View view=layoutLeftMenus.getChildAt(i);
            if(view instanceof MainMenuItemView){
                view.setOnClickListener(this);
            }
        }

        LinearLayout layoutRightMenus=(LinearLayout)mainRightMenu.findViewById(R.id.layout_right_menus);
        size=layoutRightMenus.getChildCount();
        for(int i=0;i<size;i++){
            View view=layoutRightMenus.getChildAt(i);
            if(view instanceof MainMenuItemView){
                view.setOnClickListener(this);
            }
        }
    }

    private void onMenuItemClick(View view){
        LinearLayout layoutLeftMenus= (LinearLayout) mainLeftMenu.findViewById(R.id.layout_left_menus);
        int size=layoutLeftMenus.getChildCount();
        for(int i=0;i<size;i++){
            View v=layoutLeftMenus.getChildAt(i);
            if(v instanceof MainMenuItemView){
                ((MainMenuItemView)v).setCheckedState(v == view);
            }
        }

        LinearLayout layoutRightMenus=(LinearLayout)mainRightMenu.findViewById(R.id.layout_right_menus);
        size=layoutRightMenus.getChildCount();
        for(int i=0;i<size;i++){
            View v=layoutRightMenus.getChildAt(i);
            if(v instanceof MainMenuItemView){
                ((MainMenuItemView)v).setCheckedState(v == view);
            }
        }
        setSelectedMenuItem(view.getId());
    }

    private void setSelectedMenuItem(int id){
        BaseFragment fragment=null;
        switch (id){
            case R.id.menu_index:
                slidingMenu.setTouchModeAbove(SlidingMenu.TOUCH_MODE_MARGIN);
                fragment=new BallQHomeFragment();
                break;
        }

        if(fragment!=null){
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.layout_container,fragment,fragment.getClass().getSimpleName());
            transaction.commitAllowingStateLoss();
        }

    }

    @Override
    protected boolean isCanceledEventBus() {
        return false;
    }

    @Override
    protected void saveInstanceState(Bundle outState) {

    }

    @Override
    protected void handleInstanceState(Bundle outState) {

    }

    private void goLogin(){
        Intent intent=new Intent(this, WXEntryActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onViewClick(View view) {
        int id=view.getId();
        switch(id){
            case R.id.iv_user_header:
                if(UserInfoUtil.checkLogin(this)){

                }else{
                    goLogin();
                }
                break;
            case R.id.tv_user_name:
                if(UserInfoUtil.checkLogin(this)){

                }else{
                    goLogin();
                }
                break;
        }

    }
}
