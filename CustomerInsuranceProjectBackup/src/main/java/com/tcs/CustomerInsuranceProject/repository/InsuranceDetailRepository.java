package com.tcs.CustomerInsuranceProject.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tcs.CustomerInsuranceProject.model.InsuranceDetail;

@Repository
public interface InsuranceDetailRepository extends MongoRepository<InsuranceDetail,String>{

}
