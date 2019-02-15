package com.shuto.mam.app.sr;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * @author 作者 E-mail: xiamy@shuto.cn
 * @date 创建时间：2017年5月12日 下午9:49:29
 * @since
 * @version 1.0
 * @Copyright: 2017 Shuto版权所有
 */
public class FldSrType extends MAXTableDomain {
	public FldSrType(MboValue mbv) {
		super(mbv);
		// 获得当前字段名
		String thisAtt = getMboValue().getName();
		// 设置要获取数据表的关系
		// 获取数据表名｜关系(一般不会在此设置关系都用1=1，因为getList设置关系更灵活)
		setRelationship("srtype", "1=1");
		// 目标字段名变量
		String[] strTo = { thisAtt };
		// 获取值 字段名变更
		String[] strFrom = { "type" };
		// 此方法将获取到的值设置到目标字段 中
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public void action() throws RemoteException, MXException {
		super.action();
		String qxlb = getMboValue().getMbo().getString("S_QXLB");
		String zlto = getMboValue().getMbo().getString("S_ZLTO");
		String attname = getMboValue().getName();
//		if ("S_ZLTO".equals(attname)) {
//			if ("四类缺陷".equals(zlto)) {
//				getMboValue().getMbo().setFieldFlag("ZLCLASSIFICATION", 128L, true);
//			} else {
//				getMboValue().getMbo().setFieldFlag("ZLCLASSIFICATION", 128L, false);
//				getMboValue().getMbo().setFieldFlag("yxjk", 128l, true);
//			}
//		} else {
//			if ("四类缺陷".equals(qxlb)) {
//				getMboValue().getMbo().setFieldFlag("S_CLASSIFICATION", 128L, true);
//			} else {
//				getMboValue().getMbo().setFieldFlag("S_CLASSIFICATION", 128L, false);
//				getMboValue().getMbo().setFieldFlag("yxjk", 128l, true);
//			}
//		}
	}
}
