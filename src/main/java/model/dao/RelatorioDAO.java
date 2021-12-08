package model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import model.dto.RelatorioTecnicoDTO;
import model.vo.ChamadoVO;
import model.vo.TipoUsuarioVO;

public class RelatorioDAO {

	DateTimeFormatter formaterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public boolean consultarAdministrador(int idUsuario) {
		Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        ResultSet resultado = null;
        TipoUsuarioVO id = TipoUsuarioVO.USUARIO;
        boolean retorno = false;

        String query = "SELECT descricao " + 
        		"FROM tipousuario " + 
        		"inner join usuario on " + 
        		"usuario.idtipousuario = tipousuario.idtipousuario " + 
        		"WHERE idusuario = "+idUsuario;
        try {
			resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				id = TipoUsuarioVO.valueOf(resultado.getString(1));
			}
			if(id.getValor() == 1) {
				retorno = true;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query para consultar administrador.");
			System.out.println("Erro: "+e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
        
		return retorno;
	}

	public ArrayList<RelatorioTecnicoDTO> consultarRelatorioTecnicoDAO() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;

		ArrayList<RelatorioTecnicoDTO> listaRelatorioTecnico = new ArrayList<RelatorioTecnicoDTO>();

		String query =
				"SELECT " +
						"usuario.idusuario as idusuario, usuario.nome, COUNT(chamados.idchamado),  ("+
						"SELECT chamados.titulo " +
						"FROM chamados " +
						"INNER JOIN usuario on " +
						"usuario.idusuario = chamados.idtecnico "+
						"WHERE chamados.idtecnico = usuario.idusuario " +
						"ORDER BY chamados.idchamado DESC LIMIT 1"+
						") as titulo"+
						", (" +
						"SELECT chamados.datafechamento " +
						"FROM chamados " +
						"INNER JOIN usuario on " +
						"usuario.idusuario = chamados.idtecnico "+
						"WHERE chamados.idtecnico = usuario.idusuario " +
						"ORDER BY chamados.idchamado DESC LIMIT 1"+
						") as data_ultimo_chamado"+
						"FROM usuario " +
						"INNER JOIN chamados on " +
						"usuario.idusuario = chamados.idtecnico " +
						"GROUP BY " +
						"usuario.idusuario, usuario.nome, titulo, data_ultimo_chamado";
		try{
			resultado = stmt.executeQuery(query);
			while(resultado.next()) {
				RelatorioTecnicoDTO relatorio = new RelatorioTecnicoDTO();
				relatorio.setIdTecnico(Integer.parseInt(resultado.getString(1)));
				relatorio.setNomeTecnico(resultado.getString(2));
				relatorio.setQtdChamadosAtendido(Integer.parseInt(resultado.getString(3)));
				relatorio.setTituloUltimoChamado(resultado.getString(4));
				if(resultado.getString(5) != null){
					relatorio.setDataUltimoChamadoAtendido(LocalDate.parse(resultado.getString(5), formaterDate));
				}
				listaRelatorioTecnico.add(relatorio);
			}
		} catch (SQLException e){
			System.out.println("\nErro ao excutar a query de consulta do relatório sobre os técnicos");
			System.out.println(e.getMessage());
		}finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listaRelatorioTecnico;
	}

    public ArrayList<RelatorioTecnicoDTO> consultarRelatorioTecnicoParticularDAO(int idUsuario) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;

		ArrayList<RelatorioTecnicoDTO> listaRelatorioTecnico = new ArrayList<RelatorioTecnicoDTO>();
		String query =
				"SELECT " +
					"usuario.idusuario, usuario.nome, COUNT(chamados.idchamado),  ("+
						"SELECT chamados.titulo " +
						"FROM chamados " +
						"WHERE chamados.idtecnico = " +idUsuario+" " +
						"ORDER BY chamados.idchamado DESC LIMIT 1"+
							") as titulo"+
						", (" +
						"SELECT chamados.datafechamento " +
						"FROM chamados " +
						"WHERE chamados.idtecnico = " +idUsuario+" " +
						"ORDER BY chamados.idchamado DESC LIMIT 1" +
						") as data_ultimo_chamado"+
				"FROM usuario " +
				"INNER JOIN chamados on " +
					"usuario.idusuario = chamados.idtecnico " +
				"WHERE " +
					"usuario.idusuario = "+idUsuario+" " +
				"GROUP BY " +
					"usuario.idusuario, usuario.nome, titulo, data_ultimo_chamado";
		try{
			resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				RelatorioTecnicoDTO relatorio = new RelatorioTecnicoDTO();
				relatorio.setIdTecnico(Integer.parseInt(resultado.getString(1)));
				relatorio.setNomeTecnico(resultado.getString(2));
				relatorio.setQtdChamadosAtendido(Integer.parseInt(resultado.getString(3)));
				relatorio.setTituloUltimoChamado(resultado.getString(4));
				if(resultado.getString(5) != null){
					relatorio.setDataUltimoChamadoAtendido(LocalDate.parse(resultado.getString(5), formaterDate));
				}
				listaRelatorioTecnico.add(relatorio);
			}
		} catch (SQLException e){
			System.out.println("\nErro ao excutar a query de consulta do relatório pessoal do técnico");
			System.out.println(e.getMessage());
		}finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listaRelatorioTecnico;
    }
}
