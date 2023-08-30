package com.example.server;

public class Stats {
    public int id = 0;
    public int total_shots = 0;
    public int hits = 0;
    public int missed = 0;

    public Stats(int id, int total_shots, int hits, int missed) {
        this.id = id;
        this.total_shots = total_shots;
        this.hits = hits;
        this.missed = missed;
    }
}
