package iD3J;

import iD3J.Exceptions.*;

import java.io.*;
import java.util.Hashtable;


public class ID3TagBody {
	
	private ID3TagHeader tagHeader;
	private ID3ExtendedHeader extendedHeader = null;
	private Hashtable<String, ID3Frame> frames;
	private RandomAccessFile ras;
	public static final int MIN_SUPPORTED_VERSION = 3;
	
	public ID3TagBody(File file) throws ID3Exception
	{
		frames = new Hashtable<String, ID3Frame>();
		
		try {
			ras = new RandomAccessFile(file, "rw");
		} 
		catch (Exception e){
			throw new ID3FileException("error opening file: " + file.getAbsolutePath());
		}		
	}
	
	public void close() throws ID3Exception
	{
		try{
			ras.close();
		}
		catch (Exception e){
			throw new ID3FileException("error while closing file");
		}
	}
	
	public void parse() throws ID3Exception
	{		
		tagHeader = new ID3TagHeader( ras );
		
		if( !tagHeader.isValid() ){
			throw new ID3TagNotFoundException();
		}
		
		if( tagHeader.version() < MIN_SUPPORTED_VERSION){
			throw new UnsupportedID3TagVersionException();
		}
		
		if( tagHeader.flagIsSet( ID3TagHeader.ExtendedHeaderFlag ) ){
			extendedHeader = new ID3ExtendedHeader( ras );
		}
		
		ID3FrameHeader currentFrameHeader;
		ID3Frame currentID3Frame;
		
		while(true)
		{
			currentFrameHeader = new ID3FrameHeader( ras );
			
			if(!currentFrameHeader.isValid()){
				break;
			}
			
			currentID3Frame = new ID3Frame( currentFrameHeader, ras );
			frames.put(currentFrameHeader.getFrameID(), currentID3Frame);
		}
	}
	
	public ID3TagHeader getHeader()
	{
		return tagHeader;
	}
	
	public ID3ExtendedHeader getExtendedHeader()
	{
		return extendedHeader;
	}
	
	public ID3Frame getFrame(String id)
	{
		return frames.get(id);
	}
}
