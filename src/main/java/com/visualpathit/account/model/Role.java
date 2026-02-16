package com.visualpathit.account.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Role entity.
 */
@Entity
@Table(name = "role")
public class Role {

    /**
     * Role identifier.
     */
    private Long id;

    /**
     * Role name.
     */
    private String name;

    /**
     * Users assigned to this role.
     */
    private Set<User> users;

    /**
     * Returns the role id.
     *
     * @return the role id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    /**
     * Sets the role id.
     *
     * @param newId the role id
     */
    public void setId(final Long newId) {
        this.id = newId;
    }

    /**
     * Returns the role name.
     *
     * @return the role name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the role name.
     *
     * @param newName the role name
     */
    public void setName(final String newName) {
        this.name = newName;
    }

    /**
     * Returns users assigned to this role.
     *
     * @return users assigned to this role
     */
    @ManyToMany(
            fetch = FetchType.EAGER,
            mappedBy = "roles",
            cascade = CascadeType.ALL
    )
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Sets users assigned to this role.
     *
     * @param newUsers users assigned to this role
     */
    public void setUsers(final Set<User> newUsers) {
        this.users = newUsers;
    }
}
