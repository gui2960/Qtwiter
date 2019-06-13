package principal;

import java.util.ArrayList;

public class Validacoes {
	private ArrayList<String> entradas = new ArrayList<String>();

	public boolean Entrada() {
		for (String string : entradas) {
			if(string.equals("") || string == null) return false;
		}
		return true;
	}
	
	public void addEntrada(String entrada) {
		this.entradas.add(entrada);
	}
	
	public boolean validateEmail(String email) { // validar email
		String regex = "[A-Za-z0-9\\._-]+@[A-Za-z]+\\.[A-Za-z]+";
		boolean ok = email.matches(regex);
		return ok;
		
	}
	
	public ArrayList<String> getEntradas() {
		return entradas;
	}

	public void setEntradas(ArrayList<String>entradas) {
		this.entradas = entradas;
	}

}
