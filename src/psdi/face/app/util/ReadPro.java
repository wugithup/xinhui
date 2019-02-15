package psdi.face.app.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author zhaowei
 * @email zhaowei@shuto.cn
 * @create 2018-08-15 11:35
 * @desc
 * @class psdi.face.app.util.ReadPro
 * @Copyright: 2018 Shuto版权所有
 **/

public class ReadPro {

    public Properties getPro() {
        Properties prop = new Properties();
        InputStream in = ReadPro.class.getResourceAsStream("interfacePath.properties");
        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
//TODO
   /* public static void main(String[] args) {
        Properties prop = getPro();
        System.out.println(prop.getProperty("webservice.path").trim());
    }*/
}
