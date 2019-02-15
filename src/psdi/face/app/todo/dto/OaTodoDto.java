package psdi.face.app.todo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author zhaowei
 * @email zhaowei@shuto.cn
 * @create 2018-09-13 9:24
 * @desc
 * @class psdi.face.app.xdj.dto.OaTodoDto
 * @Copyright: 2018 Shuto版权所有
 **/

public class OaTodoDto {


    @JsonProperty("recoardcount")
    private String recoardcount;

    @JsonProperty("pagecount")
    private String pagecount;

    @JsonProperty("pageindexs")
    private String pageindexs;

    @JsonProperty("todoList")
    private List<OaTodoListDTO> todoList;


    public List<OaTodoListDTO> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<OaTodoListDTO> todoList) {
        this.todoList = todoList;
    }

    public String getRecoardcount() {
        return recoardcount;
    }

    public void setRecoardcount(String recoardcount) {
        this.recoardcount = recoardcount;
    }

    public String getPagecount() {
        return pagecount;
    }

    public void setPagecount(String pagecount) {
        this.pagecount = pagecount;
    }

    public String getPageindexs() {
        return pageindexs;
    }

    public void setPageindexs(String pageindexs) {
        this.pageindexs = pageindexs;
    }

    @Override
    public String toString() {
        return "OaTodoDto{" +
                "todoList=" + todoList +
                ", recoardcount='" + recoardcount + '\'' +
                ", pagecount='" + pagecount + '\'' +
                ", pageindexs='" + pageindexs + '\'' +
                '}';
    }
}
