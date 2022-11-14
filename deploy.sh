#!/bin/bash
set -x #echo on

chmod u+x ~/IdeaProjects/iot-smart-utility-backend/deploy.sh


aws lambda update-function-code --function-name IOT_register_device --zip-file fileb://build/distributions/iot-smart-utility-backend.zip --region us-east-1
aws lambda update-function-code --function-name IOT_get_devices_for_hub --zip-file fileb://build/distributions/iot-smart-utility-backend.zip --region us-east-1
aws lambda update-function-code --function-name IOT_get_devices --zip-file fileb://build/distributions/iot-smart-utility-backend.zip --region us-east-1
aws lambda update-function-code --function-name IOT_get_device_info --zip-file fileb://build/distributions/iot-smart-utility-backend.zip --region us-east-1
aws lambda update-function-code --function-name IOT_update_device_info --zip-file fileb://build/distributions/iot-smart-utility-backend.zip --region us-east-1
aws lambda update-function-code --function-name IOT_create_resource_plan --zip-file fileb://build/distributions/iot-smart-utility-backend.zip --region us-east-1
aws lambda update-function-code --function-name IOT_get_resource_plan --zip-file fileb://build/distributions/iot-smart-utility-backend.zip --region us-east-1
aws lambda update-function-code --function-name IOT_register_central_iot_hub --zip-file fileb://build/distributions/iot-smart-utility-backend.zip --region us-east-1
aws lambda update-function-code --function-name IOT_User_Signup --zip-file fileb://build/distributions/iot-smart-utility-backend.zip --region us-east-1
aws lambda update-function-code --function-name IOT_User_Login --zip-file fileb://build/distributions/iot-smart-utility-backend.zip --region us-east-1
aws lambda update-function-code --function-name IOT_register_device_data --zip-file fileb://build/distributions/iot-smart-utility-backend.zip --region us-east-1

aws lambda update-function-code --function-name IOT_daily_consumption_processor --zip-file fileb://build/distributions/iot-smart-utility-backend.zip --region us-east-1
aws lambda update-function-code --function-name IOT_device_info_processor --zip-file fileb://build/distributions/iot-smart-utility-backend.zip --region us-east-1
aws lambda update-function-code --function-name IOT_resource_utilization_plan_processor --zip-file fileb://build/distributions/iot-smart-utility-backend.zip --region us-east-1
