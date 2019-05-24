package com.tcs.CustomerInsuranceProject.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.tcs.CustomerInsuranceProject.model.CustomerInfo;

@Repository
public interface CustomerInsuranceRepository extends MongoRepository<CustomerInfo,String>{

}
