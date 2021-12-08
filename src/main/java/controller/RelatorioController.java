package controller;

import java.util.ArrayList;

import model.bo.RelatorioBO;
import model.dto.RelatorioTecnicoDTO;
import model.vo.UsuarioVO;

public class RelatorioController {

	public ArrayList<RelatorioTecnicoDTO> consultarRelatorioTecnicoController(UsuarioVO usuarioVO) {
		RelatorioBO relatorioBO = new RelatorioBO();
		return relatorioBO.consultarRelatorioTecnicoBO(usuarioVO);
	}
	

}
