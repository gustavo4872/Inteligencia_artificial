package main;

import java.util.PriorityQueue;

public class Jogo {
	
	public Jogo(int[][] estadoInicial, int[][] estadoFinal) {
		this.estadoInicial = estadoInicial;
		this.estadoFinal = estadoFinal;
		this.montaMatrizHeuristica();
	}
	
	private int[][] matrizHeuristica = new int[9][9]; // Matriz da heuristica de cada valor em cada posi��o da matriz final; 
	private int[][] estadoInicial, estadoFinal;	
	private Estado estadoAtual;	 
	private int qtEstadosCriados;
	private int ciclo;
	private PriorityQueue<Estado> estados = new PriorityQueue<Estado>();
	
	public void jogar() {
		boolean condition = false;
		try {			
			this.estados.add(new Estado(estadoInicial, Contante.START, matrizHeuristica));			
			while (!condition) {
				this.ciclo++;
				//Thread.sleep(500);
				this.estadoAtual = estados.remove();
				this.log();
				if (this.estadoAtual.getHeuristica()!=0) {
					this.montaNovoEstado();
				}else {
					break;					
				}					
			}
		} catch (Exception e) {			
			e.printStackTrace();			
		}
		
	}
	
	private void log() {
		System.out.println("-CICLO: "+this.ciclo);
		System.out.println("--Estados criados: "+this.qtEstadosCriados);
		System.out.println("--Estados em mem�ria: "+estados.size());	
		System.out.println(estadoAtual);	
	}
	
	private void montaMatrizHeuristica() {
		/*
		 * Temos dois la�os for pois � necess�rio fazer a heuristica de cada posi��o da matriz com todas as posi��es
		 * 
		 * ----MATRIZ FINAL-----
		 * 0 - 0 | 0 - 1 | 0 - 2
		 * 1 - 0 | 1 - 1 | 1 - 2
		 * 2 - 0 | 2 - 1 | 2 - 2
		 * 
		 * Cada index da matriz final corresponde a uma posi��o Y (0 - 0 = 0, 0 - 1 == 1, ....) na matriz Heuristica;
		 * Somando X + Y (coordenadas da matriz final) com o resultado da multiplica��o do k (vari�vel que itera sobre X) por 2, temos o Y da matriz Heuristica;
		 * Cada X da matriz heuristica corresponde a um valor (0-8) dos valores do jogo;
		 * Cada valor do index da matriz heuristica representa a dist�ncia que o valor (X) est� da sua posi��o final (Y); 
		 *
		*/
		for (int i = 0; i < estadoFinal.length; i++) {
			for (int j = 0; j < estadoFinal.length; j++) {
				int aux1 = estadoFinal[i][j];//Pega uma posi��o na matriz final, sendo aux1 o valor da posi��o. I e j correspodem as coordenadas X e Y;
				//Essa posi��o ser� comparada com todas as posi��es da mesma matriz;
				//Ou seja, se um valor estiver na posi��o 0 - 0, podemos calcular a dist�ncia at� 0 - 1, 0 - 2, e assim por diante;
				//No sentido inverso, se o valor estiver na 0 - 2 e seu lugar for 0 - 0, saberemos a dist�ncia;
				if (aux1!=0) {//N�o � necess�rio calcular dist�ncia para o 0;
					for (int k = 0; k < estadoFinal.length; k++) {
						for (int k2 = 0; k2 < estadoFinal.length; k2++) {

							if (i!=k||j!=k2) { //Se pegar as mesmas posi��es, ent�o � colocado 0 na posi��o correpondente da matriz heuristica no else.
											   //Pois na matriz final, cada posi��o corresponde ao lugar em que o valor deve terminar. Sendo assim;
								if (i==k) {//Se caso as posi��es estiverem na mesma linha, a dist�ncia entre as posi��es � o resultado ABS da diferen�a entre os Y(j e k2);
									matrizHeuristica[aux1][(k+k2)+(k*2)] = Math.abs(j - k2);
								}else if (j==k2) {//Se caso as posi��es estiverem na mesma coluna, a dist�ncia entre as posi��es � o resultado ABS da diferen�a entre os X(i e k);
									matrizHeuristica[aux1][(k+k2)+(k*2)] = Math.abs(i - k);
								}else {//Se caso as posi��es estiverem em linhas e colunas diferentes, a dist�ncia se d� pela soma da diferen�a entre os X (i e k) e Y (j e k2);
									matrizHeuristica[aux1][(k+k2)+(k*2)] = Math.abs(i - k) + Math.abs(j - k2);
								}
								
							}else {
								matrizHeuristica[aux1][(k+k2)+(k*2)] = 0;
							}
							
						}
					}
				}
				
			}
		}
	}
	
