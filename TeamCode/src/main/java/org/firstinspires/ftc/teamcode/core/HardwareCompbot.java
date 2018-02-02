package org.firstinspires.ftc.teamcode.core;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * This class is used to define our hardware object definitions and registers them into our robot configuration profiles
 * and names them accordingly. It also sets default values we want for motors, servos, sensors, etc.
 */

public class HardwareCompbot {

    // Define all the hardware objects we need
    public DcMotor frontRight = null;
    public DcMotor backRight = null;
    public DcMotor frontLeft = null;
    public DcMotor backLeft = null;
    public DcMotor glyphLift = null;
    public Servo leftClaw = null;
    public Servo rightClaw = null;
    public Servo jewelArm = null;
    public Servo jewelShoulder = null;
    public ColorSensor colorSensor = null;
    public ModernRoboticsI2cGyro gyroSensor = null;

    // TODO: Configure gyro sensor

    //Create the hardware map object for our robot
    HardwareMap hwMap = null;

    //Initialize the hardware for out bot
    public void init(HardwareMap ahwMap) {

        hwMap = ahwMap;

        // Find and configure MOTORS
        frontRight      = hwMap.dcMotor.get("front_right_drive");
        backRight       = hwMap.dcMotor.get("back_right_drive");
        frontLeft       = hwMap.dcMotor.get("front_left_drive");
        backLeft        = hwMap.dcMotor.get("back_left_drive");
        glyphLift       = hwMap.dcMotor.get("glyph_lift");

        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        glyphLift.setDirection(DcMotor.Direction.FORWARD);

        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        glyphLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
        glyphLift.setPower(0);

        // Find and configure SERVOS
        leftClaw = hwMap.get(Servo.class, "left_claw");
        rightClaw = hwMap.get(Servo.class, "right_claw");
        jewelArm = hwMap.get(Servo.class, "jewel_arm");
        jewelShoulder = hwMap.get(Servo.class, "jewel_shoulder");

        leftClaw.setPosition(0);
        rightClaw.setPosition(1);
        jewelArm.setPosition(1);
        jewelShoulder.setPosition(0.5);

        // Find an configure SENSOR
        colorSensor = hwMap.get(ColorSensor.class, "sensor_color");
        gyroSensor = hwMap.get(ModernRoboticsI2cGyro.class, "gyro_sensor");

    }

}
