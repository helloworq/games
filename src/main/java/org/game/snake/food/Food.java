package org.game.snake.food;

import lombok.Data;

@Data
public class Food {
    int position_X;
    int position_Y;
    int awardGrades = 1;//吃到后的奖励分数
}
