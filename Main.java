package main;


public class Main {

	public static void main(String[] args) {
		//Troubleshooting
		//int[][] estadoInicial = {{0,5,2},{3,8,7},{4,1,6}};
		//int[][] estadoFinal = {{1,2,3},{8,0,4},{7,6,5}};
		
		int[][] estadoInicial = {{1,2,3},{4,5,6},{7,8,0}};
		int[][] estadoFinal = {{1,3,0},{4,2,5},{7,8,6}};
		
		Jogo jogo = new Jogo(estadoInicial, estadoFinal);
		jogo.jogar();
		
		
		
	}
	
	public static void message(String msg) {
		System.out.println(msg);
	}
	

}
