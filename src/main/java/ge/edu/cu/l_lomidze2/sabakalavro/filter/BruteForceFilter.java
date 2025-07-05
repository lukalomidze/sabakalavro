package ge.edu.cu.l_lomidze2.sabakalavro.filter;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ge.edu.cu.l_lomidze2.sabakalavro.service.BruteForceService;
import ge.edu.cu.l_lomidze2.sabakalavro.util.IpUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class BruteForceFilter extends OncePerRequestFilter {
    private static Logger logger = LogManager.getLogger();

    @Autowired
    private BruteForceService bruteForceService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        var ip = IpUtil.getIp(request);

        if (bruteForceService.isLockedOut(ip)) {
            logger.info("ip {} locked out", ip);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "try again later");
        }

        filterChain.doFilter(request, response);
    }
}
