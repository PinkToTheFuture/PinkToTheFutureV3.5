package org.firstinspires.ftc.robotinoneweek;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


class RobotComponents extends LinearOpMode{
    DcMotor Arm = hardwareMap.dcMotor.get("armmotor");
    DcMotor LFdrive = hardwareMap.dcMotor.get("LFdrive");
    DcMotor LBdrive = hardwareMap.dcMotor.get("LBdrive");
    DcMotor RFdrive = hardwareMap.dcMotor.get("RFdrive");
    DcMotor RBdrive = hardwareMap.dcMotor.get("RBdrive");
    Servo GlyphgrabL = hardwareMap.servo.get("glyphgrabl");
    Servo GlyphgrabR = hardwareMap.servo.get("glyphgrabr");
    Servo RelicSlideOpenerR = hardwareMap.servo.get("relicslideopenerr");
    Servo RelicSlideOpenerL = hardwareMap.servo.get("relicslideopenerl");
    Servo JewelservoExtend = hardwareMap.servo.get("Jewelservoextend");
    Servo JewelservoTurn = hardwareMap.servo.get("Jewelservoturn");
    ColorSensor colorsensorjewels = hardwareMap.colorSensor.get("colorsensorjewels");

    public void runOpMode(){

    }

}