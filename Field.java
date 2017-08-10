package Tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.util.Random;

public class Field extends JFrame {

    private JPanel p = new JPanel();

    //ブロックの種類分け
    static final int wall = 0;
    static final int space = 1;
    static final int block = 2;
    static final int mBlock = 3;

    //縦，横のブロック数
    static final int height = 20;
    static final int width = 10;
    
    //ウィンドウサイズ
    static final int window_height = 500;
    static final int window_width = 700;

    //Blockの座標をキーとしてインスタンスを格納
    HashMap<String, Block> map = new HashMap<>();

    //ブロック数
    static final int blockNum = 4;//ブロック数
    private int[] mx = new int[blockNum];//x座標
    private int[] my = new int[blockNum];//y座標

    static final int moveRight = 1;
    static final int moveLeft = -1;

    Field() {
        setTitle("Tetris");
        setBounds(10, 10, window_height, window_width); //ウィンドウサイズの指定
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //画面を閉じたときの処理
        p.setLayout(new GridLayout(height + 1, width + 2));
        initField();
        initBlock();
    }

    public void initField() {
        for (int i = 0; i < height + 1; i++) {
            for (int j = 0; j < width + 2; j++) {
                if (i == height || j == 0 || j == width + 1) {
                    addBlock(new Block(), wall, j, i);
                } else {
                    addBlock(new Block(), space, j, i);
                }
            }
        }
    }

    public void initBlock() {
        int r = new Random().nextInt(7);
        switch (r) {
            case 0:
                mx[0] = (width + 2) / 2;
                mx[1] = (width + 2) / 2;
                mx[2] = (width + 2) / 2;
                mx[3] = (width + 2) / 2;
                my[0] = 0;
                my[1] = -1;
                my[2] = -2;
                my[3] = -3;
                break;
            case 1:
                mx[0] = (width + 2) / 2;
                mx[1] = (width + 2) / 2;
                mx[2] = ((width + 2) / 2) - 1;
                mx[3] = ((width + 2) / 2) - 2;
                my[0] = 0;
                my[1] = -1;
                my[2] = -1;
                my[3] = -1;
                break;
            case 2:
                mx[0] = (width + 2) / 2;
                mx[1] = (width + 2) / 2;
                mx[2] = ((width + 2) / 2) + 1;
                mx[3] = ((width + 2) / 2) + 2;
                my[0] = 0;
                my[1] = -1;
                my[2] = -1;
                my[3] = -1;
                break;
            case 3:
                mx[0] = (width + 2) / 2;
                mx[1] = ((width + 2) / 2) + 1;
                mx[2] = ((width + 2) / 2);
                mx[3] = ((width + 2) / 2) - 1;
                my[0] = 0;
                my[1] = 0;
                my[2] = -1;
                my[3] = -1;
                break;
            case 4:
                mx[0] = (width + 2) / 2;
                mx[1] = ((width + 2) / 2) - 1;
                mx[2] = ((width + 2) / 2);
                mx[3] = ((width + 2) / 2) + 1;
                my[0] = 0;
                my[1] = 0;
                my[2] = -1;
                my[3] = -1;
                break;
            case 5:
                mx[0] = (width + 2) / 2;
                mx[1] = ((width + 2) / 2) - 1;
                mx[2] = (width + 2) / 2;
                mx[3] = ((width + 2) / 2) + 1;
                my[0] = 0;
                my[1] = -1;
                my[2] = -1;
                my[3] = -1;
                break;
            case 6:
                mx[0] = (width + 2) / 2;
                mx[1] = ((width + 2) / 2) + 1;
                mx[2] = (width + 2) / 2;
                mx[3] = ((width + 2) / 2) + 1;
                my[0] = 0;
                my[1] = 0;
                my[2] = -1;
                my[3] = -1;
                break;
        }
    }

