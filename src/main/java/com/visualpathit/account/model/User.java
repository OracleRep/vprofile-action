package com.visualpathit.account.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * User entity.
 */
@Entity
@Table(name = "user")
public final class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * User identifier.
     */
    private Long id;

    /**
     * Username.
     */
    private String username;

    /**
     * Password (hashed when persisted).
     */
    private String password;

    /**
     * User email address.
     */
    private String userEmail;

    /**
     * Password confirmation (not persisted).
     */
    private String passwordConfirm;

    /**
     * Profile image filename.
     */
    private String profileImg;

    /**
     * Profile image absolute path.
     */
    private String profileImgPath;

    /**
     * Date of birth.
     */
    private String dateOfBirth;

    /**
     * Father's name.
     */
    private String fatherName;

    /**
     * Mother's name.
     */
    private String motherName;

    /**
     * Gender.
     */
    private String gender;

    /**
     * Marital status.
     */
    private String maritalStatus;

    /**
     * Permanent address.
     */
    private String permanentAddress;

    /**
     * Temporary address.
     */
    private String tempAddress;

    /**
     * Primary occupation.
     */
    private String primaryOccupation;

    /**
     * Secondary occupation.
     */
    private String secondaryOccupation;

    /**
     * Skills.
     */
    private String skills;

    /**
     * Primary phone number.
     */
    private String phoneNumber;

    /**
     * Secondary phone number.
     */
    private String secondaryPhoneNumber;

    /**
     * Nationality.
     */
    private String nationality;

    /**
     * Language.
     */
    private String language;

    /**
     * Working experience.
     */
    private String workingExperience;

    /**
     * Roles assigned to the user.
     */
    private Set<Role> roles;

    /**
     * Returns the user id.
     *
     * @return the user id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    /**
     * Sets the user id.
     *
     * @param newId the user id
     */
    public void setId(final Long newId) {
        this.id = newId;
    }

    /**
     * Returns the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param newUsername the username
     */
    public void setUsername(final String newUsername) {
        this.username = newUsername;
    }

    /**
     * Returns the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param newPassword the password
     */
    public void setPassword(final String newPassword) {
        this.password = newPassword;
    }

    /**
     * Returns the email address.
     *
     * @return the email address
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Sets the email address.
     *
     * @param newUserEmail the email address
     */
    public void setUserEmail(final String newUserEmail) {
        this.userEmail = newUserEmail;
    }

    /**
     * Returns the password confirmation.
     *
     * @return the password confirmation
     */
    @Transient
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    /**
     * Sets the password confirmation.
     *
     * @param newPasswordConfirm the password confirmation
     */
    public void setPasswordConfirm(final String newPasswordConfirm) {
        this.passwordConfirm = newPasswordConfirm;
    }

    /**
     * Returns roles assigned to the user.
     *
     * @return roles assigned to the user
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Sets roles assigned to the user.
     *
     * @param newRoles roles assigned to the user
     */
    public void setRoles(final Set<Role> newRoles) {
        this.roles = newRoles;
    }

    /**
     * Returns profile image filename.
     *
     * @return profile image filename
     */
    public String getProfileImg() {
        return profileImg;
    }

    /**
     * Sets profile image filename.
     *
     * @param newProfileImg profile image filename
     */
    public void setProfileImg(final String newProfileImg) {
        this.profileImg = newProfileImg;
    }

    /**
     * Returns profile image path.
     *
     * @return profile image path
     */
    public String getProfileImgPath() {
        return profileImgPath;
    }

    /**
     * Sets profile image path.
     *
     * @param newProfileImgPath profile image path
     */
    public void setProfileImgPath(final String newProfileImgPath) {
        this.profileImgPath = newProfileImgPath;
    }

    /**
     * Returns date of birth.
     *
     * @return date of birth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets date of birth.
     *
     * @param newDateOfBirth date of birth
     */
    public void setDateOfBirth(final String newDateOfBirth) {
        this.dateOfBirth = newDateOfBirth;
    }

    /**
     * Returns father name.
     *
     * @return father name
     */
    public String getFatherName() {
        return fatherName;
    }

    /**
     * Sets father name.
     *
     * @param newFatherName father name
     */
    public void setFatherName(final String newFatherName) {
        this.fatherName = newFatherName;
    }

    /**
     * Returns mother name.
     *
     * @return mother name
     */
    public String getMotherName() {
        return motherName;
    }

    /**
     * Sets mother name.
     *
     * @param newMotherName mother name
     */
    public void setMotherName(final String newMotherName) {
        this.motherName = newMotherName;
    }

    /**
     * Returns gender.
     *
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets gender.
     *
     * @param newGender gender
     */
    public void setGender(final String newGender) {
        this.gender = newGender;
    }

    /**
     * Returns marital status.
     *
     * @return marital status
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Sets marital status.
     *
     * @param newMaritalStatus marital status
     */
    public void setMaritalStatus(final String newMaritalStatus) {
        this.maritalStatus = newMaritalStatus;
    }

    /**
     * Returns permanent address.
     *
     * @return permanent address
     */
    public String getPermanentAddress() {
        return permanentAddress;
    }

    /**
     * Sets permanent address.
     *
     * @param newPermanentAddress permanent address
     */
    public void setPermanentAddress(final String newPermanentAddress) {
        this.permanentAddress = newPermanentAddress;
    }

    /**
     * Returns temporary address.
     *
     * @return temporary address
     */
    public String getTempAddress() {
        return tempAddress;
    }

    /**
     * Sets temporary address.
     *
     * @param newTempAddress temporary address
     */
    public void setTempAddress(final String newTempAddress) {
        this.tempAddress = newTempAddress;
    }

    /**
     * Returns primary occupation.
     *
     * @return primary occupation
     */
    public String getPrimaryOccupation() {
        return primaryOccupation;
    }

    /**
     * Sets primary occupation.
     *
     * @param newPrimaryOccupation primary occupation
     */
    public void setPrimaryOccupation(final String newPrimaryOccupation) {
        this.primaryOccupation = newPrimaryOccupation;
    }

    /**
     * Returns secondary occupation.
     *
     * @return secondary occupation
     */
    public String getSecondaryOccupation() {
        return secondaryOccupation;
    }

    /**
     * Sets secondary occupation.
     *
     * @param newSecondaryOccupation secondary occupation
     */
    public void setSecondaryOccupation(final String newSecondaryOccupation) {
        this.secondaryOccupation = newSecondaryOccupation;
    }

    /**
     * Returns skills.
     *
     * @return skills
     */
    public String getSkills() {
        return skills;
    }

    /**
     * Sets skills.
     *
     * @param newSkills skills
     */
    public void setSkills(final String newSkills) {
        this.skills = newSkills;
    }

    /**
     * Returns primary phone number.
     *
     * @return primary phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets primary phone number.
     *
     * @param newPhoneNumber primary phone number
     */
    public void setPhoneNumber(final String newPhoneNumber) {
        this.phoneNumber = newPhoneNumber;
    }

    /**
     * Returns secondary phone number.
     *
     * @return secondary phone number
     */
    public String getSecondaryPhoneNumber() {
        return secondaryPhoneNumber;
    }

    /**
     * Sets secondary phone number.
     *
     * @param newSecondaryPhoneNumber secondary phone number
     */
    public void setSecondaryPhoneNumber(final String newSecondaryPhoneNumber) {
        this.secondaryPhoneNumber = newSecondaryPhoneNumber;
    }

    /**
     * Returns nationality.
     *
     * @return nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Sets nationality.
     *
     * @param newNationality nationality
     */
    public void setNationality(final String newNationality) {
        this.nationality = newNationality;
    }

    /**
     * Returns language.
     *
     * @return language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets language.
     *
     * @param newLanguage language
     */
    public void setLanguage(final String newLanguage) {
        this.language = newLanguage;
    }

    /**
     * Returns working experience.
     *
     * @return working experience
     */
    public String getWorkingExperience() {
        return workingExperience;
    }

    /**
     * Sets working experience.
     *
     * @param newWorkingExperience working experience
     */
    public void setWorkingExperience(final String newWorkingExperience) {
        this.workingExperience = newWorkingExperience;
    }
}
