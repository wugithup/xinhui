package com.shuto.mam.workflow.inventory.srcode;

import com.shuto.mam.util.AttributeUtil;
import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * @author xiamy
 * @email xiamy@shuto.cn
 * @create 2017-11-30 18:39
 * @desc 物资编码生成操作类
 * @class com.shuto.mam.workflow.inventory.srcode.ActionSrcode
 * @Copyright: 2017 Shuto版权所有
 **/

public class ActionSrcode implements ActionCustomClass {
    @Override
    public void applyCustomAction(MboRemote srcdembo, Object[] arg1)
            throws MXException, RemoteException {
        srcdembo.setValue("status", "已创建", 7L);
        CODING(srcdembo);
    }

    public void CODING(MboRemote mainMbo)
            throws MXException, RemoteException {
        Map shopItem = new HashMap();

        Set mapKeySet = new HashSet();

        String setItem = "";

        AttributeUtil au = new AttributeUtil();

        NumberFormat nf = new DecimalFormat("00000");

        MboSetRemote srcMboSet = mainMbo.getMboSet("itemlistitemisnull");

        srcMboSet.setOrderBy("classstructureid");

        MboSetRemote itemSet = null;

        int num = 1;

        String nfnum = "";

        String itemnum = "";

        MboRemote srcMbo = null;

        String classstructureid = "";

        String vendor = "";

        double unitcost = 0.0D;

        boolean isShop = false;

        MboSetRemote itemMboSet = null;

        MboRemote itemMbo = null;

        MboSetRemote vendorSet = null;

        MboRemote vendorMbo = null;

        MboSetRemote inventorySet = null;

        MboRemote inventoryMbo = null;

        MboSetRemote invcostSet = null;

        MboRemote invcostMbo = null;
        Double islongterm = Double.valueOf(mainMbo.getDouble("islongterm"));

        itemMboSet = mainMbo.getMboSet("$2012item", "item", "1=1");
        inventorySet = mainMbo.getMboSet("$2012inventory", "inventory", "1=1");

        invcostSet = mainMbo.getMboSet("$2013invcostSet", "invcost", "1=1");
        for (int i = 0; i < srcMboSet.count(); ++i) {
            itemSet = null;

            srcMbo = srcMboSet.getMbo(i);
            if (srcMbo.isNull("LOCATION")) {
                StringBuffer message = new StringBuffer("请选择仓库\n物资名称:")
                        .append(srcMbo.getString("DESCRIPTION"))
                        .append("\n型号/规格:")
                        .append(srcMbo.getString("MODELNUM"));
                throw new MXApplicationException("生成编码错误", message.toString());
            }

            vendor = srcMbo.getString("company");

            unitcost = srcMbo.getDouble("unitcost");

            isShop = srcMbo.getBoolean("isshop");

            classstructureid = srcMbo
                    .getString("classstructure.classificationid");

            if (i == 0) {
                itemSet = srcMbo.getMboSet("$2012item" + i, "item",
                        "itemnum like '" + classstructureid + "%' ");
            } else if (!srcMboSet
                    .getMbo(i)
                    .getString("classstructureid")
                    .equalsIgnoreCase(
                            srcMboSet.getMbo(i - 1).getString(
                                    "classstructureid"))) {
                itemSet = srcMbo.getMboSet("$2012item" + i, "item",
                        "itemnum like '" + classstructureid + "%' ");
            } else {
                num = srcMboSet.getMbo(i - 1).getInt("num") + 1;
            }

            if ((itemSet != null) && (itemSet.count() > 0)) {
                num = (int) itemSet.max("num") + 1;
            }

            nfnum = nf.format(num);

            itemnum = classstructureid + nfnum;

            itemMbo = itemMboSet.add(2L);

            itemMbo.setValue("itemnum", itemnum, 2L);

            itemMbo.setValue("num", num, 11L);

            itemMbo.setValue("INSPECTIONREQUIRED", 1, 11L);

            itemMbo.setValue("COMMODITYGROUP", srcMbo.getString("COMMODITYGROUP"), 11L);
            System.out.println("COMMODITYGROUP=" + srcMbo.getString("COMMODITYGROUP"));

            itemMbo.setValue("COMMODITY", srcMbo.getString("COMMODITY"), 11L);
            System.out.println("COMMODITY=" + srcMbo.getString("COMMODITY"));
            itemMbo.setValue("islongterm", islongterm.doubleValue());

            srcMbo.setValue("itemnum", itemnum, 2L);

            srcMbo.setValue("num", num);

            au.copyAttribute("SRCODE", srcMbo, itemMbo);

            if ((vendor != null) && (!"".equals(vendor))) {
                vendorSet = itemMbo.getMboSet("invvendor");

                vendorMbo = vendorSet.add();

                au.copyAttribute("SRCODE", srcMbo, vendorMbo);
            }

            shopItem.put(itemnum, srcMbo);
        }

        itemMboSet.save();

        if (islongterm.doubleValue() != 1.0D) {
            mapKeySet = shopItem.keySet();
            for (Iterator it = mapKeySet.iterator(); it.hasNext(); ) {
                setItem = (String) it.next();

                srcMbo = (MboRemote) shopItem.get(setItem);

                inventoryMbo = inventorySet.add();

                au.copyAttribute("SRCODE", srcMbo, inventoryMbo);
            }

            inventorySet.save();

            inventorySet.close();
        }

        itemMboSet.close();
        srcMboSet.close();
    }
}