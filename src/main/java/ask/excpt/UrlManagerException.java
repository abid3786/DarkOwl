package ask.excpt;

/**
 * Generic exception, just to be able to throw with acceptable/
 * understandable message.
 *
 * @author Abid Khan
 */
public class UrlManagerException extends RuntimeException  {
    public UrlManagerException(String msg) {
        super(msg);
    }

    public UrlManagerException(String msg, Throwable c) {
        super(msg, c);
    }

}
