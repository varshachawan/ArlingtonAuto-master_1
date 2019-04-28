package com.arunika.arlingtonauto.model;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String utaId;
    private int aacMembership;
    private String role;
    private int isRevoked;

    public int getId() { return id; }
    public void setId(int id) { this.id=id; }

    public int getAacMembership() { return aacMembership; }
    public void setAacMembership(int aacMembership) { this.aacMembership=aacMembership; }

    public int getIsRevoked() { return isRevoked; }
    public void setIsRevoked(int isRevoked) { this.isRevoked = isRevoked; }

    public String getUtaId() { return utaId; }
    public void setUtaId(String utaId) { this.utaId=utaId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username=username; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName=firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName=lastName; }

    public String getPassword() { return password; }
    public void setPassword(String password){ this.password=password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email=email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role=role; }

    /************* VERIFY LOGIN ****************/
    public boolean verifyPassword (String passwordEntered) {
        return this.password.equals(passwordEntered);
    }
}