package com.tpu.itr.smart_budget.limits;

public class NotificationResponse {
    private double overspend;

    public NotificationResponse(double overspend) {
        this.overspend = overspend;
    }

    public double getOverspend() { return overspend; }
    public void setOverspend(double overspend) { this.overspend = overspend; }
}