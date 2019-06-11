package com.tcs.CustomerInsuranceProjectBackup.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.tcs.CustomerInsuranceProjectBackup.model.CustomerInfo;

@Repository
public interface CustomerInsuranceRepository extends MongoRepository<CustomerInfo,String>{

}
