package application.domain;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
public class Statistics {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;


}
