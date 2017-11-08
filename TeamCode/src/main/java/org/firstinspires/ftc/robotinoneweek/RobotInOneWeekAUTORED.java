package org.firstinspires.ftc.robotinoneweek;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name="RobotInOneWeekAUTORED", group="FTC")
public class RobotInOneWeekAUTORED extends LinearOpMode implements ServoVariables{
    @Override
    public void runOpMode() throws InterruptedException  {
        AutonomousVoids autonomousvoids = new AutonomousVoids();

        waitOneFullHardwareCycle();
        waitForStart();

        //autonomousvoids.Forward(1,1);
        autonomousvoids.Jewels("red");


    }
}
