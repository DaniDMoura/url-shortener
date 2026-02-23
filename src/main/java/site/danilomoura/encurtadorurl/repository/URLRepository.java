package site.danilomoura.encurtadorurl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.danilomoura.encurtadorurl.entity.URL;

import java.util.Optional;

public interface URLRepository extends JpaRepository<URL, Long> {
    Optional<URL> getBySlug(String slug);
}
