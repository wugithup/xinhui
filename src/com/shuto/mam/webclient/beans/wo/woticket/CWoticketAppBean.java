package com.shuto.mam.webclient.beans.wo.woticket;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import com.ibm.icu.text.NumberFormat;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.beans.workorder.WorkorderAppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.workflow.WorkFlowServiceRemote;

public class CWoticketAppBean extends WorkorderAppBean
{
    @Override
    public int INSERT()
            throws MXException, RemoteException
    {
        if (!(isMaxadmin())) {
            List list = hasAuthByOrdertype();
            if (list.isEmpty()) {
                throw new MXApplicationException("", "您没有工作负责人权限，只有工作负责人才能开工作票!");
            }
        }
        return super.INSERT();
    }

    public List<String> hasAuthByOrdertype()
            throws MXException, RemoteException
    {
        MboSetRemote mboset = getMXSession().getMboSet("WOTICKLINE");

        String personid = getMXSession().getUserInfo().getPersonId();

        String sql = " type='负责人' and personid = '" + personid + "'";
        mboset.setWhere(sql);
        mboset.reset();
        if (mboset.isEmpty()) {
            return new ArrayList();
        }

        List types = new ArrayList();
        for (int i = 0; i < mboset.count(); ++i) {
            String type = mboset.getMbo(i).getString("description");
            types.add(type);
        }
        return types;
    }

    @Override
    protected void setCurrentRecordData(MboRemote mbo)
            throws MXException, RemoteException
    {
        String status = mbo.getString("status");
        String orderType = mbo.getString("S_ORDERTYPE");

        MboSetRemote RELATEDWO = mbo.getMboSet("RELATEDWO");//相关记录

        String[] rattrs = { "DESCRIPTION", "LOCATION", "ASSETNUM", "PHONE", "S_GZTJ", "WORKSITE", //工作地点
                "S_ORDERTYPE", "S_PROFESSION", "S_WWZY", "S_GZJHPERSON", "S_JIZU", //工作票类型,专业，班组，外委专业,工作监护人，机组
                "BZWONUM", "OPLOGTYPE", "SCHEDSTART", "SCHEDFINISH",  "S_DEPARTMENT", //标准工作票编号，日志类别，预计开工时间，预计完工时间，电气或热机工作票，工作部门
                "S_DHZXPERSON", "REMARKDESC", "SFGLDH", "S_PZGZJSDATE", "S_XKKSDATE", "S_ZJDATE", //动火执行人，故障报告注释，是否关联动火票，批准结束时间，许可开始时间，工作牌终结时间
                "S_WORKCY", "S_WORKCYQTY", "S_FZRBGDATE", "S_XFZPERSON", "S_YQTODATE", "OPLOG_DELAYCAUSE", //系统外工作人员，系统外人数，负责人变更时间，新负责人，延期至，延期原因
                "S_JSZCBMARK", "S_XFMARK", "S_SQBMZGMARK", "S_BMZGMARK", "S_GSLEADMARK", //技术支持部意见，消防意见，安全意见，部门主管意见，公司领导意见
                "WOTICKET_QUALIFIED", "WOTICKET_EVALUATE" };//合格，评价
        //    mbo.setFieldFlag(rattrs, 7L, true);

        if ((status.equals("等待批准")) || (status.equals("等待核准")) || (status.equals("新建"))) {
            String[] attrs = { "S_ORDERTYPE" };
            mbo.setFieldFlag(attrs, 7L, false);
            mbo.setFieldFlag(attrs, 128L, true);
        }
        String[] attr;
        String[] rqattr;
        if ((orderType.equals("一级动火工作票")) || (orderType.equals("二级动火工作票")))
        {
            if ((status.equals("新建")) || (status.equals("等待核准")) || (status.equals("等待批准")) ||
                    (status.equals("退回修改")) || (status.equals("退回重新修改"))) {
                attr = new String[] { "LOCATION", "DESCRIPTION", "PHONE", "S_GZTJ", "WORKSITE",
                        "S_ORDERTYPE", "BZWONUM", "OPLOGTYPE", "SCHEDSTART", "SCHEDFINISH",
                        "S_WORKCY", "S_WORKCYQTY" };
                rqattr = new String[] { "LOCATION", "DESCRIPTION","WORKSITE",
                        "SCHEDSTART", "SCHEDFINISH"};
                mbo.setFieldFlag(attr, 7L, false);
                mbo.setFieldFlag(rqattr, 128L, true);
            }

            if ((status.equals("待确认")) || (status.equals("待值长审核")) || (status.equals("待值长审批"))) {
                attr = new String[] { "S_PZGZJSDATE" };

                mbo.setFieldFlag(attr, 7L, false);
            }

            if (status.equals("已终结")) {
                if (!(hasEvaluateAuth().isEmpty())) {
                    mbo.setFlag(7L, false);
                    attr = new String[] { "WOTICKET_QUALIFIED", "WOTICKET_EVALUATE" };
                    mbo.setFieldFlag(attr, 7L, false);
                } else {
                    mbo.setFlag(7L, true);
                }
            }

            if (!(status.equals("关闭"))) status.equals("已关闭");
        }
        //    else {
        //      if (RELATEDWO.isEmpty()) {
        //        if ((status.equals("新建")) || (status.equals("等待核准")) || (status.equals("等待批准"))) {
        //          attr = new String[] { "LOCATION", "DESCRIPTION", "PHONE", "S_GZTJ", "WORKSITE",
        //            "S_ORDERTYPE", "S_PROFESSION", "TEAMNAME", "S_WWZY", "S_JIZU",
        //            "BZWONUM", "OPLOGTYPE", "SCHEDSTART", "SCHEDFINISH", "S_REPWONUM",
        //            "S_WORKCY", "S_WORKCYQTY" };
        //          rqattr = new String[] { "LOCATION", "DESCRIPTION", "S_PROFESSION", "WORKSITE",
        //            "TEAMNAME", "S_WWZY", "S_JIZU", "SCHEDSTART", "SCHEDFINISH" };
        //          mbo.setFieldFlag(attr, 7L, false);
        //          mbo.setFieldFlag(rqattr, 128L, true);
        //        }
        //        if ((status.equals("退回修改")) || (status.equals("退回重新修改"))) {
        //          attr = new String[] { "LOCATION", "DESCRIPTION", "PHONE", "S_GZTJ", "WORKSITE",
        //            "S_ORDERTYPE", "S_PROFESSION", "TEAMNAME", "S_WWZY", "S_JIZU",
        //            "BZWONUM", "OPLOGTYPE", "SCHEDSTART", "SCHEDFINISH", "S_REPWONUM",
        //            "S_WORKCY", "S_WORKCYQTY" };
        //          rqattr = new String[] { "LOCATION", "DESCRIPTION", "S_PROFESSION", "WORKSITE",
        //            "S_ORDERTYPE", "TEAMNAME", "S_WWZY", "S_JIZU", "SCHEDSTART", "SCHEDFINISH" };
        //          mbo.setFieldFlag(attr, 7L, false);
        //          mbo.setFieldFlag(rqattr, 128L, true);
        //        }
        //      }
        else {
            if ((status.equals("新建")) || (status.equals("等待核准")) || (status.equals("等待批准"))) {
                attr = new String[] { "DESCRIPTION", "PHONE", "S_GZTJ", "WORKSITE",
                        "S_ORDERTYPE", "BZWONUM", "OPLOGTYPE",
                        "S_WORKCY", "S_WORKCYQTY" };
                rqattr = new String[] { "DESCRIPTION", "WORKSITE" };
                mbo.setFieldFlag(attr, 7L, false);
                mbo.setFieldFlag(rqattr, 128L, true);
            }
            if ((status.equals("退回修改")) || (status.equals("退回重新修改"))) {
                attr = new String[] { "DESCRIPTION", "PHONE", "S_GZTJ", "WORKSITE",
                        "S_ORDERTYPE",
                        "BZWONUM", "OPLOGTYPE", "SCHEDSTART", "SCHEDFINISH",
                        "S_WORKCY", "S_WORKCYQTY" };
                rqattr = new String[] { "DESCRIPTION", "WORKSITE",
                        "S_ORDERTYPE",  "SCHEDSTART", "SCHEDFINISH" };
                mbo.setFieldFlag(attr, 7L, false);
                mbo.setFieldFlag(rqattr, 128L, true);
            }
        }
        if ((status.equals("关闭")) || (status.equals("已关闭")) ||
                (status.equals("已作废"))) {
            mbo.setFlag(7L, true);
        }


        super.setCurrentRecordData(mbo);
    }

