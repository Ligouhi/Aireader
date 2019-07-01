package nuc.edu.aireader2;

import java.io.File;
import java.io.IOException;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.entity.mime.content.FileBody;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.util.EntityUtils;

public class UpLoadPhotos   {

    public static String init(String path){
        String serverURL = "http://10.0.2.2:8000/uploadFile/";
//        String path = "/storage/emulated/0/阅图/PictureUnlock_hknew_78340.pictureunlock.jpg";
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/KK.jpeg";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();





            FileBody bin = new FileBody(new File(path));
            multipartEntityBuilder.addPart("sourcefiles", bin);


        HttpPost httppost = new HttpPost(serverURL);
        // 默认的的ContentType是application/octet-stream
// FileBody bin = new FileBody(new File(path), ContentType.create("image/png"));

        // 其他文本类型的参数
        HttpEntity reqEntity = multipartEntityBuilder.build();

        httppost.setEntity(reqEntity);

        try {
            CloseableHttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {

                System.out.println("服务器正常返回的数据: " + EntityUtils.toString(resEntity));// httpclient自带的工具类读取返回数据

                System.out.println(resEntity.getContent());
                return resEntity.toString();

            } else if (statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {

                System.out.println("上传文件发生异常，请检查服务端异常问题");
            }

            EntityUtils.consume(resEntity);
            response.close();
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverURL;
    }}

