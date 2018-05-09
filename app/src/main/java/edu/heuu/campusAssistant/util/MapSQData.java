package edu.heuu.campusAssistant.util;

public class MapSQData
{
	PubMethod pub=new PubMethod();
	public static String[] buildingName;
	public static int[][] buildingBorder;
	public static int[][] buildingBallon;
	public static int[][][] AABB;
	public MapSQData()
	{
		//各个建筑物的名称
		String ss1=pub.loadFromFile("map/schoolmap.txt");
		buildingName=ss1.split("，");
		
		//各个建筑物的包围盒组
		String ss2=pub.loadFromFile("map/schoolab.txt");
		String[] st1=ss2.split("/");
		AABB=new int[st1.length][][];
		for(int i=0;i<st1.length;i++)
		{
			String[] st2=st1[i].split(";");
			AABB[i]=new int[st2.length][];
			for(int j=0;j<st2.length;j++)
			{
				String[] st3=st2[j].split(",");
				AABB[i][j]=new int[st3.length-1];
				for(int t=0;t<st3.length-1;t++)
				{
					AABB[i][j][t]=Integer.parseInt(st3[t+1].trim());
				}
			}
		}
		
		//各个建筑物的边框
		String ss3=pub.loadFromFile("map/schoolbk.txt");
		String[] str=ss3.split("/");
		buildingBorder=new int[str.length][];
		for(int i=0;i<str.length;i++)
		{
			String[] sst=str[i].split(",");
			buildingBorder[i]=new int[sst.length-1];
			for(int j=0;j<sst.length-1;j++)
			{
				buildingBorder[i][j]=Integer.parseInt(sst[j+1].trim());
			}
		}
		
		//各个建筑物的气球点
		String ss4=pub.loadFromFile("map/schoolzx.txt");
		String[] srr=ss4.split("/");
		buildingBallon=new int[srr.length][];
		for(int i=0;i<srr.length;i++)
		{
			String[] sst=srr[i].split(",");
			buildingBallon[i]=new int[sst.length-1];
			for(int j=0;j<sst.length-1;j++)
			{
				buildingBallon[i][j]=Integer.parseInt(sst[j+1].trim());
			}
		}
	}
}