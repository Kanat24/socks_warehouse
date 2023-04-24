package socks_warehouse.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socks_warehouse.model.Socks;
import socks_warehouse.repositories.SocksRepository;
import socks_warehouse.services.SocksService;


@RestController
@RequestMapping("/api/socks")
public class SocksController {
    private final SocksService socksService;

    public SocksController(SocksService socksService, SocksRepository socksRepository) {
        this.socksService = socksService;
    }

    @GetMapping()
    public String getAllSocks(@RequestParam("color") String color,
                              @RequestParam("operation") String operation,
                              @RequestParam("cottonPart") int cottonPart) {

        return socksService.getAllSocks(color, operation, cottonPart);
    }

    @PostMapping("/income")
    public ResponseEntity<Socks> incomeSocks(@RequestBody Socks socks) {
        if (socks == null) {
            ResponseEntity.badRequest().build();
        }
        assert socks != null;
        Socks socksNew = socksService.addSocks(socks);
        return ResponseEntity.status(HttpStatus.OK).body(socksNew);
    }

    @PostMapping("/outcome")
    public ResponseEntity<Socks> outcomeSocks(@RequestBody Socks socks) {
        if (socks == null) {
            return ResponseEntity.badRequest().build();
        } else {
            socksService.deleteSocks(socks);
        }
        return ResponseEntity.status(HttpStatus.OK).body(socks);
    }

}
