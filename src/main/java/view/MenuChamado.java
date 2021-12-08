package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import controller.ChamadoController;
import model.vo.ChamadoVO;
import model.vo.UsuarioVO;

public class MenuChamado {
	
	Scanner teclado = new Scanner(System.in);
	DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private static final int OPCAO_MENU_CADASTRAR_CHAMADO = 1;
	private static final int OPCAO_MENU_CONSULTAR_CHAMADO = 2;
	private static final int OPCAO_MENU_ATUALIZAR_CHAMADO = 3;
	private static final int OPCAO_MENU_EXCLUIR_CHAMADO = 4;
	private static final int OPCAO_MENU_CHAMADO_SAIR = 9;
	
	private static final int OPCAO_MENU_CONSULTAR_TODOS_CHAMADOS = 1;
	private static final int OPCAO_MENU_CONSULTAR_CHAMADOS_ABERTOS = 2;
	private static final int OPCAO_MENU_CONSULTAR_CHAMADOS_FECHADOS = 3;
	private static final int OPCAO_MENU_CONSULTAR_SAIR = 9;
	
	public void apresentarMenuChamado(UsuarioVO usuarioVO) {
		int opcao = this.apresentarOpcoesMenu();
		while (opcao != OPCAO_MENU_CHAMADO_SAIR) {
			switch (opcao) {
				case OPCAO_MENU_CADASTRAR_CHAMADO: {
					this.cadastrarNovoChamado(usuarioVO);
					break;
				}
				case OPCAO_MENU_CONSULTAR_CHAMADO: {
					this.consultarChamado(usuarioVO);
					break;
				}
				case OPCAO_MENU_ATUALIZAR_CHAMADO: {
					this.atualizarChamado(usuarioVO);
					break;
				}
				case OPCAO_MENU_EXCLUIR_CHAMADO: {
					this.excluirChamado(usuarioVO);
					break;
				}
				default: {
					System.out.println("\nOpção inválida!");
					break;
				}
			}
			opcao = this.apresentarOpcoesMenu();
		}
	}

	private void cadastrarNovoChamado(UsuarioVO usuarioVO) {
		this.cadastrarChamado(usuarioVO);
		
	}

	private void cadastrarChamado(UsuarioVO usuarioVO) {
		
		ChamadoVO chamadoVO = new ChamadoVO();
		chamadoVO.setIdUsuario(usuarioVO.getIdUsuario());
		System.out.println(usuarioVO.getIdUsuario());
		System.out.print("\nDigite o título do chamado: ");
		chamadoVO.setTitulo(teclado.nextLine());
		System.out.print("\nDigite a descrição do chamado: ");
		chamadoVO.setDescricao(teclado.nextLine());
		chamadoVO.setDataAbertura(LocalDate.now());
		
		boolean resultado = true;
		System.out.println();
		
		if(chamadoVO.getTitulo().isEmpty() || chamadoVO.getTitulo() == null) {
			System.out.println("O campo Título é obrigatório!");
			resultado = false;
		}
		if(chamadoVO.getDescricao().isEmpty() || chamadoVO.getDescricao() == null) {
			System.out.println("O campo descrição é obrigatório!");
			resultado = false;
		}
		
		if(resultado) {
			ChamadoController chamadoController = new ChamadoController();
			chamadoVO = chamadoController.cadastrarChamadoController(chamadoVO);
			
			if(chamadoVO.getIdChamado() != 0) {
				System.out.println("Chamado cadastrado com sucesso!");
			}else {
				System.out.println("Não foi possível cadastrar o chamado.");
			}
		}
		
	}

	private void consultarChamado(UsuarioVO usuarioVO) {
		int opcao = this.apresentarOpcoesConsulta();
		ChamadoController chamadoController = new ChamadoController();
		ChamadoVO chamadoVO = new ChamadoVO();
		chamadoVO.setIdUsuario(usuarioVO.getIdUsuario());
		while (opcao != OPCAO_MENU_CONSULTAR_SAIR) {
			switch (opcao) {
				case OPCAO_MENU_CONSULTAR_TODOS_CHAMADOS:{
					opcao = OPCAO_MENU_CONSULTAR_SAIR;
					ArrayList<ChamadoVO> listaChamadosVO = chamadoController.consultarTodosChamadosUsuarioController(chamadoVO);
					System.out.println("\n-------- RESULTADO DA CONSULTA --------");
					System.out.printf("\n%10s  %10s  %10s  %-30s  %-50s  %-15s  %-30s  %-15s  ", 
							"ID CHAMADO", "ID USUÁRIO", "ID TÉCNICO", "TÍTULO", "DESCRIÇÃO", "DATA ABERTURA", "SOLUÇÃO", "DATA FECHAMENTO");
					for(ChamadoVO chamado: listaChamadosVO) {
						chamado.imprimir();
					}
					break;
				}
				case OPCAO_MENU_CONSULTAR_CHAMADOS_ABERTOS:{
					opcao = OPCAO_MENU_CONSULTAR_SAIR;
					ArrayList<ChamadoVO> listaChamadosAbertosVO = chamadoController.consultarChamadosAbertosUsuarioController(chamadoVO);
					System.out.println("\n-------- RESULTADO DA CONSULTA --------");
					System.out.printf("\n%10s  %10s  %10s  %-30s  %-50s  %-15s  %-30s  %-15s  ", 
							"ID CHAMADO", "ID USUÁRIO", "ID TÉCNICO", "TÍTULO", "DESCRIÇÃO", "DATA ABERTURA", "SOLUÇÃO", "DATA FECHAMENTO");
					for(ChamadoVO chamado: listaChamadosAbertosVO) {
						chamado.imprimir();
					}
					break;
				}
				case OPCAO_MENU_CONSULTAR_CHAMADOS_FECHADOS:{
					opcao = OPCAO_MENU_CONSULTAR_SAIR;
					opcao = OPCAO_MENU_CONSULTAR_SAIR;
					ArrayList<ChamadoVO> listaChamadosFechadosVO = chamadoController.consultarChamadosFechadosController(chamadoVO);
					System.out.println("\n-------- RESULTADO DA CONSULTA --------");
					System.out.printf("\n%10s  %10s  %10s  %-30s  %-50s  %-15s  %-30s  %-15s  ", 
							"ID CHAMADO", "ID USUÁRIO", "ID TÉCNICO", "TÍTULO", "DESCRIÇÃO", "DATA ABERTURA", "SOLUÇÃO", "DATA FECHAMENTO");
					for(ChamadoVO chamado: listaChamadosFechadosVO) {
						chamado.imprimir();
					}
					break;
				}
				default:{
					System.out.println("\nOpção inválida!");
					opcao = this.apresentarOpcoesConsulta();
					break;
				}
			}
		}
		
	}

