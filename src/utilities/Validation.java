package utilities;

import java.sql.Timestamp;
import java.time.*;

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
}
