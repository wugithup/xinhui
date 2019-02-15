package psdi.face.app.util;

/**
 * @author zhaowei
 * @email zhaowei@shuto.cn
 * @create 2018-09-05 16:27
 * @desc
 * @class psdi.face.app.util.getJsonStr
 * @Copyright: 2018 Shuto版权所有
 **/

public class AddHeadStr {

    public String addHead(String str, String code, String msg) {
        return "[{" +
                "\"resultcode\":\"" + code + "\",\"resultmsg\":\"" + msg + "\",\"resultdata\":[" +
                str +
                "]}]";
    }
}
