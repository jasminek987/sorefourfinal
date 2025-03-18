package model;

public class Move {

    public boolean putBead(Player player, Peg peg, Bead bead) {
        if (peg.isFull()) {
            System.out.println("Oops! The peg is full. Try again!");
            return false;
        }
        peg.addBead(bead.getColor());
        System.out.println("Player " + player.getName() + " placed a " + bead.getColor() + " bead on Peg " + peg);
        return true;
    }
}