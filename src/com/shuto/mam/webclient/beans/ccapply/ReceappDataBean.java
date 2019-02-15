package com.shuto.mam.webclient.beans.ccapply;

import com.shuto.mam.webclient.beans.base.CDataBean;
import psdi.mbo.MboRemote;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @author xiamy
 * @email xiamy@shuto.cn
 * @create 2018-02-01 16:52
 * @desc 出差申请（公司派车）DateBean
 * @class com.shuto.mam.webclient.beans.ccapply.ReceappDateBean
 * @Copyright: 2018 Shuto版权所有
 **/

public class ReceappDataBean extends CDataBean {

    @Override
    protected void setCurrentRecordData(MboRemote mbo) throws MXException, RemoteException {
        super.setCurrentRecordData(mbo);
    }
}
