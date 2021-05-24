package com.company;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Запуск игры.");
        JFrame window = new JFrame("Крестики-Нолики");//главное окно
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//кнопка закрытия
        window.setSize(600, 600);//размер окна
        window.setLayout(new BorderLayout());//менеджер компонентов
        window.setLocationRelativeTo(null);//открытие окна в центре экрана
        window.setVisible(true);//включаем видимость окна
        risunok game = new risunok();//создаем объект нашего класса
        window.add(game);// добавляем его в окно
        System.out.println("Конец игры.");
    }
}
