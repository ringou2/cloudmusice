package com.ringou.cloudemusic;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Change{
	private static final String targetDir = "E:\\mp3\\CloudMusic\\";
	private static final 	String cacheDir = "E:\\CloudMusic\\cache\\Cache";
	public static void main(String[] args){
		Change  change=new Change();
	
		List<File> list = change.listFile(cacheDir);
		for (File file : list) {
			change.changeFile(file);
		}
	}
	public List<File> listFile(String dir) {
		List <File> list=new ArrayList<File>();
		File file=new File(dir);
		File[] files = file.listFiles();
		for (File file2 : files) {
			if(file2.getAbsolutePath().endsWith(".uc") && file2.length()>5000) {
				list.add(file2);
			}
		}
		return list;
	}
	public void changeFile(File inFile) {
//		File inFile = new File(fileName);
		long time = inFile.lastModified();
		Date date=new Date(time);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
		String targetName=sdf.format(date);
		File outFile = new File(targetDir+targetName
				+ ".mp3");
		System.out.println(inFile.getAbsolutePath()+"  out put="+outFile.getAbsolutePath());
		try {
			DataInputStream dis = new DataInputStream( new FileInputStream(inFile));
			DataOutputStream dos = new DataOutputStream( new FileOutputStream(outFile));
			byte[] by = new byte[1000];
			int len;
			while((len=dis.read(by))!=-1){
				for(int i=0;i<len;i++){
					by[i]^=0xa3;
				}
				dos.write(by,0,len);
			}
			dis.close();
			dos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
