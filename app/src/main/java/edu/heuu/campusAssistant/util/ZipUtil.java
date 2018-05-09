package edu.heuu.campusAssistant.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.content.Context;

public class ZipUtil {
	/** 
     * ��ѹAssets�е��ļ� 
     * @param context�����Ķ��� 
     * @param assetNameѹ�����ļ��� 
     * @param outputDirectory���Ŀ¼ 
     * @throws IOException 
     */ 
    public static void unZip(Context context, String assetName,  String outputDirectory) throws IOException 
    {  
        //������ѹĿ��Ŀ¼  
        File file = new File(outputDirectory);  
        //���Ŀ��Ŀ¼�����ڣ��򴴽�  
        if (!file.exists()) 
        {  
            file.mkdirs();  
        }  
        InputStream inputStream = null;  
        //��ѹ���ļ�  
        inputStream = context.getAssets().open(assetName);  
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);  
        //��ȡһ�������  
        ZipEntry zipEntry = zipInputStream.getNextEntry();  
        //ʹ��1Mbuffer  
        byte[] buffer = new byte[1024 * 1024];  
        //��ѹʱ�ֽڼ���  
        int count = 0;  
        //��������Ϊ��˵���Ѿ�����������ѹ�������ļ���Ŀ¼  
        while (zipEntry != null) 
        {  
            //�����һ��Ŀ¼  
            if (zipEntry.isDirectory()) 
            {  
                //String name = zipEntry.getName();  
                //name = name.substring(0, name.length() - 1);  
                file = new File(outputDirectory + File.separator + zipEntry.getName());  
                file.mkdir();  
            } else 
            {  
                //������ļ�  
                file = new File(outputDirectory + File.separator  
                        + zipEntry.getName());  
                //�������ļ�  
                file.createNewFile();  
                FileOutputStream fileOutputStream = new FileOutputStream(file);  
                while ((count = zipInputStream.read(buffer)) > 0) 
                {  
                    fileOutputStream.write(buffer, 0, count);  
                }  
                fileOutputStream.close();  
             }  
            //��λ����һ���ļ����  
            zipEntry = zipInputStream.getNextEntry();  
        }  
        zipInputStream.close();  
    } 
}
