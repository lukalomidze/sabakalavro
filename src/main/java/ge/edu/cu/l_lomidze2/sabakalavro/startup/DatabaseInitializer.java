package ge.edu.cu.l_lomidze2.sabakalavro.startup;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ResourceLoader;

import ge.edu.cu.l_lomidze2.sabakalavro.model.User;
import ge.edu.cu.l_lomidze2.sabakalavro.repository.UserRepository;
import ge.edu.cu.l_lomidze2.sabakalavro.util.JsonUtil;

public class DatabaseInitializer implements ApplicationRunner {
    private static Logger logger = LogManager.getLogger();

    private UserRepository userRepository;

    private ResourceLoader resourceLoader;

    public DatabaseInitializer(
        UserRepository userRepository,
        ResourceLoader resourceLoader
    ) {
        this.userRepository = userRepository;
        this.resourceLoader = resourceLoader;
    }

    @Value("${app.startup.data.path}")
    private String jsonPath;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            initializeUsers();
        } catch (IOException exception) {
            logger.error("failed to initialize database: file {} not found", exception.getMessage());
            return;
        }

        logger.info("initialized database with startup data");
    }

    private void initializeUsers() throws IOException {
        if (userRepository.count() != 0) {
            return;
        }

        var json = new String(resourceLoader.getResource(jsonPath).getInputStream().readAllBytes());

        var users = JsonUtil.toList(
            json,
            User.class
        );

        userRepository.saveAll(users);
    }
}
