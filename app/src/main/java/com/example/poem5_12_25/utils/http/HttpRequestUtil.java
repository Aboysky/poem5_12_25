package com.example.poem5_12_25.utils.http;

import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.poem5_12_25.contant.ServerContant;
import com.example.poem5_12_25.entity.Poem;
import com.example.poem5_12_25.pojo.PoemPojo;
import com.example.poem5_12_25.utils.http.tool.HttpRequestData;
import com.example.poem5_12_25.utils.http.tool.HttpResponseData;
import com.example.poem5_12_25.utils.http.tool.StreamTool;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class HttpRequestUtil {

    private static final String TAG = "HttpRequestUtil";

    /**
     * 设置http连接的头部信息
     * @param conn
     * @param method
     * @param req_data
     * @throws ProtocolException
     */
    private static void setConnHeader(HttpURLConnection conn, String method, HttpRequestData req_data)
            throws ProtocolException {
        conn.setRequestMethod(method);
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(10000);
        conn.setRequestProperty("Accept", "*/*");
        //IE使用
//		conn.setRequestProperty("Accept-Language", "zh-CN");
//		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.2; .NET4.0C)");
        //firefox使用
        conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-us;q=0.5,en;q=0.3");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36 Edg/87.0.664.66");
        conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
        if (req_data.content_type.equals("") != true) {
            conn.setRequestProperty("Content-Type", req_data.content_type);
        }
        if (req_data.x_requested_with.equals("") != true) {
            conn.setRequestProperty("X-Requested-With", req_data.x_requested_with);
        }
        if (req_data.referer.equals("") != true) {
            conn.setRequestProperty("Referer", req_data.referer);
        }
        if (req_data.cookie.equals("") != true) {
            conn.setRequestProperty("Cookie", req_data.cookie);
            Log.d(TAG, "setConnHeader cookie=" + req_data.cookie);
        }
    }

    private static String getRespCookie(HttpURLConnection conn, HttpRequestData req_data) {
        String cookie = "";
        Map<String, List<String>> headerFields = conn.getHeaderFields();
        if (headerFields != null) {
            List<String> cookies = headerFields.get("Set-Cookie");
            if (cookies != null) {
                for (String cookie_item : cookies) {
                    cookie = cookie + cookie_item + "; ";
                }
            } else {
                cookie = req_data.cookie;
            }
        } else {
            cookie = req_data.cookie;
        }
        Log.d(TAG, "cookie=" + cookie);
        return cookie;
    }

    /**
     * 根据req_data加载接口数据
     * @param req_data
     * @return
     */
    public static HttpResponseData getData(HttpRequestData req_data) {
        HttpResponseData resp_data = new HttpResponseData();
        try {
            URL url = new URL(req_data.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置连接头
            setConnHeader(conn, "GET", req_data);
            conn.connect();
            resp_data.content = StreamTool.getUnzipStream(conn.getInputStream(),
                    conn.getHeaderField("Content-Encoding"), req_data.charset);
            resp_data.cookie = conn.getHeaderField("Set-Cookie");
            resp_data.status_code = conn.getResponseCode();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            resp_data.err_msg = e.getMessage();
            resp_data.success = false;
        }
        return resp_data;
    }

    /**
     * 从服务器拿到一首诗并返回PoemPojo对象
     * @param id 现在显示的诗词id,保证拿到的不和这一首相同
     * @return Poempoho 封装的Poem
     */
    public static PoemPojo getHttpPoem(Long id){
        URL url = null;
        try {
            if (id!=null){
                url = new URL(ServerContant.GET_POEM_URL+"?id="+id.toString());
            }else {
                url = new URL(ServerContant.GET_POEM_URL);
            }

            HttpURLConnection connect=(HttpURLConnection)url.openConnection();
            connect.setRequestMethod("GET");
            connect.connect();
            if (connect.getResponseCode() ==200){
                InputStream input=connect.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(input));
                String line = null;
                System.out.println(connect.getResponseCode());
                StringBuilder sb = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
                return JSON.parseObject(sb.toString(), PoemPojo.class);
            }
            return null;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }


    /**
     * 登录验证请求
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public static Boolean login(String username,String password){
        String param = "{\"username\":\""+username+"\","+"\"password\":\""+password+"\"}";
        System.out.println(param);
        PrintWriter out = null;
        BufferedReader in1 = null;
        try {
            URL url = new URL(ServerContant.LOGIN_URL);
            HttpURLConnection connect=(HttpURLConnection)url.openConnection();
            setConnHeader(connect,"POST",new HttpRequestData(ServerContant.LOGIN_URL));
            connect.setDoOutput(true);
            connect.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(connect.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            connect.connect();
            if (connect.getResponseCode() == 200){
                InputStream input=connect.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(input));
                String line = null;
                System.out.println(connect.getResponseCode());
                StringBuilder sb = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
                System.out.println(sb.toString());
                JSONObject jsonObject = JSON.parseObject(sb.toString());
                Integer code = (Integer) jsonObject.get("code");
                System.out.println("code = "+code);
                if (code!=null&&code==200){
                    System.out.println("后端验证成功");
                    return Boolean.TRUE;
                }
                return Boolean.FALSE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            System.out.println(e.toString());
            return Boolean.FALSE;
        }
    }

    public static Boolean register(String username,String password){
        String param = "{\"username\":\""+username+"\","+"\"password\":\""+password+"\"}";
        System.out.println(param);
        PrintWriter out = null;
        BufferedReader in1 = null;
        try {
            URL url = new URL(ServerContant.REGISTER_URL);
            HttpURLConnection connect=(HttpURLConnection)url.openConnection();
            setConnHeader(connect,"POST",new HttpRequestData(ServerContant.LOGIN_URL));
            connect.setDoOutput(true);
            connect.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(connect.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            connect.connect();
            if (connect.getResponseCode() == 200){
                InputStream input=connect.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(input));
                String line = null;
                System.out.println(connect.getResponseCode());
                StringBuilder sb = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
                System.out.println(sb.toString());
                JSONObject jsonObject = JSON.parseObject(sb.toString());
                Integer code = (Integer) jsonObject.get("code");
                if (code!=null&&code==200){
                    return Boolean.TRUE;
                }
                return Boolean.FALSE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            System.out.println(e.toString());
            return Boolean.FALSE;
        }
    }



    public static String getHtml(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200) {
            InputStream in = conn.getInputStream();
            byte[] data = StreamTool.readInputStream(in);
            return new String(data, "UTF-8");
        }
        return null;
    }

    //get图片数据
    public static HttpResponseData getImage(HttpRequestData req_data) {
        HttpResponseData resp_data = new HttpResponseData();
        try {
            URL url = new URL(req_data.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            setConnHeader(conn, "GET", req_data);
            conn.connect();

            InputStream is = conn.getInputStream();
            resp_data.bitmap = BitmapFactory.decodeStream(is);
            resp_data.cookie = conn.getHeaderField("Set-Cookie");
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            resp_data.err_msg = e.getMessage();
            resp_data.success = false;
        }
        return resp_data;
    }

    //post的内容放在url中
    public static HttpResponseData postUrl(HttpRequestData req_data) {
        HttpResponseData resp_data = new HttpResponseData();
        String s_url = req_data.url;
        if (req_data.params != null) {
            s_url += "?" + req_data.params.toString();
        }
        Log.d(TAG, "s_url=" + s_url);
        try {
            URL url = new URL(s_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            setConnHeader(conn, "POST", req_data);
            conn.setDoOutput(true);

            conn.connect();
            resp_data.content = StreamTool.getUnzipStream(conn.getInputStream(),
                    conn.getHeaderField("Content-Encoding"), req_data.charset);
            resp_data.cookie = conn.getHeaderField("Set-Cookie");
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            resp_data.err_msg = e.getMessage();
            resp_data.success = false;
        }
        return resp_data;
    }

    //post的内容放在输出流中
    public static HttpResponseData postData(HttpRequestData req_data) {
        req_data.content_type = "application/x-www-form-urlencoded";
        HttpResponseData resp_data = new HttpResponseData();
        String s_url = req_data.url;
        Log.d(TAG, "s_url=" + s_url + ", params=" + req_data.params.toString());
        try {
            URL url = new URL(s_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            setConnHeader(conn, "POST", req_data);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();

            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(req_data.params.toString());
            out.flush();

            resp_data.content = StreamTool.getUnzipStream(conn.getInputStream(),
                    conn.getHeaderField("Content-Encoding"), req_data.charset);
            resp_data.cookie = getRespCookie(conn, req_data);
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            resp_data.err_msg = e.getMessage();
            resp_data.success = false;
        }
        return resp_data;
    }

    //post的内容分段传输
    public static HttpResponseData postMultiData(HttpRequestData req_data, Map<String, String> map) {
        HttpResponseData resp_data = new HttpResponseData();
        String s_url = req_data.url;
        Log.d(TAG, "s_url=" + s_url);
        String end = "\r\n";
        String hyphens = "--";
        try {
            URL url = new URL(s_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            setConnHeader(conn, "POST", req_data);
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + req_data.boundary);
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            StringBuffer buffer = new StringBuffer();
            Log.d(TAG, "map.size()=" + map.size());
            Iterator<String> it = map.keySet().iterator();
            while (it.hasNext()) {
                String str = it.next();
                buffer.append(hyphens + req_data.boundary + end);
                buffer.append("Content-Disposition: form-data; name=\"");
                buffer.append(str);
                buffer.append("\"" + end + end);
                buffer.append(map.get(str));
                buffer.append(end);
                Log.d(TAG, "key=" + str + ", value=" + map.get(str));
            }
            if (map.size() > 0) {
                buffer.append(hyphens + req_data.boundary + end);
                byte[] param_data = buffer.toString().getBytes(req_data.charset);
                OutputStream out = conn.getOutputStream();
                out.write(param_data);
                out.flush();
            }

            conn.connect();
            resp_data.content = StreamTool.getUnzipStream(conn.getInputStream(),
                    conn.getHeaderField("Content-Encoding"), req_data.charset);
            resp_data.cookie = conn.getHeaderField("Set-Cookie");
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            resp_data.err_msg = e.getMessage();
            resp_data.success = false;
        }
        return resp_data;
    }
//
//    public static void main(String[] args) {
//        String gethttpresult = gethttpresult(new HttpRequestData("http://127.0.0.1:5000/getpoem"));
//        System.out.println(gethttpresult);
//    }
}
