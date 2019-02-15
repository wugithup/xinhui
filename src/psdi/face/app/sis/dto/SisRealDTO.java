package psdi.face.app.sis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author zhaowei
 * @email zhaowei@shuto.cn
 * @create 2018-10-16 14:58
 * @desc
 * @class psdi.face.app.sis.dto.SisReal
 * @Copyright: 2018 Shuto版权所有
 **/

public class SisRealDTO {

    @JsonProperty("Value")
    private String value;

    @JsonProperty("State")
    private String state;

    @JsonProperty("Date")
    private String date;

    public String getValue() {

        return value;
    }

    public void setValue(String value) {

        this.value = value;
    }

    public String getState() {

        return state;
    }

    public void setState(String state) {

        this.state = state;
    }

    public String getDate() {

        return date;
    }

    public void setDate(String date) {

        this.date = date;
    }

    @Override
    public String toString() {

        return "SisRealDTO{" + "value='" + value + '\'' + ", state='" + state + '\'' + ", date='" +
                date + '\'' + '}';
    }
}
