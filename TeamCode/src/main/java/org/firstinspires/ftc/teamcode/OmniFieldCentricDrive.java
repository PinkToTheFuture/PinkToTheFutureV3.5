package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.MatrixConstants;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="OmniFieldCentricDrive", group="PinktotheFuture")
public class OmniFieldCentricDrive extends LinearOpMode {

    bno055driver imu;

    @Override
    public void runOpMode() throws InterruptedException {
        double LFpower = 0;
        double LBpower = 0;
        double RFpower = 0;
        double RBpower = 0;
        double fastency = 1;

        double fwd = gamepad1.left_stick_y;
        double stf = gamepad1.left_stick_x;

        double gyroyaw = imu.getAngles()[0];
        double temp = (fwd * Math.cos(gyroyaw) + stf * Math.sin(gyroyaw));

        double strf = (-fwd * Math.sin(gyroyaw) + stf * Math.cos(gyroyaw));
        double forward = temp;
        double strafe = strf;

        DcMotor LFdrive = hardwareMap.dcMotor.get("LFdrive");
        DcMotor RBdrive = hardwareMap.dcMotor.get("RBdrive");
        DcMotor LBdrive = hardwareMap.dcMotor.get("LBdrive");
        DcMotor RFdrive = hardwareMap.dcMotor.get("RFdrive");

        //RFdrive.setDirection(DcMotorSimple.Direction.REVERSE);
        //RBdrive.setDirection(DcMotorSimple.Direction.REVERSE);
        LBdrive.setDirection(DcMotorSimple.Direction.REVERSE);
        LFdrive.setDirection(DcMotorSimple.Direction.REVERSE);

        imu = new bno055driver("IMU", hardwareMap);

        waitForStart();


        while (opModeIsActive()) {
            if (gamepad1.dpad_up)     fastency = 1;
            if (gamepad1.dpad_down)   fastency = 0.3;

            RFpower = 0;
            RBpower = 0;
            LFpower = 0;
            LBpower = 0;


            RFpower = -((forward + strafe) / 2);
            RBpower = -((forward - strafe) / 2);
            LFpower = -((forward - strafe) / 2);
            LBpower = -((forward + strafe) / 2);

            //RIGHT STICK
            RFpower = RFpower - (strafe);
            RBpower = RBpower - (strafe);
            LFpower = LFpower + (strafe);
            LBpower = LBpower + (strafe);


            if (strafe > -0.1 && strafe < 0.1) {
                RFpower = -forward;
                RBpower = -forward;
                LFpower = -forward;
                LBpower = -forward;
            }
            if (forward > -0.1 && forward < 0.1) {
                RFpower = -strafe;
                RBpower = strafe;
                LFpower = strafe;
                LBpower = -strafe;
            }

            Range.clip(RFpower, -1, 1);
            Range.clip(RBpower, -1, 1);
            Range.clip(LFpower, -1, 1);
            Range.clip(LBpower, -1, 1);



            LFdrive.setPower(LFpower * fastency);
            RBdrive.setPower(RBpower * fastency);
            LBdrive.setPower(LBpower * fastency);
            RFdrive.setPower(RFpower * fastency);



            telemetry.addData("LB",LBpower);
            telemetry.addData("LF",LFpower);
            telemetry.addData("RB",RBpower);
            telemetry.addData("RF",RFpower);
            telemetry.update();


        }
    }
}
