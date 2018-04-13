package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.core.HardwareKiwi;

/**
 * Created by HSstudent on 3/8/2018.
 */
@Autonomous(name="Kiwi: Autonomous", group="Kiwi")
public class KiwiAutonomous extends LinearOpMode{

    HardwareKiwi robot = new HardwareKiwi();
    ElapsedTime time = new ElapsedTime();

    static final double     COUNTS_PER_INCH = 1556 / 4 / 3.1416;
    static final double     TURN_SPEED              = 0.25;
    static final double     FINE_TURN               = 0.1 ;

    public void runOpMode() {

        robot.init(hardwareMap);

        waitForStart();

        encoderDrive(20, .5);

    }

    public void encoderDrive(double inches, double speed) {

        // double rotate;
        int m1, m3;

        // Encoder targets
        m1 = robot.motorOne.getCurrentPosition() + (int) (COUNTS_PER_INCH * inches * 1.1547);
        m3 = robot.motorThree.getCurrentPosition() + (int) (COUNTS_PER_INCH * inches * 1.1547);

        robot.motorOne.setTargetPosition(m1);
        robot.motorThree.setTargetPosition(m3);

        robot.motorOne.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.motorThree.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.motorOne.setPower(speed * .866);
        robot.motorThree.setPower(speed * .866);

        while(robot.motorOne.isBusy() && robot.motorThree.isBusy() && opModeIsActive()) {
            telemetry.addData("Path", "m1 %d :: m3 %d", m1, m3);
            telemetry.addData("Position", "m1 %d :: m3 %d",
                    robot.motorOne.getCurrentPosition(), robot.motorThree.getCurrentPosition());
            telemetry.update();
        }

        robot.motorOne.setPower(0.0);
        robot.motorThree.setPower(0.0);

        robot.motorOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorThree.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void gyroRotate(int degrees) {

        int targetHeading = (robot.gyroSensor.getHeading() + degrees + 360) % 360;

        double speed = TURN_SPEED;

        if (degrees > 0) {
            speed *= -1;
        }

        robot.motorOne.setPower(speed);
        robot.motorTwo.setPower(speed);
        robot.motorThree.setPower(speed);

        while(opModeIsActive()) {

            telemetry.addData("target heading",targetHeading);
            telemetry.addData("current heading",robot.gyroSensor.getHeading());
            telemetry.addData("heading check",Math.abs(targetHeading-robot.gyroSensor.getHeading()) % 360);
            telemetry.update();

            // When we are within 15 degrees of the target, move slower
            if(Math.abs(targetHeading - robot.gyroSensor.getHeading()) % 360 <= 15) {
                robot.motorOne.setPower(speed / TURN_SPEED * FINE_TURN);
                robot.motorTwo.setPower(speed / TURN_SPEED * FINE_TURN);
                robot.motorThree.setPower(speed / TURN_SPEED * FINE_TURN);
            }

            // If we are within 3 degrees of the target, end the rotation
            if(Math.abs(targetHeading - robot.gyroSensor.getHeading()) % 360 <= 3) {
                break;
            }
        }

        // Turn motors off
        robot.motorOne.setPower(0);
        robot.motorTwo.setPower(0);
        robot.motorThree.setPower(0);
    }
}
