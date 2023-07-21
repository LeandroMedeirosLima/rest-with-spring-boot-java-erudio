package br.com.erudio.mapper.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.erudio.data.vo.v2.PersonVOV2;
import br.com.erudio.model.Person;

@Service
public class PersonMapper {

    public PersonVOV2 convertEntityToVo(Person person) {
        PersonVOV2 vo = new PersonVOV2();
        vo.setId(person.getId());
        vo.setFirstName(person.getFirstName());
        vo.setLastName(person.getLastName());
        vo.setGender(person.getGender());
        vo.setAddress(person.getAddress());
        vo.setBirthday(new Date());
        
        return vo;
    }
    
    public Person convertVoToEntity(PersonVOV2 voV2) {
        Person entity = new Person();
        entity.setId(voV2.getId());
        entity.setFirstName(voV2.getFirstName());
        entity.setLastName(voV2.getLastName());
        entity.setGender(voV2.getGender());
        entity.setAddress(voV2.getAddress());
        //vo.setBirthday(new Date());
        
        return entity;
    }
    
}
