package coordenador;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Operacoes_liberar_secao_critica extends Remote {

	public void liberar_secao_critica() throws RemoteException;
		
}
