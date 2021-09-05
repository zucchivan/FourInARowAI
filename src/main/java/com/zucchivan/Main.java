package com.zucchivan;

import java.util.Scanner;

public class Main {

	private Tabuleiro t;
	private Scanner scanner;
	private int proximaJogada = -1;
	private int profundidadeMaxima = 6;

		public Main(Tabuleiro t) {
		this.t = t;
		scanner = new Scanner(System.in);
	}

	public static void main(String[] args) {
		Tabuleiro t = new Tabuleiro();
		Main jogo = new Main(t);
		jogo.jogar();

		/*
		jogo.testaProfundidade(1, Tabuleiro.OPONENTE, 10000);
		jogo.testaProfundidade(2, Tabuleiro.OPONENTE, 10000);
		jogo.testaProfundidade(3, Tabuleiro.OPONENTE, 10000);
		jogo.testaProfundidade(4, Tabuleiro.OPONENTE, 10000);
		jogo.testaProfundidade(5, Tabuleiro.OPONENTE, 10000);
		jogo.testaProfundidade(6, Tabuleiro.OPONENTE, 10000);
		
		jogo.testaProfundidade(1, Tabuleiro.IA, 10000);
		jogo.testaProfundidade(2, Tabuleiro.IA, 10000);
		jogo.testaProfundidade(3, Tabuleiro.IA, 10000);
		jogo.testaProfundidade(4, Tabuleiro.IA, 10000);
		jogo.testaProfundidade(5, Tabuleiro.IA, 10000);
		jogo.testaProfundidade(6, Tabuleiro.IA, 10000);
		 */

	}

	public void realizaJogadaOponente() {
		System.out.println("Digite a jogada:\n");
		int jogada = scanner.nextInt();

		while (jogada < 0 || jogada > 6 || !t.jogadaPossivel(jogada)) {
			System.out.println("Jogada inv�lida.\n\n" + "Tente novamente com n�meros entre 0 e 6: ");
			jogada = scanner.nextInt();
		}

		t.realizaJogada(jogada, Tabuleiro.OPONENTE);
	}

	public int getResultado(Tabuleiro t) {
		int pontosIA = 0, pontosOponente = 0;

		for (int y = 5; y >= 0; --y) {
			for (int x = 0; x <= 6; ++x) {
				if (t.tabuleiro[y][x] == 0)
					continue;

				// Se x <= 3, checando se houve four in a row para a direita
				if (x <= 3) {
					for (int k = 0; k < 4; ++k) {
						if (t.tabuleiro[y][x + k] == Tabuleiro.IA)
							pontosIA++;
						else if (t.tabuleiro[y][x + k] == Tabuleiro.OPONENTE)
							pontosOponente++;
						else
							break;
					}

					if (pontosIA == 4)
						return Tabuleiro.IA;
					else if (pontosOponente == 4)
						return Tabuleiro.OPONENTE;

					pontosIA = 0;
					pontosOponente = 0;
				}

				// Se y >= 3, checando se houve four in a row na vertical (cima)
				if (y >= 3) {
					for (int k = 0; k < 4; ++k) {
						if (t.tabuleiro[y - k][x] == Tabuleiro.IA)
							pontosIA++;
						else if (t.tabuleiro[y - k][x] == Tabuleiro.OPONENTE)
							pontosOponente++;
						else
							break;
					}

					if (pontosIA == 4)
						return Tabuleiro.IA;
					else if (pontosOponente == 4)
						return Tabuleiro.OPONENTE;

					pontosIA = 0;
					pontosOponente = 0;
				}

				// Se x <= 3 e y >= 3, checando diagonal cima/direita
				if (x <= 3 && y >= 3) {
					for (int k = 0; k < 4; ++k) {
						if (t.tabuleiro[y - k][x + k] == Tabuleiro.IA)
							pontosIA++;
						else if (t.tabuleiro[y - k][x + k] == Tabuleiro.OPONENTE)
							pontosOponente++;
						else
							break;
					}

					if (pontosIA == 4)
						return Tabuleiro.IA;
					else if (pontosOponente == 4)
						return Tabuleiro.OPONENTE;

					pontosIA = 0;
					pontosOponente = 0;
				}

				// Se x >= 3 e y >=3, checando diagonal cima/esquerda
				if (x >= 3 && y >= 3) {
					for (int k = 0; k < 4; ++k) {
						if (t.tabuleiro[y - k][x - k] == 1)
							pontosIA++;
						else if (t.tabuleiro[y - k][x - k] == 2)
							pontosOponente++;
						else
							break;
					}
					if (pontosIA == 4)
						return Tabuleiro.IA;
					else if (pontosOponente == 4)
						return Tabuleiro.OPONENTE;

					pontosIA = 0;
					pontosOponente = 0;
				}
			}
		}

		for (int j = 0; j < 7; ++j) {
			// Jogo ainda n�o finalizou
			if (t.tabuleiro[0][j] == 0)
				return -1;
		}
		// Empate
		return 0;
	}

