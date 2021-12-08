package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import controller.ChamadoController;
import model.vo.ChamadoVO;
import model.vo.UsuarioVO;

public class MenuAtendimento {
	
	Scanner teclado = new Scanner(System.in);
	DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private static final int OPCAO_MENU_LISTAR_CHAMADO = 1;
	private static final int OPCAO_MENU_ATENDER_CHAMADO = 2;
	private static final int OPCAO_MENU_ATENDIMENTO_SAIR = 9;
	
	private static final int OPCAO_MENU_LISTAR_CHAMADOS_ABERTOS = 1;
	private static final int OPCAO_MENU_LISTAR_CHAMADOS_FECHADOS = 2;
	private static final int OPCAO_MENU_LISTAS_CHAMADOS_SAIR = 9;

	public void apresentarMenuAtendimento(UsuarioVO usuarioVO) {
		int opcao = this.apresentarOpcoesMenu();		
		while(opcao != OPCAO_MENU_ATENDIMENTO_SAIR) {
			switch (opcao) {
				case OPCAO_MENU_LISTAR_CHAMADO:{
					this.listarChamados(usuarioVO);
					break;
				}
				case OPCAO_MENU_ATENDER_CHAMADO:{
					this.atenderChamado(usuarioVO);
					break;
				}
				default:{
					System.out.println("\nOpção Inválida");
				}
			}
			opcao = apresentarOpcoesMenu();
		}
	}
	
	private void listarChamados(UsuarioVO usuarioVO) {
		int opcao = this.apresentarOpcoesConsulta();
		ChamadoController chamadoController = new ChamadoController();
		while(opcao != OPCAO_MENU_LISTAS_CHAMADOS_SAIR) {
			switch (opcao) {
				case OPCAO_MENU_LISTAR_CHAMADOS_ABERTOS:{
					opcao = OPCAO_MENU_LISTAS_CHAMADOS_SAIR;
					ArrayList<ChamadoVO> listaChamadosVO = chamadoController.listarChamadosAbertosController();
					System.out.print("\n------------ RESULTADO DA CONSULTA ------------");
					System.out.printf("\n%10s  %10s  %10s  %-30s  %-50s  %-15s  %-30s  %-15s  ", 
							"ID CHAMADO", "ID USUÁRIO", "ID TÉCNICO", "TÍTULO", "DESCRIÇÃO", "DATA ABERTURA", "SOLUÇÃO", "DATA FECHAMENTO");
					for(ChamadoVO chamado : listaChamadosVO) {
						chamado.imprimir();
					}
					System.out.println();
					break;
				}
				case OPCAO_MENU_LISTAR_CHAMADOS_FECHADOS:{
					opcao = OPCAO_MENU_LISTAS_CHAMADOS_SAIR;
					ArrayList<ChamadoVO> listaChamadosVO = chamadoController.listarChamadosFechadosTecnicoController(usuarioVO);
					System.out.print("\n------------ RESULTADO DA CONSULTA ------------");
					System.out.printf("\n%10s  %10s  %10s  %-30s  %-50s  %-15s  %-30s  %-15s  ", 
							"ID CHAMADO", "ID USUÁRIO", "ID TÉCNICO", "TÍTULO", "DESCRIÇÃO", "DATA ABERTURA", "SOLUÇÃO", "DATA FECHAMENTO");
					for(ChamadoVO chamado : listaChamadosVO) {
						chamado.imprimir();
					}
					System.out.println();
					break;
				}
				default:{
					System.out.println("\nOpção Inválida");
					opcao = apresentarOpcoesConsulta();
				}
			}
		}
		
	}

	private void atenderChamado(UsuarioVO usuarioVO) {
		ChamadoVO chamadoVO = new ChamadoVO();
		chamadoVO.setIdTecnico(usuarioVO.getIdUsuario());
		System.out.print("\nDigite o código do chamado: ");
		chamadoVO.setIdChamado(Integer.parseInt(teclado.nextLine()));
		System.out.print("Digite a solução: ");
		chamadoVO.setSolucao(teclado.nextLine());
		chamadoVO.setDataFechamento(LocalDate.now());
		
		if(chamadoVO.getIdChamado() == 0 || chamadoVO.getSolucao().isEmpty()) {
			System.out.println("Os campos código do chamado e solução são obrigatórios");
		}else {
			ChamadoController chamadoController = new ChamadoController();
			chamadoVO = chamadoController.atenderChamadoController(chamadoVO);
			if(chamadoVO.getDataFechamento() != null) {
				System.out.println("Chamado fechado com Sucesso!");
			}else {
				System.out.println("Não foi possível fechar o Chamado!");
			}
		}
	}
	
	private int apresentarOpcoesMenu() {
		System.out.println("\n---- Sistema Socorro Desk ----");
		System.out.println("\n---- Menu de Atendimento de Chamados ----");
		System.out.println(OPCAO_MENU_LISTAR_CHAMADO + " - Listar chamados");
		System.out.println(OPCAO_MENU_ATENDER_CHAMADO + " - Atender chamado");
		System.out.println(OPCAO_MENU_ATENDIMENTO_SAIR + " - Sair");
		System.out.print("\nDigite a opção: ");
		return Integer.parseInt(teclado.nextLine());
	}
	
	private int apresentarOpcoesConsulta() {
		System.out.println("\n---- Sistema Socorro Desk ----");
		System.out.println("\n---- Menu de Consulta de Chamados ----");
		System.out.println(OPCAO_MENU_LISTAR_CHAMADOS_ABERTOS + " - Listar todos os chamados abertos");
		System.out.println(OPCAO_MENU_LISTAR_CHAMADOS_FECHADOS + " - Listar todos os chamados fechados");
		System.out.println(OPCAO_MENU_LISTAS_CHAMADOS_SAIR + " - Sair");
		System.out.print("\nDigite a opção: ");
		return Integer.parseInt(teclado.nextLine());
	}

}
