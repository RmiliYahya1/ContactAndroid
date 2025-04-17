package com.emsi.front_contact.domaine.usecases;

import com.emsi.front_contact.domaine.entities.Contact;
import com.emsi.front_contact.domaine.repository.ContactRepository;

public class AjouterContactUsecase {
    private final ContactRepository contactRepository;
    public AjouterContactUsecase(ContactRepository contactRepository){
        this.contactRepository=contactRepository;
    }
    public void execute(Contact contact) {
        contactRepository.ajouterContact(contact);
    }
}
