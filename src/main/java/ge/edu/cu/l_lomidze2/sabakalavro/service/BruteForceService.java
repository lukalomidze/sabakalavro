package ge.edu.cu.l_lomidze2.sabakalavro.service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BruteForceService {
    private static Logger logger = LogManager.getLogger();

    private static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    @Autowired
    private Map<String, Integer> cache;

    @Value("${app.authentication.failure.lockout}")
    private Duration lockoutDuration;

    public void onLoginFailed(String ip) {
        var failed = cache.merge(ip, 1, Math::addExact);

        logger.warn("ip {} failed login {} times", ip, failed);

        if (failed >= 3) {
            executor.schedule(() -> cache.remove(ip), 3, TimeUnit.SECONDS);
        }
    }

    public boolean isLockedOut(String ip) {
        var attempts = cache.getOrDefault(ip, 0);

        return attempts >= 3;
    }

    public void clearIp(String ip) {
        cache.remove(ip);
    }
}
