package com.shuto.mam.app.operation.tz;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
/**
 * 现场保温箱检查记录             TZ_BWX
 com.shuto.mam.app.operation.tz.FldBwxType 江苏
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月17日 上午10:51:42
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class FldBwxType extends MboValueAdapter{

	public FldBwxType(MboValue mbv) {
		super(mbv);
	}
	
	public void action() throws MXException, RemoteException {
		super.action();
		
		MboRemote mbo = getMboValue().getMbo();
		String bwxtype = getMboValue().getMbo().getString("type");// 类型
		String siteid = getMboValue().getMbo().getString("siteid");// 站点
		String num = getMboValue().getMbo().getString("bwxnum");
		MboSetRemote bwxtempSet = mbo.getMboSet("#TZ_BWXCB ","TZ_BWXCB",
				        "siteid='" + siteid + "' and type='"
						+ bwxtype + "' and istemplate=1");// 得到模版数据
															// istemplate是否模板
		bwxtempSet.setOrderBy("sortnum");// 子表的 排序字段

		int count = bwxtempSet.count();
		MboSetRemote bwx_detail_bynumset = mbo.getMboSet("FHFD_DETAIL_BYNUM");// 得到子表结果集
		
		if (!bwxtempSet.isEmpty()) {// 如果模版数据不为空
			bwx_detail_bynumset.deleteAll();

			MboRemote gykgdz_detail;
			for (int i = 0; i < count; i++) {

				gykgdz_detail = bwx_detail_bynumset.add();
				gykgdz_detail.setValue("bwxnum", num, 2L);//  主键
				gykgdz_detail.setValue("bwxgdwd", bwxtempSet.getMbo(i).getString("bwxgdwd"),2L);
				gykgdz_detail.setValue("bwxjcff", bwxtempSet.getMbo(i).getString("bwxjcff"),2L);
				gykgdz_detail.setValue("bwxname", bwxtempSet.getMbo(i).getString("bwxname"),2L);
				gykgdz_detail.setValue("bwxsjwd", bwxtempSet.getMbo(i).getString("bwxsjwd"),2L);
				gykgdz_detail.setValue("bwxwz", bwxtempSet.getMbo(i).getString("bwxwz"),2L);
				gykgdz_detail.setValue("czwt", bwxtempSet.getMbo(i).getString("czwt"),2L);
				gykgdz_detail.setValue("sortnum", bwxtempSet.getMbo(i)// 排序字段
						.getString("sortnum"), 2L);
				gykgdz_detail.setValue("type", bwxtempSet.getMbo(i)// 类型
						.getString("type"), 2L);

			}
		}
		bwxtempSet.close();
		bwx_detail_bynumset.close();
	}
		
}
