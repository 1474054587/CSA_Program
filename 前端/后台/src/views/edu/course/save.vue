<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="UP主">
        <el-select v-model="form.teacherId" placeholder="请选择">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"
          />
        </el-select>
        &emsp;
        <el-button type="primary" icon="el-icon-search" @click="search()"
          >查询</el-button
        >
      </el-form-item>
    </el-form>
    <el-table :data="videos" border fit highlight-current-row v-show="hasUP">
      <el-table-column label="序号" width="60" align="center">
        <template slot-scope="scope">
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column label="封面" width="180">
        <template slot-scope="scope">
          <html>
            <head>
              <meta name="referrer" content="no-referrer" />
            </head>
            <body>
              <img width="160" height="120" :src="scope.row.pic" />
            </body>
          </html>
        </template>
      </el-table-column>

      <el-table-column prop="title" label="标题" width="180" />

      <el-table-column prop="description" label="描述" width="385" />

      <el-table-column label="操作" width="190" align="center">
        <template slot-scope="scope">
          <el-button
            type="info"
            size="mini"
            icon="el-icon-view"
            @click="preview(scope.row.bvid)"
            >预览</el-button
          >
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-circle-plus-outline"
            @click="editCourse(scope.row)"
            v-if="scope.row.aid"
            v-text="text.add"
          />
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-circle-plus-outline"
            disabled
            v-else
            v-text="text.forbidToAdd"
          />
          <el-dialog title="添加课程" :visible.sync="dialogFormVisible">
            <el-form :model="form">
              <el-form-item label="课程名称" :label-width="formLabelWidth">
                <el-input v-model="form.title"></el-input>
              </el-form-item>
              <el-form-item label="课程分类">
                <el-select
                  v-model="form.subjectOneId"
                  placeholder="一级分类"
                  @change="subjectLevelOneChanged"
                >
                  <el-option
                    v-for="subject in subjectOneList"
                    :key="subject.id"
                    :label="subject.title"
                    :value="subject.id"
                  />
                </el-select>
                <el-select v-model="form.subjectTwoId" placeholder="二级分类">
                  <el-option
                    v-for="subject in subjectTwoList"
                    :key="subject.id"
                    :label="subject.title"
                    :value="subject.id"
                  />
                </el-select>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="cancelAdd">取 消</el-button>
              <el-button type="primary" @click="addCourse">确 定</el-button>
            </div>
          </el-dialog>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      v-show="hasUP"
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center"
      layout="total, prev, pager, next, jumper"
      @current-change="search"
    />
  </div>
</template>
<script>
import courseApi from "@/api/edu/course";
import subjectApi from "@/api/edu/subject";
import videoApi from "@/api/bilibili/video";
import Tinymce from "@/components/Tinymce";

