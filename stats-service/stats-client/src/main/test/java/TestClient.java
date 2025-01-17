import ru.practicum.emw.stats.StatsClient;
import ru.practicum.emw.stats.StatsClientImpl;
import ru.practicum.ewm.stats.dto.HitDto;

import java.time.LocalDateTime;
import java.util.List;

public class TestClient {

    public static void main(String[] args) {

        StatsClient sc = new StatsClientImpl("http://localhost:9090");
        HitDto hit = sc.addHit("3112", "/rrr/tttt", "10.10.10.1", LocalDateTime.now());

        List<String> uris = List.of("/rrr/tttt", "/343434/ffff");

        System.out.println("hit = " + hit);
        System.out.println(
                "stats = " + sc.getStats(LocalDateTime.now().minusDays(1),
                        LocalDateTime.now().plusDays(1), uris, false)
        );
    }
}
