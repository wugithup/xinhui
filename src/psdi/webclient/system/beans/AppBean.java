/*
 * Copyright (c) 2018. Shuto版权所有
 */

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package psdi.webclient.system.beans;

import com.alibaba.fastjson.JSONObject;
import com.ibm.tivoli.maximo.report.birt.admin.ReportAdminServiceRemote;
import psdi.app.report.ReportUtil;
import psdi.app.signature.MaxAppsInfo;
import psdi.app.signature.SignatureCache;
import psdi.app.ticket.TicketRemote;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.UserInfo;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.util.MXFormat;
import psdi.util.MXSession;
import psdi.util.MXSystemException;
import psdi.webclient.beans.doclinks.DocLinksControlBean;
import psdi.webclient.controls.BulletinBoard;
import psdi.webclient.system.controller.AppInstance;
import psdi.webclient.system.controller.ControlInstance;
import psdi.webclient.system.controller.PageInstance;
import psdi.webclient.system.controller.SessionContext;
import psdi.webclient.system.controller.WebClientEvent;
import psdi.webclient.system.erm.UIERMEntity;
import psdi.webclient.system.runtime.LoggingUtils;
import psdi.webclient.system.runtime.WebClientRuntime;
import psdi.webclient.system.serviceability.Category;
import psdi.webclient.system.serviceability.Level;
import psdi.webclient.system.session.WebClientSession;
import psdi.workflow.DirectorInput;
import psdi.workflow.WorkflowDirector;

import javax.servlet.http.HttpSession;
import java.beans.Beans;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;

public class AppBean extends DataBean {

    public static final int QUERY_NONE = -1;

    public static final int QUERY_ALL = -2;

    public static final int QUERY_KEY = -3;

    public static final int QUERY_ALL_BOOKMARKS = -4;

    protected static final Category LOG_CATEGORY;

    protected ResultsBean resultsBean = null;

    protected QbeBean qbeBean = null;

    protected BookmarkBean bookmarkBean = null;

    protected boolean noResultSet = true;

    protected MboSetRemote quickFindRemote = null;

    private String canSave = null;

    int queryOption = -1;

    private long kpiId = 0L;

    private boolean initialInitialize = true;

    private boolean userWhereClauseThroughIntegration = false;

    private boolean filterCleared = false;

    public static final String QUICK_LINK = "QL";

    public static final String DIRECTPRINT_LINK = "DP";

    public static final String DIRECTPRINT_WATTACH_LINK = "PAD";

    public static final int LONG_OP_READY = 1;

    public static final int LONG_OP_INPRG = 2;

    int msgRet = -1;

    public AppBean() {

    }

    public int getQueryOption() {

        return this.queryOption;
    }

    public void setQueryOption(int option) {

        this.queryOption = option;
    }

    private void setPresentationProperty(String presentationPropertyName,
            String mainrecPropertyName) {

        if (this.resultsBean != null) {
            String presentationProperty = this.getCreator().getProperty(presentationPropertyName);
            if (!WebClientRuntime.isNull(presentationProperty) && WebClientRuntime
                    .isNull(this.resultsBean.getCreator().getProperty(mainrecPropertyName))) {
                this.resultsBean.getCreator()
                                .setProperty(mainrecPropertyName, presentationProperty);
            }
        }

    }

    @Override
    public void setupBean(WebClientSession wcs) {

        if (wcs.getTipPortalMode()) {
            this.convertPortalParameters(wcs);
        }

        String beanClass = null;
        this.clientSession = wcs;
        this.app = this.clientSession.getCurrentApp();
        String resultsTable = this.creator.getProperty("resultstableid");
        if (!WebClientRuntime.isNull(resultsTable) && this.resultsBean == null) {
            this.noResultSet = false;
            ControlInstance tableHandler =
                    this.app.getCurrentPage().getControlInstance(resultsTable);
            if (tableHandler == null) {
                LoggingUtils.logConsoleMessages(2,
                        "ERROR: In the '" + this.app.getId() + "' app, a resultstableid of '" +
                                resultsTable +
                                "' was specified, but a control with that id could not be found, the ResultsBean will not be created.");
            } else {
                try {
                    beanClass = tableHandler.getProperty("beanclass");
                    if (WebClientRuntime.isNull(beanClass)) {
                        beanClass = "psdi.webclient.system.beans.ResultsBean";
                    }

                    ClassLoader classLoader = this.getClass().getClassLoader();
                    Object bean = null;
                    bean = Beans.instantiate(classLoader, beanClass);
                    if (!(bean instanceof ResultsBean)) {
                        LoggingUtils.logConsoleMessages(2,
                                "bean class is not an instance of psdi.webclient.beans.ResultsBean");
                    }

                    this.resultsBean = (ResultsBean) bean;
                    this.app.getCurrentPage().put(resultsTable, bean);
                    this.resultsBean.setId(resultsTable);
                    this.resultsBean.setCreator(tableHandler);
                    String listAppWhere = tableHandler.getProperty("apprestrictions");
                    if (!WebClientRuntime.isNull(listAppWhere)) {
                        this.creator.setProperty("apprestrictions", listAppWhere);
                    } else {
                        this.setPresentationProperty("apprestrictions", "apprestrictions");
                    }

                    this.setPresentationProperty("whereclause", "whereclause");
                    this.setPresentationProperty("orderby", "orderby");
                    this.resultsBean.setupBean(wcs);
                    this.resultsBean.addListener(this);
                    this.parent = this.resultsBean;
                } catch (Exception var8) {
                    LoggingUtils
                            .logConsoleMessages(0, "Error creating bean of type = " + beanClass);
                }
            }
        }

        super.setupBean(wcs);
        this.keyAttribute = this.creator.getProperty("keyattribute");
    }

    /**
     * @deprecated
     */
    public void setupBean(SessionContext sc) {

        this.setupBean(sc.getMasterInstance());
    }

    public boolean hasStartRecord(long uniqueId) throws MXException, RemoteException {

        MboRemote mbo = null;
        if (uniqueId != -1L) {
            if (this.resultsBean != null) {
                if (!this.resultsBean.asyncLocked()) {
                    mbo = this.resultsBean.getMboForUniqueId(uniqueId);
                }
            } else {
                mbo = this.getMboForUniqueId(uniqueId);
            }
        }

        return mbo != null;
    }

    public void initializeApp() throws MXException, RemoteException {

        String queryName;
        if (this.initialInitialize) {
            if (this.resultsBean != null) {
                this.resultsBean.saveCurrentQbeSettings(true);
            } else {
                this.saveCurrentQbeSettings(true);
            }

            MboRemote mbo;
            if (this.clientSession.lockMboOnEntry() &&
                    this.clientSession.getRequest().getParameterValues("uniqueid") != null) {
                queryName = this.clientSession.getRequest().getParameterValues("uniqueid")[0];
                mbo = this.getMboForUniqueId(new Long(queryName));
                if (mbo != null && mbo.isLocked(true)) {
                    Object[] params = new Object[]{mbo.getLockedByDisplayName()};
                    throw new MXApplicationException("jspmessages", "lockedby", params);
                }
            }

            if (this.setIntegrationRecords()) {
                this.initialInitialize = false;
                return;
            }

            if (this.creatingEvent != null &&
                    "insert".equalsIgnoreCase(this.creatingEvent.getAdditionalEvent())) {
                queryName = this.app.getStartupQueryName();
                if (!WebClientRuntime.isNull(queryName)) {
                    this.useQuery(queryName);
                } else if (this.resultsBean != null) {
                    this.resultsBean.setStartEmpty();
                }

                mbo = this.getMbo();
                if (mbo == null ||
                        !mbo.isNew() && !this.hasTableData && this.saveYesNoInteractionCheck()) {
                    this.app.getCurrentPage().put("starttab", "insert");
                    this.INSERT();
                    this.resetRemote = false;
                }

                this.initialInitialize = false;
                return;
            }
        }

        queryName = this.app.getStartupQueryName();
        String additionalEvent = this.clientSession.getCurrentEvent().getAdditionalEvent();
        if (!WebClientRuntime.isNull(queryName) &&
                (WebClientRuntime.isNull(additionalEvent) || !additionalEvent.equals("useqbe"))) {
            this.useQuery(queryName);
        } else if (this.resultsBean != null) {
            this.resultsBean.setStartEmpty();
            this.resultsBean.setQbeDefaults();
        }

        this.initialInitialize = false;
    }

