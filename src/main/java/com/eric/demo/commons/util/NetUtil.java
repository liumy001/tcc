package com.eric.demo.commons.util;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 网络工具类
 *
 * @author Administrator
 */
public class NetUtil {

    private static final String APPLICATION_JSON = "application/json";

    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

    // 日志
    private static final Logger logger = LoggerFactory.getLogger(NetUtil.class);

    /**
     * 通过http获取图片信息
     *
     * @param path 图片地址
     * @return
     * @throws Exception
     */
    public static byte[] getImage(String path) throws Exception {

        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(6 * 1000);
        if (conn.getResponseCode() == 200) {
            InputStream inputStream = conn.getInputStream();
            return readStream(inputStream);
        }
        return null;
    }

    /**
     * 通过http获取字符串
     *
     * @param path
     * @param encoding
     * @return
     * @throws Exception
     */
    public static String getHtml(String path, String encoding) throws Exception {

        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(6 * 1000);
        if (conn.getResponseCode() == 200) {
            InputStream inputStream = conn.getInputStream();
            byte[] data = readStream(inputStream);
            return new String(data, encoding);
        }
        return null;
    }

    /**
     * description:通过HTTPget请求获取数据 Nov 23, 2013
     *
     * @author:sajh
     */
    public static String getDataByHttpGet(String path) throws Exception {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(path);
        HttpResponse httpResponse = httpClient.execute(httpget);
        HttpEntity ett = httpResponse.getEntity(); // 获取响应里面的内容
        String str = EntityUtils.toString(ett);
        return str;
    }

    /**
     * post提交
     * <p><b>注意：</b><br>
     * </p>
     * <p>
     * <b>变更记录：</b><br>
     * </p>
     *
     * @param path       请求路径
     * @param parameters 参数集合
     * @param encoded    编码方式，默认为UTF-8
     * @return
     * @throws Exception
     * @author: liuxuming
     * @date:2014年12月23日 下午5:25:45
     * @since 1.0.0
     */
    public static String getDataByHttpPost(String path, List<NameValuePair> parameters, String encoded) throws Exception {

        //如果不传编码方式，默认为UTF-8
        if (encoded == null || "".equals(encoded)) {
            encoded = "UTF-8";
        }
        // 1 得到浏览器
        HttpClient httpClient = new DefaultHttpClient();
        // 2 指定请求方式
        HttpPost httpPost = new HttpPost(path);
        // 3构建请求实体的数据
        String strResult = null;
        // 4 构建实体
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters,
                encoded);
        // 5 把实体数据设置到请求对象
        httpPost.setEntity(entity);
        // 6 执行请
        // 得到服务气端发回的响应的内容（都在一个字符串里面）
        HttpResponse httpResponse = httpClient.execute(httpPost);
        // 获取响应里面的内容
        HttpEntity ett = httpResponse.getEntity();
        strResult = EntityUtils.toString(ett);

