package ge.edu.cu.l_lomidze2.sabakalavro.util;

import jakarta.servlet.http.HttpServletRequest;

public class IpUtil {
    public static String getIp(HttpServletRequest request) {
        var xfHeader = request.getHeader("X-Forwarded-For");

        return (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(request.getRemoteAddr()))
            ? request.getRemoteAddr()
        : xfHeader.split(",")[0];
    }
}
