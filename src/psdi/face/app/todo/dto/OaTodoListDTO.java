package psdi.face.app.todo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author zhaowei
 * @email zhaowei@shuto.cn
 * @create 2018-09-13 9:27
 * @desc
 * @class psdi.face.app.xdj.dto.OaTodoListDTO
 * @Copyright: 2018 Shuto版权所有
 **/

public class OaTodoListDTO {

    @JsonProperty("suid")
    private String suid;

    @JsonProperty("jinji")
    private String jinji;

    @JsonProperty("bz")
    private String bz;

    @JsonProperty("sdate")
    private String sdate;

    @JsonProperty("flmc")
    private String flmc;

    @JsonProperty("url")
    private String url;

    @JsonProperty("strfrom")
    private String strfrom;

    @JsonProperty("state")
    private String state;

    @JsonProperty("fldmodule")
    private String fldmodule;

    public String getSuid() {
        return suid;
    }

    public void setSuid(String suid) {
        this.suid = suid;
    }

    public String getJinji() {
        return jinji;
    }

    public void setJinji(String jinji) {
        this.jinji = jinji;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getFlmc() {
        return flmc;
    }

    public void setFlmc(String flmc) {
        this.flmc = flmc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStrfrom() {
        return strfrom;
    }

    public void setStrfrom(String strfrom) {
        this.strfrom = strfrom;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFldmodule() {
        return fldmodule;
    }

    public void setFldmodule(String fldmodule) {
        this.fldmodule = fldmodule;
    }

    @Override
    public String toString() {
        return "OaTodoListDTO{" +
                "suid='" + suid + '\'' +
                ", jinji='" + jinji + '\'' +
                ", bz='" + bz + '\'' +
                ", sdate='" + sdate + '\'' +
                ", flmc='" + flmc + '\'' +
                ", url='" + url + '\'' +
                ", strfrom='" + strfrom + '\'' +
                ", state='" + state + '\'' +
                ", fldmodule='" + fldmodule + '\'' +
                '}';
    }
}
