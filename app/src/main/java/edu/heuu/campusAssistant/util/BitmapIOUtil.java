package edu.heuu.campusAssistant.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapIOUtil //图片获取类
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
			System.out.println("出现异常！！");
		}
		return bp;
	}
}
