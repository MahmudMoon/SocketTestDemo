package com.example.moon.sockettestdemo;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    EditText et_ip,et_msg;
    Button btn;
    String Ip = "";
    TextView tv_ip;
    String s = "";
    TextView tv_msg;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_ip = (EditText)findViewById(R.id.et_ip_addr);
        btn = (Button)findViewById(R.id.btn_send_msg);
        tv_ip = (TextView)findViewById(R.id.tv_ip);
        et_msg = (EditText)findViewById(R.id.editText);
        tv_msg = (TextView)findViewById(R.id.tv_msg);


        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        tv_ip.setText(ip);
        et_ip.setText("192.168.1.10");
        et_msg.setText("Hi Baby");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                DataInputStream dataOutputStream;

                try {
                    ServerSocket serverSocket = new ServerSocket(8090);
                        while (true) {
                            Socket socket = serverSocket.accept();
                            Log.i(TAG, "Server running");
                            if (socket != null) {
                                dataOutputStream = new DataInputStream(socket.getInputStream());
                                s = dataOutputStream.readUTF();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                                        tv_msg.setText(s);
                                    }
                                });

                                Log.i(TAG, "message: " + s);
                            }
                        }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            });
        thread.start();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ip = et_ip.getText().toString().trim();
                new BackTask(){
                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        Log.i(TAG, "onPostExecute: "+s);
                    }
                }.execute(Ip,et_msg.getText().toString().trim());
            }
        });
    }
}
