package folow;

import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.ArrayList;

import jdbc.Connection_factory;

public class Folow_dao {
	private Connection connection;

	public Folow_dao() {
		
	}
	
	public boolean seguir(Folow seguir) {
		String sql = "INSERT INTO folow(email_segue, email_seguido) VALUES (?, ?)";
		this.connection = new Connection_factory().getConnection();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, seguir.getEmail_segue());
			stmt.setString(2, seguir.getEmail_seguido());
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
	public boolean pararDeSeguir(Folow parar) {
		String sql = "DELETE FROM folow WHERE email_segue = ? AND  email_seguido = ?;";
		this.connection = new Connection_factory().getConnection();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			// seta os valores
			stmt.setString(1, parar.getEmail_segue());
			stmt.setString(2, parar.getEmail_seguido());
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
