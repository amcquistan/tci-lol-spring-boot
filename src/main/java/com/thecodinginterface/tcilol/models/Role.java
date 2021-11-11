package com.thecodinginterface.tcilol.models;

import javax.persistence.*;

@Entity(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AvailableRoles name;

    public Role(){}

    public Role(AvailableRoles name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AvailableRoles getName() {
        return name;
    }

    public void setName(AvailableRoles name) {
        this.name = name;
    }
}
