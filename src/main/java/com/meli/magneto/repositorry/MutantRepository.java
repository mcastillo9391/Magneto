package com.meli.magneto.repositorry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meli.magneto.domain.Mutants;

public interface MutantRepository extends JpaRepository<Mutants, String>{

}
