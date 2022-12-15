package org.game.plane;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtil {
    public static BufferedImage rotateImage(BufferedImage bufferedimage,
                                             int degree) {
        int w = bufferedimage.getWidth();// 得到图片宽度。
        int h = bufferedimage.getHeight();// 得到图片高度。
        w = Math.max(w, h);
        h = w;//强制长宽一致

        int type = bufferedimage.getColorModel().getTransparency();// 得到图片透明度。
        BufferedImage img;// 空的图片。
        Graphics2D graphics2d;// 空的画笔。
        (graphics2d = (img = new BufferedImage(w, h, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        graphics2d.rotate(Math.toRadians(-degree), w / 2.0, h / 2.0);// 旋转，degree是整型，角度数，比如垂直90度。180°角度 = Π弧度
        graphics2d.drawImage(bufferedimage, 0, 0, null);// 从bufferedimagecopy图片至img，0,0是img的坐标。
        graphics2d.dispose();
        return img;// 返回复制好的图片，原图片依然没有变，没有旋转，下次还可以使用。
    }
}
