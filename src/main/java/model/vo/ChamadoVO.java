package model.vo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ChamadoVO {
	
	DateTimeFormatter formaterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");	

	private int idChamado;
	private int idUsuario;
	private int idTecnico;
	private String titulo;
	private String descricao;
	private LocalDate dataAbertura;
	private String solucao;
	private LocalDate dataFechamento;
	
	public ChamadoVO(int idChamado, int idUsuario, int idTecnico, String titulo, String descricao,
			LocalDate dataAbertura, String solucao, LocalDate dataFechamento) {
		super();
		this.idChamado = idChamado;
		this.idUsuario = idUsuario;
		this.idTecnico = idTecnico;
		this.titulo = titulo;
		this.descricao = descricao;
		this.dataAbertura = dataAbertura;
		this.solucao = solucao;
		this.dataFechamento = dataFechamento;
	}
	
	public ChamadoVO() {
		super();
	}

	public int getIdChamado() {
		return idChamado;
	}

	public void setIdChamado(int idChamado) {
		this.idChamado = idChamado;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdTecnico() {
		return idTecnico;
	}

	public void setIdTecnico(int idTecnico) {
		this.idTecnico = idTecnico;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDate dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public String getSolucao() {
		return solucao;
	}

	public void setSolucao(String solucao) {
		this.solucao = solucao;
	}

	public LocalDate getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(LocalDate dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public void imprimir() {
		System.out.printf("\n%10s  %10s  %10s  %-30s  %-50s  %-15s  %-30s  %-15s "
				,this.getIdChamado(), this.getIdUsuario(), this.getIdTecnico(), this.getTitulo(), this.getDescricao(),
				validarData(this.getDataAbertura()), this.getSolucao(), validarData(this.getDataFechamento()));
				
	}
	
	private String validarData(LocalDate data) {
		String resultado = "";
		if(data != null) {
			resultado = data.format(formaterDate);
		}
		
		return resultado;
	}
	
}
