package com.ibm.tivoli.maximo.rest;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 修改Maximo原生ResourceResponse，用于实现封装复杂的数据对象。
 * 1.添加restData字段
 * =============================================================
 * Version          Author          Date            Description
 * 1.0              Neal.Y         2017/5/26             创建
 * =============================================================
 */
public class ResourceResponse implements Serializable {

    protected Map<String, String> customHeaders = null;
    private byte[] data;
    private String format;
    private String mimeType;
    private String resourceType;
    private String resourceName;
    private Date lastModified = null;
    private String etag = null;
    private boolean modified = true;
    private int maxAge = 0;
    private String resourceId = null;
    private String resourceLocatorId = null;
    private Object restData;

    public ResourceResponse(Object restData, String format, String mimeType, String resourceType, String resourceName) {

        this.lastModified = null;
        this.etag = null;
        this.modified = true;
        this.maxAge = 0;
        this.restData = restData;
        this.format = format;
        this.mimeType = mimeType;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
    }

    public ResourceResponse(String resourceId, String format, String mimeType, String resourceType, String resourceName) {
        this.resourceId = resourceId;
        this.format = format;
        this.mimeType = mimeType;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
    }

    public ResourceResponse(byte[] data, String format, String mimeType, String resourceType, String resourceName) {
        this.data = data;
        this.format = format;
        this.mimeType = mimeType;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
    }

    public ResourceResponse(byte[] data, String format, String mimeType, String resourceType, String resourceName, Date lastModified, boolean modified, int maxAge, String etag, String resourceLocatorId, Map<String, String> customHeaders) {
        this(data, format, mimeType, resourceType, resourceName);
        this.lastModified = lastModified;
        this.modified = modified;
        this.maxAge = maxAge;
        this.etag = etag;
        this.resourceLocatorId = resourceLocatorId;
        this.customHeaders = customHeaders;
    }

    public String getResourceLocatorId() {
        return this.resourceLocatorId;
    }

    public void setResourceLocatorId(String resourceLocatorId) {
        this.resourceLocatorId = resourceLocatorId;
    }

    public Map<String, String> getCustomHeaders() {
        return this.customHeaders;
    }

    public int getMaxAge() {
        return this.maxAge;
    }

    public String getResourceId() {
        return this.resourceId;
    }

    public String getETag() {
        return this.etag;
    }

    public boolean isModified() {
        return this.modified;
    }

    public Date getLastModified() {
        return this.lastModified;
    }

    public byte[] getData() {
        return this.data;
    }

    public String getFormat() {
        return this.format;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public String getResourceType() {
        return this.resourceType;
    }

    public String getResourceName() {
        return this.resourceName;
    }

    public Object getRestData() {

        return restData;
    }
}
