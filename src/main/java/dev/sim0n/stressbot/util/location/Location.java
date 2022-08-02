package dev.sim0n.stressbot.util.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author sim0n
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private double x, y, z;
    private float yaw, pitch;
    private boolean onGround;

    public void update(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void update(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.yaw = yaw;
        this.pitch = pitch;
    }

    public void add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public Location move(double direction, double offset) {
        x += offset * -Math.sin(direction);
        z += offset * Math.cos(direction);

        return this;
    }

    public double distance2D(Location loc) {
        return Math.sqrt(Math.pow(x - loc.x, 2D) + Math.pow(z - loc.z, 2D));
    }

    public double distance(Location loc) {
        return Math.sqrt(Math.pow(x - loc.x, 2D) + Math.pow(y - loc.y, 2D) + Math.pow(z - loc.z, 2D));
    }

    @Override
    public Location clone() {
        return new Location(x, y, z, yaw, pitch, onGround);
    }
}
