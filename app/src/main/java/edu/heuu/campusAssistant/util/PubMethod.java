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
	//��ȡ�ļ���Ϣ
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
    		System.out.println("�ҵ��ļ�"+fileName);
    		result=result.replaceAll("\\r\\n","\n");
		}
		catch(Exception e)
		{
			System.out.println("�Բ���û���ҵ�ָ���ļ���"+fileName);
			Toast.makeText(activity, "�Բ���û���ҵ�ָ���ļ���", Toast.LENGTH_SHORT).show();
		}
		return result;
	}
//	//��ȡͼƬ��Ϣ
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
//			System.out.println("�ҵ�ͼƬ"+fileName);
//			fin.close();
//		}
//		catch(Exception e)
//		{
//			System.out.println("�Բ���û���ҵ�ָ��ͼƬ��"+fileName);
//			e.printStackTrace();
//		}
//		return result;
//	}
}
