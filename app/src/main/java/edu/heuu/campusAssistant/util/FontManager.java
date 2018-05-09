package edu.heuu.campusAssistant.util;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FontManager
{	
	public static Typeface tf =null;
	
	//��ʼ��Typeface
	public static void init(Activity act)
	{
		if(tf==null)
		{
		    tf= Typeface.createFromAsset(act.getAssets(),  "fonts/newfont.ttf");  
		}
	}
	//ת������
	public static void changeFonts(ViewGroup root,Activity act) 
	{ 
        for (int i = 0; i < root.getChildCount(); i++) 
        { 
            View v = root.getChildAt(i);  
            if (v instanceof TextView) 
            { 
                ((TextView) v).setTypeface(tf);  
            } 
            else if (v instanceof Button) 
            {  
                ((Button) v).setTypeface(tf);  
            } 
            else if (v instanceof EditText) 
            {  
                ((EditText) v).setTypeface(tf);  
            } 
            else if (v instanceof ViewGroup) 
            {  
                changeFonts((ViewGroup) v, act);  
            }  
       }  
	}  
	public static ViewGroup getContentView(Activity act)
	{ 
		ViewGroup systemContent = (ViewGroup)act.getWindow().getDecorView().findViewById(android.R.id.content); 
		ViewGroup content = null; 
		if(systemContent.getChildCount() > 0 && systemContent.getChildAt(0) instanceof ViewGroup)
		{ 
			content = (ViewGroup)systemContent.getChildAt(0); 
		} 
		return content; 
	}
}