package com.munib.hostin.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class NotificationService extends Service {
    String SIGNALING_URI ="http://172.20.52.223:3000";

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


            obj.put("name","muneeb");

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
