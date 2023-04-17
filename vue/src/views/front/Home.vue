<script setup>
import {reactive, ref} from "vue";
import router from "@/router"
import request from "@/utils/request";
import APlayer from "APlayer"; // 引入音乐插件
import "APlayer/dist/APlayer.min.css";
import {onMounted} from "vue"; // 引入音乐插件的样式
import {ElMessage} from "element-plus";
import {useUserStore} from "@/stores/user";
const userStore = useUserStore()
const user = userStore.getUser

  const getUrl = (name) => {
    return new URL(`../../assets/${name}`,import.meta.url).href
  }
  const imgs = ref([
    getUrl('rBABCmOgEGaAV_13AAAAAAAAAAA94.1080x534.jpeg'),
    getUrl('rBABC2OgEGeAfOkBAAAAAAAAAAA68.1139x653.jpeg'),
    getUrl('8.jpg'),
    getUrl('412.jpg'),
  ])

  const name = ref('')
  const state = reactive({
    hots: [],
    dynamics: [],
    news: []
  })

const sign=()=>{
    const location = localStorage.getItem("location")
    const username = user.username
    const userid=user.userId

    request.post("/sign", { user: username,user_id: userid, location: location }).then(res => {
        if (res.code === '200') {
            ElMessage.success("打卡成功")
        } else {
            ElMessage.error(res.msg)
        }
    })
}

const pageNum = ref(1)
const pageSize = ref(5)
const total = ref(0)
const load = () => {
  request.get('/dynamic/page', {
    params: {
      name: name.value,
      pageNum: pageNum.value,
      pageSize: pageSize.value
    }
  }).then(res => {
    state.dynamics = res.data.records
    total.value = res.data.total
  })

    request.get("/notice").then(res => {
        state.notice = res.data
    })
  request.get('/dynamic/hot').then(res => {
    state.hots = res.data
  })
  request.get('/news').then(res => {
    state.news = res.data
  })
}
load()  // 调用 load方法拿到后台数据

const refreshNews = () => {
    // 刷新新闻
  request.get('/news').then(res => {
    state.news = res.data
  })
}
const audio = [ // 歌曲列表
    {
        name: "遇见", // 歌曲名字
        artist: "孙燕姿", // 歌曲演唱者
        url: // 歌曲地址（这里用外链地址）
            "/src/assets/孙燕姿 - 遇见.mp3",
        cover: "https://imgessl.kugou.com/stdmusic/20200626/20200626180520721538.jpg", // 歌曲头像
        lrc: "", // 歌词
        theme: "rgb(127, 218, 180)", // 播放这首歌曲时的主题色
    },
    {
        name: "小幸运",
        artist: "田馥甄",
        url:
            "/src/assets/田馥甄 - 小幸运.MP3",
        cover: "https://imgessl.kugou.com/stdmusic/20200909/20200909122144241759.jpg",
        lrc: "",
        theme: "rgb(61, 162, 230)",
    },
    {
        name: "Summer 3D", // 歌曲名字
        artist: "徐梦圆", // 歌曲演唱者
        url: // 歌曲地址（这里用外链地址）
            "/src/assets/徐梦圆 - Summer 3D.mp3",
        cover: "https://imgessl.kugou.com/stdmusic/20200909/20200909125106930287.jpg", // 歌曲头像
        lrc: "", // 歌词
        theme: "rgb(127, 218, 180)", // 播放这首歌曲时的主题色
    },
    {
        name: "3D RUN",
        artist: "徐梦圆",
        url:
            "/src/assets/徐梦圆 - 3D RUN.mp3",
        cover: "https://imgessl.kugou.com/stdmusic/20200909/20200909125106930287.jpg",
        lrc: "",
        theme: "rgb(61, 162, 230)",
    },
]
const info = {
    fixed: false, // 不开启吸底模式
    listFolded: true, // 折叠歌曲列表
    autoplay: true, // 开启自动播放
    preload: "auto", // 自动预加载歌曲
    loop: "all", // 播放循环模式、all全部循环 one单曲循环 none只播放一次
    order: "list", //  播放模式，list列表播放, random随机播放
}

const initAudio = () => {
    // 创建一个音乐播放器实例，并挂载到DOM上，同时进行相关配置
    const ap = new APlayer({
        container: document.getElementById("aplayer"),
        audio: audio, // 音乐信息
        info: info, // 其他配置信息
    })
}
onMounted(() => {
    // 初始化播放器
    initAudio()

    initw
})

