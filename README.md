# Android Pingpong motion tracker

## Current State

- [x] 与发球装置（esp8266 server）通信
  - [x] 实现在HttpPost类中。按"+"键，发送一个Http Post，控制电机旋转一周
- [x] 数据源（视频、摄像头）切换，视频文件路径填写
  - [x] SettingsActivity
- [x] 摄像头输入和视频读取逐帧调用统一接口进行处理（MainActivity）
  - [x] 摄像头输入（来自官方[example](https://github.com/bytedeco/sample-projects/blob/master/javacv-android-camera-preview/app/src/main/java/org/bytedeco/javacv/android/example/CvCameraPreview.java)，已封装好）：CvCameraPreview
  - [x] 视频输入(自行封装，只针对mp4视频，肯定还有bug但是基本能用)： VideoPreview
  - [x] 每一帧图像会转换为opencv_core.Mat格式，交给displayFragment的onCameraFrame处理
  - [ ] 还没有检查从视频和摄像头传给onCameraFrame的Mat编码是否一致OTZ

## Dependencies

javacv: 只要等Gradle自己下载配置好就可
