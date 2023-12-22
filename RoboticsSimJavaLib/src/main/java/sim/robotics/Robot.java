package sim.robotics;

import sim.robotics.connection.ConnectionHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Robot {
    private float speedX = 0;
    private float speedY = 0;
    private float speedZ = 0;

    private float positionX = 0;
    private float positionY = 0;
    private float positionZ = 0;

    private float ultrasound1 = 0;
    private float ultrasound2 = 0;

    private float infrared1 = 0;
    private float infrared2 = 0;

    private boolean buttonStart = false;
    private boolean buttonReset = false;
    private boolean buttonStop = false;

    private ConnectionHelper connHelper = null;

    public Robot() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                this.stop();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }));

        connHelper = new ConnectionHelper();
        connHelper.startChannels();
    }

    public void stop() throws InterruptedException {
        connHelper.stopChannels();
    }

    public void setAxesSpeed(float x, float y, float z) {
        this.speedX = x;
        this.speedY = y;
        this.speedZ = z;
        updateSetData();
    }

    public float getAxisX(){
        updateGetData();
        return positionX;
    }

    public float getAxisY(){
        updateGetData();
        return positionY;
    }

    public float getAxisZ(){
        updateGetData();
        return positionZ;
    }

    public float getUltrasound1(){
        updateGetData();
        return ultrasound1;
    }

    public float getUltrasound2(){
        updateGetData();
        return ultrasound2;
    }

    public float getInfrared1(){
        updateGetData();
        return infrared1;
    }

    public float getInfrared2(){
        updateGetData();
        return infrared2;
    }

    public boolean getStart(){
        updateGetData();
        return buttonStart;
    }

    public boolean getReset(){
        updateGetData();
        return buttonReset;
    }

    public boolean getStop(){
        updateGetData();
        return buttonStop;
    }

    private void updateSetData(){
        List<Float> lst = new ArrayList<Float>(Arrays.asList(this.speedX, this.speedY, this.speedZ));
        this.connHelper.setData(lst);
    }

    private void updateGetData(){
        List<Float> values = this.connHelper.getData();
        if (values.size() == ConnectionHelper.MAX_DATA_RECEIVE){
            this.positionX = values.get(0);
            this.positionY = values.get(1);
            this.positionZ = values.get(2);
            this.ultrasound1 = values.get(3);
            this.ultrasound2 = values.get(4);
            this.infrared1 = values.get(5);
            this.infrared2 = values.get(6);
            this.buttonStart = values.get(7) == 1;
            this.buttonReset = values.get(8) == 1;
            this.buttonStop = values.get(9) == 1;
        }
    }
}
