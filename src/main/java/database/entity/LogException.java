package database.entity;

import jakarta.persistence.*;

import java.io.PrintWriter;
import java.io.StringWriter;

@Entity
@Table( name = "log_exception")
public class LogException {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "city")
    private String city;

    @Column(name = "exc_message",nullable = false, columnDefinition = "TEXT")
    private String exception;

    public LogException() {
    }

    public LogException(String city, Throwable exception) {
        this.city = city;
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        this.exception = sw.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
