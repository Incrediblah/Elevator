// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs;
import frc.robot.RobotContainer;
import frc.robot.Constants.ElevatorSubsystemConstants;
import frc.robot.Constants.ElevatorSubsystemConstants.ElevatorSetPoints;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkBase.ControlType;


import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElevatorSubsystem extends SubsystemBase {
  public enum Setpoint {
    Level1,
    Level2,
    Level3,
    Level4;
  }
  private boolean wasResetByButton = false;
  private boolean wasResetByLimit = false ;

   private SparkFlex elevatorMotor =
      new SparkFlex(ElevatorSubsystemConstants.ElevatorVortexCanId, MotorType.kBrushless);
  private SparkClosedLoopController elevatorClosedLoopController =
      elevatorMotor.getClosedLoopController();
  private RelativeEncoder elevatorEncoder = elevatorMotor.getEncoder();
  /** Creates a new ElevatorSubsystem. */

  private double elevatorCurrentTarget = 0;
  public ElevatorSubsystem() {

    elevatorMotor.configure(
      Configs.ElevatorSubsystem.elevatorConfig,
      ResetMode.kResetSafeParameters,
      PersistMode.kPersistParameters);

      elevatorEncoder.setPosition(0);

  }

  private void moveToSetpoint() {
    elevatorClosedLoopController.setReference( elevatorCurrentTarget, ControlType.kMAXMotionPositionControl);
  }

  private void zeroOnUserButton(){
    if (!wasResetByButton && RobotController.getUserButton()){
      wasResetByButton = true;
      elevatorEncoder.setPosition(0);
     
    } else if (!RobotController.getUserButton()){
      wasResetByButton = false;
    }
}


  public Command setSetpointCommand(Setpoint setpoint) {
    return this.runOnce(
        () -> {
          switch (setpoint) {
          
            case Level1:
              elevatorCurrentTarget = ElevatorSetPoints.Level1;
              break;
            case Level2:
              elevatorCurrentTarget = ElevatorSetPoints.Level2;
              break;
            case Level3:
              elevatorCurrentTarget = ElevatorSetPoints.Level3;
              break;
            case Level4:
              elevatorCurrentTarget = ElevatorSetPoints.Level4;
              break;
          }
        });
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    moveToSetpoint();
    zeroOnUserButton();



    SmartDashboard.putNumber("ElevatorTarget Position", elevatorCurrentTarget);
    SmartDashboard.putNumber("Elevator Actual Position", elevatorEncoder.getPosition());
  }
}
