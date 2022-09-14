package pl.javasolutions.security;

import java.time.Clock;
import java.time.LocalDateTime;

public interface ClockRepository {
    LocalDateTime now();

    Clock getCurrentClock();
}
