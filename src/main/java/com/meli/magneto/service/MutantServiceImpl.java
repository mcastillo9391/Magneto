package com.meli.magneto.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.meli.magneto.domain.Mutants;
import com.meli.magneto.repositorry.MutantRepository;


@Service
public class MutantServiceImpl implements MutantService{

	@Autowired
	MutantRepository mutantRepository;
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Mutants save(Mutants Entity) throws Exception {
		
		if (mutantRepository.findById(Entity.getDna()).isPresent()==true) {
			throw new Exception("La secuencia "+Entity.getDna()+" ya ha sido examinada");
		}
		return mutantRepository.save(Entity);	
	}

	@Override
	public Mutants update(Mutants Entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Mutants Entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(String id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Mutants> findById(String id) {
		// TODO Auto-generated method stub
		return mutantRepository.findById(id);
	}

	@Override
	public List<Mutants> findall() {
		// TODO Auto-generated method stub
		return mutantRepository.findAll();
	}
	

}
