package pl.javasolutions.security.jwt;

import pl.javasolutions.security.ClockRepository;

import java.time.Clock;
import java.time.LocalDateTime;

class LocalDateClockRepository implements ClockRepository {
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }

    @Override
    public Clock getCurrentClock() {
        return Clock.systemDefaultZone();
    }


}
