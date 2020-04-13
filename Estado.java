package main;

public class Estado implements Comparable<Estado>{
	
	public Estado(int[][] tabuleiro, int movimento) {
		setTabuleiro(tabuleiro);
		setMovimento(movimento);		
	}

	public Estado(int[][] tabuleiro, int movimento, int[][] matrizHeuristica){
		this (tabuleiro, movimento);
		this.findXYZero();
		this.calculaHeuristica(matrizHeuristica);		
	}
	
	public Estado(int[][] tabuleiro, int movimento, int[][] matrizHeuristica, int x, int y, int pai, int id, int profundidade){		
		this (tabuleiro, movimento);
		this.profundidade = profundidade+1;
		this.x = x;
		this.y = y;
		this.calculaHeuristica(matrizHeuristica);
		this.id = id;
		this.pai_id = pai;		
	}
	
	@Override
	public String toString() {
		String retorno = "--Estado atual:\n";
		retorno += "---Pai.ID: "+pai_id + 
				"\n---Estado.ID: "+id+
				"\n---Heuristica: "+heuristica+
				"\n---Profundidade: "+profundidade+
				"\n---Tabuleiro:\n";
		for (int i = 0; i < tabuleiro.length; i++) {
			retorno += "----\t";
			for (int j = 0; j < tabuleiro.length; j++) {
				retorno +=tabuleiro[i][j] + "\t";
			}
			retorno += "\n";
		}
		return retorno;
	}
	
	private int[][] tabuleiro;
	private int heuristica;
	private int movimento;
	private int x, y;
	private int id;
	private int pai_id;
	private int profundidade;
	
	private void calculaHeuristica(int[][] matrizHeuristica) {
		for (int i = 0; i < tabuleiro.length; i++) {
			for (int j = 0; j < tabuleiro.length; j++) {
				int valor = tabuleiro[i][j];				
				this.heuristica+=matrizHeuristica[valor][(i+j)+(i*2)];				
			}
		}
		
	}
	
	private void findXYZero() {		
		for (int i = 0; i < tabuleiro.length; i++) {
			for (int j = 0; j < tabuleiro.length; j++) {
				if (tabuleiro[i][j] == 0) {					
					this.x = i;
					this.y = j;
					return;
				}
			}
		}
	}
	
	@Override
	public int compareTo(Estado o) {		
		return (this.heuristica + profundidade) - (o.getHeuristica() + o.profundidade);
		//return this.getHeuristica() - o.getHeuristica();
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

	public int getMovimento() {
		return movimento;
	}

	public void setMovimento(int movimento) {
		this.movimento = movimento;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPai_id() {
		return pai_id;
	}

	public void setPai_id(int pai_id) {
		this.pai_id = pai_id;
	}

	public int getProfundidade() {
		return profundidade;
	}

	public void setProfundidade(int profundidade) {
		this.profundidade = profundidade;
	}

}