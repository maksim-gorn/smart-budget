package com.tpu.itr.smart_budget.limits;

import com.tpu.itr.smart_budget.common.MessageResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/limits")
public class LimitController {

    private final LimitService limitService;

    public LimitController(LimitService limitService) {
        this.limitService = limitService;
    }

    @PostMapping("/set")
    public MessageResponse setLimit(@RequestBody SetLimitRequest request) {
        return limitService.setLimit(request.getLimit());
    }

    @GetMapping("/notification")
    public NotificationResponse getNotification() {
        return limitService.getNotification();
    }
}