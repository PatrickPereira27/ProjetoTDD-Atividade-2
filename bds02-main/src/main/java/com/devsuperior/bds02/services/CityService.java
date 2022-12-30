package com.devsuperior.bds02.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.services.exceptions.DatabaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class CityService {

	@Autowired
	private CityRepository repository;
	
	@Transactional(readOnly = true)
	public List<CityDTO> findAll(){
		List<City> lista = repository.findAll(Sort.by("name"));
		return lista.stream().map(c -> new CityDTO(c)).collect(Collectors.toList());
	}

	@Transactional
	public CityDTO save(CityDTO dto) {
		City city = new City();
		city.setName(dto.getName());
		city = repository.save(city);
		return new CityDTO(city);
		
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);			
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(String.format("Id %s não localizado para remoção", id));
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(String.format("Cidade com id %s não pode ser removida, vinculada a eventos", id));
		}		
	}
	
	
	
}
