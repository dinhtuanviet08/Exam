package com.example.playereval.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "indexer")
public class Indexer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_id")
    private Integer indexId;

    @Column(nullable = false, length = 64)
    private String name;

    @Column(nullable = false)
    private Float valueMin;

    @Column(nullable = false)
    private Float valueMax;

    public Integer getIndexId() { return indexId; }
    public void setIndexId(Integer indexId) { this.indexId = indexId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Float getValueMin() { return valueMin; }
    public void setValueMin(Float valueMin) { this.valueMin = valueMin; }
    public Float getValueMax() { return valueMax; }
    public void setValueMax(Float valueMax) { this.valueMax = valueMax; }
}
