package com.example.supermarket_1_0.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.os.Looper;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.supermarket_1_0.R;
import com.example.supermarket_1_0.pay_activity.entity.ProductMsg;

import java.util.ArrayList;
import java.util.List;


public class Utils {
    private static Utils utils;
    public static void sleeptime(int ms){
        try {
            Thread.currentThread();
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Utils getInstance() {
        if (utils == null) {
            utils = new Utils();
        }
        return utils;
    }

    public static String makeformat(String str) {
        String str2 = "";
        int len=str.length();
        int i = 0;
        Log.i("format_left",str);
        while (i<len) {
            char c = str.charAt(i);
            if (c >= '0' && c <= '9') break;
            str2 += str.charAt(i);
            i++;
        }
        str2 += "%.2f";
        while (i<len) {
            char c = str.charAt(i);
            if (!((c >= '0' && c <= '9') || (c == '.'))) break;
            i++;
        }
        while (i < str.length()) {
            str2 += str.charAt(i);
            i++;
        }
        return str2;
    }

    public static String reverseformat(String str) {
        String str2 = "";
        int i = str.length()-1;
        int j,k;
        while (i>=0) {
            char c = str.charAt(i);
            if (c >= '0' && c <= '9') break;
            i--;
        }
        j=i+1;
        while (i>=0) {
            char c = str.charAt(i);
            if (!((c >= '0' && c <= '9') || (c == '.'))) break;
            i--;
        }
        k=i+1;
        str2=str.substring(0,k)+"%d"+str.substring(j);
        return str2;
    }
    //activity??????
    public void startActivityCloseOther(Activity curAct, Class targetAct) {
        Intent intent = new Intent(curAct, targetAct);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        curAct.startActivity(intent);
        curAct.finish();

    }

    //activity??????
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void startActivity(Activity curAct, Class targetAct) {
        Intent intent = new Intent(curAct, targetAct);
        curAct.startActivity(intent);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void startActivityAnimation(Activity curAct, Class targetAct) {
        Intent intent = new Intent(curAct, targetAct);
        curAct.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(curAct).toBundle());
    }

    //activity?????? ??????activityId ????????????activity
    public void startActivity(Activity curAct, Class targetAct, int activityId) {
        Intent intent = new Intent(curAct, targetAct);
        intent.putExtra("activityId", activityId);
        curAct.startActivity(intent);
    }

    //activity??????,???????????????
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void startActivityData(Activity curAct, Class targetAct, ProductMsg productMsg) {
        Intent intent = new Intent(curAct, targetAct);
        intent.putExtra("product", productMsg);
        curAct.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(curAct).toBundle());
    }

    /*//activity ?????????????????????????????????
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void startActivityDataForResult(Activity curAct, Class targetAct, int requestCode, AddressInfo info){
        Intent intent = new Intent(curAct,targetAct);
        intent.putExtra("addressInfo",info);
        curAct.startActivityForResult(intent,requestCode, ActivityOptions.makeSceneTransitionAnimation(curAct).toBundle());
    }*/
    //activity??????????????????
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void startActivityForResultAnimation(Activity curAct, Class targetAct, int requestCode) {
        Intent intent = new Intent(curAct, targetAct);
        curAct.startActivityForResult(intent, requestCode, ActivityOptions.makeSceneTransitionAnimation(curAct).toBundle());
    }

    //activity??????,????????????????????????activity
    public void startActivityClose(Activity curAct, Class targetAct) {
        Intent intent = new Intent(curAct, targetAct);
        curAct.startActivity(intent);
        curAct.finish();
    }

    //activity??????,????????????????????????activity
    public void startActivityCloseAnimation(Activity curAct, Class targetAct) {
        Intent intent = new Intent(curAct, targetAct);
        curAct.startActivity(intent);
        curAct.finish();
    }

    public void tips(Context activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * ??????
     */
    public void sendNotification(Context context, String channelId, String title, String contentText, int notificationId) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = null;
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(channelId, channelId + "_name", NotificationManager.IMPORTANCE_HIGH);
            if (manager != null) {
                //?????? channel
                manager.createNotificationChannel(channel);
            }
            //api ??????26 ????????????channelId
            builder = new Notification.Builder(context, channelId);
        } else {
            builder = new Notification.Builder(context);
        }
        builder.setContentTitle(title);
        builder.setContentText(contentText);
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.mipmap.icon_dog);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_dog));
        builder.setAutoCancel(true);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);    //??????????????????????????????????????????????????????????????????
        assert manager != null;
        manager.notify(notificationId, builder.build());
    }

    /**
     * ?????????????????????
     */
    public Bitmap createCircleBitmap(Bitmap resource) {
        //?????????????????????
        int width = resource.getWidth();
        int height = resource.getHeight();

        int r = 0;
        if (width < height) {
            r = width;
        } else {
            r = height;
        }
        //??????????????????bitmap????????????????????????bitmap
        Bitmap roundBitmap = Bitmap.createBitmap(r, r, Bitmap.Config.ARGB_8888);
        //??????bitmap???????????????????????????
        Canvas canvas = new Canvas(roundBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);//?????????
        RectF rectF = new RectF(0, 0, r, r);
        //??????
        canvas.drawRoundRect(rectF, r / 2, r / 2, paint);
        //??????????????????????????????
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //????????????
        canvas.drawBitmap(resource, null, rectF, paint);

        return roundBitmap;
    }

    /**
     * ????????????????????????
     *
     * @param params
     * @return ???????????? true??????????????????false???????????????
     */
    public boolean checkParameter(String... params) {
        boolean result = true;
        for (String param : params) {
            if (TextUtils.isEmpty(param) || param.trim().length() == 0) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * activity ??????????????????????????????????????????
     *
     * @param activity     ??????activityd
     * @param transitionId ?????????????????????id ???R.transition.fade???
     */
    public void actUseAnim(Activity activity, int transitionId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            Transition slide = TransitionInflater.from(activity).inflateTransition(transitionId);
            activity.getWindow().setExitTransition(slide);    //??????
            activity.getWindow().setEnterTransition(slide);   //??????
            activity.getWindow().setReenterTransition(slide); //????????????
        }

    }

    /**
     * ????????????????????????
     *
     * @param context
     * @return
     */
    public List<Integer> getSize(Activity context) {
        List<Integer> list = new ArrayList<>();
        //TODO ?????????????????????
        Display display = context.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        list.add(width);
        list.add(height);
        return list;
    }

    /**
     * ??????????????????????????? dp ????????? ????????? px(??????)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * ???????????????????????????
     */
    public boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

}
