package psdi.face.app.todo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author zhaowei
 * @email zhaowei@shuto.cn
 * @create 2018-09-04 9:53
 * @desc
 * @class psdi.face.app.todo.dto.WfDTO
 * @Copyright: 2018 Shuto版权所有
 **/

public class WfDTO {

    @JsonProperty("personid")
    private String personid;

    @JsonProperty("description")
    private String description;

    @JsonProperty("wfid")
    private String wfid;

    @JsonProperty("count")
    private int count;

    private String url;

    public String getPersonid() {
        return personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWfid() {
        return wfid;
    }

    public void setWfid(String wfid) {
        this.wfid = wfid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "{" +
                "\"personid\":\"" + personid + '\"' +
                ",\"description\":\"" + description + '\"' +
                ",\"wfid=\":\"" + wfid + '\"' +
                ",\"count=\":\"" + count + "\"" + '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
