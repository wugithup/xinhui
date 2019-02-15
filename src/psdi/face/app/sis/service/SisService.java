package psdi.face.app.sis.service;

import psdi.face.app.sis.dto.SisRealDTO;

import java.util.List;

/**
 * @author zhao
 */
public interface SisService {

    /**
     * 返回一号机组实时负荷
     *
     * @return
     */
    List<SisRealDTO> getSisDto();

    /**
     * 返回二号机组实时负荷
     *
     * @return
     */
    List<SisRealDTO> getSisDtoTwo();
}
