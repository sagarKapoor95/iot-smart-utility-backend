package iot.service;

import com.amazonaws.services.dynamodbv2.document.Item;
import iot.converter.ResourcePlanUtilizationConverter;
import iot.entity.ResourceUtilizationPlanEntity;
import iot.exception.DeviceNotFoundException;
import iot.exception.UtilizationPlanException;
import iot.repository.ResourceUtilizationPlanRepository;
import iot.request.CreatePlanRequest;

/**
 * The type Resource utilization plan service.
 */
public class ResourceUtilizationPlanService {
    private final ResourceUtilizationPlanRepository repository;
    private final DeviceService deviceService;

    /**
     * Instantiates a new Resource utilization plan service.
     *
     * @param repository the repository
     */
    public ResourceUtilizationPlanService(ResourceUtilizationPlanRepository repository, DeviceService deviceService) {
        this.repository = repository;
        this.deviceService = deviceService;
    }

    /**
     * Create resource utilization plan resource utilization plan entity.
     *
     * @param deviceId the device id
     * @param request  the request
     * @return the resource utilization plan entity
     */
    public ResourceUtilizationPlanEntity createResourceUtilizationPlan(String deviceId, CreatePlanRequest request)
    throws DeviceNotFoundException{
        final var deviceDetails = deviceService.getDevice(deviceId);

        if(deviceDetails == null) {
            throw new DeviceNotFoundException("device not found");
        }

        final var entity =
                ResourcePlanUtilizationConverter.toResourcePlanUtilizationEntity(deviceId, request);

        return repository.saveResourceUtilizationPlanEntity(entity);
    }

    /**
     * Gets resource utilization plan entity.
     *
     * @param deviceId the device id
     * @return the resource utilization plan entity
     * @throws UtilizationPlanException the utilization plan exception
     */
    public ResourceUtilizationPlanEntity getResourceUtilizationPlanEntity(String deviceId)
            throws UtilizationPlanException {
        final var entity = repository.getResourceUtilizationPlanEntity(deviceId);

        if (entity == null) {
            throw new UtilizationPlanException("utilization plan not found");
        }

        return entity;
    }
}
