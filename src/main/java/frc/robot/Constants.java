// Copyright (c) 2021-2026 Littleton Robotics
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by a BSD
// license that can be found in the LICENSE file
// at the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.GravityTypeValue;
import edu.wpi.first.wpilibj.RobotBase;

/**
 * This class defines the runtime mode used by AdvantageKit. The mode is always "real" when running
 * on a roboRIO. Change the value of "simMode" to switch between "sim" (physics sim) and "replay"
 * (log replay from a file).
 */
public final class Constants {
  public static final Mode simMode = Mode.SIM;
  public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;

  public static enum Mode {
    /** Running on a real robot. */
    REAL,

    /** Running a physics simulator. */
    SIM,

    /** Replaying from a log file. */
    REPLAY
  }

  public static class ElevatorConstants {
    public static final double ELEVATOR_MAGNET_OFFSET = -0.13;
    public static final MotionMagicConfigs MOTION_MAGIC_CONFIGS =
        new MotionMagicConfigs().withMotionMagicAcceleration(150).withMotionMagicCruiseVelocity(50);
    public static final Slot0Configs SLOT0_CONFIGS =
        new Slot0Configs()
            .withGravityType(GravityTypeValue.Elevator_Static)
            .withKP(14)
            .withKI(0)
            .withKD(0.6)
            .withKG(0.01)
            .withKA(0)
            .withKS(0)
            .withKV(0);
    public static final FeedbackConfigs FEEDBACK_CONFIGS =
        new FeedbackConfigs()
            .withFeedbackRemoteSensorID(MotorIDConstants.ELEVATOR_ENCODER_ID)
            .withFeedbackSensorSource(FeedbackSensorSourceValue.RemoteCANcoder);

    public static final double maxHeight = 8.25;
    public static final double minHeight = -0.04;
  }

  public static class MotorIDConstants {
    public static final int END_EFFECTOR_MOTOR_ID = 2;
    public static final int GROUND_CORAL_MOTOR_ID = 1;
    public static final int CLIMBER_TOP_MOTOR_ID = 5;
    public static final int CLIMBER_MOTOR_ID1 = 4;
    public static final int CLIMBER_MOTOR_ID2 = 6;
    public static final int PIVOT_MOTOR_ID = 7;

    public static final int ELEVATOR_MOTOR_ID1 = 9;
    public static final int ELEVATOR_MOTOR_ID2 = 10;
    public static final int ELEVATOR_ENCODER_ID = 8;
    public static final int SERVO_CHANNE1 = 2;
    public static final int SERVO_CHANNE2 = 3;
  }
}
