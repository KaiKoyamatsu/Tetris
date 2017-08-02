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

    //フィールドのサイズ決め
    static final int height = 30;
    static final int width = 10;
    //+1は地面,+2は壁
    static final int window_height = 500;
    static final int window_width = 700;

    //Blockの座標をキーとしてインスタンスを格納
    HashMap<String, Block> map = new HashMap<>();

    //Blockを落とす開始位置
    static final int blockNum = 4;//ブロック数
    private int[] mx = new int[blockNum];//x座標
    private int[] my = new int[blockNum];//y座標

    static final int moveRight = 1;
    static final int moveLeft = -1;

    Field() {
        setTitle("Tetris");
        setBounds(10, 10, window_height, window_width);     //ウィンドウサイズの指定
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
        int r = new Random().nextInt(2);
//        r = 0;
        switch (r) {
            //0が中心
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

        b.setState(state);

        getContentPane().add(p, BorderLayout.CENTER);
        Container contentPane = getContentPane();
        contentPane.add(p);//パネルの更新
    }

    public void act() {
        if (isGround()) {
            for (int i = 0; i < blockNum; i++) {
                downBlock(mx[i], my[i]);
                my[i]++;
            }
        }
        for (int i = height; i >= 0; i--) {
            if (isCheckLine(i)) {
                deleteLine(i);
            }
        }
    }

    //キーが押された時の動作
    public void keyAction(int key) {
        switch (key) {
            case KeyEvent.VK_RIGHT:
                if (isWall(moveRight)) {
                    for (int i = 0; i < blockNum; i++) {
                        moveRight(mx[i], my[i]);
                        mx[i]++;
                    }
                }
                break;
            case KeyEvent.VK_LEFT:
                if (isWall(moveLeft)) {
                    for (int i = 0; i < blockNum; i++) {
                        moveLeft(mx[i], my[i]);
                        mx[i]--;
                    }
                }
                break;
            case KeyEvent.VK_DOWN:
                if (isGround()) {
                    for (int i = 0; i < blockNum; i++) {
                        moveDown(mx[i], my[i]);
                        my[i]++;
                    }
                }
                break;
            case KeyEvent.VK_UP:
                /*
                if (isGround()) {
                    for (int i = 0; i < blockNum; i++) {
                        moveDown(mx[i], my[i]);
                        my[i]++;
                    }
                }
                 */
                break;
        }
    }

    //Blockを1つ下へ移動
    public void downBlock(int mx, int my) {
        if (my == 0) {
            Block b = map.get(mx + "," + my);
            b.getLabel().setBackground(Color.GREEN);
            b.setState(mBlock);
        } else if (my > 0) {
            Block before = map.get((mx) + "," + (my - 1));
            before.getLabel().setBackground(Color.GRAY);
            before.setState(space);

            Block b = map.get(mx + "," + my);
            b.getLabel().setBackground(Color.GREEN);
            b.setState(mBlock);
        }
    }

    //右に移動
    public void moveRight(int mx, int my) {
        if (my > 0) {
            Block before = map.get(mx + "," + (my - 1));
            before.getLabel().setBackground(Color.GRAY);
            before.setState(space);

            Block b = map.get((mx + 1) + "," + (my - 1));
            b.getLabel().setBackground(Color.GREEN);
            b.setState(mBlock);
        }
    }

    //左に移動
    public void moveLeft(int mx, int my) {
        if (my > 0) {
            Block before = map.get(mx + "," + (my - 1));
            before.getLabel().setBackground(Color.GRAY);
            before.setState(space);

            Block b = map.get((mx - 1) + "," + (my - 1));
            b.getLabel().setBackground(Color.GREEN);
            b.setState(mBlock);
        }
    }

    //下に移動
    public void moveDown(int mx, int my) {
        if (my > 0) {
            Block before = map.get(mx + "," + (my - 1));
            before.getLabel().setBackground(Color.GRAY);
            before.setState(space);

            Block b = map.get(mx + "," + my);
            b.getLabel().setBackground(Color.GREEN);
            b.setState(mBlock);
        }
    }

    public void turnBlock() {
        int[][] turnPlace = new int[5][5];

        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                turnPlace[0][0] = 0;
            }
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