    public MboSetRemote hasEvaluateAuth()
            throws RemoteException, MXException
    {
        MboRemote mbo = getMbo();
        String personid = mbo.getUserInfo().getPersonId();
        String profession = mbo.getString("S_PROFESSION");

        String sql = " department ='技术支持部' and personid='" + personid + "'";

        return mbo.getMboSet("$person", "person", sql);
    }

    @Override
    public int SAVE()
            throws MXException, RemoteException
    {
        if (!(hasSaveAuth())) {
            throw new MXApplicationException("工作票", "当前用户没有操作权限！");
        }
        int i = super.SAVE();
        saveTagout();
        return i;
    }

    private void saveTagout()
            throws MXException, RemoteException
    {
        String ORDERTYPE = getMbo().getString("S_ORDERTYPE");
        MboSetRemote tagouts = getMbo().getMboSet("$tagout", "tagout");
        MboSetRemote wosafetylinks = getMbo().getMboSet("COPYWOSAFETYLINKS");
        MboSetRemote wotagouts = getMbo().getMboSet("wotagout");
        MboSetRemote tagoutSet = null;
        int i;
        MboRemote wosafetylink;
        String wotagoutid;
        MboRemote tagout;
        if (ORDERTYPE.equals("电气一种工作票")) {
            DataBean LZ_DQ1WX001 = this.app.getDataBean("LZ_DQ1WX001");
            DataBean LZ_DQ1WX001_1 = this.app.getDataBean("LZ_DQ1WX001_1");
            DataBean LZ_DQ1WX002 = this.app.getDataBean("LZ_DQ1WX002");
            DataBean LZ_DQ1WX002_1 = this.app.getDataBean("LZ_DQ1WX002_1");
            DataBean LZ_DQ1WX003 = this.app.getDataBean("LZ_DQ1WX003");
            DataBean LZ_DQ1WX003_1 = this.app.getDataBean("LZ_DQ1WX003_1");
            DataBean LZ_DQ1WX004 = this.app.getDataBean("LZ_DQ1WX004");

            if (LZ_DQ1WX001 == null) {
                return;
            }
            if (LZ_DQ1WX002 == null) {
                return;
            }
            if (LZ_DQ1WX003 == null) {
                return;
            }
            if (LZ_DQ1WX004 == null) {
                return;
            }
            if (LZ_DQ1WX001_1 == null) {
                return;
            }
            if (LZ_DQ1WX002_1 == null) {
                return;
            }
            if (LZ_DQ1WX003_1 == null) {
                return;
            }
            for (i = 0; i < LZ_DQ1WX001.count(); ++i) {
                wosafetylink = LZ_DQ1WX001.getMbo(i);

                if (wosafetylink.toBeDeleted()) {
                    continue;
                }
                if (wosafetylink.getString("tagoutdescription").length() == 0) {
                    throw new MXApplicationException("workorder",
                            "隔离描述不能为空");
                }
                wotagoutid = wosafetylink.getString("tagoutid");
                tagoutSet = wosafetylink.getMboSet("$tagout", "tagout", "tagoutid='" + wotagoutid + "'");
                if (tagoutSet.count() == 0) {
                    tagout = tagouts.add();
                    copyAttrsFromWOSLTagoutToTagout(tagout, wosafetylink);
                }

                tagoutSet.close();
            }
            for (i = 0; i < LZ_DQ1WX002.count(); ++i) {
                wosafetylink = LZ_DQ1WX002.getMbo(i);

                if (wosafetylink.toBeDeleted()) {
                    continue;
                }
                if (wosafetylink.getString("tagoutdescription").length() == 0) {
                    throw new MXApplicationException("workorder",
                            "隔离描述不能为空");
                }
                wotagoutid = wosafetylink.getString("tagoutid");
                tagoutSet = wosafetylink.getMboSet("$tagout", "tagout", "tagoutid='" + wotagoutid + "'");
                if (tagoutSet.count() == 0) {
                    tagout = tagouts.add();
                    copyAttrsFromWOSLTagoutToTagout(tagout, wosafetylink);
                }

                tagoutSet.close();
            }
            for (i = 0; i < LZ_DQ1WX003.count(); ++i) {
                wosafetylink = LZ_DQ1WX003.getMbo(i);

                if (wosafetylink.toBeDeleted()) {
                    continue;
                }
                if (wosafetylink.getString("tagoutdescription").length() == 0) {
                    throw new MXApplicationException("workorder",
                            "隔离描述不能为空");
                }
                wotagoutid = wosafetylink.getString("tagoutid");
                tagoutSet = wosafetylink.getMboSet("$tagout", "tagout", "tagoutid='" + wotagoutid + "'");
                if (tagoutSet.count() == 0) {
                    tagout = tagouts.add();
                    copyAttrsFromWOSLTagoutToTagout(tagout, wosafetylink);
                }

                tagoutSet.close();
            }
            for (i = 0; i < LZ_DQ1WX004.count(); ++i) {
                wosafetylink = LZ_DQ1WX004.getMbo(i);

                if (wosafetylink.toBeDeleted()) {
                    continue;
                }
                if (wosafetylink.getString("tagoutdescription").length() == 0) {
                    throw new MXApplicationException("workorder",
                            "隔离描述不能为空");
                }
                wotagoutid = wosafetylink.getString("tagoutid");
                tagoutSet = wosafetylink.getMboSet("$tagout", "tagout", "tagoutid='" + wotagoutid + "'");
                if (tagoutSet.count() == 0) {
                    tagout = tagouts.add();
                    copyAttrsFromWOSLTagoutToTagout(tagout, wosafetylink);
                }

                tagoutSet.close();
            }
            for (i = 0; i < LZ_DQ1WX001_1.count(); ++i) {
                wosafetylink = LZ_DQ1WX001_1.getMbo(i);

                if (wosafetylink.toBeDeleted()) {
                    continue;
                }
                if (wosafetylink.getString("tagoutdescription").length() == 0) {
                    throw new MXApplicationException("workorder",
                            "隔离描述不能为空");
                }
                wotagoutid = wosafetylink.getString("tagoutid");
                tagoutSet = wosafetylink.getMboSet("$tagout", "tagout", "tagoutid='" + wotagoutid + "'");
                if (tagoutSet.count() == 0) {
                    tagout = tagouts.add();
                    copyAttrsFromWOSLTagoutToTagout(tagout, wosafetylink);
                }

                tagoutSet.close();
            }
            for (i = 0; i < LZ_DQ1WX002_1.count(); ++i) {
                wosafetylink = LZ_DQ1WX002_1.getMbo(i);

                if (wosafetylink.toBeDeleted()) {
                    continue;
                }
                if (wosafetylink.getString("tagoutdescription").length() == 0) {
                    throw new MXApplicationException("workorder",
                            "隔离描述不能为空");
                }
                wotagoutid = wosafetylink.getString("tagoutid");
                tagoutSet = wosafetylink.getMboSet("$tagout", "tagout", "tagoutid='" + wotagoutid + "'");
                if (tagoutSet.count() == 0) {
                    tagout = tagouts.add();
                    copyAttrsFromWOSLTagoutToTagout(tagout, wosafetylink);
                }

                tagoutSet.close();
            }
            for (i = 0; i < LZ_DQ1WX003_1.count(); ++i) {
                wosafetylink = LZ_DQ1WX003_1.getMbo(i);

                if (wosafetylink.toBeDeleted()) {
                    continue;
                }
                if (wosafetylink.getString("tagoutdescription").length() == 0) {
                    throw new MXApplicationException("workorder",
                            "隔离描述不能为空");
                }
                wotagoutid = wosafetylink.getString("tagoutid");
                tagoutSet = wosafetylink.getMboSet("$tagout", "tagout", "tagoutid='" + wotagoutid + "'");
                if (tagoutSet.count() == 0) {
                    tagout = tagouts.add();
                    copyAttrsFromWOSLTagoutToTagout(tagout, wosafetylink);
                }

                tagoutSet.close();
            }
        }
        else
        {
            int j;
            MboRemote wosafetylink1;
            String wotagoutid1;
            MboRemote tagout1;
            if (ORDERTYPE.equals("电气二种工作票")) {
                DataBean LZ_DQ2WX001 = this.app.getDataBean("LZ_DQ2WX001");
                DataBean LZ_DQ2WX002 = this.app.getDataBean("LZ_DQ2WX002");
                if (LZ_DQ2WX001 == null) {
                    return;
                }
                if (LZ_DQ2WX002 == null) {
                    return;
                }
                for (j = 0; j < LZ_DQ2WX001.count(); ++j) {
                    wosafetylink1 = LZ_DQ2WX001.getMbo(j);

                    if (wosafetylink1.toBeDeleted()) {
                        continue;
                    }
                    if (wosafetylink1.getString("tagoutdescription").length() == 0) {
                        throw new MXApplicationException("workorder",
                                "隔离描述不能为空");
                    }
                    wotagoutid = wosafetylink1.getString("tagoutid");
                    tagoutSet = wosafetylink1.getMboSet("$tagout", "tagout", "tagoutid='" + wotagoutid + "'");
                    if (tagoutSet.count() == 0) {
                        tagout1 = tagouts.add();
                        copyAttrsFromWOSLTagoutToTagout(tagout1, wosafetylink1);
                    }

                    tagoutSet.close();
                }
                for (i = 0; i < LZ_DQ2WX002.count(); ++i) {
                    wosafetylink1 = LZ_DQ2WX002.getMbo(i);

                    if (wosafetylink1.toBeDeleted()) {
                        continue;
                    }
                    if (wosafetylink1.getString("tagoutdescription").length() == 0) {
                        throw new MXApplicationException("workorder",
                                "隔离描述不能为空");
                    }
                    wotagoutid = wosafetylink1.getString("tagoutid");
                    tagoutSet = wosafetylink1.getMboSet("$tagout", "tagout", "tagoutid='" + wotagoutid + "'");
                    if (tagoutSet.count() == 0) {
                        tagout1 = tagouts.add();
                        copyAttrsFromWOSLTagoutToTagout(tagout1, wosafetylink1);
                    }

                    tagoutSet.close();
                }
            } else if (ORDERTYPE.equals("热控第一种工作票") || ORDERTYPE.equals("热控第二种工作票")) {

                DataBean LZ_RKWX001 = this.app.getDataBean("LZ_RKWX001");
                DataBean LZ_RKWX002 = this.app.getDataBean("LZ_RKWX002");
                DataBean LZ_RKWX003 = this.app.getDataBean("LZ_RKWX003");
                //        DataBean LZ_RKWX004 = this.app.getDataBean("LZ_RKWX004");
                if (LZ_RKWX001 == null) {
                    return;
                }
                if (LZ_RKWX002 == null) {
                    return;
                }
                if (LZ_RKWX003 == null) {
                    return;
                }
                //        if (LZ_RKWX004 == null) {
                //          return;
                //        }
                for (int k = 0; k < LZ_RKWX001.count(); ++k) {
                    tagout1 = LZ_RKWX001.getMbo(k);

                    if (tagout1.toBeDeleted()) {
                        continue;
                    }
                    if (tagout1.getString("tagoutdescription").length() == 0) {
                        throw new MXApplicationException("workorder",
                                "隔离描述不能为空");
                    }
                    wotagoutid = tagout1.getString("tagoutid");
                    tagoutSet = tagout1.getMboSet("$tagout", "tagout", "tagoutid='" + wotagoutid + "'");
                    if (tagoutSet.count() == 0) {
                        tagout1 = tagouts.add();
                        copyAttrsFromWOSLTagoutToTagout(tagout1, tagout1);
                    }

                    tagoutSet.close();
                }
                for (int n = 0; n < LZ_RKWX002.count(); ++n) {
                    tagout1 = LZ_RKWX002.getMbo(n);

                    if (tagout1.toBeDeleted()) {
                        continue;
                    }
                    if (tagout1.getString("tagoutdescription").length() == 0) {
                        throw new MXApplicationException("workorder",
                                "隔离描述不能为空");
                    }
                    wotagoutid = tagout1.getString("tagoutid");
                    tagoutSet = tagout1.getMboSet("$tagout", "tagout", "tagoutid='" + wotagoutid + "'");
                    if (tagoutSet.count() == 0) {
                        tagout1 = tagouts.add();
                        copyAttrsFromWOSLTagoutToTagout(tagout1, tagout1);
                    }

                    tagoutSet.close();
                }
                for (int a= 0; a < LZ_RKWX003.count(); ++a) {
                    tagout1 = LZ_RKWX003.getMbo(a);

                    if (tagout1.toBeDeleted()) {
                        continue;
                    }
                    if (tagout1.getString("tagoutdescription").length() == 0) {
                        throw new MXApplicationException("workorder",
                                "隔离描述不能为空");
                    }
                    wotagoutid = tagout1.getString("tagoutid");
                    tagoutSet = tagout1.getMboSet("$tagout", "tagout", "tagoutid='" + wotagoutid + "'");
                    if (tagoutSet.count() == 0) {
                        tagout1 = tagouts.add();
                        copyAttrsFromWOSLTagoutToTagout(tagout1, tagout1);
                    }

                    tagoutSet.close();
                }
                //        for (int b = 0; b < LZ_RKWX004.count(); ++b) {
                //          tagout1 = LZ_RKWX004.getMbo(b);
                //
                //          if (tagout1.toBeDeleted()) {
                //            continue;
                //          }
                //          if (tagout1.getString("tagoutdescription").length() == 0) {
                //            throw new MXApplicationException("workorder",
                //              "隔离描述不能为空");
                //          }
                //          wotagoutid = tagout1.getString("tagoutid");
                //          tagoutSet = tagout1.getMboSet("$tagout", "tagout", "tagoutid='" + wotagoutid + "'");
                //          if (tagoutSet.count() == 0) {
                //            tagout1 = tagouts.add();
                //            copyAttrsFromWOSLTagoutToTagout(tagout1, tagout1);
                //          }
                //
                //          tagoutSet.close();
                //        }
            } else if (ORDERTYPE.equals("热力机械第一种工作票") || ORDERTYPE.equals("热力机械第二种工作票")) {
                DataBean LZ_RJWX001 = this.app.getDataBean("LZ_RJWX001");
                DataBean LZ_RJWX002 = this.app.getDataBean("LZ_RJWX002");
                DataBean LZ_RJWX003 = this.app.getDataBean("LZ_RJWX003");
                DataBean LZ_RJWX004 = this.app.getDataBean("LZ_RJWX004");
                DataBean LZ_RJWX005 = this.app.getDataBean("LZ_RJWX005");
                //        DataBean LZ_RJWX006 = this.app.getDataBean("LZ_RJWX006");
                //        DataBean LZ_RJWX007 = this.app.getDataBean("LZ_RJWX007");
                //        DataBean LZ_RJWX008 = this.app.getDataBean("LZ_RJWX008");
                if (LZ_RJWX001 == null) {
                    return;
                }
                if (LZ_RJWX002 == null) {
                    return;
                }
                if (LZ_RJWX003 == null) {
                    return;
                }
                if (LZ_RJWX004 == null) {
                    return;
                }
                if (LZ_RJWX005 == null) {
                    return;
                }
                //        if (LZ_RJWX006 == null) {
                //          return;
                //        }
                //        if (LZ_RJWX007 == null) {
                //          return;
                //        }
                //        if (LZ_RJWX008 == null)
                //          return;
                MboRemote wosafetylink2;
                String wotagoutid2;
                MboRemote tagout2;
                for (int c = 0; c < LZ_RJWX001.count(); ++c) {
                    wosafetylink2 = LZ_RJWX001.getMbo(c);

                    if (wosafetylink2.toBeDeleted()) {
                        continue;
                    }
                    if (wosafetylink2.getString("tagoutdescription").length() == 0) {
                        throw new MXApplicationException("workorder",
                                "隔离描述不能为空");
                    }
                    wotagoutid = wosafetylink2.getString("tagoutid");
                    tagoutSet = wosafetylink2.getMboSet("$tagout", "tagout", "tagoutid='" + wotagoutid + "'");
                    if (tagoutSet.count() == 0) {
                        tagout = tagouts.add();
                        copyAttrsFromWOSLTagoutToTagout(tagout, wosafetylink2);
                    }

                    tagoutSet.close();
                }
                for (i = 0; i < LZ_RJWX002.count(); ++i) {
                    wosafetylink2 = LZ_RJWX002.getMbo(i);

                    if (wosafetylink2.toBeDeleted()) {
                        continue;
                    }
                    if (wosafetylink2.getString("tagoutdescription").length() == 0) {
                        throw new MXApplicationException("workorder",
                                "隔离描述不能为空");
                    }
                    wotagoutid = wosafetylink2.getString("tagoutid");
                    tagoutSet = wosafetylink2.getMboSet("$tagout", "tagout", "tagoutid='" + wotagoutid + "'");
                    if (tagoutSet.count() == 0) {
                        tagout = tagouts.add();
                        copyAttrsFromWOSLTagoutToTagout(tagout, wosafetylink2);
                    }

                    tagoutSet.close();
                }
                for (i = 0; i < LZ_RJWX003.count(); ++i) {
                    wosafetylink2 = LZ_RJWX003.getMbo(i);

                    if (wosafetylink2.toBeDeleted()) {
                        continue;
                    }
                    if (wosafetylink2.getString("tagoutdescription").length() == 0) {
                        throw new MXApplicationException("workorder",
                                "隔离描述不能为空");
                    }
                    wotagoutid = wosafetylink2.getString("tagoutid");
                    tagoutSet = wosafetylink2.getMboSet("$tagout", "tagout", "tagoutid='" + wotagoutid + "'");
                    if (tagoutSet.count() == 0) {
                        tagout = tagouts.add();
                        copyAttrsFromWOSLTagoutToTagout(tagout, wosafetylink2);
                    }

                    tagoutSet.close();
                }
                for (i = 0; i < LZ_RJWX004.count(); ++i) {
                    wosafetylink2 = LZ_RJWX004.getMbo(i);

                    if (wosafetylink2.toBeDeleted()) {
                        continue;
                    }
                    if (wosafetylink2.getString("tagoutdescription").length() == 0) {
                        throw new MXApplicationException("workorder",
                                "隔离描述不能为空");
                    }
                    wotagoutid = wosafetylink2.getString("tagoutid");
                    tagoutSet = wosafetylink2.getMboSet("$tagout", "tagout", "tagoutid='" + wotagoutid + "'");
                    if (tagoutSet.count() == 0) {
                        tagout = tagouts.add();
                        copyAttrsFromWOSLTagoutToTagout(tagout, wosafetylink2);
                    }

                    tagoutSet.close();
                }
                for (i = 0; i < LZ_RJWX005.count(); ++i) {
                    wosafetylink2 = LZ_RJWX005.getMbo(i);

                    if (wosafetylink2.toBeDeleted()) {
                        continue;
                    }
                    if (wosafetylink2.getString("tagoutdescription").length() == 0) {
                        throw new MXApplicationException("workorder",
                                "隔离描述不能为空");
                    }
                    wotagoutid = wosafetylink2.getString("tagoutid");
                    tagoutSet = wosafetylink2.getMboSet("$tagout", "tagout", "tagoutid='" + wotagoutid + "'");
                    if (tagoutSet.count() == 0) {
                        tagout = tagouts.add();
                        copyAttrsFromWOSLTagoutToTagout(tagout, wosafetylink2);
                    }

                    tagoutSet.close();
                }
                //        for (i = 0; i < LZ_RJWX006.count(); ++i) {
                //          wosafetylink2 = LZ_RJWX006.getMbo(i);
                //
                //          if (wosafetylink2.toBeDeleted()) {
                //            continue;
                //          }
                //          if (wosafetylink2.getString("tagoutdescription").length() == 0) {
                //            throw new MXApplicationException("workorder",
                //              "隔离描述不能为空");
                //          }
                //          wotagoutid = wosafetylink2.getString("tagoutid");
                //          tagoutSet = wosafetylink2.getMboSet("$tagout", "tagout", "tagoutid='" + wotagoutid + "'");
                //          if (tagoutSet.count() == 0) {
                //            tagout = tagouts.add();
                //            copyAttrsFromWOSLTagoutToTagout(tagout, wosafetylink2);
                //          }
                //
                //          tagoutSet.close();
                //        }
                //        for (i = 0; i < LZ_RJWX007.count(); ++i) {
                //          wosafetylink2 = LZ_RJWX007.getMbo(i);
                //
                //          if (wosafetylink2.toBeDeleted()) {
                //            continue;
                //          }
                //          if (wosafetylink2.getString("tagoutdescription").length() == 0) {
                //            throw new MXApplicationException("workorder",
                //              "隔离描述不能为空");
                //          }
                //          wotagoutid = wosafetylink2.getString("tagoutid");
                //          tagoutSet = wosafetylink2.getMboSet("$tagout", "tagout", "tagoutid='" + wotagoutid + "'");
                //          if (tagoutSet.count() == 0) {
                //            tagout = tagouts.add();
                //            copyAttrsFromWOSLTagoutToTagout(tagout, wosafetylink2);
                //          }
                //
                //          tagoutSet.close();
                //        }
                //        for (i = 0; i < LZ_RJWX008.count(); ++i) {
                //          wosafetylink2 = LZ_RJWX008.getMbo(i);
                //
                //          if (wosafetylink2.toBeDeleted()) {
                //            continue;
                //          }
                //          if (wosafetylink2.getString("tagoutdescription").length() == 0) {
                //            throw new MXApplicationException("workorder",
                //              "隔离描述不能为空");
                //          }
                //          wotagoutid = wosafetylink2.getString("tagoutid");
                //          tagoutSet = wosafetylink2.getMboSet("$tagout", "tagout", "tagoutid='" + wotagoutid + "'");
                //          if (tagoutSet.count() == 0) {
                //            tagout = tagouts.add();
                //            copyAttrsFromWOSLTagoutToTagout(tagout, wosafetylink2);
                //          }
                //
                //          tagoutSet.close();
                //        }
            }
            else if (ORDERTYPE.equals("一级动火工作票")) {
                DataBean LZ_DH1WX001 = this.app.getDataBean("LZ_DH1WX001");
                DataBean LZ_DH1WX002 = this.app.getDataBean("LZ_DH1WX002");
                if (LZ_DH1WX001 == null) {
                    return;
                }
                if (LZ_DH1WX002 == null) {
                    return;
                }
                for (i = 0; i < LZ_DH1WX001.count(); ++i) {
                    wosafetylink = LZ_DH1WX001.getMbo(i);

                    if (wosafetylink.toBeDeleted()) {
                        continue;
                    }
                    if (wosafetylink.getString("tagoutdescription").length() == 0) {
                        throw new MXApplicationException("workorder",
                                "隔离描述不能为空");
                    }
                    wotagoutid = wosafetylink.getString("tagoutid");
                    tagoutSet = wosafetylink.getMboSet("$tagout", "tagout", "tagoutid='" + wotagoutid + "'");
                    if (tagoutSet.count() == 0) {
                        tagout = tagouts.add();
                        copyAttrsFromWOSLTagoutToTagout(tagout, wosafetylink);
                    }

                    tagoutSet.close();
                }
                for (i = 0; i < LZ_DH1WX002.count(); ++i) {
                    wosafetylink = LZ_DH1WX002.getMbo(i);

                    if (wosafetylink.toBeDeleted()) {
                        continue;
                    }
                    if (wosafetylink.getString("tagoutdescription").length() == 0) {
                        throw new MXApplicationException("workorder",
                                "隔离描述不能为空");
                    }
                    wotagoutid = wosafetylink.getString("tagoutid");
                    tagoutSet = wosafetylink.getMboSet("$tagout", "tagout", "tagoutid='" + wotagoutid + "'");
                    if (tagoutSet.count() == 0) {
                        tagout = tagouts.add();
                        copyAttrsFromWOSLTagoutToTagout(tagout, wosafetylink);
                    }

                    tagoutSet.close();
                }
            }else if (ORDERTYPE.equals("二级动火工作票")) {
                DataBean LZ_DH2WX001 = this.app.getDataBean("LZ_DH2WX001");
                DataBean LZ_DH2WX002 = this.app.getDataBean("LZ_DH2WX002");
                if (LZ_DH2WX001 == null) {
                    return;
                }
                if (LZ_DH2WX002 == null) {
                    return;
                }
                for (i = 0; i < LZ_DH2WX001.count(); ++i) {
                    wosafetylink = LZ_DH2WX001.getMbo(i);

                    if (wosafetylink.toBeDeleted()) {
                        continue;
                    }
                    if (wosafetylink.getString("tagoutdescription").length() == 0) {
                        throw new MXApplicationException("workorder",
                                "隔离描述不能为空");
                    }
                    wotagoutid = wosafetylink.getString("tagoutid");
                    tagoutSet = wosafetylink.getMboSet("$tagout", "tagout", "tagoutid='" + wotagoutid + "'");
                    if (tagoutSet.count() == 0) {
                        tagout = tagouts.add();
                        copyAttrsFromWOSLTagoutToTagout(tagout, wosafetylink);
                    }

                    tagoutSet.close();
                }
                for (i = 0; i < LZ_DH2WX002.count(); ++i) {
                    wosafetylink = LZ_DH2WX002.getMbo(i);

                    if (wosafetylink.toBeDeleted()) {
                        continue;
                    }
                    if (wosafetylink.getString("tagoutdescription").length() == 0) {
                        throw new MXApplicationException("workorder",
                                "隔离描述不能为空");
                    }
                    wotagoutid = wosafetylink.getString("tagoutid");
                    tagoutSet = wosafetylink.getMboSet("$tagout", "tagout", "tagoutid='" + wotagoutid + "'");
                    if (tagoutSet.count() == 0) {
                        tagout = tagouts.add();
                        copyAttrsFromWOSLTagoutToTagout(tagout, wosafetylink);
                    }

                    tagoutSet.close();
                }
            } else if (ORDERTYPE.equals("工作任务单")) {
                DataBean databean = this.app.getDataBean("14664346402822");
                if (databean == null) {
                    return;
                }
                for (int c = 0; c < databean.count(); ++c) {
                    MboRemote wosafetylink3 = databean.getMbo(c);

                    if (wosafetylink3.toBeDeleted()) {
                        continue;
                    }
                    if (wosafetylink3.getString("tagoutdescription").length() == 0) {
                        throw new MXApplicationException("workorder",
                                "隔离描述不能为空");
                    }
                    String wotagoutid3 = wosafetylink3.getString("tagoutid");
                    tagoutSet = wosafetylink3.getMboSet("$tagout", "tagout", "tagoutid='" + wotagoutid3 + "'");
                    if (tagoutSet.count() == 0) {
                        MboRemote tagout3 = tagouts.add();
                        copyAttrsFromWOSLTagoutToTagout(tagout3, wosafetylink3);
                    }

                    tagoutSet.close();
                }
            }
        }

        tagouts.save();
        wotagouts.save();
        wosafetylinks.save();
    }

