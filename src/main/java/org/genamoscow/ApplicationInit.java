package org.genamoscow;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class ApplicationInit implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        log.info("Starting gallery service...");
    }
}
