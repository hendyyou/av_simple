package com.tencent.avsdk;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;
import android.widget.TabHost;

import com.orhanobut.logger.Logger;
import com.tencent.TIMManager;
import com.tencent.avsdk.constants.DemoConstants;
import com.tencent.avsdk.model.MemberInfo;
import com.tencent.avsdk.model.UserInfo;
import com.tencent.avsdk.zcontrol.QavsdkControl;
import com.tencent.bugly.imsdk.crashreport.CrashReport;


public class QavsdkApplication extends Application {
    private static final String TAG = "QavsdkApplication";
    private QavsdkControl mQavsdkControl = null;
    private UserInfo mSelfUserInfo;


    private MemberInfo hostInfo;
    private TabHost appTabHost;
    private boolean handleMemberRoomSuccess = false;
    private int enterIndex = 0;//进入的人数
    private int exitIndex = 0;//出去的人数
    private String roomName = "";
    private String roomCoverPath = "";
    private boolean enableBeauty = false;


    private int requestCount = 0;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "WL_DEBUG onConfigurationChanged");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //TODO 初始化 IMSDK
        TIMManager.getInstance().init(getApplicationContext());
        //TODO 初始化直播sdk
        mQavsdkControl = new QavsdkControl(this);
        //TODO 初始化Crash上报
        CrashReport.initCrashReport(this, "" + DemoConstants.APPID, true);

        mSelfUserInfo = new UserInfo("123", 10, R.drawable.user, 1000);
        Logger.init("caoyang");
        Log.d(TAG, "WL_DEBUG onCreate");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "WL_DEBUG onLowMemory");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "WL_DEBUG onTerminate");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.d(TAG, "WL_DEBUG onTrimMemory");
    }

    public QavsdkControl getQavsdkControl() {
        return mQavsdkControl;
    }

    public UserInfo getMyselfUserInfo() {
        return mSelfUserInfo;
    }

    public void setAppTabHost(TabHost tabHost) {
        appTabHost = tabHost;
    }

    public TabHost getAppTabHost() {
        return appTabHost;
    }

    public void enterPlusPlus() {
        enterIndex++;
    }

    public void exitPlusPlus() {
        exitIndex++;
    }

    public boolean isHandleMemberRoomSuccess() {
        return handleMemberRoomSuccess;
    }

    public void setHandleMemberRoomSuccess(boolean handleMemberRoomSuccess) {
        this.handleMemberRoomSuccess = handleMemberRoomSuccess;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }


    public int getEnterIndex() {
        return enterIndex;
    }

    public void setEnterIndex(int enterIndex) {
        this.enterIndex = enterIndex;
    }

    public int getExitIndex() {
        return exitIndex;
    }

    public void setExitIndex(int exitIndex) {
        this.exitIndex = exitIndex;
    }

    public String getRoomCoverPath() {
        return roomCoverPath;
    }

    public void setRoomCoverPath(String roomCoverPath) {
        this.roomCoverPath = roomCoverPath;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }


    public MemberInfo getHostInfo() {
        return hostInfo;
    }

    public void setHostInfo(MemberInfo hostInfo) {
        this.hostInfo = hostInfo;
    }

    public boolean isEnableBeauty() {
        return enableBeauty;
    }


}