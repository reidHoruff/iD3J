package dj;

import java.io.RandomAccessFile;
import djExceptions.ID3Exception;
import djExceptions.ID3FileException;

/*
* not really doing anything with the extended header except for
* getting size so i can move on the the valid frames
* very easily to implement the extra data like flags but im not going to :P
*/

public class ID3ExtendedHeader {
	
	private int size;
	private byte[] body;
	private static final int DefaultExtendedHeaderSize = 6;
	private RandomAccessFile ras;
	
	public ID3ExtendedHeader( RandomAccessFile r ) throws ID3Exception
	{
		ras = r;
		body = new byte[DefaultExtendedHeaderSize];
		
		try{
			ras.readFully(body); //read min extended header size;
			size = ID3Bitwise.compileIntFromSyncsafe(body[0], body[1], body[2], body[3]); //just so i can get the size
			ras.seek( (long)ID3TagHeader.TagHeaderSize ); //go back to the beginning
			body = new byte[size];
			ras.readFully(body); //and now read the whole thing			
		} 
		catch (Exception e){
			throw new ID3FileException("internal error occurred while reading ExtendedHeader");
		}
	}
	
	public int size()
	{
		return size;
	}
	
	public byte[] getRawData()
	{
		return body;
	}
}
