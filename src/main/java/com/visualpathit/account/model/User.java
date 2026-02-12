package com.visualpathit.account.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entity representing an application user.
 */
@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String password;

    private String userEmail;

    private String passwordConfirm;

    private String profileImg;

    private String profileImgPath;

    private String dateOfBirth;

    private String fatherName;

    private String motherName;

    private String gender;

    private String maritalStatus;

    private String permanentAddress;

    private String tempAddress;

    private String primaryOccupation;

    private String secondaryOccupation;

    private String skills;

    private String phoneNumber;

    private String secondaryPhoneNumber;

    private String nationality;

    private String language;

    private String workingExperience;

    private Set<Role> roles;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(final String userEmail) {
        this.userEmail = userEmail;
    }

    @Transient
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(final String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(final Set<Role> roles) {
        this.roles = roles;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(final String profileImg) {
        this.profileImg = profileImg;
    }

    public String getProfileImgPath() {
        return profileImgPath;
    }

    public void setProfileImgPath(final String profileImgPath) {
        this.profileImgPath = profileImgPath;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(final String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(final String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(final String motherName) {
        this.motherName = motherName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(final String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(final String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getTempAddress() {
        return tempAddress;
    }

    public void setTempAddress(final String tempAddress) {
        this.tempAddress = tempAddress;
    }

    public String getPrimaryOccupation() {
        return primaryOccupation;
    }

    public void setPrimaryOccupation(final String primaryOccupation) {
        this.primaryOccupation = primaryOccupation;
    }

    public String getSecondaryOccupation() {
        return secondaryOccupation;
    }

    public void setSecondaryOccupation(final String secondaryOccupation) {
        this.secondaryOccupation = secondaryOccupation;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(final String skills) {
        this.skills = skills;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSecondaryPhoneNumber() {
        return secondaryPhoneNumber;
    }

    public void setSecondaryPhoneNumber(final String secondaryPhoneNumber) {
        this.secondaryPhoneNumber = secondaryPhoneNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(final String nationality) {
        this.nationality = nationality;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    public String getWorkingExperience() {
        return workingExperience;
    }

    public void setWorkingExperience(final String workingExperience) {
        this.workingExperience = workingExperience;
    }
}