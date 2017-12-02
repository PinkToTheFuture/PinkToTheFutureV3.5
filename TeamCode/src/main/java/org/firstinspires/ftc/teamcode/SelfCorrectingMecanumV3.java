package org.firstinspires.ftc.teamcode;



import android.app.ActivityManager;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.MatrixConstants;
import com.qualcomm.robotcore.util.Range;
import com.sun.tools.javac.util.Convert;



@TeleOp(name="SelfCorrectingMecanumV3", group="PinktotheFuture")
public class SelfCorrectingMecanumV3 extends LinearOpMode {
    bno055driver imu2;
    BNO055IMU imu;


    @Override
    public void runOpMode() throws InterruptedException {
        double LFpower = 0;
        double LBpower = 0;
        double RFpower = 0;
        double RBpower = 0;
        double speed = 1;

        boolean correcting = false;


        imu2 = new bno055driver("IMU", hardwareMap);
        imu = hardwareMap.get(BNO055IMU.class, "IMU");

        DcMotor LFdrive = hardwareMap.dcMotor.get("LFdrive");
        DcMotor RBdrive = hardwareMap.dcMotor.get("RBdrive");
        DcMotor LBdrive = hardwareMap.dcMotor.get("LBdrive");
        DcMotor RFdrive = hardwareMap.dcMotor.get("RFdrive");

        //RFdrive.setDirection(DcMotorSimple.Direction.REVERSE);
        //RBdrive.setDirection(DcMotorSimple.Direction.REVERSE);
        LBdrive.setDirection(DcMotorSimple.Direction.REVERSE);
        LFdrive.setDirection(DcMotorSimple.Direction.REVERSE);

        double Xacc;
        Xacc = imu.getLinearAcceleration().xAccel;
        double Yacc;
        Yacc = imu.getLinearAcceleration().yAccel;

        Double[] imuArray;
        imuArray = new Double[1];
        imuArray[0] = 0.0;


        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpad_up)     speed = 1;
            if (gamepad1.dpad_right)  speed =.5;
            if (gamepad1.dpad_down)   speed = 0.3;

            double temp;

            double theta = imu2.getAngles()[0];

            double forward = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double rcw = gamepad1.right_stick_x;

            if (Math.abs(gamepad1.left_stick_x) > 0 || Math.abs(gamepad1.left_stick_y) > 0 || Math.abs(gamepad1.right_stick_x) > 0 || Math.abs(gamepad1.right_stick_y) > 0 ) {
                imuArray[0] = theta;
            }

            double oldAngle = imuArray[0]*180/Math.PI;
            double newAngle = theta*180/Math.PI;

            double rawDiff = oldAngle > newAngle ? oldAngle - newAngle : newAngle - oldAngle;


            if (theta < 0){
                rawDiff = rawDiff * -1;
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

            if (Math.abs(gamepad1.left_stick_x) == 0 && Math.abs(gamepad1.left_stick_y) == 0 && Math.abs(gamepad1.right_stick_x) ==  0 && Math.abs(gamepad1.right_stick_y) == 0){

                RFpower = ((Yacc + Xacc) / 2);
                RBpower = ((Yacc - Xacc) / 2);
                LFpower = ((Yacc - Xacc) / 2);
                LBpower = ((Yacc + Xacc) / 2);

                if (rawDiff > 5.0){
                    LFpower = 0.2;
                    LBpower = 0.2;
                    RFpower = -0.2;
                    RBpower = -0.2;
                }

                if (rawDiff < -5.0){
                    LFpower = -0.2;
                    LBpower = -0.2;
                    RFpower = 0.2;
                    RBpower = 0.2;
                }


                correcting = true;

            }else{

                correcting = false;
            }


            Range.clip(RFpower, -1, 1);
            Range.clip(RBpower, -1, 1);
            Range.clip(LFpower, -1, 1);
            Range.clip(LBpower, -1, 1);


            LFdrive.setPower(LFpower * speed);
            RBdrive.setPower(RBpower * speed);
            LBdrive.setPower(LBpower * speed);
            RFdrive.setPower(RFpower * speed);

            //telemetry.addData("imuArray: ", imuArray[0]);
            //telemetry.addData("imu: ", imu2.getAngles()[0]);
            /*telemetry.addData("LB",LBpower);
            telemetry.addData("LF",LFpower);
            telemetry.addData("RB",RBpower);
            telemetry.addData("RF",RFpower);
            */

            telemetry.addData("LFdrive", LFdrive.getCurrentPosition());
            telemetry.addData("RFdrive", RFdrive.getCurrentPosition());
            telemetry.addData("LBdrive", LBdrive.getCurrentPosition());
            telemetry.addData("RBdrive", RBdrive.getCurrentPosition());

            telemetry.addData("correcting is:", correcting);

            telemetry.update();


        }
    }



}
