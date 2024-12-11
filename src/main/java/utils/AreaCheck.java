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
            // Top-right (Quarter circle)
            if (x >= 0 && y >= 0) {
                return (x * x + y * y) <= (r / 2) * (r / 2);
            }
            // Top-left (Rectangle)
            if (x <= 0 && y >= 0) {
                return (y <= r) && (x >= (-r) / 2);
            }
            // Bottom-left (Triangle)
            if (x <= 0 && y <= 0) {
                return (y >= -x - r / 2);
            }
        } else if (r < 0) {
            // Reflecting the shapes when r < 0

            // Bottom-left (Quarter circle, reflected to top-right)
            if (x <= 0 && y <= 0) {
                return (x * x + y * y) <= (r / 2) * (r / 2);
            }
            // Bottom-right (Rectangle, reflected to top-left)
            if (x >= 0 && y <= 0) {
                return (y >= r) && (x <= (-r) / 2);
            }
            // Top-right (Triangle, reflected to bottom-left)
            if (x >= 0 && y >= 0) {
                return (y <= -x - r / 2);
            }
        }
        return false;
    }

}
