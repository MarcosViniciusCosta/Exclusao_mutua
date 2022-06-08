package coordenador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Manipula_txt {

	public static String ler_txt(String caminho)
	{
		String retorno = "";
		//String conteudo = "";
		try {
			FileReader arq = new FileReader(caminho);
			BufferedReader lerArq = new BufferedReader(arq);
			String linha="";
			try {
				linha = lerArq.readLine();
				while(linha!=null){
					retorno += linha+"\n";
					linha = lerArq.readLine();
				}
				arq.close();
				//removendo \n que ficou sobrando
				if(retorno.length() > 0)
				{
					retorno = retorno.substring(0,retorno.length()-1);
				}
				//System.out.println("Mensagem lida = " + retorno);
				return retorno;
			} catch (IOException ex) {
				System.out.println("Erro: Não foi possível ler o arquivo!");
				return "";
			}
		} catch (FileNotFoundException ex) {
			System.out.println("Erro: Arquivo não encontrado!");
			return "";
		}

	}


	public static boolean salvar_no_txt(String caminho, String mensagem)
	{
		
		File arquivo = new File(caminho);

		if(arquivo.exists() == false) 
		{
			try {
				arquivo.createNewFile();
			} catch (IOException e) {
				System.out.println("Arquivo nï¿½o criado");
				e.printStackTrace();
				return false;
			}
		}

		try {

			FileWriter fileWriter = new FileWriter(arquivo, false);

			PrintWriter printWriter = new PrintWriter(fileWriter);
			//System.out.println("Caminho = {" + caminho+"}");
			
			
			
			Thread.sleep(1000);
			
			
			printWriter.println(mensagem);

			printWriter.flush();

			printWriter.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}

		return true;
	} 

}
