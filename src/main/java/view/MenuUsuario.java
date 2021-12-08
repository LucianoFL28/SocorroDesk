package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import controller.UsuarioController;
import model.vo.TipoUsuarioVO;
import model.vo.UsuarioVO;

public class MenuUsuario {

	Scanner teclado = new Scanner(System.in);
	DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private static final int OPCAO_MENU_CADASTRAR_USUARIO = 1;
	private static final int OPCAO_MENU_CONSULTAR_USUARIO = 2;
	private static final int OPCAO_MENU_ATUALIZAR_USUARIO = 3;
	private static final int OPCAO_MENU_EXCLUIR_USUARIO = 4;
	private static final int OPCAO_MENU_USUARIO_SAIR = 9;
	
	private static final int OPCAO_MENU_CONSULTAR_TODOS_USUARIOS = 1;
	private static final int OPCAO_MENU_CONSULTAR_UM_USUARIO = 2;
	private static final int OPCAO_MENU_CONSULTAR_USUARIO_SAIR = 9;

	public void apresentarMenuUsuario() {
		int opcao = this.apresentarOpcoesMenu();
		while (opcao != OPCAO_MENU_USUARIO_SAIR) {
			switch (opcao) {
				case OPCAO_MENU_CADASTRAR_USUARIO: {
					UsuarioVO usuarioVO = new UsuarioVO();
					this.cadastrarUsuario(usuarioVO);
					break;
				}
				case OPCAO_MENU_CONSULTAR_USUARIO: {
					this.consultarUsuario();
					break;
				}
				case OPCAO_MENU_ATUALIZAR_USUARIO: {
					this.atualizarUsuario();
					break;
				}
				case OPCAO_MENU_EXCLUIR_USUARIO: {
					this.excluirUsuario();
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
	
	private int apresentarOpcoesMenu() {
		System.out.println("\n---- Sistema Socorro Desk ----");
		System.out.println("\n---- Menu Cadastro de Usuários ----");
		System.out.println(OPCAO_MENU_CADASTRAR_USUARIO + " - Cadastrar Usuário");
		System.out.println(OPCAO_MENU_CONSULTAR_USUARIO + " - Consultar Usuário");
		System.out.println(OPCAO_MENU_ATUALIZAR_USUARIO + " - Atualizar Usuário");
		System.out.println(OPCAO_MENU_EXCLUIR_USUARIO + " - Excluir Usuário");
		System.out.println(OPCAO_MENU_USUARIO_SAIR + " - Sair");
		System.out.print("\nDigite a opção: ");
		return Integer.parseInt(teclado.nextLine());
	}

	public void cadastrarNovoUsuario(UsuarioVO usuarioVO) {
		this.cadastrarUsuario(usuarioVO);
	}

	private void cadastrarUsuario(UsuarioVO usuarioVO) {
		if(usuarioVO.getTipoUsuarioVO() == null) {
			do {
				usuarioVO.setTipoUsuarioVO(TipoUsuarioVO.getTipoUsuarioVOPorValor(this.apresentarOpcoesTipoUsuario()));
			} while(usuarioVO.getTipoUsuarioVO() == null);
		}
		System.out.print("\nDigite o nome: ");
		usuarioVO.setNome(teclado.nextLine());
		System.out.print("Digite o cpf: ");
		usuarioVO.setCpf(teclado.nextLine());
		System.out.print("Digite o e-mail: ");
		usuarioVO.setEmail(teclado.nextLine());
		usuarioVO.setDataCadastro(LocalDate.now());
		System.out.print("Digite o login: ");
		usuarioVO.setLogin(teclado.nextLine());
		System.out.print("Digite a senha: ");
		usuarioVO.setSenha(teclado.nextLine());
		
		if(this.validarCamposCadastro(usuarioVO)) {
			UsuarioController usuarioController = new UsuarioController();
			usuarioVO = usuarioController.cadastrarUsuarioController(usuarioVO);
			
			if(usuarioVO.getIdUsuario() != 0) {
				System.out.println("Usuário cadastrado com sucesso!");
			}else {
				System.out.println("Não foi possível cadastrar o Usuário!");
			}
			
		}
	}

	private int apresentarOpcoesTipoUsuario() {
		UsuarioController usuarioController = new UsuarioController();
		ArrayList<TipoUsuarioVO> tipoUsuariosVO = usuarioController.consultarTipoUsuariosController();
		System.out.println("\n---- Tipos de Usuários ----");
		System.out.println("Opções:");
		for(int i = 0; i < tipoUsuariosVO.size(); i++) {
			System.out.println(tipoUsuariosVO.get(i).getValor() + " - "+tipoUsuariosVO.get(i));
		}
		System.out.print("\nDigite uma opção: ");
		return Integer.parseInt(teclado.nextLine());
	}

	private boolean validarCamposCadastro(UsuarioVO usuarioVO) {
		boolean resultado = true;
		System.out.println();
		if(usuarioVO.getNome().isEmpty() || usuarioVO.getNome() == null) {
			System.out.println("O campo Nome é obrigatório!");
			resultado = !resultado;
		}
		if(usuarioVO.getCpf().isEmpty() || usuarioVO.getCpf() == null) {
			System.out.println("O campo CPF é obrigatório!");
			resultado = !resultado;
		}
		if(usuarioVO.getEmail().isEmpty() || usuarioVO.getEmail() == null) {
			System.out.println("O campo E-mail é obrigatório!");
			resultado = !resultado;
		}
		if(usuarioVO.getDataCadastro() == null) {
			System.out.println("O campo data de cadastro é obrigatório!");
			resultado = !resultado;
		}
		if(usuarioVO.getLogin().isEmpty() || usuarioVO.getLogin() == null) {
			System.out.println("O campo Login é obrigatório!");
			resultado = !resultado;
		}
		if(usuarioVO.getSenha().isEmpty() || usuarioVO.getSenha() == null) {
			System.out.println("O campo Senha é obrigatório!");
			resultado = !resultado;
		}
		return resultado;
	}

	private void consultarUsuario() {
		int opcao = this.apresentarOpcoesConsulta();
		UsuarioController usuarioController = new UsuarioController();
		while (opcao != OPCAO_MENU_CONSULTAR_USUARIO_SAIR) {
			switch (opcao) {
				case OPCAO_MENU_CONSULTAR_TODOS_USUARIOS: {
					opcao = OPCAO_MENU_CONSULTAR_USUARIO_SAIR;
					ArrayList<UsuarioVO> listaUsuariosVO = usuarioController.consultarTodosUsuariosController();
					System.out.println("\n-------- RESULTADO DA CONSULTA --------");
					System.out.printf("\n%3s  %-13s  %-20s  %-11s  %-20s  %-15s  %-15s  %-10s  %-10s  "
							,"ID", "TIPO USUÁRIO", "NOME", "CPF", "E-MAIL", "DATA CADASTRO", "DATA EXPIRAÇÃO", "LOGIN", "SENHA");
					for(UsuarioVO usuarioVO: listaUsuariosVO) {
						usuarioVO.imprimir();
					}
					System.out.println();
					break;
				}
				case OPCAO_MENU_CONSULTAR_UM_USUARIO: {
					opcao = OPCAO_MENU_CONSULTAR_USUARIO_SAIR;
					
					UsuarioVO usuarioVO = new UsuarioVO();
					System.out.print("\nInforme o código do usuário: ");
					usuarioVO.setIdUsuario(Integer.parseInt(teclado.nextLine()));
					
					usuarioVO = usuarioController.consultarUsuarioController(usuarioVO);
					System.out.println("\n-------- RESULTADO DA CONSULTA --------");
					System.out.printf("\n%3s  %-13s  %-20s  %-11s  %-20s  %-15s  %-15s  %-10s  %-10s  "
							,"ID", "TIPO USUÁRIO", "NOME", "CPF", "E-MAIL", "DATA CADASTRO", "DATA EXPIRAÇÃO", "LOGIN", "SENHA");
					usuarioVO.imprimir();
					System.out.println();
					break;
				}
				default: {
					System.out.println("\nOpção inválida!");
					opcao = this.apresentarOpcoesConsulta();
					break;
				}
			}
		}
	}

	private int apresentarOpcoesConsulta() {
		System.out.println("\nInforme o tipo de consulta a ser realizada");
		System.out.println(OPCAO_MENU_CONSULTAR_TODOS_USUARIOS + " - Consultar todos os usuários");
		System.out.println(OPCAO_MENU_CONSULTAR_UM_USUARIO + " - Consultar um usuário específico");
		System.out.println(OPCAO_MENU_CONSULTAR_USUARIO_SAIR + " - Sair");
		System.out.print("\nDigite uma opção: ");
		return Integer.parseInt(teclado.nextLine());
	}

	private void atualizarUsuario() {
		UsuarioVO usuarioVO = new UsuarioVO();
		System.out.print("\nDigite o código do usuário: ");
		usuarioVO.setIdUsuario(Integer.parseInt(teclado.nextLine()));
		do {
			usuarioVO.setTipoUsuarioVO(TipoUsuarioVO.getTipoUsuarioVOPorValor(this.apresentarOpcoesTipoUsuario()));
		} while(usuarioVO.getTipoUsuarioVO() == null);
		
		System.out.print("\nDigite o nome: ");
		usuarioVO.setNome(teclado.nextLine());
		System.out.print("Digite o cpf: ");
		usuarioVO.setCpf(teclado.nextLine());
		System.out.print("Digite o e-mail: ");
		usuarioVO.setEmail(teclado.nextLine());
		System.out.println("Digite a data de cadastro");
		usuarioVO.setDataCadastro(LocalDate.parse(teclado.nextLine(), dataFormatter));
		System.out.print("Digite o login: ");
		usuarioVO.setLogin(teclado.nextLine());
		System.out.print("Digite a senha: ");
		usuarioVO.setSenha(teclado.nextLine());
		
		if(this.validarCamposCadastro(usuarioVO)) {
			UsuarioController usuarioController = new UsuarioController();
			boolean resultado = usuarioController.atualizarUsuarioController(usuarioVO);
			
			if(resultado) {
				System.out.println("Usuário atualizado com sucesso!");
			}else {
				System.out.println("Não foi possível atualizar o Usuário!");
			}
		}
		
	}

	private void excluirUsuario() {
		UsuarioVO usuarioVO = new UsuarioVO();
		System.out.print("\nDigite o código do usuário: ");
		usuarioVO.setIdUsuario(Integer.parseInt(teclado.nextLine()));
		System.out.print("Digite a data de expiração no formato dd/MM/yyy: ");
		usuarioVO.setDataExpiracao(LocalDate.parse(teclado.nextLine(), dataFormatter));
		
		UsuarioController usuarioController = new UsuarioController();
		boolean resultado = usuarioController.excluirUsuarioController(usuarioVO);
		
		if(resultado) {
			System.out.println("\nUsuário excluído com sucesso!");
		}else {
			System.out.println("\nNão foi possível excluir o usuário!");
		}
		
	}

}
