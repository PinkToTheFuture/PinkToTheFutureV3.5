package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="KickOffRobot", group="PinktotheFuture")
public class KickOffRobot extends LinearOpMode implements org.firstinspires.ftc.teamcode.ServoVariables {

    @Override
    public void runOpMode() throws InterruptedException {
        double LFpower = 0;
        double LBpower = 0;
        double RFpower = 0;
        double RBpower = 0;

        double geleiderPw = 0;

        double fastency = 1;

        double GlyphgrabLPos = 0.5;
        double GlyphgrabRPos = 0.5;

        Servo RelicSlideOpenerR = hardwareMap.servo.get("relicslideopenerr");
        Servo RelicSlideOpenerL = hardwareMap.servo.get("relicslideopenerl");

        Servo GlyphgrabL = hardwareMap.servo.get("glyphgrabl");
        Servo GlyphgrabR = hardwareMap.servo.get("glyphgrabr");

        RelicSlideOpenerR.setPosition(RelicSlideRServoMIN);
        RelicSlideOpenerL.setPosition(RelicSlideLServoMAX);

        DcMotor Geleider = hardwareMap.dcMotor.get("geleider");
        Geleider.setDirection(DcMotorSimple.Direction.REVERSE);


        DcMotor LFdrive = hardwareMap.dcMotor.get("LFdrive");
        DcMotor RBdrive = hardwareMap.dcMotor.get("RBdrive");
        DcMotor LBdrive = hardwareMap.dcMotor.get("LBdrive");
        DcMotor RFdrive = hardwareMap.dcMotor.get("RFdrive");

        //RFdrive.setDirection(DcMotorSimple.Direction.REVERSE);
        //RBdrive.setDirection(DcMotorSimple.Direction.REVERSE);
        LBdrive.setDirection(DcMotorSimple.Direction.REVERSE);
        LFdrive.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();


        while (opModeIsActive()) {
            if (gamepad1.dpad_up)     fastency = 1;
            if (gamepad1.dpad_down)   fastency = 0.3;

            RFpower = 0;
            RBpower = 0;
            LFpower = 0;
            LBpower = 0;

            geleiderPw = 0;


            RFpower = -((gamepad1.left_stick_y + gamepad1.left_stick_x) / 2);
            RBpower = -((gamepad1.left_stick_y - gamepad1.left_stick_x) / 2);
            LFpower = -((gamepad1.left_stick_y - gamepad1.left_stick_x) / 2);
            LBpower = -((gamepad1.left_stick_y + gamepad1.left_stick_x) / 2);

            //RIGHT STICK
            RFpower = RFpower - (gamepad1.right_stick_x);
            RBpower = RBpower - (gamepad1.right_stick_x);
            LFpower = LFpower + (gamepad1.right_stick_x);
            LBpower = LBpower + (gamepad1.right_stick_x);


            if (gamepad1.left_stick_x > -0.1 && gamepad1.left_stick_x < 0.1) {
                RFpower = -gamepad1.left_stick_y;
                RBpower = -gamepad1.left_stick_y;
                LFpower = -gamepad1.left_stick_y;
                LBpower = -gamepad1.left_stick_y;
            }
            if (gamepad1.left_stick_y > -0.1 && gamepad1.left_stick_y < 0.1) {
                RFpower = -gamepad1.left_stick_x;
                RBpower = gamepad1.left_stick_x;
                LFpower = gamepad1.left_stick_x;
                LBpower = -gamepad1.left_stick_x;
            }


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




            Range.clip(RFpower, -1, 1);
            Range.clip(RBpower, -1, 1);
            Range.clip(LFpower, -1, 1);
            Range.clip(LBpower, -1, 1);


            GlyphgrabLPos = Range.clip(GlyphgrabLPos,0.4,1);
            GlyphgrabRPos = Range.clip(GlyphgrabRPos,0,0.6);
            GlyphgrabL.setPosition(GlyphgrabLPos);
            GlyphgrabR.setPosition(GlyphgrabRPos);

            LFdrive.setPower(LFpower * fastency);
            RBdrive.setPower(RBpower * fastency);
            LBdrive.setPower(LBpower * fastency);
            RFdrive.setPower(RFpower * fastency);

            Geleider.setPower(gamepad2.right_stick_y);

            telemetry.addData("LB",LBpower);
            telemetry.addData("LF",LFpower);
            telemetry.addData("RB",RBpower);
            telemetry.addData("RF",RFpower);
            telemetry.update();


        }
    }
}
