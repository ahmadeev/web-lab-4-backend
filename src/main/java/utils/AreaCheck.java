package utils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named(value = "areaCheck")
@ApplicationScoped
public class AreaCheck {

    public AreaCheck() {
        System.out.println("AreaCheck initialized");
    }

    public boolean isHit(double x, double y, double r) {
        if (r > 0) {
            if (x > 0 && y >= 0) {
                return (x * x + y * y) <= (r / 2) * (r / 2);
            }
            if (x <= 0 && y >= 0) {
                return (y <= r) && (x >= (-r) / 2);
            }
            if (x <= 0 && y <= 0) {
                return (y >= -x - r / 2);
            }
        } else if (r < 0) {
            x = -x;
            y = -y;
            r = -r;

            if (x < 0 && y <= 0) {
                return (x * x + y * y) <= (r / 2) * (r / 2);
            }
            if (x >= 0 && y <= 0) {
                return (y >= -r) && (x <= r / 2);
            }
            if (x >= 0 && y >= 0) {
                return (y <= -x + r / 2);
            }
        }
        return false;
    }

}
