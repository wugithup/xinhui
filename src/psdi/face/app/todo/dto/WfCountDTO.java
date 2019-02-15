package psdi.face.app.todo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author zhaowei
 * @email zhaowei@shuto.cn
 * @create 2018-11-07 9:20
 * @desc
 * @class psdi.face.app.todo.dto.WfCountDTO
 * @Copyright: 2018 Shuto版权所有
 **/

public class WfCountDTO {

    @JsonProperty("personid")
    private String personid;

    @JsonProperty("count")
    private int count;

    private String url;

    public String getPersonid() {

        return personid;
    }

    public void setPersonid(String personid) {

        this.personid = personid;
    }

    public int getCount() {

        return count;
    }

    public void setCount(int count) {

        this.count = count;
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {

        this.url = url;
    }
}
