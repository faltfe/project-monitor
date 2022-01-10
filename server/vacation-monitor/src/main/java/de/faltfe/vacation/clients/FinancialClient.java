package de.faltfe.vacation.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "financial-monitor")
public interface FinancialClient {

    @RequestMapping(method = RequestMethod.GET, value = "/financial")
    String getFinancial();
}
