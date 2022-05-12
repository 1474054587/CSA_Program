<template>
  <div class="app-container">
    课程列表

    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="courseQuery.title" placeholder="课程名称" />
      </el-form-item>

      <el-form-item>
        <el-select
          v-model="courseQuery.status"
          clearable
          placeholder="课程状态"
        >
          <el-option value="Normal" label="已上架" />
          <el-option value="Draft" label="未上架" />
        </el-select>
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()"
        >查询</el-button
      >
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table :data="list" border fit highlight-current-row>
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column label="课程封面" width="180">
        <template slot-scope="scope">
          <html>
            <head>
              <meta name="referrer" content="no-referrer" />
            </head>
            <body>
              <img width="160" height="120" :src="scope.row.cover" />
            </body>
          </html>
        </template>
      </el-table-column>

      <el-table-column prop="title" label="课程名称" />

      <el-table-column prop="teacherName" label="UP主" width="110" />

      <el-table-column label="课程状态" width="80">
        <template slot-scope="scope">
          {{ scope.row.status === "Normal" ? "已上架" : "未上架" }}
        </template>
      </el-table-column>

      <!-- <el-table-column prop="lessonNum" label="课时数" width="70" /> -->

      <el-table-column prop="viewCount" label="浏览数量" width="70" />

      <el-table-column prop="buyCount" label="收藏数量" width="70" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <p>
            <el-button
              type="success"
              size="mini"
              icon="el-icon-upload2"
              @click="publish(scope.row.id, 'Normal')"
              v-if="scope.row.status == 'Draft'"
              >上架</el-button
            >
            <el-button
              type="success"
              size="mini"
              icon="el-icon-download"
              @click="publish(scope.row.id, 'Draft')"
              v-else
              >下架</el-button
            >
            <el-button
              type="info"
              size="mini"
              icon="el-icon-view"
              @click="preview(scope.row.bvid)"
              >预览</el-button
            >
          </p>
          <p>
            <el-button
              type="primary"
              size="mini"
              icon="el-icon-edit"
              @click="edit(scope.row)"
              >编辑</el-button
            >
            <el-button
              type="danger"
              size="mini"
              icon="el-icon-delete"
              @click="removeDataById(scope.row.id)"
              >删除</el-button
            >
          </p>
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
              <el-button @click="cancelEdit">取 消</el-button>
              <el-button type="primary" @click="update">确 定</el-button>
            </div>
          </el-dialog>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />
  </div>
</template>
<script>
//引入调用teacher.js文件
import courseApi from "@/api/edu/course";
import subjectApi from "@/api/edu/subject";

export default {
  //写核心代码位置
  // data:{
  // },
  data() {
    //定义变量和初始值
    return {
      list: null, //查询之后接口返回集合
      page: 1, //当前页
      limit: 10, //每页记录数
      total: 0, //总记录数
      courseQuery: {}, //条件封装对象
      videoInfo: {},
      subjectOneList: [],
      subjectTwoList: [],
      dialogFormVisible: false,
      form: {
        id: "",
        subjectOneId: "",
        subjectTwoId: "",
        title: "",
        status: "",
      },
    };
  },
  created() {
    this.getList();
    this.getOneSubject();
  },
  methods: {
    getList() {
      courseApi.getListCourse().then((response) => {
        this.list = response.data.list;
        console.log(this.list);
      });
    },
    subjectLevelOneChanged(value) {
      for (var i = 0; i < this.subjectOneList.length; i++) {
        var oneSubject = this.subjectOneList[i];
        if (value === oneSubject.id) {
          this.subjectTwoList = oneSubject.children;
          this.form.subjectTwoId = "";
        }
      }
    },
    getOneSubject() {
      subjectApi.getSubjectList().then((response) => {
        this.subjectOneList = response.data.list;
      });
    },
    getTwoSubject() {
      for (var i = 0; i < this.subjectOneList.length; i++) {
        var oneSubject = this.subjectOneList[i];
        if (this.form.subjectOneId === oneSubject.id) {
          this.subjectTwoList = oneSubject.children;
        }
      }
    },
    resetData() {
      this.courseQuery = {};
      this.getList();
    },
    publish(id, status) {
      var a = "上架";
      if (status == "Draft") {
        a = "下架";
      }
      this.$confirm("确定要" + a + "该课程?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
      }).then(() => {
        courseApi.publishCourse(id, status).then((response) => {
          this.$message({
            type: "success",
            message: "上架成功!",
          });
          //回到列表页面
          this.getList();
        });
      });
    },
    edit(video) {
      this.form.id = video.id;
      this.form.subjectOneId = video.subjectOneId;
      this.form.subjectTwoId = video.subjectTwoId;
      this.form.title = video.title;
      this.form.status = video.status;
      this.getTwoSubject();
      this.dialogFormVisible = true;
    },
    cancelEdit() {
      this.dialogFormVisible = false;
      this.form = {};
    },
    update() {
      this.dialogFormVisible = false;
      courseApi.updateCourse(this.form).then((response) => {
        this.$message({
          type: "success",
          message: "修改课程成功!",
        });
      });
      var delayInMilliseconds = 700; //ms
      setTimeout(function () {
        location.reload();
      }, delayInMilliseconds);
    },
    preview(bvid) {
      window.open("https://www.bilibili.com/video/" + bvid);
    },
    removeDataById(id) {
      this.$confirm(
        "此操作将永久删除此课程,且无法恢复。下架此课程可以达到类似的效果，确定要删除吗?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).then(() => {
        courseApi.deleteCourse(id).then((response) => {
          this.$message({
            type: "success",
            message: "删除课程成功!",
          });
        });
        var delayInMilliseconds = 700; //1 second
        setTimeout(function () {
          location.reload();
        }, delayInMilliseconds);
      });
    },
  },
};
</script>
