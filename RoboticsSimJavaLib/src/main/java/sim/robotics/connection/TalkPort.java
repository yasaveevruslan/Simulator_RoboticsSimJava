package sim.robotics.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class TalkPort
{
    private final int port;

    private boolean stopThread = false;
    public String outString = "";

    private Socket sct;
    private Thread thread;

    public TalkPort(int port)
    {
        this.port = port;
    }

    public void startTalking()
    {
        this.thread = new Thread(this::talking);
        this.thread.start();
    }

    private void talking()
    {
        try
        {
            this.sct = new Socket("localhost", this.port);
        }
        catch (IOException e)
        {
            // there could be a error
        }

        this.stopThread = false;

        try
        {
            DataInputStream in = new DataInputStream(this.sct.getInputStream());
            DataOutputStream out = new DataOutputStream(this.sct.getOutputStream());

            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                // there could be a error
            }

            while (!this.stopThread)
            {
                out.write((this.outString + "$").getBytes(StandardCharsets.UTF_16LE));
                byte[] message = new byte[4];
                in.readFully(message, 0, message.length);

                Thread.sleep(4);
            }

            this.sct.shutdownInput();
            this.sct.shutdownOutput();
            this.sct.close();
        }
        catch (IOException | InterruptedException e)
        {
            // there could be a error
        }
    }

    private void resetOut()
    {
        this.outString = "";
    }

    public void stopTalking()
    {
        this.stopThread = true;
        this.resetOut();
        if (this.sct != null)
        {
            try
            {
                this.sct.shutdownInput();
                this.sct.shutdownOutput();
            }
            catch (IOException e)
            {
                // there could be a error
            }

            if (this.thread != null)
            {
                int stTime = LocalDateTime.now().toLocalTime().toSecondOfDay();
                while (this.thread.isAlive())
                {
                    if (LocalDateTime.now().toLocalTime().toSecondOfDay() - stTime > 1)
                    {
                        try
                        {
                            this.sct.close();
                        }
                        catch (IOException e)
                        {
                            // there could be a error
                        }

                    }
                }
            }
        }
    }
}