    public void addBlock(Block b, int state, int x, int y) {
        map.put(x + "," + y, b);

        b.setState(state);
        if (state == wall) {
            b.getLabel().setBackground(Color.BLACK);
        } else {
            b.getLabel().setBackground(Color.GRAY);
        }
        b.getLabel().setBorder(new LineBorder(Color.BLACK, 1, true));
        b.getLabel().setOpaque(true);
        p.add(b.getLabel());

        getContentPane().add(p, BorderLayout.CENTER);
        Container contentPane = getContentPane();
        contentPane.add(p);//パネルの更新
    }

    public void act() {
        //地面についてなければ，ブロックを落とす
        if (isGround()) {
            moveDown();
        }
    }

    //キーが押された時の動作
    public void keyAction(int key) {
        switch (key) {
            case KeyEvent.VK_RIGHT:
                if (isWall(moveRight)) {
                    moveRight();
                }
                break;
            case KeyEvent.VK_LEFT:
                if (isWall(moveLeft)) {
                    moveLeft();
                }
                break;
            case KeyEvent.VK_DOWN:
                if (isGround()) {
                    moveDown();
                }
                break;
            case KeyEvent.VK_UP:
                if (isChangePoint()) {
                    turnBlock();
                }
                break;
        }
    }

    //ブロックを消す
    public void clear(int mx, int my) {
        if (my > 0) {
            Block b = map.get(mx + "," + (my - 1));
            b.getLabel().setBackground(Color.GRAY);
            b.setState(space);
        }
    }

    //ブロックを置く
    public void place(int mx, int my) {
        if (my > 0) {
            Block b = map.get(mx + "," + (my - 1));
            b.getLabel().setBackground(Color.GREEN);
            b.setState(mBlock);
        }
    }

    //右に移動
    public void moveRight() {
        for (int i = 0; i < blockNum; i++) {
            clear(mx[i], my[i]);
            mx[i]++;
        }

        for (int i = 0; i < blockNum; i++) {
            place(mx[i], my[i]);
        }
    }

    //左に移動
    public void moveLeft() {
        for (int i = 0; i < blockNum; i++) {
            clear(mx[i], my[i]);
            mx[i]--;
        }

        for (int i = 0; i < blockNum; i++) {
            place(mx[i], my[i]);
        }
    }

    //下に移動
    public void moveDown() {
        for (int i = 0; i < blockNum; i++) {
            clear(mx[i], my[i]);
            my[i]++;
        }

        for (int i = 0; i < blockNum; i++) {
            place(mx[i], my[i]);
        }
    }

    //回転後の座標にブロックを配置することができるか
    public boolean isChangePoint() {
        boolean flag = true;

        //ブロックの塊の中で，左側と上側のブロックを保持する変数
        int minX = mx[0];
        int minY = my[0];

        //左側と上側を探す
        for (int i = 0; i < blockNum; i++) {
            if (minX > mx[i]) {
                minX = mx[i];
            }
            if (minY > my[i]) {
                minY = my[i];
            }
        }

        int x, y;
        int tmpX, tmpY;
        for (int i = 0; i < blockNum; i++) {
            //ブロックの塊を左上に移動(どこにいても同様に回転行列を適用させるため，一時的に移動)
            tmpX = mx[i] - minX;
            tmpY = my[i] - minY;

            //回転行列の式に則り，座標を変える
            x = -tmpX + 1;
            y = tmpY;

            //元に位置に戻す
            x += minX;
            y += minY;

            //元の位置が壁かブロックの場合はflagをfalseにする
            Block b = map.get(x + "," + (y-1));
            if (b.getState() == wall || b.getState() == block) {
                flag = false;
            }
        }
        return flag;
    }

