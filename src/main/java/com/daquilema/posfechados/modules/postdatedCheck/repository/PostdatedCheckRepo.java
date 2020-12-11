package com.daquilema.posfechados.modules.postdatedCheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daquilema.posfechados.modules.postdatedCheck.entity.PosdatedCheck;

@Repository
public interface PostdatedCheckRepo extends JpaRepository<PosdatedCheck, Long> {

}
