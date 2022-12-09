package org.game.snake;

import org.game.snake.constants.Direction;
import org.game.snake.event.KeyMointer;
import org.game.snake.food.Food;
import org.game.snake.food.NormalFood;
import org.game.snake.node.Node;
import org.game.snake.node.SnakeNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

public class SnakeClient extends JFrame {
    //蛇的初始坐标
    public static int StartPosition_X = 40;
    public static int StartPosition_Y = 40;
    //蛇身大小
    public static int SnakeSize = 20;
    //普通食物，后期可扩展为数组达成多个食物
    public static Food food = new NormalFood();
    //食物是否被吃
    public static int FoodAlive = 0;
    //蛇颜色
    public static Color SnakeColor = Color.red;
    //初始往右走
    public static Direction SnakeDirection = Direction.RIGHT;
    //使用链表维护蛇身节点数据
    public static LinkedList<Node> SnakeList = new LinkedList<>();
    //游戏速度
    public final static int GAME_SPEED = 200;
    //蛇身图片
    //Image normalFood= ImageIO.read(new FileInputStream("C:\\Users\\12733\\Desktop\\food.jpg"));
    //食物图片
    //Image snakeBody=ImageIO.read(new FileInputStream("C:\\Users\\12733\\Desktop\\a.jpg"));

    public SnakeClient() {
    }

    public static void main(String[] args) throws InterruptedException {
        SnakeClient snakeClient = new SnakeClient();
        snakeClient.lanch();
    }


    public void lanch() throws InterruptedException {
        this.setBounds(200, 100, 400, 400); //设置窗体大小和位置
        this.setVisible(true); //使用该属性才能显示窗体
        //实现程序运行关闭的功能
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(-1);
            }
        });

        //蛇链表加入头节点
        SnakeList.addFirst(new SnakeNode(StartPosition_X, StartPosition_Y));
        //调用键盘
        addKeyListener(new KeyMointer());
        //开始
        while (true) {
            repaint();
            Thread.sleep(GAME_SPEED);
        }
    }

    public static void snakeMove() {
        //蛇移动采取增头去尾方法,同时在增头去尾的过程中判断蛇头是否与食物坐标重合，如果重合则不去尾
        //达到蛇身增加的目的
        switch (SnakeClient.SnakeDirection) {
            case UP: {
                //创建新头节点
                SnakeNode newHeadSnakeNode = new SnakeNode();
                newHeadSnakeNode.setPosition_X(SnakeList.getFirst().getPosition_X());
                newHeadSnakeNode.setPosition_Y(SnakeList.getFirst().getPosition_Y() - 20);
                //将新节点加入链表
                SnakeList.addFirst(newHeadSnakeNode);
                //如果没吃到就去除尾节点达到增加蛇身的目的
                if (isEatFood(newHeadSnakeNode.getPosition_X(), newHeadSnakeNode.getPosition_Y(), food.getPosition_X(), food.getPosition_Y()))
                    FoodAlive--;//迟到了则食物存活符减一
                else
                    SnakeList.removeLast();
            }
            break;
            case LEFT: {
                //创建新头节点
                SnakeNode newHeadSnakeNode = new SnakeNode();

                newHeadSnakeNode.setPosition_X(SnakeList.getFirst().getPosition_X() - 20);
                newHeadSnakeNode.setPosition_Y(SnakeList.getFirst().getPosition_Y());
                //将新节点加入链表
                SnakeList.addFirst(newHeadSnakeNode);
                //如果没吃到就去除尾节点达到增加蛇身的目的
                if (isEatFood(newHeadSnakeNode.getPosition_X(), newHeadSnakeNode.getPosition_Y(), food.getPosition_X(), food.getPosition_Y()))
                    FoodAlive--;//迟到了则食物存活符减一
                else
                    SnakeList.removeLast();
            }
            break;
            case DOWN: {
                //创建新头节点
                SnakeNode newHeadSnakeNode = new SnakeNode();
                newHeadSnakeNode.setPosition_X(SnakeList.getFirst().getPosition_X());
                newHeadSnakeNode.setPosition_Y(SnakeList.getFirst().getPosition_Y() + 20);
                //将新节点加入链表
                SnakeList.addFirst(newHeadSnakeNode);
                //如果没吃到就去除尾节点达到增加蛇身的目的
                if (isEatFood(newHeadSnakeNode.getPosition_X(), newHeadSnakeNode.getPosition_Y(), food.getPosition_X(), food.getPosition_Y()))
                    FoodAlive--;//迟到了则食物存活符减一
                else
                    SnakeList.removeLast();
            }
            break;
            case RIGHT: {
                //创建新头节点
                SnakeNode newHeadSnakeNode = new SnakeNode();
                newHeadSnakeNode.setPosition_X(SnakeList.getFirst().getPosition_X() + 20);
                newHeadSnakeNode.setPosition_Y(SnakeList.getFirst().getPosition_Y());
                //将新节点加入链表
                SnakeList.addFirst(newHeadSnakeNode);
                //如果没吃到就去除尾节点达到增加蛇身的目的
                if (isEatFood(newHeadSnakeNode.getPosition_X(), newHeadSnakeNode.getPosition_Y(), food.getPosition_X(), food.getPosition_Y()))
                    FoodAlive--;//迟到了则食物存活符减一
                else
                    SnakeList.removeLast();
            }
            break;
        }
    }

    private static boolean isEatFood(int SnakeX, int SnakeY, int FoodX, int FoodY) {
        //蛇吃东西身体变长的处理方式为移动时不去除尾节点
        //坐标重合则判定为吃到食物
        return SnakeX == FoodX && SnakeY == FoodY;
    }

    private static void creatFood() {
        if (FoodAlive == 0) {//如果没有食物则创建
            food = new NormalFood();
            FoodAlive++;
        }
    }

    private void drawNode(Graphics g) {
        //画蛇
        for (Node node : SnakeList) {
            g.setColor(SnakeNode.snakeColor);
            g.fillRect(node.getPosition_X(), node.getPosition_Y(), SnakeSize, SnakeSize);
            //g.drawImage(snakeBody,snakeList.get(i).position_X,snakeList.get(i).position_Y,snakeSIZE,snakeSIZE,null);
        }
    }

    private void drawFood(Graphics g) {
        //画食物
        g.fillRect(food.getPosition_X(), food.getPosition_Y(), SnakeSize, SnakeSize);
        //g.drawImage(normalFood,food.position_X,food.position_Y,snakeSIZE,snakeSIZE,null);
    }

    //画图函数，这个是重写paint，Frame类的内置函数
    @Override
    public void paint(Graphics g) {
        //逐个读取链表中的节点数据，循环打印节点
        //清屏
        g.clearRect(0, 0, 400, 400);
        snakeMove();
        creatFood();
        drawNode(g);
        drawFood(g);
    }
}
