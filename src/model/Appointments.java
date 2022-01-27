package model;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * Main class for Appointments. Includes getters and setters.
 */
public class Appointments {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String updatedBy;
    private int customerId;
    private int userId;
    private int contactId;

    /** Method to get appointment id.
     * @return
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /** Method to set appointment id.
     * @param appointmentId
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /** Method to return appointment title.
     * @return
     */
    public String getTitle() {
        return title;
    }

    /** Method to set title.
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Method to get location.
     * @return
     */
    public String getLocation() {
        return location;
    }

    /** Method to set location.
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /** Method to get contact.
     * @return
     */
    public String getContact() {
        return contact;
    }

    /** Method to set contact.
     * @param contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /** Method to get description.
     * @return
     */
    public String getDescription() {
        return description;
    }

    /** Method to set description.
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /** Method to get type.
     * @return
     */
    public String getType() {
        return type;
    }

    /** Method to set type.
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /** Method to get start.
     * @return
     */
    public LocalDateTime getStart() {
        return start;
    }

    /** Method to set start.
     * @param start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /** Method to get end.
     * @return
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /** Method to set end.
     * @param end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /** Method to get creation date.
     * @return
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /** Method to set creation date.
     * @param createDate
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /** Method to get creator.
     * @return
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /** Method to set creator.
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** Method to get last update time.
     * @return
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /** Method to set last update time.
     * @param lastUpdate
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /** Method to get customer id.
     * @return
     */
    public int getCustomerId() {
        return customerId;
    }

    /** Method to set customer id.
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /** Method to get user id.
     * @return
     */
    public int getUserId() {
        return userId;
    }

    /** Method to set user id.
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** Method to get contact id.
     * @return
     */
    public int getContactId() {
        return contactId;
    }

    /** Method to set contact id.
     * @param contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
