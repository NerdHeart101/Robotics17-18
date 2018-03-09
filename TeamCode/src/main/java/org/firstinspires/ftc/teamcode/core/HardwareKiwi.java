package org.firstinspires.ftc.teamcode.core;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by HSstudent on 2/27/2018.
 */

public class HardwareKiwi {

    // Define all hardware objects
    public DcMotor motorOne = null;
    public DcMotor motorTwo = null;
    public DcMotor motorThree = null;

    public ColorSensor colorSensor = null;

    HardwareMap hwMap = null;

    public void init(HardwareMap ahwMap) {

        hwMap = ahwMap;

        // Get all devices
        motorOne    = hwMap.dcMotor.get("motor_one");
        motorTwo    = hwMap.dcMotor.get("motor_two");
        motorThree  = hwMap.dcMotor.get("motor_three");
        colorSensor = hwMap.colorSensor.get("color_sensor");

        // Motor configurations
        motorOne.setDirection(DcMotor.Direction.FORWARD);
        motorTwo.setDirection(DcMotor.Direction.FORWARD);
        motorThree.setDirection(DcMotor.Direction.FORWARD);

        motorOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorTwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorThree.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorOne.setPower(0);
        motorTwo.setPower(0);
        motorThree.setPower(0);
    }
}