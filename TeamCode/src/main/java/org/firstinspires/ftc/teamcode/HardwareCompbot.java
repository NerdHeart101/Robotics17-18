package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by hsstudent on 9/28/2017.
 */

public class HardwareCompbot {

    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;
    public DcMotor glyphLift = null;
    public Servo leftClaw = null;
    public Servo rightClaw = null;
    public Servo jewelArm = null;
    public Servo jewelShoulder = null;
    public ColorSensor colorSensor = null;

    HardwareMap hwMap = null;

    public void init(HardwareMap ahwMap) {

        hwMap = ahwMap;

        // Find and configure MOTORS
        leftDrive = hwMap.get(DcMotor.class, "left_drive");
        rightDrive = hwMap.get(DcMotor.class, "right_drive");
        glyphLift = hwMap.get(DcMotor.class, "glyph_lift");

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        glyphLift.setDirection(DcMotor.Direction.FORWARD);

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        glyphLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftDrive.setPower(0);
        rightDrive.setPower(0);
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

        // Find an configure SENSORS
        colorSensor = hwMap.get(ColorSensor.class, "sensor_color");

    }

}
