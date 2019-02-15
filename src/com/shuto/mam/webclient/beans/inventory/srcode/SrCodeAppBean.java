package com.shuto.mam.webclient.beans.inventory.srcode;

import com.shuto.mam.util.ValidateUtil;
import com.shuto.mam.workflow.inventory.srcode.ActionSrcode;
import psdi.mbo.MboRemote;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

import java.rmi.RemoteException;

/**
 * @author SumMer
 */
public class SrCodeAppBean extends AppBean {
    @Override
    public int ROUTEWF() throws MXException, RemoteException {
        MboRemote mainMbo = getMbo();
   
        ValidateUtil vu = new ValidateUtil();

        vu.valideate(this.appName, super.getClass().getSimpleName(), new java.lang.Exception().getStackTrace()[0].getMethodName(), mainMbo);

        return super.ROUTEWF();
    }

    @Override
    public int SAVE() throws MXException, RemoteException {
        MboRemote mainMbo = getMbo();
        ValidateUtil vu = new ValidateUtil();
        vu.valideate(this.appName, super.getClass().getSimpleName(), new java.lang.Exception().getStackTrace()[0].getMethodName(), mainMbo);
        return super.SAVE();
    }

    public void CODING() throws MXException, RemoteException {
        WebClientEvent event = this.clientSession.getCurrentEvent();

        int msgRet = event.getMessageReturn();
        if (msgRet < 0) {
            throw new MXApplicationYesNoCancelException("BMXAA8278E", "item", "itemcodeapp");
        }
        if (msgRet == 8) {
            MboRemote mbo = getMbo();
            ActionSrcode as = new ActionSrcode();
            as.applyCustomAction(mbo, null);
            Utility.showMessageBox(this.clientSession.getCurrentEvent(), "srcode", "srcodesuccess", null);
        }
        this.app.getAppBean().save();
    }
}