package iot.processor;

import iot.bo.ElectricityMeterInfo;
import iot.bo.GasMeterInfo;
import iot.bo.WaterMeterInfo;
import iot.enums.DeviceType;
import iot.repository.ResourceUtilizationPlanRepository;

public class PlanConsumptionProcessor {
    private final ResourceUtilizationPlanRepository repository;

    public PlanConsumptionProcessor(ResourceUtilizationPlanRepository repository) {
        this.repository = repository;
    }

    public void processData() {

    }

    public void process() {
    }
}
