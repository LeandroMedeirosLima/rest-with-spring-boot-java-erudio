package br.com.erudio.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;
import br.com.erudio.services.PersonService;
import br.com.erudio.unittests.mapper.mocks.MockPerson;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    MockPerson input;
    
    @InjectMocks
    private PersonService service;
    
    @Mock
    PersonRepository repository; 
    
    @BeforeEach
    void setUp() throws Exception {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        Person entity = input.mockEntity(1);
        Person persisted = entity;
        persisted.setId(1L);
        
        when(repository.save(entity)).thenReturn(persisted);
        
        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);
        
        var result = service.create(vo); 
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        
        assertEquals("Addres Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }
    
    @Test
    void testCreateWithNumPerson() {
        Exception exp = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });
        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exp.getMessage();
        
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDelete() {
        Person entity = input.mockEntity(1);
        entity.setId(1L);
        
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        service.delete(1L); 
        
    }

    @Test
    void testFindAll() {
        List<Person> list = input.mockEntityList();
        
        when(repository.findAll()).thenReturn(list);
        
        
//        var peoples = service.findAll();
//        assertNotNull(peoples);
//        assertEquals(14, peoples.size());
//        
//        var personOne = peoples.get(1); 
//        assertNotNull(personOne);
//        assertNotNull(personOne.getKey());
//        assertNotNull(personOne.getLinks());
//        assertTrue(personOne.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
//        
//        assertEquals("Addres Test1", personOne.getAddress());
//        assertEquals("First Name Test1", personOne.getFirstName());
//        assertEquals("Last Name Test1", personOne.getLastName());
//        assertEquals("Female", personOne.getGender());
//        
//        var personFour = peoples.get(4); 
//        assertNotNull(personFour);
//        assertNotNull(personFour.getKey());
//        assertNotNull(personFour.getLinks());
//        assertTrue(personFour.toString().contains("links: [</api/person/v1/4>;rel=\"self\"]"));
//        
//        assertEquals("Addres Test4", personFour.getAddress());
//        assertEquals("First Name Test4", personFour.getFirstName());
//        assertEquals("Last Name Test4", personFour.getLastName());
//        assertEquals("Male", personFour.getGender());
//        
//        var personSeven = peoples.get(7); 
//        assertNotNull(personSeven);
//        assertNotNull(personSeven.getKey());
//        assertNotNull(personSeven.getLinks());
//        assertTrue(personSeven.toString().contains("links: [</api/person/v1/7>;rel=\"self\"]"));
//        
//        assertEquals("Addres Test7", personSeven.getAddress());
//        assertEquals("First Name Test7", personSeven.getFirstName());
//        assertEquals("Last Name Test7", personSeven.getLastName());
//        assertEquals("Female", personSeven.getGender());
    }
    
    @Test
    void testFindById() {
        Person entity = input.mockEntity(1);
        entity.setId(1L);
        
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        
        var result = service.findById(1L); 
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        
        assertEquals("Addres Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }


    @Test
    void testUpdate() {
        Person entity = input.mockEntity(1);
        entity.setId(1L);
        
        Person persisted = entity;
        persisted.setId(1L);
        
        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);
        
        var result = service.update(vo); 
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        
        assertEquals("Addres Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }
    
    @Test
    void testUpdateWithNumPerson() {
        Exception exp = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.update(null);
        });
        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exp.getMessage();
        
        assertTrue(actualMessage.contains(expectedMessage));
    }

}
