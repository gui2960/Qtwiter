package postagem;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import jdbc.Connection_factory;

public class Postagem_dao {
	private Connection connection;

	// crud
	public Postagem_dao() {
	}

	public boolean insercaoPostagem(Postagem post) {
		// Postagem postagem = new Postagem(texto, email);
		String sql = "INSERT INTO postagem (data, hora, texto, email_usuario) VALUES(?,?,?,?);";
		this.connection = new Connection_factory().getConnection();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setDate(1, (Date) post.getData());
			stmt.setTime(2, post.getHora());
			stmt.setString(3, post.getTexto());
			stmt.setString(4, post.getEmail_usuario());

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

	public boolean deletarPostagem(String texto, String email) {
		Scanner scan = new Scanner(System.in);
		ArrayList<Postagem> possiveis = new ArrayList<Postagem>();
		Postagem post;
		String sql = "SELECT * FROM postagem where texto LIKE ? AND email_usuario = ?;";
		this.connection = new Connection_factory().getConnection();

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, texto);
			stmt.setString(2, email);
			ResultSet rs = stmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Date data = rs.getDate("Data");
				java.sql.Time hora = rs.getTime("Hora");
				String text = rs.getString("texto");
				post = new Postagem(data, hora, text, email);
				possiveis.add(i, post);
				i++;
			}
			if (possiveis == null)
				return false;
			System.out.println("Selecione pelo número da postagem qual gostaria de apagar: ");
			for (Postagem p : possiveis) {
				System.out.println(possiveis.indexOf(p) + ": ");
				System.out.println(p);
			}

			int select = scan.nextInt();
			if (select == -1)
				return false;
			String sql1 = "DELETE FROM postagem WHERE texto = ? and email_usuario = ?";
			PreparedStatement stmt1 = connection.prepareStatement(sql1);
			stmt1.setString(1, possiveis.get(select).getTexto());
			stmt1.setString(2, email);
			int qtdRowsAffected = stmt1.executeUpdate();
			stmt1.close();
			if (qtdRowsAffected > 0)
				return true;
			return false;

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		return false;
	}

	public ArrayList<Postagem> list_my_Posts(String email) {
		String sql = "SELECT * FROM postagem where email_usuario = ?;";
		ArrayList<Postagem> minhas_postagens = new ArrayList<Postagem>();
		this.connection = new Connection_factory().getConnection();

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Date data = rs.getDate("Data");
				java.sql.Time hora = rs.getTime("Hora");
				String texto = rs.getString("texto");
				Postagem post = new Postagem(data, hora, texto, email);
				minhas_postagens.add(post);
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

		return minhas_postagens;
	}

	/*
	 * 
	 * VIEW IMPLEMENTADO NA FUNÇÃO posts_Today();
	 * 
	 * 
	 */

	public void posts_Today() {
		String sql = "SELECT * FROM postagens_dia";
		this.connection = new Connection_factory().getConnection();

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				System.out.println("Nome: " + rs.getString("nome"));
				System.out.println(rs.getString("texto"));
				System.out.println();

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

	}

	public boolean list_Postagem_seguido() {
		return false;
	}

	public ArrayList<Postagem> feed(String email_segue) {
		String sql = "select * from Postagem P where P.email_usuario in ( select email_seguido from folow where email_segue = ?) OR P.email_usuario = ?;";
		ArrayList<Postagem> feed = new ArrayList<Postagem>();
		this.connection = new Connection_factory().getConnection();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, email_segue);
			stmt.setString(2, email_segue);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Date data = rs.getDate("Data");
				java.sql.Time hora = rs.getTime("Hora");
				String texto = rs.getString("texto");
				String email = rs.getString("email_usuario");
				Postagem post = new Postagem(data, hora, texto, email);
				feed.add(post);
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
		return feed;
	}

	public ArrayList<Postagem> pesqPostByTex(String texto, String email_user) {
		ArrayList<Postagem> possiveis = new ArrayList<Postagem>();
		Postagem post;
		String sql = "SELECT * FROM postagem where texto LIKE ? AND email_usuario = ?;";
		this.connection = new Connection_factory().getConnection();

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, texto);
			stmt.setString(2, email_user);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Date data = rs.getDate("Data");
				java.sql.Time hora = rs.getTime("Hora");
				String text = rs.getString("texto");
				post = new Postagem(data, hora, text, email_user);
				possiveis.add(post);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		return possiveis;

	}

	public boolean alterTextPost(String texto, String email) {
		Scanner scan = new Scanner(System.in);
		ArrayList<Postagem> possiveis = new ArrayList<Postagem>();
		String novoTexto;
		String sql = "SELECT * FROM postagem where texto LIKE ? AND email_usuario = ?;";
		this.connection = new Connection_factory().getConnection();

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, texto);
			stmt.setString(2, email);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Date data = rs.getDate("Data");
				java.sql.Time hora = rs.getTime("Hora");
				String text = rs.getString("texto");
				Postagem post = new Postagem(data, hora, text, email);
				possiveis.add(post);
			}
			if (possiveis == null)
				return false;
			int i = 0;
			System.out.println("Selecione pelo número da postagem qual gostaria de editar: ");
			for (Postagem p : possiveis) {
				System.out.println(i + ": ");
				System.out.println(p);
				i++;
			}
			int select = Integer.parseInt(scan.nextLine());
			if (select == -1)
				return false;
			System.out.println("O que você quer dizer?");
			novoTexto = scan.nextLine();
			String sql1 = "UPDATE postagem SET texto = ? where texto = ? and email_usuario = ?";
			PreparedStatement stmt1 = connection.prepareStatement(sql1);
			stmt1.setString(1, novoTexto);
			stmt1.setString(2, possiveis.get(select).getTexto());
			stmt1.setString(3, email);
			int qtdRowsAffected = stmt1.executeUpdate();
			stmt1.close();
			if (qtdRowsAffected > 0)
				return true;
			return false;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		return false;
	}

}
