/**
 * @Title: TeamPersonDialogDateBean.java
 * @Package com.shuto.mam.webclient.beans.team
 * @Description: TODO(用一句话描述该文件做什么)
 * @author lull lull@shuto.cn
 * @date 2017年6月19日 下午11:05:37
 * @version V1.0.0
 */
package com.shuto.mam.webclient.beans.team;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

import java.rmi.RemoteException;
import java.util.Vector;

/**
 * @author lull lull@shuto.cn
 * @ClassName: TeamPersonDialogDateBean
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2017年6月19日 下午11:05:37
 */
public class TeamPersonDialogDateBean extends DataBean {
    // com.shuto.mam.webclient.beans.team.TeamPersonDialogDateBean

    @Override
    protected MboSetRemote getMboSetRemote() throws MXException, RemoteException {
        MboRemote mbo = this.app.getAppBean().getMbo();
        MboSetRemote person = mbo.getMboSet("person");
        String siteid = mbo.getString("siteid");
        String teamnum = mbo.getString("teamnum");
        String sql = "  Not Exists (Select personid From teamperson t Where teamnum = '" + teamnum
                + "' And person.personid = t.personid) And   status = '活动' and locationsite = '" + siteid + "'";
        person.setWhere(sql);
        return person;
    }

    @Override
    public synchronized int execute() throws MXException, RemoteException {
        MboRemote thismbo = this.app.getAppBean().getMbo();
        String teamnum = thismbo.getString("teamnum");
        String siteid = thismbo.getString("siteid");
        DataBean persondataBean = this.app.getDataBean("selectperson");
        MboSetRemote diaSet = persondataBean.getMboSet();
        Vector selSet = diaSet.getSelection();
        MboRemote teampersonmbo = null;
        MboRemote person = null;
        String personid = null;
        MboSetRemote personls = thismbo.getMboSet("$teamperson", "teamperson", "siteid = '" + siteid + "'");
        int count = selSet.size();
        for (int i = 0; i < selSet.size(); i++) {
            person = (MboRemote) selSet.get(i);
            personid = person.getString("personid");
            teampersonmbo = personls.add();
            teampersonmbo.setValue("siteid", siteid, 11L);
            teampersonmbo.setValue("teamnum", teamnum, 11L);
            teampersonmbo.setValue("personid", personid, 11L);
            MboSetRemote personmbo = thismbo.getMboSet("$person", "person", "personid = '" + personid + "' and locationsite='" + siteid + "'");
            personmbo.getMbo(0).setValue("banzu", teamnum, 11L);
            this.app.getAppBean().save();
            personls.close();
        }
        this.app.getAppBean().reloadTable();
        this.app.getAppBean().refreshTable();
        return super.execute();
    }

}