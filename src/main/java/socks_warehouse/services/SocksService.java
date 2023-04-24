package socks_warehouse.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import socks_warehouse.model.Socks;
import socks_warehouse.repositories.SocksRepository;

import java.util.List;

@Service
public class SocksService {
    private final SocksRepository socksRepository;
    Logger logger = LoggerFactory.getLogger(SocksService.class);

    public SocksService(SocksRepository socksRepository) {
        this.socksRepository = socksRepository;
    }

    public String getAllSocks(String color, String operation, Integer cottonPart) {
        logger.info("Return the total number of socks in stock");
        List<Socks> socksFromRep1 = socksRepository.findAllByColorAndCottonPartGreaterThan(color, cottonPart);
        List<Socks> socksFromRep2 = socksRepository.findAllByColorAndCottonPartLessThan(color, cottonPart);
        List<Socks> socksFromRep3 = socksRepository.findAllByColorAndCottonPartEquals(color, cottonPart);
        switch (operation) {
            case "moreThan" -> {
                List<Socks> socksList1 = socksFromRep1.stream().
                        filter(s -> s.getCottonPart() >= cottonPart).toList();
                if (socksList1.isEmpty()) {
                    return "Товар подходящий под данные критерии отсутстыует на складе!";
                }
                int sumSocksList = socksList1.stream().mapToInt(Socks::getQuantity).sum();
                return "Общее количество носков " + color + " цвета с процентом содержания хлопка в составе больше " + cottonPart + " процентов, равно " + sumSocksList;
            }
            case "lessThan" -> {
                List<Socks> socksList2 = socksFromRep2.stream().filter(s -> s.getCottonPart() <= cottonPart).toList();
                if (socksList2.isEmpty()) {
                    return "Товар подходящий под данные критерии отсутстыует на складе!";
                }
                int sumSocksList = socksList2.stream().mapToInt(Socks::getQuantity).sum();
                return "Общее количество носков " + color + " цвета  с процентом содержания хлопка в составе меньше " + cottonPart + " процентов, равно " + sumSocksList;
            }
            case "equal" -> {
                List<Socks> socksList3 = socksFromRep3.stream().filter(s -> s.getCottonPart() == cottonPart).toList();
                if (socksList3.isEmpty()) {
                    return "Товар подходящий под данные критерии отсутстыует на складе!";
                }
                int sumSocksList = socksList3.stream().mapToInt(Socks::getQuantity).sum();
                return "Общее количество носков " + color + " цвета  с процентом содержания хлопка в составе " + cottonPart + " процентов, равно " + sumSocksList;

            }
        }
        return "";
    }


    public Socks addSocks(Socks socks) {
        logger.info("Register the arrival of socks to the warehouse");
        Socks sockFromRep = socksRepository.findSocksByColorAndCottonPart(socks.getColor(), socks.getCottonPart());
        if (!socksRepository.findAll().contains(sockFromRep)) {
            return socksRepository.save(socks);
        } else {
            int quantity = socks.getQuantity() + sockFromRep.getQuantity();
            sockFromRep.setQuantity(quantity);
            return socksRepository.save(sockFromRep);
        }

    }

    public void deleteSocks(Socks socks) {
        logger.info("Registers the release of socks from the warehouse");
        Socks sockFromRep = socksRepository.findSocksByColorAndCottonPart(socks.getColor(), socks.getCottonPart());
        if (!socksRepository.findAll().contains(sockFromRep)) {
            throw new RuntimeException("Данного товара нет в наличии");
        }
        int quantity = sockFromRep.getQuantity() - socks.getQuantity();
        if (quantity < 0) {
            throw new RuntimeException("К сожалению, столько носков данной категории не имеется");
        } else if (quantity == 0) {
            socksRepository.deleteById(sockFromRep.getId());
        } else {
            sockFromRep.setQuantity(quantity);
            socksRepository.save(sockFromRep);
        }

    }
}
