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
        //Top-right
        if (x >= 0 && y >= 0) {
            return (x * x + y * y) <= (r / 2) * (r / 2);
        }
        //Top-left
        if (x <= 0 && y >= 0) {
            return x / 2 + r / 2 >= y;
        }
        //Bottom-left
        if (x <= 0 && y <= 0) {
            return (y >= (-r)) && (x >= (-r) / 2);
        }
        //Bottom-right
        return false;
    }
}
