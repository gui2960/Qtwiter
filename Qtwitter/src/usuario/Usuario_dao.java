package usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jdbc.Connection_factory;

public class Usuario_dao {
	private Connection connection;
	public Usuario_dao() {}

	/*
	 * TRIGGER && FUNCTION IMPLEMENTADOS NO addUser(Usuario usuario){};
	 * 
	 * Motivos de não implementaçao nos outros métodos:
	 * 
	 * Ao adotar Trigger resultará na quase impossibilidade de migração de banco de dados﻿. Trigger utiliza linguagerm proprietária.
	 * 
	 * Queda de perfmormance. Por mais que você utilize condições para sua Trigger ser rodada ela será chamada para fazer o teste.
	 * 
	 *  Em um ambiente de segurança, data center por exemplo, dependerá de um DBA responsável para criar, alterar ou apagar a Trigger, 
	 *  visto que esses comandos são DDL.
	 *  
	 *  etc...
	 */
	
	public boolean addUser(Usuario usuario) {
		String sql = "INSERT INTO usuario(nome, email, senha) VALUES (?, ?, ?)";
		this.connection = new Connection_factory().getConnection();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, usuario.getNome());
			stmt.setString(2, usuario.getEmail());
			stmt.setString(3, usuario.getSenha());
			
			int qtdRowsAffected = stmt.executeUpdate();
			stmt.close();
			if (qtdRowsAffected > 0)
				return true;
			return false;
		} catch (SQLException stmt) {
			System.out.println(stmt.getMessage());
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean deleteUser(String email) {
		String sql = "DELETE FROM usuario WHERE email = ?";
		this.connection = new Connection_factory().getConnection();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, email);
			int qtdRowsAffected = stmt.executeUpdate();
			stmt.close();
			if (qtdRowsAffected > 0)
				return true;
			return false;
		} catch (SQLException e) {
			 System.out.println("Usuario nao encontrado");
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean alterUser(Usuario usuario, String novoNome) {
		String sql = "UPDATE usuario SET nome = ? where email = ?";
		this.connection = new Connection_factory().getConnection();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1, novoNome);
			stmt.setString(2, usuario.getEmail());

			int qtdRowsAffected = stmt.executeUpdate();
			stmt.close();
			if (qtdRowsAffected > 0)
				return true;
			return false;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public ArrayList<Usuario> getListUser() {
		String sql = "SELECT * FROM usuario;";
		ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();

		this.connection = new Connection_factory().getConnection();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String nome = rs.getString("nome");
				String email = rs.getString("email");
				String senha = rs.getString("senha");

				Usuario user = new Usuario(nome, email, senha);

				listaUsuarios.add(user);
			}
			stmt.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listaUsuarios;
	}

	public ArrayList<Usuario> getListUserNome(String name) {
		String sql = "SELECT * FROM usuario where nome = ?;";
		ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
		this.connection = new Connection_factory().getConnection();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String nome = rs.getString("nome");
				String email = rs.getString("email");
				String senha = rs.getString("senha");

				Usuario user = new Usuario(nome, email, senha);

				listaUsuarios.add(user);
			}
			stmt.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listaUsuarios;
	}

	public Usuario getListUserEmail(String emailp) {
		String sql = "SELECT * FROM usuario where email = ?;";
		Usuario user = null;
		this.connection = new Connection_factory().getConnection();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, emailp);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			String nome = rs.getString("nome");
			String email = rs.getString("email");
			String senha = rs.getString("senha");
			user = new Usuario(nome, email, senha);
			stmt.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	public Usuario getNamePass(String name, String pass) {
		String sql = "SELECT * FROM usuario where nome = ? and senha = ?;";
		Usuario user = null;
		this.connection = new Connection_factory().getConnection();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setString(2, pass);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			String nome = rs.getString("nome");
			String email = rs.getString("email");
			String senha = rs.getString("senha");
			user = new Usuario(nome, email, senha);
			stmt.close();
		} catch (SQLException e) {
			// System.out.println(e.getMessage());
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	public ArrayList<Usuario> seguidores(String email_seguido) {
		String sql = "SELECT * FROM usuario U where U.email in "
				+ "(select email_segue from folow where email_seguido = ?);";
		ArrayList<Usuario> seguidores = new ArrayList<Usuario>();
		this.connection = new Connection_factory().getConnection();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, email_seguido);
			
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
			String nome = rs.getString("nome");
			String email = rs.getString("email");
			String senha = rs.getString("senha");
			Usuario user = new Usuario(nome, email, senha);
			seguidores.add(user);
			}
			stmt.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return seguidores;
	}
	
	public ArrayList<Usuario> seguidos(String email_seguidor) {
		String sql = "SELECT * FROM usuario U where U.email in "
				+ "(select email_seguido from folow where email_segue = ?);";
		ArrayList<Usuario> seguidos = new ArrayList<Usuario>();
		this.connection = new Connection_factory().getConnection();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, email_seguidor);
			
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
			String nome = rs.getString("nome");
			String email = rs.getString("email");
			String senha = rs.getString("senha");
			Usuario user = new Usuario(nome, email, senha);
			seguidos.add(user);
			}
			stmt.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return seguidos;
	}
	


public boolean alterSenhaUser(Usuario usuario, String novaSenha) {
	String sql = "UPDATE usuario SET senha = ? where email = ?";
	this.connection = new Connection_factory().getConnection();
	try {
		PreparedStatement stmt = connection.prepareStatement(sql);

		stmt.setString(1, novaSenha);
		stmt.setString(2, usuario.getEmail());

		int qtdRowsAffected = stmt.executeUpdate();
		stmt.close();
		if (qtdRowsAffected > 0)
			return true;
		return false;
	} catch (SQLException e) {
		System.err.println(e.getMessage());
	} finally {
		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return false;
}

public boolean alterEUser(Usuario usuario, String email) {
	String sql = "UPDATE usuario SET email = ? where email = ?";
	this.connection = new Connection_factory().getConnection();
	try {
		PreparedStatement stmt = connection.prepareStatement(sql);

		stmt.setString(1, email);
		stmt.setString(2, usuario.getEmail());

		int qtdRowsAffected = stmt.executeUpdate();
		stmt.close();
		if (qtdRowsAffected > 0)
			return true;
		return false;
	} catch (SQLException e) {
		System.err.println(e.getMessage());
	} finally {
		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return false;
}




}
