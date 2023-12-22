package com.roboticssimjava;

public class FunctionalRobot {

    public MainViewController m = new MainViewController();

    public float speedX, speedY, speedZ = 0;
    public float setPositionX = 290, setPositionY = 290, setPositionZ = 0;
    public float setLastPositionX, setLastPositionY, setLastPositionZ = 0;

    public float getPositionX, getPositionY, getPositionZ = 0;

    public float getUp, getDown, getLeft, getRight;

    public void setRobotPosition()
    {
        m.RobotImage.setX(setPositionX);
        m.RobotImage.setY(setPositionY);
        m.RobotImage.setRotate(setPositionZ);
    }

    public void calculateCoordinates()
    {
        float disX = setPositionX - setLastPositionX;
        float disY = setPositionY - setLastPositionY;
        float disZ = setPositionZ - setLastPositionZ;

        getPositionX += disX;
        getPositionY += disY;
        getPositionZ += disZ;

        setLastPositionX = setPositionX;
        setLastPositionY = setPositionY;
        setLastPositionZ = setPositionZ;
    }

    public void calculateSides()
    {
        getUp = 0;
        getDown = 0;
        getLeft = 0;
        getRight = 0;
    }

    public void setAxisSpeed(float x, float y, float z)
    {
        speedX = x / 16;
        speedY = y / 16;
        speedZ = z / 16;
    }


    public static int frontierLeft = 45, frontierRight = 1043, frontierUp = 70, frontierDown = 570;

    /*
    выделение контуров
    создание барьера поля
    */
    public void changePosition()
    {

        setPositionZ += speedZ;

        setPositionX = setPosition(setPositionX, frontierLeft, frontierRight - 105, speedX);
        setPositionY = setPosition(setPositionY, frontierUp, frontierDown - 105, speedY);

//        positionRobotX = setPosition(positionRobotX, limitLeft, limitRight - frontierX, speedX);
//        positionRobotY = setPosition(positionRobotY, limitUp, limitDown - frontierY, speedY);
    }

    /*
    проверка на вмещение робота в контуры (рамки поля)
    */
    public static float setPosition(float out, float min, float max, float speed)
    {
        boolean left = out <= min && speed < 0;
        boolean right = out >= max && speed > 0;

        out = left ? min : right ? max : out + speed;
        return out;
    }


    public void update()
    {
        setRobotPosition();
        calculateCoordinates();
        calculateSides();
//        setAxisSpeed(0, 0, 0);
        changePosition();
    }
}
