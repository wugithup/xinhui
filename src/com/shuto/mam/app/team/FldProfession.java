/**   
* @Title: FldProfession.java 
* @Package com.shuto.mam.app.team 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lull lull@shuto.cn
* @date 2017年6月19日 下午11:01:04 
* @version V1.0.0
*/
package com.shuto.mam.app.team;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * @ClassName: FldProfession
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lull lull@shuto.cn
 * @date 2017年6月19日 下午11:01:04
 * 
 */
public class FldProfession extends MAXTableDomain {

	// com.shuto.mam.webclient.beans.setup.team.TeamPersonDateBean
	// com.shuto.mam.app.team.FldProfession

	/**
	 * Title: Description:
	 * 
	 * @param mbv
	 */
	public FldProfession(MboValue mbv) {
		super(mbv);
		setRelationship("profession", "1=1");
		String[] strFrom = new String[] { "professionnum" };
		String thisAttr = getMboValue().getAttributeName();
		String strTo[] = { thisAttr };
		setLookupKeyMapInOrder(strTo, strFrom);

	}

	/**
	 * <p>
	 * Title: getList
	 * <p>
	 * Description:
	 * 
	 * @return
	 * @throws MXException
	 * @throws RemoteException
	 * @see psdi.mbo.MAXTableDomain#getList()
	 */
	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		String siteid = getMboValue("siteid").getString();
		setListCriteria("status = '活动'  and siteid = '" + siteid + "' ");
		return super.getList();
	}

}
