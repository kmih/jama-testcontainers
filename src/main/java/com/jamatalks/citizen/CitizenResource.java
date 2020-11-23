package com.jamatalks.citizen;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CitizenResource {

    private final CitizenRepository citizenRepository;
    private final FineClient fineClient;

    public CitizenResource(CitizenRepository citizenRepository, FineClient fineClient) {
        this.citizenRepository = citizenRepository;
        this.fineClient = fineClient;
    }

    @GetMapping("/citizens/{identification}")
    @ResponseStatus(HttpStatus.OK)
    public CitizenStatisticDto getCitizenStatistic(@PathVariable String identification) {
        Citizen citizen = citizenRepository.findByIdentificationNumber(identification)
                .orElseThrow(() -> new ResourceNotFoundException("No such citizen"));
        BigDecimal finesTotal = fineClient.getFines(citizen)
                .stream()
                .filter(fine -> !fine.isPaid())
                .map(Fine::getSum)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .stripTrailingZeros();

        CitizenStatisticDto citizenStatisticDto = new CitizenStatisticDto();
        citizenStatisticDto.firstName = citizen.getFirstName();
        citizenStatisticDto.lastName = citizen.getLastName();
        citizenStatisticDto.finesTotal = finesTotal;
        return citizenStatisticDto;
    }

}
