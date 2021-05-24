package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class risunok extends JComponent {

    public static final int FIELD_EMPTY = 0;//пустое поле
    public static final int FIELD_X = 10;//поле с крестиком
    public static final int FIELD_O = 200;//поле с ноликом
    int [][] field;// объявляем массив игрового поля
    boolean isXturn;

    public risunok(){
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        field = new int [3][3];// выделяем память под массив
        initGame();
    }

    public void initGame(){
        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 3; ++j){
                field[i][j] = FIELD_EMPTY;//очищаем массив, заполняя его 0
            }
        }
        isXturn = true;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        //очищаем холст
        graphics.clearRect(0,0,getWidth(),getHeight());
        //рисуем сетку из линий
        drawGrid(graphics);
        //рисуеем текущие крестики и нолики
        drawXO(graphics);
    }

    void drawGrid(Graphics graphics) {
        int w = getWidth();//ширина игрового поля
        int h = getHeight();//высота игрового поля
        int dw = w / 3;// делим ширину на 3 - получаем ширину одной ячейки
        int dh = h / 3;
        graphics.setColor(Color.BLACK);//цвет линий
        for (int i = 1; i < 3; i++) {
            graphics.drawLine(0,  dh*i, w, dh*i);//горизонтальная линия
            graphics.drawLine(dw*i, 0, dw*i, h);//вертикальная
        }
    }
    @Override
    protected void processMouseEvent(MouseEvent mouseEvent){
        super.processMouseEvent(mouseEvent);
        if (mouseEvent.getButton() == MouseEvent.BUTTON1){//проверяем что нажата левая клавиша
            int x = mouseEvent.getX();//координата х клика
            int y = mouseEvent.getY();//координата у клика
            //переведем координаты в индексы ячейки в массиве field
            int i = (int) ((float) x / getWidth() * 3);
            int j = (int) ((float) y / getHeight() * 3);
            //проверяем что выбранная ячейка пуста и туда можно сходить
            if (field[i][j] == FIELD_EMPTY){
               // проверка чей ход, если Х-ставим крест, если О-ставим ноль
                field[i][j] = isXturn ? FIELD_X : FIELD_O;
                isXturn = !isXturn;
                repaint();//перерисовка компонента, это вызовет метод paintComponent()
                int res = checkState();
                if (res!=0){
                    if (res == FIELD_O * 3){
                        //победил 0
                        JOptionPane.showMessageDialog(this, "Нолики выйграли!", "Победа!",JOptionPane.INFORMATION_MESSAGE);
                    }else if (res == FIELD_X * 3){
                        //победил Х
                        JOptionPane.showMessageDialog(this, "Крестики выйграли!", "Победа!", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(this, "Ничья!", "Ничья!", JOptionPane.INFORMATION_MESSAGE);
                    }
                    //перезапуск игры
                    initGame();
                    //перерисовка поля
                    repaint();
                }
            }
        }
    }

    void drawO(int i, int j, Graphics graphics){
        graphics.setColor(Color.RED);
        int dw = getWidth() / 3;
        int dh = getHeight() / 3;
        int x = i * dw;
        int y = j * dh;
        graphics.drawOval(x + 5 * dw / 100, y, dw * 9 / 10, dh);
    }

    void drawX(int i, int j, Graphics graphics){
        graphics.setColor(Color.BLUE);
        int dw = getWidth() / 3;
        int dh = getHeight() / 3;
        int x = i * dw;
        int y = j * dh;
        //линия от верхнего левого угла в правый нижний
        graphics.drawLine(x, y, x+dw, y+dh);
        //линия от левого нижнего угла до правого верхнего
        graphics.drawLine(x, y+dh, x+dw, y);
    }

    void drawXO(Graphics graphics){
        for (int i = 0; i < 3; ++i){
            for(int j = 0; j < 3; ++j){
                //если в данной ячейке крестик - рисуем его
                if (field [i][j] == FIELD_X){
                    drawX(i, j, graphics);
                    //то же для нолика
                } else if (field[i][j] == FIELD_O){
                    drawO(i, j, graphics);
                }
            }
        }
    }

    int checkState() {
        //проверяем диагонали
        int diag = 0;
        int diag2 = 0;
        for (int i = 0; i < 3; i++) {
            diag += field[i][i];
            diag2 += field[i][2 - i];
        }
        if (diag == FIELD_O * 3 || diag == FIELD_X * 3) {
            return diag;
        }
        if (diag2 == FIELD_O * 3 || diag2 == FIELD_X * 3) {
            return diag2;
        }
        boolean hasEmpty = false;
        int checkHorizontal, checkVertical;

        for(int p = 0; p < 3; p++) { // проверка выигрыша по горизонтали и вертикали
            checkHorizontal = checkVertical = 0;

            for(int q = 0; q < 3; q++) {
                checkHorizontal += field[p][q];
                checkVertical += field[q][p];

                if (field[p][q] == 0) hasEmpty = true;
            }

            if (checkHorizontal == FIELD_O * 3 || checkHorizontal == FIELD_X * 3) return checkHorizontal;
            if (checkVertical == FIELD_O * 3 || checkVertical == FIELD_X * 3) return checkVertical;
        }
            if (hasEmpty) return 0;
            else return -1;
        }
}
