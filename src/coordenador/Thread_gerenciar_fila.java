package coordenador;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import procesos.Operacoes_secao_critica;

public class Thread_gerenciar_fila implements Runnable, Operacoes_liberar_secao_critica{

	static Fila fila;
	final String caminho;
	static boolean secao_critica_liberada;
	
	public Thread_gerenciar_fila(Fila fila, String caminho) {
		this.caminho = caminho;
		Thread_gerenciar_fila.fila = fila;
		secao_critica_liberada = true;
	}

	public void run() 
	{
		
		try {
			
			Thread_gerenciar_fila refObjetoRemoto = new Thread_gerenciar_fila(fila, caminho);
			
			Operacoes_liberar_secao_critica stub = (Operacoes_liberar_secao_critica) UnicastRemoteObject.exportObject(refObjetoRemoto, 0);
			
			//porta 1099
			LocateRegistry.createRegistry(8777); 

			Registry registro2 = LocateRegistry.getRegistry();

			registro2.bind("Liberar_secao_critica", stub);
			
			System.out.println("Thread rodando");
			
			
			
			
			
			
			
			while(true)
			{
		
				Thread.sleep(3000);
				if(fila.esta_vazia() == false && secao_critica_liberada)
				{
					
					long id = fila.acessar_primeira_posicao();
					fila.remover();
					String nome_stub = ("Processo"+id).trim().toString();
					Registry registro = LocateRegistry.getRegistry("localhost");

					Operacoes_secao_critica stub_processos = (Operacoes_secao_critica) registro.lookup(nome_stub);
					System.out.println("Processo de id "+id+ " entrou na seção crítica!");
					secao_critica_liberada = false;
					stub_processos.entrou_secao_critica();
					//Thread.sleep(10000);
					System.out.println("Processo de id "+id+ " saiu da seção crítica!");
									
				}
			}

		} catch (InterruptedException e) {
			System.out.println("Erro do tipo InterruptedException");
			e.printStackTrace();
		}catch(RemoteException e)
		{
			System.out.println("erro do tipo RemoteException");
			e.printStackTrace();
		}catch(NotBoundException e)
		{
			System.out.println("erro do tipo NotBoundException");
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			System.out.println("erro do tipo AlreadyBoundException");
			e.printStackTrace();
		}

	}


	public void liberar_secao_critica()
	{
		secao_critica_liberada = true;
	}
	


}
