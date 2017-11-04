package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

/**
 * Created by hsstudent on 11/2/2017.
 */

@Autonomous(name="Compbpt: Auto", group="Compbot")

public class AutoComp extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareCompbot robot   = new HardwareCompbot();   // Use a Pushbot's hardware
    private ElapsedTime runtime = new ElapsedTime();

    static final double     TURN_SPEED    = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);
        robot.colorSensor.enableLed(true);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        robot.jewelArm.setPosition(.3);
        runtime.reset();
        sleep(1000);
        while (runtime.seconds() < 1.0) {
            telemetry.addData("2.5f s", runtime.seconds());
            if(robot.colorSensor.red() >= 1) {
                robot.leftDrive.setPower(-.5);
                robot.rightDrive.setPower(-.5);
                sleep(1000);
                robot.jewelArm.setPosition(1);
                sleep(1000);
                runtime.reset();
                break;

            } else {
                robot.leftDrive.setPower(.5);
                robot.rightDrive.setPower(5);
                sleep(1000);
                robot.jewelArm.setPosition(1);
                sleep(1000);
                runtime.reset();
                while (runtime.seconds() < 4.0) {
                    robot.leftDrive.setPower(.5);
                    robot.rightDrive.setPower(-.5);
                    sleep(100);
                    robot.leftDrive.setPower(.7);
                    robot.rightDrive.setPower(.7);
                    runtime.reset();

                }


            }
        }


        telemetry.addData("Path", "Complete");
        telemetry.addData("%d", robot.colorSensor.red());
        telemetry.addData("%d", robot.colorSensor.blue());

        telemetry.update();
        sleep(1000);
    }
}
