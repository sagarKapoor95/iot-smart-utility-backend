package iot.repository;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.RangeKeyCondition;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import iot.bo.DevicesInfo;
import iot.converter.DailyConsumptionsConverter;
import iot.converter.DeviceInfoConverter;
import iot.converter.IndicatorStatusConverter;
import iot.entity.DailyConsumptionEntity;
import iot.entity.DevicesInfoEntity;
import iot.entity.IndicatorStateEntity;
import iot.utility.JsonUtil;

import java.util.ArrayList;
import java.util.List;

import static iot.constant.constant.*;

public class DevicesInfoRepository {
    private final Table table;

    public DevicesInfoRepository(Table table) {
        this.table = table;
    }

    public List<DevicesInfoEntity> getDevicesInfoInRange(Long startTimeStamp, Long endTimeStamp) {
        final var devices = new ArrayList<DevicesInfoEntity>();
        final var condition = new RangeKeyCondition("sk")
                .between(TIMESTAMP_KEY_PREFIX + startTimeStamp, TIMESTAMP_KEY_PREFIX + endTimeStamp);

        QuerySpec query = new QuerySpec()
                .withHashKey("pk", DEVICE_INFO_KEY_PREFIX)
                .withRangeKeyCondition(condition);

        for (final var item : this.table.query(query)) {
            devices.add(DeviceInfoConverter.toDevicesInfoEntity(item));
        }

        return devices;
    }

    public DevicesInfoEntity saveDevicesInfo(DevicesInfoEntity entity) {
        final var item = new Item()
                .withPrimaryKey("pk", entity.getPk(), "sk", entity.getSk())
                .withString("payload", JsonUtil.serialize(entity.getDevicesInfo()));

        table.putItem(item);
        return entity;
    }

    public IndicatorStateEntity saveIndicatorStatus(IndicatorStateEntity indicatorStateEntity) {
        final var item = new Item()
                .withPrimaryKey("pk", indicatorStateEntity.getPk(), "sk", indicatorStateEntity.getSk())
                .withBoolean("gas_leakage",indicatorStateEntity.getGasLeakage())
                .withBoolean("gas_pressure", indicatorStateEntity.getGasPressure())
                .withBoolean("gas_temperature", indicatorStateEntity.getGasTemperature())
                .withBoolean("gas_velocity", indicatorStateEntity.getGasVelocity())
                .withBoolean("electricity_voltage", indicatorStateEntity.getElectricityVoltage())
                .withDouble("water_consumption", indicatorStateEntity.getWaterConsumption())
                .withDouble("electricity_consumption", indicatorStateEntity.getElectricityConsumption())
                .withDouble("gas_consumption", indicatorStateEntity.getGasConsumption())
                .withBoolean("processed", indicatorStateEntity.isProcessed());

        table.putItem(item);
        return indicatorStateEntity;
    }

    public List<IndicatorStateEntity> getIndicatorsState(int size) {
        final var indicatorStateEntities = new ArrayList<IndicatorStateEntity>();
        final var condition = new RangeKeyCondition("sk")
                .beginsWith(TIMESTAMP_KEY_PREFIX);

        QuerySpec query = new QuerySpec()
                .withHashKey("pk", INDICATOR_STATUS_KEY)
                .withRangeKeyCondition(condition);

        query.setMaxResultSize(size);

        for (final var item : this.table.query(query)) {
            indicatorStateEntities.add(IndicatorStatusConverter.toIndicatorStateEntity(item));
        }

        return indicatorStateEntities;
    }

    public DailyConsumptionEntity saveConsumptions(DailyConsumptionEntity entity) {
        final var item = new Item()
                .withPrimaryKey("pk", entity.getPk(), "sk", entity.getSk())
                .withDouble("electricity_consumption",entity.getElectricityConsumption())
                .withDouble("water_consumption", entity.getWaterConsumption())
                .withDouble("gas_consumption", entity.getGasConsumption());

        table.putItem(item);
        return entity;
    }

    public List<DailyConsumptionEntity> getConsumptions(int limit) {
        final var dailyConsumptionEntities = new ArrayList<DailyConsumptionEntity>();
        final var condition = new RangeKeyCondition("sk")
                .beginsWith(TIMESTAMP_KEY_PREFIX);

        QuerySpec query = new QuerySpec()
                .withHashKey("pk", DAILY_CONSUMPTION_KEY)
                .withRangeKeyCondition(condition);

        query.setMaxResultSize(limit);

        for (final var item : this.table.query(query)) {
            dailyConsumptionEntities.add(DailyConsumptionsConverter.toDailyConsumptionEntity(item));
        }

        return dailyConsumptionEntities;
    }
}
