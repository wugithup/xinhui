package psdi.face.app.util;

import org.codehaus.xfire.client.Client;

import java.net.URL;

/**
 * @author zhaowei
 * @email zhaowei@shuto.cn
 * @create 2018-08-10 17:27
 * @desc
 * @class psdi.face.app.util.SendXml
 * @Copyright: 2018 Shuto版权所有
 **/

public class SendXml {

    public String sendQueXml(String str, String url) {
//        StringBuilder xml= new StringBuilder();
        String returnStr = "";
        try {
            Client client = new Client(new URL(url));
            Object[] objs = {str};
            Object[] obj = client.invoke("query", objs);
            returnStr = (String) obj[0];

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnStr.substring(find(returnStr, 5), returnStr.length() - 4);
    }

    private int find(String findStr, int n) {
        int num = 0;
        char[] c = findStr.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if ('{' == c[i]) {
                num++;
            }
            if (num == n) {
                return i;
            }
        }
        return 0;
    }

    //TODO
    /*public static void main(String[] args) {
        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "\n" +
                "<query>\n" +
                "  <servicename>QUERYZCDZ</servicename>\n" +
                "  <partnerid>CRPEAM</partnerid>\n" +
                "  <sign>cf3826e79fc3a053fed30090611f50d0</sign>\n" +
                "  <personid>110608</personid>\n" +
                "</query>";
        String retustr = sendQueXml(str, "http://10.59.3.104:7001/webservice/webservice/CommonService?wsdl");
        System.out.println(retustr);
//        String strs =
        System.out.println(retustr.indexOf("{", 0 + 5));
        System.out.println(retustr.substring(find(retustr, 5), retustr.length() - 4));

    }*/
}