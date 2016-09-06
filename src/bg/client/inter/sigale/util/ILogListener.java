package bg.client.inter.sigale.util;


public interface ILogListener {

	public void log(String message) ;

	public  void log(String message, Throwable t) ;

	public void logText(String message);

	public void logTitle(String s);
}
