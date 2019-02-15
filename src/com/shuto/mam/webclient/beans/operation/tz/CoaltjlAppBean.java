package com.shuto.mam.webclient.beans.operation.tz;

import java.math.BigDecimal;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 * 存煤统计记录 CMTJ com.shuto.mam.webclient.beans.operation.tz.CoaltjlAppBean 江苏
 *  @author       liwc   liwc@shuoto.cn  @date         2017年5月17日 上午10:42:06
 *  @Copyright:   2017 Shuto版权所有  @version      V1.0 
 */
public class CoaltjlAppBean extends AppBean {

	public int INSERT() throws MXException, RemoteException {
		super.INSERT();

		MboRemote mbo = app.getAppBean().getMbo();
		String siteid = mbo.getString("siteid");
		String COALTJZBNUM = mbo.getString("COALTJZBNUM");

		MboSetRemote gykgdztempSet = mbo.getMboSet("#COALTJ", "COALTJ",
				"siteid='" + siteid + "' and istemplate=1");// 得到模版数据
		// istemplate是否模板

		gykgdztempSet.setOrderBy("sortnum");// 子表的 排序字段

		int count = gykgdztempSet.count();
		MboSetRemote gykgdz_detail_bynumset_1 = mbo.getMboSet("COALTJ");// 得到子表结果集

		if (!gykgdztempSet.isEmpty()) {// 如果模版数据不为空

			MboRemote gykgdz_detail;
			for (int i = 0; i < count; i++) {

				gykgdz_detail = gykgdz_detail_bynumset_1.add();
				gykgdz_detail.setValue("COALTJZBNUM", COALTJZBNUM, 2L);// 主键
				gykgdz_detail.setValue("TEX1", gykgdztempSet.getMbo(i)
						.getString("TEX1"),// 参数名称
						2L);
				gykgdz_detail.setValue("sortnum", gykgdztempSet.getMbo(i)// 排序字段
						.getString("sortnum"), 2L);
			}
		}
		gykgdztempSet.close();
		gykgdz_detail_bynumset_1.close();
		return 1;
	}

