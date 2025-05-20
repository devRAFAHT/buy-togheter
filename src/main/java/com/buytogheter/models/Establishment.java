package com.buytogheter.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_establishments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Establishment extends BaseEntity{

    @Column(nullable = false)
    private Double altitude;
    @Column(nullable = false)
    private Double longitude;
    @Column(nullable = false, length = 100)
    private String name;

}
