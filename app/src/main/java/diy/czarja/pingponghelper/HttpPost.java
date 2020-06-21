package diy.czarja.pingponghelper;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPost implements Runnable{
    private String returnStr = "Default Message";

    @Override
    public void run() {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL("http://192.168.4.1/deliver");
            connection = (HttpURLConnection) url.openConnection();
            //设置请求方法
            connection.setRequestMethod("POST");
            //设置连接超时时间（毫秒）
            connection.setConnectTimeout(5000);
            //设置读取超时时间（毫秒）
            connection.setReadTimeout(5000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            OutputStream out = connection.getOutputStream();
            String data = "";
            out.write(data.getBytes());
            out.flush();
            //返回输入流
            if (connection.getResponseCode() / 100 == 2) {
                returnStr = "Successfully Delivered";
            }else{
                Log.d("httpCode", String.valueOf(connection.getResponseCode()));
            }
        } catch (Exception e) {
            returnStr = e.getMessage();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    returnStr = e.getMessage();
                }
            }
            if (connection != null) {//关闭连接
                connection.disconnect();
            }
        }
    }

    public String getReturnValue(){
        return returnStr;
    }
}
