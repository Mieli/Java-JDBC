package pos_graduacao.conexaojdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pos_graduacao.conexaojdbc.SingleConnection;
import pos_graduacao.conexaojdbc.model.BeanUserFone;
import pos_graduacao.conexaojdbc.model.Telefone;
import pos_graduacao.conexaojdbc.model.Usuario;

public class UsuarioDAO {
	
	private Connection connection;
	
	public UsuarioDAO() {
		connection = SingleConnection.getConnection();
	}
	
	public void Salvar(Usuario user) {
		
		try {
			
			String sql = "INSERT INTO usuarios (nome, email) VALUES (?,?)";
			PreparedStatement prepare = connection.prepareStatement(sql);
			prepare.setString(1, user.getNome());
			prepare.setString(2, user.getEmail());
			prepare.execute();		
			connection.commit();
			
		}catch (Exception e) {
			try {
				connection.rollback();
			}catch (Exception ex) {
				ex.printStackTrace();
			}	
			e.printStackTrace();			
		}		
	}
	
	public void salvarTelefone(Telefone telefone) {
		try {
			
			String sql = "INSERT INTO telefones (numero, tipo, usuario_id) VALUES (?, ?, ?)";
			
			PreparedStatement prepare = connection.prepareStatement(sql);
			prepare.setString(1, telefone.getNumero());
			prepare.setString(2, telefone.getTipo());
			prepare.setLong(3, telefone.getUsuario_id());
			prepare.execute();
			
			
			connection.commit();
			
			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	public List<Usuario> listar() throws Exception{
		
		List<Usuario> lista = new ArrayList<Usuario>();
		String sql = "SELECT * FROM usuarios";		
	
			PreparedStatement prepare = connection.prepareStatement(sql);
			ResultSet resultado = prepare.executeQuery();
			
			while( resultado.next() ) {
				Usuario user = new Usuario();
				user.setId(resultado.getLong("id"));
				user.setNome(resultado.getString("nome"));
				user.setEmail(resultado.getString("email"));
				
				lista.add(user);
			}		
		return lista;		
	}
	
	
	
	public Usuario buscarUser(Long id) throws Exception {			
		Usuario user = new Usuario();
		String sql = "SELECT * FROM usuarios WHERE id= " +id;
	
		PreparedStatement prepare = connection.prepareStatement(sql);
		ResultSet resultado = prepare.executeQuery();
		
		while( resultado.next() ) {
			user.setId(resultado.getLong("id"));
			user.setNome(resultado.getString("nome"));
			user.setEmail(resultado.getString("email"));
			
		}			
		return user;		
	}
	
	public List<BeanUserFone> pesquisarTelefoneUsuario(Long idUsuario){
		
		List<BeanUserFone> listaTelefoneUsuario = new ArrayList<BeanUserFone>();
		
		String sql = "SELECT nome, email, numero, tipo FROM telefones as tel ";
		   	   sql += "inner join usuarios as user ";
		   	   sql += "on tel.usuarios_id = user.id ";
		   	   sql += "where user.id = "+ idUsuario;
		   	   		
		try {
			
			PreparedStatement prepare = connection.prepareStatement(sql);
			ResultSet resultado = prepare.executeQuery();
			
			while( resultado.next() ) {
				
				BeanUserFone beanUserFone = new BeanUserFone();
				beanUserFone.setNome(resultado.getString("nome"));
				beanUserFone.setEmail(resultado.getString("email"));
				beanUserFone.setNumero(resultado.getString("numero"));
				beanUserFone.setTipo(resultado.getString("tipo"));
				
				listaTelefoneUsuario.add(beanUserFone);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return listaTelefoneUsuario; 
	}
	
	
	public void atualizar(Usuario usuario) {		
		String sql = "UPDATE usuarios SET nome = ?, email = ? WHERE id = ?";
		try { 
			
			PreparedStatement prepare = connection.prepareStatement(sql);
			prepare.setString(1, usuario.getNome());
			prepare.setString(2, usuario.getEmail());
			prepare.setLong(3, usuario.getId());
			prepare.execute();
			connection.commit();
			
		}catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}		
	}
	
	public void remover(Long id) {	
		
		String sql = "DELETE FROM usuarios WHERE id = ?";		
		try {			
			PreparedStatement prepare = connection.prepareStatement(sql);
			prepare.setLong(1, id);
			prepare.execute();
			connection.commit();			
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}		
	}
	
	public void removerTelefone(Long idUser) {
		
		String sqlFone = "DELETE FROM telefones WHERE usuarios_id= "+ idUser;
		String sqlUser = "DELETE FROM usuarios WHERE id = "+idUser;
		
		try {
			
			//exclui os registros filhos da tabela com o id do pai
			PreparedStatement prepara = connection.prepareStatement(sqlFone);
			prepara.executeUpdate();
			connection.commit();
			
			//exclui o registro pai
			prepara = connection.prepareStatement(sqlUser);
			prepara.executeUpdate();
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
		}		
	}
	
}
