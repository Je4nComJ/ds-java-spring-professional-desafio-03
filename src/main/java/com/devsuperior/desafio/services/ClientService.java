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
}
