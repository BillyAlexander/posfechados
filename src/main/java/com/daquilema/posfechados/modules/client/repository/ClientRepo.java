package com.daquilema.posfechados.modules.client.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daquilema.posfechados.modules.client.entity.Client;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {
	
	public Client findByRucIgnoreCaseAndStatusTrue(String ruc);
	
	public List<Client> findByStatusTrue();
}
