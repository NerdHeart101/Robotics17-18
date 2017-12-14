package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Compbot: TANK", group="Compbot")

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
    public void start() {
        robot.leftClaw.setPosition(.1);
        robot.rightClaw.setPosition(.9);
    }

    @Override
    public void loop() {
        double left;
        double right;
        double glyph;

        // DRIVER CONTROLS

        left = -gamepad1.left_stick_y;
        right = -gamepad1.right_stick_y;

        // Allow for fine control by pressing RB, cancel using LB
        if (gamepad1.right_bumper) {
            fine = true;
        } else if (gamepad1.left_bumper) {
            fine = false;
        }

        if(fine) {
            left *= .15;
            right *= .15;
        }

        robot.leftDrive.setPower(left);
        robot.rightDrive.setPower(right);

        // OPERATOR CONTROLS

        glyph = gamepad2.right_trigger - gamepad2.left_trigger;
        robot.glyphLift.setPower(glyph);

        if (gamepad2.right_bumper) {
            robot.leftClaw.setPosition(1);
            robot.rightClaw.setPosition(0);
        } else if (gamepad2.left_bumper) {
            robot.leftClaw.setPosition(.1);
            robot.rightClaw.setPosition(.9);
        }

        if (gamepad2.a) {
            robot.jewelArm.setPosition(DOWN);
        } else if (gamepad2.b) {
            robot.jewelArm.setPosition(UP);
        }

        // TELEMETRY

        telemetry.addData("left wheel",  "%.2f", left);
        telemetry.addData("right wheel", "%.2f", right);
        telemetry.addData("glyph lift", "%.2f", glyph);

        telemetry.addData("fine control:", fine);
    }
}
