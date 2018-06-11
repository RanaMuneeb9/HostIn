package com.munib.hostin.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.munib.hostin.MainActivity;
import com.munib.hostin.R;
import com.munib.hostin.SavedSharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class NotificationService extends Service {
    String SIGNALING_URI ="http://13.127.35.98:3300";

    Socket socket;

    JSONObject obj;

    @Override
    public void onCreate() {


        super.onCreate();


        try {
            socket = IO.socket(SIGNALING_URI);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        obj = new JSONObject();
        try {


            obj.put("name", SavedSharedPreferences.getUserEmail(getApplicationContext()));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("done","Started");
        socket.connect();
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();

        socket.emit("user",obj);
        socket.on("notification", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                JSONObject obj = (JSONObject) args[0];
                try {
                    Log.d("abc",obj.getString("msg"));

                    NotificationCompat.Builder builder =
                            new NotificationCompat.Builder(NotificationService.this)
                                    .setSmallIcon(R.drawable.com_facebook_button_like_icon_selected)
                                    .setContentTitle("Host Inn")
                                    .setContentText(obj.getString("msg"));

                    if(obj.getString("msg").equals("You Booking Has not Accepted !"))
                    {
                        SavedSharedPreferences.setBookingRequest(getApplicationContext(),0);
                    }else if(obj.getString("msg").equals("user Removed From Current Hostel !"))
                    {
                        SavedSharedPreferences.setBookingRequest(getApplicationContext(),0);
                    }

                    Intent notificationIntent = new Intent(NotificationService.this, MainActivity.class);
                    PendingIntent contentIntent = PendingIntent.getActivity(NotificationService.this, 0, notificationIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(contentIntent);

                    // Add as notification
                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.notify(0, builder.build());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
