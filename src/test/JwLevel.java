package test;

import java.util.ArrayList;

public class JwLevel {

	public static final int MAXX = 256; 
	public static final int MAXY = 256; 
	protected String [][] map = new String [MAXX][MAXY];
	
	public void addWall(String wallDef, int x,int y){

		map[x][y] =  wallDef;
	}
	
	public String getWall(int x,int y){
		return map[x][y] ;
	}
}
