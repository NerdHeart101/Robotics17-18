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


    static final double     FORWARD_SPEED = 0.5;
    static final double     TURN_SPEED    = 0.5;

    @Override
    public void runOpMode() {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path, ensuring that the Auto mode has not been stopped along the way

        while (opModeIsActive() && runtime.seconds() < 3.0) {
            robot.jewelArm.setPosition(.3);
            if(robot.colorSensor.red() > 1) {
                robot.leftDrive.setPower(-FORWARD_SPEED);
                robot.rightDrive.setPower(-FORWARD_SPEED);
                runtime.reset();

            } else {
                robot.leftDrive.setPower(FORWARD_SPEED);
                robot.rightDrive.setPower(FORWARD_SPEED);
                runtime.reset();
            }

        }

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }
}
