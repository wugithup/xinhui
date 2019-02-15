package com.shuto.mam.webclient.beans.pm;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

import java.rmi.RemoteException;

/**
 * @author wuchang
 * @version V1.0
 * @Title: LocationDialogDataBean
 * @Package com.shuto.mam.webclient.beans.pm
 * @Description: 用于PM表中location字段的dialog
 * @date 2019/1/15 15:34
 */
public class LocationDialogDataBean extends DataBean {

    /**
     * 对dialog中选择的数据添加目标数据中
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public synchronized int execute() throws MXException, RemoteException {

        //位置描述LOCATIONDETAIL , LOCATION
        //获得主表的mbo
        MboRemote pmMbo = this.app.getAppBean().getMbo();
        String location = pmMbo.getString("LOCATION");
        String locationDetail = pmMbo.getString("LOCATIONDETAIL");

        //获得dialog中的数据
        MboSetRemote locationSet = this.getMboSet();
        //获得dialog中选中的数据
        locationSet.resetWithSelection();

        if(!locationSet.isEmpty())
        {
            //先清空之前填入的数据
            pmMbo.setValueNull("LOCATION",11L);
            pmMbo.setValueNull("LOCATIONDETAIL",11L);

            StringBuffer locationBuffer = new StringBuffer(200);
            StringBuilder loDetail = new StringBuilder(500);

            for (int i = 0,size = locationSet.count(); i < size; i++)
            {
                if (i==0)
                {
                    locationBuffer.append(locationSet.getMbo(i).getString("LOCATION"));
                    loDetail.append(locationSet.getMbo(i).getString("DESCRIPTION"));
                }else{
                    locationBuffer.append("，"+locationSet.getMbo(i).getString("LOCATION"));
                    loDetail.append("，"+locationSet.getMbo(i).getString("DESCRIPTION"));
                }


            }

            //填充数据
            pmMbo.setValue("LOCATION",locationBuffer.toString(),11L);
            pmMbo.setValue("LOCATIONDETAIL",loDetail.toString(),11L);

        }

        this.app.getAppBean().save();

        return 1;
    }

}
