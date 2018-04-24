package com.example.iot_stu.apidemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.nlecloud.nlecloudlibrary.api.net.HttpEngine;
import com.nlecloud.nlecloudlibrary.core.ActionCallbackListener;
import com.nlecloud.nlecloudlibrary.core.AppActionImpl;
import com.nlecloud.nlecloudlibrary.domain.AccessToken;

public class MainActivity extends AppCompatActivity {
    private Button btn_login,btn_On,btn_Off;
    private AppActionImpl actionImpl;
    private SeekBar rgb_light;

    //RGB灯带执行器id 196

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //实例化控件
        init();

        //设置云平台 ip
        HttpEngine.SERVER_URL = "http://10.5.15.253";
        //登录云平台

        //设置控件监听器
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(MainActivity.this);
            }
        });
        //执行控制器开
        btn_On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               isOnOff (true);
            }
        });
        //执行控制器关
        btn_Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isOnOff(false);

            }
        });
        //RGB灯带-控件
        rgb_light.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                actionImpl.OperationAtuator(196,i,null);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    private void isOnOff(boolean flag){
        if (flag){
            actionImpl.onOff(182,true,null);
        }
        else{

            actionImpl.onOff(182,false,null);
        }
    }
    private void login (final Context context){
        actionImpl = new AppActionImpl(context); //创建类对象
        actionImpl.login("test","123456", "iotOne",new  ActionCallbackListener<AccessToken>(){

            @Override
            public void onSuccess(AccessToken token) {
                HttpEngine.ACCESSTOKEN = token.getAccessToken();//得到token
                Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(String s, String s1) {
                Toast.makeText(context,"登录失败",Toast.LENGTH_SHORT).show();
            }
        });

        }
    //实例化控件
    void init(){
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_Off   = (Button)findViewById(R.id.btn_off);
        btn_On   = (Button)findViewById(R.id.btn_on);
        rgb_light = (SeekBar)findViewById(R.id.rgb_light);
    }
}
