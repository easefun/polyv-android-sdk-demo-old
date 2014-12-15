polyv-android-sdk-demo
======================

主要演示polyv视频下载，本地播放，网络播放，视频拍摄和上传功能

配置
--
下载本案例，在eclipse创建android项目，选择"android project from existing code"

额外需要的包有

	httpclient-android-4.3.3.jar
	httpmime-4.3.5.jar

项目需要android.support.v7支持	

选择 File > Import.
Select Existing Android Code Into Workspace 选择下一步.
浏览到Android SDK安装目录继续进入<sdk>/extras/android/support/v7/appcompat/.
点击完成. eclipse会创建一个新项目叫android-support-v7-appcompat.

在当前项目选择项目属性-> android，在library点击add按钮，选择android-support-v7-appcompat。


描述
--
      先初始化PolyvSDKClient，设置token
      	PolyvSDKClient client = PolyvSDKClient.getInstance();
	client.setReadtoken("nsJ7ZgQMN0-QsVkscukWt-qLfodxoDFm");
	client.setWritetoken("Y07Q4yopIVXN83n-MPoIlirBKmrMPJu0");
		
参考MainActivity按钮事件演示代码

<img src="https://cloud.githubusercontent.com/assets/3022663/4606614/4593a3e2-5227-11e4-8108-e1ef286ca087.png" alt="" style="width: 200px;"/>


  
