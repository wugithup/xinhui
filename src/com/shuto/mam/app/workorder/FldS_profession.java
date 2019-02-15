/**   
 * @Title: FldS_profession.java 
 * @Package com.shuto.mam.app.workorder 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author lull lull@shuto.cn
 * @date 2017年6月6日 下午3:04:49 
 * @version V1.0.0
 */
package com.shuto.mam.app.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * @ClassName: FldS_profession
 * @Description: 工作票 选择专业 字段类
 * @author lull lull@shuto.cn
 * @date 2017年6月6日 下午3:04:49
 * 
 */
// / com.shuto.mam.app.wo.workorder.FldWoTeamName
public class FldS_profession extends MAXTableDomain {
	// com.shuto.mam.app.workorder.FldS_profession

	public FldS_profession(MboValue mbv) throws RemoteException, MXException {
		super(mbv);
		setRelationship("profession", "1=1");
		String[] target = { this.getMboValue().getName(), "WOJO1" };
		String[] source = { "professionnum", "DESCRIPTION" };
		setLookupKeyMapInOrder(target, source);
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mainmbo = getMboValue().getMbo();
		String siteid;
		siteid = mainmbo.getString("siteid");
		if (siteid.isEmpty()) {
			siteid = mainmbo.getInsertSite();
		}
		setListCriteria("SITEID = '" + siteid + "' and STATUS='活动'");
		setListOrderBy("PROFESSIONNUM");
		return super.getList();
	}
	public void action() throws MXException, RemoteException {
		super.action();
		MboRemote mainMbo = this.getMboValue().getMbo();
		String siteid = mainMbo.getString("siteid"); // 地点
		String profession = mainMbo.getString("S_PROFESSION"); // 专业

		MboSetRemote professionmst = mainMbo.getMboSet("$PROFESSION", "PROFESSION",
				"professionnum ='" + profession + "' and siteid='" + siteid + "'");
		if (!professionmst.isEmpty()) {
			String profession_parent = professionmst.getMbo(0).getString("parentnum"); // 父级专业
			mainMbo.setValue("PROFESSION_PARENT", profession_parent, 11l);
			mainMbo.setValue("WOJO1", professionmst.getMbo(0).getString("DESCRIPTION"), 11l);
		}

	}

}
