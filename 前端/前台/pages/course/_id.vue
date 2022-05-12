<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程详情 开始 -->
    <section class="container">
      <section class="path-wrap txtOf hLh30">
        <a href="#" title class="c-999 fsize14">首页</a>
        \
        <a href="#" title class="c-999 fsize14">{{ subjectOne.title }}</a>
        \
        <span class="c-333 fsize14">{{ subjectTwo.title }}</span>
      </section>
      <div>
        <article class="c-v-pic-wrap" style="height: 357px">
          <section class="p-h-video-box" id="videoPlay">
            <img
              height="357px"
              width="640px"
              :src="courseInfo.cover"
              :alt="courseInfo.title"
              class="dis c-v-pic"
            />
          </section>
        </article>
        <aside class="c-attr-wrap">
          <section class="ml20 mr15">
            <br />
            <h2 class="hLh30 txtOf mt15">
              <span class="c-fff fsize24">{{ courseInfo.title }}</span>
            </h2>
            <!-- <section class="c-attr-jg">
              <span class="c-fff">价格：</span>
              <b class="c-yellow" style="font-size: 24px"
                >￥{{ courseWebVo.price }}</b
              >
            </section> -->
            <br />
            <section class="c-attr-mt c-attr-undis">
              <span class="c-fff fsize14"
                >分类： {{ subjectOne.title }}&emsp;/&emsp;</span
              >
              <span class="c-fff fsize14">{{ subjectTwo.title }}</span>
            </section>
            <section class="c-attr-mt c-attr-undis">
              <span class="c-fff fsize14"
                >UP主： {{ courseInfo.teacherName }}</span
              >
            </section>
            <section class="c-attr-mt of">
              <span class="ml10 vam">
                <em class="icon18 scIcon"></em>
                <a class="c-fff vam" title="收藏" href="#">收藏</a>
              </span>
            </section>
            <!-- v-if="isbuy || Number(courseWebVo.price) === 0" -->
            <section class="c-attr-mt">
              <a
                @click="watch(null)"
                href="#"
                title="开始学习"
                class="comm-btn c-btn-3"
                >开始学习</a
              >
            </section>
          </section>
        </aside>
        <aside class="thr-attr-box">
          <ol class="thr-attr-ol">
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">收藏数</span>
                <br />
                <h6 class="c-fff f-fM mt10">{{ courseInfo.buyCount }}</h6>
              </aside>
            </li>
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">课时数</span>
                <br />
                <h6 class="c-fff f-fM mt10">{{ videoInfo.videos }}</h6>
              </aside>
            </li>
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">观看次数</span>
                <br />
                <h6 class="c-fff f-fM mt10">{{ courseInfo.viewCount }}</h6>
              </aside>
            </li>
          </ol>
        </aside>
        <div class="clear"></div>
      </div>
      <!-- /课程封面介绍 -->
      <div class="mt20 c-infor-box">
        <article class="fl col-7">
          <section class="mr30">
            <div class="i-box">
              <div>
                <section id="c-i-tabTitle" class="c-infor-tabTitle c-tab-title">
                  <a name="c-i" class="current" title="课程详情">课程详情</a>
                </section>
              </div>
              <article class="ml10 mr10 pt20">
                <div>
                  <h6 class="c-i-content c-infor-title">
                    <span>课程介绍</span>
                  </h6>
                  <div class="course-txt-body-wrap">
                    <section class="course-txt-body">
                      <p v-html="videoInfo.desc"></p>
                    </section>
                  </div>
                </div>
                <!-- /课程介绍 -->
                <div class="mt50">
                  <h6 class="c-g-content c-infor-title">
                    <span>课程目录</span>
                  </h6>
                  <section class="mt20">
                    <div class="lh-menu-wrap">
                      <menu id="lh-menu" class="lh-menu mt10 mr10">
                        <ul>
                          <!-- 文件目录 -->
                          <li
                            class="lh-menu-stair"
                            v-for="item in videoInfo.pages"
                            :key="item.page"
                          >
                            <a :title="item.part" class="current-1">
                              P{{ item.page }}&emsp;
                              {{ item.part }}
                              <p style="float: right">
                                <nbor>时长：</nbor>
                                <nbor v-if="item.duration / 60 < 10"
                                  >0{{ Math.trunc(item.duration / 60) }}</nbor
                                >
                                <nbor v-else>{{
                                  Math.trunc(item.duration / 60)
                                }}</nbor>
                                :
                                <nbor v-if="item.duration % 60 < 10"
                                  >0{{ item.duration % 60 }}</nbor
                                >
                                <nbor v-else>{{
                                  Math.trunc(item.duration % 60)
                                }}</nbor>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <button @click="watch(item.page)">
                                  立即观看
                                </button>
                              </p>
                            </a>
                            <!-- 
                            <ol class="lh-menu-ol" style="display: block">
                              <li
                                class="lh-menu-second ml30"
                                v-for="video in chapter.children"
                                :key="video.id"
                              >
                                <a
                                  :href="'/player/' + video.videoSourceId"
                                  target="_blank"
                                >
                                  <span class="fr">
                                    <i class="free-icon vam mr10">免费试听</i>
                                  </span>
                                  <em class="lh-menu-i-2 icon16 mr5">&nbsp;</em
                                  >{{ video.title }}
                                </a>
                              </li>
                            </ol> -->
                          </li>
                        </ul>
                      </menu>
                    </div>
                  </section>
                </div>
                <!-- /课程大纲 -->
              </article>
            </div>
          </section>
        </article>
        <aside class="fl col-3">
          <div class="i-box">
            <div>
              <section class="c-infor-tabTitle c-tab-title">
                <a title href="javascript:void(0)">UP主</a>
              </section>
              <section class="stud-act-list">
                <ul style="height: auto">
                  <li>
                    <div class="u-face">
                      <a :href="teacherId">
                        <img :src="teacher.avatar" width="50" height="50" alt />
                      </a>
                    </div>
                    <section class="hLh30 txtOf">
                      <a class="c-333 fsize16 fl" :href="teacherId">{{
                        teacher.name
                      }}</a>
                    </section>
                    <section class="hLh20 txtOf">
                      <span class="c-999">{{ teacher.mid }}</span>
                    </section>
                  </li>
                </ul>
              </section>
            </div>
          </div>
        </aside>
        <div class="clear"></div>
      </div>
    </section>
    <!-- /课程详情 结束 -->
  </div>
