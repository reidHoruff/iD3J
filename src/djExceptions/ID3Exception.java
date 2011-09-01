package djExceptions;

@SuppressWarnings("serial")
public class ID3Exception extends RuntimeException{
	
	public ID3Exception(String m)
	{
		super(m);
	}
	
	public ID3Exception()
	{
		super();
	}
}
