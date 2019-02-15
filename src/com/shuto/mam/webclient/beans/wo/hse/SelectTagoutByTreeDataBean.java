package com.shuto.mam.webclient.beans.wo.hse;

import java.rmi.RemoteException;
import java.util.Vector;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * com.shuto.mam.webclient.beans.wo.hse.SelectTagoutByTreeDataBean 选择隔离点 设备子级的类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年6月9日 下午4:01:19
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class SelectTagoutByTreeDataBean extends DataBean {

	@Override
	public synchronized int execute() throws MXException, RemoteException {
		MboRemote mainMbo = this.app.getAppBean().getMbo();// 获取主单Mbo//主单Mbo
		String wonum = mainMbo.getString("wonum");
		String GLDJC = null;

		Vector<MboRemote> v = this.getSelection();// 获取用户选择的设备信息

		MboSetRemote woHazardMsr = mainMbo.getMboSet("WOSLTAGENABLED");// 获取危险定义列
																		// 安错分类
		MboSetRemote HSEWOWHMSR = mainMbo.getMboSet("HSEWOWH");// 获取隔离锁工作票使用维护
		if (!HSEWOWHMSR.isEmpty()) {
			GLDJC = HSEWOWHMSR.getMbo(0).getString("GLDJC");
		}
		if (!woHazardMsr.isEmpty()) {
			MboRemote woHazardMbo = woHazardMsr.getMbo(woHazardMsr
					.getCurrentPosition());
			MboSetRemote safetyMsr = woHazardMbo.getMboSet("WOSAFETYLINKTAG");// 安措列表
			for (int i = 0; i < v.size(); i++) {
				MboRemote selectMbo = v.get(i);

				MboRemote safetyMbo = safetyMsr.add();
				safetyMbo.setValue("wonum", wonum, 2L);
				safetyMbo.setValue("wosafetydatasource",
						woHazardMbo.getString("wosafetydatasource"), 2L);
				safetyMbo.setValue("hazardid",
						woHazardMbo.getString("hazardid"), 2L);
				safetyMbo.setValue("location",
						woHazardMbo.getString("location"), 2L);

				MboSetRemote tagoutMsr = selectMbo.getMboSet("TAGOUT");
				MboRemote tagoutMbo = null;
				if (!tagoutMsr.isEmpty()) {
					tagoutMbo = tagoutMsr.getMbo(0);
					safetyMbo.setValue("tagoutid",
							tagoutMbo.getString("tagoutid"), 2L);
					safetyMbo.setValue("TAGMETHOD",
							tagoutMbo.getString("TAGMETHOD"), 2L);
					safetyMbo.setValue("REQUIREDSTATE",
							tagoutMbo.getString("REQUIREDSTATE"), 2L);
					safetyMbo.setValue("ISKG", tagoutMbo.getString("ISKG"), 2L);
					safetyMbo.setValue("ISDX", tagoutMbo.getString("ISDX"), 2L);
					safetyMbo.setValue("ISBSP", tagoutMbo.getString("ISBSP"),
							2L);
					// safetyMbo.setValue("PERFORMROLE",
					// tagoutMbo.getString("PERFORMROLE"), 2L);
					MboSetRemote locationMsr = tagoutMbo.getMboSet(
							"&LOCATIONS",
							"locations",
							"location='" + tagoutMbo.getString("location")
									+ "' and siteid='"
									+ tagoutMbo.getString("siteid") + "'");
					if (!locationMsr.isEmpty()) {
						safetyMbo.setValue("TAGOUTDESCRIPTION", locationMsr
								.getMbo(0).getString("description"), 2L);
					}
					locationMsr.close();
					// // 检测是否有交叉作业
					// MboSetRemote woset = mainMbo
					// .getMboSet(
					// "$sonworkorder",
					// "workorder",
					// "   wonum in   (select min(wonum) wonum  from WOSAFETYLINK   where wosafetylinkid in  "
					// +
					// "(select wosafetylinkid   from wosafetylink ws,   (select wonum, tagoutid"
					// +
					// "    from wotagout   where location in (select location from wotagout)) wt  "
					// +
					// "where ws.wonum = wt.wonum  and ws.tagoutid = wt.tagoutid  and ws.wonum in"
					// +
					// "   (select wonum  from workorder   where status in ('已许可',   '工作终结待许可人签字',  "
					// + " '工作票押回',    '工作负责人变更签发人签字',   '延期待值长签字')"
					// +
					// "    and worktype = '工作票'   and siteid = '菏泽电厂'   and wonum not in (' "
					// + wonum
					// + "'))     and iskg = '1')  "
					// + " and WOSAFETYLINK.TAGOUTID = '"
					// + tagoutMbo.getString("tagoutid")
					// + "')");
					// if (!woset.isEmpty()) {
					// System.out.println(tagoutMbo.getString("tagoutid")
					// + "  是交叉作业【树形结构】");
					// safetyMbo.setValue("ISGLCT", "1", 2L);
					// safetyMbo.setValue("YSXID",
					// woset.getMbo(0).getString("BOXCODE"), 2L);
					// safetyMbo.setValue("AQSID",
					// woset.getMbo(0).getString("AQSID"), 2L);
					// safetyMbo.setValue("KZSID",
					// woset.getMbo(0).getString("KZSID"), 2L);
					// }
					// woset.close();
				} else {
					safetyMbo.setValue("TAGOUTID",
							GLDJC + selectMbo.getString("location"), 2L);
					safetyMbo.setValue("TAGOUTDESCRIPTION",
							selectMbo.getString("description"), 2L);
					safetyMbo.setValue("TAGOUTLOCATION",
							selectMbo.getString("location"), 2L);

					safetyMbo.setValue("APPLYSEQ", i + 1, 2L);
					// safetyMbo.setValue("PERFORMROLE", "运行人员", 2L);

				}

			}

			sessionContext.queueEvent(new WebClientEvent("dialogok",
					sessionContext.getCurrentPageId(), "", sessionContext));
			DataBean tagoutDataBean = this.app.getDataBean("WOTagOut_table");
			tagoutDataBean.reloadTable();
			tagoutDataBean.refreshTable();
			this.app.getDataBean().refreshTable();

		}

		return super.execute();
	}

}