    //座標を変える
    public void changePoint() {
        //ブロックの塊の中で，左側と上側のブロックを保持する変数
        int minX = mx[0];
        int minY = my[0];

        //左側と上側を探す
        for (int i = 0; i < blockNum; i++) {
            if (minX > mx[i]) {
                minX = mx[i];
            }
            if (minY > my[i]) {
                minY = my[i];
            }
        }

        for (int i = 0; i < blockNum; i++) {
            //ブロックの塊を左上に移動(どこにいても同様に回転行列を適用させるため，一時的に移動)
            mx[i] -= minX;
            my[i] -= minY;

            //座標を変換したブロックを一時的に入れる変数
            int x, y;

            //回転行列の式に則り，座標を変える
            x = -my[i] + 1;
            y = mx[i];
            mx[i] = x;
            my[i] = y;

            //元に位置に戻す
            mx[i] += minX;
            my[i] += minY;
        }
    }

    //回転動作のメソッド
    /*制作中*/
    public void turnBlock() {
        //ブロックを消す
        for (int i = 0; i < blockNum; i++) {
            clear(mx[i], my[i]);
        }

        changePoint();

        for (int i = 0; i < blockNum; i++) {
            place(mx[i], my[i]);
        }
    }

    //地面についたときfalse，それ以外がtrueとなる
    public boolean isGround() {
        boolean flag = true;
        for (int i = 0; i < blockNum; i++) {
            if (my[i] >= 0) {
                Block b = map.get(mx[i] + "," + my[i]);
                if (b.getState() == wall || b.getState() == block) {
                    for (int j = 0; j < blockNum; j++) {
                        b = map.get(mx[j] + "," + (my[j] - 1));
                        b.setState(block);
                    }
                    initBlock();
                    flag = false;

                    //揃っていたら消す
                    for (int k = height; k >= 0; k--) {
                        if (isCheckLine(k)) {
                            deleteLine(k);
                        }
                    }
                }
            }
        }
        return flag;
    }

    //壁についたときfalse，それ以外がtrueとなる
    public boolean isWall(int dx) {
        boolean flag = true;
        switch (dx) {
            case moveRight:
                for (int i = 0; i < blockNum; i++) {
                    if (my[i] > 0) {
                        Block b = map.get((mx[i] + dx) + "," + (my[i] - 1));
                        if (b.getState() == wall || b.getState() == block) {
                            flag = false;
                        }
                    }
                }
                break;
            case moveLeft:
                for (int i = 0; i < blockNum; i++) {
                    if (my[i] > 0) {
                        Block b = map.get((mx[i] + dx) + "," + (my[i] - 1));
                        if (b.getState() == wall || b.getState() == block) {
                            flag = false;
                        }
                    }
                }
                break;
        }
        return flag;
    }

    //引数で渡された高さで，ブロックが横一列に揃った場合にtrue
    public boolean isCheckLine(int height) {
        int blockNum = 0;
        for (int i = 1; i < width + 1; i++) {
            Block b = map.get(i + "," + height);
            if (b.getState() == block) {
                blockNum++;
            }
        }
        if (blockNum == width) {
            return true;
        } else {
            return false;
        }
    }

    //ラインを消す
    public void deleteBlock(int width, int height) {
        Block b = map.get(width + "," + height);
        b.getLabel().setBackground(Color.GRAY);
        b.setState(space);
    }

    //一段下げる
    public void downLine(int width, int height) {
        if (isCheckLine(height - 1)) {
            deleteLine(height - 1);
        }

        Block on = map.get(width + "," + (height - 1));
        Block b = map.get(width + "," + height);

        if (on.getState() == block) {
            on.getLabel().setBackground(Color.GRAY);
            on.setState(space);

            b.getLabel().setBackground(Color.GREEN);
            b.setState(block);
        }
    }

//引数で渡された高さより上のブロックを下げる
    public void deleteLine(int height) {
        for (int j = 1; j < width + 1; j++) {
            deleteBlock(j, height);
        }

        for (int i = height; i > 0; i--) {
            for (int j = 1; j < width + 1; j++) {
                downLine(j, i);
            }
        }
    }
}
