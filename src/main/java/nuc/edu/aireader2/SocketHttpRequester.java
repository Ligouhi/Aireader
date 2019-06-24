package nuc.edu.aireader2;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class SocketHttpRequester

{

    /**

     * 发送xml数据

     * @param path 请求地址

     * @param xml xml数据

     * @param encoding 编码

     * @return

     * @throws Exception

     */

    public static byte[] postXml(String path, String xml, String encoding) throws Exception{

        byte[] data = xml.getBytes(encoding);

        URL url = new URL(path);

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        conn.setRequestMethod("POST");

        conn.setDoOutput(true);

        conn.setRequestProperty("Content-Type", "text/xml; charset="+ encoding);

        conn.setRequestProperty("Content-Length", String.valueOf(data.length));

        conn.setConnectTimeout(5 * 1000);

        OutputStream outStream = conn.getOutputStream();

        outStream.write(data);

        outStream.flush();

        outStream.close();

        if(conn.getResponseCode()==200){

            return readStream(conn.getInputStream());

        }

        return null;

    }



    /**

     * 直接通过HTTP协议提交数据到服务器,实现如下面表单提交功能:

     *   <FORM METHOD=POST ACTION="http://192.168.0.200:8080/ssi/fileload/test.do" enctype="multipart/form-data">

     <INPUT TYPE="text" NAME="name">

     <INPUT TYPE="text" NAME="id">

     <input type="file" name="imagefile"/>

     <input type="file" name="zip"/>

     </FORM>

     * @param path 上传路径(注：避免使用localhost或127.0.0.1这样的路径测试，

     *                  因为它会指向手机模拟器，你可以使用http://www.baidu.com或http://192.168.1.10:8080这样的路径测试)

     * @param params 请求参数 key为参数名,value为参数值*/

     /* @param file 上传文件*/



    public static boolean post(String path, Map<String, String> params, FormFile[] files) throws Exception

    {

        //数据分隔线

        final String BOUNDARY = "---------------------------7da2137580612";

        //数据结束标志"---------------------------7da2137580612--"

        final String endline = "--" + BOUNDARY + "--/r/n";



        //下面两个for循环都是为了得到数据长度参数，依据表单的类型而定

        //首先得到文件类型数据的总长度(包括文件分割线)

        int fileDataLength = 0;

        for(FormFile uploadFile : files)

        {

            StringBuilder fileExplain = new StringBuilder();

            fileExplain.append("--");

            fileExplain.append(BOUNDARY);

            fileExplain.append("/r/n");

            fileExplain.append("Content-Disposition: form-data;name=\""+ uploadFile.getParameterName()+"\";filename=\""+ uploadFile.getFilname() + "\"/r/n");

            fileExplain.append("Content-Type: "+ uploadFile.getContentType()+"/r/n/r/n");

            fileExplain.append("/r/n");

            fileDataLength += fileExplain.length();

            if(uploadFile.getInStream()!=null){

                fileDataLength += uploadFile.getFile().length();

            }else{

                fileDataLength += uploadFile.getData().length;

            }

        }

        //再构造文本类型参数的实体数据

        StringBuilder textEntity = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet())

        {

            textEntity.append("--");

            textEntity.append(BOUNDARY);

            textEntity.append("/r/n");

            textEntity.append("Content-Disposition: form-data; name=\""+ entry.getKey() + "\"/r/n/r/n");

            textEntity.append(entry.getValue());

            textEntity.append("/r/n");

        }



        //计算传输给服务器的实体数据总长度(文本总长度+数据总长度+分隔符)

        int dataLength = textEntity.toString().getBytes().length + fileDataLength +  endline.getBytes().length;



        URL url = new URL(path);

        //默认端口号其实可以不写

        int port = url.getPort()==-1 ? 80 : url.getPort();

        //建立一个Socket链接

        Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);

        //获得一个输出流（从Android流到web）

        OutputStream outStream = socket.getOutputStream();

        //下面完成HTTP请求头的发送

        String requestmethod = "POST "+ url.getPath()+" HTTP/1.1/r/n";

        outStream.write(requestmethod.getBytes());

        //构建accept

        String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*/r/n";

        outStream.write(accept.getBytes());

        //构建language

        String language = "Accept-Language: zh-CN/r/n";

        outStream.write(language.getBytes());

        //构建contenttype

        String contenttype = "Content-Type: multipart/form-data; boundary="+ BOUNDARY+ "/r/n";

        outStream.write(contenttype.getBytes());

        //构建contentlength

        String contentlength = "Content-Length: "+ dataLength + "/r/n";

        outStream.write(contentlength.getBytes());

        //构建alive

        String alive = "Connection: Keep-Alive/r/n";

        outStream.write(alive.getBytes());

        //构建host

        String host = "Host: "+ url.getHost() +":"+ port +"/r/n";

        outStream.write(host.getBytes());

        //写完HTTP请求头后根据HTTP协议再写一个回车换行

        outStream.write("/r/n".getBytes());

        //把所有文本类型的实体数据发送出来

        outStream.write(textEntity.toString().getBytes());



        //把所有文件类型的实体数据发送出来

        for(FormFile uploadFile : files)

        {

            StringBuilder fileEntity = new StringBuilder();

            fileEntity.append("--");

            fileEntity.append(BOUNDARY);

            fileEntity.append("/r/n");

            fileEntity.append("Content-Disposition: form-data;name=\""+ uploadFile.getParameterName()+"\";filename=\""+ uploadFile.getFilname() + "\"/r/n");

            fileEntity.append("Content-Type: "+ uploadFile.getContentType()+"/r/n/r/n");

            outStream.write(fileEntity.toString().getBytes());

            //边读边写

            if(uploadFile.getInStream()!=null)

            {

                byte[] buffer = new byte[1024];

                int len = 0;

                while((len = uploadFile.getInStream().read(buffer, 0, 1024))!=-1)

                {

                    outStream.write(buffer, 0, len);

                }

                uploadFile.getInStream().close();

            }

            else

            {

                outStream.write(uploadFile.getData(), 0, uploadFile.getData().length);

            }

            outStream.write("/r/n".getBytes());

        }

        //下面发送数据结束标志，表示数据已经结束

        outStream.write(endline.getBytes());

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //读取web服务器返回的数据，判断请求码是否为200，如果不是200，代表请求失败

        if(reader.readLine().indexOf("200")==-1)

        {

            return false;

        }

        outStream.flush();

        outStream.close();

        reader.close();

        socket.close();

        return true;

    }



    /**

     * 提交数据到服务器

     * @param path 上传路径(注：避免使用localhost或127.0.0.1这样的路径测试，因为它会指向手机模拟器，你可以使用http://www.baidu.com或http://192.168.1.10:8080这样的路径测试)

     * @param params 请求参数 key为参数名,value为参数值

     * @param file 上传文件

     */

    public static boolean post(String path, Map<String, String> params, FormFile file) throws Exception

    {

        return post(path, params, new FormFile[]{file});

    }

    /**

     * 提交数据到服务器

     * @param path 上传路径(注：避免使用localhost或127.0.0.1这样的路径测试，因为它会指向手机模拟器，你可以使用http://www.baidu.com或http://192.168.1.10:8080这样的路径测试)

     * @param params 请求参数 key为参数名,value为参数值

     * @param encode 编码

     */

    public static byte[] postFromHttpClient(String path, Map<String, String> params, String encode) throws Exception

    {

        //用于存放请求参数

        List<NameValuePair> formparams = new ArrayList<NameValuePair>();

        for(Map.Entry<String, String> entry : params.entrySet())

        {

            formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));

        }

        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, encode);

        HttpPost httppost = new HttpPost(path);

        httppost.setEntity(entity);

        //看作是浏览器

        HttpClient httpclient = new DefaultHttpClient();

        //发送post请求

        HttpResponse response = httpclient.execute(httppost);

        return readStream(response.getEntity().getContent());

    }

    /**

     * 发送请求

     * @param path 请求路径

     * @param params 请求参数 key为参数名称 value为参数值

     * @param encode 请求参数的编码

     */

    public static byte[] post(String path, Map<String, String> params, String encode) throws Exception

    {

        //String params = "method=save&name="+ URLEncoder.encode("老毕", "UTF-8")+ "&age=28&";//需要发送的参数

        StringBuilder parambuilder = new StringBuilder("");

        if(params!=null && !params.isEmpty())

        {

            for(Map.Entry<String, String> entry : params.entrySet())

            {

                parambuilder.append(entry.getKey()).append("=")

                        .append(URLEncoder.encode(entry.getValue(), encode)).append("&");

            }

            parambuilder.deleteCharAt(parambuilder.length()-1);

        }

        byte[] data = parambuilder.toString().getBytes();

        URL url = new URL(path);

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        //设置允许对外发送请求参数

        conn.setDoOutput(true);

        //设置不进行缓存

        conn.setUseCaches(false);

        conn.setConnectTimeout(5 * 1000);

        conn.setRequestMethod("POST");

        //下面设置http请求头

        conn.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");

        conn.setRequestProperty("Accept-Language", "zh-CN");

        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");

        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        conn.setRequestProperty("Content-Length", String.valueOf(data.length));

        conn.setRequestProperty("Connection", "Keep-Alive");



        //发送参数

        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());

        outStream.write(data);//把参数发送出去

        outStream.flush();

        outStream.close();

        if(conn.getResponseCode()==200)

        {

            return readStream(conn.getInputStream());

        }

        return null;

    }



    /**

     * 读取流

     * @param inStream

     * @return 字节数组

     * @throws Exception

     */

    public static byte[] readStream(InputStream inStream) throws Exception

    {

        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];

        int len = -1;

        while( (len=inStream.read(buffer)) != -1)

        {

            outSteam.write(buffer, 0, len);

        }

        outSteam.close();

        inStream.close();

        return outSteam.toByteArray();

    }

}







