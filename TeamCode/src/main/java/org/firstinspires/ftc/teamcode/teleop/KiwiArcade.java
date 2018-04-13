package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.HardwareKiwi;

/**
 * Created by HSstudent on 2/27/2018.
 */

@TeleOp(name="Kiwi: Arcade", group="Kiwi")
public class KiwiArcade extends OpMode {

    HardwareKiwi robot = new HardwareKiwi();

    public void init() {
        robot.init(hardwareMap);
        telemetry.addData("Say","Hello Driver");
    }

    public void loop() {
        double speed;
        double moveX;
        double moveY;
        double rotate;

        double motorOnePower;
        double motorTwoPower;
        double motorThreePower;
        double kickerPower;

        double maxPower;

        // Driving

        speed = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
        moveX = gamepad1.left_stick_x;
        moveY = -gamepad1.left_stick_y;
        rotate = -gamepad1.right_stick_x;

        motorOnePower = speed * (moveX * -0.5 + moveY * .866) + rotate;
        motorTwoPower = speed * (moveX) + rotate;
        motorThreePower = speed * (moveX * -0.5 - moveY * .866) + rotate;

        maxPower = max(motorOnePower, motorTwoPower, motorThreePower);

        motorTwoPower *= .925;

        if (maxPower > 1.0) {
            motorOnePower /= maxPower;
            motorTwoPower /= maxPower;
            motorThreePower /= maxPower;
        }

        robot.motorOne.setPower(motorOnePower);
        robot.motorTwo.setPower(motorTwoPower);
        robot.motorThree.setPower(motorThreePower);

        telemetry.addData("moveX", moveX);
        telemetry.addData("moveY", moveY);
        telemetry.addData("rotate", rotate);

        // Kicker

        kickerPower = gamepad1.right_trigger - gamepad1.left_trigger;

        robot.kicker.setPower(kickerPower);

        telemetry.addData("kicker", kickerPower);

        // Update telemetry

        telemetry.update();

    }

    public double max(double n1, double n2, double n3) {
        return Math.max(Math.max(Math.abs(n1),Math.abs(n2)),Math.abs(n3));
    }
}
