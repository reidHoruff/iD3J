package iD3J.Demo;


import iD3J.*;
import iD3J.Exceptions.ID3Exception;
import iD3J.Exceptions.ID3TagNotFoundException;
import iD3J.Exceptions.UnsupportedID3TagVersionException;

import java.io.*;

public class example {
	
	static int foo = 0;

	public static void main(String[] args) throws IOException {
		list(new File("/media/Shared/Music/"));
		//getInfo(new File("/media/Shared/Music/good music/lib10/Spor Discography/Silver Spaceman _ Some Other Funk [12_ V/A1 Silver Spaceman.mp3"));
	}
	
	public static void writeAlbumArt( ID3TagBody tagBody )
	{		
		ID3Frame picFrame = tagBody.getFrame("APIC");
		
		if( picFrame == null ){
			System.out.println("APIC frame not found");
			return;
		}
		
		byte body[] = picFrame.getRawData();
		int position = 0;
		
		byte textEncoding, pictureType;
		StringBuffer MIMEtype = new StringBuffer();
		StringBuffer description = new StringBuffer();
		
		textEncoding = body[position];
		position++;
		
		while( body[position] != 0 ){
			MIMEtype.append( (char)body[position] );
			position++;
		}
		
		position++;
		
		pictureType = body[position];
		
		boolean ran = false;
		
		while( body[position] != 0 ){
			description.append( (char)body[position] );
			position++;
			ran = true;
		}
		
		if (!ran){
			position++;
		}
		
		position++;
		
		try{
			FileOutputStream fos = new FileOutputStream("/home/reid/foo/" + foo + MIMEtype.toString().replaceAll("/", "."));
			DataOutputStream dos = new DataOutputStream(fos);
			fos.write(body, position, (body.length-position));
			fos.close();
			foo++;
		}
		catch(IOException e){
			System.out.println("Error writing image to file");
		}
	}
	
	public static void printInfo( ID3TagBody tagBody )
	{
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
	}
	
	public static void getInfo( File file ){
		
		ID3TagBody tagBody = new ID3TagBody(file);

		try{
			tagBody.parse(); 
		}
		catch(ID3TagNotFoundException e){
			System.out.println("ID3 tag was not found");
			return;
		}
		catch(UnsupportedID3TagVersionException e){
			System.out.println("ID3 tag version not supported");
			return;
		}
		
		printInfo( tagBody );
		writeAlbumArt( tagBody );
		
		try{
			tagBody.close();
		}
		catch(ID3Exception e){
			e.printStackTrace();
		}
	}
	
	/* depth first file searching */
	public static void list( File f ) throws IOException
	{		
		if(f.isFile() && f.getName().endsWith(".mp3")){
			getInfo(f);
		}
		
		else if(f.isDirectory()){
			for(File cur: f.listFiles()){
				list(cur);
			}		
		}
	}
}
