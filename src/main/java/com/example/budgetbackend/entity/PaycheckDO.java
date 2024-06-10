package com.example.budgetbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "paycheck")
public class PaycheckDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "value")
    private double value;

    @Column(name = "sub_category")
    private String sub_category;

    public void setLabel(String label) {
        this.label = label;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setSubCategory(String sub_category) {
        this.sub_category = sub_category;
    }

    public PaycheckDO() {}

    public PaycheckDO(
            String label,
            double value,
            String sub_category
    ) {
        this.label = label;
        this.value = value;
        this.sub_category = sub_category;
    }
}
