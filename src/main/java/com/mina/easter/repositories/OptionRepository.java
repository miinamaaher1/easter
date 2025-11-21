package com.mina.easter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mina.easter.entities.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {

}
