package com.jotunheim.thor.web;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
 * @author : zhangle
 * @version : 10/20/16.
 */

public class AjaxResult implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int SUCCESS = 200;
    public static final int INPUT_ERROR = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int CLIENT_SIGN_ERROR = 405;
    public static final int REQUEST_EXPIRED = 408;
    public static final int SYSTEM_ERROR = 500;

    public static final AjaxResult OK = new AjaxResult(SUCCESS);

    private int code;
    private String message;
    private Object data;

    public AjaxResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public AjaxResult(int code) {
        this(code, "success", null);
    }

    public AjaxResult(int code, String message) {
        this(code, message, null);
    }

    public AjaxResult(Object data) {
        this(SUCCESS, "success", data);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    
    public static void main(String[] args) {
        JSONObject json = JSONObject.fromObject(AjaxResult.OK);
        System.out.println(json);
        System.out.println(System.currentTimeMillis());
    }
}
