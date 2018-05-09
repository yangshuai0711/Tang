package edu.heuu.campusAssistant.util;

public class MapSQUtil
{
	static MapSQData msd=new MapSQData();
	//获得建筑物的id号
	public static int getSelectBuildingID(float tx,float ty)
	{
		for(int i=0;i<MapSQData.AABB.length;i++)
		{
			int[][] aabbs=MapSQData.AABB[i];
			for(int[] aabb:aabbs)
			{
				if(tx>aabb[0]&&tx<aabb[2]&&ty>aabb[1]&&ty<aabb[3])
				{
					return i;
				}
			}
		}
		return -1;
	}
	
	@SuppressWarnings("static-access")
	public static void main(String args[])
	{
		int bid=getSelectBuildingID(947,309);
		System.out.println(msd.buildingName[bid]);
	}
}