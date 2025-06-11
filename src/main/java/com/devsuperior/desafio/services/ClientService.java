package com.devsuperior.desafio.services;

import com.devsuperior.desafio.dtos.ClientDTO;
import com.devsuperior.desafio.entities.Client;
import com.devsuperior.desafio.repositories.ClientRepository;
import com.devsuperior.desafio.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id){
        Client entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cliente não encontrado com o ID:" + id));
        return new ClientDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable){
        Page<Client> clients = repository.findAll(pageable);
        return clients.map(ClientDTO::new);
    }

    @Transactional
    public ClientDTO insert(ClientDTO dto){
        Client entity = new Client();
        copyDtoToEntity(dto,entity);
        entity = repository.save(entity);
        return new ClientDTO(entity);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto){
        Client entity = repository.getReferenceById(id);
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ClientDTO(entity);
    }

    @Transactional
    public void delete(Long id){
        repository.deleteById(id);
    }

    private void copyDtoToEntity(ClientDTO dto, Client entity) {
        entity.setName(dto.getName());
        entity.setBirthDate(dto.getBirthDate());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setChildren(dto.getChildren());
    }
}
