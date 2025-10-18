package com.example.demo.web;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberForm {

  @NotEmpty(message = "Member name is required.")
  private String name;

  private String city;

  private String street;

  private String zipcode;

}