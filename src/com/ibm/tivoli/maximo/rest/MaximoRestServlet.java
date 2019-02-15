package com.ibm.tivoli.maximo.rest;

import com.ibm.tivoli.maximo.log.MaxLoggerContext;
import org.apache.commons.io.IOUtils;
import psdi.iface.app.commons.ResponseResult;
import psdi.iface.app.util.JsonUtil;
import psdi.iface.util.XMLUtils;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.util.MXSession;
import psdi.util.MXSystemException;
import psdi.util.MXUnknownObjectException;
import psdi.util.logging.MXCorrelator;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;
import psdi.webclient.system.websession.TokenWebAppSession;
import psdi.webclient.system.websession.TokenWebAppSessionFactory;
import psdi.webclient.system.websession.WebAppEnv;
import psdi.webclient.system.websession.WebAppSessionProvider;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;

/**
 * 本类实现的功能写在此处
 * =============================================================
 * Version          Author          Date            Description
 * 1.0              Neal.Y         2017/5/19             创建
 * =============================================================
 */
public class MaximoRestServlet extends HttpServlet {

    public static final String CHARACTER_ENCODING = "UTF-8";

    public static final String REST_CONTEXT = "rest";

    public static final String CUST_MAX_POST_DATA_KEY = "CUST_MAX_POST_DATA";

    public static final String JSON_FORMAT = "json";

    public static final String MIME_TYPE = "application/json";

    private static MXLogger logger = MXLoggerFactory.getLogger("maximo.rest");

    private Map<String, String> resourceHandlers = new HashMap();

    private Map<String, String> formatMimeTypeMap = new HashMap();

    private Map<String, Set<String>> blockAccessMap = new HashMap();

    private String requestEncoding = null;

    public MaximoRestServlet() {

    }

    @Override
    public void init(ServletConfig sc) throws ServletException {

        try {
            super.init(sc);
            this.requestEncoding = this.getInitParameter("char_encoding");
            Set e = RESTUtil.getPropertyValues("mxe.rest.supportedformats", ",", true);
            Iterator i$ = e.iterator();

            while (true) {
                while (i$.hasNext()) {
                    String format = (String) i$.next();
                    Set mimeTypes = RESTUtil.getPropertyValues("mxe.rest.format." + format + ".mimetypes", ",", true);
                    if (mimeTypes != null) {
                        Iterator i$1 = mimeTypes.iterator();

                        while (i$1.hasNext()) {
                            String mimeType = (String) i$1.next();
                            this.formatMimeTypeMap.put(mimeType.toLowerCase(), format.toLowerCase());
                        }
                    } else {
                        // logger.info("No mime types registered for format " + format);
                    }
                }

                return;
            }
        } catch (Exception var8) {
            throw new ServletException(var8);
        }
    }

    protected void loadBlockAccessList(String handlerName) throws RemoteException, MXException {

        String blockAccessPropName = "mxe.rest." + handlerName + ".blockaccess";
        Set resourceBlockSet = RESTUtil.getPropertyValues(blockAccessPropName, ",", false);
        if (resourceBlockSet != null) {
            this.blockAccessMap.put(handlerName.toLowerCase(), resourceBlockSet);
        }

    }

