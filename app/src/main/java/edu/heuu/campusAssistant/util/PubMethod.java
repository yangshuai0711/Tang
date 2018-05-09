package edu.heuu.campusAssistant.util;

import java.io.File;
import java.io.FileInputStream;

import android.app.Activity;
import android.widget.Toast;

public class PubMethod 
{
    Activity activity;
	public PubMethod()
	{
	}
	public PubMethod(Activity activity)
	{
		this.activity=activity;
	}
	//获取文件信息
	public  String loadFromFile(String fileName)
	{
		String result=null;
		try
		{
			File file=new File(Constant.ADD_PRE+fileName);
			int length=(int)file.length();
    		byte[] buff=new byte[length];
    		FileInputStream fin=new FileInputStream(file);
    		fin.read(buff);
    		fin.close();
    		result=new String(buff,"UTF-8"); 
    		System.out.println("找到文件"+fileName);
    		result=result.replaceAll("\\r\\n","\n");
		}
		catch(Exception e)
		{
			System.out.println("对不起，没有找到指定文件！"+fileName);
			Toast.makeText(activity, "对不起，没有找到指定文件！", Toast.LENGTH_SHORT).show();
		}
		return result;
	}
//	//获取图片信息
//	public byte[] getBytesInfo(String fileName)
//	{
//		byte[] result=null;
//		try
//		{
//			File f=new File(Constant.ADD_PRE+fileName);
//			int length=(int)f.length();
//			byte[] buff=new byte[length];
//    		FileInputStream fin=new FileInputStream(f);
//    		fin.read(buff);
//			result=buff;
//			System.out.println("找到图片"+fileName);
//			fin.close();
//		}
//		catch(Exception e)
//		{
//			System.out.println("对不起，没有找到指定图片！"+fileName);
//			e.printStackTrace();
//		}
//		return result;
//	}
}
