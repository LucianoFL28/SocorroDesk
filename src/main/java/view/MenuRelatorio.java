package view;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import controller.RelatorioController;
import model.dto.RelatorioTecnicoDTO;
import model.vo.UsuarioVO;;

public class MenuRelatorio {
	
	Scanner teclado = new Scanner(System.in);
	DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private static final int OPCAO_MENU_TECNICO_RELATORIO = 1;
	private static final int OPCAO_MENU_USUARIO_RELATORIO = 2;
	private static final int OPCAO_MENU_RELATORIO_SAIR = 9;

	public void apresentarMenuRelatorio(UsuarioVO usuarioVO) {
		int opcao = this.apresentarOpcoesMenu();
		RelatorioController relatorioController = new RelatorioController();
		while(opcao != OPCAO_MENU_RELATORIO_SAIR) {
			switch (opcao) {
				case OPCAO_MENU_TECNICO_RELATORIO:{
					opcao = OPCAO_MENU_RELATORIO_SAIR;
					ArrayList<RelatorioTecnicoDTO> listaRelatorioTecnicoDTO = relatorioController.consultarRelatorioTecnicoController(usuarioVO);
					System.out.println("\n-------- RELATÓRIO TÉCNICOS E RESOLUÇÃO DE CHAMADOS --------");
					System.out.printf("\n%10s  %-30s  %33s  %-40s %-40s "
							,"ID TECNICO", "NOME TÉCNICO", "QUANTIDADE DE CHAMADOS RESOLVIDOS", "TÍTULO DO ÚLTIMO CHAMADO ATENDIDO", "DATA ÚLTIMO CHAMADO ATENDIDO");
					for(RelatorioTecnicoDTO relatorio : listaRelatorioTecnicoDTO) {
						relatorio.imprimir();
					}
					break;
				}
				case OPCAO_MENU_USUARIO_RELATORIO:{
					opcao = OPCAO_MENU_RELATORIO_SAIR;
					break;
				}
				default:{
					System.out.println("\nOpção Inválida");
					opcao = apresentarOpcoesMenu();
				}
			}
		}
		
	}

	private int apresentarOpcoesMenu() {
		System.out.println("\n---- Sistema Socorro Desk ----");
		System.out.println("\n---- Menu de Relatórios ----");
		System.out.println(OPCAO_MENU_TECNICO_RELATORIO + " - Relatório de técnicos e resolução de chamados");
		System.out.println(OPCAO_MENU_USUARIO_RELATORIO + " - Relatório de usuários e chamados abertos");
		System.out.println(OPCAO_MENU_RELATORIO_SAIR + " - Sair");
		System.out.print("\nDigite a opção: ");
		return Integer.parseInt(teclado.nextLine());
	}

}
