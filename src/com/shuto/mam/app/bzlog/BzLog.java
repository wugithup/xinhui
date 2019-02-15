package com.shuto.mam.app.bzlog;

import psdi.mbo.Mbo;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXAccessException;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @author wuchang
 * @version V1.0
 * @Title: BzLog
 * @Package com.shuto.mam.app.bzlog
 * @Description: 班组日志的Mbo
 * @date 2019/1/10 14:00
 */
public class BzLog extends Mbo implements BzLogRemote {

    public BzLog(MboSet ms) throws RemoteException {

        super(ms);
    }

    /**
     * 进行初始化设置
     * @throws MXException
     */
    @Override
    public void init() throws MXException {
        super.init();
        MboRemote mbo = this;
        final String woStatus = "已关闭";
        try {
            //获取当前状态
            String status = mbo.getString("STATUS");
            MboSetRemote logNoteSet = mbo.getMboSet("BZLOGNOTE");


            if (status!=null&&!"".equals(status))
            {
                if (woStatus.equals(status))
                {
                    setFieldFlag("ZY", MboConstants.READONLY,true);
                    //将子表设置成只读
                    logNoteSet.setFlag(MboConstants.READONLY,true);
                    //获取出勤记录集合
                    logNoteSet = mbo.getMboSet("BZCQJL");
                    logNoteSet.setFlag(MboConstants.READONLY,true);
                    //获取设备移动集合
                    logNoteSet = mbo.getMboSet("BZSBYD");
                    logNoteSet.setFlag(MboConstants.READONLY,true);
                }

            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

}
