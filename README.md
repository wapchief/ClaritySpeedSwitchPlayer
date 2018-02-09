# QiNiuPalyer

集成了

### 七牛视频播放器

### GSY视频播放器

### JiaoZi播放器（基于自带MediaPlayer播放器）

> 实现倍速切换(Android 5.0以下不支持)、清晰度切换，下载功能
>
> 1、需要重写[JZMediaInterface](https://github.com/wapchief/QiNiuPlayer/blob/master/app/src/main/java/com/wapchief/qiniuplayer/views/MyJZVideoPlayerStandard.java)，拿到MediaPlayer控件，设置倍速控制。
>
> 2、需要重写[JZVideoPlayerStandard](https://github.com/wapchief/QiNiuPlayer/blob/master/app/src/main/java/com/wapchief/qiniuplayer/system/MyJZMediaSystem.java)，并且重写[XML布局](https://github.com/wapchief/QiNiuPlayer/blob/master/app/src/main/res/layout/jiaozi_player_video.xml)，添加倍速、下载切换的按钮

![](https://github.com/wapchief/QiNiuPlayer/blob/master/screenshots/device-2018-02-09-145921.png?raw=true)

![](https://github.com/wapchief/QiNiuPlayer/blob/master/screenshots/device-2018-02-09-150102.png?raw=true)