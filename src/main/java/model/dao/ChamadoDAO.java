package model.dao;

import model.vo.ChamadoVO;
import model.vo.UsuarioVO;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ChamadoDAO {
	
	DateTimeFormatter formaterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ChamadoVO cadastrarChamadoDAO(ChamadoVO chamadoVO) {
        String query = "INSERT INTO chamados (idusuario, titulo, descricao, dataabertura) "+
                "VALUES (?,?,?,?)";

        Connection conn = Banco.getConnection();
        PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);

        try{

            pstmt.setInt(1, chamadoVO.getIdUsuario());
            pstmt.setString(2, chamadoVO.getTitulo());
            pstmt.setString(3, chamadoVO.getDescricao());
            pstmt.setObject(4, chamadoVO.getDataAbertura());
            pstmt.execute();

            ResultSet resultado = pstmt.getGeneratedKeys();

            if(resultado.next()) {
                chamadoVO.setIdChamado(resultado.getInt(1));
            }
        }catch (SQLException e){
            System.out.println("Erro ao executar a query de cadastro do chamado.");
            System.out.println("Erro: "+e.getMessage());
        }finally {
            Banco.closeStatement(pstmt);
            Banco.closeConnection(conn);
        }

        return chamadoVO;
    }

    public boolean verificarExistenciaPorIdChamadoDAO(int idChamado) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        ResultSet resultado = null;
        boolean retorno = false;

        String query = "SELECT idchamado FROM chamados WHERE idchamado = "+idChamado;

        try {
            resultado = stmt.executeQuery(query);
            if(resultado.next()) {
                retorno = true;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao executar a query que verifica a existência de chamado por ID.");
            System.out.println("Erro: "+e.getMessage());
        } finally {
            Banco.closeResultSet(resultado);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }

        return retorno;
    }

    public boolean verificarDonoPorIdUsuarioDAO(ChamadoVO chamadoVO) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        ResultSet resultado = null;
        boolean retorno = false;

        String query = "SELECT idchamado FROM chamados WHERE idchamado = "+chamadoVO.getIdChamado()+" AND idusuario = "+chamadoVO.getIdUsuario();

        try {
            resultado = stmt.executeQuery(query);
            if(resultado.next()) {
                retorno = true;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao executar a query que verifica se o chamado pertence ao usuário por ID.");
            System.out.println("Erro: "+e.getMessage());
        } finally {
            Banco.closeResultSet(resultado);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }

        return retorno;
    }

    public boolean verificarChamadoAbertoDAO(ChamadoVO chamadoVO) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        ResultSet resultado = null;
        boolean retorno = false;

        String query = "SELECT idchamado FROM chamados WHERE idchamado = "+chamadoVO.getIdChamado()+" AND datafechamento is null";

        try {
            resultado = stmt.executeQuery(query);
            if(resultado.next()) {
                retorno = true;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao executar a query que verifica se o chamado está aberto.");
            System.out.println("Erro: "+e.getMessage());
        } finally {
            Banco.closeResultSet(resultado);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }

        return retorno;
    }

    public boolean excluirChamadoDAO(ChamadoVO chamadoVO) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);

        boolean retorno = false;

        String query = "DELETE FROM chamados WHERE IDCHAMADO = "+chamadoVO.getIdChamado();

        try {
            if(stmt.executeUpdate(query) == 1) {
                retorno = true;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao executar a query de exclusão do chamado.");
            System.out.println("Erro: "+e.getMessage());
        } finally {
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }

        return retorno;
    }

    public boolean atualizarChamadoDAO(ChamadoVO chamadoVO) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        boolean retorno = false;

        String query = "UPDATE chamados SET " +
                "idusuario = " + chamadoVO.getIdUsuario() +
                ", titulo = '" + chamadoVO.getTitulo() +
                "', descricao = '" + chamadoVO.getDescricao() +
                "', dataabertura = '" + chamadoVO.getDataAbertura() +
                "' WHERE idchamado = "+ chamadoVO.getIdChamado();
                ;

        try {
            if(stmt.executeUpdate(query) == 1) {
                retorno = true;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao executar a query de atualização do chamado.");
            System.out.println("Erro: "+e.getMessage());
        } finally {
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }

        return retorno;
    }

	public ArrayList<ChamadoVO> consultarTodosChamadosUsuarioDAO(ChamadoVO chamadoVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		
		ArrayList<ChamadoVO> listaChamadosVO = new ArrayList<ChamadoVO>();
		
		String query = "SELECT idchamado, idusuario, idtecnico, titulo, descricao, dataabertura, solucao, datafechamento "
				+"FROM chamados WHERE idusuario = "+chamadoVO.getIdUsuario();
		
		try {
			resultado = stmt.executeQuery(query);
			while(resultado.next()) {
				ChamadoVO chamado = new ChamadoVO();
				chamado.setIdChamado(Integer.parseInt(resultado.getString(1)));
				chamado.setIdUsuario(Integer.parseInt(resultado.getString(2)));
				if(resultado.getString(3) != null) {
					chamado.setIdTecnico(Integer.parseInt(resultado.getString(3)));
				}else {
					chamado.setIdTecnico(0);
				}
				chamado.setTitulo(resultado.getString(4));
				chamado.setDescricao((resultado.getString(5)));
				chamado.setDataAbertura(LocalDate.parse(resultado.getString(6), formaterDate));
				if(resultado.getString(7) == null) {
					chamado.setSolucao("Não resolvido");
				}else {
					chamado.setSolucao(resultado.getString(7));
				}
				if(resultado.getString(8) != null) {
					chamado.setDataFechamento(LocalDate.parse(resultado.getString(8), formaterDate));
				}
				listaChamadosVO.add(chamado);
			}
			
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query de consulta de todos os usuários.");
			System.out.println("Erro: "+e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		
		return listaChamadosVO;
	}

	public ArrayList<ChamadoVO> consultarChamadosAbertosUsuarioDAO(ChamadoVO chamadoVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		
		ArrayList<ChamadoVO> listaChamadosVO = new ArrayList<ChamadoVO>();
		
		String query = "SELECT idchamado, idusuario, idtecnico, titulo, descricao, dataabertura, solucao, datafechamento "
				+"FROM chamados WHERE idusuario = "+chamadoVO.getIdUsuario()+" AND datafechamento is null";
		
		try {
			resultado = stmt.executeQuery(query);
			while(resultado.next()) {
				ChamadoVO chamado = new ChamadoVO();
				chamado.setIdChamado(Integer.parseInt(resultado.getString(1)));
				chamado.setIdUsuario(Integer.parseInt(resultado.getString(2)));
				if(resultado.getString(3) != null) {
					chamado.setIdTecnico(Integer.parseInt(resultado.getString(3)));
				}else {
					chamado.setIdTecnico(0);
				}
				chamado.setTitulo(resultado.getString(4));
				chamado.setDescricao((resultado.getString(5)));
				chamado.setDataAbertura(LocalDate.parse(resultado.getString(6), formaterDate));
				if(resultado.getString(7) == null) {
					chamado.setSolucao("Não resolvido");
				}else {
					chamado.setSolucao(resultado.getString(7));
				}
				if(resultado.getString(8) != null) {
					chamado.setDataFechamento(LocalDate.parse(resultado.getString(8), formaterDate));
				}
				listaChamadosVO.add(chamado);
			}
			
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query de consulta de todos os usuários.");
			System.out.println("Erro: "+e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		
		return listaChamadosVO;
	}

	public ArrayList<ChamadoVO> consultarChamadosFechadosDAO(ChamadoVO chamadoVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		
		ArrayList<ChamadoVO> listaChamadosVO = new ArrayList<ChamadoVO>();
		
		String query = "SELECT idchamado, idusuario, idtecnico, titulo, descricao, dataabertura, solucao, datafechamento "
				+"FROM chamados WHERE idusuario = "+chamadoVO.getIdUsuario()+" AND datafechamento is not null";
		
		try {
			resultado = stmt.executeQuery(query);
			while(resultado.next()) {
				ChamadoVO chamado = new ChamadoVO();
				chamado.setIdChamado(Integer.parseInt(resultado.getString(1)));
				chamado.setIdUsuario(Integer.parseInt(resultado.getString(2)));
				if(resultado.getString(3) != null) {
					chamado.setIdTecnico(Integer.parseInt(resultado.getString(3)));
				}else {
					chamado.setIdTecnico(0);
				}
				chamado.setTitulo(resultado.getString(4));
				chamado.setDescricao(resultado.getString(5));
				chamado.setDataAbertura(LocalDate.parse(resultado.getString(6), formaterDate));
				if(resultado.getString(7) == null) {
					chamado.setSolucao("Não resolvido");
				}else {
					chamado.setSolucao(resultado.getString(7));
				}
				if(resultado.getString(8) != null) {
					chamado.setDataFechamento(LocalDate.parse(resultado.getString(8), formaterDate));
				}
				listaChamadosVO.add(chamado);
			}
			
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query de consulta de todos os usuários.");
			System.out.println("Erro: "+e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		
		return listaChamadosVO;
	}

	public ChamadoVO atenderChamadoDAO(ChamadoVO chamadoVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ChamadoVO retorno = new ChamadoVO();
		String query = "UPDATE chamados SET idtecnico = " + chamadoVO.getIdTecnico()
					+ ", solucao = '" + chamadoVO.getSolucao()
					+"', datafechamento = '" + chamadoVO.getDataFechamento()
					+"' WHERE idChamado = " + chamadoVO.getIdChamado();
		try {
			if(stmt.executeUpdate(query) == 1) {
				retorno = this.consultarChamadoAtendido(chamadoVO.getIdChamado());
			}
		} catch (SQLException e){
			System.out.println("Erro ao executar a Query de Atendimento do Chamado.");
			System.out.println("Erro: "+e.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		
		return retorno;
	}

	private ChamadoVO consultarChamadoAtendido(int idChamado) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		ChamadoVO chamadoVO = new ChamadoVO();		
		
		String query = "SELECT idchamado, idusuario, idtecnico, titulo, descricao, dataabertura, solucao, datafechamento "
				+"FROM chamados WHERE idchamado = "+idChamado;
		
		try {
			resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				chamadoVO.setIdChamado(Integer.parseInt(resultado.getString(1)));
				chamadoVO.setIdUsuario(Integer.parseInt(resultado.getString(2)));
				chamadoVO.setIdTecnico(Integer.parseInt(resultado.getString(3)));
				chamadoVO.setTitulo(resultado.getString(4));
				chamadoVO.setDescricao(resultado.getString(5));
				chamadoVO.setDataAbertura(LocalDate.parse(resultado.getString(6), formaterDate));
				chamadoVO.setSolucao(resultado.getString(7));
				chamadoVO.setDataFechamento(LocalDate.parse(resultado.getString(8), formaterDate));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a Query de Consulta de chamado por ID");
			System.out.println("Erro: "+e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		
		return chamadoVO;
	}

	public ArrayList<ChamadoVO> listarChamadosAbertosDAO() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		
		ArrayList<ChamadoVO> listaChamadosVO = new ArrayList<ChamadoVO>();
		
		String query = "SELECT idchamado, idusuario, idtecnico, titulo, descricao, dataabertura, solucao, datafechamento "
				+"FROM chamados WHERE datafechamento is null";
		
		try {
			resultado = stmt.executeQuery(query);
			while(resultado.next()) {
				ChamadoVO chamado = new ChamadoVO();
				chamado.setIdChamado(Integer.parseInt(resultado.getString(1)));
				chamado.setIdUsuario(Integer.parseInt(resultado.getString(2)));
				if(resultado.getString(3) != null) {
					chamado.setIdTecnico(Integer.parseInt(resultado.getString(3)));
				}else {
					chamado.setIdTecnico(0);
				}
				chamado.setTitulo(resultado.getString(4));
				chamado.setDescricao((resultado.getString(5)));
				chamado.setDataAbertura(LocalDate.parse(resultado.getString(6), formaterDate));
				if(resultado.getString(7) == null) {
					chamado.setSolucao("Não resolvido");
				}else {
					chamado.setSolucao(resultado.getString(7));
				}
				if(resultado.getString(8) != null) {
					chamado.setDataFechamento(LocalDate.parse(resultado.getString(8), formaterDate));
				}
				listaChamadosVO.add(chamado);
			}
			
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query de consulta de todos os usuários.");
			System.out.println("Erro: "+e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		
		return listaChamadosVO;
	}

	public ArrayList<ChamadoVO> listarChamadosFechadosTecnicoDAO(UsuarioVO usuarioVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		
		ArrayList<ChamadoVO> listaChamadosVO = new ArrayList<ChamadoVO>();
		
		String query = "SELECT idchamado, idusuario, idtecnico, titulo, descricao, dataabertura, solucao, datafechamento "
				+"FROM chamados WHERE idtecnico = "+usuarioVO.getIdUsuario();
		
		try {
			resultado = stmt.executeQuery(query);
			while(resultado.next()) {
				ChamadoVO chamado = new ChamadoVO();
				chamado.setIdChamado(Integer.parseInt(resultado.getString(1)));
				chamado.setIdUsuario(Integer.parseInt(resultado.getString(2)));
				if(resultado.getString(3) != null) {
					chamado.setIdTecnico(Integer.parseInt(resultado.getString(3)));
				}else {
					chamado.setIdTecnico(0);
				}
				chamado.setTitulo(resultado.getString(4));
				chamado.setDescricao(resultado.getString(5));
				chamado.setDataAbertura(LocalDate.parse(resultado.getString(6), formaterDate));
				if(resultado.getString(7) == null) {
					chamado.setSolucao("Não resolvido");
				}else {
					chamado.setSolucao(resultado.getString(7));
				}
				if(resultado.getString(8) != null) {
					chamado.setDataFechamento(LocalDate.parse(resultado.getString(8), formaterDate));
				}
				listaChamadosVO.add(chamado);
			}
			
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query de consulta de todos os usuários.");
			System.out.println("Erro: "+e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		
		return listaChamadosVO;
	}
}
