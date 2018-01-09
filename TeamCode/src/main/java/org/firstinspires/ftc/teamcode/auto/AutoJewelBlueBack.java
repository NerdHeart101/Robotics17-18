package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.core.HardwareCompbot;

/**
 * Created by HSstudent on 12/5/2017.
 */

@Autonomous(name="Blue (Back): Jewel & Park", group="blue")
@Disabled
public class AutoJewelBlueBack extends LinearOpMode {

    HardwareCompbot robot = new HardwareCompbot();
    private ElapsedTime runtime = new ElapsedTime();

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
                if (robot.colorSensor.red() < robot.colorSensor.blue()) {
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

        robot.jewelArm.setPosition(up);
        robot.jewelShoulder.setPosition(0.5);

        telemetry.addData("Status", "Complete");
        telemetry.update();

        while(opModeIsActive()){}
    }
}
