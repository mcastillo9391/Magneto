package com.meli.magneto.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "Mutants.findAll", query = "SELECT c FROM Mutants c")
public class Mutants {
	
	@Id
	@Column(name = "dna")
	private String dna;

	@Column(name = "isMutant")
	private String isMutant;
	
	public void setDna(String secId) {
		this.dna = secId;
	}
	public String getDna() {
		return this.dna;
	}
	public void setIsMutant(String secId) {
		this.isMutant = secId;
	}
	public String getIsMutant() {
		return this.isMutant;
	}
}