    public void copyAttrsFromWOSLTagoutToTagout(MboRemote tagout, MboRemote wosltagout) throws MXException, RemoteException
    {
        String[] tagoutAttrs = { "description", "location", "assetnum",
                "requiredstate", "TAG01", "TAG02", "TAG03", "TAG04", "TAG05",
                "TAG06", "TAG07", "TAG08", "TAGOUTID" };

        String[] wosltagoutAttrs = { "tagoutdescription", "location",
                "assetnum", "requiredstate", "TAG01", "TAG02", "TAG03",
                "TAG04", "TAG05", "TAG06", "TAG07", "TAG08", "TAGOUTID" };

        for (int i = 0; i < tagoutAttrs.length; ++i)
        {
            tagout.setValue(tagoutAttrs[i],
                    wosltagout.getString(wosltagoutAttrs[i]), 11L);
        }

    }

    @Override
    public int ROUTEWF()
            throws MXException, RemoteException
    {
        this.app.getAppBean().save();
        this.app.getAppBean().refreshTable();
        if (!(hasRoutewfAuth())) {
            throw new MXApplicationException("hasnoAuth", "hasnoAuth");
        }
        return super.ROUTEWF();
    }

    public boolean isMaxadmin()
            throws RemoteException, MXException
    {
        String personid = getMXSession().getUserInfo().getPersonId();
        System.out.println(personid);

        return (personid.equalsIgnoreCase("maxadmin"));
    }

