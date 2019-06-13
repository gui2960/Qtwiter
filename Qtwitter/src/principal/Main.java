package principal;

import java.text.ParseException;
import java.util.Scanner;

import usuario.Usuario;
import usuario.Usuario_dao;

public class Main {
	public static void main(String[] args) throws ParseException {
		boolean run = true;
		Logado log = new Logado();
		Usuario usuario = null;
		Usuario_dao usuario_dao = new Usuario_dao();
		String nome, email, senha;
		while (run) {
			System.out.println("------ QTWITTER---------");
			System.out.println("| 1 | Cadastrar usuário");
			System.out.println("| 2 | Logar");
			System.out.println("| 0 | Sair");
			
			
			Scanner input = new Scanner(System.in);
			String comando = input.nextLine();
			switch (comando) {
			case "0":
				System.out.println("Programa encerrado!");
				input.close();
				run = false;
				break;
				
			case "1":
				System.out.println("Digite seu nome:");
				nome = input.nextLine();
				System.out.println("Digite sua senha:");
				senha = input.nextLine();
				System.out.println("Digite seu email:");
				email = input.nextLine();	
				usuario = new Usuario(nome, email, senha);
				usuario_dao.addUser(usuario);
				break;
				
			case "2":
				System.out.println("Digite seu nome:");
				nome = input.nextLine();
				System.out.println("Digite sua senha:");
				senha = input.nextLine();
				usuario = usuario_dao.getNamePass(nome, senha);
				
				if (usuario == null)
					System.out.println("Erro: usuario inexistente!");
				else {
					log.ativo(usuario);
				}
				
				break;
				
			default:
				System.err.println("comando inválido");
				break;
			}
		}
	}
	public void validacao(Usuario user) {
		if(user == null) return;
		else {
			this.logado(user);
		}
	}
	
	public void logado(Usuario user) {
		
	}
}
