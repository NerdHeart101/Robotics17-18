package org.firstinspires.ftc.teamcode.core;

import android.graphics.Color;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

/**
 * Created by HSstudent on 2/27/2018.
 */

public class HardwareKiwi {

    // Define all hardware objects
    public DcMotor motorOne = null;
    public DcMotor motorTwo = null;
    public DcMotor motorThree = null;
    public DcMotor kicker = null;

    public OpticalDistanceSensor distanceSensor = null;
    public ModernRoboticsI2cGyro gyroSensor = null;

    HardwareMap hwMap = null;

    public void init(HardwareMap ahwMap) {

        hwMap = ahwMap;

        // Get all devices
        motorOne    = hwMap.dcMotor.get("motor_one");
        motorTwo    = hwMap.dcMotor.get("motor_two");
        motorThree  = hwMap.dcMotor.get("motor_three");
        kicker      = hwMap.dcMotor.get("kicker");
        distanceSensor = hwMap.get(OpticalDistanceSensor.class, "distance_sensor");
        gyroSensor  = hwMap.get(ModernRoboticsI2cGyro.class, "gyro_sensor");

        // Motor configurations
        motorOne.setDirection(DcMotor.Direction.FORWARD);
        motorTwo.setDirection(DcMotor.Direction.FORWARD);
        motorThree.setDirection(DcMotor.Direction.FORWARD);
        kicker.setDirection(DcMotor.Direction.REVERSE);

        motorOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorTwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorThree.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        kicker.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorOne.setPower(0);
        motorTwo.setPower(0);
        motorThree.setPower(0);
        kicker.setPower(0);

    }
}