    public boolean hasSaveAuth()
            throws RemoteException, MXException
    {
        MboRemote mbo = getMbo();
        long id = mbo.getUniqueIDValue();
        String tableName = mbo.getName().toUpperCase();
        String personid = mbo.getUserInfo().getPersonId();
        String status = mbo.getString("STATUS");
        String S_CREATEPERSON = mbo.getString("S_CREATEPERSON");

        if (personid.equalsIgnoreCase("maxadmin")) {
            return true;
        }

        if ((((status.equals("新建")) || (status.equals("等待批准")) || (status.equals("等待核准")))) && (personid.equals(S_CREATEPERSON))) {
            return true;
        }

        MboSetRemote mbosetremote = mbo.getMboSet("$instance", "WFINSTANCE",
                "ownertable='" + tableName + "' and ownerid='" + id + "' and active = 1");
        if (!(mbosetremote.isEmpty())) {
            String sql = "ownerid='" + id + "' and ownertable='" + tableName +
                    "' and assignstatus='活动' and assigncode='" + personid + "'";
            MboSetRemote mbosetremote1 = getMbo().getMboSet("$assigncode", "WFASSIGNMENT", sql);
            return (!(mbosetremote1.isEmpty()));
        }
        return false;
    }

    public boolean hasRoutewfAuth()
            throws MXException, RemoteException
    {
        MboRemote mbo = getMbo();
        long id = mbo.getUniqueIDValue();
        String tableName = mbo.getName().toUpperCase();
        String personid = mbo.getUserInfo().getPersonId();
        String status = mbo.getString("STATUS");
        String S_CREATEPERSON = mbo.getString("S_CREATEPERSON");

        if (personid.equalsIgnoreCase("maxadmin")) {
            return true;
        }

        if ((((status.equals("新建")) || (status.equals("等待批准")) || (status.equals("等待核准")) || (status.equals("退回修改")))) && (personid.equals(S_CREATEPERSON))) {
            return true;
        }

        MboSetRemote mbosetremote = mbo.getMboSet("$instance", "WFINSTANCE",
                "ownertable='" + tableName + "' and ownerid='" + id + "' and active = 1");
        if (!(mbosetremote.isEmpty())) {
            String sql = "ownerid='" + id + "' and ownertable='" + tableName +
                    "' and assignstatus='活动' and assigncode='" + personid + "'";
            MboSetRemote mbosetremote1 = getMbo().getMboSet("$assigncode", "WFASSIGNMENT", sql);
            return (!(mbosetremote1.isEmpty()));
        }
        return false;
    }

