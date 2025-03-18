package model;

import java.util.ArrayList;
import java.util.List;

public class Peg {
    private List<Bead> beads = new ArrayList<>();
    private final int maxCapacity = 4;
    private int pegRow;
    private int pegCol;

    public Peg(int row, int col) {
        this.pegRow = row;
        this.pegCol = col;
    }

    public boolean isFull() {
        return beads.size() == maxCapacity;
    }

    public int getPegRow() {
        return pegRow;
    }

    public int getPegCol() {
        return pegCol;
    }

    public int getAvailableIndex(){
        if (!isFull()){
            for (Bead bead : beads) {
                if(bead == null){
                    return beads.indexOf(bead);
                }
            }
        }
        return -1;
    }

    public void addBead(String color) {
        if (!isFull()) {
            Bead bead = new Bead(pegRow, pegCol, getAvailableIndex(), color);
            beads.add(bead);
        }
    }

    public Bead getBead(int level) {
        return beads.get(level);
    }

    public void removeBeads(){
        beads.clear();
    }



    @Override
    public String toString() {
        for(Bead bead : beads){
            if (bead != null){
                return bead.toString()+" ";
            }
            else return " ";
        }
        return "";
    }
}
