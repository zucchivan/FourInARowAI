package com.zucchivan;

public class Tabuleiro{

	public static int IA = 1;
	public static int OPONENTE = 2;
	public static int VAZIA = 0;
	
    byte[][] tabuleiro = new byte[6][7];
	    
    public Tabuleiro(){
        tabuleiro = new byte[][]{
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,},    
        };
    } 
	    
    public boolean jogadaPossivel(int coluna){
        return tabuleiro[0][coluna] == 0;
    }
	    
    public boolean realizaJogada(int coluna, int jogador){ 
    	if(!jogadaPossivel(coluna)) {
        	System.out.println("Jogada inv�lida!"); 
        	return false;
        }
        
        for(int i=5;i>=0;--i){
            if(tabuleiro[i][coluna] == 0) {
                tabuleiro[i][coluna] = (byte)jogador;
                return true;
            }
        }
        return false;
    }
    
    public void removeJogada(int coluna){
        for(int i=0;i<=5;++i){
            if(tabuleiro[i][coluna] != 0) {
                tabuleiro[i][coluna] = 0;
                break;
            }
        }        
    }

    public void imprimeTabuleiro(){
    	System.out.println("0 1 2 3 4 5 6");
        for(int i=0;i<=5;++i){
            for(int j=0;j<=6;++j){
                System.out.print(tabuleiro[i][j]+" ");
	            }
	            System.out.println();
	        }
	        System.out.println();
	    }
	}