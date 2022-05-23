package com.meli.magneto.service;

import java.util.List;
import java.util.Optional;

public interface GenericService<T, ID> {

	T save(T Entity) throws Exception;	
	
	T update(T Entity) throws Exception;	

	void delete(T Entity) throws Exception;

	void deleteById(ID id) throws Exception;
	
	Optional<T> findById(ID id) ;

	List<T> findall();

}