    protected synchronized ResourceRequestHandler createResourceHandler(String name) {

        try {
            String e = this.resourceHandlers.get(name);
            if (e == null) {
                e = MXServer.getMXServer().getProperty("mxe.rest.handler." + name);
                if (e == null) {
                    String[] clazz1 = new String[]{name};
                    throw new MXApplicationException("rest", "nohandler", clazz1);
                }

                this.resourceHandlers.put(name, e);
                this.loadBlockAccessList(name);
            }

            Class clazz = Class.forName(e);
            ResourceRequestHandler handler = (ResourceRequestHandler) clazz.newInstance();
            handler.setBlockAccessList(this.blockAccessMap.get(name));
            return handler;
        } catch (Exception var5) {
            logger.error(var5.getMessage(), var5);
            return null;
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        try

        {
            if (this.requestEncoding != null) {
                request.setCharacterEncoding(this.requestEncoding);
            }
            boolean enableCorrelator = "1".equals(MXServer.getMXServer().getProperty("mxe.logging.CorrelationEnabled"));
            MXCorrelator correlator = null;
            if (enableCorrelator) {
                correlator = MXCorrelator.startCorrelation("MXREST");
            }

            String contentType = request.getContentType();
            String requestURI = request.getRequestURI();

            String resourceReq = requestURI;
            String contextPath = request.getContextPath();

            if ((contextPath != null) && (!contextPath.equals(""))) {
                resourceReq = requestURI.substring(contextPath.length());
            }
            logger.debug(new StringBuilder().append("resourceReq=").append(resourceReq).toString());

            logger.debug(new StringBuilder().append("Content-Type:").append(contentType).toString());
            logger.debug(new StringBuilder().append("RequestURI:").append(requestURI).toString());

            Enumeration headerNamesEnum = request.getHeaderNames();
            Map headers = new HashMap();
            Map map = request.getParameterMap();
            StringBuffer querURL = request.getRequestURL();
            if (!map.isEmpty()) {
                //遍历map中的键
                querURL.append("?");
                for (String key : ((Map<String, String[]>) map).keySet()) {
                    querURL.append(key);
                    querURL.append("=");
                    querURL.append(((Map<String, String[]>) map).get(key)[0]);
                    querURL.append("&");
                }
            }
            //记录日志URL
            logger.info("请求URL:" + querURL.deleteCharAt(querURL.length() - 1));
            Date ifModifiedSince = null;
            while (headerNamesEnum.hasMoreElements()) {
                String headerName = (String) headerNamesEnum.nextElement();
                if (headerName.equalsIgnoreCase("if-modified-since")) {
                    ifModifiedSince = new Date(request.getDateHeader(headerName));
                } else {
                    headers.put(headerName.toLowerCase(), Collections.list(request.getHeaders(headerName)));
                }
            }
            HttpHeaders httpHeaders = new HttpHeaders(headers);
            if (ifModifiedSince != null) {
                httpHeaders.setIfModifiedSince(ifModifiedSince);
            }

            if (((!resourceReq.endsWith("/rest")) && (resourceReq.endsWith("/rest/"))) ||
                    (resourceReq.startsWith("/rest"))) {
                resourceReq = resourceReq.substring("/rest/".length());
            }

            logger.debug(new StringBuilder().append("resourceReq ").append(resourceReq).toString());

            if (resourceReq.endsWith("/")) {
                resourceReq = resourceReq.substring(0, resourceReq.length() - 1);
            }

            StringTokenizer strtk = new StringTokenizer(resourceReq, "/");
            int tokenCount = strtk.countTokens();
            String resourceType = strtk.nextToken().toLowerCase();

            if (resourceType.equals("logout")) {
                logout(request, resp);
                return;
            }

            List resourcePath = null;
            if (tokenCount > 1) {
                resourcePath = new ArrayList(tokenCount - 1);
                while (strtk.hasMoreTokens()) {
                    resourcePath.add(strtk.nextToken());
                }
            }

            logger.debug("before invoking request handler");
            HttpSession session = request.getSession(true);
            if (session.isNew()) {
                logger.debug(new StringBuilder().append("created new session with id ").append(session.getId()).toString());
                RESTSession restSession = new RESTSession();
                session.setAttribute("restsession", restSession);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug(new StringBuilder().append("existing session with id ").append(session.getId()).toString());
                }
                if (session.getAttribute("restsession") == null) {
                    if (logger.isDebugEnabled()) {
                        logger.debug(new StringBuilder().append("existing session ").append(session.getId()).append(" has no REST session ").toString());
                    }

                    RESTSession restSession = new RESTSession();
                    restSession.setMXSession((MXSession) request.getSession().getAttribute("MXSession"));
                    session.setAttribute("restsession", restSession);
                }

            }

            if (correlator != null) {
                correlator.addCorrelationData("RequestURI", requestURI);
                correlator.addCorrelationData("RequestParams", request.getQueryString());
                correlator.addCorrelationData("HttpSessionId", session.getId());
                String clientIP = request.getRemoteAddr();
                if (clientIP != null) {
                    correlator.addCorrelationData("ClientIP", clientIP);
                }
            }
            String format = determineResponseFormatFromRequest(map, httpHeaders);
            ResourceRequest restRequest = new ResourceRequest(resourceType, resourcePath, map, httpHeaders, (RESTSession) session.getAttribute("restsession"), request.getMethod(), format, request.getRequestURL().toString());

            restRequest.setClientAddr(request.getRemoteAddr());
            restRequest.setClientHost(request.getRemoteHost());

            if (resourceType.equalsIgnoreCase("login")) {
                authenticateRequest(restRequest);
                return;
            }

            resp.getOutputStream();
            long ts1 = System.currentTimeMillis();

            if ((resourceType.equalsIgnoreCase("log")) && (restRequest.getMXSession() != null)) {
                streamLog(restRequest, resp);
                return;
            }

            ResourceResponse response = invokeRequestHandler(restRequest);
            long ts2 = System.currentTimeMillis();
            if (response.getRestData() != null) {
                this.sendPostResponse(resourceReq, resp, response, restRequest);
            } else {
                sendResponse(resp, response, restRequest);
            }
            long ts3 = System.currentTimeMillis();
            if (correlator != null) {
                correlator.addCorrelationData("InvokeAndSerTime", Long.toString(ts2 - ts1));
                correlator.addCorrelationData("SendResponseTime", Long.toString(ts3 - ts2));
            }

        } catch (
                Throwable t)

        {
            logger.error(t.getMessage(), t);
            if (t instanceof MXException) {
                MXException me = (MXException) t;
                if ((t instanceof MXUnknownObjectException) || ((me.getErrorGroup().equalsIgnoreCase("system")) && (((me.getErrorKey().equals("unknownobject")) || (me.getErrorKey().equals("norelationship")))))) {
                    resp.sendError(404, t.getMessage());
                    return;
                }

                if (((me.getErrorGroup().equalsIgnoreCase("rest")) && (((me.getErrorKey().equals("mboresourcenotfound")) || (me.getErrorKey().equals("osresourcenotfound"))))) || (me.getErrorKey().equals("noresourcehandler"))) {
                    resp.sendError(404, t.getMessage());
                    return;
                }

                if ((me.getErrorGroup().equalsIgnoreCase("rest")) && (me.getErrorKey().equals("unsupportedformat"))) {
                    resp.sendError(406, t.getMessage());
                    return;
                }

                if (((me.getErrorGroup().equalsIgnoreCase("rest")) && (me.getErrorKey().equals("forbidden"))) || ((me.getErrorGroup().equalsIgnoreCase("iface")) && (me.getErrorKey().equalsIgnoreCase("SKIP_TRANSACTION")))) {
                    resp.sendError(403, t.getMessage());
                    return;
                }

                if ((me.getErrorGroup().equalsIgnoreCase("rest")) && (((me.getErrorKey().equalsIgnoreCase("opnotallowed")) || (me.getErrorKey().equalsIgnoreCase("ssactionopnotallowed")) || (me.getErrorKey().equalsIgnoreCase("actionopnotallowed"))))) {
                    resp.sendError(405, t.getMessage());
                    return;
                }

                if ((me.getErrorGroup().equalsIgnoreCase("access")) && (((me.getErrorKey().equalsIgnoreCase("notauthorized")) || (me.getErrorKey().equalsIgnoreCase("NotAuthorizedForMbo")) || (me.getErrorKey().equalsIgnoreCase("invaliduser")) || (me.getErrorKey().equalsIgnoreCase("invalidCertificate")) || (me.getErrorKey().equalsIgnoreCase("invalidusersite"))))) {
                    resp.sendError(401, t.getMessage());
                    return;
                }

                if ((me.getErrorGroup().equalsIgnoreCase("system")) && (me.getErrorKey().equalsIgnoreCase("rowupdateexinfo"))) {
                    resp.sendError(412, t.getMessage());
                    return;
                }

                if ((me.getErrorGroup().equalsIgnoreCase("system")) && (me.getErrorKey().equals("sql"))) {
                    resp.sendError(400, new StringBuilder().append("unable to process request ").append(me.getClass().getName()).toString());
                    return;
                }

                resp.sendError(400, t.getMessage());
                return;
            }

            resp.sendError(500, t.getMessage());
        } finally

        {
            MXCorrelator.endCorrelation();
            if (MXServer.isMTEnabled())
                MXServer.clearTenantContext();
            MaxLoggerContext.destroyCurrentContext();
        }

    }

