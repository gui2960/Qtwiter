package folow;

public class Folow {
	private int id_folow;
	private String email_segue;
	private String email_seguido;
	
	public Folow(String email_segue, String email_seguido) {
		this.email_segue = email_segue;
		this.email_seguido = email_seguido;
		}
	
	public Folow(int id ,String email_segue, String email_seguido) {
		this.id_folow = id;
		this.email_segue = email_segue;
		this.email_seguido = email_seguido;
		}
	public String getEmail_segue() {
		return email_segue;
	}
	public void setEmail_segue(String email_segue) {
		this.email_segue = email_segue;
	}
	public String getEmail_seguido() {
		return email_seguido;
	}
	public void setEmail_seguido(String email_seguido) {
		this.email_seguido = email_seguido;
	}
	
	public int getId_folow() {
		return id_folow;
	}

	public void setId_folow(int id_folow) {
		this.id_folow = id_folow;
	}

	@Override
	public String toString() {
		return "Folow [email_segue=" + email_segue + ", email_seguido=" + email_seguido + "]";
	}
	
	
	
}
