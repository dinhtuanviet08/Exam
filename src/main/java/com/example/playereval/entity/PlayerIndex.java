package com.example.playereval.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "player_index")
public class PlayerIndex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "index_id", nullable = false)
    private Indexer indexer;

    @Column(nullable = false)
    @jakarta.validation.constraints.Min(0)
    @jakarta.validation.constraints.Max(100)
    private Integer value;



    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }
    public Indexer getIndexer() { return indexer; }
    public void setIndexer(Indexer indexer) { this.indexer = indexer; }
    public Integer getValue() { return value; }
    public void setValue(Integer value) { this.value = value; }
}
