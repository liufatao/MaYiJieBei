package com.mayi.mayijiebei;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
  String userName="阿宝";
  String userPhone="123456789";
  String userDaikuan="100000";
  EditText et_userName,et_userPhone,et_userDaikuan;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    Button button=findViewById(R.id.btn_commit);
     et_userName=findViewById(R.id.et_userName);
     et_userPhone=findViewById(R.id.et_userPhone);
    et_userDaikuan=findViewById(R.id.et_userDaikuan);


    button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        userName=et_userName.getText().toString();
        userPhone=et_userPhone.getText().toString();
        userDaikuan=et_userDaikuan.getText().toString();
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("name", userName);
        bundle.putString("phone", userPhone);
        bundle.putString("dk",userDaikuan);
        intent.putExtras(bundle);
        startActivity(intent);
      }
    });
  }
}
