package org.firstinspires.ftc.teamcode;



import android.app.ActivityManager;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.MatrixConstants;
import com.qualcomm.robotcore.util.Range;



@TeleOp(name="SelfCorrectingMecanum", group="PinktotheFuture")
public class SelfCorrectingMecanum extends LinearOpMode {
    bno055driver imu;



    @Override
    public void runOpMode() throws InterruptedException {
        double LFpower = 0;
        double LBpower = 0;
        double RFpower = 0;
        double RBpower = 0;
        double fastency = 1;

        imu = new bno055driver("IMU", hardwareMap);

        DcMotor LFdrive = hardwareMap.dcMotor.get("LFdrive");
        DcMotor RBdrive = hardwareMap.dcMotor.get("RBdrive");
        DcMotor LBdrive = hardwareMap.dcMotor.get("LBdrive");
        DcMotor RFdrive = hardwareMap.dcMotor.get("RFdrive");

        //RFdrive.setDirection(DcMotorSimple.Direction.REVERSE);
        //RBdrive.setDirection(DcMotorSimple.Direction.REVERSE);
        LBdrive.setDirection(DcMotorSimple.Direction.REVERSE);
        LFdrive.setDirection(DcMotorSimple.Direction.REVERSE);

        double[] imuArray = new double[1];


        waitForStart();


        while (opModeIsActive()) {
            if (gamepad1.dpad_up)     fastency = 1;
            if (gamepad1.dpad_right) fastency =.5;
            if (gamepad1.dpad_down)   fastency = 0.3;

            double temp;

            double max = Math.abs(LFpower);
            double theta = imu.getAngles()[0];

            double forward = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double rcw = gamepad1.right_stick_x;

            boolean done = true;



            if (gamepad1.left_stick_y == 0 && gamepad1.left_stick_x == 0 && gamepad1.right_stick_x == 0) {
                double delta;
                imuArray[0] = theta;
                delta = imu.getAngles()[0]- imuArray[0];

                int imuaccel = 0;



                RFpower = -delta; // + imuaccel * 0.5; voor die tijdens acceleratie tegenwerken
                RBpower = -delta;
                LFpower = +delta;
                LBpower = +delta;

                Range.clip(RFpower, -1, 1);
                Range.clip(RBpower, -1, 1);
                Range.clip(LFpower, -1, 1);
                Range.clip(LBpower, -1, 1);

                LFdrive.setPower(LFpower);
                RBdrive.setPower(RBpower);
                LBdrive.setPower(LBpower);
                RFdrive.setPower(RFpower);

                telemetry.addData("delta: ", delta);

            }

            if (theta >0) {
                temp = forward*Math.cos(theta)-strafe*Math.sin(theta);
                strafe = forward*Math.sin(theta)+strafe*Math.cos(theta);
                forward = temp;
            }

            if (theta <=0) {
                temp = forward*Math.cos(theta)-strafe*Math.sin(theta);
                strafe = forward*Math.sin(theta)+strafe*Math.cos(theta);
                forward = temp;
            }

            RFpower = 0;
            RBpower = 0;
            LFpower = 0;
            LBpower = 0;


            LFpower = forward+rcw+strafe;
            RFpower = forward-rcw-strafe;
            LBpower = forward+rcw-strafe;
            RBpower = forward-rcw+strafe;



            Range.clip(RFpower, -1, 1);
            Range.clip(RBpower, -1, 1);
            Range.clip(LFpower, -1, 1);
            Range.clip(LBpower, -1, 1);


            LFdrive.setPower(LFpower * fastency);
            RBdrive.setPower(RBpower * fastency);
            LBdrive.setPower(LBpower * fastency);
            RFdrive.setPower(RFpower * fastency);

            telemetry.addData("imuArray: ", imuArray[0]);
            telemetry.addData("imu: ", imu.getAngles()[0]);
            telemetry.addData("LB",LBpower);
            telemetry.addData("LF",LFpower);
            telemetry.addData("RB",RBpower);
            telemetry.addData("RF",RFpower);
            telemetry.update();


        }
    }



}
