package com.shuto.mam.app.operation.tz;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
/**
 * 机组用煤统计     TZ_JZYMTJ,防寒防冻记录      TZ_FHFD
 com.shuto.mam.app.operation.tz.FldFhfdType 江苏
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月17日 上午10:45:58
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class FldFhfdType extends MboValueAdapter{

	public FldFhfdType(MboValue mbv) {
		super(mbv);
	}
	
	public void action() throws MXException, RemoteException {
		super.action();
		MboRemote mbo = getMboValue().getMbo();
		String fhfdtype = getMboValue().getMbo().getString("type");// 类型
		String siteid = getMboValue().getMbo().getString("siteid");// 站点
		String num = getMboValue().getMbo().getString("ffnum");
		MboSetRemote fhfdtempSet = mbo.getMboSet("#TZ_FHFDCB ","TZ_FHFDCB",
				        "siteid='" + siteid + "' and type='"
						+ fhfdtype + "' and istemplate=1");// 得到模版数据
															// istemplate是否模板
		fhfdtempSet.setOrderBy("sortnum");// 子表的 排序字段

		int count = fhfdtempSet.count();
		MboSetRemote fhfd_detail_bynumset = mbo.getMboSet("FHFD_DETAIL_BYNUM");// 得到子表结果集
		
		if (!fhfdtempSet.isEmpty()) {// 如果模版数据不为空
			fhfd_detail_bynumset.deleteAll();

			MboRemote gykgdz_detail;
			for (int i = 0; i < count; i++) {

				gykgdz_detail = fhfd_detail_bynumset.add();
				gykgdz_detail.setValue("ffnum", num, 2L);//  主键
				gykgdz_detail.setValue("description", fhfdtempSet.getMbo(i).getString("description"),2L);
				gykgdz_detail.setValue("sbmc", fhfdtempSet.getMbo(i).getString("sbmc"),2L);
				gykgdz_detail.setValue("ffcs", fhfdtempSet.getMbo(i).getString("ffcs"),2L);
				gykgdz_detail.setValue("fhfdqk", fhfdtempSet.getMbo(i).getString("fhfdqk"),2L);
				gykgdz_detail.setValue("zxr", fhfdtempSet.getMbo(i).getString("zxr"),2L);
				gykgdz_detail.setValue("sortnum", fhfdtempSet.getMbo(i)// 排序字段
						.getString("sortnum"), 2L);
				gykgdz_detail.setValue("type", fhfdtempSet.getMbo(i)// 类型
						.getString("type"), 2L);

			}
		}
		fhfdtempSet.close();
		fhfd_detail_bynumset.close();
		
	
	}
}
