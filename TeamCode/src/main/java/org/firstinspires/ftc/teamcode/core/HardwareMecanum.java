package org.firstinspires.ftc.teamcode.core;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by hsstudent on 9/28/2017.
 */

public class HardwareMecanum {

    public DcMotor frontLeft = null;
    public DcMotor frontRight = null;
    public DcMotor backLeft = null;
    public DcMotor backRight = null;

    HardwareMap hwMap = null;

    public void init(HardwareMap ahwMap) {

        hwMap = ahwMap;

        frontRight      = hwMap.dcMotor.get("front_right_drive");
        backRight       = hwMap.dcMotor.get("back_right_drive");
        frontLeft       = hwMap.dcMotor.get("front_left_drive");
        backLeft        = hwMap.dcMotor.get("back_left_drive");

        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);

        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

}
