package com.tencent.avsdk.activity.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.tencent.avsdk.QavsdkApplication;

import java.util.ArrayList;


public abstract class BaseActivity extends Activity {
    protected QavsdkApplication mQavsdkApplication;
    protected ProgressDialog mProgressDialog;
    protected AlertDialog mAlertDialog;
    protected Context mContext;
    /**
     * 关闭Activity的广播，放在自定义的基类中，让其他的Activity继承这个Activity就行
     */
    protected BroadcastReceiver finishAppReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onBackPressed();
        }
    };

    /*
     * 静态集合  存储Activity
     */
    protected static ArrayList<BaseActivity> activityList = new ArrayList<BaseActivity>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mQavsdkApplication = (QavsdkApplication) getApplication();//Application的获取
        activityList.add(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
    }

    abstract protected void findViewByIDS();

    @Override
    protected void onResume() {
        super.onResume();
        // 在当前的activity中注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(this.getClass().getSimpleName());
        this.registerReceiver(this.finishAppReceiver, filter);
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);//初始化UI界面
        findViewByIDS();
    }


    /**
     * 跳转Activity
     */
    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(BaseActivity.this, cls);
        startActivity(intent);
    }

    /**
     * 跳转Activity
     * 包含数据传送
     */
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(BaseActivity.this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 跳转Activity
     * 包含数据传送
     * 包含过度动画的实现
     */
    public void startActivity(Class<?> cls, Bundle bundle, int enterAnim, int exitAnim) {
        startActivity(cls, bundle);
        overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * 跳转Activity
     * 包含过度动画的实现
     *
     * @param cls       跳转的类
     * @param enterAnim 进入的动画
     * @param exitAnim  退出的动画
     */
    public void startActivity(Class<?> cls, int enterAnim, int exitAnim) {
        startActivity(cls);
        overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * 退出加动画
     *
     * @param exitAnim 退出动画
     */
    public void finish(int exitAnim) {
        finish();
        overridePendingTransition(0, exitAnim);
    }

    /**
     * 获取Bundle
     */
    protected Bundle getBundle() {
        Bundle extras = getIntent().getExtras();
        return extras;
    }

    /**
     * 土司通知的简化版本
     */
    public void toast_short(String str) {
        Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT).show();
    }

    public void toast_long(String str) {
        Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();
    }

    /**
     * 销毁Activity
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityList.remove(this);
        this.unregisterReceiver(this.finishAppReceiver);
    }


    //左上角图标点击事件及普通按钮点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void closeAllctivity() {
        for (Activity activity : activityList) {
            //Log.e("", "close:" + activity.getLocalClassName());
            if (activity != null) {
                activity.finish();
            }
        }
    }


    public <T extends View> T MyFindViewById(int viewId) {
        View view = findViewById(viewId);
        return (T) view;
    }

    public <T extends View> T findViewsById(View parent, int viewId) {
        View view = parent.findViewById(viewId);
        return (T) view;
    }
}
