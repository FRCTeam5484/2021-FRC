package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LED;
import frc.robot.helpers.FMSManager;

public class subLED extends SubsystemBase {
  private AddressableLED leftStrand = new AddressableLED(LED.LiftStrand.LeftPort);
  private AddressableLED rightStrand = new AddressableLED(LED.LiftStrand.RightPort);
  private AddressableLEDBuffer liftBuffer = new AddressableLEDBuffer(LED.LiftStrand.Count);
  private int liftRainbowFirst = 0;

  private AddressableLED turretStrand = new AddressableLED(LED.TurretStrand.Port);
  private AddressableLEDBuffer turretBuffer = new AddressableLEDBuffer(LED.TurretStrand.Count);
  private int turretRainbowFirst = 0;

  private Timer time = new Timer();
  public Boolean running = false;
  private double ledsPerSecond = Math.round(LED.LiftStrand.Count / 30);

  subIndexer indexer;
  subShooter shooter;
  subTurret turret;
  FMSManager fms;

  public subLED(subIndexer _indexer, subShooter _shooter, subTurret _turret, FMSManager _fms) {
    indexer = _indexer;
    shooter = _shooter;
    turret = _turret;
    fms = _fms;

    leftStrand.setLength(liftBuffer.getLength());
    rightStrand.setLength(liftBuffer.getLength());
    turretStrand.setLength(turretBuffer.getLength());

    liftRainbow();
    turretRainbow();

    leftStrand.start();
    rightStrand.start();
    turretStrand.start();
    time.start();
  }

  @Override
  public void periodic() {
    if(fms.matchTime() < 31){
      setEndGame();
    }
    else{
      if(running){
        setLiftBallColor(!indexer.ballSensor1.get(), !indexer.ballSensor2.get(), !indexer.ballSensor3.get(), !indexer.ballSensor4.get(), !indexer.ballSensor5.get());
      }
      else{
        liftRainbow();
        turretRainbow();
      }    
    }
  }
  public void liftRainbow() {
    for (var i = 0; i < liftBuffer.getLength(); i++) {
      final var hue = (liftRainbowFirst + (i * 180 / liftBuffer.getLength())) % 180;
      liftBuffer.setHSV(i, hue, 255, 128);
    }
    liftRainbowFirst += 3;
    liftRainbowFirst %= 180;
  }
  public void turretRainbow() {
    for (var i = 0; i < turretBuffer.getLength(); i++) {
      final var hue = (turretRainbowFirst + (i * 180 / turretBuffer.getLength())) % 180;
      turretBuffer.setHSV(i, hue, 255, 128);
    }
    turretRainbowFirst += 3;
    turretRainbowFirst %= 180;
  }
  private void setFullColor(AddressableLEDBuffer buffer, int Red, int Green, int Blue){
    for (var i = 0; i < buffer.getLength(); i++) {
      buffer.setRGB(i, Red, Green, Blue);
    }
  }
  private void setColor(AddressableLEDBuffer buffer, int[] leds, int Red, int Green, int Blue){
    for (var i = 0; i < buffer.getLength(); i++) {
      buffer.setRGB(i, Red, Green, Blue);
    }
  }
  public void setLiftBallColor(boolean ball1, boolean ball2, boolean ball3, boolean ball4, boolean ball5){
    if(ball1){
      int[] leds = new int[]{0,1,2};
      setColor(liftBuffer, leds, 0,255,0);
    }
    else{
      int[] leds = new int[]{0,1,2};
      setColor(liftBuffer, leds, 255,0,0);
    }
    if(ball2){
      int[] leds = new int[]{3,4,5};
      setColor(liftBuffer, leds, 0,255,0);
    }
    else{
      int[] leds = new int[]{3,4,5};
      setColor(liftBuffer, leds, 255,0,0);
    }
    if(ball3){
      int[] leds = new int[]{6,7,8};
      setColor(liftBuffer, leds, 0,255,0);
    }
    else{
      int[] leds = new int[]{6,7,8};
      setColor(liftBuffer, leds, 255,0,0);
    }
    if(ball4){
      int[] leds = new int[]{9,10,11};
      setColor(liftBuffer, leds, 0,255,0);
    }
    else{
      int[] leds = new int[]{9,10,11};
      setColor(liftBuffer, leds, 255,0,0);
    }
    if(ball5){
      int[] leds = new int[]{12,13,14};
      setColor(liftBuffer, leds, 0,255,0);
    }
    else{
      int[] leds = new int[]{12,13,14};
      setColor(liftBuffer, leds, 255,0,0);
    }
    leftStrand.setData(liftBuffer);
    rightStrand.setData(liftBuffer);
  }
  public void setShootReady(){
    if(turret.TurretReady() && shooter.ShooterReady() && indexer.HasABall()){
      for (var i = 0; i < turretBuffer.getLength(); i++) {
        turretBuffer.setRGB(i, 0, 255, 0);
      }   
    }
    else if(turret.TurretReady() && !shooter.ShooterReady() && indexer.HasABall()){
      if(time.get() % 2 == 0){
        for (var i = 0; i < turretBuffer.getLength(); i++) {
          turretBuffer.setRGB(i, 0, 0, 255);
        }
      }
      else{
        for (var i = 0; i < turretBuffer.getLength(); i++) {
          turretBuffer.setRGB(i, 0, 0, 0);
        }
      }
    }
    else{
      for (var i = 0; i < turretBuffer.getLength(); i++) {
        turretBuffer.setRGB(i, 255, 0, 0);
      }
    }
  }
  public void setEndGame(){
    double seconds = fms.matchTime();
    if(seconds >= 30){
      setFullColor(liftBuffer, 0, 255, 0);
    }
    else {
      for(int i = LED.LiftStrand.Count-1; i >= (LED.LiftStrand.Count-1)-ledsPerSecond; i--){
        liftBuffer.setRGB(i, 0, 0, 0);
        ledsPerSecond--;
      }
    }
  }
}
