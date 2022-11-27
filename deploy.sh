#!/bin/bash
set -x #echo on

chmod u+x ~/IdeaProjects/iot-smart-utility-backend/deploy.sh

aws s3 cp ./build/distributions/iot-smart-utility-backend.zip s3://iot-lambda-function-s3-bucket

aws lambda update-function-code --function-name IOT_register_device --s3-bucket iot-lambda-function-s3-bucket --s3-key iot-smart-utility-backend.zip --region us-east-1
aws lambda update-function-code --function-name IOT_get_devices_for_hub --s3-bucket iot-lambda-function-s3-bucket --s3-key iot-smart-utility-backend.zip --region us-east-1
aws lambda update-function-code --function-name IOT_get_devices --s3-bucket iot-lambda-function-s3-bucket --s3-key iot-smart-utility-backend.zip --region us-east-1
aws lambda update-function-code --function-name IOT_get_device_info --s3-bucket iot-lambda-function-s3-bucket --s3-key iot-smart-utility-backend.zip --region us-east-1
aws lambda update-function-code --function-name IOT_update_device_info --s3-bucket iot-lambda-function-s3-bucket --s3-key iot-smart-utility-backend.zip --region us-east-1
aws lambda update-function-code --function-name IOT_create_resource_plan --s3-bucket iot-lambda-function-s3-bucket --s3-key iot-smart-utility-backend.zip --region us-east-1
aws lambda update-function-code --function-name IOT_get_resource_plan --s3-bucket iot-lambda-function-s3-bucket --s3-key iot-smart-utility-backend.zip --region us-east-1
aws lambda update-function-code --function-name IOT_register_central_iot_hub --s3-bucket iot-lambda-function-s3-bucket --s3-key iot-smart-utility-backend.zip --region us-east-1
aws lambda update-function-code --function-name IOT_User_Signup --s3-bucket iot-lambda-function-s3-bucket --s3-key iot-smart-utility-backend.zip --region us-east-1
aws lambda update-function-code --function-name IOT_User_Login --s3-bucket iot-lambda-function-s3-bucket --s3-key iot-smart-utility-backend.zip --region us-east-1
aws lambda update-function-code --function-name IOT_register_device_data --s3-bucket iot-lambda-function-s3-bucket --s3-key iot-smart-utility-backend.zip --region us-east-1

aws lambda update-function-code --function-name IOT_daily_consumption_processor --s3-bucket iot-lambda-function-s3-bucket --s3-key iot-smart-utility-backend.zip --region us-east-1
aws lambda update-function-code --function-name IOT_device_info_processor --s3-bucket iot-lambda-function-s3-bucket --s3-key iot-smart-utility-backend.zip --region us-east-1
aws lambda update-function-code --function-name IOT_resource_utilization_plan_processor --s3-bucket iot-lambda-function-s3-bucket --s3-key iot-smart-utility-backend.zip --region us-east-1
