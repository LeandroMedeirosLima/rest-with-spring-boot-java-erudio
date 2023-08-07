package br.com.erudio.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "books")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "author", nullable = true, length = 100)
    private String author;
    
    @Column(name = "launch_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date launchDate;
    
    @Column(name = "price", nullable = false)
    private Double price;
    
    @Column(name = "title", nullable = true, length = 100)
    private String title;

    public String getAuthor() {
        return author;
    }

    public Long getId() {
        return id;
    }

    public Date getLaunchDate() {
        return launchDate;
    }

    public Double getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Book other = (Book) obj;
        return Objects.equals(author, other.author) && Objects.equals(id, other.id)
                && Objects.equals(launchDate, other.launchDate) && Objects.equals(price, other.price)
                && Objects.equals(title, other.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, id, launchDate, price, title);
    }

}
