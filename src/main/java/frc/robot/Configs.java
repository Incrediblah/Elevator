package frc.robot;

import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class Configs {

    public static final class ElevatorSubsystem{

        public static final SparkFlexConfig elevatorConfig = new SparkFlexConfig();

        static {
                // Configure basic settings of the arm motor
                elevatorConfig.idleMode(IdleMode.kCoast).smartCurrentLimit(40).voltageCompensation(12);
          
                /*
                 * Configure the closed loop controller. We want to make sure we set the
                 * feedback sensor as the primary encoder.
                 */
                elevatorConfig
                   
             .closedLoop
          .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
          // Set PID values for position control
          .p(0.1)
          .outputRange(-1, 1)
          .maxMotion
          // Set MAXMotion parameters for position control
          .maxVelocity(4200)
          .maxAcceleration(6000)
          .allowedClosedLoopError(0.5);


    }}}
    

