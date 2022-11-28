package com.bosonit.pruebamain;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.ainirobot.coreservice.client.Definition;
import com.ainirobot.coreservice.client.RobotApi;

import com.ainirobot.coreservice.client.listener.CommandListener;
import com.ainirobot.coreservice.client.robotsetting.RobotSettingApi;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private int checkTimes;
    LinearLayout mContent;
    TextView tvBateria;
    TextView tvSystemInfo;
    TextView tvSn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContent = findViewById(R.id.main_activity);
        checkInit();
        initTextViews();
        setTextViews();




    }

    @SuppressLint("SetTextI18n")
    private void setTextViews() {
        tvBateria.setText("La batería esta al " + RobotSettingApi.getInstance().getRobotString(Definition.ROBOT_SETTINGS_BATTERY_INFO));
        tvSystemInfo.setText("La version del robot es: " + RobotApi.getInstance().getRobotVersion());
        tvSn.setText("El numero de serie del robot es" + RobotApi.getInstance().getRobotSn(new CommandListener() {
            @Override
            public void onResult(int result, String message) {
                if (Definition.RESULT_OK == result) {
                    String serialNum = message;
                } else {
                }
            }
        }));
    }

    private void initTextViews() {
        tvBateria = (TextView) findViewById(R.id.tv_bateria);
        tvSystemInfo = (TextView)findViewById(R.id.systeminfo);
        tvSn = (TextView)findViewById(R.id.tvSn);
    }

    private void checkInit(){
        checkTimes++;
        if(checkTimes > 10){
            Log.i(TAG, "Fallo, 10 intentos de conexion");
        }
        else if(RobotApi.getInstance().isApiConnectedService()){
            Log.i(TAG, "Conectado -- check");
        }else
        {
            System.out.println("intento de conextion: " + checkTimes);
            mContent.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkInit();
                }
            },300);
        }
    }


}