	int calculaPontuacaoEixo(int posicoesConsequentes) {
		Double x = Math.pow(2, posicoesConsequentes);
		return 100 * x.intValue();
	}

	public int avalia(Tabuleiro t) {
		int pontuacao = 0;

		for (int y = 5; y >= 0; --y) {
			for (int x = 0; x <= 6; ++x) {
				pontuacao += calculaPontuacaoPosicao(t, y, x);
			}
		}
		
		return pontuacao;
	}

	private int calculaPontuacaoPosicao(Tabuleiro t, int y, int x) {
		int pontuacaoPosicao = 0;
		
		if (t.tabuleiro[y][x] == Tabuleiro.VAZIA || t.tabuleiro[y][x] == Tabuleiro.OPONENTE)
			return 0;

		if (x <= 3) {
			int pontuacaoHorizontalDireita = 1;
			
			for (int k = 1; k < 4; ++k) {
				if (t.tabuleiro[y][x + k] == Tabuleiro.IA)
					pontuacaoHorizontalDireita++;
				else if (t.tabuleiro[y][x + k] == Tabuleiro.OPONENTE) {
					pontuacaoHorizontalDireita = 0;
					break;
				}
			}

			pontuacaoPosicao += calculaPontuacaoEixo(pontuacaoHorizontalDireita);
		}
		
		if (x >= 3) {
			int pontuacaoHorizontalEsquerda = 1;
			
			for (int k = 1; k < 4; ++k) {
				if (t.tabuleiro[y][x - k] == Tabuleiro.IA)
					pontuacaoHorizontalEsquerda++;
				else if (t.tabuleiro[y][x - k] == Tabuleiro.OPONENTE) {
					pontuacaoHorizontalEsquerda = 0;
					break;
				}
			}

			pontuacaoPosicao += calculaPontuacaoEixo(pontuacaoHorizontalEsquerda);
		}

		if (y >= 3) {
			int pontuacaoVertical = 1;
			
			for (int k = 1; k < 4; ++k) {
				if (t.tabuleiro[y - k][x] == Tabuleiro.IA)
					pontuacaoVertical++;
				else if (t.tabuleiro[y - k][x] == Tabuleiro.OPONENTE) {
					pontuacaoVertical = 0;
					break;
				}
			}

			pontuacaoPosicao += calculaPontuacaoEixo(pontuacaoVertical);
		}

		if (x <= 3 && y >= 3) {
			int pontuacaoDiagonalDireita = 1; 
			
			for (int k = 1; k < 4; ++k) {
				if (t.tabuleiro[y - k][x + k] == Tabuleiro.IA)
					pontuacaoDiagonalDireita++;
				else if (t.tabuleiro[y - k][x + k] == Tabuleiro.OPONENTE) {
					pontuacaoDiagonalDireita = 0;
					break;
				}
			}

			pontuacaoPosicao += calculaPontuacaoEixo(pontuacaoDiagonalDireita);
		}

		if (y >= 3 && x >= 3) {
			int pontuacaoDiagonalEsquerda = 1;
			
			for (int k = 1; k < 4; ++k) {
				if (t.tabuleiro[y - k][x - k] == Tabuleiro.IA)
					pontuacaoDiagonalEsquerda++;
				else if (t.tabuleiro[y - k][x - k] == Tabuleiro.OPONENTE) {
					pontuacaoDiagonalEsquerda = 0;
					break;
				}
			}
			
			pontuacaoPosicao += calculaPontuacaoEixo(pontuacaoDiagonalEsquerda);
		}
		
		return pontuacaoPosicao;
	}

