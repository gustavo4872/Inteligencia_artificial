package main;


public class Main {

	public static void main(String[] args) {
		//Troubleshooting
		//int[][] estadoInicial = {{0,5,2},{3,8,7},{4,1,6}};
		//int[][] estadoFinal = {{1,2,3},{8,0,4},{7,6,5}};
	
		//Teste rápido
		//int[][] estadoInicial = {{0,1,2},{7,8,3},{6,5,4}};
		//int[][] estadoFinal = {{1,2,3},{8,0,4},{7,6,5}};
		
		//int[][] estadoInicial = {{3,0,5},{1,6,2},{7,4,8}};
		//int[][] estadoFinal = {{1,2,3},{8,0,6},{7,8,0}};
		
		//int[][] estadoInicial = {{8,7,6},{5,4,3},{1,2,0}};
		//int[][] estadoFinal = {{1,2,3},{8,0,4},{7,6,5}};
		
		//Teste simples
		//int[][] estadoInicial = {{1,2,5},{3,4,8},{6,7,0}};
		//int[][] estadoFinal = {{0,1,2},{3,4,5},{6,7,8}};
		
		//int[][] estadoInicial = {{2,5,1},{4,0,8},{7,3,6}};
		//int[][] estadoFinal = {{0,1,2},{3,4,5},{6,7,8}};
		
		//int[][] estadoInicial = {{1,6,0},{7,5,8},{2,3,4}};
		//int[][] estadoFinal = {{0,1,2},{3,4,5},{6,7,8}};
				
		//int[][] estadoInicial = {{8,7,4},{1,3,0},{6,5,2}};
		//int[][] estadoFinal = {{0,1,2},{3,4,5},{6,7,8}};
		
		int[][] estadoInicial = {{4,3,6},{5,7,8},{1,0,2}};
		int[][] estadoFinal = {{0,1,2},{3,4,5},{6,7,8}};
		
		Jogo jogo = new Jogo(estadoInicial, estadoFinal);
		jogo.jogar();
		
		
		
	}
	
	public static void message(String msg) {
		System.out.println(msg);
	}
	

}
