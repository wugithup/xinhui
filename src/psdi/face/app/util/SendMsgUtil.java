package psdi.face.app.util;

import java.io.UnsupportedEncodingException;

/**
 * @author zhaowei
 * @email zhaowei@shuto.cn
 * @create 2018-09-06 14:15
 * @desc
 * @class psdi.face.app.util.SendErrMsg
 * @Copyright: 2018 Shuto版权所有
 **/

public class SendMsgUtil {

    public byte[] getErrMsg(String msg) {
        String re = "{\"META\":{\"SUCCESS\":false,\"message\":\"" + msg + "\",\"CODE\":\"401\",\"RESOURCE\": \"ERROR\"},\"DATA\": null}";
        byte[] returnStr = null;
        try {
            returnStr = re.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return returnStr;
    }
}
