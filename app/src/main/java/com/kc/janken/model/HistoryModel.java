package com.kc.janken.model;

public class HistoryModel {
    int no, humanScore, GPUScore;

    public HistoryModel(int no, int humanScore, int GPUScore) {
        this.no = no;
        this.humanScore = humanScore;
        this.GPUScore = GPUScore;
    }

    public HistoryModel(int humanScore, int GPUScore) {
        this.humanScore = humanScore;
        this.GPUScore = GPUScore;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getHumanScore() {
        return humanScore;
    }

    public void setHumanScore(int humanScore) {
        this.humanScore = humanScore;
    }

    public int getGPUScore() {
        return GPUScore;
    }

    public void setGPUScore(int GPUScore) {
        this.GPUScore = GPUScore;
    }
}
