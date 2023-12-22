package sim.robotics.connection;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class ListenPort
{
    private final int port;

    private boolean stopThread = false;
    public String outString = "";

    private Socket sct;
    private Thread thread;

    public ListenPort(int port)
    {
        this.port = port;
    }

    public void startListening()
    {
        this.thread = new Thread(this::listening);
        this.thread.start();
    }

    private void listening()
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
                out.write("Wait for data".getBytes(StandardCharsets.UTF_16LE));
                byte[] dataSize = new byte[4];
                in.readFully(dataSize, 0, 4);

                int length = (dataSize[3] & 0xff) << 24 | (dataSize[2] & 0xff) << 16 |
                        (dataSize[1] & 0xff) << 8 | (dataSize[0] & 0xff);
                if(length > 0)
                {
                    byte[] message = new byte[length];
                    in.readFully(message, 0, length);
                    this.outString = new String(message, StandardCharsets.UTF_16LE);
                }
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

    public void stopListening()
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