    private void streamLog(ResourceRequest restRequest, HttpServletResponse response)
            throws IOException, MXException {
        byte[] data = null;
        if ((restRequest.getQueryParams() != null) && (restRequest.getQueryParams().containsKey("_pageSize"))) {
            String spageSize = restRequest.getQueryParams().get("_pageSize")[0];
            int pageSize = Integer.parseInt(spageSize);
            int startLine = 0;
            if (restRequest.getQueryParams().containsKey("_pageNum")) {
                String sPageNum = restRequest.getQueryParams().get("_pageNum")[0];
                int pageNum = Integer.parseInt(sPageNum);
                startLine = pageSize * pageNum;
            }
            response.setHeader("content-type", "text/plain");
            String responseLog = readFile(response, new StringBuilder().append("REST.").append(restRequest.getMXSession().getUserInfo().getLoginID()).append("_").append(restRequest.getMXSession().getUserInfo().getMaxSessionID()).append(".log").toString(), startLine, startLine + pageSize);
            data = responseLog.getBytes(StandardCharsets.UTF_8);
        } else {
            response.setHeader("content-disposition", new StringBuilder().append("attachment; filename=").append(restRequest.getMXSession().getUserInfo().getLoginID()).append("_").append(restRequest.getMXSession().getUserInfo().getMaxSessionID()).append(".log").toString());
            data = XMLUtils.readXMLFileToBytes(new StringBuilder().append("REST.").append(restRequest.getMXSession().getUserInfo().getLoginID()).append("_").append(restRequest.getMXSession().getUserInfo().getMaxSessionID()).append(".log").toString());
        }

        if (data == null)
            return;
        response.setContentLength(data.length);
        ServletOutputStream out = response.getOutputStream();
        out.write(data);
        out.flush();
    }

    public String readFile(HttpServletResponse response, String filepath, int start, int end)
            throws MXException {
        FileInputStream fis = null;
        LineNumberReader reader = null;
        StringBuilder strb = null;
        try {
            File file = new File(filepath);

            if (file.exists()) {
                fis = new FileInputStream(file);
                reader = new LineNumberReader(new InputStreamReader(fis));
                String line = reader.readLine();
                int lineNum = 0;
                boolean append = false;
                while (line != null) {
                    if ((reader.getLineNumber() > start) && (strb == null)) {
                        strb = new StringBuilder();
                        append = true;
                    }
                    line = reader.readLine();
                    if ((strb != null) && (append)) {
                        strb.append(new StringBuilder().append(line).append("\r\n").toString());
                    }
                    ++lineNum;
                    if (reader.getLineNumber() < end) {
                        continue;
                    }
                    append = false;
                }

            }

            try {
                if (fis != null) fis.close();
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e1) {
                throw new MXSystemException("system", "major", e1);
            }
        } catch (FileNotFoundException e) {
            throw new MXSystemException("iface", "nofile");
        } catch (IOException e) {
            throw new MXSystemException("iface", "ioerror", e);
        } catch (Exception e) {
            throw new MXSystemException("system", "major", e);
        } finally {
            try {
                if (fis != null) fis.close();
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e1) {
                throw new MXSystemException("system", "major", e1);
            }

        }

        return (strb == null) ? null : strb.toString();
    }

