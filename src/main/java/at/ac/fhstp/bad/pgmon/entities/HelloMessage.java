package at.ac.fhstp.bad.pgmon.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class HelloMessage {

    @Id
    @GeneratedValue
    private Long id;
    private String messageDE;
    private String messageEN;
    private Instant created_at;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageDE() {
        return messageDE;
    }

    public void setMessageDE(String messageDE) {
        this.messageDE = messageDE;
    }

    public String getMessageEN() {
        return messageEN;
    }

    public void setMessageEN(String messageEN) {
        this.messageEN = messageEN;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }
}
