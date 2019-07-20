package com.example.moon.sockettestdemo;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerClass extends AsyncTask<String,Void, Socket> {
    private static final String TAG = "ServerClass";

    @Override
    protected Socket doInBackground(String... strings) {
        String Ip = strings[0];
        try {
            ServerSocket serverSocket = new ServerSocket(8090);
            Socket accept = serverSocket.accept();
            Log.i(TAG, "doInBackground: "+"Connection Established");
            return accept;
        } catch (IOException e) {
            Log.i(TAG, "doInBackground: "+e.toString()  );
            e.printStackTrace();
        }

        return null;
    }
}
