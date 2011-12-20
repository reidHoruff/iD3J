package djDemo;

import dj.*;
import djExceptions.ID3Exception;
import djExceptions.ID3TagNotFoundException;
import djExceptions.UnsupportedID3TagVersionException;

import java.io.*;

public class example {

	public static void main(String[] args) throws FileNotFoundException {
		list(new File("/media/Shared/Music/"));
	}
	
	public static void printInfo(File file)
	{
		ID3TagBody tagBody = new ID3TagBody(file);
		
		try{
			tagBody.parse(); 
		}catch(ID3TagNotFoundException e){
			System.out.println("ID3 tag was not found");
			return;
		}
		catch(UnsupportedID3TagVersionException e){
			System.out.println("ID3 tag version not supported");
			return;
		}
		
		ID3Frame titleFrame = tagBody.getFrame("TIT2");
		ID3Frame albumFrame = tagBody.getFrame("TALB");
		//returns null if not found
		
		String songTitle = "<unknown>";
		String songAlbum = "<unknown>";
		
		if(titleFrame != null){
			songTitle = titleFrame.getText();
		}
		
		if(albumFrame != null){
			songAlbum = albumFrame.getText();
		}
		
		System.out.println( songTitle + " -on- " + songAlbum );
		
		try{
			tagBody.close();
		}catch(ID3Exception e){
			e.printStackTrace();
		}
	}
	
	/* depth first file searching */
	public static void list( File f ) throws FileNotFoundException
	{		
		if(f.isFile() && f.getName().endsWith(".mp3")){
			printInfo(f);
		}
		
		else if(f.isDirectory()){
			for(File cur: f.listFiles()){
				list(cur);
			}		
		}
	}
}
