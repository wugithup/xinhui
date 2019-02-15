package com.shuto.mam.app.reportparameter;

import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;
/**   
 * @Title: FldTeamName.java 
 * @Description: TODO(消缺班组过滤) reportparmeter表TEAMNAME
 * @author LZQ   
 * @date 2017-7-4 下午02:39:59 
 * @version V1.0   
 */
public class FldTeamName  extends MAXTableDomain
{
	  public FldTeamName(MboValue mbv) {
		super(mbv);
		setRelationship("team", "1=1");
	    String[] target = { getMboValue().getName() };
	    String[] source = { "teamnum" };
	    setLookupKeyMapInOrder(target, source);
	}

	  public MboSetRemote getList() throws MXException, RemoteException {
	    MboRemote mainMbo = getMboValue().getMbo();

	    String siteid = mainMbo.getString("H_SITEID");
	    setListCriteria("SITEID = '" + siteid + "' and STATUS='活动'");
	    return super.getList();
	  }

	}