const initw = () => {
    (function (a, h, g, f, e, d, c, b) {
        b = function () {
            d = h.createElement(g);
            c = h.getElementsByTagName(g)[0];
            d.src = e;
            d.charset = "utf-8";
            d.async = 1;
            c.parentNode.insertBefore(d, c)
        };
        a["SeniverseWeatherWidgetObject"] = f;
        a[f] || (a[f] = function () {
            (a[f].q = a[f].q || []).push(arguments)
        });
        a[f].l = +new Date();
        if (a.attachEvent) {
            a.attachEvent("onload", b)
        } else {
            a.addEventListener("load", b, false)
        }
    }(window, document, "script", "SeniverseWeatherWidget", "//cdn.sencdn.com/widget2/static/js/bundle.js?t=" + parseInt((new Date().getTime() / 100000000).toString(), 10)));
    window.SeniverseWeatherWidget('show', {
        flavor: "slim",
        location: "WS0E9D8WN298",
        geolocation: true,
        language: "zh-Hans",
        unit: "c",
        theme: "auto",
        token: "fc738637-1b4d-4124-b9fd-6bc2d4c1a2d9",
        hover: "enabled",
        container: document.getElementById("tp-weather-widget")
    })
}
</script>

<template>
  <div>



    <div style="display: flex">
      <div style="width: 70%">
        <el-carousel :interval="5000" arrow="always" height="400px">
          <el-carousel-item v-for="item in imgs" :key="item" >
            <img :src="item" alt="" style="width: 100%; height: 100%">
          </el-carousel-item>
        </el-carousel>
      </div>
      <div style="width: 30%;  margin-left: 10px">
        <div  id="tp-weather-widget" ></div>
        <div v-is="'script'">
          (function(a,h,g,f,e,d,c,b){b=function(){d=h.createElement(g);c=h.getElementsByTagName(g)[0];d.src=e;d.charset="utf-8";d.async=1;c.parentNode.insertBefore(d,c)};a["SeniverseWeatherWidgetObject"]=f;a[f]||(a[f]=function(){(a[f].q=a[f].q||[]).push(arguments)});a[f].l=+new Date();if(a.attachEvent){a.attachEvent("onload",b)}else{a.addEventListener("load",b,false)}}(window,document,"script","SeniverseWeatherWidget","//cdn.sencdn.com/widget2/static/js/bundle.js?t="+parseInt((new Date().getTime() / 100000000).toString(),10)));
          window.SeniverseWeatherWidget('show', {
          flavor: "slim",
          location: "WS0E9D8WN298",
          geolocation: true,
          language: "zh-Hans",
          unit: "c",
          theme: "auto",
          token: "fc738637-1b4d-4124-b9fd-6bc2d4c1a2d9",
          hover: "enabled",
          container: "tp-weather-widget"
          })
        </div>
        <el-card style="max-height: 400px;margin-top: 20px" shadow="hover">
          <div style="padding-bottom: 10px; border-bottom: 1px solid #ccc; font-size: 20px; font-weight: bold; color: orangered">热门动态</div>
          <div v-for="(item, index) in state.hots" :key="item.id" style="margin: 10px 0; display: flex; cursor: pointer"  @click="router.push('/front/detail?id=' + item.id)">
            <div class="overflowShow" style="flex: 1;"><span style="color: goldenrod">{{ index + 1 }}</span><span style="margin-left: 10px">{{ item.name }}</span></div>
            <div style="width: 60px; display: flex; align-items: center"><img src="@/assets/hot.svg" alt="" style="width: 20px;"><span style="color: red"> {{ item.hot }}</span></div>
          </div>

        </el-card>

      </div>
    </div>

    <div style="display: flex; margin: 10px 0;">
      <div style="width: 70%; color: #666">
        <el-card>
          <div v-for="item in state.dynamics" :key="item.id" style="border-bottom: 1px solid #ddd; padding: 20px; cursor: pointer"
               @click="router.push('/front/detail?id=' + item.id)">

            <div style="display: flex">
              <div style="width: 50%; display: flex">
                <img v-if="item.user" :src="item.user.avatar" alt="" style="width: 40px; height: 40px; border-radius: 50%">
                <div style="flex: 1; line-height: 40px; margin-left: 10px; " v-if="item.user" >
                  <span style="font-weight: bold; font-size: 16px">{{ item.user.name }}</span>
                  <span style="margin-left: 10px; font-size: 12px; color: #888">{{ item.time }}</span>
                </div>
              </div>
              <div style="flex: 1;">

              </div>
            </div>

            <div style="margin: 10px 0">
              <div style="color: #000; font-size: 18px; font-weight: bold">{{ item.name }}</div>
            </div>

            <div style="margin: 10px 0">
              <div style="color: #666; font-size: 14px">{{ item.descr }}</div>
            </div>

            <div style="margin: 10px 0">
              <div><img :src="item.img " alt="" style="width: 40%; border-radius: 10px"></div>
            </div>

            <div style="display: flex">
              <div style="width: 50%">
                <el-tag style="margin-right: 10px" v-for="tag in item.tags" :key="tag"># {{ tag }} </el-tag>
              </div>
              <div style="width: 50%; text-align: right; font-size: 16px; color: #888">
                  <span>
                  <el-icon :size="20" style="top: 4px"><View /></el-icon>
                  <span style="margin-left: 3px">{{ item.view }}</span>
                </span>
                <span style="margin-left: 10px">
                  <el-icon :size="20" style="top: 4px"><Star /></el-icon>
                  <span style="margin-left: 3px">{{ item.collectCount }}</span>
                </span>
                <span style="margin-left: 10px">
                  <el-icon :size="20" style="top: 4px"><ChatLineSquare /></el-icon>
                  <span style="margin-left: 3px">{{ item.commentCount }}</span>
                </span>

                <span style="margin-left: 10px">
                  <el-icon :size="20" style="top: 4px"><img style="width: 20px" src="@/assets/点赞.svg" alt=""></el-icon>
                  <span style="margin-left: 3px">{{ item.praiseCount }}</span>
                </span>
              </div>
            </div>

          </div>
        </el-card>

        <el-card style="margin: 10px 0; background-color: white">
          <el-pagination
              @current-change="load"
              @size-change="load"
              v-model:current-page="pageNum"
              v-model:page-size="pageSize"
              background
              layout="total, prev, pager, next"
              :total="total"
          />
        </el-card>
      </div>

      <div style="width: 30%; margin-left: 10px">
        <el-card shadow="hover">
          <template #header>
            <div>
              <span style="font-size: 20px; font-weight: bold; color: #ff7329">公告列表</span>
            </div>
          </template>
          <div style="display: flex">
            <el-collapse accordion>
              <el-collapse-item v-for="(item,index) in state.notice" :key="item.id" :name="'' + index">
                <template #title>
                  <span style="font-size: 20px;">{{ item.name }}</span>
                  <span style="margin-left: 10px">{{ item.createTime }}</span>
                </template>
                <div v-html="item.content"></div>
              </el-collapse-item>
            </el-collapse>
          </div>
        </el-card>
        <el-card style="margin-top: 20px" shadow="hover">
          <div style="padding-bottom: 10px; border-bottom: 1px solid #ccc; ">
            <span style="font-size: 20px; font-weight: bold; color: orange">班级资讯</span>
            <span style="float: right; top: 5px; font-size: 14px; color: #888" class="refresh" @click="refreshNews"><el-icon style="top: 2px"><Refresh /></el-icon> 换一换</span>
          </div>
          <div v-for="(item, index) in state.news" :key="item.id" style="margin: 10px 0">
            <div style="cursor: pointer" class="overflowShow" @click="router.push('/front/news?id=' + item.id)"><span style="margin-left: 10px">{{ item.name }}</span></div>
          </div>
        </el-card>
        <el-card style="margin-top: 20px" shadow="hover">
            <!-- 准备一个容器用来存放音乐播放器 -->
            <div id="aplayer" style=" margin: 0 auto"></div>
        </el-card>
        <el-card style="margin-top: 20px" shadow="hover">
          <template #header>
            <div>
              <span style="font-size: 20px; font-weight: bold; color: #1E90FF">打卡</span>
            </div>
          </template>
          <div style="margin: 10px 0; font-size: 20px ;">{{ new Date().getFullYear() + '年' + (new Date().getMonth() + 1) + '月' + new Date().getDate() + '日' }}</div><!--text-align: center-->
          <div herf="..views/time.html"></div>
          <div style="align-items: center;width: 140px; height: 140px; line-height: 140px; border-radius: 50%; background-color: #1E90FF;
        font-size: 40px; color: #fff; text-align: center; cursor: pointer; box-shadow: 0 0 30px rgba(0, 0, 0, .2);" @click="sign">
            打 卡
          </div>
          <div>
            <div style="" class="location">{{ state.location }}</div>
            <div style="" class="time">{{ state.time }}</div>
          </div>
        </el-card>
      </div>
    </div>



  </div>
</template>

<style scoped>
.refresh:hover {
  cursor: pointer;
}
:deep(.el-card__body) {
  padding: 10px !important;
}
</style>