        return strResult;
    }

    /**
     * post请求数据
     * <p><b>注意：</b><br>
     * </p>
     * <p>
     * <b>变更记录：</b><br>
     * </p>
     *
     * @param url  访问的url
     * @param json 数据串
     * @param json 编码规则 默认 utf-8
     * @return
     * @throws Exception
     * @author: liuxuming
     * @date:2014年12月24日 下午3:39:18
     * @since 1.0.0
     */
    public static String httpPostWithJSON(String url, String json, String encoded) throws Exception {

        if (logger.isDebugEnabled()) {
            logger.debug("请求post url:" + url + ";请求的数据是：" + json + ";编码方式：" + encoded);
        }

        //如果不传编码方式，默认为UTF-8
        if (encoded == null || "".equals(encoded)) {
            encoded = HTTP.UTF_8;
        }

        //创建浏览器
        DefaultHttpClient httpClient = new DefaultHttpClient();
        //创建post请求
        HttpPost httpPost = new HttpPost(url);
        //设定请求头
        httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
        StringEntity se = new StringEntity(json, encoded);
        se.setContentType(CONTENT_TYPE_TEXT_JSON);
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));

        //设置请求数据
        httpPost.setEntity(se);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        // 获取响应里面的内容
        HttpEntity ett = httpResponse.getEntity();
        String strResult = EntityUtils.toString(ett);

        if (logger.isDebugEnabled()) {
            logger.debug("请求返回值：" + strResult);
        }
        return strResult;
    }


    /**
     * 发送 XML 请求
     *
     * @param path
     * @param encoding
     * @return
     * @throws Exception
     */
    public static InputStream sendXmlRequest(String path,
                                             InputStream inputStream, String encoding) throws Exception {

        byte[] data = readStream(inputStream);
        URL url = new URL(path);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setConnectTimeout(6 * 1000);
        con.setDoOutput(true);// 发送 POST 请求必须允许输出
        con.setUseCaches(false);// 不设置 cache
        con.setRequestProperty("Connection", "Keep-Alive");// 维持长链接
        con.setRequestProperty("Charset", encoding);
        con.setRequestProperty("Content-Length", String.valueOf(data.length));
        con.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        // con.setRequestProperty("content-type", "text/html");
        DataOutputStream dataOutputStream = new DataOutputStream(con
                .getOutputStream());
        dataOutputStream.write(data);
        dataOutputStream.flush();
        dataOutputStream.close();
        if (con.getResponseCode() == 200) {
            return con.getInputStream();
        }
        return null;
    }

    /**
     * 发送 POST 请求
     *
     * @param path 请求路径
     * @param map  参数
     * @return 如果为空设置 GET 请求
     * @throws Exception
     */
    public static InputStream sendPostRequest(String path, Map<String, String> map) throws Exception {

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(
                    URLEncoder.encode(entry.getValue(), "UTF-8"));
            // sb.append(entry.getKey()).append("=").append(entry.getValue());
            sb.append('&');
        }
        sb.deleteCharAt(sb.length() - 1);
        if (path.indexOf("?") != -1) {
            logger.info("path:" + path + "&" + sb);
        } else {
            logger.info("path:" + path + "?" + sb);
        }
        byte[] data = sb.toString().getBytes();
        URL url = new URL(path);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setConnectTimeout(6 * 1000);
        con.setDoOutput(true);// 发送 POST 请求必须设置允许输出
        con.setUseCaches(false);// 不设置 cache
        con.setRequestProperty("Connection", "Keep-Alive");// 维持长链接
        con.setRequestProperty("Charset", "UTF-8");
        con.setRequestProperty("Content-Length", String.valueOf(data.length));
        con.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        DataOutputStream dataOutputStream = new DataOutputStream(con
                .getOutputStream());
        dataOutputStream.write(data);
        dataOutputStream.flush();
        dataOutputStream.close();
        logger.info("支付接口http请求状态：" + con.getResponseCode());
        if (con.getResponseCode() == 200) {
            return con.getInputStream();
        }

        return null;
    }

    /**
     * description:发送post请求，参数是json字符串格式的 Apr 27, 2014
     *
     * @author:sajh
     */
    public static String sendPostReqByJsonParam(String jsonStr, String url)
            throws Exception {

        try {
            // 创建连接
            URL requestUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) requestUrl
                    .openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type",
                    "application/x-www-form-urlencoded");
            conn.connect();
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            // json参数
            out.writeBytes(jsonStr);
            out.flush();
            out.close();

            // 获取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer();
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            reader.close();
            // 关闭连接
            conn.disconnect();
            return sb.toString();
        } catch (Exception e) {
            logger.error("", e);
            throw new Exception("发送post请求失败：URL=" + url + " param=" + jsonStr);
        }
    }

    /**
     * 把输入流转换成字符串
     *
     * @param inputStream
     * @param encoding
     * @return
     * @throws Exception
     */
    public static String getTextContent(InputStream inputStream, String encoding)
            throws Exception {

        byte[] data = readStream(inputStream);
        return new String(data, encoding);
    }

    /**
     * 根据路径获返回输入流
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static InputStream getContent(String path) throws Exception {

        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(6 * 1000);
        if (conn.getResponseCode() == 200) {
            return conn.getInputStream();
        }
        return null;
    }

    /**
     * 把输入流转换成字节数组
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static byte[] readStream(InputStream inputStream) throws Exception {

        ByteArrayOutputStream outputSteam = new ByteArrayOutputStream();// 往内存中写字节数据
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inputStream.read(buffer)) != -1) {
            outputSteam.write(buffer, 0, len);
        }

        inputStream.close();
        outputSteam.close();
        return outputSteam.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> map = Maps.newHashMap();
        map.put("districtIds", "1");
        System.out.println(JSON.toJSONString(map));
        System.out.print(httpPostWithJSON("http://localhost:8080/api/users", JSON.toJSONString(map), "utf-8"));
    }
}
