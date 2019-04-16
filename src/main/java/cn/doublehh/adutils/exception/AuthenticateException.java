package cn.doublehh.adutils.exception;

/**
 * @author 胡昊
 * Description:
 * Date: 2019/4/16
 * Time: 23:03
 * Create: DoubleH
 */
public class AuthenticateException extends RuntimeException {
    public AuthenticateException() {
    }

    public AuthenticateException(String message) {
        super(message);
    }
}
