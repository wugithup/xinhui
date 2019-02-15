package com.shuto.mam.app.question;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXException;
/**
 * 技术问答和考问讲解
 com.shuto.mam.app.question.Question 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月5日 下午8:45:51
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class Question extends Mbo
  implements QuestionRemote
{
  public Question(MboSet ms)
    throws RemoteException
  {
    super(ms);
  }

  public void init() throws MXException {
    super.init();

    String status = getMboValue("STATUS").getString();
    try
    {
      if (status.equals("答题人答题")) {
        setFieldFlag("ZHUANYE", 7L, false);
        setFieldFlag("ZHUANYE", 128L, true);

        setFieldFlag("ZHIBIE", 7L, false);
        setFieldFlag("ZHIBIE", 128L, true); return;
      }
      setFieldFlag("ZHUANYE", 7L, true);
      setFieldFlag("ZHUANYE", 128L, false);
      setFieldFlag("ZHIBIE", 7L, true);
      setFieldFlag("ZHIBIE", 128L, false);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}