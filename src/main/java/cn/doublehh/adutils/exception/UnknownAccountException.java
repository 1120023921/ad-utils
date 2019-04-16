package cn.doublehh.adutils.exception;

/**
 * @author 胡昊
 * Description:
 * Date: 2019/4/16
 * Time: 22:45
 * Create: DoubleH
 */
public class UnknownAccountException extends RuntimeException {
    public UnknownAccountException() {
    }

    public UnknownAccountException(String message) {
        super(message);
    }
}
