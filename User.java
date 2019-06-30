package com.example.a90535.letgo;

public class User {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String id;
    public String firstname;
    public String lastname;
    public String email;
    public String contactno;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String password;

    public User() {
    }

    public User(String firstname, String lastname, String email, String contactno, String id,String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.contactno = contactno;
        this.id=id;
        this.password=password;
    }
}
