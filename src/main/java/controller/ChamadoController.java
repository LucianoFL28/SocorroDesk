package controller;

import java.util.ArrayList;

import model.bo.ChamadoBO;
import model.vo.ChamadoVO;
import model.vo.UsuarioVO;

public class ChamadoController {

	public ChamadoVO cadastrarChamadoController(ChamadoVO chamadoVO) {
		ChamadoBO chamadoBO = new ChamadoBO();
		return chamadoBO.cadastrarChamadoBO(chamadoVO);
	}

	public boolean excluirChamadoController(ChamadoVO chamadoVO) {
		ChamadoBO chamadoBO = new ChamadoBO();
		return chamadoBO.excluirChamadoBO(chamadoVO);
	}

	public boolean atualizarChamadoController(ChamadoVO chamadoVO) {
		ChamadoBO chamadoBO = new ChamadoBO();
		return chamadoBO.atualizarChamadoBO(chamadoVO);
	}

	public ArrayList<ChamadoVO> consultarTodosChamadosUsuarioController(ChamadoVO chamadoVO) {
		ChamadoBO chamadoBO = new ChamadoBO();
		return chamadoBO.consultarTodosChamadosUsuarioBO(chamadoVO);
	}

	public ArrayList<ChamadoVO> consultarChamadosAbertosUsuarioController(ChamadoVO chamadoVO) {
		ChamadoBO chamadoBO = new ChamadoBO();
		return chamadoBO.consultarChamadosAbertosUsuarioBO(chamadoVO);
	}

	public ArrayList<ChamadoVO> consultarChamadosFechadosController(ChamadoVO chamadoVO) {
		ChamadoBO chamadoBO = new ChamadoBO();
		return chamadoBO.consultarChamadosFechadosBO(chamadoVO);
	}

	public ChamadoVO atenderChamadoController(ChamadoVO chamadoVO) {
		ChamadoBO chamadoBO = new ChamadoBO();
		return chamadoBO.atenderChamadoBO(chamadoVO);
	}

	public ArrayList<ChamadoVO> listarChamadosAbertosController() {
		ChamadoBO chamadoBO = new ChamadoBO();
		return chamadoBO.listarChamadosAbertosBO();
	}

	public ArrayList<ChamadoVO> listarChamadosFechadosTecnicoController(UsuarioVO usuarioVO) {
		ChamadoBO chamadoBO = new ChamadoBO();
		return chamadoBO.listarChamadosFechadosTecnicoBO(usuarioVO);
	}
}
