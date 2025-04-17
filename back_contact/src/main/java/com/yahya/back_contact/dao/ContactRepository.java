package com.yahya.back_contact.dao;

import com.yahya.back_contact.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author yahya
 **/
public interface ContactRepository extends JpaRepository<Contact,Integer> {
    public int deleteByNom(String nom);
    public Optional<Contact> findByNom(String nom);
    public List<Contact> findByNomOrNumeroContaining(String nom, String numero);
}
