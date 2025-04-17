package com.yahya.back_contact.ws;

import com.yahya.back_contact.model.Contact;
import com.yahya.back_contact.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yahya
 **/

@RestController
@RequestMapping("/contact")
@CrossOrigin(origins = "*")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService){
        this.contactService = contactService;
    }

    @PostMapping("/ajouter")
    public ResponseEntity<String> createContact(@RequestBody Contact contact){
        contactService.createContacte(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body("Contact ajouté avec succès");
    }

    @DeleteMapping("/supprimer")
    public ResponseEntity<String> deleteContactByNom(@RequestParam String nom){
        int result = contactService.deleteContactByNom(nom);
        if (result == 1) {
            return ResponseEntity.ok("Contact supprimé");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contact non trouvé");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Contact>> findAll(){
        return ResponseEntity.ok(contactService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Contact>> search(@RequestParam String contenu){
        return ResponseEntity.ok(contactService.rechercher(contenu));
    }

    @GetMapping("/find")
    public ResponseEntity<?> findByNom(@RequestParam String nom){
        return contactService.findByNom(nom)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contact non trouvé"));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(){
        return ResponseEntity.ok(contactService.getNombreContact());
    }
}
