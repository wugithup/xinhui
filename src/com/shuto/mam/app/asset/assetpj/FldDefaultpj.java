/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.asset.assetpj;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;

import java.rmi.RemoteException;

public class FldDefaultpj extends MAXTableDomain {

    private static MXLogger MX_LOGGER = MXLoggerFactory.getLogger("maximo.application");

    public FldDefaultpj(MboValue paramMboValue) {

        super(paramMboValue);

        setRelationship("ASSETBZFL", "1=1");
        String str = getMboValue().getName();
        String[] arrayOfString1 = {str};

        String[] arrayOfString2 = {"bzfl"};

        setLookupKeyMapInOrder(arrayOfString1, arrayOfString2);
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {

        Mbo localMbo = getMboValue().getMbo();
        String str1 = localMbo.getString("siteid");
        String str2 = localMbo.getString("bzlx");
        setListCriteria("siteid='" + str1 + "' and fltype='评级结果分类' and fljb=2 " +
                "and parentfl IN (select assetbzflnum from assetbzfl where fltype='评级结果分类' and siteid=:siteid and bzfl='" +
                str2 + "')");

        setListOrderBy("ordernum");
        return super.getList();
    }

    @Override
    public void action() {

        try {
            Mbo localMbo = getMboValue().getMbo();
            MboRemote localMboRemote = localMbo.getMboSet("ASSETPJ").getMbo(0);
            MboSetRemote localMboSetRemote = localMbo.getMboSet("ASSETPJLINE");

            if ((!localMboSetRemote.isEmpty()) && (localMboRemote != null) &&
                    (localMboRemote.getString("fieldmark") != null)) {
                for (int i = 0; i < localMboSetRemote.count(); i++) {
                    localMboSetRemote.getMbo(i).setValue(localMboRemote.getString("fieldmark"),
                            getMboValue().getString());
                }
            }
        } catch (RemoteException localRemoteException) {
            MX_LOGGER.error(localRemoteException);
        } catch (MXException localMXException) {
            MX_LOGGER.error(localMXException);
        }
    }
}