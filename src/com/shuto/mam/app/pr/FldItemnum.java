package com.shuto.mam.app.pr;

import psdi.app.common.purchasing.FldPurItemNum;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @author xiamy
 * @email xiamy@shuto.cn
 * @create 2017-11-28 19:45
 * @desc 采购申请item字段类
 * @class com.shuto.mam.app.pr.FldItemnum
 * @Copyright: 2017 Shuto版权所有
 **/
public class FldItemnum extends FldPurItemNum {
    public FldItemnum(MboValue arg0) throws RemoteException, MXException {
        super(arg0);
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        MboSetRemote mbset = super.getList();
        mbset.setSQLOptions(mbset.getSQLOptions().replaceAll("and rownum<=5000", ""));
        mbset = super.getList();
        return mbset;
    }

    @Override
    public void action() throws MXException, RemoteException {
        super.action();
        MboRemote ownermbo = getMboValue().getMbo().getOwner();
        String appname = ownermbo.getThisMboSet().getApp();

        Double pr_item_invcost = Double.valueOf(0.0D);

        if ("PR".equals(appname)) {
            MboRemote mbo = getMboValue().getMbo();
            String str = getMboValue().getString();
            String siteid = mbo.getString("siteid");
            MboSetRemote mbs = mbo.getMboSet("$item", "item", "itemnum='" + str + "'");
            MboSetRemote set = mbo.getMboSet("$pr_inventory", "inventory", "itemnum = '" + str + "' and siteid = '" + siteid + "'");
            if (set.getMbo(0) == null) {
                throw new MXApplicationException("提示", siteid + "的库存中没有物资编码为" + str + "的物资，请添加！");
            }
            String location = set.getMbo(0).getString("location");
            if (!location.isEmpty()) {
                mbo.setValue("STORELOC", location);
                mbo.setValueNull("CONTRACTREFNUM", 2L);
            }
            MboSetRemote pr_invcost;
            if (mbo.getString("STORELOC") == null) {
                pr_invcost = mbo.getMboSet("$pr_invcost", "invcost", "itemnum = '" + str + "' and siteid='" + siteid + "' and rownum =1 ");
            } else {
                pr_invcost = mbo.getMboSet("$pr_invcost", "invcost", "itemnum = '" + str + "' and siteid='" + siteid + "' and location='" + mbo.getString("STORELOC") + "'");
            }
            if (pr_invcost.getMbo(0) != null) {
                pr_item_invcost = Double.valueOf(pr_invcost.getMbo(0).getDouble("AVGCOST"));
                mbo.setValue("S_TAXUNITCOST", pr_item_invcost.doubleValue() * 1.17D, 11L);
            }
            MboSetRemote PRLINE_ZT = mbo.getMboSet("PRLINE_ZT");
            if (PRLINE_ZT.getMbo(0) != null) {
                mbo.setValue("RL4", PRLINE_ZT.sum("ORDERQTY"), 11L);
            }
            MboSetRemote pr_item = mbo.getMboSet("$pr_item", "item", "itemnum = '" + str + "'");
            String pr_item_name = pr_item.getMbo(0).getString("ITEM_NAME");
            String pr_item_MATERIAL = pr_item.getMbo(0).getString("MATERIAL");
            mbo.setValue("A_MODELNUM", pr_item_name, 11L);
            mbo.setValue("MATERIAL", pr_item_MATERIAL, 11L);
        }
    }
}
