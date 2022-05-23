package com.meli.magneto.service;

public class FindMutant {

	private char[][] adn;
    private int N;
    private String sameWord;
    
   /**
     * @param adn the adn to set
     */
    public void setAdn(char[][] adn) {
        this.adn = adn;
    } 
    
    private boolean valHorizontal(int row, int col){
        for(int i = col + 1; i < (col + 4); i++){
            if (Character.compare(adn[row][col], adn[row][i])!=0) {
                return false;
            }
        }
        
        if (sameWord.equals("N")) {
        	for(int i = col; i < (col + 4); i++){
                adn[row][i] = 'X';                
            }
		}
        return true;
    }
    
    private boolean valVertical(int row, int col){
    	
        for(int i = row + 1; i < (row + 4); i++){
            if (Character.compare(adn[row][col], adn[i][col])!=0) {
                return false;
            }
        }
        
        if (sameWord.equals("N")) {
        	for(int i = row ; i < (row + 4); i++){
        		adn[i][col] = 'X';
        	}        	 
		}
        return true;    
    }
    
    private boolean valDiagonal(int row, int col){
        for(int i = 1; i < 4; i++){
            if (Character.compare(adn[row][col], adn[row+i][col+i])!=0) {
                return false;
            }
        }
        
        if (sameWord.equals("N")) {
        	for(int i = 0; i < 4; i++){
        		adn[row+i][col+i] = 'X';
        	}
		}
        return true;
    }
    
    private boolean valDiagonalInv(int row, int col){
        for(int i = 1; i < 4; i++){
            if (Character.compare(adn[row][col], adn[row+i][col-i])!=0) {
                return false;
            }
        }
        
        if (sameWord.equals("N")) {
        	for(int i = 0; i < 4; i++){
        		adn[row+i][col-i] = 'X';
        	}
		}
        return true;
    }
    
    public boolean valDNA(){
        int cont = 0;
        //Se recorre la matriz de caracteres
        for (int i = 0; i < N; i++){
        	
            for(int j = 0; j < N; j++){
            	//Se verifica que en la posición actual es calculable la secuencia horizontalmente
                if ((j+4)<=N){
                    if (valHorizontal(i, j)) {
                        cont++;
                        if (cont >= 2) {
                            return true;
                        }
                        /*	Si no se permite que la misma letra sirva para otras combinaciones entonces continúa con la
                  			siguiente posición	*/
                        if (sameWord.equals("N")) {
                        	continue;
                        }
                    }                    
                }
                
                if ((i+4)<=N) {
                    if (valVertical(i, j)) {
                        cont++;
                        if (cont >= 2) {
                            return true;
                        }
                        /*	Si no se permite que la misma letra sirva para otras combinaciones entonces continúa con la
                  			siguiente posición	*/
                        if (sameWord.equals("N")) {
                        	continue;
                        }
                    }
                } 
                
                if (((i+4)<=N)&&((j-4)>=0)) {
                    if (valDiagonalInv(i, j)) {
                        cont++;
                        if (cont >= 2) {
                            return true;
                        }
                        /*	Si no se permite que la misma letra sirva para otras combinaciones entonces continúa con la
          					siguiente posición	*/
                        if (sameWord.equals("N")) {
                        	continue;
                        }
                    }
                }
                
                if (((i+4)<=N)&&((j+4)<=N)) {
                    if (valDiagonal(i, j)) {
                        cont++;
                        if (cont >= 2) {
                            return true;
                        }                        
                    }
                }                
            }
        }
        return false;
    }
    
    private boolean valCharacter(char var){
    	//Se valida que la letra ingresada sea una de las permitidas en la secuencia
        if ((Character.compare(var, 'A')==0) ||
            (Character.compare(var, 'T')==0) ||
            (Character.compare(var, 'C')==0) ||
            (Character.compare(var, 'G')==0))  
        {
            return true;
        }else{
            return false;
        }
        
    }
    private boolean runProcess(String[] dna){
        
    	N = dna.length-1;
    	
        if (N<4) {
        	return false;
        }else{
        	sameWord = dna[dna.length-1];
        	
        	//Se establece si se usará la misma letra para varias coincidencias
        	if (!(sameWord.equals("Y")||sameWord.equals("N"))) {
        		sameWord = "Y";
    		}        	
        	
            char[][] backup = new char[N][N];
            char[] sec;
            
            //Se formatea el array de secuencias en una matriz de caracteres
            for (int i = 0; i < N; i++){            
                sec = dna[i].toCharArray();
                for(int j = 0; j < N; j++){
                    if (valCharacter(sec[j]))  
                    {
                        backup[i][j]=sec[j];                        
                    }else{
                        return false;
                    }                    
                }
            }
            setAdn(backup);
            return valDNA();          
        }
    }
    
    public boolean isMutant(String[] dna){
        return runProcess(dna);
    }  
}
