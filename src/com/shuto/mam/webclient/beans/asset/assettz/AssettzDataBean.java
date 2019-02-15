/**
 *
 */
package com.shuto.mam.webclient.beans.asset.assettz;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

import java.rmi.RemoteException;

/**
 * @author Administrator
 *
 */
public class AssettzDataBean extends DataBean {
	public int addrow() throws MXException {
		return super.addrow();
	}

	public void save() {
		if ("specifications_table".equals(this.getId())) {
			saveSpecifications();
		}
		try {
			super.save();
		} catch (MXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 保存技术规范
	 */
	private void saveSpecifications() {
		MboRemote location;
		try {
			location = this.getParent().getMbo().getMboSet("locations")
					.getMbo(0);
			MboSetRemote assets = location.getMboSet("ASSET");
			if (assets.count() == 1) {
				DataBean specifications = this.app
						.getDataBean("specifications_table");
				MboRemote locationspec = null;
				MboRemote assetspec = null;
				boolean mark = false;
				for (int i = 0; i < specifications.count(); i++) {
					locationspec = specifications.getMbo(i);
					if (locationspec.toBeAdded() || locationspec.toBeUpdated()
							|| locationspec.toBeDeleted()) {
						mark = true;
						break;
					}
				}
				if (mark) {
					MboSetRemote assetspecs = assets.getMbo(0).getMboSet(
							"assetspec");
					assetspecs.deleteAll();
					for (int i = 0; i < specifications.count(); i++) {
						locationspec = specifications.getMbo(i);
						if (!locationspec.toBeDeleted()) {
							assetspec = assetspecs.add();
							assetspec.setValue("assetnum", assets.getMbo(0)
									.getString("assetnum"));
							assetspec.setValue("classstructureid", assets
									.getMbo(0).getString("classstructureid"));
							assetspec.setValue("assetattrid",
									locationspec.getString("assetattrid"));
							assetspec.setValue("measureunitid",
									locationspec.getString("measureunitid"));
							assetspec.setValue("alnvalue",
									locationspec.getString("alnvalue"));
							assetspec.setValue("displaysequence",
									locationspec.getString("displaysequence"));
							assetspec.setValue("linearassetspecid", 0);
						}
					}
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 调用润乾报表打印
	 *
	 * @return
	 */
	public int report_jxls() {
		try {
			DataBean resultsBean = this.app.getDataBean("jxls_childloc_table");   //获取列表
			MboSetRemote mboset = resultsBean.getMboSet();
			MboRemote matrmbo = mboset.getMbo(getCurrentRow());		//列表行对象
			String location = matrmbo.getString("location");
			String siteid = matrmbo.getString("siteid");

			String rqreportname = "/jxls.rpx"; // 报表名称
			StringBuffer urlbf = new StringBuffer();
			//RqGetProperty getrqurl = new RqGetProperty();
			//String rqurl = getrqurl.getProperty("mxe.runqian.url") + "";
			StringBuffer rqurl = new StringBuffer(MXServer.getMXServer().getProperty("mxe.runqian.url"));

			urlbf.append(rqurl);
			urlbf.append(rqreportname);
			urlbf.append("&location="+location+"&siteid="+siteid);
			this.app.openURL(urlbf.toString(), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}
}
