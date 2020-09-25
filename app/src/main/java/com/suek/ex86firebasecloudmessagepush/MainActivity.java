package com.suek.ex86firebasecloudmessagepush;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickBtn(View view) {
        //앱을 FCM 서버에 등록하는 과정이 필요
        //앱을 FCM 서버에 등록하면 앱을 식별할 수 있는 고유 토큰값을 줌(문자열로 되어있음)- 토큰값을 통해 디바이스들이 구별됨
        //이 토큰값(InstanceID)을 통해서 앱들(디바이스들)을 구별하여 메세지가 전달되는 것임

        FirebaseInstanceId firebaseInstanceId= FirebaseInstanceId.getInstance();
        Task<InstanceIdResult> task = firebaseInstanceId.getInstanceId();    //결과(instanceID)를 가져오는 Task
        task.addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                String token= task.getResult().getToken();

                //토큰값 출력
                Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                //Logcat 창에 토큰값 출력 : i 정보, E 에러
                Log.i("TAG", token);   //식별자, token


                //실무에서는 이 token 값을 본인의 웹서버(dothome 서버)에 전송하여
                //웹 DB 에 token 값 저장하도록 해야함
            }
        });
    }
}
