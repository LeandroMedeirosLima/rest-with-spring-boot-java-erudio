package br.com.erudio.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.controllers.BookController;
import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Book;
import br.com.erudio.repositories.BookRepository;

@Service
public class BookService {

    private Logger logger = Logger.getLogger(BookService.class.getName());
    
    @Autowired
    private BookRepository repository;
    
    
    public List<BookVO> findAll() {
        logger.info("Finding all books");
        
        var booksList = repository.findAll();
        var booksVoList = DozerMapper.parseListObjects(booksList, BookVO.class);
        
        booksVoList
            .stream()
            .forEach(book -> book.add(linkTo(methodOn(BookController.class).findById(book.getKey())).withSelfRel()));
        
        return booksVoList;
    }
    
    public BookVO findById(Long id) {
        logger.info("Finding a book by id");
        
        var book = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id " + id));
        
        BookVO bookVo = DozerMapper.parseObject(book, BookVO.class); 
        bookVo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        
        return bookVo;
    }
    
    public List<BookVO> findByExample(BookVO bookVo) {
        return new ArrayList<BookVO>();
    }
    
    public BookVO create(BookVO book) {
//        if (bookVo == null) { throw new RequiredObjectIsNullException(); }  
//        
//        logger.info("Creating book");
//        var book = DozerMapper.parseObject(bookVo, Book.class); 
//        var vo = DozerMapper.parseObject(repository.save(book), BookVO.class);
//        
//        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
//        return vo;
        
        if (book == null) throw new RequiredObjectIsNullException();
        
        logger.info("Creating one book!");
        var entity = DozerMapper.parseObject(book, Book.class);
        var vo =  DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }
    
    public BookVO update(BookVO bookVo) {
        
        if (bookVo == null) { throw new RequiredObjectIsNullException(); }  
        logger.info("Update person");
       
        var book = repository.findById(bookVo.getKey())
                             .orElseThrow(() -> new ResourceNotFoundException("No records found for this id " + bookVo.getKey()));

        book.setAuthor(bookVo.getAuthor());
        book.setTitle(bookVo.getTitle());
        book.setLaunchDate(bookVo.getLaunchDate());
        book.setPrice(bookVo.getPrice());
        
        var vo = DozerMapper.parseObject(repository.save(book), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        
        return vo;
    }
    
    public void delete(Long id) {
        var entidade = repository.findById(id)
                                 .orElseThrow(() -> new ResourceNotFoundException("No records found for this id " + id));
        repository.delete(entidade);
    }
}