	private void montaNovoEstado() {		
		int xEstadoAntigo = this.estadoAtual.getX();
		int yEstadoAntigo = this.estadoAtual.getY();
		
		if (xEstadoAntigo == 0) {
			this.movBaixo();
			this.montaNovoEstado(xEstadoAntigo, yEstadoAntigo);
		}else if (xEstadoAntigo == 2) {
			this.movCima();
			this.montaNovoEstado(xEstadoAntigo, yEstadoAntigo);
		}else {
			this.movCima();
			this.movBaixo();
			this.montaNovoEstado(xEstadoAntigo, yEstadoAntigo);
		}
	}
	
	private void montaNovoEstado(int xEstadoAntigo, int yEstadoAntigo) {
		if (yEstadoAntigo == 0) {
			this.movDireita();
		}else if (yEstadoAntigo == 2) {
			this.movEsquerda();
		}else {			
			this.movEsquerda();
			this.movDireita();
		}
	}
	
	private int[][] clonaTabuleiro(int[][] tabuleiro) {
		int[][] novoTabuleiro = new int[3][3];
		for (int i = 0; i < novoTabuleiro.length; i++) {
			for (int j = 0; j < novoTabuleiro.length; j++) {
				novoTabuleiro[i][j] = tabuleiro[i][j];
			}
		}
		return novoTabuleiro;
	}
	
	private void movDireita() {
		if (this.estadoAtual.getMovimento() != Contante.ESQUERDA) {
			qtEstadosCriados++;
			int[][] tabuleiroNovo = clonaTabuleiro(estadoAtual.getTabuleiro());
			int xZero = this.estadoAtual.getX();
			int yZero = this.estadoAtual.getY() + 1;
			int valor = tabuleiroNovo[xZero][yZero];
			tabuleiroNovo[xZero][yZero] = 0;
			tabuleiroNovo[xZero][yZero-1] = valor;		
			estados.add(new Estado(tabuleiroNovo, Contante.DIREITA, matrizHeuristica, xZero, yZero, estadoAtual.getId(), qtEstadosCriados, estadoAtual.getProfundidade()));			
		}
	}	
	
	private void movEsquerda() {	
		if (estadoAtual.getMovimento() != Contante.DIREITA) {			
			qtEstadosCriados++;
			int[][] tabuleiroNovo = clonaTabuleiro(estadoAtual.getTabuleiro());
			int xZero = this.estadoAtual.getX();
			int yZero = this.estadoAtual.getY() - 1;
			int valor = tabuleiroNovo[xZero][yZero];
			tabuleiroNovo[xZero][yZero] = 0;
			tabuleiroNovo[xZero][yZero+1] = valor;
			estados.add(new Estado(tabuleiroNovo, Contante.ESQUERDA, matrizHeuristica, xZero, yZero, estadoAtual.getId(), qtEstadosCriados, estadoAtual.getProfundidade()));			
		}
	}
	
	private void movCima() {
		if (estadoAtual.getMovimento() != Contante.BAIXO) {
			qtEstadosCriados++;
			int[][] tabuleiroNovo = clonaTabuleiro(estadoAtual.getTabuleiro());
			int xZero = this.estadoAtual.getX()-1;
			int yZero = this.estadoAtual.getY();
			int valor = tabuleiroNovo[xZero][yZero];
			tabuleiroNovo[xZero][yZero] = 0;
			tabuleiroNovo[xZero+1][yZero] = valor;
			estados.add(new Estado(tabuleiroNovo, Contante.CIMA, matrizHeuristica, xZero, yZero, estadoAtual.getId(), qtEstadosCriados, estadoAtual.getProfundidade()));			
		}
	}
	
	private void movBaixo() {		
		if (estadoAtual.getMovimento() != Contante.CIMA) {
			qtEstadosCriados++;
			int[][] tabuleiroNovo = clonaTabuleiro(estadoAtual.getTabuleiro());
			int xZero = this.estadoAtual.getX()+1;
			int yZero = this.estadoAtual.getY();
			int valor = tabuleiroNovo[xZero][yZero];
			tabuleiroNovo[xZero][yZero] = 0;
			tabuleiroNovo[xZero-1][yZero] = valor;
			estados.add(new Estado(tabuleiroNovo, Contante.BAIXO, matrizHeuristica, xZero, yZero, estadoAtual.getId(), qtEstadosCriados, estadoAtual.getProfundidade()));			
		}
	}
	
}