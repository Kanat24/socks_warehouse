package socks_warehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import socks_warehouse.model.Socks;

import java.util.List;

@Repository
public interface SocksRepository extends JpaRepository<Socks, Long> {
    Socks findSocksByColorAndCottonPart(String color, int cottonPart);

    List<Socks> findAllByColorAndCottonPartEquals(String color, int cottonPart);

    List<Socks> findAllByColorAndCottonPartLessThan(String color, int cottonPart);

    List<Socks> findAllByColorAndCottonPartGreaterThan(String color, int cottonPart);


}