	public int SAVE() throws MXException, RemoteException {
		super.SAVE();
		MboRemote mbo = app.getAppBean().getMbo();
		String siteid = mbo.getString("siteid");
		String COALTJZBNUM = mbo.getString("COALTJZBNUM");

		MboSetRemote a_detail_bynumset = mbo.getMboSet("#COALTJRL", "COALTJ",
				" COALTJZBNUM='" + COALTJZBNUM + "' and siteid='" + siteid
						+ "' and istemplate=0 and sortnum=5"); // 得到所有热量结果集

		if (!a_detail_bynumset.isEmpty()) {
			double asjz1 = a_detail_bynumset.getMbo(0).getDouble("NUM1");
			// 得到每一个热量值
			double asjz2 = a_detail_bynumset.getMbo(0).getDouble("NUM2");
			double asjz3 = a_detail_bynumset.getMbo(0).getDouble("NUM3");
			double asjz4 = a_detail_bynumset.getMbo(0).getDouble("NUM4");
			double asjz5 = a_detail_bynumset.getMbo(0).getDouble("NUM5");
			double asjz6 = a_detail_bynumset.getMbo(0).getDouble("NUM6");
			double asjz7 = a_detail_bynumset.getMbo(0).getDouble("NUM7");
			double asjz8 = a_detail_bynumset.getMbo(0).getDouble("NUM8");
			double asjz9 = a_detail_bynumset.getMbo(0).getDouble("NUM9");
			double asjz10 = a_detail_bynumset.getMbo(0).getDouble("NUM10");
			double asjz11 = a_detail_bynumset.getMbo(0).getDouble("NUM11");
			double asjz12 = a_detail_bynumset.getMbo(0).getDouble("NUM12");
			double asjz13 = a_detail_bynumset.getMbo(0).getDouble("NUM13");
			double asjz14 = a_detail_bynumset.getMbo(0).getDouble("NUM14");
			double asjz15 = a_detail_bynumset.getMbo(0).getDouble("NUM15");
			double asjz16 = a_detail_bynumset.getMbo(0).getDouble("NUM16");
			double asjz17 = a_detail_bynumset.getMbo(0).getDouble("NUM17");
			double asjz18 = a_detail_bynumset.getMbo(0).getDouble("NUM18");
			double asjz19 = a_detail_bynumset.getMbo(0).getDouble("NUM19");
			double asjz20 = a_detail_bynumset.getMbo(0).getDouble("NUM20");

			BigDecimal ac1 = new BigDecimal(asjz1);
			BigDecimal ac2 = new BigDecimal(asjz2);
			BigDecimal ac3 = new BigDecimal(asjz3);
			BigDecimal ac4 = new BigDecimal(asjz4);
			BigDecimal ac5 = new BigDecimal(asjz5);
			BigDecimal ac6 = new BigDecimal(asjz6);
			BigDecimal ac7 = new BigDecimal(asjz7);
			BigDecimal ac8 = new BigDecimal(asjz8);
			BigDecimal ac9 = new BigDecimal(asjz9);
			BigDecimal ac10 = new BigDecimal(asjz10);
			BigDecimal ac11 = new BigDecimal(asjz11);
			BigDecimal ac12 = new BigDecimal(asjz12);
			BigDecimal ac13 = new BigDecimal(asjz13);
			BigDecimal ac14 = new BigDecimal(asjz14);
			BigDecimal ac15 = new BigDecimal(asjz15);
			BigDecimal ac16 = new BigDecimal(asjz16);
			BigDecimal ac17 = new BigDecimal(asjz17);
			BigDecimal ac18 = new BigDecimal(asjz18);
			BigDecimal ac19 = new BigDecimal(asjz19);
			BigDecimal ac20 = new BigDecimal(asjz20);

			BigDecimal akc = ac1.add(ac2).add(ac3).add(ac4).add(ac5).add(ac6)
					.add(ac7).add(ac8).add(ac9).add(ac10).add(ac11).add(ac12)
					.add(ac13).add(ac14).add(ac15).add(ac16).add(ac17)
					.add(ac18).add(ac19).add(ac20);

			double count = 0;
			for (int i = 1; i <= 20; i++) {
				double value = a_detail_bynumset.getMbo(0).getDouble("NUM" + i);
				if (value > 0) {
					count++;
				}
			}

			BigDecimal rlcount = new BigDecimal(count);

			if (rlcount.signum() > 0 && akc.signum() > 0) {

				BigDecimal avg = akc.divide(rlcount, 2,
						BigDecimal.ROUND_HALF_EVEN);

				mbo.setValue("NUM6", avg + "", 2L);// 平均热值就是所有热量的平均
			}
			a_detail_bynumset.close();

		}

		MboSetRemote b_detail_bynumset = mbo.getMboSet("#COALTJML", "COALTJ",
				" COALTJZBNUM='" + COALTJZBNUM + "' and siteid='" + siteid
						+ "' and istemplate=0 and sortnum=7 ");// 得到存煤量结果集
		if (!b_detail_bynumset.isEmpty()) {
			double bsjz1 = b_detail_bynumset.getMbo(0).getDouble("NUM1");
			double bsjz2 = b_detail_bynumset.getMbo(0).getDouble("NUM2");
			double bsjz3 = b_detail_bynumset.getMbo(0).getDouble("NUM3");
			double bsjz4 = b_detail_bynumset.getMbo(0).getDouble("NUM4");
			double bsjz5 = b_detail_bynumset.getMbo(0).getDouble("NUM5");
			double bsjz6 = b_detail_bynumset.getMbo(0).getDouble("NUM6");
			double bsjz7 = b_detail_bynumset.getMbo(0).getDouble("NUM7");
			double bsjz8 = b_detail_bynumset.getMbo(0).getDouble("NUM8");
			double bsjz9 = b_detail_bynumset.getMbo(0).getDouble("NUM9");
			double bsjz10 = b_detail_bynumset.getMbo(0).getDouble("NUM10");
			double bsjz11 = b_detail_bynumset.getMbo(0).getDouble("NUM11");
			double bsjz12 = b_detail_bynumset.getMbo(0).getDouble("NUM12");
			double bsjz13 = b_detail_bynumset.getMbo(0).getDouble("NUM13");
			double bsjz14 = b_detail_bynumset.getMbo(0).getDouble("NUM14");
			double bsjz15 = b_detail_bynumset.getMbo(0).getDouble("NUM15");
			double bsjz16 = b_detail_bynumset.getMbo(0).getDouble("NUM16");
			double bsjz17 = b_detail_bynumset.getMbo(0).getDouble("NUM17");
			double bsjz18 = b_detail_bynumset.getMbo(0).getDouble("NUM18");
			double bsjz19 = b_detail_bynumset.getMbo(0).getDouble("NUM19");
			double bsjz20 = b_detail_bynumset.getMbo(0).getDouble("NUM20");

			BigDecimal bc1 = new BigDecimal(bsjz1);
			BigDecimal bc2 = new BigDecimal(bsjz2);
			BigDecimal bc3 = new BigDecimal(bsjz3);
			BigDecimal bc4 = new BigDecimal(bsjz4);
			BigDecimal bc5 = new BigDecimal(bsjz5);
			BigDecimal bc6 = new BigDecimal(bsjz6);
			BigDecimal bc7 = new BigDecimal(bsjz7);
			BigDecimal bc8 = new BigDecimal(bsjz8);
			BigDecimal bc9 = new BigDecimal(bsjz9);
			BigDecimal bc10 = new BigDecimal(bsjz10);
			BigDecimal bc11 = new BigDecimal(bsjz11);
			BigDecimal bc12 = new BigDecimal(bsjz12);
			BigDecimal bc13 = new BigDecimal(bsjz13);
			BigDecimal bc14 = new BigDecimal(bsjz14);
			BigDecimal bc15 = new BigDecimal(bsjz15);
			BigDecimal bc16 = new BigDecimal(bsjz16);
			BigDecimal bc17 = new BigDecimal(bsjz17);
			BigDecimal bc18 = new BigDecimal(bsjz18);
			BigDecimal bc19 = new BigDecimal(bsjz19);
			BigDecimal bc20 = new BigDecimal(bsjz20);
			BigDecimal kc = bc1.add(bc2).add(bc3).add(bc4).add(bc5).add(bc6)
					.add(bc7).add(bc8).add(bc9).add(bc10).add(bc11).add(bc12)
					.add(bc13).add(bc14).add(bc15).add(bc16).add(bc17)
					.add(bc18).add(bc19).add(bc20);

			mbo.setValue("NUM5", kc + "");// 总煤量为所有的存煤值相加
			b_detail_bynumset.close();

		}
		this.refreshTable();
		this.reloadTable();
		return super.SAVE();
	}
}