package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by hsstudent on 10/19/2017.
 */
@Autonomous(name = "Babybot: Auto", group = "babybot")
public class AutoTest extends LinearOpMode {

    HardwareCompbot babybot = new HardwareCompbot();
    private ElapsedTime elapsedTime = new ElapsedTime();

    private static final double SPEED = 0.5;
    private static final double TURN_SPEED = 0.5;


    @Override
    public void runOpMode() throws InterruptedException {

        babybot.init(hardwareMap);

        telemetry.addData("Status","Ready to go!");
        telemetry.update();

        waitForStart();

        babybot.leftDrive.setPower(SPEED);
        babybot.rightDrive.setPower(SPEED);
        elapsedTime.reset();

        while (opModeIsActive() && (elapsedTime.seconds() < 3.0)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", elapsedTime.seconds());
            telemetry.update();

        }

        babybot.leftDrive.setPower(TURN_SPEED);
        babybot.rightDrive.setPower(-TURN_SPEED);
        elapsedTime.reset();
        while (opModeIsActive() && (elapsedTime.seconds() < 1.3)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", elapsedTime.seconds());
            telemetry.update();
        }

        babybot.leftDrive.setPower(-SPEED);
        babybot.rightDrive.setPower(-SPEED);
        elapsedTime.reset();
        while (opModeIsActive() && (elapsedTime.seconds() < 1.0)) {
            telemetry.addData("Path", "Leg 3: %2.5f S Elapsed", elapsedTime.seconds());
            telemetry.update();
        }

    }
}
