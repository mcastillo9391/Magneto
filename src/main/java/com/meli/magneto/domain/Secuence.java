package com.meli.magneto.domain;

public class Secuence {

	private String[] dna;
	private String sameWord;
	
	public String getSameWord() {
		return sameWord;
	}
	public void setSameWord(String sameWord) {
		this.sameWord = sameWord;
	}
	public void setDna(String[] secId) {
		this.dna = secId;
	}
	public String[] getDna() {
		return this.dna;
	}
}
