package com.tcs.CustomerInsuranceProjectBackup.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tcs.CustomerInsuranceProjectBackup.model.InsuranceDetail;

@Repository
public interface InsuranceDetailRepository extends MongoRepository<InsuranceDetail,String>{

}
