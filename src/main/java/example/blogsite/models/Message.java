package example.blogsite.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String message;
    private LocalDateTime sendAt;
    private Long fromId;
    private Long toId;
    @Transient
    private User from;
    @Transient
    private User to;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userA_id", referencedColumnName = "userAId")
    @JoinColumn(name = "userB_id", referencedColumnName = "userBId")
    private Chat chat;

    public String getSendAtFormat() {
        LocalDateTime timeNow = LocalDateTime.now();
        Duration duration = Duration.between(sendAt, timeNow);

        DateTimeFormatter formatter;

        if (duration.toDays() < 1) {
            formatter = DateTimeFormatter.ofPattern("hh:mm");
            return sendAt.format(formatter);
        }
        else if (duration.toDays() < 2) {
            formatter = DateTimeFormatter.ofPattern("hh:mm");
            return sendAt.format(formatter) + " hôm qua";
        }
        long years = ChronoUnit.YEARS.between(sendAt, timeNow);
        if (years < 1) {
            formatter = DateTimeFormatter.ofPattern("hh:mm dd-MM");
            return sendAt.format(formatter);
        }
        formatter = DateTimeFormatter.ofPattern("MM-yyyy");
        return sendAt.format(formatter);
    }
}
