package com.group.project.model.common;

/**
 * NBWeb自定义响应结构体
 */
public class WebResult {
    // 响应业务状态
    private boolean success;

    // 响应状态码
    private int code;//0:成功；1：失败；2：未登陆；3：再次确认
  
    // 响应的消息
    private String msg;

    // 响应的数据  
    private Object data;

    public WebResult() {

    }

    public static WebResult success(Object data) {
        return new WebResult(data);
    }  
    
    public static WebResult successMsg(String msg) {
        return new WebResult(true, 0, msg, null);
    }

    public static WebResult success() {
        return new WebResult(null);
    }
    
    public static WebResult fail(String msg) {
        return new WebResult(false, 1, msg, null);
    }

    public static WebResult fail() {
        return new WebResult(false, 1);
    }
    public static WebResult notLogin() {
        return new WebResult(false, 2, "会话已过期，请重新登陆。", null);
    }

    public static WebResult toConfirm(String msg) {
        return new WebResult(false, 3, msg, null);
    }

    public WebResult(boolean success, int code, String msg, Object data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data; 
    }  
    
    public WebResult(boolean success, int code) {
    	this.success = success;
    	this.code = code;
    }
  
    public WebResult(Object data) {
        this.success = true;
        this.code = 0;
        this.data = data;  
    }  
  
    public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {  
        return data;  
    }  
  
    public void setData(Object data) {  
        this.data = data;  
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "WebResult{" +
                "success=" + success +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