    // 创建临时用电申请单
    public void addlsyd() throws RemoteException, MXException {
        app.getAppBean().save();
        // 创建一个许可证对象
        MboRemote syMbo = addXkzMbo("LICENCEMAIN7");
        // 设置许可证的类型
        syMbo.setValue("type", "1007");
    }

    // 创建起吊作业许可证
    public void addqdxkz() throws RemoteException, MXException {
        app.getAppBean().save();
        // 创建一个许可证对象
        MboRemote syMbo = addXkzMbo("LICENCEMAIN4");
        // 设置许可证的类型
        syMbo.setValue("type", "1004");

    }

    /**
     * 创建高处作业许可证
     *
     * @throws RemoteException
     * @throws MXException
     */
    public void addgcxkz() throws RemoteException, MXException {
        app.getAppBean().save();

        // 创建一个许可证对象
        MboRemote syMbo = addXkzMbo("LICENCEMAIN2");
        // 设置许可证的类型
        syMbo.setValue("type", "1002");
    }

    // 创建密闭空间作业许可证
    public void addsyxkz() throws RemoteException, MXException {
        app.getAppBean().save();

        // 创建一个许可证对象
        MboRemote syMbo = addXkzMbo("LICENCEMAIN");

        // 设置许可证的类型
        syMbo.setValue("type", "1001");

    }

