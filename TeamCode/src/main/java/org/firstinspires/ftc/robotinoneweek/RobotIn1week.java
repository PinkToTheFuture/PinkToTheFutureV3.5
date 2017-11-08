package org.firstinspires.ftc.robotinoneweek;


import com.qualcomm.ftccommon.Restarter;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="RobotInOneWeek", group="FTC")
public class RobotIn1week extends LinearOpMode implements ServoVariables{

    @Override
    public void runOpMode() throws InterruptedException  {
        double ArmPos = 0;
        double Rpower;
        double Lpower;
        double GlyphgrabLPos = 0;
        double GlyphgrabRPos = 1;

        double fastency = 1;

        DcMotor Arm = hardwareMap.dcMotor.get("armmotor");
        DcMotor Ldrive = hardwareMap.dcMotor.get("Ldrive");
        DcMotor Rdrive = hardwareMap.dcMotor.get("Rdrive");
        Servo GlyphgrabL = hardwareMap.servo.get("glyphgrabl");
        Servo GlyphgrabR = hardwareMap.servo.get("glyphgrabr");
        Servo RelicSlideOpenerR = hardwareMap.servo.get("relicslideopenerr");
        Servo RelicSlideOpenerL = hardwareMap.servo.get("relicslideopenerl");
        Servo JewelservoExtend = hardwareMap.servo.get("jewelservoextend");
        Servo JewelservoTurn = hardwareMap.servo.get("jewelservoturn");
        Ldrive.setDirection(DcMotorSimple.Direction.REVERSE);
        RelicSlideOpenerR.setPosition(RelicSlideRServoMIN);
        RelicSlideOpenerL.setPosition(RelicSlideLServoMAX);
        


        waitOneFullHardwareCycle();
        waitForStart();
        while (opModeIsActive()) {
            waitOneFullHardwareCycle();
            if (gamepad1.dpad_up)     fastency = 1;
            if (gamepad1.dpad_down)   fastency = 0.2;
            if (gamepad1.dpad_right)  fastency = 0.4;

            if (gamepad2.a) {
                GlyphgrabLPos += 0.01;
                GlyphgrabRPos -= 0.01;
            }
            if (gamepad2.b) {
                GlyphgrabLPos -= 0.01;
                GlyphgrabRPos += 0.01;
            }

            if (gamepad2.x){
                RelicSlideOpenerR.setPosition(RelicSlideRServoMAX);
                RelicSlideOpenerL.setPosition(RelicSlideLServoMIN);
            }
            if (gamepad2.y){
                RelicSlideOpenerR.setPosition(RelicSlideRServoMIN);
                RelicSlideOpenerL.setPosition(RelicSlideLServoMAX);
            }

            Lpower = gamepad1.left_stick_y;
            Rpower = gamepad1.right_stick_y;

            Ldrive.setPower(Lpower*fastency*0.5);
            Rdrive.setPower(Rpower*fastency);
            Arm.setPower(gamepad2.left_stick_y);


            GlyphgrabLPos = Range.clip(GlyphgrabLPos,0.4,1);
            GlyphgrabRPos = Range.clip(GlyphgrabRPos,0,0.6);
            GlyphgrabL.setPosition(GlyphgrabLPos);
            GlyphgrabR.setPosition(GlyphgrabRPos);

        }
    }
}