	public int minimax(int profundidade, int jogador, int alpha, int beta) {
		if (beta <= alpha) {
			if (jogador == Tabuleiro.IA)
				return Integer.MAX_VALUE;
			else
				return Integer.MIN_VALUE;
		}

		int resultado = getResultado(t);

		switch (resultado) {
		case 1:
			return Integer.MAX_VALUE / 2;
		case 2:
			return Integer.MIN_VALUE / 2;
		case 0:
			return 0;
		}

		if (profundidade == profundidadeMaxima)
			return avalia(t);

		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;

		for (int j = 0; j <= 6; ++j) {
			int pontuacaoAtual = 0;

			if (!t.jogadaPossivel(j))
				continue;

			if (jogador == Tabuleiro.IA) {
				t.realizaJogada(j, 1);
				pontuacaoAtual = minimax(profundidade + 1, Tabuleiro.OPONENTE, alpha, beta);

				if (profundidade == 0) {
					System.out.println("Pontua��o da localiza��o " + j + " = " + pontuacaoAtual);
					if (pontuacaoAtual > max)
						proximaJogada = j;
					if (pontuacaoAtual == Integer.MAX_VALUE / 2) {
						t.removeJogada(j);
						break;
					}
				}

				max = Math.max(pontuacaoAtual, max);

				alpha = Math.max(pontuacaoAtual, alpha);
			} else if (jogador == Tabuleiro.OPONENTE) {
				t.realizaJogada(j, Tabuleiro.OPONENTE);
				pontuacaoAtual = minimax(profundidade + 1, Tabuleiro.IA, alpha, beta);
				min = Math.min(pontuacaoAtual, min);

				beta = Math.min(pontuacaoAtual, beta);
			}

			t.removeJogada(j);
			if (pontuacaoAtual == Integer.MAX_VALUE || pontuacaoAtual == Integer.MIN_VALUE)
				break;
		}

		return jogador == Tabuleiro.IA ? max : min;
	}

	public int getJogadaIA() {
		proximaJogada = 3;
		minimax(0, Tabuleiro.IA, Integer.MIN_VALUE, Integer.MAX_VALUE);
		System.out.println("IA obteve como melhor jogada: " + proximaJogada + "\n\n");
		return proximaJogada;
	}

	public void jogar() {
		System.out.println("Jogar primeiro? (s/n) ");
		String resposta = scanner.next().trim();
		
		t.imprimeTabuleiro();

		if (resposta.equalsIgnoreCase("s"))
			realizaJogadaOponente();

		t.imprimeTabuleiro();
		t.realizaJogada(3, Tabuleiro.IA);
		t.imprimeTabuleiro();

		while (true) {
			realizaJogadaOponente();
			t.imprimeTabuleiro();

			int resultado = getResultado(t);

			if (resultado == 1) {
				System.out.println("IA venceu!");
				return;
			} else if (resultado == 2) {
				System.out.println("Voc� venceu!");
				return;
			} else if (resultado == 0) {
				System.out.println("Empate!");
				return;
			}

			t.realizaJogada(getJogadaIA(), Tabuleiro.IA);
			t.imprimeTabuleiro();

			resultado = getResultado(t);

			if (resultado == 1) {
				System.out.println("IA venceu!");
				return;
			} else if (resultado == 2) {
				System.out.println("Voc� venceu!");
				return;
			} else if (resultado == 0) {
				System.out.println("Empate!");
				return;
			}
		}
	}
	
	public int[] testaProfundidade(int profundidade, int primeiro, int repeticoes) {
		this.profundidadeMaxima = profundidade;
		int vitoriasIA = 0;
		int vitoriasOponente = 0;
		int empates = 0;
		int[] result = {0,0,0};
		boolean aux;
		
		for(int i = 0; i < repeticoes; i++) {
			aux = true;
			this.t = new Tabuleiro();
			
			if (primeiro == Tabuleiro.OPONENTE)
				realizaJogadaAleatoria();

			t.realizaJogada(3, Tabuleiro.IA);

			while (aux) {
				realizaJogadaAleatoria();

				int resultado = getResultado(t);

				
				if (resultado == 1) {
					vitoriasIA++;
					aux = false;
					break;
				} else if (resultado == 2) {
					vitoriasOponente++;
					aux = false;
					break;
				} else if (resultado == 0) {
					empates++;
					aux = false;
					break;
				}

				t.realizaJogada(getJogadaIA(), Tabuleiro.IA);

				resultado = getResultado(t);

				if (resultado == 1) {
					vitoriasIA++;
					aux = false;
					break;
				} else if (resultado == 2) {
					vitoriasOponente++;
					aux = false;
					break;
				} else if (resultado == 0) {
					empates++;
					aux = false;
					break;
				}
			}
		}

		result[0] = empates;
		result[1] = vitoriasOponente;
		result[2] = vitoriasIA;
		
		System.out.println("Profundidade: " + profundidade + "\n"
				+ "Primeiro a jogar: " + primeiro + "\n"
				+ "Empates: "+empates+"\n"
				+ "Vit�rias IA: " + vitoriasIA + "\n"
				+ "Vit�rias Oponente: " + vitoriasOponente);
		
		return result;
	}
	
	public void realizaJogadaAleatoria() {
		int jogada = (int) (Math.random() * 7);
		
		while (jogada < 0 || jogada > 6 || !t.jogadaPossivel(jogada)) {
			jogada = (int) (Math.random() * 7);
		}

		t.realizaJogada(jogada, Tabuleiro.OPONENTE);
	}

}
