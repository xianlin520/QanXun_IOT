package vip.xianlin.exception;

// 定义系统异常类
public class SystemException extends RuntimeException {
    // 定义系统异常码
    private Integer code;
    
    public SystemException(Integer code, String message) {
        super(message);
        this.code = code;
    }
    
    public SystemException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
    
    public Integer getCode() {
        return code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }
}
