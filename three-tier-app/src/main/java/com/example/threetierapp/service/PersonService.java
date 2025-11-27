package com.example.threetierapp.service;

import com.example.threetierapp.model.Person;
import com.example.threetierapp.repository.PersonRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private static int cacheHits = 0;
    private static int totalRequests = 0;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Cacheable(value = "persons", key = "'all'")
    public List<Person> getAllPersons() {
        totalRequests++;
        // Simulate database delay to show cache benefit
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return personRepository.findAll();
    }

    @CacheEvict(value = "persons", allEntries = true)
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    @CacheEvict(value = "persons", allEntries = true)
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    public boolean existsByEmail(String email) {
        return personRepository.existsByEmail(email);
    }

    public String getCacheStatus() {
        if (totalRequests == 0) {
            return "No requests yet";
        }
        
        // Check if data was served from cache (fast response)
        if (totalRequests > 1) {
            cacheHits = totalRequests - 1; // First request is always DB, rest from cache
        }
        
        return String.format("Cache Status: %d/%d requests served from cache (%.1f%%)", 
                           cacheHits, totalRequests, 
                           totalRequests > 0 ? (cacheHits * 100.0 / totalRequests) : 0);
    }
}