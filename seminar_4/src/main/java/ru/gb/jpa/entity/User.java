package ru.gb.jpa.entity;

import lombok.Data;


@Data
//@Entity
//@Table(name = "user")
public class User {

//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

//  @Column(name = "name")
  private String name;

}
