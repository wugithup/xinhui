package com.shuto.mam.app.question;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;
/**
 * 技术问答和考问讲解
 com.shuto.mam.app.question.QuestionSet 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月5日 下午8:46:07
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class QuestionSet extends MboSet
  implements QuestionSetRemote
{
  public QuestionSet(MboServerInterface ms)
    throws RemoteException
  {
    super(ms);
  }

  protected Mbo getMboInstance(MboSet arg0)
    throws MXException, RemoteException
  {
    return new Question(arg0);
  }
}