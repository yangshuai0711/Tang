package edu.heuu.campusAssistant.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapIOUtil //ͼƬ��ȡ��
{
	static PubMethod pub=new  PubMethod();
	static Bitmap bp=null;
	public  static Bitmap  getSBitmap(String subPath)
	{
		try
		{
			String path=Constant.ADD_PRE+subPath;
			bp = BitmapFactory.decodeFile(path);
		}
		catch(Exception e)
		{
			System.out.println("�����쳣����");
		}
		return bp;
	}
}
