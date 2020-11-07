package com.tutorial.couchbase.demo.controller;

import com.tutorial.couchbase.demo.domain.Country;
import com.tutorial.couchbase.demo.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryController {

    @Autowired
    CountryRepository countryRepository;

    @GetMapping(value = "/country/{code}")
    public ResponseEntity<Country> getCountryByCode(@PathVariable("code") String code) {
        Country country = countryRepository.findByCode(code);
        return ResponseEntity.ok(country);
    }

    @DeleteMapping(value = "/country/{code}")
    public ResponseEntity deleteCountryByCode(@PathVariable("code") String code) {
        countryRepository.deleteCode(code);
        return ResponseEntity.ok().build();
    }
}
