package psdi.iface.app.commons;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 本类实现的功能写在此处
 * =============================================================
 * Version          Author          Date            Description
 * 1.0              Neal.Y         2017/5/26             创建
 * =============================================================
 */
public class ResponseResult {

    private static final String OK = "OK";

    private static final String ERROR = "ERROR";

    @JsonProperty("META")
    private Meta meta;

    @JsonProperty("DATA")
    private Object data;

    public ResponseResult success() {

        this.meta = new Meta(true, OK);
        return this;
    }

    public ResponseResult success(Object data) {

        this.meta = new Meta(true, OK);
        this.data = data;
        return this;
    }

    public ResponseResult success(String resource, Object data) {

        this.meta = new Meta(true, 200, resource, OK);
        this.data = data;
        return this;
    }

    public ResponseResult failure() {

        this.meta = new Meta(false, ERROR);
        return this;
    }

    public ResponseResult failure(String message) {

        this.meta = new Meta(false, message);
        return this;
    }

    public ResponseResult failure(int code, String resource, String message) {

        this.meta = new Meta(false, code, resource, message);
        return this;
    }

    public Meta getMeta() {

        return meta;
    }

    public Object getData() {

        return data;
    }

    public class Meta {

        @JsonProperty("SUCCESS")
        private boolean success;

        @JsonProperty("MESSAGE")
        private String message;

        @JsonProperty("CODE")
        private int code;

        @JsonProperty("RESOURCE")
        private String resource;

        public Meta(boolean success) {

            this.success = success;
        }

        public Meta(boolean success, String message) {

            this.success = success;
            this.message = message;
        }

        public Meta(boolean success, int code, String resource, String message) {

            this.success = success;
            this.message = message;
            this.code = code;
            this.resource = resource;
        }

        public boolean isSuccess() {

            return success;
        }

        public Object getMessage() {

            return message;
        }

        public int getCode() {

            return code;
        }

        public String getResource() {

            return resource;
        }
    }
}