	private int apresentarOpcoesConsulta() {
		System.out.println("\nInforme o tipo de consulta a ser realizada");
		System.out.println(OPCAO_MENU_CONSULTAR_TODOS_CHAMADOS + " - Consultar todos os chamados");
		System.out.println(OPCAO_MENU_CONSULTAR_CHAMADOS_ABERTOS + " - Consultar chamados abertos");
		System.out.println(OPCAO_MENU_CONSULTAR_CHAMADOS_FECHADOS + " - Consultar chamados fechados");
		System.out.println(OPCAO_MENU_CONSULTAR_SAIR + " - Sair");
		System.out.print("\nDigite uma opção: ");
		return Integer.parseInt(teclado.nextLine());
	}

	private void atualizarChamado(UsuarioVO usuarioVO) {
		ChamadoVO chamadoVO = new ChamadoVO();
		System.out.print("\nDigite o código do chamado: ");
		chamadoVO.setIdChamado(Integer.parseInt(teclado.nextLine()));
		chamadoVO.setIdUsuario(usuarioVO.getIdUsuario());
		System.out.print("\nDigite o novo título do chamado: ");
		chamadoVO.setTitulo(teclado.nextLine());
		System.out.print("\nDigite a nova descrição do chamado: ");
		chamadoVO.setDescricao(teclado.nextLine());
		chamadoVO.setDataAbertura(LocalDate.now());

		boolean resultado = true;
		System.out.println();

		if(chamadoVO.getTitulo().isEmpty() || chamadoVO.getTitulo() == null) {
			System.out.println("O campo Título é obrigatório!");
			resultado = false;
		}
		if(chamadoVO.getDescricao().isEmpty() || chamadoVO.getDescricao() == null) {
			System.out.println("O campo descrição é obrigatório!");
			resultado = false;
		}

		if(resultado) {
			ChamadoController chamadoController = new ChamadoController();
			resultado = chamadoController.atualizarChamadoController(chamadoVO);

			if(resultado) {
				System.out.println("Chamado atualizado com sucesso!");
			}else {
				System.out.println("Não foi possível atualizar o chamado.");
			}
		}
		
	}

	private void excluirChamado(UsuarioVO usuarioVO) {
		ChamadoVO chamadoVO = new ChamadoVO();
		System.out.print("\nDigite o código do chamado: ");
		chamadoVO.setIdChamado(Integer.parseInt(teclado.nextLine()));
		chamadoVO.setIdUsuario(usuarioVO.getIdUsuario());
		ChamadoController chamadoController = new ChamadoController();
		boolean resultado = chamadoController.excluirChamadoController(chamadoVO);

		if(resultado){
			System.out.println("\nChamado excluído com sucesso!");
		}else{
			System.out.println("\nNão foi possível excluir o chamado!");
		}
		
	}

	private int apresentarOpcoesMenu() {
		System.out.println("\n---- Sistema Socorro Desk ----");
		System.out.println("\n---- Menu de Chamados ----");
		System.out.println(OPCAO_MENU_CADASTRAR_CHAMADO + " - Cadastrar Chamado");
		System.out.println(OPCAO_MENU_CONSULTAR_CHAMADO + " - Consultar Chamado");
		System.out.println(OPCAO_MENU_ATUALIZAR_CHAMADO + " - Atualizar Chamado");
		System.out.println(OPCAO_MENU_EXCLUIR_CHAMADO + " - Excluir Chamado");
		System.out.println(OPCAO_MENU_CHAMADO_SAIR + " - Sair");
		System.out.print("\nDigite a opção: ");
		return Integer.parseInt(teclado.nextLine());
	}

}
