package br.com.erudio.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;

@Service
public class PersonService {

    //private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository personRepository;

    public List<Person> findAll() {
        logger.info("Finding all person");
        return personRepository.findAll();
    }

    public Person findById(Long id) {
        logger.info("Finding one person");
        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id " + id));
    }

    public Person create(Person person) {
        logger.info("Creating person");
        return personRepository.save(person);
    }

    public Person update(Person person) {
        logger.info("Update person");
        var entidade = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id " + person.getId()));

        entidade.setFirstName(person.getFirstName());
        entidade.setLastName(person.getLastName());
        entidade.setAddress(person.getAddress());
        entidade.setGender(person.getGender());

        return personRepository.save(entidade);
    }

    public void delete(Long id) {
        var entidade = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id " + id));
        personRepository.delete(entidade);

    }
}
