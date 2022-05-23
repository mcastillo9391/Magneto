package com.meli.magneto;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.MimeTypeUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.magneto.domain.Mutants;
import com.meli.magneto.domain.Secuence;
import com.meli.magneto.domain.StatsMapper;
import com.meli.magneto.service.FindMutant;
import com.meli.magneto.service.MutantService;
@AutoConfigureMockMvc
@SpringBootTest
class MagnetoApplicationTests {

	@Autowired
	  private MockMvc mockMvc;

	  @Autowired
	  private ObjectMapper objectMapper;
	  
	  @Autowired
	  private MutantService mutantService;
	
	  
	  private Secuence createMutant() {
	    Secuence mutant = new Secuence();
	    String[] dna= {	"ATTATG",
	    	       		"CGGTTC",
	    	       		"ATAGCC",
	    	       		"AGAATC",
	    	       		"TAGTAT",
	    	       		"CCCCTA"};
	    mutant.setDna(dna);
	    mutant.setSameWord("N");
	    return mutant;
	  }
	  
	  @Test
	  void stats() throws Exception {
		
		  var secuence = createMutant();
		  
		    mockMvc.perform(
		            MockMvcRequestBuilders.post("/magneto/mutant/")
		                .contentType(MediaType.APPLICATION_JSON)
		                .accept(MediaType.APPLICATION_JSON)
		                .content(objectMapper.writeValueAsString(secuence)))
		        .andExpect(status().isOk());
		    
		  var findStats = mockMvc.perform(
		            get("/magneto/stats").accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
		        .andExpect(status().isOk())
		        .andReturn();
		  var b = objectMapper.readValue(findStats.getResponse().getContentAsString(), StatsMapper.class);
		  
		  assert b.getRatio() > 0;
		  
		  var secuence1 = createHuman();
		  
		    mockMvc.perform(
		            MockMvcRequestBuilders.post("/magneto/mutant/")
		                .contentType(MediaType.APPLICATION_JSON)
		                .accept(MediaType.APPLICATION_JSON)
		                .content(objectMapper.writeValueAsString(secuence1)))
		        .andExpect(status().isForbidden());
		    
		  var findStats1 = mockMvc.perform(
		            get("/magneto/stats").accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
		        .andExpect(status().isOk())
		        .andReturn();
		  var b1 = objectMapper.readValue(findStats1.getResponse().getContentAsString(), StatsMapper.class);
		  
		  assert b1.getRatio() > 0;
	  }
	  
	  @Test
	  void findADN() throws Exception {
		
		  Secuence secuence = createMutant();
		  FindMutant findMutant = new FindMutant();	
		  String[] copy = Arrays.copyOf(secuence.getDna(), secuence.getDna().length+1);
		   
		  copy[copy.length-1] = secuence.getSameWord();
		  
		  assert findMutant.isMutant(copy);
	  }
	  
	  private Secuence createHuman() {
		    Secuence mutants = new Secuence();
		    String[] dna= {	"AAAATG",
		    	       		"CAGTGC",
		    	       		"ATAGCC",
		    	       		"AGATGC",
		    	       		"TCTTGT",
		    	       		"CTCTTG"};
		    mutants.setDna(dna);
		    mutants.setSameWord("N");
		    return mutants;
		  }
		  
		  

	  @Test
	  void contextLoads() {
		
		
	  }

}
