package djExceptions;

@SuppressWarnings("serial")
public class UnsupportedID3TagVersionException extends ID3Exception{
	
	public UnsupportedID3TagVersionException(String m)
	{
		super(m);
	}
	
	public UnsupportedID3TagVersionException()
	{
		super();
	}
}
