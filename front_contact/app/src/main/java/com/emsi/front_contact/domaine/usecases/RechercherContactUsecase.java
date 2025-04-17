package com.emsi.front_contact.domaine.usecases;

import com.emsi.front_contact.domaine.entities.Contact;
import com.emsi.front_contact.domaine.repository.ContactRepository;

import java.util.List;

public class RechercherContactUsecase {
    private final ContactRepository repository;

    public RechercherContactUsecase(ContactRepository repository) {
        this.repository = repository;
    }

    public void execute(String query, ContactRepository.RepositoryCallback<List<Contact>> callback) {
        repository.search(query, callback);
    }
}
