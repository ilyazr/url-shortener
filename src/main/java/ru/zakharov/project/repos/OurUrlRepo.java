package ru.zakharov.project.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zakharov.project.domain.OurUrl;

public interface OurUrlRepo extends JpaRepository<OurUrl, Integer> {
    OurUrl findByLongUrl(String longUrl);
    OurUrl findByToken(String token);
}
