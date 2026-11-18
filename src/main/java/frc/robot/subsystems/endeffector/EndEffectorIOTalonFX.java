package frc.robot.subsystems.endeffector;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;

public class EndEffectorIOTalonFX implements EndEffectorIO {
  TalonFX motor;

  private final StatusSignal<AngularVelocity> rpmSignal;
  private final StatusSignal<Voltage> voltageSignal;
  private final StatusSignal<Current> currentSignal;

  private final Debouncer encoderConnectedDebounce = new Debouncer(0.5);

  public EndEffectorIOTalonFX(int motorID) {
    motor = new TalonFX(motorID);
    rpmSignal = motor.getVelocity();
    voltageSignal = motor.getMotorVoltage();
    currentSignal = motor.getStatorCurrent();

    TalonFXConfiguration config = new TalonFXConfiguration();
    config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
    config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
    config.CurrentLimits.StatorCurrentLimit = 45.0;
    config.CurrentLimits.StatorCurrentLimitEnable = true;
    motor.getConfigurator().apply(config);
  }

  public void updateInputs(EndEffectorIOInputs inputs) {
    StatusCode status = BaseStatusSignal.refreshAll(rpmSignal, voltageSignal, currentSignal);
    inputs.endEffectorEncoderConnected =
        encoderConnectedDebounce.calculate(status == StatusCode.OK);
    inputs.endEffectorRPM = rpmSignal.getValueAsDouble();
    inputs.endEffectorVoltage = voltageSignal.getValueAsDouble();
    inputs.endEffectorAppliedAmps = currentSignal.getValueAsDouble();
  }

  public void stop() {
    motor.stopMotor();
  }

  public void setEndEffectorSpeed(double voltage) {
    motor.setVoltage(MathUtil.clamp(voltage, -12.0, 12.0));
  }

  public void setBrakeMode(boolean brakemode) {
    motor.setNeutralMode(brakemode ? NeutralModeValue.Brake : NeutralModeValue.Coast);
  }
}
