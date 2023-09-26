package com.example.repository;

import com.example.entity.Users;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;



@Repository
public interface UsersRepository extends CrudRepository<Users,Long> {


}
