package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.HardwareCompbot;

@TeleOp(name="Compbot: ARCADE", group="Compbot")

public class CombotArcade extends OpMode{

    /* Declare OpMode members. */
    HardwareCompbot robot       = new HardwareCompbot();
    boolean fine = false;
    int bottomClaw = 0;
    int topClaw = 0;

    double[] bottomPositions = {0.0,0.5,0.6};
    double[] topPositions = {0.0,0.4,0.55};

    // Values to only let claws move once per press
    boolean bottomPress = false;
    boolean topPress = true;

    // Arm positions
    final double UP = 1.0;
    final double DOWN = 0.2;

    // Speeds
    final double GLYPH_SPEED = 1.0;

    @Override
    public void init() {

        robot.init(hardwareMap);

        robot.bottomLeftClaw.setPosition(0.0);
        robot.bottomRightClaw.setPosition(1.0);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Drivers");

    }

    @Override
    public void loop() {
        double speed;
        double angle;
        double rotate;
        double glyph;
        double bottomPosition;
        double topPosition;

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

        // If any speed value would be over 1, scale everything down to allow this greater value to be equal to 1

        robot.frontRight.setPower(speed * Math.sin(angle) - rotate);
        robot.backRight.setPower(speed * Math.cos(angle) - rotate);
        robot.frontLeft.setPower(speed * Math.cos(angle) + rotate);
        robot.backLeft.setPower(speed * Math.sin(angle) + rotate);

        // OPERATOR CONTROLS

        // Glyph lift: D-Pad up and down
        glyph = gamepad2.dpad_up ? GLYPH_SPEED : gamepad2.dpad_down ? -GLYPH_SPEED : 0;
        robot.glyphLift.setPower(glyph);

        // Glyph claws: right to close farther, left to close farther
        if(gamepad2.right_trigger > 0.1 && !bottomPress) {
            bottomClaw++;
            bottomPress = true;
        } else if (gamepad2.left_trigger > 0.1 && !bottomPress) {
            bottomClaw--;
            bottomPress = true;
        } else if (gamepad2.right_trigger < 0.1 && gamepad2.left_trigger < 0.1) {
            bottomPress = false;
        }

        if(gamepad2.right_bumper && !topPress) {
            topClaw++;
            topPress = true;
        } else if (gamepad2.left_bumper && !topPress) {
            topClaw--;
            topPress = true;
        } else if (!gamepad2.right_bumper && !gamepad2.left_bumper) {
            topPress = false;
        }

        // Keep claw values between useful values
        if(bottomClaw > 2) {
            bottomClaw = 2;
        } else if (bottomClaw < 0) {
            bottomClaw = 0;
        }
        if(topClaw > 2) {
            topClaw = 0;
        } else if (topClaw < 0) {
            topClaw = 0;
        }

        // Use appropriate positions
        bottomPosition = bottomPositions[bottomClaw];
        topPosition = topPositions[topClaw];

        robot.bottomLeftClaw.setPosition(bottomPosition);
        robot.bottomRightClaw.setPosition(1 - bottomPosition);
        robot.topLeftClaw.setPosition(topPosition);
        robot.topRightClaw.setPosition(1 - topPosition);

        // TELEMETRY

        telemetry.addData("speed",  "%.2f", speed);
        telemetry.addData("angle", "%.2f", angle);
        telemetry.addData("rotation", "%.2f", rotate);
        telemetry.addData("glyph lift", "%.2f", glyph);
        telemetry.addData("fine control", fine);
    }
}
