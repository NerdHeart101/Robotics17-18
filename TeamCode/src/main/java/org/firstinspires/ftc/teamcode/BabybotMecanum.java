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

@TeleOp(name="Babybot: MECANUM", group="Babybot")

public class BabybotMecanum extends OpMode{

    HardwareBabybot robot       = new HardwareBabybot();

    final double DRIVE_POWER = 0.8;

    @Override
    public void init() {

        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
    }

    @Override
    public void loop() {
        double speed,angle,rotate;

        // Run wheels in arcade mode
        speed = Math.hypot(gamepad1.left_stick_x,gamepad1.left_stick_y);
        angle = Math.atan2(gamepad1.left_stick_y,gamepad1.left_stick_x)+3*Math.PI/4;
        rotate = gamepad1.right_stick_x;

        // Set power of all motors to the correct value
        robot.frontRight.setPower(DRIVE_POWER * speed * Math.sin(angle) - rotate);
        robot.backRight.setPower(DRIVE_POWER * speed * Math.cos(angle) - rotate);
        robot.frontLeft.setPower(DRIVE_POWER * speed * Math.cos(angle) + rotate);
        robot.backLeft.setPower(DRIVE_POWER * speed * Math.sin(angle) + rotate);

        // Send telemetry message to signify robot running;
        telemetry.addData("speed",    "%.2f", speed);
        telemetry.addData("angle",    "%.2f", (Math.toDegrees(angle)-90)%360);
    }
}