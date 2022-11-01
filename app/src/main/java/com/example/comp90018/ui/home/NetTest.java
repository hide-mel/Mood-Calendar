package com.example.comp90018.ui.home;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetTest {
    public static String sendRequest(String urlParam, String coordinate){
        HttpURLConnection con = null;
        BufferedReader buffer = null;
        StringBuffer resultBuffer = null;

        coordinate = "-33.7,127";
        urlParam = "http://api.positionstack.com/v1/reverse?access_key=5314a6050cd7bd68eb2ebcc6c6200bfd&query=";

        try{
            // prepare the params and send request
            urlParam += coordinate;
            URL url = new URL(urlParam);
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type","application/json");
            con.setDoOutput(true);


            System.out.println("message out");
            System.out.println( con.getURL().getPath());

            // receive response
            int responseCode = con.getResponseCode();
            System.out.println(responseCode);
            if (responseCode == 200){
                InputStream is = con.getInputStream();
                resultBuffer = new StringBuffer();
                String line;
                buffer = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                while ((line = buffer.readLine()) != null){
                    resultBuffer.append(line);
                }
                return resultBuffer.toString();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        String coordinate = "-33.7,127";
        String url = "http://api.positionstack.com/v1/reverse?access_key=5314a6050cd7bd68eb2ebcc6c6200bfd&query=";
        System.out.println(sendRequest(url,coordinate));
    }
}
