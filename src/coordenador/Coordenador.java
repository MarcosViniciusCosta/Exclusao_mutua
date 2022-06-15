package coordenador;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;




public class Coordenador implements Operacoes_manipulacao_arquivo 
{

	
	public static Fila fila;
	public final static String caminho = "arquivo.txt";
	public static Thread thread_gerenciar_fila;
	
	
	public static void main(String[] args) 
	{
		
		try {
			
			Coordenador refObjetoRemoto = new Coordenador();

			fila = new Fila();
			
			Operacoes_manipulacao_arquivo stub= (Operacoes_manipulacao_arquivo) UnicastRemoteObject.exportObject(refObjetoRemoto, 0);

			//porta 1099
			LocateRegistry.createRegistry( Registry.REGISTRY_PORT ); 

			Registry registro = LocateRegistry.getRegistry();

			registro.bind("Arquivo", stub);

			System.err.println("Coordenador pronto:");

			Thread_gerenciar_fila thread_intermediaria =
					new Thread_gerenciar_fila(fila, caminho);

			thread_gerenciar_fila = new Thread(thread_intermediaria);
			thread_gerenciar_fila.start();
			
			
		} catch (Exception e) {
			System.err.println("Cordenador exception: " + e.toString());
			e.printStackTrace();
		}
		
		
	}

	
	public String solicitar_leitura_arquivo(long id, boolean gerado_por_processo_escrita) throws RemoteException {
		
		if(gerado_por_processo_escrita == false)
		{
			System.out.println("Tamanho da fila: "+fila.fila.size());
			System.out.println("Fila: "+ String.join(",",fila.fila.toString()));
			System.out.println("Processo de id "+id+ " solicitou leitura do conteúdo do arquivo!");
		}
		
		String resultado = Manipula_txt.ler_txt(caminho);
		return resultado;
	}

	
	public boolean solicitar_escrita_no_arquivo(long id) throws RemoteException {
		System.out.println("Processo de id "+id+ " solicitou escrita, então entrou na fila");
		fila.adicionar(id);
		
		
		return false;
	}

}
