polyv-android-sdk-demo
======================

主要演示polyv视频下载，本地播放，网络播放，视频拍摄和上传功能

配置
--
下载本案例，在eclipse创建android项目，选择"android project from existing code"


描述
--
      先初始化PolyvSDKClient，设置token
      
      PolyvSDKClient client = PolyvSDKClient.getInstance();
			client.setReadtoken("nsJ7ZgQMN0-QsVkscukWt-qLfodxoDFm");
			client.setWritetoken("Y07Q4yopIVXN83n-MPoIlirBKmrMPJu0");
			
参考MainActivity按钮事件演示代码
  
