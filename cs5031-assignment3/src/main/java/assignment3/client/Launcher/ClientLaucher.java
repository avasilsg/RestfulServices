package assignment3.client.Launcher;

import java.awt.EventQueue;

import assignment3.client.core.MainGUI;

public class ClientLaucher
{
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    MainGUI frame = new MainGUI();
                    frame.setVisible(true);
                    frame.setAlwaysOnTop(false);
                    frame.setLocation(500, 180);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
