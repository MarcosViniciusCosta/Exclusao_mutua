package procesos;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Operacoes_secao_critica extends Remote{

	void entrou_secao_critica() throws RemoteException;
	void saiu_secao_critica() throws RemoteException;
	
}
