package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

/**
 * Created by hsstudent on 11/2/2017.
 */

@Autonomous(name="Compbpt: Auto Safe Zone", group="Compbot")

public class AutoSafe extends LinearOpMode {

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
        robot.leftClaw.setPosition(.2);
        robot.rightClaw.setPosition(.1);

        waitForStart();

        robot.leftDrive.setPower(.2);
        robot.rightDrive.setPower(.2);
        runtime.reset();
        while (opModeIsActive() && runtime.seconds() < 2.0) {

        }

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

    }
}

