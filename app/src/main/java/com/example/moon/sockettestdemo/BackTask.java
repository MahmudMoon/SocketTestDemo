package com.example.moon.sockettestdemo;

import android.net.wifi.WifiManager;
import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static android.content.Context.WIFI_SERVICE;

public class BackTask extends AsyncTask<String,Void,String> {

    String Ip ;
    String msg;
    Socket socket;
    @Override
    protected String doInBackground(String... strings) {
        Ip = strings[0];
        msg = strings[1];
        try {
            socket = new Socket(Ip,8090);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(msg);
            return "Send";

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
