package com.suek.ex86firebasecloudmessagepush;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFCMReceiveService extends FirebaseMessagingService {

    //push server 에서 보낸 메세지가 수신되었을때 자동으로 발동하는 메소드

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        //이 안에서는 알림 Notification 만 만들 수 있음 [ 심지어 Toast 도 불가 ]
        //우선, 리시브 확인용으로 Logcat 에 출력 해보기 (로그캣은 화멵출력이 아니니까 제약을 받지않음)
        Log.i("TAG", "onMessageReceived!!!");

        //이 메소드의 파라미터로 전달된 RemoteMessage 객체 : 받은 원격 메세지 -> 모든 메세지를 가져옴

        //메세지를 보낸 기기명[ Firebase 서버에서 자동으로 지정된 이름 ]을 받을 수 있음
        String fromWho= remoteMessage.getFrom();

        //알림에 대한 데이터들
        String notiTitle= "title";      //알림제목    //제목이 안왔을때 기본 값
        String notiBody= "body text";   //알림텍스트  //글씨가 안왔을때 기본 값

        if(remoteMessage.getNotification() != null){
            notiTitle= remoteMessage.getNotification().getTitle();
            notiBody= remoteMessage.getNotification().getBody();
            //Uri notiImgUri= remoteMessage.getNotification().getImageUrl();   //이렇게 하면 이미지를 받음.. 근데 이건 유료...;;
        }

        //firebase 푸시 메세지에 추가로 데이터가 있을 경우가 있음.. ([ 키:밸류 ] 형태로 송신된 데이터들)
        Map<String, String> data= remoteMessage.getData();

        String name=null;
        String msg= null;
        if(data != null){
            name= data.get("name");
            msg= data.get("msg");
        }


        //잘 받았는지 확인
        Log.i("TAG", fromWho + " : " + notiTitle + " , " + notiBody + " >> " + name +", " + msg);

        //이 받은 값들을 알림 Notification 객체를 만들어 공지하기
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder= null;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel= new NotificationChannel("ch01", "channel 01", NotificationManager.IMPORTANCE_HIGH );
            notificationManager.createNotificationChannel(channel);

            builder= new NotificationCompat.Builder(this, "ch01");
        }else {
            builder= new NotificationCompat.Builder(this, null);   //오레오 전 버전
        }

        builder.setSmallIcon(R.drawable.ic_stat_name);
        builder.setContentTitle(notiTitle);
        builder.setContentText(notiBody);

        //알림을 선택했을 때 실행될 액티비티를 실행하는 Intent 생성
        Intent intent= new Intent(this, MessageActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("msg", msg);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);   //이 데이터들이 MessageActicity.java 로 보내짐
        //보류중인 인텐트로 변환
        PendingIntent pendingIntent= PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);   //intent= 메세지를 실행시키는 위의 인텐트
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        Notification notification= builder.build();
        notificationManager.notify(111, notification);
    }
}
