package com.example.supermarket_1_0.work_activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.supermarket_1_0.NoticeActivity;
import com.example.supermarket_1_0.R;
import com.example.supermarket_1_0.core.BaseActivity;
import com.example.supermarket_1_0.utils.XToastUtils;
import com.example.supermarket_1_0.work_activity.entity.WorkTimeItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class WorkActivity extends BaseActivity implements View.OnClickListener {
    private String userid, nickname;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.punch_in_button)
    Button mPunchInButton;
    @BindView(R.id.punch_in_textView)
    TextView mPunchInTextview;
    @BindView(R.id.punch_out_button)
    Button mPunchOutButton;
    @BindView(R.id.punch_out_textView)
    TextView mPunchOutTextview;
    @BindView(R.id.beg_off_button)
    Button mBegOffButton;
    @BindView(R.id.beg_off_textView)
    TextView mBegOffTextview;
    @BindView(R.id.work_button)
    Button mWorkButton;

    public WorkActivity context;

    final String strDateFormat = "yyyy-MM-dd HH:mm:ss";
    Date date;
    SimpleDateFormat dateFormat;
    WorkTimeItem workTimeItem;
    String starttime, endtime, begofftime;
    private final String TAG = getClass().getSimpleName();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        initViews();
        initListeners();
        test();
    }

    private void test() {
        mCompositeDisposable.add(Observable.interval(0, 120, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver(1)));
    }

    private DisposableObserver getObserver(final int id) {
        DisposableObserver disposableObserver = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object o) {
                Log.d(id + TAG, "#####开始#####");
                Log.d(id + "数据", String.valueOf(o));
                new NoticeActivity(context).getNote(userid);
                Log.d(id + TAG, "#####结束#####");
            }

            @Override
            public void onComplete() {
                Log.d(id + TAG, "onComplete");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(id + TAG, e.toString(), e);
            }
        };

        return disposableObserver;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 如果退出程序，就清除后台任务
        mCompositeDisposable.clear();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_work;
    }

    protected boolean isSupportSlideBack() {
        return false;
    }

    private void initViews() {
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        nickname = intent.getStringExtra("nickname");

        /*userid = "018307130003";
        nickname = "张三";*/
        //XToastUtils.info("otherlogin " + userid + " " + nickname);
        toolbar.setTitle("祝你今天工作顺利，" + nickname);

        date = new Date();
        dateFormat = new SimpleDateFormat(strDateFormat);
        String sim = dateFormat.format(date);

        workTimeItem = new WorkTimeItem(userid);
        while (workTimeItem.getstarttime() == null) ;
        starttime = workTimeItem.getstarttime();
        endtime = workTimeItem.getendtime();
        begofftime = workTimeItem.getbegofftime();

        if (starttime == "") starttime = "xx-xx-xx";
        if (endtime == "") endtime = "xx-xx-xx";
        if (begofftime == "") begofftime = "xx-xx-xx";

        mPunchInTextview.setText("你上次打卡时间为" + starttime + "\n当前时间为" + sim);
        mPunchOutTextview.setText("你上次打卡时间为" + endtime + "\n当前时间为" + sim);
        mBegOffTextview.setText("你上次请假时间为" + begofftime + "\n当前时间为" + sim);
    }

    protected void initListeners() {
        mPunchInButton.setOnClickListener(this);
        mPunchOutButton.setOnClickListener(this);
        mBegOffButton.setOnClickListener(this);
        mWorkButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.punch_in_button:
                date = new Date();
                String sim = dateFormat.format(date);
                mPunchInTextview.setText("你已经打卡，请勿重复打卡\n当前时间为" + sim);
                workTimeItem.setstarttime(sim);
                workTimeItem.addPunchInTime();
                break;
            case R.id.punch_out_button:
                date = new Date();
                sim = dateFormat.format(date);
                mPunchOutTextview.setText("你已经打卡，请勿重复打卡\n当前时间为" + sim);
                workTimeItem.setendtime(sim);
                workTimeItem.addPunchOutTime();
                break;
            case R.id.beg_off_button:
                date = new Date();
                sim = dateFormat.format(date);
                mBegOffTextview.setText("你已经请假，请勿重复请假\n当前时间为" + sim);
                workTimeItem.setbegofftime(sim);
                workTimeItem.addBegOffTime();
                break;
            case R.id.work_button:
                //XToastUtils.toast("click work button!");
                Intent intent = new Intent(WorkActivity.this, WorkListActivity.class);
                intent.putExtra("name", nickname);
                intent.putExtra("userid", userid);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}
