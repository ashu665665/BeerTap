package com.backend.dispenser.beertap.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class CreateDispenserRequest {

    private double flow_volume;

    public double getFlow_volume() {
        return flow_volume;
    }

    public void setFlow_volume(double flow_volume) {
        this.flow_volume = flow_volume;
    }
}
