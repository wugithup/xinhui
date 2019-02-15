package com.shuto.mam.webclient.beans.bzlognote;

import psdi.mbo.SqlFormat;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.BoundAttribute;
import psdi.webclient.system.controller.BoundComponentInstance;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wuchang
 * @version V1.0
 * @Title: BzLogNoteAppBean
 * @Package com.shuto.mam.webclient.beans.bzlognote
 * @Description: 班组日志查询<BZLOGNOTE>应用类
 * @date 2019/1/11 18:59
 */
public class BzLogNoteAppBean extends AppBean {
    public int CHAXUNJILUASUS() throws RemoteException, MXException {
        Map<String, BoundComponentInstance> attMap = new HashMap<String, BoundComponentInstance>();
        String happenDate1 = "";
        String happenDate2 = "";
        String notetype = "";
        for (String key : this.boundAttributes.keySet()) {
            BoundAttribute bb = (BoundAttribute) this.boundAttributes.get(key);
            BoundComponentInstance boundComponent = bb.getBoundComponent();
            if (boundComponent != null) {
                String bcId = boundComponent.getId();
                attMap.put(bcId, boundComponent);
                if ("1547202660786-tb".equals(bcId)) {
                    happenDate1 = boundComponent.getString();
                } else if ("1547202665286-tb".equals(bcId)) {
                    happenDate2 = boundComponent.getString();
                } else if ("1547202841229-tb".equals(bcId)) {
                    notetype = boundComponent.getString();
                }
            }
        }

        StringBuffer sb = null;
        ArrayList<Map> al = new ArrayList<Map>();
        Map<String, String> map = null;
        if ((happenDate1 != null) && (!happenDate1.isEmpty())) {
            sb = new StringBuffer("LOGDATE <= :1");
            map = new HashMap<String, String>();
            map.put("TYPE", "Object");
            map.put("OBJECTNAME", "BZLOGNOTE");
            map.put("ATTRIBUTENAME", "LOGDATE");
            map.put("value", happenDate1);
            al.add(map);
        }

        if ((happenDate2 != null) && (!happenDate2.isEmpty())) {
            if (sb == null)
            {
                sb = new StringBuffer("LOGDATE >= :1");
            }else{
                sb = sb.append(" and LOGDATE >= :2");
            }
            map = new HashMap<String, String>();
            map.put("TYPE", "Object");
            map.put("OBJECTNAME", "BZLOGNOTE");
            map.put("ATTRIBUTENAME", "LOGDATE");
            map.put("value", happenDate2);
            al.add(map);
        }

        if ((notetype != null) && (!notetype.isEmpty())) {
            // 获取页面上的NOTETYPE并把=号替换 并以，分割
            String[] notetype_sub = notetype.replace("=", "").split(",");
            StringBuffer notetype_ub = new StringBuffer();
            // 循环分割后的数组
            for (String s : notetype_sub) {
                // 取出数组中的值
                notetype_ub.append("'" + s + "',");
            }
            // 去除最后一个，号
            notetype_ub = notetype_ub.deleteCharAt(notetype_ub.length() - 1);
            if (sb == null) {
                sb = new StringBuffer("NOTETYPE in (" + notetype_ub + ")");
            } else {
                sb = sb.append(" and NOTETYPE in (" + notetype_ub + ")");
            }
        }

        try {
            DataBean dataBean = this.app.getDataBean("results_showlist");
            if (sb != null) {
                SqlFormat sf = new SqlFormat(sb.toString());
                int i = 1;
                for (Map m : al) {
                    if ("Object".equals(m.get("TYPE"))) {
                        sf.setObject(i++, m.get("OBJECTNAME").toString(), m.get("ATTRIBUTENAME").toString(),
                                m.get("value").toString());
                    }
                }
                System.out.println("sf=" + sf.format());
                dataBean.setUserWhere(sf.format());
            } else {
                dataBean.setUserWhere("");
            }

            dataBean.resetQbe();

            dataBean.reset();

            dataBean.refreshTable();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MXException e) {
            e.printStackTrace();
        }

        return 1;
    }

}
