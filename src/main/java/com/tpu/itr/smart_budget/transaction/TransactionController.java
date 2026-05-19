package com.tpu.itr.smart_budget.transaction;


import com.tpu.itr.smart_budget.authentication.AuthController;
import com.tpu.itr.smart_budget.transaction.dto.AddApiRequest;
import com.tpu.itr.smart_budget.transaction.dto.SuccessResponse;
import com.tpu.itr.smart_budget.transaction.webClient.dto.TransactionsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @PostMapping("/addapi")
    public ResponseEntity<SuccessResponse> addApi(
            Authentication authentication,
            @RequestBody AddApiRequest addApiRequest
            )
    {
        Long userId = (Long) authentication.getPrincipal();
        log.info("TransactionController addApi called for user: "+ userId);
        transactionService.addApi(userId, addApiRequest);
        return ResponseEntity.ok(new SuccessResponse("Success"));
    }


    // Тестовый мапинг, потом удалю
    @PostMapping("/update")
    public ResponseEntity<SuccessResponse> update(
            Authentication authentication
    ){
        Long userId = (Long) authentication.getPrincipal();
        transactionService.update(userId);
        return ResponseEntity.ok(new SuccessResponse("Updated"));
    }

}
