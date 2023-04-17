<script setup>
import { reactive } from 'vue';
const getUrl = (name) => {
  return new URL(`../../assets/${name}`, import.meta.url).href
}

const options = reactive({
  width: '800px', //播放器高度
  height: '450px', //播放器高度
  color: "#409eff", //主题色
  muted: false, //静音
  webFullScreen: false,
  speedRate: ["0.75", "1.0", "1.25", "1.5", "2.0"], //播放倍速
  autoPlay: false, //自动播放
  loop: false, //循环播放
  mirror: false, //镜像画面
  ligthOff: false,  //关灯模式
  volume: 0.3, //默认音量大小
  control: true, //是否显示控制器
  title: '', //视频名称
  src: getUrl('2022迎新花絮-1.mp4'), //视频源
  poster: '', //封面
})
</script>

<template>
  <div style="display:flex;justify-content: center; align-items:center;">
    <vue3VideoPlay class="vue-video" v-bind="options" />
  </div>
</template>
