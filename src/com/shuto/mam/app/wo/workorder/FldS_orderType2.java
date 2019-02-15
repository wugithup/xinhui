
package com.shuto.mam.app.wo.workorder;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class FldS_orderType2 extends MboValueAdapter {
    public FldS_orderType2(MboValue mbv) {
        super(mbv);
    }

    public void action() throws MXException, RemoteException {
        super.action();
        MboRemote mainMbo = this.getMboValue().getMbo();
        String location = "南京项目";
        MboSetRemote hazards = mainMbo.getMboSet("WOSLTAGENABLED");
        if (!hazards.isEmpty()) {
            for(int i = 0; i < hazards.count(); ++i) {
                hazards.getMbo(i).getMboSet("WOSAFETYLINKTAG").deleteAndRemoveAll();
            }

            hazards.deleteAndRemoveAll();
        }

        MboSetRemote acfl = mainMbo.getMboSet("WOSAFETYLINK");
        if (acfl.count() > 0) {
            MboSetRemote fl = mainMbo.getMboSet("WOHAZARD");
            MboSetRemote WOTAGOUT = mainMbo.getMboSet("WOTAGOUT");
            fl.deleteAll();
            fl.commit();
            fl.save();
            WOTAGOUT.deleteAll();
            WOTAGOUT.commit();
            WOTAGOUT.save();
            acfl.deleteAll();
            acfl.commit();
            acfl.save();
        }

        String odrertype = this.getMboValue().getString();
        String worktype = mainMbo.getString("worktype");
        MboRemote haz;
        if (odrertype.equals("电气一种票")) {
            haz = hazards.add();
            haz.setValue("hazardid", "LZ_DQ1WX001");
            haz.setValue("location", location);
            haz.setValue("applyseq", "1");
            haz = hazards.add();
            haz.setValue("hazardid", "LZ_DQ1WX002");
            haz.setValue("location", location);
            haz.setValue("applyseq", "2");
            haz = hazards.add();
            haz.setValue("hazardid", "LZ_DQ1WX003");
            haz.setValue("location", location);
            haz.setValue("applyseq", "3");
            haz = hazards.add();
            haz.setValue("hazardid", "LZ_DQ1WX004");
            haz.setValue("location", location);
            haz.setValue("applyseq", "4");
        } else if (odrertype.equals("电气二种票")) {
            haz = hazards.add();
            haz.setValue("hazardid", "LZ_DQ2WX001");
            haz.setValue("location", location);
            haz.setValue("applyseq", "1");
            haz = hazards.add();
            haz.setValue("hazardid", "LZ_DQ2WX002");
            haz.setValue("location", location);
            haz.setValue("applyseq", "2");
        } else if (odrertype.equals("热控票")) {
            haz = hazards.add();
            haz.setValue("hazardid", "LZ_RKWX001");
            haz.setValue("location", location);
            haz.setValue("applyseq", "1");
            haz = hazards.add();
            haz.setValue("hazardid", "LZ_RKWX002");
            haz.setValue("location", location);
            haz.setValue("applyseq", "2");
            haz = hazards.add();
            haz.setValue("hazardid", "LZ_RKWX003");
            haz.setValue("location", location);
            haz.setValue("applyseq", "3");
            haz.setValue("hazardid", "LZ_RKWX004");
            haz.setValue("location", location);
            haz.setValue("applyseq", "3");
        } else if (odrertype.equals("热力机械票")) {
            haz = hazards.add();
            haz.setValue("hazardid", "LZ_RJWX001");
            haz.setValue("location", location);
            haz.setValue("applyseq", "1");
            haz = hazards.add();
            haz.setValue("hazardid", "LZ_RJWX002");
            haz.setValue("location", location);
            haz.setValue("applyseq", "2");
            haz = hazards.add();
            haz.setValue("hazardid", "LZ_RJWX003");
            haz.setValue("location", location);
            haz.setValue("applyseq", "3");
            haz = hazards.add();
            haz.setValue("hazardid", "LZ_RJWX004");
            haz.setValue("location", location);
            haz.setValue("applyseq", "4");
            haz = hazards.add();
            haz.setValue("hazardid", "LZ_RJWX005");
            haz.setValue("location", location);
            haz.setValue("applyseq", "5");
            haz = hazards.add();
            haz.setValue("hazardid", "LZ_RJWX006");
            haz.setValue("location", location);
            haz.setValue("applyseq", "6");
            haz = hazards.add();
            haz.setValue("hazardid", "LZ_RJWX007");
            haz.setValue("location", location);
            haz.setValue("applyseq", "7");
            haz = hazards.add();
            haz.setValue("hazardid", "LZ_RJWX008");
            haz.setValue("location", location);
            haz.setValue("applyseq", "8");
        } else if (odrertype.equals("一级动火票")) {
            haz = hazards.add();
            haz.setValue("hazardid", "LZ_DH1WX001");
            haz.setValue("location", location);
            haz.setValue("applyseq", "1");
            haz = hazards.add();
            haz.setValue("hazardid", "LZ_DH1WX002");
            haz.setValue("location", location);
            haz.setValue("applyseq", "2");
        } else if (odrertype.equals("二级动火票")) {
            haz = hazards.add();
            haz.setValue("hazardid", "LZ_DH2WX001");
            haz.setValue("location", location);
            haz.setValue("applyseq", "1");
            haz = hazards.add();
            haz.setValue("hazardid", "LZ_DH2WX002");
            haz.setValue("location", location);
            haz.setValue("applyseq", "2");
        } else if (odrertype.equals("工作任务单")) {
            haz = hazards.add();
            haz.setValue("hazardid", "HZ_WX014");
            haz.setValue("location", location);
            haz.setValue("applyseq", "1");
        } else if (odrertype.equals("试运行许可证")) {
            haz = hazards.add();
            haz.setValue("hazardid", "HZ_WX015");
            haz.setValue("location", location);
            haz.setValue("applyseq", "1");
            haz = hazards.add();
            haz.setValue("hazardid", "HZ_WX016");
            haz.setValue("location", location);
            haz.setValue("applyseq", "2");
        } else if (odrertype.equals("高处作业")) {
            haz = hazards.add();
            haz.setValue("hazardid", "HZ_WX017");
            haz.setValue("location", location);
            haz.setValue("applyseq", "1");
            haz = hazards.add();
            haz.setValue("hazardid", "HZ_WX018");
            haz.setValue("location", location);
            haz.setValue("applyseq", "2");
            haz = hazards.add();
            haz.setValue("hazardid", "HZ_WX019");
            haz.setValue("location", location);
            haz.setValue("applyseq", "3");
            haz = hazards.add();
            haz.setValue("hazardid", "HZ_WX020");
            haz.setValue("location", location);
            haz.setValue("applyseq", "4");
            haz = hazards.add();
            haz.setValue("hazardid", "HZ_WX021");
            haz.setValue("location", location);
            haz.setValue("applyseq", "5");
            haz = hazards.add();
            haz.setValue("hazardid", "HZ_WX022");
            haz.setValue("location", location);
            haz.setValue("applyseq", "6");
            haz = hazards.add();
            haz.setValue("hazardid", "HZ_WX023");
            haz.setValue("location", location);
            haz.setValue("applyseq", "7");
            haz = hazards.add();
            haz.setValue("hazardid", "HZ_WX024");
            haz.setValue("location", location);
            haz.setValue("applyseq", "8");
            haz = hazards.add();
            haz.setValue("hazardid", "HZ_WX025");
            haz.setValue("location", location);
            haz.setValue("applyseq", "9");
        }

        if (!"试运行许可证".equals(worktype)) {
            if (!odrertype.equals("一级动火票") && !odrertype.equals("二级动火票")) {
                this.getMboValue("s_repwonum").setRequired(false);
                this.getMboValue("s_repwonum").setReadOnly(false);
                this.getMboValue("s_repwonum").setValueNull();
                this.getMboValue("s_department").setRequired(false);
                this.getMboValue("s_department").setReadOnly(false);
                this.getMboValue("s_department").setValueNull();
                this.getMboValue("s_dhzxperson").setRequired(false);
                this.getMboValue("s_dhzxperson").setReadOnly(false);
                this.getMboValue("s_dhzxperson").setValueNull();
            } else {
                String parent = this.getMboValue("parent").getString();
                mainMbo.setValue("S_DEPARTMENT", mainMbo.getString("TEAMNAME"), 11L);
                MboSetRemote woset = this.getMboValue().getMbo().getMboSet("$workorder", "workorder", "PARENT= '" + parent + "' and s_ordertype not in('一级动火票','二级动火票') and worktype = '工作票' and status not in('取消','等待批准')");
                if (woset.count() == 1) {
                    this.getMboValue("s_repwonum").setValue(woset.getMbo(0).getString("wonum"), 11L);
                }

                woset.close();
                this.getMboValue("s_repwonum").setRequired(true);
                this.getMboValue("s_department").setRequired(true);
                this.getMboValue("s_dhzxperson").setRequired(true);
            }

        }
    }

    public void insertBzTagout(String location, String siteid, String wonum) throws RemoteException, MXException {
        MboRemote mainMbo = this.getMboValue().getMbo();
        MboSetRemote hazardSet = mainMbo.getMboSet("WOSLTAGENABLED");
        if (!hazardSet.isEmpty() && !location.isEmpty() && !location.equals("")) {
            MboSetRemote locationMsr = mainMbo.getMboSet("temporaryLocations", "LOCATIONS", "LOCATION='" + location + "' and siteid='" + siteid + "'");
            MboRemote locationMbo = null;
            if (!locationMsr.isEmpty()) {
                locationMbo = locationMsr.getMbo(0);
            }

            int count = hazardSet.count();

            for(int i = 0; i < count; ++i) {
                MboRemote hazardMbo = hazardSet.getMbo(i);
                String hazardId = hazardMbo.getString("hazardid");
                MboSetRemote safetyExiconMSr = locationMbo.getMboSet("SAFETYLEXICON");
                safetyExiconMSr.setRelationship("HAZARDID='" + hazardId + "' and tagoutid is not null and location='" + location + "'");
                safetyExiconMSr.resetQbe();
                if (!safetyExiconMSr.isEmpty()) {
                    MboSetRemote woSafetyTagMsr = hazardMbo.getMboSet("WOSAFETYLINKTAG");
                    if (!woSafetyTagMsr.isEmpty()) {
                        woSafetyTagMsr.deleteAndRemoveAll();
                    }

                    for(int x = 0; x < safetyExiconMSr.count(); ++x) {
                        MboRemote safetyExiconMbo = safetyExiconMSr.getMbo(x);
                        MboSetRemote tagoutMsr = safetyExiconMbo.getMboSet("TAGOUT");
                        if (!tagoutMsr.isEmpty()) {
                            MboRemote woSafetyTagMbo = woSafetyTagMsr.add();
                            MboRemote tagoutMbo = tagoutMsr.getMbo(0);
                            String tagoutId = tagoutMbo.getString("TAGOUTID");
                            woSafetyTagMbo.setValue("WONUM", wonum, 2L);
                            woSafetyTagMbo.setValue("HAZARDID", hazardId, 2L);
                            woSafetyTagMbo.setValue("TAGOUTID", tagoutId, 2L);
                            woSafetyTagMbo.setValue("REQUIREDSTATE", tagoutMbo.getString("REQUIREDSTATE"), 11L);
                            woSafetyTagMbo.setValue("TAGMETHOD", tagoutMbo.getString("TAGMETHOD"), 11L);
                            woSafetyTagMbo.setValue("TAGOUTLOCATION", tagoutMbo.getString("LOCATION"), 11L);
                            woSafetyTagMbo.setValue("ISKG", tagoutMbo.getString("ISKG"), 2L);
                            woSafetyTagMbo.setValue("ISDX", tagoutMbo.getString("ISDX"), 2L);
                            woSafetyTagMbo.setValue("ISBSP", tagoutMbo.getString("ISBSP"), 2L);
                            woSafetyTagMbo.setValue("PERFORMROLE", tagoutMbo.getString("PERFORMROLE"), 2L);
                        }
                    }
                }
            }
        }

        hazardSet.close();
    }
}
