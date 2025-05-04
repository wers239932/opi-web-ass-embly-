package beans;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeBean implements Serializable {

    public String getNow() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"));
    }
}