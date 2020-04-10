package main;

public class Estado implements Comparable<Estado>{
	
	public Estado() {
		
	}

	public Estado(int[][] tabuleiro, int movimento, int[][] matrizHeuristica){
		setTabuleiro(tabuleiro);
		setMovimento(movimento);
		this.findXYZero();
		this.calculaHeuristica(matrizHeuristica);
	}
	
	public Estado(int[][] tabuleiro, int movimento, int[][] matrizHeuristica, int x, int y){
		setTabuleiro(tabuleiro);
		setMovimento(movimento);
		setX(x);
		setY(y);
		this.calculaHeuristica(matrizHeuristica);
	}
	
	@Override
	public String toString() {
		String retorno = "";
		retorno += "H - "+heuristica + "\n";
		for (int i = 0; i < tabuleiro.length; i++) {
			for (int j = 0; j < tabuleiro.length; j++) {
				retorno += tabuleiro[i][j] + "\t";
			}
			retorno += "\n";
		}
		return retorno;
	}
	
	public int[][] tabuleiro;
	public int heuristica;
	public int movimento;
	public int x, y;
	
	private void calculaHeuristica(int[][] matrizHeuristica) {		
		for (int i = 0; i < tabuleiro.length; i++) {
			for (int j = 0; j < tabuleiro.length; j++) {
				int valor = tabuleiro[i][j];				
				setHeuristica(getHeuristica()+matrizHeuristica[valor][(i+j)+(i*2)]);				
			}
		}
		
	}
	
	private void findXYZero() {		
		for (int i = 0; i < tabuleiro.length; i++) {
			for (int j = 0; j < tabuleiro.length; j++) {
				if (tabuleiro[i][j] == 0) {					
					setX(i);
					setY(j);
					return;
				}
			}
		}
	}
	
	@Override
	public int compareTo(Estado o) {		
		return this.getHeuristica() - o.getHeuristica();
	}

	public int[][] getTabuleiro() {
		return tabuleiro;
	}

	public void setTabuleiro(int[][] tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	public int getHeuristica() {
		return heuristica;
	}

	public void setHeuristica(int heuristica) {
		this.heuristica = heuristica;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	private int getMovimento() {
		return movimento;
	}

	private void setMovimento(int movimento) {
		this.movimento = movimento;
	}

}