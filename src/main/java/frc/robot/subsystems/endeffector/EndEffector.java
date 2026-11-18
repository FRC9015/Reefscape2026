package frc.robot.subsystems.endeffector;

import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.littletonrobotics.junction.Logger;

public class EndEffector extends SubsystemBase {
  private final EndEffectorIO io;
  private final EndEffectorIOInputsAutoLogged inputs = new EndEffectorIOInputsAutoLogged();
  private final Alert encoderDisconnectedAlert;
  private final Alert coralDetectedAlert;

  public EndEffector(EndEffectorIO io) {
    this.io = io;
    this.encoderDisconnectedAlert =
        new Alert("End Effector Encoder Disconnected", AlertType.kError);
    this.coralDetectedAlert = new Alert("Coral Detected", AlertType.kInfo);
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);

    encoderDisconnectedAlert.set(!inputs.endEffectorEncoderConnected);
    coralDetectedAlert.set(inputs.coralDetected);
    Logger.processInputs("EndEffector", inputs);
  }

  public void setVoltage(double voltage) {
    io.setEndEffectorSpeed(voltage);
    Logger.recordOutput("EndEffector/Voltage", voltage);
  }

  public void stop() {
    io.stop();
  }

  public void setBrakeMode(boolean brakeMode) {
    io.setBrakeMode(brakeMode);
    Logger.recordOutput("EndEffector/BrakeMode", brakeMode);
  }

  public boolean isCoralDetected() {
    return inputs.coralDetected;
  }

  public double getRPM() {
    return inputs.endEffectorRPM;
  }

  public double getAppliedVoltage() {
    return inputs.endEffectorVoltage;
  }

  public double getAppliedAmps() {
    return inputs.endEffectorAppliedAmps;
  }

  public Command runEffectorReverse(double voltage) {
    Logger.recordOutput("EndEffector/ReverseVoltage", -voltage);
    return this.startEnd(() -> setVoltage(-voltage), () -> stop());
  }

  public Command runEffectorAutoCommand() {
    return new SequentialCommandGroup(
        new InstantCommand(this::autoEffectorVoltage),
        new WaitCommand(0.25),
        new InstantCommand(this::stop));
  }

  public Command runEffector(double voltage) {
    return startEnd(() -> setVoltage(4), () -> stop());
  }

  private void autoEffectorVoltage() {
    setVoltage(-4);
  }
}
