package postagem;



import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
import java.util.Date;
//import java.sql.Date;

public class Postagem {
	Date data;
	java.sql.Time hora;
	private String texto;
	private String email_usuario;
	
	
	
	@Override
	public String toString() {
		return "\nData: " + data + "\nHora: " + hora + "\nPublicado:  " 
	            + texto + "\n" ;
	}

	public Postagem(String texto, String email_usuario) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
		Date data = new Date();
		Date hora = new Date();
		String dat = dateFormat.format(data);	
		String hou = hourFormat.format(data);
		data = (Date) dateFormat.parse(dat);
		hora = (Date) hourFormat.parse(hou);
		java.sql.Date datasql = new java.sql.Date(data.getTime());
		java.sql.Time horasql = new java.sql.Time(hora.getTime());
		this.hora = horasql;
		this.data = datasql;
		this.texto = texto;
		this.email_usuario = email_usuario;
	}
	
	public Postagem (Date data, Date hora, String texto, String email) {
		this.data = data;
		this.hora = (Time) hora;
		this.texto = texto;
		this.email_usuario = email;
	}
	
	public java.sql.Time getHora() {
		return hora;
	}

	public void setHora(java.sql.Time hora) {
		this.hora = hora;
	}

	public Date getData() {
		return data;
	}

	public void setData() {
		this.data = new Date(0);//dateFormat.format(date);
	}

	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getEmail_usuario() {
		return email_usuario;
	}
	public void setEmail_usuario(String email_usuario) {
		this.email_usuario = email_usuario;
	}
	
	
	
}
