package model.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RelatorioTecnicoDTO {
	
	DateTimeFormatter formaterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private int idTecnico;
	private String nomeTecnico;
	private int qtdChamadosAtendido;
	private String tituloUltimoChamado;
	private LocalDate dataUltimoChamadoAtendido;
	
	public RelatorioTecnicoDTO(int idTecnico, String nomeTecnico, int qtdChamadosAtendido, 
			String tituloUltimoChamado, LocalDate dataUltimoChamadoAtendido) {
		super();
		this.idTecnico = idTecnico;
		this.nomeTecnico = nomeTecnico;
		this.qtdChamadosAtendido = qtdChamadosAtendido;
		this.tituloUltimoChamado = tituloUltimoChamado;
		this.dataUltimoChamadoAtendido = dataUltimoChamadoAtendido;
	}
	
	public RelatorioTecnicoDTO() {
		super();
	}

	public int getIdTecnico() {
		return idTecnico;
	}

	public void setIdTecnico(int idTecnico) {
		this.idTecnico = idTecnico;
	}

	public String getNomeTecnico() {
		return nomeTecnico;
	}

	public void setNomeTecnico(String nomeTecnico) {
		this.nomeTecnico = nomeTecnico;
	}

	public int getQtdChamadosAtendido() {
		return qtdChamadosAtendido;
	}

	public void setQtdChamadosAtendido(int qtdChamadosAtendido) {
		this.qtdChamadosAtendido = qtdChamadosAtendido;
	}

	public String getTituloUltimoChamado() {
		return tituloUltimoChamado;
	}

	public void setTituloUltimoChamado(String tituloUltimoChamado) {
		this.tituloUltimoChamado = tituloUltimoChamado;
	}

	public LocalDate getDataUltimoChamadoAtendido() {
		return dataUltimoChamadoAtendido;
	}

	public void setDataUltimoChamadoAtendido(LocalDate dataUltimoChamadoAtendido) {
		this.dataUltimoChamadoAtendido = dataUltimoChamadoAtendido;
	}
	
	public void imprimir() {
		System.out.printf("\n%10s  %-30s  %33s  %-40s %-40s "
				,this.getIdTecnico(), this.getNomeTecnico(), this.getQtdChamadosAtendido(), this.getTituloUltimoChamado(), validarData(this.getDataUltimoChamadoAtendido()));
	}
	
	private String validarData(LocalDate data) {
		String resultado = "";
		if(data != null) {
			resultado = data.format(formaterDate);
		}
		
		return resultado;
	}

}
