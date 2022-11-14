package iot.service;

import iot.converter.CentralIoTHubConverter;
import iot.entity.CentralIoTHubEntity;
import iot.entity.DeviceInfoEntity;
import iot.entity.ResourceUtilizationPlanEntity;
import iot.exception.CentralHubNotFoundException;
import iot.exception.UserNotFoundException;
import iot.exception.UtilizationPlanException;
import iot.repository.CentralIoTHubRepository;
import iot.repository.DeviceInfoRepository;
import iot.repository.HubAndDeviceMappingRepository;
import iot.repository.UserAndCentralIoTHubMappingRepo;
import iot.request.RegisterCentralIoTHubRequest;
import iot.response.DeviceDetailsResponse;
import iot.response.GetAllDevicesResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Central io t hub service.
 */
public class CentralIoTHubService {
    private final UserAndCentralIoTHubMappingRepo userAndCentralIoTHubMappingRepo;
    private final HubAndDeviceMappingRepository hubAndDeviceMappingRepository;
    private final DeviceService deviceService;
    private final CentralIoTHubRepository centralIoTHubRepository;
    private final LoginSignUpService signUpService;
    private final ResourceUtilizationPlanService resourceUtilizationPlanService;

    /**
     * Instantiates a new Central io t hub service.
     *
     * @param userAndCentralIoTHubMappingRepo the user and central io t hub mapping repo
     * @param centralIoTHubRepository         the central io t hub repository
     * @param signUpService                   the sign up service
     * @param deviceService                   the device service
     * @param hubAndDeviceMappingRepository   the hub and device mapping repository
     * @param resourceUtilizationPlanService  the resource utilization plan service
     */
    public CentralIoTHubService(UserAndCentralIoTHubMappingRepo userAndCentralIoTHubMappingRepo,
                                CentralIoTHubRepository centralIoTHubRepository,
                                LoginSignUpService signUpService,
                                DeviceService deviceService,
                                HubAndDeviceMappingRepository hubAndDeviceMappingRepository,
                                ResourceUtilizationPlanService resourceUtilizationPlanService) {

        this.userAndCentralIoTHubMappingRepo = userAndCentralIoTHubMappingRepo;
        this.centralIoTHubRepository = centralIoTHubRepository;
        this.signUpService = signUpService;
        this.deviceService = deviceService;
        this.hubAndDeviceMappingRepository = hubAndDeviceMappingRepository;
        this.resourceUtilizationPlanService = resourceUtilizationPlanService;
    }

    /**
     * Register central io t hub central io t hub entity.
     *
     * @param token   the token
     * @param request the request
     * @return the central io t hub entity
     * @throws UserNotFoundException the user not found exception
     */
    public CentralIoTHubEntity registerCentralIoTHub(String token, RegisterCentralIoTHubRequest request)
            throws UserNotFoundException {
        final var user = signUpService.getUser(token);

        if (user == null) {
            throw new UserNotFoundException("user not found");
        }

        final var entity =
                CentralIoTHubConverter.toCentralIoTHubEntity(request.getName(), request.getSerialId());

        final var mappingEntity =
                CentralIoTHubConverter.toCentralIoTHubAndUserMappingEntity(user.getUserName(), entity.getId());

        userAndCentralIoTHubMappingRepo.saveCentralIoTHubForUser(mappingEntity);
        return centralIoTHubRepository.saveCentralIoTHubDetails(entity);
    }

    /**
     * Gets central io t hub.
     *
     * @param centralHubId the central hub id
     * @return the central io t hub
     * @throws CentralHubNotFoundException the central hub not found exception
     */
    public CentralIoTHubEntity getCentralIoTHub(String centralHubId) throws CentralHubNotFoundException {
        final var centralHub = centralIoTHubRepository.getCentralIoTHubDetails(centralHubId);

        if (centralHub == null) {
            throw  new CentralHubNotFoundException("central hub not found");
        }

        return centralHub;
    }

    /**
     * Gets all devices for hub.
     *
     * @param id the id
     * @return the all devices for hub
     * @throws CentralHubNotFoundException the central hub not found exception
     */
    public GetAllDevicesResponse getAllDevicesForHub(String id) throws CentralHubNotFoundException{
        final var hub = centralIoTHubRepository.getCentralIoTHubDetails(id);
        var deviceList = new ArrayList<DeviceDetailsResponse>();
        if(hub == null) {
            throw new CentralHubNotFoundException("central hub not found");
        }

        final var devices =
                hubAndDeviceMappingRepository.getAllDevicesForHub(hub.getId());

        for (final var deviceId : devices) {
            final var device = deviceService.getDevice(deviceId.getDeviceId());
            deviceList.add(device);
        }
        return GetAllDevicesResponse.builder()
                .setDevices(deviceList)
                .setHubDetails(hub)
                .build();
    }

    /**
     * Gets all devices.
     *
     * @param token the token
     * @return the all devices
     * @throws UserNotFoundException       the user not found exception
     * @throws CentralHubNotFoundException the central hub not found exception
     */
    public List<GetAllDevicesResponse> getAllDevices(String token)
            throws UserNotFoundException, CentralHubNotFoundException{
        final var user = signUpService.getUser(token);

        if (user == null) {
            throw new UserNotFoundException("user not found");
        }

        final var hubs =
                userAndCentralIoTHubMappingRepo.getCentralIoTHubForUser(user.getUserName());
        final var response = new ArrayList<GetAllDevicesResponse>();


        for (final var hubId : hubs) {
            final var devicesForCentralHub = getAllDevicesForHub(hubId.getHubId());
            response.add(devicesForCentralHub);
        }

        return response;
    }

    /**
     * Gets all devices by user.
     *
     * @param user the user
     * @return the all devices by user
     * @throws UserNotFoundException       the user not found exception
     * @throws CentralHubNotFoundException the central hub not found exception
     */
    public List<GetAllDevicesResponse> getAllDevicesByUser(String user)
            throws UserNotFoundException, CentralHubNotFoundException {

        if (user == null) {
            throw new UserNotFoundException("user not found");
        }

        final var hubs =
                userAndCentralIoTHubMappingRepo.getCentralIoTHubForUser(user);
        final var response = new ArrayList<GetAllDevicesResponse>();


        for (final var hubId : hubs) {
            final var devicesForCentralHub = getAllDevicesForHub(hubId.getHubId());
            response.add(devicesForCentralHub);
        }

        return response;
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
        return resourceUtilizationPlanService.getResourceUtilizationPlanEntity(deviceId);
    }

    /**
     * Update resource utilization plan entity resource utilization plan entity.
     *
     * @param plan the plan
     * @return the resource utilization plan entity
     */
    public ResourceUtilizationPlanEntity updateResourceUtilizationPlanEntity(ResourceUtilizationPlanEntity plan){
        return resourceUtilizationPlanService.getResourceUtilizationPlanEntity(plan);
    }
}
