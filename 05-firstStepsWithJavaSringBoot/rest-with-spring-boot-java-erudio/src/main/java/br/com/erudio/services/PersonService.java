package br.com.erudio.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.data.vo.v2.PersonVOV2;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.mapper.custom.PersonMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;

@Service
public class PersonService {

    //private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private PersonMapper mapper;

    public List<PersonVO> findAll() {
        logger.info("Finding all person");
        
        var listaPerson = personRepository.findAll();
        return DozerMapper.parseListObjects(listaPerson, PersonVO.class);
    }

    public PersonVO findById(Long id) {
        logger.info("Finding one person");
        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id " + id));
        
        return DozerMapper.parseObject(entity, PersonVO.class);
    }

    public PersonVO create(PersonVO person) {
        
        logger.info("Creating person");
        var entity = DozerMapper.parseObject(person, Person.class); 
        var vo = DozerMapper.parseObject(personRepository.save(entity), PersonVO.class); 
        return vo;
    }
    
    public PersonVOV2 createV2(PersonVOV2 person) {
        
        logger.info("Creating person with VO v2");
        var entity = mapper.convertVoToEntity(person); 
        var vo = mapper.convertEntityToVo(personRepository.save(entity));
        return vo;
    }

    public PersonVO update(PersonVO person) {
        logger.info("Update person");
       
        var entity = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id " + person.getId()));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        
        var vo = DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
        return vo;
    }

    public void delete(Long id) {
        var entidade = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id " + id));
        personRepository.delete(entidade);
    }
}
