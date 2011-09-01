package djExceptions;

@SuppressWarnings("serial")
public class ID3FileException extends ID3Exception{
	
	public ID3FileException(String m)
	{
		super(m);
	}
	
	public ID3FileException()
	{
		super();
	}	
}
