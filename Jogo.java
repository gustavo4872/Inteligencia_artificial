package main;

import java.util.PriorityQueue;

public class Jogo {
	
	public Jogo(int[][] estadoInicial, int[][] estadoFinal) {
		this.estadoInicial = estadoInicial;
		this.estadoFinal = estadoFinal;
		this.montaMatrizHeuristica();
	}
	
	int[][] matrizHeuristica = new int[9][9]; // Matriz da heuristica de cada valor em cada posição da matriz final; 
	private int[][] estadoInicial;
	private int[][] estadoFinal;
	private Estado estadoAtual;	 
	private int qtEstadosCriados;	
	private int ciclo;
	private int idNovoEstado;
	private PriorityQueue<Estado> estados = new PriorityQueue<Estado>();
	
	public void jogar() {
		boolean condition = false;
		try {			
			this.estados.add(new Estado(estadoInicial, Contante.START, matrizHeuristica));			
			while (!condition) {
				setCiclo(getCiclo()+1);
				Thread.sleep(100);
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
		System.out.println("-CICLO: "+getCiclo());
		System.out.println("--Estados criados: "+getQtEstadosCriados());
		System.out.println("--Estados em memória: "+getEstados().size());	
		System.out.println(estadoAtual);	
	}
	
	private void montaMatrizHeuristica() {
		/*
		 * Temos dois laços for pois é necessário fazer a heuristica de cada posição da matriz com todas as posições
		 * 
		 * ----MATRIZ FINAL-----
		 * 0 - 0 | 0 - 1 | 0 - 2
		 * 1 - 0 | 1 - 1 | 1 - 2
		 * 2 - 0 | 2 - 1 | 2 - 2
		 * 
		 * Cada index da matriz final corresponde a uma posição Y (0 - 0 = 0, 0 - 1 == 1, ....) na matriz Heuristica;
		 * Somando X + Y (coordenadas da matriz final) com o resultado da multiplicação do k (variável que itera sobre X) por 2, temos o Y da matriz Heuristica;
		 * Cada X da matriz heuristica corresponde a um valor (0-8) dos valores do jogo;
		 * Cada valor do index da matriz heuristica representa a distância que o valor (X) está da sua posição final (Y); 
		 *
		*/
		for (int i = 0; i < estadoFinal.length; i++) {
			for (int j = 0; j < estadoFinal.length; j++) {
				int aux1 = estadoFinal[i][j];//Pega uma posição na matriz final, sendo aux1 o valor da posição. I e j correspodem as coordenadas X e Y;
				//Essa posição será comparada com todas as posições da mesma matriz;
				//Ou seja, se um valor estiver na posição 0 - 0, podemos calcular a distância até 0 - 1, 0 - 2, e assim por diante;
				//No sentido inverso, se o valor estiver na 0 - 2 e seu lugar for 0 - 0, saberemos a distância;
				if (aux1!=0) {//Não é necessário calcular distância para o 0;
					for (int k = 0; k < estadoFinal.length; k++) {
						for (int k2 = 0; k2 < estadoFinal.length; k2++) {

							if (i!=k||j!=k2) { //Se pegar as mesmas posições, então é colocado 0 na posição correpondente da matriz heuristica no else.
											   //Pois na matriz final, cada posição corresponde ao lugar em que o valor deve terminar. Sendo assim;
								if (i==k) {//Se caso as posições estiverem na mesma linha, a distância entre as posições é o resultado ABS da diferença entre os Y(j e k2);
									matrizHeuristica[aux1][(k+k2)+(k*2)] = Math.abs(j - k2);
								}else if (j==k2) {//Se caso as posições estiverem na mesma coluna, a distância entre as posições é o resultado ABS da diferença entre os X(i e k);
									matrizHeuristica[aux1][(k+k2)+(k*2)] = Math.abs(i - k);
								}else {//Se caso as posições estiverem em linhas e colunas diferentes, a distância se dá pela soma da diferença entre os X (i e k) e Y (j e k2);
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
		if (this.estadoAtual.movimento != Contante.ESQUERDA) {
			setIdNovoEstado(getIdNovoEstado()+1);
			int[][] tabuleiroNovo = clonaTabuleiro(estadoAtual.getTabuleiro());
			int xZero = this.estadoAtual.getX();
			int yZero = this.estadoAtual.getY() + 1;
			int valor = tabuleiroNovo[xZero][yZero];
			tabuleiroNovo[xZero][yZero] = 0;
			tabuleiroNovo[xZero][yZero-1] = valor;			
			estados.add(new Estado(tabuleiroNovo, Contante.DIREITA, matrizHeuristica, xZero, yZero, estadoAtual.id, getIdNovoEstado()));
			setQtEstadosCriados(getQtEstadosCriados()+1);
		}
	}	
	private void movEsquerda() {	
		if (estadoAtual.movimento != Contante.DIREITA) {
			int[][] tabuleiroNovo = clonaTabuleiro(estadoAtual.getTabuleiro());
			int xZero = this.estadoAtual.getX();
			int yZero = this.estadoAtual.getY() - 1;
			int valor = tabuleiroNovo[xZero][yZero];
			tabuleiroNovo[xZero][yZero] = 0;
			tabuleiroNovo[xZero][yZero+1] = valor;
			//System.out.println("VALOR - "+valor);
			estados.add(new Estado(tabuleiroNovo, Contante.ESQUERDA, matrizHeuristica, xZero, yZero, estadoAtual.id, idNovoEstado));
			setQtEstadosCriados(getQtEstadosCriados()+1);
		}
	}
	private void movCima() {
		if (estadoAtual.movimento != Contante.BAIXO) {
			int[][] tabuleiroNovo = clonaTabuleiro(estadoAtual.getTabuleiro());
			int xZero = this.estadoAtual.getX()-1;
			int yZero = this.estadoAtual.getY();
			int valor = tabuleiroNovo[xZero][yZero];
			tabuleiroNovo[xZero][yZero] = 0;
			tabuleiroNovo[xZero+1][yZero] = valor;
			estados.add(new Estado(tabuleiroNovo, Contante.CIMA, matrizHeuristica, xZero, yZero, estadoAtual.id, idNovoEstado));
			setQtEstadosCriados(getQtEstadosCriados()+1);
		}
	}
	private void movBaixo() {		
		if (estadoAtual.movimento != Contante.CIMA) {
			int[][] tabuleiroNovo = clonaTabuleiro(estadoAtual.getTabuleiro());
			int xZero = this.estadoAtual.getX()+1;
			int yZero = this.estadoAtual.getY();
			int valor = tabuleiroNovo[xZero][yZero];
			tabuleiroNovo[xZero][yZero] = 0;
			tabuleiroNovo[xZero-1][yZero] = valor;
			estados.add(new Estado(tabuleiroNovo, Contante.BAIXO, matrizHeuristica, xZero, yZero, estadoAtual.id, idNovoEstado));
			setQtEstadosCriados(getQtEstadosCriados()+1);
		}
	}

	
	public int[][] getMatrizHeuristica() {
		return matrizHeuristica;
	}

	public void setMatrizHeuristica(int[][] matrizHeuristica) {
		this.matrizHeuristica = matrizHeuristica;
	}

	public int[][] getEstadoInicial() {
		return estadoInicial;
	}

	public void setEstadoInicial(int[][] estadoInicial) {
		this.estadoInicial = estadoInicial;
	}

	public int[][] getEstadoFinal() {
		return estadoFinal;
	}

	public void setEstadoFinal(int[][] estadoFinal) {
		this.estadoFinal = estadoFinal;
	}

	public Estado getEstadoAtual() {
		return estadoAtual;
	}

	public void setEstadoAtual(Estado estadoAtual) {
		this.estadoAtual = estadoAtual;
	}

	public int getQtEstadosCriados() {
		return qtEstadosCriados;
	}

	public void setQtEstadosCriados(int qtEstadosCriados) {
		this.qtEstadosCriados = qtEstadosCriados;
	}

	public int getCiclo() {
		return ciclo;
	}
	public void setCiclo(int ciclo) {
		this.ciclo = ciclo;
	}
	public int getIdNovoEstado() {
		return idNovoEstado;
	}

	public void setIdNovoEstado(int idNovoEstado) {
		this.idNovoEstado = idNovoEstado;
	}

	public PriorityQueue<Estado> getEstados() {
		return estados;
	}

	public void setEstados(PriorityQueue<Estado> estados) {
		this.estados = estados;
	}

	
}