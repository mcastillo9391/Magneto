package com.meli.magneto.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meli.magneto.domain.*;
import com.meli.magneto.service.FindMutant;
import com.meli.magneto.service.MutantService;



@RestController
@RequestMapping("/magneto/")
public class MutantController {
	
	@Autowired
	MutantService mutantService;
	
	String[] adn;
	
	@PostMapping("/mutant/")
	public ResponseEntity<?> save( @RequestBody Secuence mutant) {
		Mutants data = new Mutants();
		FindMutant obj = new FindMutant();		
		String sec="";		
		String sameWord;
		try {
			//Se obtiene el arreglo de secuencias del adn
			adn = Arrays.copyOf(mutant.getDna(), mutant.getDna().length + 1);	
			
			//Se arma el ID de la secuencia
			for (String str : mutant.getDna()) {
				if (sec.equals("")) {
					sec = sec+str;
				}else {
					sec = sec+","+str;
				}
				
			}
						
			data.setDna(sec);
			
			//Se determina si una misma letra puede usarse para 2 o más secuencias iguales
			if (mutant.getSameWord() == null ) {
				sameWord = "Y";
			}else {
				sameWord = mutant.getSameWord();
			}
				
			adn[adn.length-1] = sameWord;
	        
	        if (obj.isMutant(adn)){
	            data.setIsMutant("S");
	            Mutants mutants = mutantService.save(data);
	            return ResponseEntity.ok().body(mutants);
	        }else{
	            data.setIsMutant("N");
	            Mutants mutants = mutantService.save(data);
				return ResponseEntity.status(403).body(new ResponseError("403","No es mutante"));
	        }
			
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().body(new ResponseError("403",e.getMessage()));
		}

	}
	
	@GetMapping("/stats")
	public ResponseEntity<?> findAll() {
		
		List<Mutants> listaMutantes = mutantService.findall();
		StatsMapper obj = new StatsMapper();;
		int mutant=0, human=0, cant= listaMutantes.size();
		float ratio=0;
		
		//Se determina la cantidad de humanos y mutantes procesados
		for (Mutants str : listaMutantes) { 
			if (str.getIsMutant().equals("N")) {
				human++;
			}else {
				mutant++;
			}				
		}

		//Si la cantidad de registros procesados es 0 entonces el porcentaje es 0
		if (cant == 0) {
			ratio = 0;
		}else {
			ratio = (float) mutant/(cant);			
		}
		
		obj.setCount_mutant_dna(mutant);
		obj.setCount_human_dna(human);
		obj.setRatio(ratio);
		
		return  ResponseEntity.ok().body(obj);
	}

}
