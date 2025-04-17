package com.yahya.back_contact.service;

import com.yahya.back_contact.dao.ContactRepository;
import com.yahya.back_contact.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author yahya
 **/

@Service
public class ContactService {
    private ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository){
        this.contactRepository=contactRepository;
    }

    public void createContacte(Contact contact){
        contactRepository.save(contact);
    }

    public int deleteContactByNom(String nom){
        Optional<Contact> contact=contactRepository.findByNom(nom);
        if (contact.isPresent()){
            contactRepository.deleteByNom(nom);
            return 1;
        }
        return 0;
    }

    public List<Contact> findAll(){
        return contactRepository.findAll();
    }

    public Optional<Contact> findByNom(String nom){
        return contactRepository.findByNom(nom);
    }

    public Long getNombreContact(){
       return contactRepository.count();
    }

    public List<Contact> rechercher(String contenu){
        return contactRepository.findByNomOrNumeroContaining(contenu, contenu);
    }
}
