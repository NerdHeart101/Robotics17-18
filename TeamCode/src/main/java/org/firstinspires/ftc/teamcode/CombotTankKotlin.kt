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

package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@TeleOp(name = "Compbot: TANK-Kotlin", group = "Compbot")
class CombotTankKotlin : OpMode() {

    /* Declare OpMode members. */
    internal var robot = HardwareCompbot()
    internal var fine = false

    override fun init() {

        robot.init(hardwareMap)

        robot.leftClaw.position = 0.0
        robot.rightClaw.position = 1.0

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Drivers")

    }

    override fun start() {
        robot.leftClaw.position = .1
        robot.rightClaw.position = .9
    }

    override fun loop() {
        var left: Double
        var right: Double
        val glyph: Double

        // DRIVER CONTROLS

        left = (-gamepad1.left_stick_y).toDouble()
        right = (-gamepad1.right_stick_y).toDouble()

        // Allow for fine control by pressing RB, cancel using LB
        if (gamepad1.right_bumper) {
            fine = true
        } else if (gamepad1.left_bumper) {
            fine = false
        }

        if (fine) {
            left *= .15
            right *= .15
        }

        robot.leftDrive.power = left
        robot.rightDrive.power = right

        // OPERATOR CONTROLS

        glyph = (gamepad2.right_trigger - gamepad2.left_trigger).toDouble()
        robot.glyphLift.power = glyph

        if (gamepad2.right_bumper) {
            robot.leftClaw.position = 1.0
            robot.rightClaw.position = 0.0
        } else if (gamepad2.left_bumper) {
            robot.leftClaw.position = .1
            robot.rightClaw.position = .9
        }

        if (gamepad2.a) {
            robot.jewelArm.position = .3
        } else if (gamepad2.b) {
            robot.jewelArm.position = 1.0
        }

        // TELEMETRY

        telemetry.addData("left wheel", "%.2f", left)
        telemetry.addData("right wheel", "%.2f", right)
        telemetry.addData("glyph lift", "%.2f", glyph)

        telemetry.addData("fine control:", fine)
    }
}