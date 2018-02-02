package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.HardwareCompbot;

@TeleOp(name="Compbot: ARCADE", group="Compbot")

public class CombotTank extends OpMode{

    /* Declare OpMode members. */
    HardwareCompbot robot       = new HardwareCompbot();
    boolean fine = false;

    // Arm positions
    final double UP = 1.0;
    final double DOWN = 0.2;

    @Override
    public void init() {

        robot.init(hardwareMap);

        robot.leftClaw.setPosition(0);
        robot.rightClaw.setPosition(1);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Drivers");    //

    }

    @Override
    public void loop() {
        double speed;
        double angle;
        double rotate;
        double glyph;

        // DRIVER CONTROLS

        speed = Math.hypot(gamepad1.left_stick_x,gamepad1.left_stick_y);
        angle = Math.atan2(gamepad1.left_stick_x,gamepad1.left_stick_y) + 180;
        rotate = gamepad1.right_stick_x;

        // Allow for fine control by pressing RB, cancel using LB
        if (gamepad1.right_bumper) {
            fine = true;
        } else if (gamepad1.left_bumper) {
            fine = false;
        }
        if(fine) {
            speed *= .15;
        }

        // If any speed value would be over 1, scale everything down to allow this greater value to be lower

        robot.frontRight.setPower(speed * Math.sin(angle) - rotate);
        robot.backRight.setPower(speed * Math.cos(angle) - rotate);
        robot.frontLeft.setPower(speed * Math.cos(angle) + rotate);
        robot.backLeft.setPower(speed * Math.sin(angle) + rotate);

        // OPERATOR CONTROLS

        glyph = gamepad2.right_trigger - gamepad2.left_trigger;
        robot.glyphLift.setPower(glyph);

        if (gamepad2.right_bumper) {
            robot.leftClaw.setPosition(1);
            robot.rightClaw.setPosition(0);
        } else if (gamepad2.left_bumper) {
            robot.leftClaw.setPosition(0);
            robot.rightClaw.setPosition(1);
        }

        // TELEMETRY

        telemetry.addData("speed",  "%.2f", speed);
        telemetry.addData("angle", "%.2f", angle);
        telemetry.addData("rotation", "%.2f", rotate);
        telemetry.addData("glyph lift", "%.2f", glyph);
        telemetry.addData("fine control", fine);
    }
}
