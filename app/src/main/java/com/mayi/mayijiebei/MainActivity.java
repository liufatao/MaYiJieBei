package com.mayi.mayijiebei;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  LayoutInflater inflater;
  RelativeLayout.LayoutParams linearParams;
  LinearLayout ly_load_borad;
  Button btn_going;
  Thread thread;
  String userName="阿宝";
  String userPhone="123456789";
  String userDaikuan="100000";
  int h = 450;
  private PopupWindow message_window, load_window;
  private View message_view, load_view;
  private ProgressViewTest progress;
  Handler handler = new Handler() {
    public void handleMessage(Message msg) {
      if (msg.what == 0x01) {
        progress.setCurrentCount(msg.arg1);

        showLoadWindow();

      }
      else {
        dismessLoadWindow();
      }
    }

    ;
  };
  Runnable runnable=new Runnable() {
    @Override public void run() {

        showLoadWindow();

    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    inflater = LayoutInflater.from(this);
    btn_going = findViewById(R.id.btn_going);
    btn_going.setOnClickListener(this);
    TextView tv_back = findViewById(R.id.tv_back);
    tv_back.setOnClickListener(this);
    TextView tv_name=findViewById(R.id.tv_name);
    TextView tv_Daikuan=findViewById(R.id.tv_userDaikuan);
    Bundle intent=getIntent().getExtras();
    userName=intent.getString("name");
    userPhone=intent.getString("phone");
    userDaikuan=intent.getString("dk");
    tv_name.setText(userName+",你可以借");
    tv_Daikuan.setText("¥"+Utils.addComma(userDaikuan));
  }

  /**
   *
   */
  private void showMessageWindow() {

    message_view = inflater.inflate(R.layout.message_window, null, false);
    message_window = new PopupWindow(message_view, ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT, true);
    //设置PopupWindow动画
    message_window.setFocusable(true);
    message_window.setOutsideTouchable(false);
    message_window.setAnimationStyle(android.R.style.Animation_InputMethod);
    message_window.setBackgroundDrawable(new ColorDrawable());
    message_window.showAtLocation(message_view, Gravity.CENTER, 0, 0);
    TextView tv_ok = message_view.findViewById(R.id.tv_ok);
    tv_ok.setOnClickListener(this);
    TextView tv_content=message_view.findViewById(R.id.txt_content);
    tv_content.setText("亲爱的支付宝客户：您申请的支付宝借呗开通成功"+Utils.addComma(userDaikuan)+"额度，后续请保持良好信用，按时还款。");
  }

  private void dismessLoadWindow() {
    if (load_window.isShowing()) {
      load_window.dismiss();
      showMessageWindow();
    }
  }

  /**
   *
   */
  private void showLoadWindow() {
    if (load_window != null && load_window.isShowing()) {
      linearParams.height = Utils.px2dp(getApplicationContext(), h);
      linearParams.width = Utils.px2dp(getApplicationContext(), 1200);
      linearParams.setMargins(60, 400, 0, 5);
      ly_load_borad.setLayoutParams(linearParams);
      load_window.update();
    } else {

      load_view = inflater.inflate(R.layout.load_window, null, false);
      load_window = new PopupWindow(load_view, ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.MATCH_PARENT, true);
      //设置PopupWindow动画
      load_window.setFocusable(true);
      load_window.setOutsideTouchable(false);
      load_window.setAnimationStyle(android.R.style.Animation_InputMethod);
      load_window.setBackgroundDrawable(new ColorDrawable());
      load_window.showAtLocation(load_view, Gravity.CENTER, 0, 0);
      progress = load_view.findViewById(R.id.progressbar);
      TextView tv_username=load_view.findViewById(R.id.txt_username);
      TextView tv_phone=load_view.findViewById(R.id.txt_userphone);
      TextView tv_daikuan=load_view.findViewById(R.id.tv_daikuan);
      tv_username.setText("正在申请姓名："+userName);
      tv_phone.setText("正在申请号码："+userPhone);
      tv_daikuan.setText("申请额度"+Utils.addComma(userDaikuan)+"额度");
      ly_load_borad = load_view.findViewById(R.id.ly_load_borad);
      progress.setMaxCount(100);
      linearParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
          ViewGroup.LayoutParams.WRAP_CONTENT);
      ly_load_borad.getViewTreeObserver()
          .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
              // TODO Auto-generated method stub
              ly_load_borad.getViewTreeObserver().removeGlobalOnLayoutListener(this);

              Log.e("测试：", Utils.px2dp(getApplicationContext(), ly_load_borad.getMeasuredHeight())
                  + ","
                  + ly_load_borad.getMeasuredHeight()
                  + ":"
                  + ly_load_borad.getMeasuredWidth());
            }
          });
      load_window.setOnDismissListener(new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {

          h = 200;
          if (thread != null) {
            thread.isAlive();
          }

          handler.removeMessages(0x01);
        }
      });
      thread = new Thread(new Runnable() {
        @Override public void run() {
          for (int i = 0; i <= progress.getMaxCount(); i++) {
            Message msg = new Message();
            msg.arg1 = i;
            msg.what = 0x01;
            h += 6;
            handler.sendMessage(msg);
            if (i>=progress.getMaxCount()){
              msg.what = 2;
            }
            try {
              //每隔0.1秒进度前进1
              Thread.sleep(100);
            } catch (InterruptedException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }

          }
        }
      });
      thread.start();
    }
  }

  @Override public void onClick(View view) {
    switch (view.getId()) {
      case R.id.tv_ok:
        if (message_window.isShowing()) {
          message_window.dismiss();
        }

        break;
      case R.id.btn_going:
        handler.postDelayed(runnable,5*1000);

        break;
      case R.id.tv_back:
        finish();
        break;
    }
  }
}
