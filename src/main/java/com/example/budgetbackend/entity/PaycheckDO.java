package com.example.budgetbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "paychecks")
public class PaycheckDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
