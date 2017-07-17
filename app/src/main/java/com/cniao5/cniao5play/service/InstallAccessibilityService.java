package com.cniao5.cniao5play.service;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/7/8.
 */

public class InstallAccessibilityService extends AccessibilityService {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        AccessibilityNodeInfo source = event.getSource();

        if (source == null) {
            return;
        }

        int eventType = event.getEventType();

        //检测操作，检索窗口内容
        if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED ||
                eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {

            // 中文系统
            click("安装");
            click("下一步");
            click("确定");
            click("完成");

            //英文


        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void click(String text) {

        AccessibilityNodeInfo rootInActiveWindow = getRootInActiveWindow();

        if (rootInActiveWindow != null) {
            //获取所有节点
            List<AccessibilityNodeInfo> nodeInfos = rootInActiveWindow.findAccessibilityNodeInfosByText(text);

            if (nodeInfos == null) {
                return;
            }

            for (AccessibilityNodeInfo info : nodeInfos) {
                if ("android.widget.Button".equals(info.getClassName()) && info.isClickable()) {
                    info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        }
    }

    @Override
    public void onInterrupt() {

    }
}