    // 创建受限空间危害识别单
    public void addsxkjwxd() throws RemoteException, MXException {
        app.getAppBean().save();

        // 创建一个许可证对象
        MboRemote syMbo = addXkzMbo("LICENCEMAIN10");

        // 设置许可证的类型
        syMbo.setValue("type", "1010");
        String licenceid = syMbo.getString("LICENCEMAINID");


        //创建作业类型对象
        MboSetRemote mboSet = syMbo.getMboSet("WORKTYPE");
        mboSet.reset();
        MboRemote workMbo = mboSet.add();
        workMbo.setValue("PARENT",licenceid);
        workMbo.setValue("TYPE","作业类型");
        //创建危害识别对象
        MboSetRemote mboSet2 = syMbo.getMboSet("WORKTYPE2");
        mboSet2.reset();
        MboRemote harmMbo = mboSet2.add();
        harmMbo.setValue("PARENT",licenceid);
        harmMbo.setValue("TYPE","危害识别");
        //创建技术措施对象
        MboSetRemote mboSet3 = syMbo.getMboSet("WORKTYPE3");
        mboSet3.reset();
        MboRemote skillMbo = mboSet3.add();
        skillMbo.setValue("PARENT",licenceid);
        skillMbo.setValue("TYPE","技术措施");
        //创建预防措施对象
        MboSetRemote mboSet4 = syMbo.getMboSet("WORKTYPE4");
        mboSet4.reset();
        MboRemote defendMbo = mboSet4.add();
        defendMbo.setValue("PARENT",licenceid);
        defendMbo.setValue("TYPE","预防措施");


    }