    private String determineResponseFormatFromRequest(Map<String, String[]> requestParams, HttpHeaders headerParams) {

        if (requestParams != null && requestParams.containsKey("_format")) {
            return requestParams.get("_format")[0];
        } else {
            if (headerParams != null) {
                List acceptMimeTypeHeader = headerParams.getHeader("accept");
                ArrayList acceptMimeTypes = new ArrayList();
                if (acceptMimeTypeHeader != null && acceptMimeTypeHeader.size() > 0) {
                    String i$ = (String) acceptMimeTypeHeader.get(0);
                    StringTokenizer mimeType = new StringTokenizer(i$, ",");

                    while (mimeType.hasMoreTokens()) {
                        String tok = mimeType.nextToken();
                        StringTokenizer strtk2 = new StringTokenizer(tok, ";");
                        String mimeType1 = strtk2.nextToken();
                        acceptMimeTypes.add(mimeType1);
                    }
                }

                Iterator i$1 = acceptMimeTypes.iterator();

                while (i$1.hasNext()) {
                    String mimeType2 = (String) i$1.next();
                    if (this.formatMimeTypeMap.containsKey(mimeType2.toLowerCase())) {
                        return this.formatMimeTypeMap.get(mimeType2);
                    }
                }
            }

            return "default";
        }
    }

    protected void logout(HttpServletRequest request, HttpServletResponse resp) throws RemoteException, MXException {

        HttpSession session = request.getSession(true);
        session.invalidate();
    }

