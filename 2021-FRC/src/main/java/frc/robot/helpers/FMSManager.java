package frc.robot.helpers;

import edu.wpi.first.wpilibj.DriverStation;

public class FMSManager {
    DriverStation fms;
    public FMSManager(DriverStation fms_) {
        fms = fms_;
    }

    public boolean fmsActive() {
        return fms.isFMSAttached();
    }

    public boolean isDriverStationAttached() {
        return fms.isDSAttached();
    }

    public double matchTime() {
        return fms.getMatchTime();
    }

    public String getGameMessage() {
        return String.valueOf(fms.getGameSpecificMessage().charAt(0));
    }
}
