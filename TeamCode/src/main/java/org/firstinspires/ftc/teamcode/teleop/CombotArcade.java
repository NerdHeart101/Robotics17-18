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

    final double[] bottomPositions = {0.0,0.5,0.75};
    final double[] topPositions = {0.0,0.4,0.7};

    // Variables for toggles
    boolean bottomPress = false;
    boolean topPress = false;
    boolean modePress = false;

    // Toggle for operator control modes
    boolean glyphMode = true;

    // Arm positions
    final double UP = 1.0;
    final double DOWN = 0.2;

    // Speeds
    final double GLYPH_SPEED = 1.0;
    final double RELIC_SPEED = 0.1;
    final double RELIC_SERVO_SPEED = 0.01;

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

        String modeTelemetry = "";

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

        // GLYPH MODE

        if(glyphMode) {

            modeTelemetry = "GLYPH MODE";

            // Glyph lift: D-Pad up and down
            glyph = gamepad2.dpad_up ? GLYPH_SPEED : gamepad2.dpad_down ? -GLYPH_SPEED : 0;
            robot.glyphLift.setPower(glyph);

            // Glyph claws: right to close farther, left to close farther
            if (gamepad2.right_trigger > 0.1 && !bottomPress) {
                bottomClaw++;
                bottomPress = true;
            } else if (gamepad2.left_trigger > 0.1 && !bottomPress) {
                bottomClaw--;
                bottomPress = true;
            } else if (gamepad2.right_trigger < 0.1 && gamepad2.left_trigger < 0.1) {
                bottomPress = false;
            }

            if (gamepad2.right_bumper && !topPress) {
                topClaw++;
                topPress = true;
            } else if (gamepad2.left_bumper && !topPress) {
                topClaw--;
                topPress = true;
            } else if (!gamepad2.right_bumper && !gamepad2.left_bumper) {
                topPress = false;
            }

            // Keep claw values between useful values
            if (bottomClaw > 2) {
                bottomClaw = 2;
            } else if (bottomClaw < 0) {
                bottomClaw = 0;
            }
            if (topClaw > 2) {
                topClaw = 2;
            } else if (topClaw < 0) {
                topClaw = 0;
            }

            // Set servo positions

            bottomPosition = bottomPositions[bottomClaw];
            topPosition = topPositions[topClaw];

            robot.bottomLeftClaw.setPosition(bottomPosition);
            robot.bottomRightClaw.setPosition(1 - bottomPosition);
            robot.topLeftClaw.setPosition(topPosition);
            robot.topRightClaw.setPosition(1 - topPosition);

        }

        // RELIC MODE

        else {

            modeTelemetry = "RELIC MODE";

            // Triggers for shoulder, buttons for elbow
            // right for out, left for in

            robot.relicShoulder.setPower((gamepad2.right_trigger - gamepad2.left_trigger) * GLYPH_SPEED);
            robot.relicElbow.setPower(gamepad2.right_bumper ? GLYPH_SPEED : gamepad2.left_bumper ? -GLYPH_SPEED : 0);

            // Use D-pad to move relic wrist
            double wristPosition = robot.relicWrist.getPosition() + (gamepad2.dpad_up ? RELIC_SERVO_SPEED : gamepad2.dpad_down ? -RELIC_SERVO_SPEED : 0);
            if (wristPosition < 0 || wristPosition > 1) {
                if (wristPosition > 1) {
                    wristPosition = 1;
                } else {
                    wristPosition = 0;
                }
            }
            robot.relicWrist.setPosition(wristPosition);

            // Use A and B to open and close relic hand, respectively

            if(gamepad2.a || gamepad2.b) {
                if(gamepad2.a) {
                    robot.relicHand.setPosition(0.0);
                } else if (gamepad2.b) {
                    robot.relicHand.setPosition(1.0);
                }
            }
        }

        // MODE SWITCH

        if(gamepad2.back) {
            if(!modePress) {
                modePress = !modePress;
            }
        } else {
            modePress = false;
        }

        // TELEMETRY

        telemetry.addData("speed",  "%.2f", speed);
        telemetry.addData("angle", "%.2f", angle);
        telemetry.addData("rotation", "%.2f", rotate);
        telemetry.addData("fine control", fine);
        telemetry.addData("operator mode", modeTelemetry);
    }
}