    /**
     * 处理POST请求
     *
     * @param httpServletRequest  HTTP Request
     * @param httpServletResponse Response
     * @throws ServletException Servlet异常
     * @throws IOException      IO异常
     */
    @Override
    public void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws ServletException, IOException {

        String resourceReq = "";
        String postData = "";
        try {

            httpServletRequest.setCharacterEncoding(CHARACTER_ENCODING);

            boolean t = "1".equals(MXServer.getMXServer().getProperty("mxe.logging.CorrelationEnabled"));
            MXCorrelator me1 = null;
            if (t) {
                me1 = MXCorrelator.startCorrelation("MXREST");
            }

            String contentType = httpServletRequest.getContentType();
            String requestURI = httpServletRequest.getRequestURI();

            String contextPath = httpServletRequest.getContextPath();

            System.out.println("contentType:" + contentType + "-----" + "requestURI" + requestURI + "-----" + "contextPath" + contextPath);
            if (contextPath != null && !contextPath.equals("")) {
                resourceReq = requestURI.substring(contextPath.length());
            }

            logger.debug("resourceReq=" + resourceReq);
            logger.debug("Content-Type:" + contentType);
            logger.debug("RequestURI:" + requestURI);
            Enumeration headerNamesEnum = httpServletRequest.getHeaderNames();
            HashMap headers = new HashMap();
            Map map = new HashMap(httpServletRequest.getParameterMap());
            Date ifModifiedSince = null;

            while (headerNamesEnum.hasMoreElements()) {
                String httpHeaders = (String) headerNamesEnum.nextElement();
                if (httpHeaders.equalsIgnoreCase("if-modified-since")) {
                    ifModifiedSince = new Date(httpServletRequest.getDateHeader(httpHeaders));
                } else {
                    headers.put(httpHeaders.toLowerCase(),
                            Collections.list(httpServletRequest.getHeaders(httpHeaders)));
                }
            }

            HttpHeaders httpHeaders1 = new HttpHeaders(headers);
            if (ifModifiedSince != null) {
                httpHeaders1.setIfModifiedSince(ifModifiedSince);
            }

            if (!resourceReq.endsWith("/rest") && resourceReq.endsWith("/rest/")) {
            }

            if (resourceReq.startsWith("/rest")) {
                resourceReq = resourceReq.substring("/rest/".length());
            }

            logger.debug("resourceReq " + resourceReq);
            if (resourceReq.endsWith("/")) {
                resourceReq = resourceReq.substring(0, resourceReq.length() - 1);
            }

            StringTokenizer strtk = new StringTokenizer(resourceReq, "/");
            int tokenCount = strtk.countTokens();
            String resourceType = strtk.nextToken().toLowerCase();
            if (resourceType.equals("logout")) {
                this.logout(httpServletRequest, httpServletResponse);
            } else {
                ArrayList resourcePath = null;
                if (tokenCount > 1) {
                    resourcePath = new ArrayList(tokenCount - 1);

                    while (strtk.hasMoreTokens()) {
                        String e = strtk.nextToken();
                        resourcePath.add(e);
                    }
                }

                logger.debug("before invoking httpServletRequest handler");
                HttpSession session = httpServletRequest.getSession(true);
                if (session.isNew()) {
                    logger.debug("created new session with id " + session.getId());
                    RESTSession format = new RESTSession();
                    session.setAttribute("restsession", format);
                } else {
                    logger.debug("existing session with id " + session.getId());
                    if (session.getAttribute("restsession") == null) {
                        RESTSession format = new RESTSession();
                        session.setAttribute("restsession", format);
                    }

                }

                String format1;
                if (me1 != null) {
                    me1.addCorrelationData("RequestURI", requestURI);
                    me1.addCorrelationData("RequestParams", httpServletRequest.getQueryString());
                    me1.addCorrelationData("HttpSessionId", session.getId());
                    format1 = httpServletRequest.getRemoteAddr();
                    if (format1 != null) {
                        me1.addCorrelationData("ClientIP", format1);
                    }
                }
                // httpServletRequest.g
                //将数据体封装至ResourceRequest中  body中的内容
                postData = IOUtils.toString(httpServletRequest.getInputStream(), "UTF-8");
                StringBuffer querURL = httpServletRequest.getRequestURL();
                if (!map.isEmpty()) {
                    //遍历map中的键
                    querURL.append("?");
                    for (String key : ((Map<String, String[]>) map).keySet()) {
                        querURL.append(key);
                        querURL.append("=");
                        querURL.append(((Map<String, String[]>) map).get(key)[0]);
                        querURL.append("&");
                    }
                }
                //记录日志URL
                logger.info("请求URL:" + querURL.deleteCharAt(querURL.length() - 1));
                //记录body中的内容
                logger.info("请求中的body内容:" + postData);

                map.put(CUST_MAX_POST_DATA_KEY, new String[]{postData});
                format1 = this.determineResponseFormatFromRequest(map, httpHeaders1);
                ResourceRequest resourceRequest = new ResourceRequest(resourceType, resourcePath, map, httpHeaders1,
                        (RESTSession) session.getAttribute("restsession"), httpServletRequest.getMethod(), format1, "");
                if (!resourceType.equalsIgnoreCase("login")) {
                    httpServletResponse.getOutputStream();/**/
                    long ts1 = System.currentTimeMillis();
                    //调用处理器处理请求
                    ResourceResponse resourceResponse = this.invokeRequestHandler(resourceRequest);
                    long ts2 = System.currentTimeMillis();
                    //返回响应数据
                    this.sendPostResponse(resourceReq, httpServletResponse, resourceResponse, resourceRequest);
                    long ts3 = System.currentTimeMillis();
                    /*System.out.println("#################################" + "\nrequest_resource>>>>" +
                            httpServletRequest.getRequestURI() + "\n" + "request_data=" + postData +
                            "\nresponse_data=" + JsonUtil.toJson(resourceResponse.getRestData()));*/
                    logger.info("request_resource>>>>" + httpServletRequest.getRequestURI() + "\n" + "request_data=" +
                            postData + "\nresponse_data=" + JsonUtil.toJson(resourceResponse.getRestData()));
                    if (me1 != null) {
                        me1.addCorrelationData("InvokeAndSerTime", Long.toString(ts2 - ts1));
                        me1.addCorrelationData("SendResponseTime", Long.toString(ts3 - ts2));
                    }
                } else {
                    this.authenticateRequest(resourceRequest);
                }
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            if (!(e instanceof MXException)) {
                sendPostErrorResponse(httpServletResponse, postData, resourceReq, 500, e.getMessage());
            } else {
                MXException me = (MXException) e;
                if (!(e instanceof MXUnknownObjectException) && (!me.getErrorGroup().equalsIgnoreCase("system") ||
                        !me.getErrorKey().equals("unknownobject") && !me.getErrorKey().equals("norelationship"))) {
                    if ((!me.getErrorGroup().equalsIgnoreCase("rest") ||
                            !me.getErrorKey().equals("mboresourcenotfound") &&
                                    !me.getErrorKey().equals("osresourcenotfound")) &&
                            !me.getErrorKey().equals("noresourcehandler")) {
                        if (me.getErrorGroup().equalsIgnoreCase("rest") && me.getErrorKey().equals("unsupportedformat")) {
                            sendPostErrorResponse(httpServletResponse, postData, resourceReq, 406, e.getMessage());
                        } else if (me.getErrorGroup().equalsIgnoreCase("rest") && me.getErrorKey().equals("forbidden")) {
                            sendPostErrorResponse(httpServletResponse, postData, resourceReq, 403, e.getMessage());
                        } else if (me.getErrorGroup().equalsIgnoreCase("rest") &&
                                (me.getErrorKey().equalsIgnoreCase("opnotallowed") ||
                                        me.getErrorKey().equalsIgnoreCase("ssactionopnotallowed") ||
                                        me.getErrorKey().equalsIgnoreCase("actionopnotallowed"))) {
                            sendPostErrorResponse(httpServletResponse, postData, resourceReq, 405, e.getMessage());
                        } else if (!me.getErrorGroup().equalsIgnoreCase("access") ||
                                !me.getErrorKey().equalsIgnoreCase("notauthorized") &&
                                        !me.getErrorKey().equalsIgnoreCase("NotAuthorizedForMbo") &&
                                        !me.getErrorKey().equalsIgnoreCase("invaliduser") &&
                                        !me.getErrorKey().equalsIgnoreCase("invalidCertificate") &&
                                        !me.getErrorKey().equalsIgnoreCase("invalidusersite")) {
                            if (me.getErrorGroup().equalsIgnoreCase("system") && me.getErrorKey().equals("sql")) {
                                sendPostErrorResponse(httpServletResponse, postData, resourceReq, 400,
                                        "unable to process httpServletRequest " + me.getClass().getName());
                            } else {
                                sendPostErrorResponse(httpServletResponse, postData, resourceReq, 400, e.getMessage());
                            }
                        } else {
                            sendPostErrorResponse(httpServletResponse, postData, resourceReq, 401, e.getMessage());
                        }
                    } else {
                        sendPostErrorResponse(httpServletResponse, postData, resourceReq, 404, e.getMessage());
                    }
                } else {
                    sendPostErrorResponse(httpServletResponse, postData, resourceReq, 404, e.getMessage());
                }
            }
        } finally {
            MXCorrelator.endCorrelation();
        }
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

        this.doPost(request, resp);
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

        this.doGet(request, resp);
    }

    protected void sendResponse(HttpServletResponse response, ResourceResponse responseMsg, ResourceRequest request)
            throws IOException {

        String format = request.getFormat();
        logger.debug("sendResponse format ===" + format);
        if (responseMsg == null) {
            logger.debug("responseMsg is null => 200 OK");
        } else {
            if (!responseMsg.isModified()) {
                response.setStatus(304);
                if (responseMsg.getMaxAge() > 0) {
                    response.setHeader("Cache-Control", "max-age=" + responseMsg.getMaxAge() + ", private");
                }
            } else if (format == null || format.trim().length() == 0 || format.equalsIgnoreCase("default") ||
                    format.equalsIgnoreCase(responseMsg.getFormat())) {
                response.setStatus(200);
                response.setContentType(responseMsg.getMimeType());
                if (responseMsg.getETag() != null) {
                    response.setHeader("ETag", responseMsg.getETag());
                    String data = "private";
                    if (responseMsg.getMaxAge() > 0) {
                        data = "max-age=" + responseMsg.getMaxAge() + ", " + data;
                    }

                    response.setHeader("Cache-Control", data);
                }

                if (responseMsg.getLastModified() != null) {
                    response.setDateHeader("Last-Modified", responseMsg.getLastModified().getTime());
                }

                byte[] data1 = responseMsg.getData();
                if (data1 != null) {
                    response.setContentLength(data1.length);
                }

                ServletOutputStream out = response.getOutputStream();
                out.write(responseMsg.getData());
                out.flush();
                logger.debug("sendResponse mime ===" + responseMsg.getMimeType());
            }

        }
    }

    /**
     * 发送错误响应
     *
     * @param response    响应对象
     * @param postData    接收到的数据
     * @param requestPath 请求的资源
     * @param errorCode   HTTP状态码
     * @param errorMsg    错误消息
     * @throws IOException 输入输出异常
     */
    protected void sendPostErrorResponse(HttpServletResponse response, String postData, String requestPath,
                                         int errorCode, String errorMsg) throws IOException {

        response.setStatus(200);
        response.setContentType("application/json; charset=UTF-8");
        //处理返回数据
        ServletOutputStream out = response.getOutputStream();
        String jsonResponse;

        jsonResponse = JsonUtil.toJson(new ResponseResult().failure(errorCode, requestPath, errorMsg));
        byte[] bytes = jsonResponse.getBytes(CHARACTER_ENCODING);
        response.setContentLength(bytes.length);
        out.write(bytes);
        out.flush();
        logger.info("request_resource>>>>" + requestPath + "\n" + "request_data=" + postData + "\nresponse_data=" +
                jsonResponse);
    }

    /**
     * 发送响应
     *
     * @param resourceReq 请求资源URL
     * @param response    响应对象
     * @param responseMsg 响应数据对象
     * @param request     请求数据对象
     * @throws IOException 输入输出异常
     */
    protected void sendPostResponse(String resourceReq, HttpServletResponse response, ResourceResponse responseMsg,
                                    ResourceRequest request) throws IOException {

        String format = request.getFormat();
        logger.debug("sendResponse format ===" + format);
        if (responseMsg == null) {
            logger.debug("responseMsg is null => 200 OK");
        } else {
            if (!responseMsg.isModified()) {
                response.setStatus(304);
                if (responseMsg.getMaxAge() > 0) {
                    response.setHeader("Cache-Control", "max-age=" + responseMsg.getMaxAge() + ", private");
                }
            } else if (format == null || format.trim().length() == 0 || format.equalsIgnoreCase("default") ||
                    format.equalsIgnoreCase(responseMsg.getFormat())) {
                response.setStatus(200);
                response.setContentType(responseMsg.getMimeType());
                if (responseMsg.getETag() != null) {
                    response.setHeader("ETag", responseMsg.getETag());
                    String data = "private";
                    if (responseMsg.getMaxAge() > 0) {
                        data = "max-age=" + responseMsg.getMaxAge() + ", " + data;
                    }

                    response.setHeader("Cache-Control", data);
                }

                if (responseMsg.getLastModified() != null) {
                    response.setDateHeader("Last-Modified", responseMsg.getLastModified().getTime());
                }

                //处理返回数据
                Object restData = responseMsg.getRestData();
                ServletOutputStream out = response.getOutputStream();
                String jsonResponse;

                if (resourceReq.startsWith("mrplan/")) {
                    jsonResponse = JsonUtil.toJson(restData);
                } else {
                    if (restData != null) {
                        jsonResponse =
                                JsonUtil.toJson(new ResponseResult().success(responseMsg.getResourceName(), restData));
                    } else {
                        jsonResponse = JsonUtil.toJson(new ResponseResult().failure("Maximo系统遇到未知错误，请联系管理员！"));
                    }
                }

                byte[] bytes = jsonResponse.getBytes(CHARACTER_ENCODING);
                response.setContentLength(bytes.length);
                out.write(bytes);
                out.flush();
                //记录日志
                logger.info("返回的内容:" + jsonResponse);


                logger.debug("sendResponse mime ===" + responseMsg.getMimeType());
            }

        }
    }

    protected void sendAdapterResponse(DataAdapter adapter, HttpServletResponse response, ResourceResponse responseMsg,
                                       ResourceRequest request) throws IOException {

        ResourceResponse data = null;

        try {
            data = adapter.convert(responseMsg, request);
        } catch (Exception var7) {
            response.setStatus(500);
            return;
        }

        response.setStatus(200);
        response.setContentType(data.getMimeType());
        response.setHeader("Expires", "-1");
        ServletOutputStream out = response.getOutputStream();
        out.write(data.getData());
        out.flush();
    }

    protected ResourceResponse invokeRequestHandler(ResourceRequest request) throws Exception {

        MXSession mxSession = this.authenticateRequest(request);
        MXCorrelator correlator = MXCorrelator.getCorrelator();
        if (correlator != null) {
            correlator.addCorrelationData("UserName", mxSession.getUserName());
        }
        ResourceRequestHandler resourceHandler;
        if (request.getHttpMethod().equals("POST")) {
            System.out.println("===" + request.getResourceType());
            System.out.println("=222==" + request.getResourcePath().get(0));
            resourceHandler =
                    this.createResourceHandler(request.getResourceType() + "." + request.getResourcePath().get(0));
        } else {
            resourceHandler = this.createResourceHandler(request.getResourceType());
        }
        logger.debug("resourceHandler........" + resourceHandler);
        if (resourceHandler == null) {
            String[] response1 = new String[]{request.getResourceType()};
            throw new MXApplicationException("rest", "noresourcehandler", response1);
        } else {
            resourceHandler.setHandlerName(request.getResourceType());
            resourceHandler.setRoot(true);
            resourceHandler.setMXSession(mxSession);
            ResourceResponse response = resourceHandler.handleRequest(request);
            logger.debug("resourceHandler..response......" + response);
            return response;
        }
    }

    protected MXSession authenticateRequest(ResourceRequest request) throws Exception {

        String loginid = null;
        String password = null;
        String authTok = null;
        String authUser = null;
        Object mxSession = request.getSession().getMXSession();
        if (mxSession != null && ((MXSession) mxSession).isConnected()) {
            if (this.isTokenBasedSession(request) && mxSession instanceof TokenWebAppSession) {
                TokenWebAppSession tokenWebAppSession2 = (TokenWebAppSession) mxSession;
                if (request.getHeaderParams() != null && request.getHeaderParams().containsHeader("__mxtoken") &&
                        request.getHeaderParams().containsHeader("__mxuser")) {
                    authTok = request.getHeaderParams().getHeader("__mxtoken").get(0);
                } else if (request.getQueryParams() != null && request.getQueryParams().containsKey("__mxtoken") &&
                        request.getQueryParams().containsKey("__mxuser")) {
                    authTok = request.getQueryParams().get("__mxtoken")[0];
                }

                if (!tokenWebAppSession2.getSessionToken().equals(authTok)) {
                    tokenWebAppSession2.disconnect();
                    tokenWebAppSession2.setSessionToken(authTok);
                    tokenWebAppSession2.connect();
                }
            }
        } else {
            logger.debug("before creating session........");
            if (!WebAppEnv.useAppServerSecurity()) {
                String tokenWebAppSession = null;
                if (request.getHeaderParams() != null) {
                    HttpHeaders tokenWebAppSession1 = request.getHeaderParams();
                    if (tokenWebAppSession1.containsHeader("MAXAUTH")) {
                        tokenWebAppSession = tokenWebAppSession1.getHeader("MAXAUTH").get(0);
                    }
                }

                if (tokenWebAppSession != null && tokenWebAppSession.trim().length() > 0) {
                    String tokenWebAppSession3 =
                            new String(org.apache.axiom.om.util.Base64.decode(tokenWebAppSession),
                                    StandardCharsets.UTF_8);
                    StringTokenizer strtk = new StringTokenizer(tokenWebAppSession3, ":");
                    loginid = strtk.nextToken();
                    password = strtk.nextToken();
                } else if (request.getQueryParams() != null && request.getQueryParams().containsKey("_lid") &&
                        request.getQueryParams().containsKey("_lpwd")) {
                    loginid = request.getQueryParams().get("_lid")[0];
                    password = request.getQueryParams().get("_lpwd")[0];
                } else if (request.getHeaderParams() != null && request.getHeaderParams().containsHeader("__mxtoken") &&
                        request.getHeaderParams().containsHeader("__mxuser")) {
                    authTok = request.getHeaderParams().getHeader("__mxtoken").get(0);
                    authUser = request.getHeaderParams().getHeader("__mxuser").get(0);
                } else if (request.getQueryParams() != null && request.getQueryParams().containsKey("__mxtoken") &&
                        request.getQueryParams().containsKey("__mxuser")) {
                    authTok = request.getQueryParams().get("__mxtoken")[0];
                    authUser = request.getQueryParams().get("__mxuser")[0];
                }

                if (loginid != null) {
                    mxSession = WebAppSessionProvider.getNewWebAppSession();
                    ((MXSession) mxSession).setUserName(loginid);
                    ((MXSession) mxSession).setPassword(password);
                } else {
                    if (authTok == null || authUser == null) {
                        throw new MXSystemException("access", "invaliduser");
                    }

                    TokenWebAppSession tokenWebAppSession4 =
                            (TokenWebAppSession) ((new TokenWebAppSessionFactory())
                                    .getNewSession());
                    tokenWebAppSession4.setUserName(authUser);
                    tokenWebAppSession4.setSessionToken(authTok);
                    mxSession = tokenWebAppSession4;
                }
            } else {
                mxSession = WebAppSessionProvider.getNewWebAppSession();
            }

            if (mxSession != null) {
                logger.debug("after creating session........" + mxSession);
                this.setUserLocale((MXSession) mxSession, request);
                logger.debug("after setting user locale........" + mxSession);
                ((MXSession) mxSession).connect();
                request.getSession().setMXSession((MXSession) mxSession);
                logger.debug("after connect session........" + mxSession);
            }
        }

        return (MXSession) mxSession;
    }

    protected boolean isTokenBasedSession(ResourceRequest request) {

        return request.getHeaderParams() != null && request.getHeaderParams().containsHeader("__mxtoken") &&
                request.getHeaderParams().containsHeader("__mxuser") ||
                request.getQueryParams() != null && request.getQueryParams().containsKey("__mxtoken") &&
                        request.getQueryParams().containsKey("__mxuser");
    }

    protected void setUserLocale(MXSession mxSession, ResourceRequest request) {

        String langcode = null;
        String countryCode = null;
        TimeZone tz = null;
        String acceptLang;
        if (request.getQueryParams() != null && request.getQueryParams().containsKey("_tz") &&
                request.getQueryParams().get("_tz") != null) {
            acceptLang = request.getQueryParams().get("_tz")[0];
            tz = TimeZone.getTimeZone(acceptLang);
            mxSession.setTimeZone(tz);
        }

        if (request.getQueryParams() != null && request.getQueryParams().containsKey("_lang")) {
            acceptLang = request.getQueryParams().get("_lang")[0];
            if (acceptLang.length() > 2) {
                langcode = acceptLang.substring(0, 2).toUpperCase();
                if (acceptLang.length() >= 5 & acceptLang.substring(2, 3).equals("-")) {
                    countryCode = acceptLang.substring(3, 5).toUpperCase();
                }
            } else {
                langcode = acceptLang;
            }

            if (langcode != null) {
                mxSession.setLangCode(langcode.toUpperCase());
                if (countryCode != null) {
                    mxSession.setLocale(new Locale(langcode, countryCode));
                } else {
                    mxSession.setLocale(new Locale(langcode));
                }
            }

        } else {
            if (request.getHeaderParams() != null && request.getHeaderParams().containsHeader("accept-language")) {
                acceptLang = request.getHeaderParams().getHeader("accept-language").get(0);
                if (acceptLang != null) {
                    if (acceptLang.length() > 2) {
                        langcode = acceptLang.substring(0, 2).toUpperCase();
                        if (acceptLang.length() >= 5 & acceptLang.substring(2, 3).equals("-")) {
                            countryCode = acceptLang.substring(3, 5).toUpperCase();
                        }
                    } else {
                        langcode = acceptLang.substring(0, acceptLang.length() - 1).toUpperCase();
                    }

                    if (langcode != null) {
                        mxSession.setLangCode(langcode.toUpperCase());
                        if (countryCode != null) {
                            mxSession.setLocale(new Locale(langcode, countryCode));
                        } else {
                            mxSession.setLocale(new Locale(langcode));
                        }
                    }
                }
            }

        }
    }
}