    /**
     * 创建一个许可证对象
     *
     * @return
     * @throws RemoteException
     * @throws MXException
     */
    public MboRemote addXkzMbo(String relationName) throws RemoteException, MXException {
        if (!hasExists(relationName)) {
            String xkzName = "";
            if ("LICENCEMAIN".equalsIgnoreCase(relationName)) {
                xkzName = "受限空间作业许可证";
            } else if ("LICENCEMAIN3".equalsIgnoreCase(relationName)) {
                xkzName = "挖掘作业许可证";
            } else if ("LICENCEMAIN4".equalsIgnoreCase(relationName)) {
                xkzName = "起吊作业许可证";
            } else if ("LICENCEMAIN5".equalsIgnoreCase(relationName)) {
                xkzName = "安健环技术交底检查卡";
            } else if ("LICENCEMAIN6".equalsIgnoreCase(relationName)) {
                xkzName = "检修箱使用申请单";
            } else if ("LICENCEMAIN7".equalsIgnoreCase(relationName)) {
                xkzName = "临时用电申请单";
            } else if ("LICENCEMAIN8".equalsIgnoreCase(relationName)) {
                xkzName = "压缩空气使用申请单";
            } else if ("LICENCEMAIN9".equalsIgnoreCase(relationName)) {
                xkzName = "设备试验试转申请单";
            } else if ("LICENCEMAIN1".equalsIgnoreCase(relationName)) {
                xkzName = "消防水使用申请单";
            } else if ("LICENCEMAIN10".equalsIgnoreCase(relationName)) {
                xkzName = "受限空间危害识别单";
            } else {
                xkzName = "高处作业许可证";
            }
            throw new MXApplicationException("xkz", "xkzexists", new String[]{xkzName});
        }
        // 得到主表对象
        MboRemote mainMbo = getMbo();
        // 得到工单号
        String wonum = mainMbo.getString("wonum");
        //得到位置
        String location = mainMbo.getString("LOCATION");
        // 得到受限许可证对象集
        MboSetRemote sySet = mainMbo.getMboSet(relationName);
        // 添加受限许可证对象
        MboRemote syMbo = sySet.add();
        // 设置关联的工单号
        syMbo.setValue("wonum", wonum);
        //设置位置
        syMbo.setValue("LOCATION", location);

        return syMbo;
    }

