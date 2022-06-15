package procesos;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import coordenador.Manipula_txt;
import coordenador.Operacoes_liberar_secao_critica;
import coordenador.Operacoes_manipulacao_arquivo;

public class P4 implements Operacoes_secao_critica {

	final static long id = 4;
	public final static String caminho = "arquivo.txt";
	public static Thread thread_processo_secao_critica;
	public static Operacoes_manipulacao_arquivo stub_coordenador;
	public static boolean solicitacao_escrita_pendente = false;
	
	public static void main(String[] args) 
	{
		//Scanner teclado = new Scanner(System.in);
		//System.out.println("Informe o nome/endereço do RMIRegistry:");
		String host = "localhost";//teclado.nextLine();


		try {    	
			Registry registro = LocateRegistry.getRegistry(host);
			
			stub_coordenador = (Operacoes_manipulacao_arquivo) registro.lookup("Arquivo");
					
			
			Operacoes_secao_critica stub_enquanto_servidor = (Operacoes_secao_critica) UnicastRemoteObject.exportObject(new P4(), 0);
			
			//servidor atuará na porta 4096
			LocateRegistry.createRegistry((int)(4095+id)); 

			
			String nome_stub = ("Processo"+id).trim().toString();
			
			registro.bind(nome_stub, stub_enquanto_servidor);
			
			
			int valor_aleatorio;
			while(true)
			{
				Thread.sleep(10000);
				if(solicitacao_escrita_pendente == true)
				{
					valor_aleatorio = 0;
				}else
				{
					valor_aleatorio = gerar_valor_aleatorio(2);
				}
				
				
				switch(valor_aleatorio)
				{
					case 0:
						System.out.println("Solicitar leitura");
						String resultado = stub_coordenador.solicitar_leitura_arquivo(id,false);
						System.out.println("Valor lido ={"+resultado+"}");
						break;
					case 1:
						System.out.println("Solicitar escrita");
						stub_coordenador.solicitar_escrita_no_arquivo(id);
						solicitacao_escrita_pendente = true;
						break;
						
				}
				
				
			}
				
			
		}catch (Exception e) {
			System.err.println("Process exception: " + e.toString());
			e.printStackTrace();
		}
		
		
	}


	
	public void entrou_secao_critica() throws RemoteException {
		try {
			System.out.println("Permissão para acessar o arquivo recebida");
			System.out.println("Escrevendo no arquivo a mensagem: Processo "+id+ " escreveu aqui");
			String mensagem_a_ser_enviada = stub_coordenador.solicitar_leitura_arquivo(id,true)+"\n";
			mensagem_a_ser_enviada +=("Processo "+id+" escreveu aqui");
			Manipula_txt.salvar_no_txt(caminho, mensagem_a_ser_enviada);
			Thread.sleep(7000);
			saiu_secao_critica();

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		
	}


	@Override
	public void saiu_secao_critica() throws RemoteException {
		
		try {
			Registry registro = LocateRegistry.getRegistry("localhost");
			Operacoes_liberar_secao_critica stub_liberar_secao_critica = (Operacoes_liberar_secao_critica) registro.lookup("Liberar_secao_critica");
			stub_liberar_secao_critica.liberar_secao_critica();
			System.out.println("Acesso ao arquivo terminado");
			solicitacao_escrita_pendente = false;
		} catch (NotBoundException e) {
			
			e.printStackTrace();
		}
		
	}
	
	// gera de 0 até o limite-1
	public static int gerar_valor_aleatorio(int limite)
	{
		Random gerador = new Random();
		return gerador.nextInt(limite);
	}
}
