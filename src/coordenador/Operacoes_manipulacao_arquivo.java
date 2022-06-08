package coordenador;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Operacoes_manipulacao_arquivo extends Remote {
		
	 String solicitar_leitura_arquivo(long id, boolean gerado_por_processo_escrita) throws RemoteException;
	 boolean solicitar_escrita_no_arquivo(long id) throws RemoteException;
}
