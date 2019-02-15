package psdi.face.app.todo.service;

import psdi.face.app.todo.dto.WfCountDTO;
import psdi.face.app.todo.dto.WfDTO;

import java.util.List;

public interface WfService {

    List<WfCountDTO> getWfCount(String personid);

    List<WfDTO> getWfList(String personid);
}
