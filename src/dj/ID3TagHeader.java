package dj;

import djExceptions.*;
import java.io.RandomAccessFile;

public class ID3TagHeader {

    private byte body[];
    private RandomAccessFile raf;
    public static final int 
    						TagHeaderSize = 10,
    						UnsynchronisationFlag = 7,
    						ExtendedHeaderFlag = 6,
    						ExperimentalIndicatorHeader = 5,
    						FooterPresentFlag = 4;

    public ID3TagHeader( RandomAccessFile r )throws ID3Exception
    {
    	raf = r;
    	body = new byte[TagHeaderSize];
    	
        try {
			raf.readFully( body );
		}
        catch (Exception e){
			throw new ID3FileException("Internal error occurred while reading TagHeader");
		}
    }

    public boolean isValid()
    {
    	return (body[0] == 'I' && body[1] == 'D' && body[2] == '3');
    }

    public int size()
    {
        return ID3Bitwise.compileIntFromSyncsafe(body[6], body[7], body[8], body[9]);
    }

    public byte version()
    {
        return body[3];
    }

    public byte revision()
    {
    	//Obsolete, always 0
        return body[4];
    }

    public boolean flagIsSet(int flag)
    {
    	return (body[5] & ( 1 << flag)) > 0;
    }
    
    public byte[] getRawData()
	{
		return body;
	}
}