package seminar2.task3.models;


import seminar2.task3.Column;

import java.util.UUID;

@seminar2.task3.Entity
public class Entity {

    @Column(name = "id", primaryKey = true)
    private UUID id;

}
