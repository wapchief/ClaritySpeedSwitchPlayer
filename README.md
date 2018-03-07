# ClaritySpeedSwitchPlayer

集成了

### 七牛视频播放器

### GSY视频播放器

### JiaoZi播放器（自由切换播放MediaPlayer、ijkPlayer引擎、默认ijk）

> 倍速切换(ijk支持5.0以下)、清晰度切换，下载功能
>
> 直播流rtmp(仅ijk支持)
>
> 1、需要重写[JZMediaInterface](https://github.com/wapchief/QiNiuPlayer/blob/master/app/src/main/java/com/wapchief/qiniuplayer/views/MyJZVideoPlayerStandard.java)，拿到MediaPlayer控件，设置倍速控制。
>
> 2、需要重写[JZVideoPlayerStandard](https://github.com/wapchief/QiNiuPlayer/blob/master/app/src/main/java/com/wapchief/qiniuplayer/system/MyJZMediaSystem.java)，并且重写[XML布局](https://github.com/wapchief/QiNiuPlayer/blob/master/app/src/main/res/layout/jiaozi_player_video.xml)，添加倍速、下载切换的按钮
>
> m3u8视频下载(支持)


![](https://github.com/wapchief/QiNiuPlayer/blob/master/screenshots/device-2018-02-09-145921.png?raw=true)

![](https://github.com/wapchief/QiNiuPlayer/blob/master/screenshots/device-2018-02-09-150102.png?raw=true)