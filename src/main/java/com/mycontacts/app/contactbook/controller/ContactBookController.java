package com.mycontacts.app.contactbook.controller;

import java.util.logging.Logger;

import com.mycontacts.app.contactbook.data.ContactEntity;
import com.mycontacts.app.contactbook.model.ContractBookProto.ContactBook;
import com.mycontacts.app.contactbook.model.ContractBookProto.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mycontacts.app.contactbook.data.ContactBookRepository;

@RestController
@RequestMapping("contacts")
public class ContactBookController {

    @Autowired
    ContactBookRepository repository;
    
    Logger logger = Logger.getLogger(ContactBookController.class.getName());

    /**
     * Get All Contacts
     * @return contactbook
     */
    @GetMapping(value = "/", produces = "application/x-protobuf")
    public ContactBook getAllContact() {
        logger.info("ContactBook.findAll()");
        return ContactBook.newBuilder().addAllContact(repository.findAll()).build();
    }
    
	@PostMapping(value = "/", consumes="application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createContact(@RequestBody ContactEntity contact) throws Exception{
        Contact newContact;
        try {
            newContact = Contact.newBuilder().setId(contact.getId()).setName(contact.getName()).build();
        }
        catch (Exception e){
            return new ResponseEntity<>(
                    "Invalid Parameters",HttpStatus.BAD_REQUEST);
        }
        repository.save(newContact);
		return new ResponseEntity<>("Successful", HttpStatus.OK);
    }
    
    @GetMapping(value = "/{id}", produces = "application/x-protobuf")
    public Contact findByNumber(@PathVariable("id") Integer id) {
        logger.info(String.format("Account.findById(%s)", id));
        return repository.findById(id);
    }





}