package com.emsi.front_contact.domaine.usecases;

import com.emsi.front_contact.domaine.entities.Contact;
import com.emsi.front_contact.domaine.repository.ContactRepository;

import java.util.List;

public class ChargerContactUsecase {

    private final ContactRepository contactRepository;

    public ChargerContactUsecase(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public void execute(ContactRepository.RepositoryCallback<List<Contact>> callback) {
        contactRepository.getContacts(callback);
    }
}
