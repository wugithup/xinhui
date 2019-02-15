/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.crontask.pm;

import com.jasson.im.api.APIClient;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.SqlFormat;
import psdi.server.SimpleCronTask;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;

import java.util.Date;

/**
 * @author SumMer
 * @date 2018-09-10
 * @description 短信发送定时任务
 */
public class SmsCronTask extends SimpleCronTask {

    private final static MXLogger MX_LOGGER = MXLoggerFactory.getLogger("maximo.application");

    private final static String IP = "10.42.5.243";

    private final static String APIUSER = "xhpjjmis";

    private final static String APIPWD = "password";

    private final static String APICODE = "xhpjjmis";

    private final static String DBNAME = "mas";

    @Override
    public void cronAction() {

        try {
            // 当前任务编号
            String crontaskName = getCrontaskInstance().getString("CRONTASKNAME");
            // 当前任务实例编号
            String instanceName = getCrontaskInstance().getString("INSTANCENAME");
            // 当前最大任务的执行历史
            // crontaskhistory其操作的 Cron 任务历史记录
            String where =
                    "CRONTASKHISTORYID = (SELECT MAX(CRONTASKHISTORYID) FROM CRONTASKHISTORY WHERE CRONTASKNAME = :1 AND INSTANCENAME = :2)";
            SqlFormat sf = new SqlFormat(where);
            sf.setObject(1, "CRONTASKHISTORY", "CRONTASKNAME", crontaskName);
            sf.setObject(2, "CRONTASKHISTORY", "INSTANCENAME", instanceName);
            // 当前任务的执行历史
            MboSetRemote crontaskHistorySet = this.getCrontaskInstance()
                                                  .getMboSet("$CRONTASKHISTORY", "CRONTASKHISTORY",
                                                          sf.format());
            // 当前执行的任务历史对象
            MboRemote crontaskHistoryMbo = crontaskHistorySet.getMbo(0);
            try {
                // 检索待办，发送短信
                sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
                MX_LOGGER.error(e);
            }
            // 提示信息
            crontaskHistoryMbo.setValue("RUNTIMEERROR", "成功",
                    MboConstants.NOVALIDATION_AND_NOACTION + MboConstants.NOACCESSCHECK);
            crontaskHistorySet.save();
        } catch (Exception e) {
            MX_LOGGER.error("发送短信出错：" + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 检索待办，发送短信
     */
    private void sendSMS() {

        try {
            MboSetRemote wfMboSet = getCrontaskInstance().getMboSet("$WFASSIGNMENT", "WFASSIGNMENT",
                    "ASSIGNSTATUS = '活动' AND ISSENDSMS < 4");
            wfMboSet.reset();
            MboRemote wfMbo;
            int i = 0;

            while ((wfMbo = wfMboSet.getMbo(i)) != null) {
                //初始化短信客户端
                APIClient smsClient = initialize();
                if (smsClient != null) {
                    String content = wfMbo.getString("DESCRIPTION");
                    MboRemote personMbo = wfMbo.getMboSet("ASSIGNEE").getMbo(0);
                    String phone = personMbo.getString("MOBILEPHONE");
                    int susSend = smsClient.sendSM(phone, content + "  <EAM短信测试>", 88888);
                    if (susSend == 0) {
                        // 成功发送短信后，待办这里有标志位ISSENDSMS = 1,sendsmsdate =sysdate
                        // 设置成功发送标志
                        MX_LOGGER.info("------------------------发送成功----------------------");
                        wfMbo.setValue("ISSENDSMS", wfMbo.getInt("ISSENDSMS") + 1);
                        wfMbo.setValue("SENDSMSDATE", new Date());
                        wfMboSet.save();
                    } else {
                        MX_LOGGER.error("------------------------发送失败----------------------");
                    }
                    i++;
                    smsClient.release();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            MX_LOGGER.error(e);
        }
    }

    /**
     * 移动平台服务定义：使用粤电集团短信平台
     */
    private APIClient initialize() {

        try {
            // 初始化方法
            APIClient smsClient = new APIClient();
            int sus = smsClient.init(IP, APIUSER, APIPWD, APICODE, DBNAME);
            if (sus == 0) {
                MX_LOGGER.info("--------------------初始化成功------------------");
                return smsClient;
            } else {
                MX_LOGGER.error("--------------------初始化失败------------------");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            MX_LOGGER.error(e);
        }
        return null;
    }

}
