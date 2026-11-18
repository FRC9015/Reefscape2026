package frc.robot.subsystems.endeffector;

import org.littletonrobotics.junction.AutoLog;

public interface EndEffectorIO {

  @AutoLog
  public static class EndEffectorIOInputs {
    public double endEffectorVoltage = 0.0;
    public double endEffectorAppliedAmps = 0.0;
    public double endEffectorRPM = 0.0;
    public boolean endEffectorEncoderConnected = false;
    public boolean coralDetected = false;
  }

  public void updateInputs(EndEffectorIOInputs inputs);

  public void stop();

  public void setEndEffectorSpeed(double rpm);

  public void setBrakeMode(boolean brakemode);
}