    private boolean hasExists(String relationName) throws RemoteException, MXException {
        MboRemote mainMbo = getMbo();
        MboSetRemote set = mainMbo.getMboSet(relationName);
        if (set.count() > 0) {
            return false;
        }
        return true;
    }

    /**
     * 发送临时用电工作流
     * @throws RemoteException
     * @throws MXException
     */
    public void lsydwf() throws RemoteException, MXException {
        WorkFlowServiceRemote wfsr = ((WorkFlowServiceRemote) MXServer.getMXServer().lookup("WORKFLOW"));
        MboRemote mainMbo = app.getAppBean().getMbo();
        MboSetRemote licSet = mainMbo.getMboSet("LICENCEMAIN7");
        MboRemote tmpMbo = null;
        if (!licSet.isEmpty()) {
            tmpMbo = licSet.getMbo(0);
        }
        wfsr.initiateWorkflow("EC_GZPFJ", tmpMbo);
        Utility.showMessageBox(this.clientSession.getCurrentEvent(), "Success", "临时用电申请流程发送成功. ", 1);
    }

    /**
     * 发送起吊作业工作流
     * @throws RemoteException
     * @throws MXException
     */
    public void qdzywf() throws RemoteException, MXException {
        WorkFlowServiceRemote wfsr = ((WorkFlowServiceRemote) MXServer.getMXServer().lookup("WORKFLOW"));
        MboRemote mainMbo = app.getAppBean().getMbo();
        MboSetRemote licSet = mainMbo.getMboSet("LICENCEMAIN4");
        MboRemote tmpMbo = null;
        if (!licSet.isEmpty()) {
            tmpMbo = licSet.getMbo(0);
        }
        wfsr.initiateWorkflow("EC_GZPFJ", tmpMbo);
        Utility.showMessageBox(this.clientSession.getCurrentEvent(), "Success", "起吊作业流程发送成功. ", 1);
    }

    /**
     * 登高（悬空）作业工作流
     * @throws RemoteException
     * @throws MXException
     */
    public void dgwf() throws RemoteException, MXException {
        WorkFlowServiceRemote wfsr = ((WorkFlowServiceRemote) MXServer.getMXServer().lookup("WORKFLOW"));
        MboRemote mainMbo = app.getAppBean().getMbo();
        MboSetRemote licSet = mainMbo.getMboSet("LICENCEMAIN2");
        MboRemote tmpMbo = null;
        if (!licSet.isEmpty()) {
            tmpMbo = licSet.getMbo(0);
        }
        wfsr.initiateWorkflow("EC_GZPFJ", tmpMbo);
        Utility.showMessageBox(this.clientSession.getCurrentEvent(), "Success", "登高（悬空）作业流程发送成功. ", 1);
    }

    /**
     * 密闭空间作业工作流
     * @throws RemoteException
     * @throws MXException
     */
    public void mbwf() throws RemoteException, MXException {
        WorkFlowServiceRemote wfsr = ((WorkFlowServiceRemote) MXServer.getMXServer().lookup("WORKFLOW"));
        MboRemote mainMbo = app.getAppBean().getMbo();
        MboSetRemote licSet = mainMbo.getMboSet("LICENCEMAIN");
        MboRemote tmpMbo = null;
        if (!licSet.isEmpty()) {
            tmpMbo = licSet.getMbo(0);
        }
        wfsr.initiateWorkflow("EC_GZPFJ", tmpMbo);
        Utility.showMessageBox(this.clientSession.getCurrentEvent(), "Success", "密闭空间作业流程发送成功. ", 1);
    }

    public static void main(String[] args) {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        NumberFormat nfs = NumberFormat.getPercentInstance();
        nf.format(2.4D);
        nfs.format(2.4D);
    }
}