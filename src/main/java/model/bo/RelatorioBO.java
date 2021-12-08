package model.bo;

import java.util.ArrayList;

import model.dao.RelatorioDAO;
import model.dto.RelatorioTecnicoDTO;
import model.vo.UsuarioVO;

public class RelatorioBO {

	public ArrayList<RelatorioTecnicoDTO> consultarRelatorioTecnicoBO(UsuarioVO usuarioVO) {
		RelatorioDAO relatorioDAO = new RelatorioDAO();
		ArrayList<RelatorioTecnicoDTO> listaRelatorioTecnicoDTO = new ArrayList<RelatorioTecnicoDTO>();
		
		if (relatorioDAO.consultarAdministrador(usuarioVO.getIdUsuario())) {
			listaRelatorioTecnicoDTO = relatorioDAO.consultarRelatorioTecnicoDAO();
		} else {
			listaRelatorioTecnicoDTO = relatorioDAO.consultarRelatorioTecnicoParticularDAO(usuarioVO.getIdUsuario());
		}
		return listaRelatorioTecnicoDTO;
	}

}
