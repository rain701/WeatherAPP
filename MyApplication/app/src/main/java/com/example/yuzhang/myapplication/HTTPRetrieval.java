/**
 * Created by yuzhang on 2019/9/27.
 */



    package com.example.yuzhang.myapplication;
    import android.util.Log;

    import org.json.JSONArray;

    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.io.PrintWriter;
    import java.net.HttpURLConnection;
    import java.net.MalformedURLException;
    import java.net.URL;

    public class HTTPRetrieval {
        public String HTTPWeatherGET(String cityCode) {
            String res = "";
            // StringBuilder 的作用类似于 + ，用于将字符串连接在一起
            StringBuilder sb = new StringBuilder();
            PrintWriter out=null;
            BufferedReader br=null;
            InputStream is=null;
            String param="key=b0cdb629c43d4418b56913d1f09f70a6&location=CN"+cityCode;
            String urlString = "https://free-api.heweather.com/s6/weather";
            try {
                // URL 是对 url 的处理类
                URL url = new URL(urlString);
                // 得到connection对象
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setRequestProperty("accept","*/*");
                //发送参数
                httpURLConnection.setDoOutput(true);
                out=new PrintWriter(httpURLConnection.getOutputStream());
                out.print(param);
                out.flush();
                // 使用 InputStreamReader 进行数据接收
                is=httpURLConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8");
                // 缓存
                br = new BufferedReader(isr);
                String temp = null;
                // 读取接收的数据
                while ( (temp = br.readLine()) != null) {
                    sb.append(temp);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if(is!=null){
                        is.close();
                    }
                    if(br!=null){
                        br.close();
                    }
                    if (out!=null){
                        out.close();
                    }
                } catch ( Exception ignored ) {}
            }
            System.out.println(sb.toString());
            res = sb.toString();
            return res;
        }

    }



