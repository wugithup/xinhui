package com.shuto.mam.webclient.beans.wo.xfsqt;

import com.shuto.mam.webclient.beans.base.BaseAppBean;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

import java.rmi.RemoteException;
/**
 * 消防水启/停       XFSQT
 com.shuto.mam.webclient.beans.wo.xfsqt.XfsqtAppBean 江苏
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月17日 上午10:29:00
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class XfsqtAppBean extends BaseAppBean  {
	public int SAVE() throws MXException, RemoteException {
		if (!(hasAuth())) {
			//用于弹出提示窗口 操作失败，对不起您没有修改此记录的权限！
			throw new MXApplicationException("system", "systemsavefail");
		}
		return super.SAVE();

	}
	/**
	 * 权限验证方法,通过当前应用的当前工作流任务分配人员是否与当前操作人匹配来实现
	 */
	public boolean hasAuth() throws MXException, RemoteException {

		long l = getMbo().getUniqueIDValue();
		String tableName=getMbo().getName().toUpperCase();
		String status = getMbo().getString("STATUS");
		String s1 = getMbo().getUserInfo().getPersonId();
		if (s1.equalsIgnoreCase("maxadmin"))
			return true;
		if (isNew(status))
			return true;
		MboSetRemote mbosetremote = getMbo()
				.getMboSet(
						"$instance",
						"WFINSTANCE",
						"ownertable='"+tableName+"' and ownerid='" + l
								+ "' and active = 1");
		if (!(mbosetremote.isEmpty())) {
			String s2 = "ownerid='"
					+ l
					+ "' and ownertable='"+tableName+"' and assignstatus='活动' and assigncode='"
					+ s1 + "'";
			MboSetRemote mbosetremote1 = getMbo().getMboSet("$assigncode",
					"WFASSIGNMENT", s2);
			return (!(mbosetremote1.isEmpty()));
		}
		return false;
	}
	public boolean isNew(String status){
		return "DDAPPPR".equals(status)||"新建".equals(status) ||"退回修改".equals(status);
	}
	@Override
    public int DELETE() throws MXException, RemoteException {
		//得到当前对象的mbo
		MboRemote mbo = this.app.getAppBean().getMbo();
		//得到当前用户的personid
		String personid = mbo.getUserInfo().getPersonId();
		//得到创建人得personid
		String createPerson = mbo.getString("createperson");
		//验证当前状态是否为”WAPPR“並且创建人不是操作人，当前用戶不是maxadmin
		if ((getString("status").equals("新建"))
				&& (!(personid.equals(createPerson)))
				&& (!(personid.equals("MAXADMIN")))) {
			//如果验证成立弹出提示窗口，内容为：操作失败，对不起您没有删除权限！
			throw new MXApplicationException("system", "SYSDELETE2");
		}

		return super.DELETE();
	}
}
