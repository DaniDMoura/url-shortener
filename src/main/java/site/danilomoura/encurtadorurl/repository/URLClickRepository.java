package site.danilomoura.encurtadorurl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.danilomoura.encurtadorurl.entity.URLClick;

public interface URLClickRepository extends JpaRepository<URLClick, Long> {
}