    private boolean setIntegrationRecords() throws MXException, RemoteException {

        if (this.filterCleared) {
            return false;
        } else {
            String whereClause = this.app.getStartupWhereClause();
            if (!WebClientRuntime.isNull(whereClause)) {
                if (this.resultsBean != null) {
                    this.resultsBean.resetQbe();
                    this.resultsBean.clearSavedQbeSettings();
                    this.resultsBean.setUserWhere(whereClause);
                    this.setUserWhereClauseThroughIntegration(true);
                    this.resultsBean.tableStateFlags.setFlag(256L, true);
                    this.resultsBean.reset();
                    if (this.resultsBean.count() == 1 &&
                            this.app.getCurrentPage().get("starttab") == null) {
                        this.app.getCurrentPage().put("starttab", "insert");
                    }
                } else {
                    this.resetQbe();
                    this.clearSavedQbeSettings();
                    this.setUserWhere(whereClause);
                    this.reset();
                    if (this.count() == 1 && this.app.getCurrentPage().get("starttab") == null) {
                        this.app.getCurrentPage().put("starttab", "insert");
                    }
                }

                return true;
            } else if (this.hasStartRecord(
                    this.creatingEvent == null ? -1L : this.creatingEvent.getUniqueId())) {
                if (this.app.getCurrentPage().get("starttab") == null) {
                    this.app.getCurrentPage().put("starttab", "insert");
                }

                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    protected void initialize() throws MXException, RemoteException {

        this.initQuickFIndRemote();
        this.setApp();
        if (this.screenDesignMode && this.resultsBean != null &&
                this.resultsBean.startEmpty == null) {
            String resultsTableId = this.app.getProperty("resultstableid");
            ControlInstance resultsTable = this.clientSession.getControlInstance(resultsTableId);
            if (resultsTable != null) {
                resultsTable.setProperty("startempty", "true");
            }
        }
        if (this.noResultSet) {
            try {
                this.setQueryBySiteQbe();
            } catch (Exception var3) {
                var3.printStackTrace();
            }
            super.initialize();
        } else {
            if (this.mboSetRemote != null) {
                this.setAppDefaults();
                this.setUserDefaults();
                //获取mboset
                MboSetRemote mboSet = super.getMboSet();
                //通过url获取appname
                String appname = this.clientSession.getRequestParameter("value");
                String additionalevent = this.clientSession.getRequestParameter("additionalevent");
                String uniqueid = this.clientSession.getRequestParameter("uniqueid");
                String event = this.clientSession.getRequestParameter("event");
                //判断是否是从其他应用中跳转
                if ("loadapp".endsWith(event)) {
                    //判断是否为待办、结果集
                    if (!"inboxwf".equals(additionalevent) && uniqueid.isEmpty()) {
                        //判断是否是从收件箱进入
                        //获取地址
                        String siteid = mboSet.getUserInfo().getInsertSite();
                        MXServer server = MXServer.getMXServer();
                        UserInfo userInfo = getMXSession().getUserInfo();
                        //查询过滤配置表
                        MboSetRemote initmst = server.getMboSet("INITFILTERINGCFG", userInfo);
                        //设置where条件
                        initmst.setWhere(
                                "appname='" + appname.toUpperCase() + "' and siteid='" + siteid +
                                        "'");
                        //判断配置表中是否有数据
                        if (!initmst.isEmpty()) {
                            //获取where条件
                            String wheredescription = initmst.getMbo(0).getString("description");
                            DataBean resultsBean = this.app.getResultsBean();
                            resultsBean.setUserWhere(wheredescription);
                            resultsBean.reset();
                            this.app.getAppBean().reloadTable();
                            this.app.getAppBean().refreshTable();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void structureChangedEvent(DataBean speaker) {

        if (this.noResultSet) {
            super.structureChangedEvent(speaker);
        } else {
            if (speaker == this.resultsBean) {
                this.resetRemote = true;
            }

            try {
                if (this.mboSetRemote != null) {
                    this.mboSetRemote.setRetainMboPosition(false);
                }
            } catch (Exception var3) {
                this.clientSession.serviceability.logger.log(Level.ERROR, LOG_CATEGORY, var3);
            }

            this.fireStructureChangedEvent();
        }
    }

    @Override
    public void fireStructureChangedEvent(DataBean speaker) {

        this.canSave = null;
        if (this.clientSession.getTipPortalMode()) {
            String portalEvent = this.getPortalEvent(speaker);
            if (!portalEvent.equals("")) {
                this.clientSession.setPortalMsg(portalEvent);
            }
        }

        super.fireStructureChangedEvent(speaker);
    }

    @Override
    public synchronized void reset() throws MXException {

        super.reset();
        this.app.clearErrors();
    }

    @Override
    public MboSetRemote getMboSet() throws MXException, RemoteException {

        if (this.noResultSet) {
            return super.getMboSet();
        } else {
            if (this.mboSetRemote == null) {
                MXSession s = this.getMXSession();
                if (s != null) {
                    this.mboSetRemote = s.getMboSet(this.mboName);
                }

                this.setERMOnMboSet();
                this.initialize();
                this.saveCurrentQbeSettings(this.mboName != null);
                this.resetRemote = this.mboSetRemote != null;
            }

            if (this.resetRemote) {
                this.resetRemote = false;
                if (!this.screenDesignMode && this.resultsBean != null &&
                        !this.resultsBean.asyncLocked()) {
                    this.reset();
                    this.resetDataBean();
                    this.resultsBean.getFirstRecord();
                    long uniqueId = this.resultsBean.getUniqueIdValue();
                    if (uniqueId > -1L) {
                        MboRemote mbo = this.mboSetRemote.getMboForUniqueId(uniqueId);
                        if (mbo != null) {
                            this.currentRecordData = mbo.getMboData(this.getAttributes());
                            this.currentRow = 0;
                            this.fireStructureChangedEvent();
                        }
                    }
                }
            }

            return this.mboSetRemote;
        }
    }

    @Override
    public synchronized void save() throws MXException {

        if (this.noResultSet) {
            super.save();
            this.app.clearErrors();
        } else if (this.mboSetRemote != null) {
            this.preSaveChecks();
            AutoInitModel autoInit = new AutoInitModel();

            try {
                MboRemote newMbo = this.getMbo();
                if (newMbo != null) {
                    boolean sameRec = true;
                    int curOperation = 1;
                    this.checkESigAuthenticated("SAVE");
                    String routeWF = MXServer.getMXServer().getProperty("mxe.routewfcleanup");
                    boolean toCleanup = false;
                    if (routeWF != null && !routeWF.equals("")) {
                        toCleanup = MXFormat.stringToBoolean(routeWF,
                                this.clientSession.getUserInfo().getLocale());
                    }

                    long uniqueId;
                    if (newMbo.toBeAdded()) {
                        curOperation = 2;
                        autoInit.activate(newMbo);

                        try {
                            uniqueId = this.getUniqueIdValue();
                            this.mboSetRemote.save();
                            this.clientSession.addMXWarnings(this.mboSetRemote.getWarnings());
                            if (toCleanup) {
                                this.mboSetRemote.cleanup();
                            }

                            newMbo = this.getMboForUniqueId(uniqueId);
                        } catch (NullPointerException var23) {
                            this.clientSession.serviceability.logger
                                    .log(Level.ERROR, LOG_CATEGORY, var23);
                        }
                    } else if (!newMbo.toBeAdded() && toCleanup) {
                        try {
                            uniqueId = this.getUniqueIdValue();
                            this.mboSetRemote.save();
                            this.clientSession.addMXWarnings(this.mboSetRemote.getWarnings());
                            this.mboSetRemote.cleanup();
                            newMbo = this.getMboForUniqueId(uniqueId);
                        } catch (NullPointerException var22) {
                            this.clientSession.serviceability.logger
                                    .log(Level.ERROR, LOG_CATEGORY, var22);
                        }
                    } else {
                        if (this.resultsBean != null &&
                                this.getUniqueIdValue() != this.resultsBean.getUniqueIdValue()) {
                            sameRec = false;
                        }

                        this.mboSetRemote.save();
                        if (this.isAppTableRetain() && this.mboSetRemote.isRetainMboPosition()) {
                            this.mboSetRemote.positionState();
                            PageInstance curp = this.app.getCurrentPage();
                            if (curp.getId().equals("longopwait") &&
                                    curp.getParentInstance() instanceof PageInstance) {
                                curp = this.app.getPreviousPage();
                            }

                            List<DataBean> childrenBeanList = curp.getBeans();
                            if (childrenBeanList != null) {
                                int tCount = childrenBeanList.size();

                                for (int iLoop = 0; iLoop < tCount; ++iLoop) {
                                    DataBean dbean = childrenBeanList.get(iLoop);
                                    if (dbean.isAppTableRetain() && dbean.count() <=
                                            Integer.parseInt(WebClientRuntime
                                                    .getWebClientSystemProperty(
                                                            "mxe.retainrecordlimit", "200"))) {
                                        dbean.positionState();
                                    }
                                }
                            }
                        }

                        this.clientSession.addMXWarnings(this.mboSetRemote.getWarnings());
                        newMbo = this.mboSetRemote.moveTo(this.currentRow);
                    }

                    if (newMbo == null) {
                        this.currentRecordData = null;
                        this.currentRow = -1;
                    } else {
                        this.currentRecordData = newMbo.getMboData(this.getAttributes());
                        if (autoInit.doAutoInit()) {
                            this.autoInitiateWorkflow();
                        }
                    }

                    if (this.resultsBean != null) {
                        if (this.resultsBean.isListTableRetain()) {
                            if (this.clientSession.getCurrentEvent().getType().equals("DELETE")) {
                                if (!sameRec) {
                                    curOperation = 0;
                                } else {
                                    curOperation = 3;
                                }
                            }

                            this.resultsBean.recHasChanged(curOperation);
                        } else {
                            this.resultsBean.recHasChanged();
                        }
                    }

                    this.fireStructureChangedEvent();
                }
            } catch (RemoteException var24) {
                this.handleRemoteException(var24);
            } finally {
                try {
                    autoInit.clear();
                } catch (RemoteException var21) {
                    this.handleRemoteException(var21);
                }

            }

        }
    }

    public synchronized void saveattachment() throws MXException {

        if (this.noResultSet) {
            super.save();
        } else if (this.mboSetRemote != null) {
            try {
                MboRemote newMbo = this.getMbo();
                if (newMbo != null) {
                    this.checkESigAuthenticated("SAVE");
                    if (newMbo.toBeAdded()) {
                        long uniqueId = this.getUniqueIdValue();
                        this.mboSetRemote.save();
                        this.clientSession.addMXWarnings(this.mboSetRemote.getWarnings());
                        newMbo = this.getMboForUniqueId(uniqueId);
                    } else {
                        this.mboSetRemote.save();
                        this.clientSession.addMXWarnings(this.mboSetRemote.getWarnings());
                        newMbo = this.mboSetRemote.moveTo(this.currentRow);
                    }

                    if (newMbo == null) {
                        this.currentRecordData = null;
                        this.currentRow = -1;
                    } else {
                        this.currentRecordData = newMbo.getMboData(this.getAttributes());
                    }

                    if (this.resultsBean != null) {
                        this.resultsBean.recHasChanged();
                    }

                    this.fireStructureChangedEvent();
                }
            } catch (RemoteException var4) {
                this.handleRemoteException(var4);
            }

        }
    }

    public void autoInitiateWorkflow() throws RemoteException, MXException {

        this.app.put("wfinitiated", "false");
        WorkflowDirector director = this.clientSession.getWorkflowDirector();
        if (director.doAutoInit(this.getMbo())) {
            this.app.put("wfinitiated", "true");
            this.getWorkflowDirections(director);
        }

    }

    public void convertPortalParameters(WebClientSession wcs) {

    }

    public String getPortalEvent(DataBean speaker) {

        if (speaker == this) {
            DataBean results = this.getResultsBean();
            if (results.getCurrentRow() != -1) {
                try {
                    MboRemote appmbo = this.getMbo();
                    if (appmbo != null) {
                        long uniqueId = appmbo.getUniqueIDValue();
                        if (uniqueId != -1L) {
                            return this.app.getId() + ":" +
                                    this.buildPortalMsg("MAIN", Long.toString(uniqueId));
                        }
                    }
                } catch (MXException var6) {
                    this.clientSession.serviceability.logger.log(Level.ERROR, LOG_CATEGORY, var6);
                } catch (RemoteException var7) {
                    this.clientSession.serviceability.logger.log(Level.ERROR, LOG_CATEGORY, var7);
                }
            }
        }

        return "";
    }

    @Override
    public int getCurrentRow() {

        if (this.noResultSet) {
            return super.getCurrentRow();
        } else {
            try {
                if (this.getMboSet() == null) {
                    return -1;
                }
            } catch (Exception var2) {
                return -1;
            }

            return this.currentRow;
        }
    }

    @Override
    public synchronized boolean isEmpty() throws MXException, RemoteException {

        if (this.noResultSet) {
            return super.isEmpty();
        } else if (this.resultsBean != null && this.resultsBean.getCurrentRow() == -1) {
            return !this.isNewRow() && (this.currentRow != 0 || this.resetRemote);
        } else {
            return this.getMboSet() == null || this.mboSetRemote.isEmpty();
        }
    }

    public boolean saveYesNoCheck() throws MXException {

        WebClientEvent event = this.clientSession.getCurrentEvent();
        int msgRet = event.getMessageReturn();
        if (msgRet < 0) {
            if (this.app.hasSaveAccess()) {
                boolean needToSave = this.toBeSaved();
                if (!needToSave) {
                    try {
                        this.checkForAppError();
                    } catch (MXApplicationException var9) {
                        needToSave = true;
                    }
                }

                if (needToSave) {
                    throw new MXApplicationYesNoCancelException("savecontinueid", "jspmessages",
                            "savecontinue");
                }

                boolean recordLocked = this.checkForRecordLock();
                if (recordLocked) {
                    try {
                        this.RECUNLOCK();
                    } catch (RemoteException var8) {
                        this.clientSession
                                .showMessageBox(this.clientSession.getCurrentEvent(), var8);
                    }
                }
            }
        } else {
            String msgRetId = event.getMessageReturnId();
            if ("savecontinueid".equals(msgRetId)) {
                if (msgRet == 8) {
                    event.setMessageReturn(-1);
                    event.setMessageReturnId(null);
                    this.save();
                    if (this.checkForRecordLock()) {
                        try {
                            this.RECUNLOCK();
                        } catch (RemoteException var7) {
                            this.clientSession
                                    .showMessageBox(this.clientSession.getCurrentEvent(), var7);
                        }
                    }
                } else if (msgRet == 16) {
                    this.reset();
                    this.resetRemote = true;
                    if (this.checkForRecordLock()) {
                        try {
                            this.RECUNLOCK();
                        } catch (RemoteException var6) {
                            this.clientSession
                                    .showMessageBox(this.clientSession.getCurrentEvent(), var6);
                        }
                    }
                } else {
                    return msgRet != 4 && msgRet != 1;
                }
            } else {
                this.save();
            }
        }

        return true;
    }

    private boolean checkForRecordLock() throws MXException {

        boolean retVal = false;

        try {
            Mbo mbo = (Mbo) this.getMbo();
            if (mbo != null) {
                retVal = mbo.isLocked();
            }
        } catch (RemoteException var3) {
            this.clientSession.showMessageBox(this.clientSession.getCurrentEvent(), var3);
        }

        return retVal;
    }

    public boolean canSaveBasedOnLocks() throws MXException {

        boolean retValue = true;

        try {
            Mbo mbo = (Mbo) this.getMbo();
            MaxAppsInfo mai =
                    ((SignatureCache) MXServer.getMXServer().getFromMaximoCache("SIGNATURE"))
                            .getMaxAppsCache(
                                    this.clientSession.getCurrentApp().getId().toUpperCase());
            boolean appLockEnabled = mai != null && mai.isLockEnabled();
            if (!appLockEnabled) {
                return retValue;
            }

            if (mbo != null) {
                retValue = mbo.isLockedByMe() || mbo.isNew();
            }
        } catch (RemoteException var5) {
            this.clientSession.showMessageBox(this.clientSession.getCurrentEvent(), var5);
        }

        return retValue;
    }

    public boolean saveYesNoInteractionCheck() throws MXException {

        try {
            WebClientEvent event = this.clientSession.getCurrentEvent();
            int msgRet = event.getMessageReturn();
            WorkflowDirector director = this.clientSession.getWorkflowDirector();
            if (msgRet < 0) {
                boolean hasUnsavedChanges = false;
                if (this.app.hasSaveAccess()) {
                    hasUnsavedChanges = this.toBeSaved();
                    if (!hasUnsavedChanges) {
                        try {
                            this.checkForAppError();
                        } catch (MXApplicationException var6) {
                            hasUnsavedChanges = true;
                        }
                    }
                }

                boolean isDuringInteraction = director.isAtInteraction();
                if (hasUnsavedChanges && isDuringInteraction) {
                    throw new MXApplicationYesNoCancelException("saveinteractioncontinueid",
                            "jspmessages", "saveinteractioncontinue");
                }

                if (hasUnsavedChanges) {
                    throw new MXApplicationYesNoCancelException("savecontinueid", "jspmessages",
                            "savecontinue");
                }

                if (isDuringInteraction) {
                    throw new MXApplicationYesNoCancelException("interactioncontinueid",
                            "jspmessages", "interactioncontinue");
                }

                if (this.checkForRecordLock()) {
                    this.unlockRecord();
                }
            } else {
                String msgRetId = event.getMessageReturnId();
                if (!"savecontinueid".equals(msgRetId) &&
                        !"saveinteractioncontinueid".equals(msgRetId) &&
                        !"interactioncontinueid".equals(msgRetId)) {
                    this.SAVE();
                } else if (msgRet == 8) {
                    if (director.isAtInteraction()) {
                        this.STOPWF();
                    }

                    event.setMessageReturn(-1);
                    event.setMessageReturnId(null);
                    this.SAVE();
                    if (this.checkForRecordLock()) {
                        this.unlockRecord();
                    }
                } else if (msgRet == 16) {
                    if (director.isAtInteraction()) {
                        this.STOPWF();
                    }

                    this.reset();
                    this.resetRemote = true;
                    if (this.checkForRecordLock()) {
                        this.unlockRecord();
                    }
                } else if (msgRet == 4 || msgRet == 1) {
                    return false;
                }
            }
        } catch (RemoteException var7) {
            throw new MXSystemException("system", "remoteexception", var7);
        }

        int longOpStatus = this.clientSession.getLongOpStatus();
        return longOpStatus != 1 && longOpStatus != 2;
    }

    @Override
    public boolean toBeSaved() throws MXException {

        if (!this.noResultSet && this.resultsBean != null) {
            if (this.currentRow < 0 && this.resultsBean.getCurrentRow() < 0) {
                return false;
            } else {
                return super.toBeSaved() || this.resultsBean.toBeSaved();
            }
        } else {
            return super.toBeSaved();
        }
    }

    public int NEXT() throws MXException {

        if (this.resultsBean == null) {
            LoggingUtils.logConsoleMessages(0, "No results bean, no next record - " + this);
        } else if (this.saveYesNoInteractionCheck()) {
            if (!this.resultsBean.next()) {
                throw new MXApplicationException("system", "lastrecord");
            }

            try {
                this.getMboSet().setRetainMboPosition(false);
            } catch (RemoteException var2) {
                this.clientSession.serviceability.logger.log(Level.ERROR, LOG_CATEGORY, var2);
            }

            this.clientSession.getCurrentApp().getNavigationHistory().clear();
        }

        return 1;
    }

    public int NAVHISTORY() {

        WebClientEvent event = this.clientSession.getCurrentEvent();
        String target = this.app.getId() + "_mainrec_menus";
        Hashtable<String, Object> values = new Hashtable();
        values.put("menutype", "navhistory");
        values.put("targetid", target);
        values.put("component", event.getSourceComponentInstance());
        WebClientEvent wce =
                new WebClientEvent("showmenu", target, values, Integer.toString(event.getRow()),
                        event.getAdditionalEvent(), event.getAdditionalEventValue(),
                        event.getUniqueId(), this.clientSession);
        this.clientSession.queueEvent(wce);
        return 1;
    }

    public int SAVE() throws MXException, RemoteException {

        if (this.hasSaveAccess()) {
            WebClientEvent event = this.clientSession.getCurrentEvent();
            this.save();
            this.clientSession.showMessageBox(event, "system", "saverecord", (Object[]) null);
        }

        return 1;
    }

    public int RUNREPORTS() {

        String pageName = "reportFiles" + this.clientSession.getCurrentAppId().toUpperCase();
        if (this.clientSession.findDialog(pageName) == null) {
            Object[] params = new Object[]{pageName};
            this.clientSession
                    .showMessageBox(this.clientSession.getCurrentEvent(), "reports", "noxml",
                            params);
            return 1;
        } else {
            this.clientSession.loadDialog(pageName);
            return 1;
        }
    }

    public int RUNREPORTBYNAME() throws MXException, RemoteException {

        WebClientEvent event = this.clientSession.getCurrentEvent();
        MXSession s = this.getMXSession();
        MboSetRemote reportSet = s.getMboSet("REPORT");
        Object eventValue = event.getValue();
        String rptName = "";
        String whereClause = "";
        String appName = this.clientSession.getCurrentApp().getId();
        if (eventValue instanceof Hashtable) {
            Hashtable evtHash = (Hashtable) eventValue;
            appName = evtHash.get("appname").toString();
            rptName = evtHash.get("reportname").toString();
            whereClause = evtHash.get("whereclause").toString();
        } else {
            rptName = event.getValue().toString();
        }

        reportSet.setQbe("appname", appName);
        reportSet.setQbe("reportname", rptName);
        reportSet.setQbeExactMatch(true);
        reportSet.reset();
        if (reportSet.count() == 0) {
            Object[] params = new Object[]{rptName};
            this.clientSession
                    .showMessageBox(this.clientSession.getCurrentEvent(), "reports", "noxml",
                            params);
            return 1;
        } else {
            MboRemote reportRec = reportSet.getMbo(0);
            Integer rptNum = reportRec.getInt("reportnum");
            String pageName = "reportd" + rptNum;
            if (this.clientSession.findDialog(pageName) == null) {
                Object[] params = new Object[]{pageName};
                this.clientSession
                        .showMessageBox(this.clientSession.getCurrentEvent(), "reports", "noxml",
                                params);
                return 1;
            } else {
                this.clientSession.loadDialog(pageName);
                Hashtable<String, Object> reportInfo = new Hashtable();
                reportInfo.put("reportnumber", rptNum);
                reportInfo.put("whereclause", whereClause);
                reportInfo.put("quickprinttype", "PAD");
                reportInfo.put("qpnoprompt", "true");
                this.clientSession.handleEvent(
                        new WebClientEvent("requestreportrun", pageName, reportInfo,
                                this.clientSession));
                return 1;
            }
        }
    }

    public int RUNAREPORT() throws MXException, RemoteException {

        WebClientEvent event = this.clientSession.getCurrentEvent();
        Object eventValue = event.getValue();
        String rptNum = "";
        String whereClause = "";
        String qPrintType = "";
        String appName = this.clientSession.getCurrentApp().getId();
        int msgRet = event.getMessageReturn();
        if (msgRet == 2) {
            this.clientSession.loadDialog("reportFiles" + appName.toUpperCase());
            return 1;
        } else if (msgRet == 4) {
            return 1;
        } else {
            ReportAdminServiceRemote reportAdminServiceRemote =
                    (ReportAdminServiceRemote) this.getMXSession().lookup("BIRTREPORT");
            int engineState = reportAdminServiceRemote.getReportEngineState();
            if (engineState == 2) {
                this.clientSession
                        .showMessageBox(this.clientSession.getCurrentEvent(), "Reports Overloaded",
                                this.clientSession.getMaxMessage("reports", "overload")
                                                  .getMessage(), 6);
                return 1;
            } else if (engineState == 1) {
                this.clientSession
                        .showMessageBox(this.clientSession.getCurrentEvent(), "Zero Concurrent Run",
                                this.clientSession.getMaxMessage("reports", "zeroConcurrentRun")
                                                  .getMessage(), 2);
                return 1;
            } else {
                if (eventValue instanceof Hashtable) {
                    Hashtable evtHash = (Hashtable) eventValue;
                    rptNum = evtHash.get("reportnumber").toString();
                    whereClause = evtHash.get("whereclause").toString();
                } else {
                    rptNum = event.getValue().toString();
                }

                if (rptNum.endsWith("QL")) {
                    qPrintType = "QL";
                } else if (rptNum.endsWith("DP")) {
                    qPrintType = "DP";
                } else if (rptNum.endsWith("PAD")) {
                    qPrintType = "PAD";
                }

                String validChars = "0123456789";
                StringBuilder tempNum = new StringBuilder(rptNum.length());

                for (int i = 0; i < rptNum.length(); ++i) {
                    if (validChars.indexOf(rptNum.charAt(i)) > -1) {
                        tempNum.append(rptNum.charAt(i));
                    }
                }

                rptNum = tempNum.toString();
                String pageName = "reportd" + rptNum;
                if (this.clientSession.findDialog(pageName) == null) {
                    Object[] params = new Object[]{pageName};
                    this.clientSession
                            .showMessageBox(this.clientSession.getCurrentEvent(), "reports",
                                    "noxml", params);
                    return 1;
                } else {
                    this.clientSession.loadDialog(pageName);
                    Hashtable<String, Object> reportInfo = new Hashtable();
                    reportInfo.put("reportnumber", rptNum);
                    reportInfo.put("whereclause", whereClause);
                    reportInfo.put("quickprinttype", qPrintType);
                    this.clientSession.handleEvent(
                            new WebClientEvent("requestreportrun", pageName, reportInfo,
                                    this.clientSession));
                    return 1;
                }
            }
        }
    }

    public int PREVIOUS() throws MXException {

        if (this.resultsBean == null) {
            LoggingUtils.logConsoleMessages(0, "No results bean, no next record - " + this);
        } else if (this.saveYesNoInteractionCheck()) {
            if (!this.resultsBean.previous()) {
                throw new MXApplicationException("system", "firstrecord");
            }

            try {
                this.getMboSet().setRetainMboPosition(false);
            } catch (RemoteException var2) {
                this.clientSession.serviceability.logger.log(Level.ERROR, LOG_CATEGORY, var2);
            }

            this.clientSession.getCurrentApp().getNavigationHistory().clear();
        }

        return 1;
    }

    @Override
    public synchronized void insert() throws MXException, RemoteException {

        this.resetRemote = false;
        super.insert();
    }

    public int INSERT() throws MXException, RemoteException {

        String siteorg = this.getMboSet().getMboSetInfo().getSiteOrgTypeAsString();
        String insertsite = this.clientSession.getUserInfo().getInsertSite();
        if (siteorg.equals("SITE") && WebClientRuntime.isNull(insertsite)) {
            this.clientSession
                    .showMessageBox(this.clientSession.getCurrentEvent(), "access", "nodefaultsite",
                            (Object[]) null);
            this.gotoTab(this.clientSession, "list");
            return 1;
        } else {
            if (this.hasTableData) {
                LoggingUtils.logConsoleMessages(0,
                        "Didn't perform the insert, this app bean is used in a table, expected addRow() to be called - " +
                                this);
            } else if (this.saveYesNoInteractionCheck()) {
                this.gotoTab(this.clientSession, "insert");
                this.setAppDefaults();
                this.insert();
                this.resetRemote = false;
            }

            return 1;
        }
    }

    public int DELETE() throws MXException, RemoteException {

        MboRemote mbo = this.getMbo();
        if (mbo != null) {
            mbo.checkMethodAccess("DELETE");
        }

        if (this.msgRet < 0) {
            this.msgRet = MXApplicationYesNoCancelException
                    .getUserInput("deletecontinueid", MXServer.getMXServer(),
                            this.clientSession.getUserInfo());
        }

        if (this.msgRet < 0) {
            throw new MXApplicationYesNoCancelException("deletecontinueid", "system",
                    "deletecontinue");
        } else {
            if (this.msgRet == 8) {
                this.checkESigAuthenticated("DELETE");
                this.delete();
                this.save();
                this.clientSession.showMessageBox(this.clientSession.getCurrentEvent(), "system",
                        "deleterecord", (Object[]) null);
                this.gotoTab(this.clientSession, "list");
            }

            this.msgRet = -1;
            return 1;
        }
    }

    @Override
    public int addrow() throws MXException {

        if (this.resultsBean == null) {
            try {
                MboRemote mbo = this.getMbo();
                if (mbo != null) {
                    mbo.sigOptionAccessAuthorized("INSERT");
                }
            } catch (RemoteException var2) {
                this.handleRemoteException(var2);
            }
        }

        return super.addrow();
    }

    @Override
    public int toggledeleterow() throws MXException {

        if (this.resultsBean == null) {
            try {
                MboRemote mbo = this.getMbo();
                if (mbo != null) {
                    mbo.sigOptionAccessAuthorized("DELETE");
                }
            } catch (RemoteException var2) {
                this.handleRemoteException(var2);
            }
        }

        return super.toggledeleterow();
    }

    public int DUPLICATE() throws MXException, RemoteException {

        if (this.hasTableData) {
            return this.copytonewrow();
        } else {
            this.duplicateMbo();
            this.clientSession
                    .showMessageBox(this.clientSession.getCurrentEvent(), "system", "duprecord",
                            (Object[]) null);
            this.gotoTab(this.clientSession, "insert");
            return 1;
        }
    }

    public int CLEAR() throws MXException, RemoteException {

        if (this.saveYesNoInteractionCheck()) {
            if (this.resultsBean != null) {
                this.resultsBean.clearBean();
                this.gotoTab(this.clientSession, "list");
                this.setIntegrationRecords();
            } else {
                this.clearBean();
            }
        }

        return 1;
    }

    public int BOOKMARK() throws MXException, RemoteException {

        WebClientEvent event = this.clientSession.getCurrentEvent();
        int nRow = this.getRowIndexFromEvent(event);
        if (this.mboSetRemote != null && nRow >= 0) {
            this.getBookmarkBean().bookmark(this.getMbo(nRow));
            return 1;
        } else {
            return 1;
        }
    }

    public int RESULTS() throws MXException {

        if (this.resultsBean != null && !this.hasTableData && this.saveYesNoInteractionCheck()) {
            this.gotoTab(this.clientSession, "list");
        }

        return 1;
    }

    /**
     * @deprecated
     */
    protected void gotoTab(SessionContext clientSession, String tab) {

        this.gotoTab(clientSession.getMasterInstance(), tab);
    }

    protected void gotoTab(WebClientSession wcs, String tab) {

        wcs.getCurrentApp().getPageStack().get(0).gotoTab(tab);
    }

    public int find() throws MXException, RemoteException {

        WebClientEvent event = this.clientSession.getCurrentEvent();
        String searchAttribute = this.getKeyAttribute();
        ControlInstance targetControl =
                event.getWebClientSession().getControlInstance(event.getTargetId());
        String searchValue = targetControl.getProperty("quicksearchvalue");
        if (this.saveYesNoInteractionCheck()) {
            if (searchValue != null) {
                searchValue = WebClientRuntime.decodeSafevalue(searchValue);
            }

            if (searchValue.equals("")) {
                this.clientSession.queueEvent(
                        new WebClientEvent("SEARCHMORE", this.clientSession.getCurrentPageId(),
                                null, this.clientSession));
                return 1;
            }

            targetControl.setChangedFlag(true);
            this.quickFindRemote.resetQbe();
            this.quickFindRemote.setUserWhere("");
            this.quickFindRemote.ignoreQbeExactMatchSet(false);
            this.quickFindRemote.setQbeExactMatch(true);
            this.quickFindRemote.setQbe(searchAttribute, searchValue);
            this.quickFindRemote.reset();
            if (this.quickFindRemote.isEmpty()) {
                throw new MXApplicationException("system", "norecord");
            }

            this.quickFindRemote.ignoreQbeExactMatchSet(true);
            this.setCurrentQueryName("");
            if (this.noResultSet) {
                this.setUserWhere("");
                this.setQbe(searchAttribute, searchValue);
                this.reset();
                this.next();
            }

            if (this.resultsBean != null) {
                this.resultsBean.resetQbe();
                this.resultsBean.getMboSet().ignoreQbeExactMatchSet(false);
                this.resultsBean.setUserWhere("");
                this.resultsBean.setQbe(searchAttribute, "=" + searchValue);
                this.resultsBean.reset();
                this.resultsBean.next();
                if (this.app.onListTab()) {
                    this.gotoTab(this.clientSession, "insert");
                }

                this.resultsBean.getMboSet().ignoreQbeExactMatchSet(true);
                this.resultsBean.setTableFlag(256L, true);
                targetControl.setProperty("quicksearchvalue", "");
            } else {
                targetControl.setFocus();
            }

            this.setQueryOption(-3);
            this.clientSession.getCurrentApp().getNavigationHistory().clear();
        }

        return 1;
    }

    public int returnwithvalue() throws MXException, RemoteException {

        long uid = this.getUniqueIdValue();
        if (this.saveYesNoCheck()) {
            AppInstance prevApp = this.clientSession.getPreviousApp();
            if (prevApp != null) {
                DataBean bean = prevApp.getAppLinkBean();
                if (bean != null && this.getMboSet() != null) {
                    bean.returnLookupValue(this.mboSetRemote.getMboForUniqueId(uid));
                    bean.setReturnComponentId(null);
                    bean.setReturnAttribute(null);
                }

                this.clientSession.queueEvent(
                        new WebClientEvent("applinkreturn", prevApp.getId(), null,
                                this.clientSession));
                this.clientSession.popApp();
                prevApp.setAppLinkBean(null);
            }
        }

        return 1;
    }

    public int returnnovalue() throws MXException {

        if (this.saveYesNoCheck()) {
            AppInstance prevApp = this.clientSession.getPreviousApp();
            if (prevApp != null) {
                prevApp.setAppLinkBean(null);
                this.clientSession.queueEvent(
                        new WebClientEvent("applinkreturn", prevApp.getId(), null,
                                this.clientSession));
                this.clientSession.popApp();
            }
        }

        return 1;
    }

    public int returntoapp() throws MXException {

        if (this.saveYesNoCheck()) {
            int index = Integer.parseInt(this.clientSession.getCurrentEvent().getValueString());
            WebClientSession wcs = this.clientSession;
            Stack<AppInstance> appStack = wcs.getAppStack();
            int numAppstoPop = appStack.size() - index;

            for (int i = 0; i < numAppstoPop; ++i) {
                wcs.popApp();
            }

            AppInstance returnApp = appStack.peek();
            wcs.getCurrentApp().setAppLinkBean(null);
            wcs.queueEvent(new WebClientEvent("applinkreturn", returnApp.getId(), null, wcs));
        }

        return 1;
    }

    public MboSetRemote getQuickFindRemote() {

        return this.quickFindRemote;
    }

    @Override
    public void setDefaultQbe(String attribute, String expression) {

        if (this.resultsBean != null) {
            this.resultsBean.setDefaultQbe(attribute, expression);
        } else {
            super.setDefaultQbe(attribute, expression);
        }

    }

    public ResultsBean getResultsBean() {

        return this.resultsBean;
    }

    public int RECORDLOCK() throws MXException, RemoteException {

        Mbo mbo = (Mbo) this.getMbo();
        if (!mbo.isLocked(true)) {
            mbo.lock(true);
        } else {
            this.clientSession.showMessageBox(this.clientSession.getCurrentEvent(), "system",
                    "MboAlreadyLocked", (Object[]) null);
        }

        this.canSave = null;
        return 1;
    }

    public int RECUNLOCK() throws MXException, RemoteException {

        Mbo mbo = (Mbo) this.getMbo();
        if (mbo.isLocked(true) && mbo.isMboLockedByMe() && this.saveYesNoInteractionCheck()) {
            mbo.unlock(true);
            if (mbo.isModified()) {
                if (this.resultsBean != null) {
                    this.resultsBean.clearBean();
                    this.gotoTab(this.clientSession, "list");
                    this.setIntegrationRecords();
                } else {
                    this.clearBean();
                }
            }
        }

        this.canSave = null;
        return 1;
    }

    private void unlockRecord() throws MXException, RemoteException {

        Mbo mbo = (Mbo) this.getMbo();
        if (mbo.isLocked(true) && mbo.isMboLockedByMe()) {
            mbo.unlock(true);
        }

        this.canSave = null;
    }

    public int STRECLOCK() throws MXException, RemoteException {

        Mbo mbo = (Mbo) this.getMbo();
        if (mbo != null) {
            if (mbo.isLocked(true)) {
                if (mbo.isLockedByMe()) {
                    this.RECUNLOCK();
                } else {
                    WebClientEvent event = this.clientSession.getCurrentEvent();
                    String lockeddisplayname = "";
                    if (mbo != null) {
                        lockeddisplayname = mbo.getLockedByDisplayName();
                    }

                    this.clientSession.showMessageBox(event, "jspmessages", "lockedby",
                            new Object[]{lockeddisplayname});
                }
            } else {
                this.RECORDLOCK();
            }
        }

        return 1;
    }

    public JSONObject getRecordLockProperties() {

        boolean recordLocked = false;
        boolean lockedByMe = false;
        boolean hasSaveOption = false;
        JSONObject props = new JSONObject();

        try {
            MboRemote mbo = this.getMbo();
            String lockedDisplayName = "";
            if (mbo != null) {
                recordLocked = mbo.isLocked(true);
                lockedByMe = mbo.isMboLockedByMe();
                lockedDisplayName = mbo.getLockedByDisplayName();
                hasSaveOption =
                        mbo.isOptionGranted(this.clientSession.getCurrentApp().getId(), "SAVE");
            }

            if (recordLocked && !this.clientSession.getCurrentApp().onListTab()) {
                if (lockedByMe) {
                    props.put("text",
                            this.clientSession.getMessage("jspmessages", "releaseeditmode"));
                    props.put("image", "ac22_releaseEditLock.png");
                } else {
                    props.put("text", this.clientSession.getMessage("jspmessages", "lockedby",
                            new String[]{lockedDisplayName}));
                    props.put("image", "ac22_editLock.png");
                }
            } else {
                props.put("text", this.clientSession.getMessage("jspmessages", "unlocked"));
                props.put("image", "ac22_editLock.png");
            }

            if (mbo == null || !mbo.isNew() && hasSaveOption) {
                if (recordLocked) {
                    if (lockedByMe) {
                        props.put("disabled", "false");
                    } else {
                        props.put("disabled", "true");
                    }
                } else {
                    props.put("disabled", "false");
                }
            } else {
                props.put("disabled", "true");
            }
        } catch (MXException var7) {
            var7.printStackTrace();
        } catch (RemoteException var8) {
            var8.printStackTrace();
        }

        return props;
    }

    public void updateForRecordLock(ControlInstance control) {

        JSONObject props = this.getRecordLockProperties();
        Iterator keySet = props.keySet().iterator();

        while (keySet.hasNext()) {
            String key = (String) keySet.next();
            control.setProperty(key, (String) props.get(key));
        }

    }

    public int ROUTEWF() throws MXException, RemoteException {

        MboRemote mbo = this.getMbo();
        if (mbo != null && mbo.isLocked() && !mbo.isMboLockedByMe()) {
            Object[] params = new Object[]{mbo.getLockedByDisplayName()};
            throw new MXApplicationException("jspmessages", "lockedby", params);
        } else {
            WorkflowDirector director = this.clientSession.getWorkflowDirector();
            director.preventAutoInit();
            this.clientSession.forceMessagesToMainPage(true);
            if (!this.app.isMobile()) {
                this.SAVE();
            } else if (this.hasSaveAccess()) {
                this.save();
            }

            if (!this.clientSession.hasLongOpStarted() || this.clientSession.hasLongOpCompleted()) {
                director.allowAutoInit();
                director.setProcessName(this.clientSession.getCurrentEvent().getValueString());
                director.startInput(this.clientSession.getCurrentApp().getId(), this.getMbo(),
                        DirectorInput.workflow);
                this.getWorkflowDirections(director);
            }

            return 1;
        }
    }

    public int STOPWF() throws MXException, RemoteException {

        MboRemote mbo = this.getMbo();
        if (mbo != null && mbo.isLocked() && !mbo.isMboLockedByMe()) {
            Object[] params = new Object[]{mbo.getLockedByDisplayName()};
            throw new MXApplicationException("jspmessages", "lockedby", params);
        } else {
            WorkflowDirector director = this.clientSession.getWorkflowDirector();
            director.startInput(this.clientSession.getCurrentApp().getId(), this.getMbo(),
                    DirectorInput.stop);
            this.getWorkflowDirections(director);
            return 1;
        }
    }

    public int launchwf() throws MXException, RemoteException {

        WorkflowDirector director = this.clientSession.getWorkflowDirector();
        MboRemote targetMbo = this.getMbo();
        if (targetMbo == null) {
            throw new MXApplicationException("workflow", "LaunchTarget");
        } else {
            director.startInput(this.clientSession.getCurrentApp().getId(), targetMbo,
                    DirectorInput.launch);
            this.getWorkflowDirections(director);
            return 1;
        }
    }

    public int SEARCHMORE() throws MXException {

        this.saveYesNoInteractionCheck();
        return 2;
    }

    public int SEARCHWHER() throws MXException {

        this.saveYesNoInteractionCheck();
        return 2;
    }

    public int SEARCHATTR() throws MXException {

        this.saveYesNoInteractionCheck();
        return 2;
    }

    public int FLTRLASET() throws MXException {

        this.saveYesNoInteractionCheck();
        return 2;
    }

    public long getKPIId() {

        return this.kpiId;
    }

    public void setKPIId(long newId) {

        this.kpiId = newId;
    }

    public String getKPIWhere() throws MXException, RemoteException {

        return this.resultsBean != null ? this.resultsBean.getUserAndQbeWhere() :
                this.getUserAndQbeWhere();
    }

    public String getKPISelect() {

        return "select count(*) from " + this.getMboName().toLowerCase();
    }

    public int MODIFYSLAS() throws MXException, RemoteException {

        this.checkForEditHistoryMode();
        this.clientSession.loadDialog("MODIFYSLAS");
        return 1;
    }

    private void checkForEditHistoryMode() throws MXException, RemoteException {

        MboRemote mbo = this.getMbo();
        if (mbo instanceof TicketRemote) {
            TicketRemote tr = (TicketRemote) mbo;
            if (tr.isTicketInEditHist()) {
                throw new MXApplicationException("sla", "inEditHistMode");
            }
        }

    }

    public int moveToUniqueId(long uniqueId) throws MXException, RemoteException {

        if (this.getMboForUniqueId(uniqueId) != null) {
            this.gotoTab(this.clientSession, "insert");
        }

        return 1;
    }

    public boolean canExit() throws MXException {

        return this.saveYesNoInteractionCheck();
    }

    @Override
    public String getCurrentQueryName() {

        return this.resultsBean != null ? this.resultsBean.getCurrentQueryName() :
                super.getCurrentQueryName();
    }

    @Override
    protected void setCurrentQueryName(String queryName) {

        if (this.resultsBean != null) {
            this.resultsBean.setCurrentQueryName(queryName);
        } else {
            super.setCurrentQueryName(queryName);
        }

    }

    @Override
    public String getCurrentQueryDescription() {

        return this.resultsBean != null ? this.resultsBean.getCurrentQueryDescription() :
                super.getCurrentQueryDescription();
    }

    protected BookmarkBean getBookmarkBean() throws MXException {

        if (this.bookmarkBean == null) {
            String bookmarkDataSrcId = "addtobookmarks";
            String beanClass = null;
            ControlInstance dataSrcHandler = this.clientSession.findControl(bookmarkDataSrcId);
            if (dataSrcHandler == null) {
                throw new MXApplicationException("system", "error");
            }

            dataSrcHandler.setNeedsRender(false);

            try {
                beanClass = dataSrcHandler.getProperty("beanclass");
                if (WebClientRuntime.isNull(beanClass)) {
                    beanClass = "psdi.webclient.system.beans.BookmarkBean";
                }

                ClassLoader classLoader = this.getClass().getClassLoader();
                Object bean = null;
                bean = Beans.instantiate(classLoader, beanClass);
                if (!(bean instanceof BookmarkBean)) {
                    System.out.println(
                            "bean class is not an instance of psdi.webclient.beans.BookmarkBean");
                    throw new MXApplicationException("system", "error");
                }

                this.bookmarkBean = (BookmarkBean) bean;
                AppInstance appInstance = this.clientSession.getCurrentApp();
                appInstance.put(bookmarkDataSrcId, bean);
                this.bookmarkBean.setCreator(dataSrcHandler);
                this.bookmarkBean.setId(bookmarkDataSrcId);
                this.bookmarkBean.setupBean(this.clientSession);
            } catch (Exception var7) {
                LoggingUtils.logConsoleMessages(0, "Error creating bean of type = " + beanClass);
                throw new MXApplicationException("system", "error");
            }
        }

        return this.bookmarkBean;
    }

    public int HELPWF() throws MXException, RemoteException {

        WorkflowDirector director = this.clientSession.getWorkflowDirector();
        director.startInput(this.clientSession.getCurrentApp().getId(), this.getMbo(),
                DirectorInput.help);
        this.getWorkflowDirections(director);
        return 1;
    }

    public int inboxwf() throws MXException, RemoteException {

        WorkflowDirector director = this.clientSession.getWorkflowDirector();
        director.startInput(this.clientSession.getCurrentApp().getId(), this.getMbo(),
                DirectorInput.inbox);
        this.getWorkflowDirections(director);
        return 1;
    }

    public void ensureRecord() throws RemoteException, MXException {

        if (this.resultsBean == null && !this.hasTableData && this.currentRow == -1) {
            this.moveTo(0);
        }

    }

    public void setFilterCleared() {

        if (this.isUserWhereClauseThroughIntegration()) {
            this.setUserWhereClauseThroughIntegration(false);
            if (this.resultsBean != null) {
                try {
                    this.resultsBean.setUserWhere("");
                } catch (MXException var2) {
                    this.clientSession.serviceability.logger.log(Level.ERROR, LOG_CATEGORY, var2);
                }
            }
        }

        this.filterCleared = true;
    }

    @Override
    public int clearfilter() throws MXException {

        this.setFilterCleared();
        DataBean results = this.app.getResultsBean();
        if (results != null) {
            return super.clearfilter();
        } else {
            try {
                this.resetQbe();
                this.setUserWhere("");
                this.refreshQbe = true;
                this.initializeApp();
                this.initialize();
                Map<String, String> query = this.app.getUserDefaultQuery();
                if (query != null) {
                    this.useQuery(query.get("CLAUSENAME"));
                }

                this.saveCurrentQbeSettings(true);
                this.reset();
            } catch (RemoteException var3) {
                this.clientSession.serviceability.logger.log(Level.ERROR, LOG_CATEGORY, var3);
            }

            return 1;
        }
    }

    public boolean hasSaveAccess() {

        if (this.canSave == null) {
            try {
                this.canSave = this.hasSigOptionAccess("SAVE") ? "true" : "false";
            } catch (Exception var2) {
                this.canSave = "false";
            }
        }

        return Boolean.valueOf(this.canSave);
    }

    public int submitPrintJob() {

        WebClientEvent event = this.clientSession.getCurrentEvent();
        HttpSession clientHttpSession = this.clientSession.getRequest().getSession();
        Hashtable brosAuthParam = (Hashtable) this.clientSession.getRequest().getSession()
                                                                .getAttribute("brosAuthParam");
        long brosUniqueID = 0L;
        if (brosAuthParam != null) {
            String brosUniqueIDString = (String) brosAuthParam.get("brosUniqueID");
            brosUniqueID = Long.parseLong(brosUniqueIDString);
        }

        int msgRet = event.getMessageReturn();
        if (msgRet != 8 && msgRet != 16 && msgRet != 2) {
            clientHttpSession.removeAttribute("reportLink");
            clientHttpSession.removeAttribute("attachLinks");
            clientHttpSession.removeAttribute(event.getValueString() + "-whereClause");
        } else {
            String silentPrintJspURL = this.clientSession.getRequest().getContextPath() +
                    "/webclient/controls/printattachdocs/printjobspooler.jsp";
            Vector<String> printList = new Vector();
            Object reportLinks;
            if (msgRet == 8) {
                reportLinks = clientHttpSession.getAttribute("attachLinks");
                if (reportLinks != null) {
                    if (brosAuthParam != null) {
                        brosAuthParam.put("takeipixurl", "true");
                        String brosUniqueIDString = (String) brosAuthParam.get("brosUniqueID");
                        this.clientSession.getRequest().getSession()
                                          .setAttribute("brosAuthParam", brosAuthParam);

                        try {
                            ReportUtil.addToReportBrosParam("reporttype", "PAD", true, brosUniqueID,
                                    this.getMXSession().getUserInfo());
                        } catch (Exception var14) {
                            this.clientSession.serviceability.logger
                                    .log(Level.ERROR, LOG_CATEGORY, var14);
                        }
                    } else {
                        this.clientSession.getRequest().getSession()
                                          .setAttribute("reporttype", "PAD");
                        printList.addAll((Vector) reportLinks);
                    }
                }
            } else if (msgRet == 16 || msgRet == 2) {
                reportLinks = clientHttpSession.getAttribute("reportLink");
                if (reportLinks != null) {
                    printList.addAll((Vector) reportLinks);
                }

                clientHttpSession.removeAttribute("attachLinks");
                clientHttpSession.removeAttribute(event.getValueString() + "-whereClause");
            }

            clientHttpSession.removeAttribute("reportLink");
            Hashtable<String, Object> printReportParam = new Hashtable();
            if (brosAuthParam == null) {
                clientHttpSession.setAttribute("doclinks", printList);
                clientHttpSession.setAttribute("printmessage",
                        this.clientSession.getMaxMessage("printattachdocs", "printprogress")
                                          .getMessage());
                clientHttpSession.setAttribute("printerrmsg",
                        this.clientSession.getMaxMessage("reports", "printerror").getMessage());
                printReportParam.put("redir", silentPrintJspURL);
            } else {
                Hashtable sessionParams = new Hashtable();
                sessionParams.put("printmessage",
                        this.clientSession.getMaxMessage("printattachdocs", "printprogress")
                                          .getMessage());
                sessionParams.put("printerrmsg",
                        this.clientSession.getMaxMessage("reports", "printerror").getMessage());

                try {
                    ReportUtil.addToReportBrosParam(sessionParams, true, brosUniqueID,
                            this.getMXSession().getUserInfo());
                } catch (Exception var13) {
                    this.clientSession.serviceability.logger.log(Level.ERROR, LOG_CATEGORY, var13);
                }
            }

            if (brosAuthParam != null) {
                Iterator brosParamsIterator = brosAuthParam.keySet().iterator();

                while (brosParamsIterator.hasNext()) {
                    String repParamKey = (String) brosParamsIterator.next();
                    String repParamValue = (String) brosAuthParam.get(repParamKey);
                    printReportParam.put(repParamKey, repParamValue);
                }
            }

            printReportParam.put("submitPrintJob", Boolean.TRUE);
            this.app.put("spawnreport", printReportParam);
        }

        return 1;
    }

    public boolean isUserWhereClauseThroughIntegration() {

        return this.userWhereClauseThroughIntegration;
    }

    public void setUserWhereClauseThroughIntegration(boolean userWhereClauseThroughIntegration) {

        this.userWhereClauseThroughIntegration = userWhereClauseThroughIntegration;
    }

    public boolean hasSigOptionAccess(int row, String sigOption, boolean checkAppLevel)
            throws RemoteException, MXException {

        if (sigOption == null) {
            return false;
        } else {
            if (checkAppLevel) {
                boolean inPowerApp = this.getResultsBean() != null;
                boolean onAppPage = this.app.getApplicationPage() == this.app.getCurrentPage();
                if (onAppPage && (inPowerApp && this.app.onListTab() ||
                        !inPowerApp && this.getMbo(row) == null)) {
                    return this.app.hasSigOptionAccess(sigOption);
                }
            }

            return super.hasSigOptionAccess(row, sigOption);
        }
    }

    @Override
    public boolean hasSigOptionAccess(int row, String sigOption)
            throws RemoteException, MXException {

        return this.hasSigOptionAccess(row, sigOption, true);
    }

    @Override
    public void refreshFieldErrors() throws RemoteException, MXException {

        this.app.setAttributeErrorList(this.getNullRequiedFields());
    }

    public MboSetRemote initQuickFIndRemote() throws RemoteException, MXException {

        if (this.quickFindRemote == null && this.mboName != null) {
            MXSession s = this.getMXSession();
            if (s != null) {
                this.quickFindRemote = s.getMboSet(this.mboName);
                if (this.quickFindRemote != null) {
                    this.quickFindRemote.setApp(this.clientSession.getCurrentAppId().toUpperCase());
                }

                if (this.quickFindRemote != null && this.appWhere != null) {
                    try {
                        this.quickFindRemote.setAppWhere(this.appWhere);
                    } catch (RemoteException var3) {
                        this.handleRemoteException(var3);
                    }
                }
            }
        }

        return this.quickFindRemote;
    }

    private boolean canChecktoBeSaved(DataBean bean) {

        return bean == this ||
                bean != this.getResultsBean() && !(bean instanceof DocLinksControlBean) &&
                        !(bean.getCreator() instanceof BulletinBoard);
    }

    public boolean hasModifications() {

        Map<String, UIERMEntity> entities =
                this.app.getEntityRelationshipModel().getTopLevelEntities();
        if (entities != null) {
            try {
                Iterator i$ = entities.keySet().iterator();

                while (i$.hasNext()) {
                    String key = (String) i$.next();
                    DataBean dataBean = this.app.getDataBean(key);
                    if (dataBean != null && dataBean.hasMboSetRemote() &&
                            this.canChecktoBeSaved(dataBean) && dataBean.toBeSaved()) {
                        return true;
                    }
                }
            } catch (MXException var5) {
                this.clientSession.serviceability.logger.log(Level.ERROR, LOG_CATEGORY, var5);
                return true;
            }
        }

        return false;
    }

    static {
        LOG_CATEGORY = Category.get(DataBean.LOG_CATEGORY, "AppBean");
    }

    public int OPENREPORT() {

        try {
            MboRemote mbo = this.app.getAppBean().getMbo();
            MboSetRemote reportSet;
            if (mbo == null) {
                String mboname = this.app.getAppBean().getMboName();
                MboSetRemote mboSet = MXServer.getMXServer().getMboSet(mboname,
                        MXServer.getMXServer().getSystemUserInfo());
                mbo = mboSet.getMbo(0);
            }
            reportSet = mbo.getMboSet("RQREPORT");
            if (reportSet.getMbo(0) != null && reportSet.getMbo(1) == null) {
                MboRemote reportMbo = reportSet.getMbo(0);
                String dialogID = reportMbo.getString("DIALOGID");
                String rqreportname = reportMbo.getString("RQREPORTNAME");// 报表名
                String rqreportnum = reportMbo.getString("rqreportnum");// 报表编号
                String remark = reportMbo.getString("REMARK");            //备注
                System.out.println(remark);
                if (!"".equals(dialogID)) {
                    this.clientSession.setAttribute("rqreportname", rqreportname);
                    this.clientSession.setAttribute("rqreportnum", rqreportnum);
                    this.clientSession.setAttribute("remark", remark);
                    this.clientSession.loadDialog(dialogID);
                    return 0;
                }
                StringBuilder url =
                        new StringBuilder(MXServer.getMXServer().getProperty("mxe.runqian.url"));
                url.append("/").append(rqreportname.toLowerCase()).append(".rpx");
                MboSetRemote rqreportset = mbo.getMboSet("$RQREPORTPARAM", "RQREPORTPARAM",
                        "rqreportnum = '" + rqreportnum + "'");
                if (!rqreportset.isEmpty()) {
                    for (int i = 0; i < rqreportset.count(); i++) {
                        url.append("&");
                        url.append(rqreportset.getMbo(i).getString("PARAMNAME").toLowerCase());
                        url.append("=");
                        url.append(mbo.getString(rqreportset.getMbo(i).getString("PARAMVALUE")));
                    }
                }
                rqreportset.close();
                this.app.openURL(url.toString(), true);
            } else if (reportSet.count() > 1) {
                this.clientSession.loadDialog("RQREPORT1");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MXException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
