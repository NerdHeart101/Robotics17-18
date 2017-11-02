/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * This file provides basic Telop driving for a Pushbot robot.
 * The code is structured as an Iterative OpMode
 *
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 *
 * This particular OpMode executes a basic Tank Drive Teleop for a PushBot
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Baby bot: TANK", group="Baby bot")

public class BabybotTank extends OpMode{

    /* Declare OpMode members. */
    HardwareBabybot robot       = new HardwareBabybot();

    boolean fine = false;
    boolean glyphGrab = false;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //

    }

    @Override
    public void loop() {
        double left;
        double right;
        double glyph;

        left = -gamepad1.left_stick_y;
        right = -gamepad1.right_stick_y;
        glyph = gamepad1.right_trigger - gamepad1.left_trigger;

        /*
        // Allow for fine control by pressing RB
        if (gamepad1.right_bumper) {
           fine = !fine;
        }

        if(fine) {
            left *= .15;
            right *= .15;
        }
        */

        if(gamepad1.left_bumper) {
            glyphGrab = false;
        } else if(gamepad1.right_bumper) {
            glyphGrab = true;
        }

        if (glyphGrab) {
            robot.leftClaw.setPosition(1);
            robot.rightClaw.setPosition(0);
        } else {
            robot.rightClaw.setPosition(.9);
            robot.leftClaw.setPosition(.1);
        }

        if (gamepad1.a) {
            robot.jewelArm.setPosition(.2);
        } else if (gamepad1.b) {
            robot.jewelArm.setPosition(1);
        }

        telemetry.addData("left wheel",  "%.2f", left);
        telemetry.addData("right wheel", "%.2f", right);
        telemetry.addData("glyph lift", "%.2f", glyph);

        robot.leftDrive.setPower(left);
        robot.rightDrive.setPower(right);
        robot.glyphLift.setPower(glyph);

        //telemetry.addData("fine control:", fine);
        telemetry.addData("left claw", robot.leftClaw.getPosition());
        telemetry.addData("right claw", robot.rightClaw.getPosition());
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}