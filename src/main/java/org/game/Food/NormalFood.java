package org.game.Food;

import lombok.Data;

import java.awt.*;

@Data
public class NormalFood extends Food {
    public NormalFood() {
        position_X = ((int) (Math.random() * 20 + 1)) * 20;
        position_Y = ((int) (Math.random() * 20 + 1)) * 20;
    }

    int awardGrades = 1;//吃到后的奖励分数
    public static Color FoodColor = Color.GREEN;
}
