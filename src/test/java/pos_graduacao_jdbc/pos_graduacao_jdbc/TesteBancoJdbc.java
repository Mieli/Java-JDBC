package pos_graduacao_jdbc.pos_graduacao_jdbc;

import java.util.List;
import java.util.Random;

import org.junit.Test;

import pos_graduacao.conexaojdbc.dao.UsuarioDAO;
import pos_graduacao.conexaojdbc.model.BeanUserFone;
import pos_graduacao.conexaojdbc.model.Telefone;
import pos_graduacao.conexaojdbc.model.Usuario;

public class TesteBancoJdbc {
	
	@Test
	public void initBanco() {
	
		Usuario user = new Usuario();
		UsuarioDAO dao = new UsuarioDAO();
		
		user.setNome("Sofia Loren ");
		user.setEmail("sofia@loren.com.br");
		dao.Salvar(user);			
		
	}
	
	@Test
	public void initListar() {
		
		UsuarioDAO dao = new UsuarioDAO();		
		try {			
			List<Usuario> listaDeUsuarios = dao.listar();
			
			for(Usuario user : listaDeUsuarios) {
				System.out.println(user);
				System.out.println("----------------------------");
			}			
		}catch (Exception e) {			
			e.printStackTrace();			
		}			
	}
	
	@Test
	public void initBuscarUser() {
		UsuarioDAO dao = new UsuarioDAO();
		Usuario user;
		try {
			
			user = dao.buscarUser(2L);
			System.out.println(user);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	@Test
	public void initAtualizarUsuario() {
		UsuarioDAO dao = new UsuarioDAO();
		Usuario novoUsuario = new Usuario();
		try {
			Usuario usuarioRegistradoNoBanco = dao.buscarUser(3L);
			novoUsuario.setId(usuarioRegistradoNoBanco.getId());
			novoUsuario.setNome("Tikinho Alterado via Teste no Java");
			novoUsuario.setEmail("tiko@tiko.com.br");
			dao.atualizar(novoUsuario);
		} catch (Exception e) {			
			e.printStackTrace();
		}		
	}
	
	@Test
	public void initRemoverUsuario() {
		try {
			UsuarioDAO dao = new UsuarioDAO();
			dao.remover(10L);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void intiSalvarTelefone() {
		
		for(int i = 1; i <=100; i++) {
		
			Random random = new Random();
			
			String numero = "(" + (random.nextInt(100)+i) +") 9"+ random.nextInt(1000) +"-"+ random.nextInt(1000);
			Integer id_usuario = random.nextInt(10);
			
			Telefone telefone = new Telefone();
			telefone.setNumero(numero);
			telefone.setTipo("celular");
			telefone.setUsuario_id(id_usuario.longValue());
			
			UsuarioDAO dao = new UsuarioDAO();
			dao.salvarTelefone(telefone);
			
		}	
	}
	
	@Test
	public void initPesquisarTelefonesDoUsuario() {
		
		UsuarioDAO dao = new UsuarioDAO();
		
		List<BeanUserFone> listaTel = dao.pesquisarTelefoneUsuario(2L);
		
		for (BeanUserFone bean : listaTel) {
			
			System.out.println(bean);
			System.out.println("-------------------------------");
		}
		
	}
	
	@Test
	public void excluirTelefonesDoUsuario() {
		
		UsuarioDAO dao = new UsuarioDAO();
		dao.removerTelefone(2L);
		
	}
	
	
}
