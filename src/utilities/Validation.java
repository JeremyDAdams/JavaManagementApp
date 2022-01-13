package utilities;

import model.Appointments;

import java.sql.Timestamp;
import java.time.*;

import static utilities.sqlAppointments.appointments;

public class Validation {
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
    public static boolean noAppointmentOverlap(LocalDateTime startLDT, LocalDateTime endLDT, int custId) {
        boolean appointmentOverlapBool = false;
        for (Appointments appointment : appointments) {
            if (appointment.getCustomerId() == custId) {
                if ((appointment.getStart().isBefore(startLDT) && startLDT.isBefore(appointment.getEnd()))
                        || ((endLDT.isAfter(appointment.getStart())) && endLDT.isBefore(appointment.getEnd()))) {
                    //return false;
                    appointmentOverlapBool = false;
                }
            } else {
                //return true;
                appointmentOverlapBool = true;
            }
        }
        return appointmentOverlapBool;
    }
}