</template>
<script>
import courseApi from "@/api/course";
import teacherApi from "@/api/teacher";

export default {
  asyncData({ params, error }) {
    return { courseId: params.id };
  },
  data() {
    return {
      videoInfo: {},
      courseInfo: {},
      subjectOne: {},
      subjectTwo: {},
      teacher: {},
      teacherId: "",
      subjectOneList: [],
      subjectTwoList: [],
      page: "",
    };
  },
  created() {
    //在页面渲染之前执行
    this.initCourseInfo();
  },
  methods: {
    initCourseInfo() {
      courseApi.getCourseInfo(this.courseId).then((response) => {
        this.courseInfo = response.data.data.courseInfo;
        this.initVideoInfo(this.courseInfo.bvid);
        this.initSubject(this.courseInfo);
        this.initTeacher(this.courseInfo);
      });
    },
    initVideoInfo(bvid) {
      courseApi.getVideoInfo(bvid).then((response) => {
        this.videoInfo = JSON.parse(response.data.data.json).data;
      });
    },
    initSubject(courseInfo) {
      courseApi.getAllSubject().then((response) => {
        this.subjectOneList = response.data.data.list;
        for (var i = 0; i < this.subjectOneList.length; i++) {
          var oneSubject = this.subjectOneList[i];
          if (courseInfo.subjectOneId == oneSubject.id) {
            this.subjectOne = oneSubject;
            this.subjectTwoList = oneSubject.children;
          }
        }
        for (var i = 0; i < this.subjectTwoList.length; i++) {
          var twoSubject = this.subjectTwoList[i];
          if (courseInfo.subjectTwoId == twoSubject.id) {
            this.subjectTwo = twoSubject;
          }
        }
      });
      // courseApi.getAllSubject().then((response) => {
      //   this.subjectNestedList = response.data.data.list;
      // });
    },
    initTeacher(courseInfo) {
      teacherApi.getTeacherInfo(courseInfo.teacherId).then((response) => {
        this.teacher = response.data.data.teacher;
        this.teacherId = "http://localhost:3000/teacher/" + this.teacher.id;
      });
    },
    watch(page) {
      if (page == null) {
        window.open("https://www.bilibili.com/video/" + this.courseInfo.bvid);
      } else {
        window.open(
          "https://www.bilibili.com/video/" +
            this.courseInfo.bvid +
            "?p=" +
            page
        );
      }
    },
    //查询课程详情信息
    // initCourseInfo() {
    //   courseApi.getCourseInfo(this.courseId).then((response) => {
    //     (this.courseWebVo = response.data.data.courseWebVo),
    //       (this.chapterVideoList = response.data.data.chapterVideoList),
    //       (this.isbuy = response.data.data.isBuy);
    //   });
    // },
    //生成订单
    // createOrders() {
    //   ordersApi.createOrders(this.courseId).then((response) => {
    //     //获取返回订单号
    //     //生成订单之后，跳转订单显示页面
    //     this.$router.push({ path: "/orders/" + response.data.data.orderId });
    //   });
    // },
  },
}
</script>
