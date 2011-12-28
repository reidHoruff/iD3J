package iD3J.Exceptions;

@SuppressWarnings("serial")
public class ID3TagNotFoundException extends ID3Exception{
	
	public ID3TagNotFoundException(String m)
	{
		super( m );
	}
	
	public ID3TagNotFoundException()
	{
		super();
	}	
}
