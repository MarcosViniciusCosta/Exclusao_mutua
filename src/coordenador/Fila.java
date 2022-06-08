package coordenador;

import java.util.ArrayList;

public class Fila {
	
	ArrayList<Long> fila;
	
	public Fila()
	{
		this.fila = new ArrayList<Long>();
	}
	
	public void adicionar(long elemento)
	{
		fila.add(elemento);
	}
	
	public boolean remover()
	{
		if(fila.size() >=0)
		{
			fila.remove(0);
			return true;
		}
		return false;
	}
	
	public String mostrar_fila()
	{
		
		String retorno = "";
		for(int cont=0;cont<fila.size();cont++)
		{
			retorno += (fila.get(cont) +" ");
		}
		retorno+="\n";
		
		return retorno;
	}
	
	public boolean esta_vazia()
	{
		if(fila.size() == 0)
		{
			return true;
		}else
		{
			return false;
		}
	}
	
	public long acessar_primeira_posicao()
	{
		if(esta_vazia() == false)
		{
			return fila.get(0);
		}else
		{
			return -1;
		}
	}
	
}
