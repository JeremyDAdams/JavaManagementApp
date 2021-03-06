package utilities;

import controller.LogInController;
import javafx.scene.control.Alert;
import model.Appointments;

import java.time.*;
import java.time.temporal.ChronoUnit;

import static java.time.LocalDateTime.now;
import static utilities.sqlAppointments.appointments;
import static utilities.sqlAppointments.getAppointments;

/**
 * Class to run some of the lengthier validation.
 */
public class Validation {
    /** Method to ensure appointment times fall within valid business hours.
     * @param startLDT
     * @param endLDT
     * @param date
     * @return
     */
    public static boolean validBusinessHours(LocalDateTime startLDT, LocalDateTime endLDT, LocalDate date) {
        ZoneId localZoneId = ZoneId.systemDefault();
        ZonedDateTime startLZDT = ZonedDateTime.of(startLDT, localZoneId);
        ZonedDateTime endLZDT = ZonedDateTime.of(endLDT, localZoneId);
        ZonedDateTime businessStart = ZonedDateTime.of(date, LocalTime.of(8,0), ZoneId.of("America/New_York"));
        ZonedDateTime businessEnd = ZonedDateTime.of(date, LocalTime.of(22,0), ZoneId.of("America/New_York"));

        if (startLZDT.isBefore(businessStart) || startLZDT.isAfter(businessEnd) || endLZDT.isBefore(businessStart) || endLZDT.isAfter(businessEnd)) {
            return false;
        } else {
            return true;
        }
    }

    /** Method to ensure that overlapping appointments can't be created for a customer. This method is for modifying appointments and allows for the modified appointment to overlap its previous hours.
     * @param startLDT
     * @param endLDT
     * @param custId
     * @param apptId
     * @return
     */
    public static boolean noAppointmentOverlap(LocalDateTime startLDT, LocalDateTime endLDT, int custId, int apptId) {
        boolean appointmentOverlapBool = true;
        System.out.println(custId);
        getAppointments();
        for (Appointments appointment : appointments) {
            System.out.println(appointment.getCustomerId());
            if (appointment.getCustomerId() == custId) {
                if (((startLDT.isAfter(appointment.getStart()) && startLDT.isBefore(appointment.getEnd()))
                        || (endLDT.isAfter(appointment.getStart()) && endLDT.isBefore(appointment.getEnd()))) && apptId != appointment.getAppointmentId()) {
                    //return false;
                    appointmentOverlapBool = false;
                }
            } /*else {
                appointmentOverlapBool = true;
            }*/
        }
        return appointmentOverlapBool;
    }

    /** Method to ensure that overlapping appointments can't be created for a customer. This method is used when making a new appointment.
     * @param startLDT
     * @param endLDT
     * @param custId
     * @return
     */
    public static boolean noAppointmentOverlapNew(LocalDateTime startLDT, LocalDateTime endLDT, int custId) {
        boolean appointmentOverlapBool = true;
        System.out.println(custId);
        getAppointments();
        for (Appointments appointment : appointments) {
            System.out.println(appointment.getCustomerId());
            if (appointment.getCustomerId() == custId) {
                if (((startLDT.isAfter(appointment.getStart()) && startLDT.isBefore(appointment.getEnd()))
                        || (endLDT.isAfter(appointment.getStart()) && endLDT.isBefore(appointment.getEnd())))) {
                    //return false;
                    appointmentOverlapBool = false;
                }
            } /*else {
                appointmentOverlapBool = true;
            }*/
        }
        return appointmentOverlapBool;
    }

    /**
     * Method to alert users to appointments close to their log-in time.
     */
    public static void logInAppointmentAlert() {

        getAppointments();
        LocalDateTime nowPlusFifteen = now().plus(Duration.of(16, ChronoUnit.MINUTES));
        LocalDateTime nowMinusFifteen = now().minus(Duration.of(16, ChronoUnit.MINUTES));
        boolean hasAppointment = false;
        int nearAppointmentId = 0;
        LocalDateTime nearAppointmentDT = now().plusHours(100);
        for (Appointments appointment : appointments) {
            if (appointment.getUserId() == LogInController.userId) {
                if (appointment.getStart().isAfter(nowMinusFifteen) && appointment.getStart().isBefore(nowPlusFifteen)) {
                    hasAppointment = true;
                    nearAppointmentId = appointment.getAppointmentId();
                    nearAppointmentDT = appointment.getStart();

                } else {
                    hasAppointment = false;
                }
            }
        }
        if (hasAppointment == true) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setContentText("You have an appointment, ID: " + nearAppointmentId + " start time and date: " + nearAppointmentDT);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setContentText("You don't have any upcoming appointments");
            alert.show();
        }
        //return true;
    }
}
