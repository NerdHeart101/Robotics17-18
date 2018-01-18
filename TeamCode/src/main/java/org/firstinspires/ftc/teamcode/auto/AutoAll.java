package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.core.HardwareCompbot;

/**
 * Created by HSstudent on 12/5/2017.
 */

@Autonomous(name="Auto: ALL", group="auto")
public class AutoAll extends LinearOpMode {

    HardwareCompbot robot = new HardwareCompbot();
    private ElapsedTime runtime = new ElapsedTime();

    // Encoder variables
    static final double     TURN_RADIUS             = 6.25;
    static final double     COUNTS_PER_MOTOR_REV    = 280 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.2;
    static final double     TURN_SPEED              = 0.5;

    // Auto control variables
    // autoPrefs is a placeholder for a GUI to select each variable
    // color: true means red, false means blue
    // position: true means front, false means back
    private boolean[] autoPrefs = {true,false};

    private boolean color = autoPrefs[0];
    private boolean position = autoPrefs[1];

    @Override
    public void runOpMode() {

        // Arm positions
        final double up = 1.0;
        final double down = 0.25;

        // Initialization procedures
        robot.init(hardwareMap);

        robot.colorSensor.enableLed(true);

        robot.leftClaw.setPosition(0);
        robot.rightClaw.setPosition(1);

        robot.jewelArm.setPosition(up);
        robot.jewelShoulder.setPosition(0.5);

        waitForStart();
        /*
        // BEGIN AUTONOMOUS

        // Grab glyph, get arm in sensing position

        robot.leftClaw.setPosition(1);
        robot.rightClaw.setPosition(0);

        robot.jewelArm.setPosition(down);

        sleep(2000);

        // Sense color and knock jewel
        while(opModeIsActive()) {
            telemetry.addData("Status", "Sensing");
            telemetry.update();
            if (robot.colorSensor.red() != robot.colorSensor.blue()) {
                if ((robot.colorSensor.red() > robot.colorSensor.blue()) == color) {
                    robot.jewelShoulder.setPosition(0.7);
                } else {
                    robot.jewelShoulder.setPosition(0.3);
                }
                sleep(2000);
                break;
            } else {
                robot.jewelShoulder.setPosition(robot.jewelShoulder.getPosition() + .001);
            }
        }

        // Reset arm
        robot.jewelArm.setPosition(up);
        robot.jewelShoulder.setPosition(0.5);

        // Drive to safe zone

        // Rotate the correct direction based on starting corner
        if(color == position) {
            rotateInPlace(-18.5);
        } else {
            rotateInPlace(18.5);
        }

        // Drive straight forward or backward based on color
        if(color) {
            encoderDrive(DRIVE_SPEED, -31.6, -31.6, 10);
        } else {
            encoderDrive(DRIVE_SPEED, 31.6, 31.6, 10);
        }
        */

        encoderDrive(DRIVE_SPEED, 10,10,10);

        telemetry.addData("Status", "Complete");
        telemetry.update();

        while(opModeIsActive()){}
    }

    // Method by FIRST to drive a certain number of inches
    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.leftDrive.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.rightDrive.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            robot.leftDrive.setTargetPosition(newLeftTarget);
            robot.rightDrive.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            robot.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.leftDrive.setPower(Math.abs(speed));
            robot.rightDrive.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.leftDrive.isBusy() && robot.rightDrive.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.leftDrive.getCurrentPosition(),
                        robot.rightDrive.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            robot.leftDrive.setPower(0);
            robot.rightDrive.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }

    // Positive degrees is left (Counter clockwise)
    public void rotateInPlace(double degrees) {
        degrees += 5;   // 5 degrees are added to account for errors in the encoders

        double arc = TURN_RADIUS * Math.toRadians(degrees);
        double leftArc = -arc;
        double rightArc = arc;
        double rotateTimeout = Math.abs(arc)*TURN_SPEED*2;
        // Use the encoderDrive method to perform the move
        encoderDrive(TURN_SPEED, leftArc, rightArc, rotateTimeout);
    }
}
