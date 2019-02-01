package com.mycontacts.app.contactbook.data;

import com.mycontacts.app.contactbook.model.ContractBookProto.Contact;
import com.mycontacts.app.contactbook.model.ContractBookProto.ContactBook;
import org.springframework.stereotype.Repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ContactBookRepository {

    final String FILE = "/tmp/contact_book";

    List<Contact> contacts;

    /**
     * @param contacts
     */
    public ContactBookRepository(List<Contact> contacts) {
        this.contacts = contacts;
    }

    /**
     * Load ContactBook from FILE
     * @throws IOException
     */
    public ContactBookRepository() throws IOException{
        try {
//            Load ContactBook From FILE
            FileInputStream fileInputStream = new FileInputStream(FILE);

            ContactBook contactBook = ContactBook.parseDelimitedFrom(fileInputStream);
            this.contacts = contactBook.getContactList();
        }  catch (Exception e) {
            this.contacts = new ArrayList<>();
        }
    }

    public void addContact(Contact contact){
        this.contacts.add(contact);
    }

    /**
     * Set Contact
     * @param contacts
     */
    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    /**
     * Get all Contact List
     * @return
     */
    public List<Contact> findAll() {
		return contacts;
    }

    public Contact findById(int id) {
		return contacts.stream().filter(contact -> contact.getId() == id).findFirst().get();
	}


    /**
     *  Save Contact
     *
     * @return
     * @throws IOException
     */
    public void save(Contact contact) throws IOException {
        this.contacts.add(contact);
        ContactBook contactBook = ContactBook.newBuilder().addAllContact(this.findAll()).build();
        FileOutputStream fileOutputStream = new FileOutputStream(FILE);
        contactBook.writeTo(fileOutputStream);
        fileOutputStream.close();
    }


}