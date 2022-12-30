package com.devsuperior.bds02.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {

	@Autowired
	private EventRepository repository;
	
	public EventDTO update(EventDTO dto, Long id) {
		try {
			//getOne não consulta o banco, ele simplesmente instancia um Produto com o id, bom porque não consulta o banco de dados duas vezes, evita de consultar e depois atualizar, ele só atualiza nesse caso
			Event event =  repository.getOne(id); 
			copyDtoToModel(dto,event);
			
			event = repository.save(event);
			return new EventDTO(event);			
		}catch (Exception e) {
			throw new ResourceNotFoundException(String.format("Id %s não localizado para atualização", id));
		}

	}
	
	//método auxiliar
	public void copyDtoToModel(EventDTO dto, Event event) {
		event.setName(dto.getName());
		event.setDate(dto.getDate());
		event.setUrl(dto.getUrl());
		event.setCity(new City(dto.getCityId(),null));
	}

}
