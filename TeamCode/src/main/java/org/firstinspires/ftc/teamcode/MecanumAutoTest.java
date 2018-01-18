package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.core.HardwareMecanum;

/**
 * Created by HSstudent on 1/11/2018.
 */

@Autonomous(name="Mecanum Auto Test", group="Test")
public class MecanumAutoTest extends LinearOpMode {

    // Encoder variables
    static final double TURN_RADIUS = 6.25;
    static final double COUNTS_PER_MOTOR_REV = 280;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 1.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (5.56 * COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 1.0;
    static final double TURN_SPEED = 0.5;
    HardwareMecanum robot = new HardwareMecanum();
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        waitForStart();

        encoderDrive(48,90);

    }

    public void encoderDrive(double inches, double angle) {
        encoderDrive(inches, angle, DRIVE_SPEED, inches / DRIVE_SPEED);
    }

    public void encoderDrive(double inches, double angle, double speed) {
        encoderDrive(inches, angle, speed, inches / DRIVE_SPEED);
    }

    public void encoderDrive(double inches, double angle, double speed, double timeout) {
        int fl, fr, bl, br;
        if (opModeIsActive()) {

            angle += 45;
            fl = robot.frontLeft.getCurrentPosition() + (int) (COUNTS_PER_INCH * Math.cos(Math.toRadians(angle)) * inches);
            fr = robot.frontRight.getCurrentPosition() + (int) (COUNTS_PER_INCH * Math.sin(Math.toRadians(angle)) * inches);
            bl = robot.backLeft.getCurrentPosition() + (int) (COUNTS_PER_INCH * Math.sin(Math.toRadians(angle)) * inches);
            br = robot.backRight.getCurrentPosition() + (int) (COUNTS_PER_INCH * Math.cos(Math.toRadians(angle)) * inches);

            telemetry.addData("Path", "fl %d :: br %d :: fr %d :: bl %d", fl, br, fr, bl);
            telemetry.update();

            robot.frontLeft.setTargetPosition(fl);
            robot.frontRight.setTargetPosition(fr);
            robot.backLeft.setTargetPosition(bl);
            robot.backRight.setTargetPosition(br);

            robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            robot.frontLeft.setPower(speed * Math.abs(Math.cos(Math.toRadians(angle))));
            robot.frontRight.setPower(speed * Math.abs(Math.sin(Math.toRadians(angle))));
            robot.backLeft.setPower(speed * Math.abs(Math.sin(Math.toRadians(angle))));
            robot.backRight.setPower(speed * Math.abs(Math.cos(Math.toRadians(angle))));

            while ( robot.frontLeft.isBusy() && robot.frontRight.isBusy() &&
                    robot.backLeft.isBusy() && robot.backRight.isBusy() &&
                    runtime.seconds() < timeout && opModeIsActive()) {
                /* int frontEnc = Math.abs(robot.frontLeft.getCurrentPosition());
                int backEnc = Math.abs(robot.backLeft.getCurrentPosition());
                int compareEnc = Math.max(frontEnc, backEnc); */

                telemetry.addData("Path", "fl %d :: br %d :: fr %d :: bl %d", fl, br, fr, bl);
                telemetry.addData("Position", "fl %d :: br %d :: fr %d :: bl %d",
                        robot.frontLeft.getCurrentPosition(), robot.backRight.getCurrentPosition(),
                        robot.frontRight.getCurrentPosition(), robot.backLeft.getCurrentPosition());
                telemetry.addData("Timeout", "%.2f : %.2f", timeout, runtime.seconds());
                telemetry.update();
                /* if (compareEnc > Math.max(Math.abs(fl), Math.abs(bl))) {
                    break;
                } */
            }

            robot.frontLeft.setPower(0);
            robot.frontRight.setPower(0);
            robot.backLeft.setPower(0);
            robot.backRight.setPower(0);

            robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }


    }
}
