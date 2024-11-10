import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class AppPanel extends JPanel {
    BufferedImage bufferImage;
    Timer timer;
    
    int xAxis, yAxis; 
    int imgWidth = 50; 
    int imgHeight = 50; 

    int targetX, targetY; 
    int speed = 2; 
    
    int currentTarget = 0;

    AppPanel() {
        setSize(500, 500);
        showBg();
        initializeStartPosition();
        loopPaint();
    }

    void showBg() {
        try {
            bufferImage = ImageIO.read(AppPanel.class.getResource("bg.jpg")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    void initializeStartPosition() {
        xAxis = 0; 
        yAxis = (getHeight() - imgHeight) / 2; 
        setNextTarget(); 
    }

    
    void setNextTarget() {
        switch (currentTarget) {
            case 0: 
                targetX = (getWidth() - imgWidth) / 2; 
                targetY = getHeight() - imgHeight; 
                break;
            case 1: 
                targetX = getWidth() - imgWidth; 
                targetY = (getHeight() - imgHeight) / 2; 
                break;
            case 2: 
                targetX = (getWidth() - imgWidth) / 2; 
                targetY = 0; 
                break;
            case 3: 
                targetX = 0; 
                targetY = (getHeight() - imgHeight) / 2; 
                break;
        }
        
        currentTarget = (currentTarget + 1) % 4;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.clearRect(0, 0, getWidth(), getHeight());
        g.drawImage(bufferImage, xAxis, yAxis, imgWidth, imgHeight, null);
    }


    void moveTowardsTarget() {
        
        if (xAxis < targetX) {
            xAxis += speed;
            if (xAxis > targetX) xAxis = targetX; 
        } else if (xAxis > targetX) {
            xAxis -= speed;
            if (xAxis < targetX) xAxis = targetX; 
        }

    
        if (yAxis < targetY) {
            yAxis += speed;
            if (yAxis > targetY) yAxis = targetY; 
        } else if (yAxis > targetY) {
            yAxis -= speed;
            if (yAxis < targetY) yAxis = targetY; 
        }

    
        if (xAxis == targetX && yAxis == targetY) {
            setNextTarget();
        }
    }

    void loopPaint() {
        timer = new Timer(10, (e) -> {
            moveTowardsTarget(); 
            repaint(); 
        });
        timer.start();
    }
}
