package dj;

public class ID3Bitwise
{
  public static int compileIntFromSyncsafe(byte a, byte b, byte c, byte d)
  {
	  int size = 0;
	    
	  	size |= (a & 0xFF) << 21;
	  	size |= (b & 0xFF) << 14;
	  	size |= (c & 0xFF) << 7;
	  	size |= (d & 0xFF);

	    return size;
  }
  
  public static int compileIntFromUnsyncsafe(byte a, byte b, byte c, byte d)
  {
	  int size = 0;
		
	  size |= (a & 0xFF) << 24;
	  size |= (b & 0xFF) << 16;
	  size |= (c & 0xFF) << 8;
	  size |= d & 0xFF;
		
	  return size;
  }
  
  public static byte[] expandToSyncsafeInt(int i)
  {
	  byte b[] = new byte[4];
	  
	  b[0] = (byte) ((i >> 21) & 0x7F);
	  b[1] = (byte) ((i >> 14) & 0x7F);
	  b[2] = (byte) ((i >> 7 ) & 0x7F);
	  b[3] = (byte) (i & 0x7F);
	  
	  return b;
  }
}