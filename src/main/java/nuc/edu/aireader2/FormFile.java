package nuc.edu.aireader2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**

 * 使用JavaBean封装上传文件数据

 *

 */

public class FormFile

{

    //上传文件的数据

    private byte[] data;

    private InputStream inStream;

    private File file;

    //文件名称

    private String filname;

    //请求参数名称

    private String parameterName;

    //内容类型

    private String contentType = "application/octet-stream";



    /**

     * 上传小文件，把文件数据先读入内存

     * @param filname

     * @param data

     * @param parameterName

     * @param contentType

     */

    public FormFile(String filname, byte[] data, String parameterName, String contentType)

    {

        this.data = data;

        this.filname = filname;

        this.parameterName = parameterName;

        if(contentType!=null) this.contentType = contentType;

    }



    /**

     * 上传大文件，一边读文件数据一边上传

     * @param filname

     * @param file

     * @param parameterName

     * @param contentType

     */

    public FormFile(String filname, File file, String parameterName, String contentType)

    {

        this.filname = filname;

        this.parameterName = parameterName;

        this.file = file;

        try

        {

            this.inStream = new FileInputStream(file.getAbsolutePath());

        }

        catch (FileNotFoundException e)

        {

            e.printStackTrace();

        }

        if(contentType!=null) this.contentType = contentType;

    }



    public File getFile()

    {

        return file;

    }

    public InputStream getInStream()

    {

        return inStream;

    }

    public byte[] getData()

    {

        return data;

    }

    public String getFilname()

    {

        return filname;

    }

    public void setFilname(String filname)

    {

        this.filname = filname;

    }

    public String getParameterName()

    {

        return parameterName;

    }

    public void setParameterName(String parameterName)

    {

        this.parameterName = parameterName;

    }

    public String getContentType()

    {

        return contentType;

    }

    public void setContentType(String contentType)

    {

        this.contentType = contentType;

    }

}