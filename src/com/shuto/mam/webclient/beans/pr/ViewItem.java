package com.shuto.mam.webclient.beans.pr;


import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author wuchang
 * @version V1.0
 * @Title: ViewItem
 * @Package com.shuto.mam.webclient.beans.pr
 * @Description:pr应用中字表的dialog，用于显示item表中的数据
 * @date 2018/12/21 11:45
 */
public class ViewItem extends DataBean {

    /**
     * 用于设置过滤条件
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    protected MboSetRemote getMboSetRemote() throws MXException, RemoteException {

        MboRemote mainMbo = this.app.getAppBean().getMbo();
        MboSetRemote valueListSet = super.getMboSetRemote();
        MboSetRemote curPerSet = mainMbo.getMboSet("PRLINE");
        StringBuffer buf = null;
        //收集已添加的数据编码itemnum
        if (!curPerSet.isEmpty())
        {
            for(int i = 0; i < curPerSet.count(); ++i) {
                {
                    if (!curPerSet.getMbo(i).getString("itemnum").isEmpty()) {
                        String itemnum = curPerSet.getMbo(i).getString("itemnum");
                        if (buf == null) {
                            buf = (new StringBuffer("itemnum not in ('")).append(itemnum).append("'");
                        } else {
                            buf.append(",'").append(itemnum).append("'");
                        }
                    }

                }

            }
            if (buf != null) {
                buf.append(") ");
            } else {
                buf = new StringBuffer();
            }
        }

        //对于添加的数据进行过滤操作
        if(!valueListSet.isEmpty())
        {
            if (buf!=null)
            {
                valueListSet.setWhere("STATUS='活动' and "+buf.toString());
            }else{
                valueListSet.setWhere("STATUS='活动'");
            }
            valueListSet.setOrderBy("ITEMNUM");
            valueListSet.reset();
        }

        return valueListSet;

    }

    /**
     * 对dialog中选择的数据添加目标数据中
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public synchronized int execute() throws MXException, RemoteException {
        //获得主表的mbo
        MboRemote mainMbo = this.app.getAppBean().getMbo();
        String siteid = mainMbo.getString("siteid");
        MboSetRemote curPerSet = mainMbo.getMboSet("PRLINE");

        Date date = null;
        SimpleDateFormat sdf = null;
        MboRemote inventory = null;
        MboRemote prline = null;

        //获得dialog中的数据
        MboSetRemote lineSet = this.getMboSet();
        //获得dialog中选中的数据
        lineSet.resetWithSelection();

        if(!lineSet.isEmpty())
        {
            //获取LOCATIONS集合
            MboSetRemote msr =mainMbo.getMboSet("$LOCATIONS", "LOCATIONS","TYPE = '库房' and STATUS = '操作中' and SITEID = '" + siteid + "'");
            date = new Date();
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = sdf.format(date);

            for (int i=0,count = lineSet.count();i<count;++i)
            {
                inventory = (MboRemote)lineSet.getMbo(i);


                //将dialog中获得数据添加到目标数据中
                prline = curPerSet.add();
                prline.setValue("PRLINENUM", String.valueOf(curPerSet.count()), 11L);
                prline.setValue("itemnum", inventory.getString("itemnum"), 2L);
                prline.setValue("REQDELIVERYDATE", dateString, 2L);
                if (msr.getMbo(0)!=null)
                {
                    prline.setValue("STORELOC", msr.getMbo(0).getString("LOCATION"), 11L);
                }
                prline.setValue("ORDERUNIT", inventory.getString("ORDERUNIT"), 11L);


            }
            this.app.getAppBean().reloadTable();
        }
        this.app.getAppBean().save();

        return 1;
    }
}