export default {
  components: { Tinymce },
  data() {
    return {
      title: "",
      teacherMid: "",
      parts: [],
      teacherList: [],
      subjectOneList: [],
      subjectTwoList: [],
      videos: [],
      courseQuery: {},
      page: 1,
      limit: 30,
      total: 0,
      courseInfo: {},
      dialogFormVisible: false,
      form: {
        title: "",
        bvid: "",
        teacherId: "",
        teacherName: "",
        subjectOneId: "",
        subjectTwoId: "",
        cover: "",
      },
      formLabelWidth: "120px",
      hasUP: false,
      text: {
        add: "\xa0\xa0添加\xa0\xa0",
        forbidToAdd: "已添加",
      },
    };
  },
  created() {
    //初始化所有讲师
    this.getTeacherList();
    //初始化一级分类
    this.getOneSubject();
    this.getCourseList();
  },
  methods: {
    //点击某个一级分类，触发change，显示对应二级分类
    subjectLevelOneChanged(value) {
      //value就是一级分类id值
      //遍历所有的分类，包含一级和二级
      for (var i = 0; i < this.subjectOneList.length; i++) {
        //每个一级分类
        var oneSubject = this.subjectOneList[i];
        //判断：所有一级分类id 和 点击一级分类id是否一样
        if (value === oneSubject.id) {
          //从一级分类获取里面所有的二级分类
          this.subjectTwoList = oneSubject.children;
          //把二级分类id值清空
          this.form.subjectTwoId = "";
        }
      }
    },
    //查询所有的一级分类
    getOneSubject() {
      subjectApi.getSubjectList().then((response) => {
        this.subjectOneList = response.data.list;
      });
    },
    //查询所有的讲师
    getTeacherList() {
      courseApi.getListTeacher().then((response) => {
        this.teacherList = response.data.items;
      });
    },
    getCourseList() {
      courseApi.getListCourse().then((response) => {
        this.courseQuery = response.data.list;
      });
    },
    search(page = 1) {
      this.hasUP = true;
      this.page = page;
      for (var i in this.teacherList) {
        if (this.teacherList[i].id == this.form.teacherId) {
          this.teacherMid = this.teacherList[i].mid;
          break;
        }
      }
      videoApi
        .getVideoListByUP(this.teacherMid, this.page, this.limit)
        .then((response) => {
          this.videos = JSON.parse(response.data.json).data.list.vlist;
          this.total = JSON.parse(response.data.json).data.page.count;
          console.log(this.courseQuery);
          for (var i = 0; i < this.videos.length; i++) {
            for (var j = 0; j < this.courseQuery.length; j++) {
              if (this.courseQuery[j].bvid == this.videos[i].bvid) {
                this.videos[i].aid = false;
                console.log(this.videos[i].bvid);
                console.log(this.videos[i].aid);
              }
            }
          }
        });
    },
    preview(bvid) {
      window.open("https://www.bilibili.com/video/" + bvid);
      // videoApi.getVideoInfo(bvid).then((response1) => {
      //   this.videoInfo = JSON.parse(response1.data.json).data;
      //   this.parts = this.videoInfo.pages;
      //   this.bvid = bvid;
      //   this.cid = this.parts[0].cid;
      //   this.getSrc(bvid, this.cid);
      // });
    },
    editCourse(video) {
      this.form.title = video.title;
      this.form.bvid = video.bvid;
      this.form.cover = video.pic;
      this.form.teacherName = video.author;
      this.dialogFormVisible = true;
    },
    //添加课程
    addCourse() {
      this.dialogFormVisible = false;
      courseApi.addCourse(this.form).then((response) => {
        this.$message({
          type: "success",
          message: "添加课程成功!",
        });
        this.$router.push({
          path: "/course/list",
        });
      });
    },
    cancelAdd() {
      this.dialogFormVisible = false;
      this.form = {};
    },
    //修改课程
    // updateCourse() {
    //   course.updateCourseInfo(this.courseInfo).then((response) => {
    //     //提示
    //     this.$message({
    //       type: "success",
    //       message: "修改课程信息成功!",
    //     });
    //     //跳转到第二步
    //     this.$router.push({ path: "/course/chapter/" + this.courseId });
    //   });
    // },
    // saveOrUpdate() {
    //   //判断添加还是修改
    //   if (!this.courseInfo.id) {
    //     //添加
    //     this.addCourse();
    //   } else {
    //     this.updateCourse();
    //   }
    // },
  },
  // getSrc(bvid, cid) {
  //   videoApi.getVideoSrc(bvid, cid).then((response2) => {
  //     console.log(bvid);
  //     console.log(cid);
  //     this.videoSrc = JSON.parse(response2.data.json).data.durl[0].url;
  //     console.log(this.videoSrc);
  //     this.play(this.videoSrc);
  //   });
  // },
  // play(src) {
  //   console.log("play is running");
  //   console.log(src);
  //   var str =
  //     '<html lang="en">' +
  //     "<head>" +
  //     '<meta name="referrer" content="no-referrer"/> ' +
  //     '<meta charset="UTF-8">' +
  //     '<meta http-equiv="X-UA-Compatible" content="IE=edge">' +
  //     "</head>" +
  //     "<body>" +
  //     '<video controls autoplay width="300px" height="200px" >' +
  //     "<source src=" +
  //     src +
  //     ' type="video/mp4">' +
  //     "</video></body></html>";
  //   this.$alert(str, "", {
  //     dangerouslyUseHTMLString: true,
  //   });
  // },
};
</script>
<style scoped>
.tinymce-container {
  line-height: 29px;
}
</style>