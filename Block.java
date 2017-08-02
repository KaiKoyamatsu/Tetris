package Tetris;

import javax.swing.JLabel;

public class Block {

    private JLabel label = new JLabel();
    private int state; //0:壁 1:空白 2:ブロック 3:動いているブロック

    public Block() {}

    public JLabel getLabel() {
        return this.label;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }
}
